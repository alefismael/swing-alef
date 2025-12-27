package form;

import javax.swing.JPasswordField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alefi
 */
public class CampoSenha extends CampoForm<String>{
private JPasswordField field;

    public CampoSenha() {
        super("KetSoft");
        field = new JPasswordField();
        add(field, java.awt.BorderLayout.CENTER);
    }

    public CampoSenha(String titulo) {
        super(titulo);
        field = new JPasswordField();
        add(field, java.awt.BorderLayout.CENTER);
    }

    @Override
    public String getValue() {
        return new String(field.getPassword());
    }

    @Override
    public void setValue(String value) {
        field.setText(value);
    }

    @Override
    public boolean isValido() {
        return field.getPassword().length > 0;
    }

    public JPasswordField getField() {
        return field;
    }    
}
