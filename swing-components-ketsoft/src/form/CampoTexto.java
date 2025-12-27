/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

import base.BaseTextField;
import javax.swing.JTextField;

/**
 *
 * @author alefi
 */
public class CampoTexto extends CampoForm<String>{

    protected BaseTextField field;

    public CampoTexto() {
        super("KetSoft");
        field = new BaseTextField();
        add(field, java.awt.BorderLayout.CENTER);
    }

    public CampoTexto(String titulo) {
        super(titulo);
        field = new BaseTextField();
        add(field, java.awt.BorderLayout.CENTER);
    }

    @Override
    public String getValue() {
        return field.getText();
    }

    @Override
    public void setValue(String value) {
        field.setText(value);
    }

    @Override
    public boolean isValido() {
        return !getValue().trim().isEmpty();
    }

    public BaseTextField getField() {
        return field;
    }
}
