package fields;

import base.BaseTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Campo de texto simples para formulários.
 * 
 * Recursos:
 * - Validação automática de campo obrigatório
 * - Limites de tamanho (min/max caracteres)
 * - Feedback visual de erro
 * - Compatível com NetBeans Design Builder
 * 
 * Exemplo:
 * <pre>
 * CampoTexto nome = new CampoTexto("Nome");
 * nome.setObrigatorio(true);
 * nome.setMinLength(3);
 * nome.setMaxLength(100);
 * </pre>
 * 
 * @author alefi
 */
public class CampoTexto extends CampoForm<String> {

    protected BaseTextField field;
    private int minLength = 0;
    private int maxLength = Integer.MAX_VALUE;
    private boolean validarEmTempoReal = false;

    public CampoTexto() {
        super("Campo de Texto");
        inicializar();
    }

    public CampoTexto(String titulo) {
        super(titulo);
        inicializar();
    }
    
    private void inicializar() {
        field = new BaseTextField();
        add(field, java.awt.BorderLayout.CENTER);
        
        // Listener para validação em tempo real (opcional)
        field.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validarSeAtivo(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validarSeAtivo(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validarSeAtivo(); }
            
            private void validarSeAtivo() {
                if (validarEmTempoReal) {
                    validarComFeedback();
                }
            }
        });
    }

    @Override
    public String getValue() {
        return field != null ? field.getText() : "";
    }

    @Override
    public void setValue(String value) {
        field.setText(value);
    }

    @Override
    public boolean isValid() {
        // Primeiro valida obrigatoriedade (da classe pai)
        if (!super.isValid()) {
            return false;
        }
        
        String valor = getValue().trim();
        
        // Validação de tamanho mínimo
        if (valor.length() > 0 && valor.length() < minLength) {
            setMensagemErro(getTextLabel() + " deve ter no mínimo " + minLength + " caracteres");
            return false;
        }
        
        // Validação de tamanho máximo
        if (valor.length() > maxLength) {
            setMensagemErro(getTextLabel() + " deve ter no máximo " + maxLength + " caracteres");
            return false;
        }
        
        return true;
    }
    
    /**
     * Define o tamanho mínimo do texto.
     * @param minLength número mínimo de caracteres
     */
    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }
    
    /**
     * Define o tamanho máximo do texto.
     * @param maxLength número máximo de caracteres
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
    
    /**
     * Ativa validação em tempo real enquanto digita.
     * @param ativo true para ativar
     */
    public void setValidarEmTempoReal(boolean ativo) {
        this.validarEmTempoReal = ativo;
    }

    public BaseTextField getField() {
        return field;
    }
}
