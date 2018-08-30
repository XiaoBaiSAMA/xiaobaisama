package com.xiaobaisama.ui;

import com.xiaobaisama.enums.Config;
import com.xiaobaisama.enums.TplName;
import com.xiaobaisama.pojo.Save;
import com.xiaobaisama.service.DataBaseService;
import com.xiaobaisama.ui.page.GenJPanel;
import com.xiaobaisama.ui.page.SetJPanel;
import com.xiaobaisama.util.SerializeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaobai on 2018/6/14.
 */
public class IndexJFrame extends JFrame implements Config{
    private JPanel content;

    public IndexJFrame() throws IOException, ClassNotFoundException, SQLException, FontFormatException {
        super(TITLE);
        AppContext.indexJFrame = this;
        loadSaveData();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                AppContext.save.setPositionX((int)getBounds().getX());
                AppContext.save.setPositionY((int)getBounds().getY());
                AppContext.save.setWidth((int)getBounds().getWidth());
                AppContext.save.setHeight((int)getBounds().getHeight());
                AppContext.save.save();
            }
        });
        setBounds(AppContext.save.getPositionX(),AppContext.save.getPositionY(),AppContext.save.getWidth(),AppContext.save.getHeight());
        setMinimumSize(new Dimension(MIN_WIDTH,MIN_HEIGHT));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setContentPane(content = new SetJPanel());
//        AppContext.dbs = new DataBaseService("127.0.0.1",3306,"root","1234");
//        setContentPane(content = new GenJPanel());

        setVisible(true);
    }

    public void toSetPanel() {
        setContentPane(content = new SetJPanel());
        repaint();
        validate();
    }
    public void toGenPanel() throws SQLException, IOException, FontFormatException {
        setContentPane(content = new GenJPanel());
        repaint();
        validate();
    }

    private void loadSaveData() throws IOException, ClassNotFoundException {
        try{
            AppContext.save = SerializeUtil.desSerialize(new File(Config.SAVE_DATA));
        }catch(FileNotFoundException e){
            final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            final int screenWidth = (int)screenSize.getWidth();
            final int screenHeight = (int)screenSize.getHeight();
            Map<String,String> cPack = new HashMap<>();
            cPack.put("Pojo","pojo");
            cPack.put("XML","dao.xml");
            cPack.put("DAO","dao");
            cPack.put("Service","service");
            cPack.put("Controller","controller.api");
            cPack.put("BaseDao","dao");
            cPack.put("BaseService","service");
            cPack.put("Page","dto");
            cPack.put("MyException","dto");
            cPack.put("Code","enums");
            cPack.put("Result","dto");
            AppContext.save = new Save();
            AppContext.save.setWidth(MIN_WIDTH);
            AppContext.save.setHeight(MIN_HEIGHT);
            AppContext.save.setPositionX((screenWidth-MIN_HEIGHT)/2);
            AppContext.save.setPositionY((screenHeight-MIN_HEIGHT)/2);
            AppContext.save.setConnectInfoList(new ArrayList<>());
            AppContext.save.setBasePackage("com.xiaobaisama");
            AppContext.save.setChildPackage(cPack);
            AppContext.save.setProjectPath("c:\\default");
            AppContext.save.save();
        }
    }
}