/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package base;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JComponent;

/**
 *
 * @author alefi
 */
public class BaseFormPanel extends BasePanel{

    protected GridBagConstraints gbc;

    public BaseFormPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
    }

    protected void addCampo(JComponent campo) {
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, campo.getPreferredSize().height));
        add(campo, gbc);
        gbc.gridy++;
    }
}
