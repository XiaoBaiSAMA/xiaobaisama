package com.xiaobaisama.ui.page;

import com.xiaobaisama.service.GenerateService;
import com.xiaobaisama.ui.AppContext;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by xiaobai on 2018/6/28.
 */
public class TabJPanel extends JPanel {
    private String key;
    private JPanel jp_top;
    private JLabel jl_package;
    private JTextField jtf_basePack;
    private JLabel jl_point;
    private JTextField jtf_childPack;
    private JButton jb_save;

    private JScrollPane jsp_center;
    private JTextArea jta_center;

    private JPanel jp_bottom;

    public TabJPanel(String key) {
        this.key = key;
        setLayout(new BorderLayout(2,2));
        initTop();
        initCenter();
        add(new JPanel(new BorderLayout()),BorderLayout.SOUTH);
    }

    private void initTop() {
        jp_top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        add(jp_top,BorderLayout.NORTH);

        jl_package = new JLabel("包名：");
        jtf_basePack = new JTextField();
        jl_point = new JLabel(".");
        jtf_childPack = new JTextField();
        jb_save = new JButton("保存代码");

        jl_package.setFont(GenJPanel.myFont);
        jtf_basePack.setFont(GenJPanel.myFont);
        jl_point.setFont(GenJPanel.myFont);
        jtf_childPack.setFont(GenJPanel.myFont);
        jb_save.setFont(GenJPanel.myFont);

        jtf_basePack.setPreferredSize(new Dimension(200,30));
        jtf_childPack.setPreferredSize(new Dimension(100,30));

        jp_top.add(jl_package);
        jp_top.add(jtf_basePack);
        jp_top.add(jl_point);
        jp_top.add(jtf_childPack);
        jp_top.add(jb_save);

        setEnabled(false);
        jb_save.addActionListener(event -> {
            genFile();
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "成功", "成功", JOptionPane.QUESTION_MESSAGE);
        });
    }

    public void setEnabled(boolean enabled) {
        jb_save.setEnabled(enabled);
    }

    private void initCenter() {
        jta_center = new JTextArea();
        ((DefaultCaret)jta_center.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        jta_center.setFont(GenJPanel.myFont);
        jsp_center = new JScrollPane(jta_center);
        add(jsp_center,BorderLayout.CENTER);
    }

    public void gen() {
        String packa = jtf_basePack.getText() + "." + jtf_childPack.getText();
        String data = AppContext.gs.genData(key,packa,AppContext.tableName);
        jta_center.setText(data);
    }

    public void genFile() {
        GenerateService gs = AppContext.gs;
        String projectPath = AppContext.save.getProjectPath();
        String packa = jtf_basePack.getText() + "." + jtf_childPack.getText();
        File genFile = gs.findDir(projectPath,packa);
        String fileName;
        if (AppContext.tableName != null){
            fileName = gs.getFileName(key,AppContext.tableName);
        }else{
            fileName = gs.getFileName(key);
        }
        genFile = new File(genFile,fileName);
        try{
            gs.genFile(genFile,jta_center.getText());
        }catch(IOException e){
            AppContext.myDialog.alert(e);
        }
    }

    public JTextField getJtf_basePack() {
        return jtf_basePack;
    }

    public JTextField getJtf_childPack() {
        return jtf_childPack;
    }

    public String getKey() {
        return key;
    }
}
