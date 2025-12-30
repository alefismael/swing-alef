/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package base;

import javax.swing.JLabel;
import javax.swing.UIManager;

/**
 * Label base para formulários.
 * As cores são controladas pelo tema FlatLaf.
 * 
 * @author alefi
 */
public class BaseLabel extends JLabel{

    public BaseLabel() {
        this("Label");
    }

    public BaseLabel(String text) {
        super(text);
        init();
    }

    private void init() {
        // Usar cores do tema FlatLaf
        setForeground(UIManager.getColor("Label.foreground"));
        setFont(UIManager.getFont("Label.font"));

        setFocusable(false);
        setOpaque(false);
        setHorizontalAlignment(LEFT);
    }
}
