package components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFormattedTextField;
import javax.swing.UIManager;
import javax.swing.text.MaskFormatter;

/**
 * Campo de data com máscara formatada (dd/MM/yyyy).
 * 
 * @author alefi
 */
public class CampoData extends CampoForm<Date> {

    private JFormattedTextField campo;
    private SimpleDateFormat dateFormat;

    public CampoData() {
        super("Data");
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        
        try {
            MaskFormatter formatter = new MaskFormatter("##/##/####");
            formatter.setPlaceholderCharacter('_');
            campo = new JFormattedTextField(formatter);
        } catch (ParseException e) {
            campo = new JFormattedTextField();
        }
        campo.setPreferredSize(new Dimension(100, 28));
        setForeground(UIManager.getColor("Label.foreground"));
        setFont(UIManager.getFont("Label.font"));
        add(campo, BorderLayout.CENTER);
    }

    public CampoData(String titulo) {
        this();
        setTextLabel(titulo);
    }

    @Override
    public Date getValue() {
        try {
            String texto = campo.getText().trim();
            if (texto.isEmpty() || texto.contains("_")) {
                return null;
            }
            return dateFormat.parse(texto);
        } catch (ParseException e) {
            return null;
        }
    }

    @Override
    public void setValue(Date value) {
        if (value == null) {
            campo.setText("");
        } else {
            campo.setText(dateFormat.format(value));
        }
    }

    @Override
    public boolean isValido() {
        return getValue() != null;
    }
    
    /**
     * Obtém o valor como String no formato dd/MM/yyyy
     * @return data formatada ou string vazia
     */
    public String getValueAsString() {
        Date data = getValue();
        return data != null ? dateFormat.format(data) : "";
    }
    
    /**
     * Define o valor a partir de uma String no formato dd/MM/yyyy
     * @param dataStr string da data
     */
    public void setValueFromString(String dataStr) {
        try {
            if (dataStr == null || dataStr.trim().isEmpty()) {
                campo.setText("");
            } else {
                Date data = dateFormat.parse(dataStr);
                setValue(data);
            }
        } catch (ParseException e) {
            campo.setText("");
        }
    }
    
    public JFormattedTextField getCampo() {
        return campo;
    }
}
