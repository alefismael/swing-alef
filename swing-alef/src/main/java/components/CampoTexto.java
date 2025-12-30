package components;

import base.BaseTextField;

/**
 * Campo de texto simples para formulários.
 * 
 * @author alefi
 */
public class CampoTexto extends CampoForm<String>{

    protected BaseTextField field;

    public CampoTexto() {
        super("Campo de Texto");
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
