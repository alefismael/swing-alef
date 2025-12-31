/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package base;

import java.awt.Dimension;
import javax.swing.JTextField;

/**
 * Campo de texto base para formulários.
 * As cores e borda são controladas pelo tema FlatLaf.
 * 
 * @author alefi
 */
public class BaseTextField extends JTextField{

    public BaseTextField() {
        this(20);
    }

    public BaseTextField(int columns) {
        super(columns);
        init();
    }

    private void init() {
        // Deixar cores e borda para o tema FlatLaf
        setOpaque(true);
        
        // Tamanho visual consistente
        setPreferredSize(new Dimension(300, 28)); // Corrige largura para 300
    }  
}
