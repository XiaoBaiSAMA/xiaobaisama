package com.xiaobaisama.pojo;

import java.io.Serializable;

/**
 * Created by xiaobai on 2018/6/27.
 */
public class ConnectInfo implements Serializable {
    private String ip;
    private String port;
    private String user;
    private String password;

    public ConnectInfo() {
    }

    public ConnectInfo(String ip,String port,String user,String password) {
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return ip+":"+port+"["+user+"]";
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof ConnectInfo)) return false;
        ConnectInfo param = (ConnectInfo)obj;
        return param.ip.equals(ip) && param.port.equals(port) && param.user.equals(user);
    }
}
