/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package base;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author alefi
 */
public class BaseTextField extends JTextField{

    private static final Color BORDER_COLOR = new Color(153, 153, 153);

    private static final Border NORMAL_BORDER =
            BorderFactory.createLineBorder(BORDER_COLOR, 2);

    public BaseTextField() {
        this(20);
    }

    public BaseTextField(int columns) {
        super(columns);
        init();
    }

    private void init() {
        // força borda fixa (mata o foco ciano do Nimbus)
        setBorder(NORMAL_BORDER);

        // cores neutras
        setBackground(Color.WHITE);
        setForeground(Color.BLACK);

        // seleção e cursor (caret)
        setSelectionColor(new Color(180, 180, 180));
        setSelectedTextColor(Color.BLACK);
        setCaretColor(Color.BLACK);

        // tamanho visual consistente
        setPreferredSize(new Dimension(0, 28));
    }  
}
