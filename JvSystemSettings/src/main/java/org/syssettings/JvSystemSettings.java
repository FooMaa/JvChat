package org.syssettings;

import java.awt.*;

/**
 * Hello world!
 *
 */
public class JvSystemSettings
{
    private static final Dimension screenSize;
    public static int heightScreen;
    public static int widthScreen;

    static {
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        heightScreen = screenSize.height;
        widthScreen = screenSize.width;
    }
}
