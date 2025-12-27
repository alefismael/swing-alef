/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package base;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

/**
 *
 * @author alefi
 */
public class BaseLabel extends JLabel{

    // Cinza bem escuro (quase preto)
    private static final Color LABEL_COLOR = new Color(45, 45, 45);

    // "Semi-bold" visual: bold com tamanho levemente menor
    private static final Font LABEL_FONT =
            new Font("Segoe UI", Font.BOLD, 12);

    public BaseLabel() {
        this("Label");
    }

    public BaseLabel(String text) {
        super(text);
        init();
    }

    private void init() {
        setForeground(LABEL_COLOR);
        setFont(LABEL_FONT);

        setFocusable(false);
        setOpaque(false);
        setHorizontalAlignment(LEFT);
    }
}
