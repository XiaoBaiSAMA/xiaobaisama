package com.xiaobaisama.ui.component;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xiaobai on 2018/6/27.
 */
public class FormLabel extends JLabel {
    public static final Font DEFAULT_FONT = new Font("微软雅黑",Font.PLAIN,14);

    public FormLabel(String text) {
        super(text);
        setFont(DEFAULT_FONT);
        setPreferredSize(new Dimension(100,30));
    }
}
