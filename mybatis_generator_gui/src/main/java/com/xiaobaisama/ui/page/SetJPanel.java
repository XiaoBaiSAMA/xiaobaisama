package com.xiaobaisama.ui.page;

import com.xiaobaisama.pojo.ConnectInfo;
import com.xiaobaisama.service.DataBaseService;
import com.xiaobaisama.ui.AppContext;
import com.xiaobaisama.ui.component.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by xiaobai on 2018/6/27.
 */
public class SetJPanel extends JPanel {
    private JPanel jp_title;
    private JPanel jp_content;

    private JLabel jl_title;

    private JLabel jl_save;
    private JLabel jl_ip;
    private JLabel jl_port;
    private JLabel jl_user;
    private JLabel jl_pwd;

    private JComboBox<ConnectInfo>  jcb_save;
    private JTextField jtf_ip;
    private JTextField jtf_port;
    private JTextField jtf_user;
    private JTextField jtf_pwd;

    private JButton jb_submit;
    private JButton jb_reset;

    private List<ConnectInfo> connList;


    public SetJPanel() {
        setLayout(new BorderLayout());
        initTitle();
        initForm();
        initEvent();
    }

    private void initTitle(){
        jp_title = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
        add(jp_title,BorderLayout.NORTH);
        jl_title = new TitleLabel("连接数据库");
        jp_title.add(jl_title);
    }

    private void initForm(){
        jp_content = new JPanel(new BorderLayout());
        add(jp_content,BorderLayout.CENTER);

        jl_save = new FormLabel("保存的连接：");
        jl_ip = new FormLabel("地址：");
        jl_port = new FormLabel("端口：");
        jl_user = new FormLabel("用户名：");
        jl_pwd = new FormLabel("密码：");

        jcb_save = new FormSelect<>();
        jtf_ip = new FormText();
        jtf_port = new FormText();
        jtf_user = new FormText();
        jtf_pwd = new FormText();
        jb_submit = new FormButton("连     接");
        jb_reset = new FormButton("重     置");

        connList = AppContext.save.getConnectInfoList();
        for(ConnectInfo ci : connList){
            jcb_save.addItem(ci);
        }
        flushForm(jcb_save.getSelectedIndex());

        Box hBox_save = Box.createHorizontalBox();
        hBox_save.add(Box.createHorizontalStrut(80));
        hBox_save.add(jl_save);
        hBox_save.add(Box.createHorizontalStrut(20));
        hBox_save.add(jcb_save);
        hBox_save.add(Box.createHorizontalStrut(80));


        Box hBox_ip = Box.createHorizontalBox();
        hBox_ip.add(Box.createHorizontalStrut(80));
        hBox_ip.add(jl_ip);
        hBox_ip.add(Box.createHorizontalStrut(20));
        hBox_ip.add(jtf_ip);
        hBox_ip.add(Box.createHorizontalStrut(80));

        Box hBox_port = Box.createHorizontalBox();
        hBox_port.add(Box.createHorizontalStrut(80));
        hBox_port.add(jl_port);
        hBox_port.add(Box.createHorizontalStrut(20));
        hBox_port.add(jtf_port);
        hBox_port.add(Box.createHorizontalStrut(80));

        Box hBox_user = Box.createHorizontalBox();
        hBox_user.add(Box.createHorizontalStrut(80));
        hBox_user.add(jl_user);
        hBox_user.add(Box.createHorizontalStrut(20));
        hBox_user.add(jtf_user);
        hBox_user.add(Box.createHorizontalStrut(80));

        Box hBox_pwd = Box.createHorizontalBox();
        hBox_pwd.add(Box.createHorizontalStrut(80));
        hBox_pwd.add(jl_pwd);
        hBox_pwd.add(Box.createHorizontalStrut(20));
        hBox_pwd.add(jtf_pwd);
        hBox_pwd.add(Box.createHorizontalStrut(80));

        Box hBox_btn = Box.createHorizontalBox();
        hBox_btn.setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
        JPanel jp_btn_reset = new JPanel(new BorderLayout());
        jp_btn_reset.add(jb_reset);
        JPanel jp_btn_submit = new JPanel(new BorderLayout());
        jp_btn_submit.add(jb_submit);

        hBox_btn.add(Box.createHorizontalStrut(200));
        hBox_btn.add(jp_btn_reset);
        hBox_btn.add(Box.createHorizontalStrut(20));
        hBox_btn.add(jp_btn_submit);
        hBox_btn.add(Box.createHorizontalStrut(80));


        Box vBox = Box.createVerticalBox();
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(hBox_save);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(hBox_ip);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(hBox_port);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(hBox_user);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(hBox_pwd);
        vBox.add(Box.createVerticalStrut(30));
        vBox.add(hBox_btn);
        vBox.add(Box.createVerticalStrut(60));
        jp_content.add(vBox);
    }

    private void flushForm(int index){
        if(index != -1){
            ConnectInfo info = connList.get(index);
            jtf_ip.setText(info.getIp());
            jtf_port.setText(info.getPort());
            jtf_user.setText(info.getUser());
            jtf_pwd.setText(info.getPassword());
        }else{
            jtf_ip.setText("");
            jtf_port.setText("");
            jtf_user.setText("");
            jtf_pwd.setText("");
        }
        repaint();
    }

    private void initEvent(){
        jb_reset.addActionListener((event)->{
            jcb_save.setSelectedIndex(-1);
            flushForm(jcb_save.getSelectedIndex());
        });

        jb_submit.addActionListener((event)->{
            String ip = jtf_ip.getText();
            String port = jtf_port.getText();
            String user = jtf_user.getText();
            String pwd = jtf_pwd.getText();
            try{
                AppContext.dbs = new DataBaseService(ip,Integer.parseInt(port),user,pwd);
                AppContext.dbs.testConn();
                AppContext.indexJFrame.toGenPanel();
                saveInfo(ip,port,user,pwd);
            }catch(Exception e){
                AppContext.myDialog.alert(e);
            }
        });
        jcb_save.addItemListener((event)-> flushForm(jcb_save.getSelectedIndex()));
    }

    private void saveInfo(String ip,String port,String user,String pwd) throws IOException {
        ConnectInfo info = new ConnectInfo(ip,port,user,pwd);
        List<ConnectInfo> infoList = AppContext.save.getConnectInfoList();
        boolean find = false;
        for(int i=0;i<infoList.size();i++){
            ConnectInfo item = infoList.get(i);
            if(item.equals(info)){
                find = true;
                infoList.set(i,info);
                break;
            }
        }
        if(!find) infoList.add(info);
        AppContext.save.save();
    }
}
