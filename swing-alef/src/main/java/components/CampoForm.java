package components;

import base.BaseLabel;
import java.awt.BorderLayout;
import javax.swing.JPanel;

/**
 * Classe base abstrata para componentes de formulário com label e validação.
 * Todos os campos de formulário devem estender esta classe.
 * 
 * @param <T> Tipo do valor do campo
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

    /**
     * Obtém o valor atual do campo
     * @return valor do campo
     */
    public abstract T getValue();
    
    /**
     * Define o valor do campo
     * @param value novo valor
     */
    public abstract void setValue(T value);
    
    /**
     * Verifica se o valor do campo é válido
     * @return true se válido, false caso contrário
     */
    public abstract boolean isValido();
    
    public String getTextLabel(){
       return label.getText();
    }
    
    public void setTextLabel(String textLabel){
       label.setText(textLabel);
    }
}
