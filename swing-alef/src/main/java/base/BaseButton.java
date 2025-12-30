package base;

import java.awt.Cursor;
import javax.swing.JButton;

/**
 * Botão base com cursor de mão.
 * As cores e estilos são controlados pelo tema FlatLaf.
 * 
 * @author alefi
 */
public class BaseButton extends JButton {

    public BaseButton() {
        this("Button");
    }

    public BaseButton(String text) {
        super(text);
        init();
    }

    private void init() {
        // Permitir que o look and feel padrão gerencie o visual
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Deixar cores e borda para o tema FlatLaf
        setOpaque(true);
    }
}
