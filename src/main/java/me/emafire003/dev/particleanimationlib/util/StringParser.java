package me.emafire003.dev.particleanimationlib.util;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.font.FontRenderContext;

/**
 * Based on answer at StackOverflow
 *
 * @see <a href="http://stackoverflow.com/questions/17282495/java-parsing-truetype-font-to-extract-each-characters-as-image-its-code">...</a>
 * @author Kevin
 *
 */
public final class StringParser {

    private StringParser() {

    }

    public static BufferedImage stringToBufferedImage(Font font, String s) {
        BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR);
        Graphics g = img.getGraphics();
        g.setFont(font);

        FontRenderContext frc = g.getFontMetrics().getFontRenderContext();
        Rectangle2D rect = font.getStringBounds(s, frc);
        g.dispose();

        img = new BufferedImage((int) Math.ceil(rect.getWidth()), (int) Math.ceil(rect.getHeight()), BufferedImage.TYPE_4BYTE_ABGR);
        g = img.getGraphics();
        g.setColor(Color.black);
        g.setFont(font);

        FontMetrics fm = g.getFontMetrics();
        int x = 0;
        int y = fm.getAscent();

        g.drawString(s, x, y);
        g.dispose();

        return img;
    }

}
