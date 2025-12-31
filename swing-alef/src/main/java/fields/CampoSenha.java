package fields;

import javax.swing.JPasswordField;

/**
 * Campo de senha com máscara de caracteres.
 * 
 * @author alefi
 */
public class CampoSenha extends CampoForm<String>{
    private JPasswordField field;

    public CampoSenha() {
        super("Senha");
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
        return field != null ? new String(field.getPassword()) : "";
    }

    @Override
    public void setValue(String value) {
        field.setText(value);
    }

    @Override
    public boolean isValid() {
        return field != null && field.getPassword().length > 0;
    }

    public JPasswordField getField() {
        return field;
    }
    
    /**
     * Valida se a senha atende requisitos mínimos de segurança
     * @param minLength tamanho mínimo
     * @return true se válida
     */
    public boolean isSegura(int minLength) {
        return field.getPassword().length >= minLength;
    }
}
