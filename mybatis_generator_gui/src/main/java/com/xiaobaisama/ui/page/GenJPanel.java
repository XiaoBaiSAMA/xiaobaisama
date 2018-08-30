package com.xiaobaisama.ui.page;


import com.xiaobaisama.enums.Config;
import com.xiaobaisama.service.GenerateService;
import com.xiaobaisama.ui.AppContext;
import com.xiaobaisama.ui.component.TitleLabel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaobai on 2018/6/27.
 */
public class GenJPanel extends JPanel {
    public static Font myFont;

    private JPanel jp_title;
    private JLabel jl_title;

    private JLabel jl_select;
    private JLabel jl_project;
    private JTextField jtf_project;
    private JButton jb_gen;
    private JButton jb_back;

    private JSplitPane jsp_content;

    private JTree jt_list;
    private JScrollPane jsp_left;

    private JPanel jp_right;
    private JTabbedPane jtp_tab;

    private final String[] tabNames = {"Pojo","XML","DAO","Service","Controller","BaseDao","BaseService","Page",
            "MyException","Code","Result"};
    private TabJPanel[] tabPanels;

    public GenJPanel() throws SQLException, IOException, FontFormatException {
        myFont = Font.createFont(Font.TRUETYPE_FONT,new File(Config.MY_FONT_PATH));
        myFont = myFont.deriveFont(Font.PLAIN,12);
        AppContext.gs = new GenerateService();
        setLayout(new BorderLayout());
        initTitle();
        initContent();
        initEvent();
    }

    private void initTitle() {
        jp_title = new JPanel();
        BoxLayout layout = new BoxLayout(jp_title,BoxLayout.X_AXIS);
        jp_title.setLayout(layout);

        jl_title = new TitleLabel("生成源代码");
        jl_select = new JLabel();
        refreshJLSelect();
        jl_select.setFont(myFont);
        jl_project = new JLabel("项目目录：");
        jl_project.setFont(myFont);
        jtf_project = new JTextField();
        jtf_project.setFont(myFont);
        jtf_project.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        jtf_project.setText(AppContext.save.getProjectPath());
        jb_gen = new JButton("保存全部代码");
        jb_gen.setFont(myFont);
        jb_gen.setEnabled(false);
        jb_back = new JButton("返回");
        jb_back.setFont(myFont);

        jp_title.add(Box.createRigidArea(new Dimension(10,50)));
        jp_title.add(jl_title);
        jp_title.add(Box.createHorizontalStrut(20));
        jp_title.add(jl_select);
        jp_title.add(Box.createHorizontalStrut(20));
        jp_title.add(jl_project);
        jp_title.add(jtf_project);
        jp_title.add(Box.createHorizontalStrut(20));
        jp_title.add(jb_gen);
        jp_title.add(Box.createHorizontalStrut(20));
        jp_title.add(jb_back);
        jp_title.add(Box.createHorizontalStrut(30));

        add(jp_title,BorderLayout.NORTH);
    }

    private void initContent() throws SQLException {
        initLeft();
        initRight();
        jsp_content = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,jsp_left,jp_right);
        jsp_content.setContinuousLayout(true);
        add(jsp_content,BorderLayout.CENTER);
    }

    private void initLeft() throws SQLException {
        jt_list = new JTree(flushDBData());
        jsp_left = new JScrollPane(jt_list);
        jt_list.setFont(myFont);
    }

    private DefaultMutableTreeNode flushDBData() throws SQLException {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(AppContext.dbs.getConnectInfo());
        List<String> dbList = AppContext.dbs.getDBList();
        for (String db : dbList){
            DefaultMutableTreeNode second = new DefaultMutableTreeNode(db);
            List<String> tableList = AppContext.dbs.getTableList(db);
            for (String table : tableList){
                DefaultMutableTreeNode third = new DefaultMutableTreeNode(table);
                second.add(third);
            }
            root.add(second);
        }
        return root;
    }

    private void initRight() {
        jp_right = new JPanel(new BorderLayout());
        jtp_tab = new JTabbedPane();
        jtp_tab.setFont(myFont);
        jp_right.add(jtp_tab);
        tabPanels = new TabJPanel[tabNames.length];
        for (int i = 0 ; i < tabNames.length ; i++){
            String key = tabNames[i];
            TabJPanel tabPanel = new TabJPanel(key);
            tabPanel.getJtf_basePack().setText(AppContext.save.getBasePackage());
            tabPanel.getJtf_childPack().setText(AppContext.save.getChildPackage().get(key));
            if (i >= 5){
                tabPanel.setEnabled(true);
            }
            tabPanels[i] = tabPanel;
            jtp_tab.add(key,tabPanel);
        }
        ((TabJPanel)jtp_tab.getSelectedComponent()).gen();
    }

    private void refreshJLSelect() {
        String showText = (AppContext.dbName == null ? "未选择" : AppContext.dbName) + "/" + (AppContext.tableName ==
                null ? "未选择" : AppContext.tableName);
        jl_select.setText(showText);
        repaint();
    }

    private void initEvent() {
        jt_list.addTreeSelectionListener((event -> {
            TreePath path = event.getPath();
            if (path.getPathCount() == 2){
                AppContext.dbName = path.getLastPathComponent().toString();
                AppContext.tableName = null;
                jb_gen.setEnabled(true);
                for (int i = 0 ; i < tabPanels.length ; i++){
                    if (i <= 5){
                        tabPanels[i].setEnabled(false);
                    }else{
                        tabPanels[i].setEnabled(true);
                    }
                }
            }else if (path.getPathCount() == 3){
                AppContext.dbName = path.getPathComponent(1).toString();
                AppContext.tableName = path.getLastPathComponent().toString();
                jb_gen.setEnabled(true);
                for (int i = 0 ; i < tabPanels.length ; i++){
                    tabPanels[i].setEnabled(true);
                }
                ((TabJPanel)jtp_tab.getSelectedComponent()).gen();
            }else{
                AppContext.dbName = null;
                AppContext.tableName = null;
                jb_gen.setEnabled(false);
                for (int i = 0 ; i < tabPanels.length ; i++){
                    if (i <= 5){
                        tabPanels[i].setEnabled(false);
                    }else{
                        tabPanels[i].setEnabled(true);
                    }
                }
            }
            refreshJLSelect();
        }));
        jb_gen.addActionListener(event -> {
            String projectPath = jtf_project.getText();
            String basePackage = AppContext.save.getBasePackage();
            Map<String, String> childPackage = AppContext.save.getChildPackage();
            Iterator<Map.Entry<String, String>> iterator = childPackage.entrySet().iterator();
                List<String> tableList = null;
                try{
                    tableList = AppContext.dbs.getTableList(AppContext.dbName);
                }catch(SQLException e){
                    AppContext.myDialog.alert(e);
                }
                while (iterator.hasNext()){
                    Map.Entry<String, String> entry = iterator.next();
                    String key = entry.getKey();
                    String value = entry.getValue();
                    String packa = basePackage+"."+value;
                    File dir = AppContext.gs.findDir(projectPath,packa);
                    if(key.equals("Pojo") ||
                            key.equals("XML") ||
                            key.equals("DAO") ||
                            key.equals("Service")||
                            key.equals("Controller")){
                        for(String table:tableList){
                            String fileName = AppContext.gs.getFileName(key,table);
                            String data = AppContext.gs.genData(key,packa,table);
                            try{
                                AppContext.gs.genFile(new File(dir,fileName),data);
                            }catch(IOException e){
                                AppContext.myDialog.alert(e);
                            }
                        }
                    }else{
                        String fileName = AppContext.gs.getFileName(key);
                        String data = AppContext.gs.genData(key,packa,null);
                        try{
                            AppContext.gs.genFile(new File(dir,fileName),data);
                        }catch(IOException e){
                            AppContext.myDialog.alert(e);
                        }
                    }
                }
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "成功", "成功", JOptionPane.QUESTION_MESSAGE);
            AppContext.save.setProjectPath(projectPath);
            AppContext.save.save();
        });
        jb_back.addActionListener(event -> AppContext.indexJFrame.toSetPanel());
        jtp_tab.addChangeListener(event -> ((TabJPanel)jtp_tab.getSelectedComponent()).gen());

        for (TabJPanel tjp : tabPanels){
            tjp.getJtf_basePack().addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    String text = tjp.getJtf_basePack().getText();
                    for (TabJPanel tjp2 : tabPanels){
                        tjp2.getJtf_basePack().setText(text);
                    }
                    AppContext.save.setBasePackage(text);
                    AppContext.save.save();
                    tjp.gen();
                }
            });
            tjp.getJtf_childPack().addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent e) {
                    tjp.gen();
                    String text = tjp.getJtf_childPack().getText();
                    String key = tjp.getKey();
                    AppContext.save.getChildPackage().put(key,text);
                    AppContext.save.save();
                }
            });
        }
        jtf_project.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                AppContext.save.setProjectPath(jtf_project.getText());
            }
        });
    }
}
