package button;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import theme.ThemeColors;

public class BaseButton extends JButton {

    public BaseButton() {
        this("Button");
    }

    public BaseButton(String text) {
        super(text);
        init();
    }

    private void init() {
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFont(new Font("Segoe UI", Font.PLAIN, 13));

        // Estilo flat com borda arredondada
        setBackground(Color.WHITE);
        setForeground(ThemeColors.PRIMARY);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ThemeColors.PRIMARY, 1, true),
                BorderFactory.createEmptyBorder(6, 16, 6, 16)
        ));

        // Efeito hover sutil
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(new Color(245, 245, 245));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(Color.WHITE);
            }
        });
    }
}
