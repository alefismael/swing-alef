package fields;

import base.BaseTextField;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Campo de entrada para valores monetários.
 * Formata automaticamente como R$ 1.234,56.
 * 
 * @author Alef
 * @version 1.0.0
 */
public class CampoMoeda extends CampoForm<BigDecimal> {
    
    private final BaseTextField textField;
    private boolean required = false;
    private BigDecimal minValue = null;
    private BigDecimal maxValue = null;
    private String prefix = "R$ ";
    
    private final DecimalFormat displayFormat;
    private boolean isUpdating = false;
    
    /**
     * Construtor com label.
     * @param labelText Texto do label
     */
    public CampoMoeda(String labelText) {
        super(labelText);
        this.textField = new BaseTextField();
        
        // Configurar formato brasileiro
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(new Locale("pt", "BR"));
        this.displayFormat = new DecimalFormat("#,##0.00", symbols);
        this.displayFormat.setRoundingMode(RoundingMode.HALF_UP);
        
        // Campo de texto
        textField.setHorizontalAlignment(JTextField.RIGHT);
        textField.setPreferredSize(new Dimension(150, 32));
        add(textField, BorderLayout.CENTER);
        
        // Valor inicial
        textField.setText(prefix + displayFormat.format(0));
        
        setupFilter();
    }
    
    private void setupFilter() {
        ((AbstractDocument) textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) 
                    throws BadLocationException {
                replace(fb, offset, 0, string, attr);
            }
            
            @Override
            public void remove(FilterBypass fb, int offset, int length) 
                    throws BadLocationException {
                replace(fb, offset, length, "", null);
            }
            
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) 
                    throws BadLocationException {
                if (isUpdating) {
                    super.replace(fb, offset, length, text, attrs);
                    return;
                }
                
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                StringBuilder sb = new StringBuilder(currentText);
                sb.replace(offset, offset + length, text);
                String newText = sb.toString();
                String digitsOnly = newText.replaceAll("[^0-9]", "");
                
                if (digitsOnly.isEmpty()) {
                    digitsOnly = "0";
                }
                
                try {
                    long cents = Long.parseLong(digitsOnly);
                    BigDecimal value = new BigDecimal(cents).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);
                    String formatted = prefix + displayFormat.format(value);
                    
                    isUpdating = true;
                    fb.replace(0, fb.getDocument().getLength(), formatted, attrs);
                    isUpdating = false;
                } catch (NumberFormatException e) {
                    // Número muito grande, ignora
                }
            }
        });
    }
    
    public CampoMoeda setPrefix(String prefix) {
        this.prefix = prefix;
        setValue(getValue());
        return this;
    }
    
    public CampoMoeda setMinValue(BigDecimal minValue) {
        this.minValue = minValue;
        return this;
    }
    
    public CampoMoeda setMaxValue(BigDecimal maxValue) {
        this.maxValue = maxValue;
        return this;
    }
    
    public CampoMoeda setRange(BigDecimal min, BigDecimal max) {
        this.minValue = min;
        this.maxValue = max;
        return this;
    }
    
    public CampoMoeda setRange(double min, double max) {
        return setRange(BigDecimal.valueOf(min), BigDecimal.valueOf(max));
    }
    
    public CampoMoeda setRequired(boolean required) {
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
    public BigDecimal getValue() {
        if (textField == null) {
            return BigDecimal.ZERO;
        }
        String text = textField.getText()
                .replace(prefix, "")
                .replace(".", "")
                .replace(",", ".")
                .trim();
        
        if (text.isEmpty()) {
            return BigDecimal.ZERO;
        }
        
        try {
            return new BigDecimal(text).setScale(2, RoundingMode.HALF_UP);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }
    
    @Override
    public void setValue(BigDecimal value) {
        if (value == null) {
            value = BigDecimal.ZERO;
        }
        
        isUpdating = true;
        textField.setText(prefix + displayFormat.format(value));
        isUpdating = false;
    }
    
    public void setValue(double value) {
        setValue(BigDecimal.valueOf(value));
    }
    
    public double getDoubleValue() {
        return getValue().doubleValue();
    }
    
    public void clear() {
        setValue(BigDecimal.ZERO);
    }
    
    @Override
    public boolean isValid() {
        BigDecimal value = getValue();
        
        if (required && value.compareTo(BigDecimal.ZERO) == 0) {
            return false;
        }
        
        if (minValue != null && value.compareTo(minValue) < 0) {
            return false;
        }
        
        if (maxValue != null && value.compareTo(maxValue) > 0) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        textField.setEnabled(enabled);
        label.setEnabled(enabled);
    }
    
    public CampoMoeda setPreferredWidth(int width) {
        Dimension size = textField.getPreferredSize();
        textField.setPreferredSize(new Dimension(width, size.height));
        return this;
    }
}
