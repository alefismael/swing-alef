/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package base;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;
import theme.ThemeColors;

/**
 *
 * @author alefi
 */
public class BasePanel extends JPanel{
    
    protected JPanel content;

    public BasePanel() {
        setBackground(ThemeColors.BACKGROUND);
        setLayout(new GridBagLayout());

        content = new JPanel();
        content.setBackground(ThemeColors.SURFACE);
        content.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(24, 24, 24, 24);

        add(content, gbc);
    }

    protected JPanel getContent() {
        return content;
    }
    
@Override
public Dimension getPreferredSize() {
    if (java.beans.Beans.isDesignTime()) {
        return new Dimension(1249, 706);
    }
    return super.getPreferredSize();
    }
}
