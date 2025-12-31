package fields;

import base.BaseSpinner;
import javax.swing.SpinnerNumberModel;

/**
 * Campo numérico com controles de incremento/decremento.
 * 
 * @author alefi
 */
public class CampoNumeroSpinner extends CampoForm<Integer>{
    
    private BaseSpinner spinner;

    public CampoNumeroSpinner() {
        super("Número");
        spinner = new BaseSpinner(new SpinnerNumberModel(0, 0, 999999, 1));
        add(spinner, java.awt.BorderLayout.CENTER);
    }
    
    public CampoNumeroSpinner(String titulo) {
        super(titulo);
        spinner = new BaseSpinner(new SpinnerNumberModel(0, 0, 999999, 1));
        add(spinner, java.awt.BorderLayout.CENTER);
    }
    
    public CampoNumeroSpinner(String titulo, int valorInicial, int minimo, int maximo, int passo) {
        super(titulo);
        spinner = new BaseSpinner(new SpinnerNumberModel(valorInicial, minimo, maximo, passo));
        add(spinner, java.awt.BorderLayout.CENTER);
    }

    @Override
    public Integer getValue() {
        return spinner != null ? (Integer) spinner.getValue() : null;
    }

    @Override
    public void setValue(Integer value) {
        spinner.setValue(value);
    }

    @Override
    public boolean isValid() {
        return getValue() != null;
    }
    
    public BaseSpinner getSpinner() {
        return spinner;
    }
}
