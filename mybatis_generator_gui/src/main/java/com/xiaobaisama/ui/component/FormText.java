package com.xiaobaisama.ui.component;

import javax.swing.*;
import java.awt.*;

/**
 * Created by xiaobai on 2018/6/27.
 */
public class FormText extends JTextField {
    public FormText() {
        setFont(FormLabel.DEFAULT_FONT);
        setMaximumSize(new Dimension(Integer.MAX_VALUE,30));
    }
}
