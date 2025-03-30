package Swing;

import javax.swing.*;
import java.awt.*;

public class RoundedMenuItem extends JMenuItem {
    private int arcWidth = 10;
    private int arcHeight = 10;

    public RoundedMenuItem(String text) {
        super(text);
        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (getModel().isArmed()) {
            g2.setColor(new Color(220, 220, 220)); // Màu khi hover
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        }
        
        super.paintComponent(g);
        g2.dispose();
    }
}