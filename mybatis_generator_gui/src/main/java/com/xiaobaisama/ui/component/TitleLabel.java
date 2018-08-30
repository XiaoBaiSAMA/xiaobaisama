package com.xiaobaisama.ui.component;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xiaobai on 2018/6/27.
 */
public class TitleLabel extends JLabel {
    private static final Font DEFAULT_FONT = new Font("微软雅黑",Font.BOLD,20);

    public TitleLabel(String text) {
        super(text);
        setFont(DEFAULT_FONT);
    }
}
