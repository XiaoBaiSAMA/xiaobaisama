package com.xiaobaisama.enums;

/**
 * Created by xiaobai on 2018/6/26.
 */
public interface SQL {

    static final String SHOW_DB = "SHOW DATABASES";

    static final String SHOW_TABLES = "SHOW TABLES";

    static final String SELECT_COLUMN = "SELECT COLUMN_NAME,DATA_TYPE,COLUMN_KEY FROM information_schema.columns WHERE TABLE_SCHEMA = ? AND table_name=?";
}
