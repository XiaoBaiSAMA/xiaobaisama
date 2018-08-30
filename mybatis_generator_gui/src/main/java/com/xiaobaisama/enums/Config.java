package com.xiaobaisama.enums;


/**
 * Created by xiaobai on 2018/6/14.
 */
public interface Config {
    static final String DRIVER = "com.mysql.jdbc.Driver";
    static final String URL = "jdbc:mysql://{0}:{1}";
    static final String URL_PARAM = "?useUnicode=true&characterEncoding=utf-8&useSSL=true";

    static final String TITLE = "MyBatis反向工程 v2.0 by 小白";
    static final int MIN_WIDTH = 800;
    static final int MIN_HEIGHT = 600;
    static final String SAVE_DATA = "save.data";

    static final String MY_FONT_PATH = "YaHei.Consolas.1.11b.ttf";
}