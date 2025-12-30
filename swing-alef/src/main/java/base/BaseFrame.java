package base;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.KeyStroke;
import javax.swing.JComponent;
import javax.swing.AbstractAction;

/**
 * Frame base para aplicações Swing.
 * Fornece funcionalidades comuns como suporte a F11 para tela cheia.
 * 
 * @author alefi
 */
public class BaseFrame extends JFrame {
    
    private boolean fullScreen = false;
    
    public BaseFrame(String titulo) {
        super(titulo);
        configurar();
        configurarAtalhoTelaCheia();
    }
    
    private void configurar() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    private void configurarAtalhoTelaCheia() {
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "toggleFullScreen");
        
        getRootPane().getActionMap().put("toggleFullScreen", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alternarTelaCheia();
            }
        });
    }
    
    /**
     * Alterna entre modo tela cheia e modo janela normal.
     */
    public void alternarTelaCheia() {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
        if (!device.isFullScreenSupported()) {
            System.err.println("Tela cheia não suportada neste sistema");
            return;
        }
        
        if (fullScreen) {
            device.setFullScreenWindow(null);
            dispose();
            setUndecorated(false);
            setVisible(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            fullScreen = false;
        } else {
            dispose();
            setUndecorated(true);
            device.setFullScreenWindow(this);
            setVisible(true);
            fullScreen = true;
        }
    }
}
