package com.xiaobaisama;

import com.xiaobaisama.ui.AppContext;
import com.xiaobaisama.ui.IndexJFrame;
import com.xiaobaisama.ui.MyDialog;
import org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper;

import javax.swing.*;

/**
 * Created by xiaobai on 2018/6/13.
 */
public class Main {
    public static void main(String[] args){
        try{
            System.setProperty("sun.java2d.noddraw", "true");
            BeautyEyeLNFHelper.frameBorderStyle = BeautyEyeLNFHelper.FrameBorderStyle.translucencyAppleLike;
            org.jb2011.lnf.beautyeye.BeautyEyeLNFHelper.launchBeautyEyeLNF();
            UIManager.put("RootPane.setupButtonVisible",false);
        }catch(Exception ignored){}
        try{
            IndexJFrame ijd = new IndexJFrame();
            AppContext.myDialog = new MyDialog(ijd);
        }catch(Exception e){
            AppContext.myDialog.alert(e);
        }
    }
}
