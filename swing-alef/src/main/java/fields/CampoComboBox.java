package fields;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

/**
 * Componente ComboBox com label integrado.
 * Permite exibir objetos com texto customizado através de um renderer.
 * 
 * @param <T> Tipo dos itens do ComboBox
 * 
 * @author Alef
 * @version 1.0.0
 */
public class CampoComboBox<T> extends CampoForm<T> {
    
    private final JComboBox<T> comboBox;
    private final DefaultComboBoxModel<T> model;
    private Function<T, String> displayFunction;
    private boolean required = false;
    
    /**
     * Construtor com label.
     * @param labelText Texto do label
     */
    public CampoComboBox(String labelText) {
        this(labelText, null);
    }
    
    /**
     * Construtor com label e função de exibição.
     * @param labelText Texto do label
     * @param displayFunction Função para converter o item em texto de exibição
     */
    public CampoComboBox(String labelText, Function<T, String> displayFunction) {
        super(labelText);
        this.displayFunction = displayFunction;
        this.model = new DefaultComboBoxModel<>();
        this.comboBox = new JComboBox<>(model);
        
        // ComboBox
        comboBox.setPreferredSize(new Dimension(200, 32));
        add(comboBox, BorderLayout.CENTER);
        
        setupRenderer();
    }
    
    private void setupRenderer() {
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                if (value == null) {
                    setText("");
                } else if (displayFunction != null) {
                    @SuppressWarnings("unchecked")
                    T item = (T) value;
                    setText(displayFunction.apply(item));
                } else {
                    setText(value.toString());
                }
                
                return this;
            }
        });
    }
    
    /**
     * Define a função de exibição para os itens.
     */
    public CampoComboBox<T> setDisplayFunction(Function<T, String> displayFunction) {
        this.displayFunction = displayFunction;
        comboBox.repaint();
        return this;
    }
    
    /**
     * Adiciona um item ao ComboBox.
     */
    public CampoComboBox<T> addItem(T item) {
        model.addElement(item);
        return this;
    }
    
    /**
     * Adiciona vários itens ao ComboBox.
     */
    public CampoComboBox<T> addItems(List<T> items) {
        for (T item : items) {
            model.addElement(item);
        }
        return this;
    }
    
    /**
     * Adiciona vários itens ao ComboBox.
     */
    @SafeVarargs
    public final CampoComboBox<T> addItems(T... items) {
        for (T item : items) {
            model.addElement(item);
        }
        return this;
    }
    
    /**
     * Define os itens do ComboBox (remove os anteriores).
     */
    public CampoComboBox<T> setItems(List<T> items) {
        model.removeAllElements();
        for (T item : items) {
            model.addElement(item);
        }
        return this;
    }
    
    /**
     * Remove todos os itens do ComboBox.
     */
    public CampoComboBox<T> clearItems() {
        model.removeAllElements();
        return this;
    }
    
    /**
     * Adiciona um item placeholder no início (ex: "Selecione...").
     */
    @SuppressWarnings("unchecked")
    public CampoComboBox<T> addPlaceholder(String placeholderText) {
        model.insertElementAt(null, 0);
        comboBox.setSelectedIndex(0);
        
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                
                if (value == null) {
                    setText(placeholderText);
                    setForeground(Color.GRAY);
                } else if (displayFunction != null) {
                    setText(displayFunction.apply((T) value));
                } else {
                    setText(value.toString());
                }
                
                return this;
            }
        });
        
        return this;
    }
    
    public int getSelectedIndex() {
        return comboBox.getSelectedIndex();
    }
    
    public CampoComboBox<T> setSelectedIndex(int index) {
        if (index >= 0 && index < model.getSize()) {
            comboBox.setSelectedIndex(index);
        }
        return this;
    }
    
    public int getItemCount() {
        return model.getSize();
    }
    
    public T getItemAt(int index) {
        if (index >= 0 && index < model.getSize()) {
            return model.getElementAt(index);
        }
        return null;
    }
    
    public CampoComboBox<T> addSelectionListener(Runnable listener) {
        comboBox.addActionListener(e -> listener.run());
        return this;
    }
    
    public JComboBox<T> getComboBox() {
        return comboBox;
    }
    
    public CampoComboBox<T> setEditable(boolean editable) {
        comboBox.setEditable(editable);
        return this;
    }
    
    public CampoComboBox<T> setRequired(boolean required) {
        this.required = required;
        String text = label.getText().replace(" *", "");
        if (required) {
            label.setText(text + " *");
        }
        return this;
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public T getValue() {
        return (T) comboBox.getSelectedItem();
    }
    
    @Override
    public void setValue(T value) {
        comboBox.setSelectedItem(value);
    }
    
    public void clear() {
        if (model.getSize() > 0) {
            comboBox.setSelectedIndex(0);
        }
    }
    
    @Override
    public boolean isValid() {
        if (required && comboBox != null) {
            return getValue() != null;
        }
        return true;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        comboBox.setEnabled(enabled);
        label.setEnabled(enabled);
    }
    
    public CampoComboBox<T> setPreferredWidth(int width) {
        Dimension size = comboBox.getPreferredSize();
        comboBox.setPreferredSize(new Dimension(width, size.height));
        return this;
    }
}
