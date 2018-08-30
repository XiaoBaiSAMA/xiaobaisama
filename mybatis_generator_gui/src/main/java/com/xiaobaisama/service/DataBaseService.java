package com.xiaobaisama.service;

import com.xiaobaisama.enums.Config;
import com.xiaobaisama.enums.SQL;
import com.xiaobaisama.pojo.Column;
import com.xiaobaisama.pojo.ConnectInfo;

import java.sql.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaobai on 2018/6/26.
 */
public class DataBaseService implements Config, SQL {
    private String url;
    private String user;
    private String password;

    private ConnectInfo connectInfo;

    public DataBaseService(String ip,int port,String user,String password) throws ClassNotFoundException {
        this.url = MessageFormat.format(URL,ip,String.valueOf(port));
        this.user = user;
        this.password = password;
        this.connectInfo = new ConnectInfo(ip,String.valueOf(port),user,password);
        Class.forName(DRIVER);
    }

    private void close(Connection conn,Statement stat,ResultSet rs) {
        if (rs != null){
            try{
                rs.close();
            }catch(SQLException ignored){}
        }
        if (stat != null){
            try{
                stat.close();
            }catch(SQLException ignored){}
        }
        if (conn != null){
            try{
                conn.close();
            }catch(SQLException ignored){}
        }
    }

    public void testConn() throws SQLException {
        DriverManager.getConnection(url + URL_PARAM,user,password);
    }

    public List<String> getDBList() throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> result = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(url + URL_PARAM,user,password);
            ps = conn.prepareStatement(SHOW_DB);
            rs = ps.executeQuery();
            while (rs.next()){
                String dbName = rs.getString(1);
                result.add(dbName);
            }
        }finally{
            close(conn,ps,rs);
        }
        return result;
    }

    public List<String> getTableList(String dbName) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> result = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(url + "/" + dbName + URL_PARAM,user,password);
            ps = conn.prepareStatement(SHOW_TABLES);
            rs = ps.executeQuery();
            while (rs.next()){
                String tableName = rs.getString(1);
                result.add(tableName);
            }
        }finally{
            close(conn,ps,rs);
        }
        return result;
    }

    public List<Column> getColumnList(String dbName,String tableName) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Column> result = new ArrayList<>();
        try{
            conn = DriverManager.getConnection(url + "/" + dbName + URL_PARAM,user,password);
            ps = conn.prepareStatement(SELECT_COLUMN);
            ps.setString(1,dbName);
            ps.setString(2,tableName);
            rs = ps.executeQuery();
            while (rs.next()){
                String colName = rs.getString("COLUMN_NAME");
                String colType = rs.getString("DATA_TYPE");
                String key = rs.getString("COLUMN_KEY");
                result.add(new Column(colName,colType,!key.equals("")));
            }
        }finally{
            close(conn,ps,rs);
        }
        return result;
    }

    public ConnectInfo getConnectInfo() {
        return connectInfo;
    }
}
