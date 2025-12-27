package form;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.text.MaskFormatter;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
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
        return !getValue().isBlank() && getValue().matches("\\d{5}-\\d{3}");
    }
}
