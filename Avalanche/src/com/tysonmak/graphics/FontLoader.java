package com.tysonmak.graphics;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class FontLoader {

    public Font load(String path, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(path)).deriveFont(Font.PLAIN, size);
        } catch(FontFormatException e) {
            e.printStackTrace();
            System.exit(1);
        } catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    public static void drawString(Graphics2D g, String text, int xPos, int yPos, boolean center, Color color, Font font) {
        g.setColor(color);
        g.setFont(font);
        int x = xPos;
        int y = yPos;
        if(center) {
            FontMetrics fm = g.getFontMetrics(font);
            x = xPos - fm.stringWidth(text) / 2;
            y = (yPos - fm.getHeight() / 2) + fm.getAscent();
        }
        g.drawString(text, x, y);
    }

}
