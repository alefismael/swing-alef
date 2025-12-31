package fields;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.UIManager;
import javax.swing.text.MaskFormatter;

/**
 * Campo de CEP com máscara formatada (99999-999).
 * 
 * @author alefi
 */
public class CampoCep extends CampoForm<String> {

    private JFormattedTextField campo;

    public CampoCep() {
        super("CEP");
        try {
            MaskFormatter formatter = new MaskFormatter("#####-###");
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

    @Override
    public String getValue() {
        return campo != null ? campo.getText().trim() : "";
    }

    @Override
    public void setValue(String value) {
        campo.setText(value);
    }

    @Override
    public boolean isValid() {
        return !getValue().isBlank() && getValue().matches("\\d{5}-\\d{3}");
    }
    
    public JFormattedTextField getCampo() {
        return campo;
    }
}
