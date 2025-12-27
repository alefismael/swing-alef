/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package components;

import enums.TypeFieldText;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author alefi
 */
public class InsertTextPanel extends JPanel{
    private JLabel label;
    private JTextField textField;
    private TypeFieldText tipoCampo = TypeFieldText.NOME;

    public InsertTextPanel() {
        init(); //panel
        initTextField(); //textField
                //label
    }

    private void init() {
        setLayout(new BorderLayout(0, 4));
        setOpaque(false);        
        label = new JLabel("Insert Text KetSoft");
        textField = new JTextField(20);
        add(label, BorderLayout.NORTH);
        add(textField, BorderLayout.CENTER);
    }

    
    public String getLabelText() {
        return label.getText();
    }

    public void setLabelText(String text) {
        label.setText(text);
    }
    
    private void initTextField(){
        textField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153), 2));
        textField.setSelectionColor(new java.awt.Color(153, 153, 153));
        Dimension d = textField.getPreferredSize();
        d.height = 28;
        textField.setPreferredSize(d);
    }
    
    public void aplicarTipo(TypeFieldText tipo) {
        textField.setColumns(tipo.getColunas());
        revalidate();
    }
    
    public TypeFieldText getTipoCampo() {
        return tipoCampo;
    }
    
    public void setTipoCampo(TypeFieldText tipoCampo) {
        aplicarTipo(tipoCampo);
        this.tipoCampo = tipoCampo;
    }
}
