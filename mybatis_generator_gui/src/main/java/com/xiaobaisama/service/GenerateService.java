package com.xiaobaisama.service;

import com.xiaobaisama.enums.TplName;
import com.xiaobaisama.pojo.Column;
import com.xiaobaisama.pojo.Model;
import com.xiaobaisama.pojo.Save;
import com.xiaobaisama.ui.AppContext;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by xiaobai on 2018/6/26.
 */
public class GenerateService {
    private Configuration cfg;

    public GenerateService() throws IOException {
        cfg = new Configuration(Configuration.VERSION_2_3_23);
        cfg.setDirectoryForTemplateLoading(new File(getClass().getResource("/").getPath()));
        cfg.setDefaultEncoding("UTF-8");
    }

    public String genData(String tpl,Model model) throws IOException, TemplateException {
        Template temp = cfg.getTemplate(tpl);
        StringWriter ws = new StringWriter();
        BufferedWriter bw = null;
        try{
            bw = new BufferedWriter(ws);
            temp.process(model,bw);
        }finally{
            if (bw != null){
                bw.close();
            }
        }
        return ws.toString();
    }

    public String genData(String key,String packa,String tableName) {
        if (key.equals("Pojo") || key.equals("XML") || key.equals("DAO") || key.equals("Service") || key.equals("Controller")){
            if (tableName == null){
                return "请选择表。。。";
            }
        }
        String data = "";
        Save save = AppContext.save;
        try{
            Model model = new Model();
            model.setPacka(packa);
            List<Column> columnList;
            switch (key){
                case "Pojo":
                    model.setTable(tableName);
                    columnList = AppContext.dbs.getColumnList(AppContext.dbName,tableName);
                    model.setColumnList(columnList);
                    data = genData(TplName.POJO,model);
                    break;
                case "XML":
                    model.setTable(tableName);
                    columnList = AppContext.dbs.getColumnList(AppContext.dbName,tableName);
                    model.setColumnList(columnList);
                    model.setPs(save.getBasePackage() + "." + save.getChildPackage().get("DAO") +"."+ model.getClazz() + "Mapper");
                    data = genData(TplName.XML,model);
                    break;
                case "DAO":
                    model.setTable(tableName);
                    model.addImport(save.getBasePackage() + "." + save.getChildPackage().get("Pojo") + "." + model
                            .getClazz());
                    data = genData(TplName.DAO,model);
                    break;
                case "Service":
                    model.setTable(tableName);
                    model.addImport(save.getBasePackage() + "." + save.getChildPackage().get("BaseDao") + "." +
                                            "BaseDao");
                    model.addImport(save.getBasePackage() + "." + save.getChildPackage().get("DAO") + "." + model
                            .getClazz() + "Mapper");
                    model.addImport(save.getBasePackage() + "." + save.getChildPackage().get("Pojo") + "." + model
                            .getClazz());
                    data = genData(TplName.SERVICE,model);
                    break;
                case "Controller":
                    model.setTable(tableName);
                    columnList = AppContext.dbs.getColumnList(AppContext.dbName,tableName);
                    model.setColumnList(columnList);
                    model.addImport(save.getBasePackage() + "." + save.getChildPackage().get("Page") + "." + "Page");
                    model.addImport(save.getBasePackage() + "." + save.getChildPackage().get("Result") + "." +
                                            "Result");
                    model.addImport(save.getBasePackage() + "." + save.getChildPackage().get("Pojo") + "." + model
                            .getClazz());
                    model.addImport(save.getBasePackage() + "." + save.getChildPackage().get("Service") + "." + model
                            .getClazz() + "Service");
                    data = genData(TplName.CONTROLLER,model);
                    break;
                case "BaseDao":
                    model.addImport(AppContext.save.getBasePackage() + "." + AppContext.save.getChildPackage().get
                            ("Page") + ".Page");
                    data = genData(TplName.BASE_DAO,model);
                    break;
                case "BaseService":
                    model.addImport(save.getBasePackage() + "." + save.getChildPackage().get("BaseDao") + "." +
                                            "BaseDao");
                    model.addImport(AppContext.save.getBasePackage() + "." + AppContext.save.getChildPackage().get
                            ("Page") + ".Page");
                    data = genData(TplName.BASE_SERVICE,model);
                    break;
                case "Page":
                    data = genData(TplName.PAGE,model);
                    break;
                case "MyException":
                    data = genData(TplName.MY_EXCEPTION,model);
                    break;
                case "Code":
                    data = genData(TplName.CODE,model);
                    break;
                case "Result":
                    model.addImport(save.getBasePackage() + "." + save.getChildPackage().get("Code") + "." + "Code");
                    data = genData(TplName.RESULT,model);
                    break;
            }
        }catch(IOException | TemplateException | SQLException e){
            AppContext.myDialog.alert(e);
        }
        return data;
    }

    public void genFile(File saveFile,String data) throws IOException {
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(saveFile);
            fos.write(data.getBytes("UTF-8"));
            fos.flush();
        }finally{
            if (fos != null){
                fos.close();
            }
        }
    }

    public File findDir(String projectPath,String packa) {
        File projectFile = new File(projectPath);
        if (! projectFile.isDirectory()){
            projectFile.mkdirs();
        }
        String[] packas = packa.split("\\.");
        File classDir = findClassDir(projectFile,packas[0]);
        if (classDir == null){
            classDir = projectFile;
        }
        for (String str : packas){
            classDir = new File(classDir,str);
        }
        classDir.mkdirs();
        return classDir;
    }

    private File findClassDir(File projectFile,String childName) {
        File[] files = projectFile.listFiles();
        if (files == null) return null;
        File resultFile = new File(projectFile.getAbsolutePath());
        for (File f : files){
            if (f.isFile()) continue;
            if (f.getName().equals(childName)){
                return resultFile;
            }
        }
        for (File f : files){
            File result = findClassDir(f,childName);
            if (result != null){
                return result;
            }
        }
        return null;
    }

    public String getFileName(String key,String tabelName) {
        Model model = new Model();
        model.setTable(tabelName);
        return getFileName(key,model);
    }

    public String getFileName(String key) {
        switch (key){
            case "BaseDao":
                return "BaseDao.java";
            case "BaseService":
                return "BaseService.java";
            case "Page":
                return "Page.java";
            case "MyException":
                return "MyException.java";
            case "Code":
                return "Code.java";
            case "Result":
                return "Result.java";
        }
        return "";
    }

    public String getFileName(String key,Model model) {
        switch (key){
            case "Pojo":
                return model.getClazz() + ".java";
            case "XML":
                return model.getClazz() + "Mapper.xml";
            case "DAO":
                return model.getClazz() + "Mapper.java";
            case "Service":
                return model.getClazz() + "Service.java";
            case "Controller":
                return model.getClazz() + "Controller.java";
            case "BaseDao":
                return "BaseDao.java";
            case "BaseService":
                return "BaseService.java";
            case "Page":
                return "Page.java";
            case "MyException":
                return "MyException.java";
            case "Code":
                return "Code.java";
            case "Result":
                return "Result.java";
        }
        return "";
    }
}