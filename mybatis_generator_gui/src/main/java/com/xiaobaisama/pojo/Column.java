package com.xiaobaisama.pojo;

/**
 * Created by xiaobai on 2018/6/26.
 */
public class Column {
    private String db_name;
    private String db_type;
    private boolean pk;

    private String fmt_Name;
    private String fmt_Type;

    private String fmt_name;

    public Column(String db_name,String db_type,boolean pk) {
        this.db_name = db_name;
        this.db_type = db_type;
        this.pk = pk;
        setFmt_Name();
        setFmt_Type();
        setFmt_name();
    }

    private void setFmt_Name() {
        StringBuilder sb = new StringBuilder();
        String[] strArr = db_name.toLowerCase().split("_");
        for (String item : strArr){
            sb.append(item.substring(0,1).toUpperCase());
            sb.append(item.substring(1).toLowerCase());
        }
        this.fmt_Name = sb.toString();
    }

    private void setFmt_Type() {
        switch (db_type.trim().toUpperCase()){
            case "TINYINT":
                fmt_Type = "Byte";
                break;
            case "SMALLINT":
                fmt_Type = "Integer";
                break;
            case "MEDIUMINT":
                fmt_Type = "Integer";
                break;
            case "INT":
                fmt_Type = "Integer";
                break;
            case "INTEGER":
                fmt_Type = "Integer";
                break;
            case "BIGINT":
                fmt_Type = "Long";
                break;
            case "FLOAT":
                fmt_Type = "Float";
                break;
            case "DOUBLE":
                fmt_Type = "Double";
                break;
            case "DECIMAL":
                fmt_Type = "Double";
                break;
            case "DATE":
                fmt_Type = "Date";
                break;
            case "TIME":
                fmt_Type = "Date";
                break;
            case "YEAR":
                fmt_Type = "Date";
                break;
            case "DATETIME":
                fmt_Type = "Date";
                break;
            case "TIMESTAMP":
                fmt_Type = "Date";
                break;
            case "CHAR":
                fmt_Type = "String";
                break;
            case "VARCHAR":
                fmt_Type = "String";
                break;
            case "TINYBLOB":
                fmt_Type = "String";
                break;
            case "TINYTEXT":
                fmt_Type = "String";
                break;
            case "BLOB":
                fmt_Type = "String";
                break;
            case "TEXT":
                fmt_Type = "String";
                break;
            case "MEDIUMBLOB":
                fmt_Type = "String";
                break;
            case "MEDIUMTEXT":
                fmt_Type = "String";
                break;
            case "LONGBLOB":
                fmt_Type = "String";
                break;
            case "LONGTEXT":
                fmt_Type = "String";
                break;
            default:
                fmt_Type = "String";
                break;
        }
    }

    private void setFmt_name() {
        StringBuilder sb = new StringBuilder();
        String[] strArr = db_name.toLowerCase().split("_");
        for (int i = 0 ; i < strArr.length ; i++){
            String item = strArr[i];
            if (i == 0){
                sb.append(item);
            }else{
                sb.append(item.substring(0,1).toUpperCase());
                sb.append(item.substring(1).toLowerCase());
            }
        }
        this.fmt_name = sb.toString();
    }


    public String getDb_name() {
        return db_name;
    }

    public String getDb_type() {
        return db_type;
    }

    public boolean isPk() {
        return pk;
    }

    public String getFmt_Name() {
        return fmt_Name;
    }

    public String getFmt_Type() {
        return fmt_Type;
    }

    public String getFmt_name() {
        return fmt_name;
    }
}
