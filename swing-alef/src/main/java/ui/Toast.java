package ui;

import javax.swing.*;
import java.awt.*;

public final class Toast {
    private Toast() {}

    public static void show(Component parent, String message) {
        show(parent, message, 2500);
    }

    public static void show(Component parent, String message, int durationMs) {
        Window window = SwingUtilities.getWindowAncestor(parent);
        if (window == null) return;

        JWindow toast = new JWindow(window);
        JLabel label = new JLabel(message);
        label.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        label.setForeground(Color.WHITE);
        label.setBackground(new Color(0, 0, 0, 200));
        label.setOpaque(true);
        toast.getContentPane().add(label);
        toast.pack();

        Rectangle b = window.getBounds();
        int x = b.x + b.width - toast.getWidth() - 24;
        int y = b.y + b.height - toast.getHeight() - 48;
        toast.setLocation(x, y);

        toast.setAlwaysOnTop(true);
        toast.setVisible(true);
        new Timer(durationMs, e -> {
            toast.setVisible(false);
            toast.dispose();
        }) {{ setRepeats(false); }}.start();
    }
}
