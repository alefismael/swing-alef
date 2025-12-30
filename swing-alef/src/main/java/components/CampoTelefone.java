package components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.UIManager;
import javax.swing.text.MaskFormatter;

/**
 * Campo de telefone com máscara formatada.
 * Suporta telefone fixo (99) 9999-9999 e celular (99) 99999-9999.
 * 
 * @author alefi
 */
public class CampoTelefone extends CampoForm<String> {

    private JFormattedTextField campo;
    private boolean celular;

    public CampoTelefone() {
        this("Telefone", false);
    }

    public CampoTelefone(String titulo) {
        this(titulo, false);
    }
    
    public CampoTelefone(String titulo, boolean celular) {
        super(titulo);
        this.celular = celular;
        try {
            String mask = celular ? "(##) #####-####" : "(##) ####-####";
            MaskFormatter formatter = new MaskFormatter(mask);
            formatter.setPlaceholderCharacter('_');
            campo = new JFormattedTextField(formatter);
        } catch (ParseException e) {
            campo = new JFormattedTextField();
        }
        campo.setPreferredSize(new Dimension(140, 28));
        setForeground(UIManager.getColor("Label.foreground"));
        setFont(UIManager.getFont("Label.font"));
        add(campo, BorderLayout.CENTER);
    }

    @Override
    public String getValue() {
        return campo.getText().trim();
    }

    @Override
    public void setValue(String value) {
        campo.setText(value);
    }

    @Override
    public boolean isValido() {
        String valor = getValue();
        if (valor.isBlank()) return false;
        
        if (celular) {
            return valor.matches("\\(\\d{2}\\) \\d{5}-\\d{4}");
        } else {
            return valor.matches("\\(\\d{2}\\) \\d{4}-\\d{4}");
        }
    }
    
    public JFormattedTextField getCampo() {
        return campo;
    }
    
    public boolean isCelular() {
        return celular;
    }
}
