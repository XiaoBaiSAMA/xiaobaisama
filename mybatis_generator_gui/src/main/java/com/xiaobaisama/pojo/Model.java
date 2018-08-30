package com.xiaobaisama.pojo;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xiaobai on 2018/6/29.
 */
public class Model {
    private String packa;

    private String table;

    private String clazz;

    private String obj;

    private Set<String> importSet;

    private List<Column> columnList;

    private String createDate;

    private Column pk;

    private String ps;

    public String getPacka() {
        return packa;
    }

    public void setPacka(String packa) {
        this.packa = packa;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
        setClazz();
        setObj();
    }

    public String getClazz() {
        return clazz;
    }

    private void setClazz() {
        String[] strArr = table.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for(String item:strArr){
            sb.append(item.substring(0,1).toUpperCase());
            sb.append(item.substring(1));
        }
        this.clazz = sb.toString();
    }

    public String getObj() {
        return obj;
    }

    private void setObj() {
        String[] strArr = table.toLowerCase().split("_");
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<strArr.length;i++){
            String item = strArr[i];
            if(i==0){
                sb.append(item);
            }else{
                sb.append(item.substring(0,1).toUpperCase());
                sb.append(item.substring(1));
            }
        }
        this.obj = sb.toString();
    }

    public Set<String> getImportSet() {
        return importSet;
    }

    private void setImportSet() {
        importSet = new HashSet<>();
        for(Column c : columnList){
            if(c.getFmt_Type().equals("Date")){
                importSet.add("java.util.Date");
            }
        }
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
        setImportSet();
        setPk();
    }

    public String getCreateDate() {
        return new SimpleDateFormat("yyyy/MM/dd").format(new Date());
    }

    public void addImport(String imp){
        if(importSet == null){
            importSet = new HashSet<>();
        }
        importSet.add(imp);
    }

    public Column getPk() {
        return pk;
    }

    private void setPk() {
        for(Column c:columnList){
            if(c.isPk()){
                this.pk = c;
                break;
            }
        }
    }

    public String getPs() {
        return ps;
    }

    public void setPs(String ps) {
        this.ps = ps;
    }
}
