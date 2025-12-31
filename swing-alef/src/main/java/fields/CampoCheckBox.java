package fields;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Componente CheckBox estilizado com label integrado.
 * 
 * @author Alef
 * @version 1.0.0
 */
public class CampoCheckBox extends CampoForm<Boolean> {
    
    private final JCheckBox checkBox;
    private boolean required = false;
    
    /**
     * Construtor com texto do checkbox.
     * @param text Texto exibido ao lado do checkbox
     */
    public CampoCheckBox(String text) {
        this(text, false);
    }
    
    /**
     * Construtor com texto e valor inicial.
     * @param text Texto exibido ao lado do checkbox
     * @param selected Valor inicial
     */
    public CampoCheckBox(String text, boolean selected) {
        super(""); // Não usa label padrão
        remove(label); // Remove o label padrão
        
        this.checkBox = new JCheckBox(text, selected);
        checkBox.setOpaque(false);
        checkBox.setFont(checkBox.getFont().deriveFont(Font.PLAIN, 13f));
        checkBox.setFocusPainted(false);
        checkBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        add(checkBox, BorderLayout.WEST);
    }
    
    public CampoCheckBox setSelected(boolean selected) {
        checkBox.setSelected(selected);
        return this;
    }
    
    public boolean isSelected() {
        return checkBox.isSelected();
    }
    
    public CampoCheckBox toggle() {
        checkBox.setSelected(!checkBox.isSelected());
        return this;
    }
    
    public CampoCheckBox setText(String text) {
        checkBox.setText(text);
        return this;
    }
    
    public String getText() {
        return checkBox.getText();
    }
    
    public CampoCheckBox addChangeListener(Runnable listener) {
        checkBox.addActionListener(e -> listener.run());
        return this;
    }
    
    public CampoCheckBox addActionListener(ActionListener listener) {
        checkBox.addActionListener(listener);
        return this;
    }
    
    public JCheckBox getCheckBox() {
        return checkBox;
    }
    
    public CampoCheckBox setRequired(boolean required) {
        this.required = required;
        return this;
    }
    
    @Override
    public Boolean getValue() {
        return checkBox != null && checkBox.isSelected();
    }
    
    @Override
    public void setValue(Boolean value) {
        checkBox.setSelected(value != null && value);
    }
    
    public void clear() {
        checkBox.setSelected(false);
    }
    
    @Override
    public boolean isValid() {
        // Para checkbox, "válido" quando obrigatório significa que deve estar marcado
        if (required && checkBox != null) {
            return checkBox.isSelected();
        }
        return true;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        checkBox.setEnabled(enabled);
    }
}
