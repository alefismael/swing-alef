package ui;

import javax.swing.*;
import java.awt.*;

public class LoadingOverlay extends JComponent {
    private final JComponent target;
    private final String message;

    public LoadingOverlay(JComponent target, String message) {
        this.target = target;
        this.message = message != null ? message : "Carregando...";
        setOpaque(false);
    }

    public void showOverlay() {
        RootPaneContainer rpc = (RootPaneContainer) SwingUtilities.getWindowAncestor(target);
        if (rpc == null) return;
        JRootPane root = rpc.getRootPane();
        root.setGlassPane(this);
        setVisible(true);
        repaint();
    }

    public void hideOverlay() {
        setVisible(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.SrcOver.derive(0.4f));
        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(Color.WHITE);
        String msg = message;
        FontMetrics fm = g2.getFontMetrics();
        int w = fm.stringWidth(msg);
        int x = (getWidth() - w) / 2;
        int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
        g2.drawString(msg, x, y);
        g2.dispose();
    }
}
