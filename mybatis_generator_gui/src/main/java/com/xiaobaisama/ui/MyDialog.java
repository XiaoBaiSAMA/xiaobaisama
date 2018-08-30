package com.xiaobaisama.ui;

import com.xiaobaisama.enums.Config;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.io.*;

/**
 * Created by xiaobai on 2018/7/3.
 */
public class MyDialog extends JDialog {
    private JPanel content;
    private JTextArea jta;
    private JScrollPane jsp_center;
    private JPanel jp_bottom;
    private JButton jButton;
    private Font myFont;
    public MyDialog(Frame owner) {
        super(owner);
        setTitle("错误");
        setBounds(owner.getX()+10,
                  owner.getHeight()/4+owner.getY(),
                  owner.getWidth()-20,
                  owner.getHeight()/2);
        try{
            myFont = Font.createFont(Font.TRUETYPE_FONT,new File(Config.MY_FONT_PATH));
        }catch(FontFormatException | IOException e){
            e.printStackTrace();
        }
        content = new JPanel(new BorderLayout(10,10));
        myFont = myFont.deriveFont(Font.PLAIN,12);
        jta = new JTextArea();
        jta.setFont(myFont);
        ((DefaultCaret)jta.getCaret()).setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        jta.setEditable(false);
        jsp_center = new JScrollPane(jta);
        jButton = new JButton("确定");
        jButton.setFont(myFont);
        jButton.addActionListener(event-> setVisible(false));
        jp_bottom = new JPanel();
        jp_bottom.add(jButton);

        content.add(jsp_center,BorderLayout.CENTER);
        content.add(jp_bottom,BorderLayout.SOUTH);
        setContentPane(content);
        setModal(true);
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);
    }
    public void alert(Exception e){
        setBounds(AppContext.indexJFrame.getX()+10,
                  AppContext.indexJFrame.getHeight()/4+AppContext.indexJFrame.getY(),
                  AppContext.indexJFrame.getWidth()-20,
                  AppContext.indexJFrame.getHeight()/2);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        e.printStackTrace();
        jta.setText("\r\n" + sw.toString() + "\r\n");
        setVisible(true);
    }
}
