/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package base;

import java.awt.Cursor;
import java.awt.Dimension;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Spinner base para números.
 * As cores e estilos são controlados pelo tema FlatLaf.
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
        // Deixar estilos para o tema FlatLaf
        setOpaque(true);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        ajustarTamanho();
    }

    private void ajustarTamanho() {
        setPreferredSize(new Dimension(200, 28));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
    }
}
