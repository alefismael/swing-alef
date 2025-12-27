package form;


import base.BaseSpinner;
import javax.swing.SpinnerNumberModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alefi
 */
public class CampoNumeroSpinner extends CampoForm<Integer>{
    
    private BaseSpinner spinner;

    public CampoNumeroSpinner() {
        super("KetSoft");
        spinner = new BaseSpinner(new SpinnerNumberModel(0, 0, 999999, 1));
        add(spinner, java.awt.BorderLayout.CENTER);
    }
    
    public CampoNumeroSpinner(String titulo) {
        super(titulo);
        spinner = new BaseSpinner(new SpinnerNumberModel(0, 0, 999999, 1));
        add(spinner, java.awt.BorderLayout.CENTER);
    }

    @Override
    public Integer getValue() {
        return (Integer) spinner.getValue();
    }

    @Override
    public void setValue(Integer value) {
        spinner.setValue(value);
    }

    @Override
    public boolean isValido() {
        return getValue() != null;
    }
}
