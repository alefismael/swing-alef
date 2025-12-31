package fields;

import base.BaseTextField;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

/**
 * Campo de entrada para CPF com máscara automática (000.000.000-00) e validação.
 * 
 * @author Alef
 * @version 1.0.0
 */
public class CampoCpf extends CampoForm<String> {
    
    private final BaseTextField textField;
    private boolean required = false;
    private boolean validateCpf = true;
    
    /**
     * Construtor com label padrão "CPF".
     */
    public CampoCpf() {
        this("CPF");
    }
    
    /**
     * Construtor com label customizado.
     * @param labelText Texto do label
     */
    public CampoCpf(String labelText) {
        super(labelText);
        this.textField = new BaseTextField();
        
        textField.setPreferredSize(new Dimension(150, 32));
        add(textField, BorderLayout.CENTER);
        
        setupMask();
    }
    
    private void setupMask() {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
                    throws BadLocationException {
                replace(fb, offset, 0, string, attr);
            }
            
            @Override
            public void remove(FilterBypass fb, int offset, int length) 
                    throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                StringBuilder sb = new StringBuilder(currentText);
                sb.delete(offset, offset + length);
                String newText = formatCpf(sb.toString());
                fb.replace(0, fb.getDocument().getLength(), newText, null);
            }
            
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                    throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                StringBuilder sb = new StringBuilder(currentText);
                sb.replace(offset, offset + length, text);
                String newText = formatCpf(sb.toString());
                fb.replace(0, fb.getDocument().getLength(), newText, attrs);
            }
        });
    }
    
    private String formatCpf(String text) {
        String digits = text.replaceAll("[^0-9]", "");
        
        if (digits.length() > 11) {
            digits = digits.substring(0, 11);
        }
        
        StringBuilder formatted = new StringBuilder();
        for (int i = 0; i < digits.length(); i++) {
            if (i == 3 || i == 6) {
                formatted.append(".");
            } else if (i == 9) {
                formatted.append("-");
            }
            formatted.append(digits.charAt(i));
        }
        
        return formatted.toString();
    }
    
    public CampoCpf setValidateCpf(boolean validate) {
        this.validateCpf = validate;
        return this;
    }
    
    /**
     * Verifica se o CPF é válido usando o algoritmo oficial.
     */
    public static boolean isValidCpf(String cpf) {
        if (cpf == null) return false;
        
        cpf = cpf.replaceAll("[^0-9]", "");
        
        if (cpf.length() != 11) return false;
        if (cpf.matches("(\\d)\\1{10}")) return false;
        
        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
            }
            int resto = soma % 11;
            int digito1 = (resto < 2) ? 0 : 11 - resto;
            
            if (digito1 != Character.getNumericValue(cpf.charAt(9))) return false;
            
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
            }
            resto = soma % 11;
            int digito2 = (resto < 2) ? 0 : 11 - resto;
            
            return digito2 == Character.getNumericValue(cpf.charAt(10));
            
        } catch (Exception e) {
            return false;
        }
    }
    
    public CampoCpf setRequired(boolean required) {
        this.required = required;
        String text = label.getText().replace(" *", "");
        if (required) {
            label.setText(text + " *");
        }
        return this;
    }
    
    public BaseTextField getTextField() {
        return textField;
    }
    
    @Override
    public String getValue() {
        return textField != null ? textField.getText() : "";
    }
    
    public String getUnmaskedValue() {
        return textField != null ? textField.getText().replaceAll("[^0-9]", "") : "";
    }
    
    @Override
    public void setValue(String value) {
        if (value == null) {
            textField.setText("");
        } else {
            textField.setText(formatCpf(value));
        }
    }
    
    public void clear() {
        textField.setText("");
    }
    
    @Override
    public boolean isValid() {
        String cpf = getUnmaskedValue();
        
        if (required && cpf.isEmpty()) {
            return false;
        }
        
        if (!cpf.isEmpty()) {
            if (cpf.length() != 11) {
                return false;
            }
            
            if (validateCpf && !isValidCpf(cpf)) {
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        textField.setEnabled(enabled);
        label.setEnabled(enabled);
    }
    
    public CampoCpf setPreferredWidth(int width) {
        Dimension size = textField.getPreferredSize();
        textField.setPreferredSize(new Dimension(width, size.height));
        return this;
    }
}
