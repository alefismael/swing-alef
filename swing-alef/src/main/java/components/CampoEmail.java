package components;

import base.BaseTextField;
import java.awt.Color;
import java.util.regex.Pattern;
import javax.swing.BorderFactory;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Campo de e-mail com validação em tempo real.
 * Valida o formato do e-mail e mostra feedback visual.
 * 
 * @author alefi
 */
public class CampoEmail extends CampoForm<String>{

    protected BaseTextField field;
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );

    public CampoEmail() {
        super("E-mail");
        field = new BaseTextField();
        configurarValidacao();
        add(field, java.awt.BorderLayout.CENTER);
    }

    public CampoEmail(String titulo) {
        super(titulo);
        field = new BaseTextField();
        configurarValidacao();
        add(field, java.awt.BorderLayout.CENTER);
    }

    private void configurarValidacao() {
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validarVisualmente();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validarVisualmente();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validarVisualmente();
            }
        });
    }

    private void validarVisualmente() {
        String texto = field.getText().trim();
        if (texto.isEmpty()) {
            field.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        } else if (isEmailValido(texto)) {
            field.setBorder(BorderFactory.createLineBorder(new Color(76, 175, 80), 2));
        } else {
            field.setBorder(BorderFactory.createLineBorder(new Color(244, 67, 54), 2));
        }
    }

    private boolean isEmailValido(String email) {
        return email != null && !email.trim().isEmpty() && EMAIL_PATTERN.matcher(email).matches();
    }

    @Override
    public String getValue() {
        return field.getText().trim();
    }

    @Override
    public void setValue(String value) {
        field.setText(value);
        validarVisualmente();
    }

    @Override
    public boolean isValido() {
        return isEmailValido(getValue());
    }

    public BaseTextField getField() {
        return field;
    }
    
    /**
     * Verifica se o e-mail é válido sem verificar se está vazio
     * @return true se o formato é válido
     */
    public boolean isFormatoValido() {
        String email = getValue();
        return email.isEmpty() || isEmailValido(email);
    }
}
