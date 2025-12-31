package fields;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Componente de grupo de RadioButtons com label integrado.
 * Permite selecionar uma opção de um grupo.
 * 
 * @param <T> Tipo do valor associado a cada opção
 * 
 * @author Alef
 * @version 1.0.0
 */
public class CampoRadioGroup<T> extends CampoForm<T> {
    
    private final JPanel optionsPanel;
    private final ButtonGroup buttonGroup;
    private final Map<JRadioButton, T> radioValueMap;
    private final List<JRadioButton> radioButtons;
    private boolean required = false;
    private boolean horizontal = true;
    private Runnable changeListener;
    
    /**
     * Construtor com label.
     * @param labelText Texto do label do grupo
     */
    public CampoRadioGroup(String labelText) {
        super(labelText);
        this.optionsPanel = new JPanel();
        this.buttonGroup = new ButtonGroup();
        this.radioValueMap = new LinkedHashMap<>();
        this.radioButtons = new ArrayList<>();
        
        optionsPanel.setOpaque(false);
        optionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 2));
        add(optionsPanel, BorderLayout.CENTER);
    }
    
    /**
     * Define layout horizontal (lado a lado) ou vertical (empilhado).
     */
    public CampoRadioGroup<T> setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
        if (horizontal) {
            optionsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 2));
        } else {
            optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        }
        return this;
    }
    
    /**
     * Adiciona uma opção ao grupo.
     */
    public CampoRadioGroup<T> addOption(String text, T value) {
        JRadioButton radio = new JRadioButton(text);
        radio.setOpaque(false);
        radio.setFont(radio.getFont().deriveFont(Font.PLAIN, 13f));
        radio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        radio.setFocusPainted(false);
        
        radio.addActionListener(e -> {
            if (changeListener != null) {
                changeListener.run();
            }
        });
        
        buttonGroup.add(radio);
        radioValueMap.put(radio, value);
        radioButtons.add(radio);
        optionsPanel.add(radio);
        
        return this;
    }
    
    /**
     * Adiciona várias opções de uma vez.
     */
    public CampoRadioGroup<T> addOptions(Map<String, T> options) {
        for (Map.Entry<String, T> entry : options.entrySet()) {
            addOption(entry.getKey(), entry.getValue());
        }
        return this;
    }
    
    /**
     * Remove todas as opções.
     */
    public CampoRadioGroup<T> clearOptions() {
        for (JRadioButton radio : radioButtons) {
            buttonGroup.remove(radio);
        }
        radioButtons.clear();
        radioValueMap.clear();
        optionsPanel.removeAll();
        optionsPanel.revalidate();
        optionsPanel.repaint();
        return this;
    }
    
    /**
     * Seleciona uma opção pelo valor.
     */
    public CampoRadioGroup<T> selectValue(T value) {
        for (Map.Entry<JRadioButton, T> entry : radioValueMap.entrySet()) {
            if (entry.getValue().equals(value)) {
                entry.getKey().setSelected(true);
                break;
            }
        }
        return this;
    }
    
    /**
     * Seleciona uma opção pelo índice.
     */
    public CampoRadioGroup<T> selectIndex(int index) {
        if (index >= 0 && index < radioButtons.size()) {
            radioButtons.get(index).setSelected(true);
        }
        return this;
    }
    
    public int getSelectedIndex() {
        for (int i = 0; i < radioButtons.size(); i++) {
            if (radioButtons.get(i).isSelected()) {
                return i;
            }
        }
        return -1;
    }
    
    public CampoRadioGroup<T> addChangeListener(Runnable listener) {
        this.changeListener = listener;
        return this;
    }
    
    public int getOptionCount() {
        return radioButtons.size();
    }
    
    public CampoRadioGroup<T> setRequired(boolean required) {
        this.required = required;
        String text = label.getText().replace(" *", "");
        if (required) {
            label.setText(text + " *");
        }
        return this;
    }
    
    @Override
    public T getValue() {
        for (Map.Entry<JRadioButton, T> entry : radioValueMap.entrySet()) {
            if (entry.getKey().isSelected()) {
                return entry.getValue();
            }
        }
        return null;
    }
    
    @Override
    public void setValue(T value) {
        if (value == null) {
            buttonGroup.clearSelection();
            return;
        }
        selectValue(value);
    }
    
    public void clear() {
        buttonGroup.clearSelection();
    }
    
    @Override
    public boolean isValid() {
        if (required) {
            return getValue() != null;
        }
        return true;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        label.setEnabled(enabled);
        for (JRadioButton radio : radioButtons) {
            radio.setEnabled(enabled);
        }
    }
}
