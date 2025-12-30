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
import javax.swing.UIManager;

/**
 * Painel base para layout com GridBag. As cores são controladas pelo tema
 * FlatLaf.
 *
 * @author alefi
 */
public class BasePanel extends JPanel {

    protected JPanel content;

    public BasePanel() {
        // Deixar cores para o tema FlatLaf
        setBackground(UIManager.getColor("Panel.background"));
        setLayout(new GridBagLayout());

        content = new JPanel();
        content.setBackground(UIManager.getColor("Panel.background"));
        content.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        // Padding compacto
        gbc.insets = new Insets(12, 16, 12, 16);

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
