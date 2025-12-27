/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

import base.BaseLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 *
 * @author alefi
 */
public abstract class CampoForm<T> extends JPanel{
    
    protected BaseLabel label;

    public CampoForm(String titulo) {
        setLayout(new BorderLayout(4, 4));
        setOpaque(false);

        label = new BaseLabel(titulo);
        add(label, BorderLayout.NORTH);
    }

    public abstract T getValue();
    public abstract void setValue(T value);
    public abstract boolean isValido();
    
    public String getTextLabel(){
       return label.getText();
    }
    
    public void setTextLabel(String textLabel){
       label.setText(textLabel);
    }
}
