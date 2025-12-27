/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package base;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIDefaults;
import javax.swing.plaf.basic.BasicSpinnerUI;

/**
 *
 * @author alefi
 */
public class BaseSpinner extends JSpinner{

    public BaseSpinner() {
        this(new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
    }

    public BaseSpinner(int value, int min, int max, int step) {
        this(new SpinnerNumberModel(value, min, max, step));
    }

    public BaseSpinner(SpinnerNumberModel model) {
        super(model);
        configurar();
    }

    private void configurar() {
        // Instala a UI passando explicitamente o spinner
        setUI(new SpinnerUIFlat(this));

        setBorder(BorderFactory.createLineBorder(new Color(153, 153, 153), 2));
        setBackground(Color.WHITE);
        setOpaque(true);

        ajustarEditor();
        ajustarTamanho();
    }

    private void ajustarEditor() {
        JComponent editor = getEditor();

        if (editor instanceof DefaultEditor) {
            JTextField tf = ((DefaultEditor) editor).getTextField();

            tf.setBorder(null);
            tf.setBackground(Color.WHITE);
            tf.setCaretColor(Color.BLACK);
            tf.setSelectionColor(new Color(180, 180, 180));

            tf.setFocusable(true);

            UIDefaults overrides = new UIDefaults();
            overrides.put("TextField.focusPainter", null);
            overrides.put("TextField.focusBackground", Color.WHITE);
            overrides.put("TextField[Enabled].backgroundPainter", null);

            tf.putClientProperty("Nimbus.Overrides", overrides);
            tf.putClientProperty("Nimbus.Overrides.InheritDefaults", false);
        }
    }

    private void ajustarTamanho() {
        setPreferredSize(new Dimension(200, 28));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
    }

    // =========================
    // UI CUSTOM (FLAT)
    // =========================
    private static class SpinnerUIFlat extends BasicSpinnerUI {

        private final JSpinner spinner; // referência direta ao spinner

        public SpinnerUIFlat(JSpinner spinner) {
            this.spinner = spinner;
        }

        @Override
        protected Component createNextButton() {
            return criarBotao("▲");
        }

        @Override
        protected Component createPreviousButton() {
            return criarBotao("▼");
        }

        private JButton criarBotao(String texto) {
            JButton botao = new JButton(texto);

            botao.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            botao.setForeground(new Color(90, 90, 90));
            botao.setBackground(Color.WHITE);

            botao.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(153, 153, 153)));

            botao.setFocusPainted(false);
            botao.setContentAreaFilled(false);
            botao.setOpaque(true);
            botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            // Usando referência direta do spinner
            botao.addActionListener(e -> {
                if ("▲".equals(texto)) {
                    Object next = spinner.getNextValue();
                    if (next != null) spinner.setValue(next);
                } else {
                    Object prev = spinner.getPreviousValue();
                    if (prev != null) spinner.setValue(prev);
                }
            });

            return botao;
        }
    }  
}
