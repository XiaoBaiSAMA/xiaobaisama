package com.xiaobaisama.pojo;

import com.xiaobaisama.enums.Config;
import com.xiaobaisama.ui.AppContext;
import com.xiaobaisama.util.SerializeUtil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaobai on 2018/6/27.
 */
public class Save implements Serializable {
    private int width;
    private int height;
    private int positionX;
    private int positionY;
    private List<ConnectInfo> connectInfoList;
    private String basePackage;
    private Map<String, String> childPackage;
    private String projectPath;

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public List<ConnectInfo> getConnectInfoList() {
        return connectInfoList;
    }

    public void setConnectInfoList(List<ConnectInfo> connectInfoList) {
        this.connectInfoList = connectInfoList;
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public Map<String, String> getChildPackage() {
        return childPackage;
    }

    public void setChildPackage(Map<String, String> childPackage) {
        this.childPackage = childPackage;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    public void save() {
        try{
            SerializeUtil.serialize(new File(Config.SAVE_DATA),this);
        }catch(IOException e){
            AppContext.myDialog.alert(e);
        }
    }
}
