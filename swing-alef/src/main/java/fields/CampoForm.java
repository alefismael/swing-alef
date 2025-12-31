package fields;

import base.BaseLabel;
import base.Validavel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

/**
 * Classe base abstrata para componentes de formulário com label e validação.
 * Todos os campos de formulário devem estender esta classe.
 * 
 * Implementa {@link Validavel} para integração com dialogs e formulários.
 * 
 * Recursos:
 * - Label automático
 * - Validação com feedback visual (borda vermelha + mensagem)
 * - Campo obrigatório configurável
 * - Compatível com NetBeans Design Builder
 * 
 * @param <T> Tipo do valor do campo
 * @author alefi
 */
public abstract class CampoForm<T> extends JPanel implements Validavel {
    
    protected BaseLabel label;
    protected JLabel labelErro;
    protected boolean obrigatorio = false;
    protected String mensagemErro;
    
    // Bordas para estados
    private static final Border BORDA_NORMAL = BorderFactory.createEmptyBorder(0, 0, 0, 0);
    private static final Border BORDA_ERRO = BorderFactory.createCompoundBorder(
        BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(239, 68, 68)),
        BorderFactory.createEmptyBorder(0, 0, 0, 0)
    );
    private static final Color COR_ERRO = new Color(239, 68, 68);

    public CampoForm(String titulo) {
        super(new BorderLayout(4, 2));

        label = new BaseLabel(titulo);
        add(label, BorderLayout.NORTH);
        
        // Label de erro (inicialmente invisível)
        labelErro = new JLabel(" ");
        labelErro.setForeground(COR_ERRO);
        labelErro.setFont(labelErro.getFont().deriveFont(11f));
        labelErro.setVisible(false);
        add(labelErro, BorderLayout.SOUTH);
    }
    
    @Override
    public Dimension getPreferredSize() {
        // Força cálculo do layout para garantir preferredSize correto
        doLayout();
        return super.getPreferredSize();
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
     * Verifica se o valor do campo é válido.
     * Subclasses podem sobrescrever para adicionar validações específicas.
     * @return true se válido, false caso contrário
     */
    @Override
    public boolean isValid() {
        mensagemErro = null;
        
        // Validação de campo obrigatório
        if (obrigatorio) {
            T valor = getValue();
            if (valor == null || (valor instanceof String && ((String) valor).trim().isEmpty())) {
                mensagemErro = label.getText() + " é obrigatório";
                return false;
            }
        }
        
        return true;
    }
    
    @Override
    public String getMensagemErro() {
        return mensagemErro;
    }
    
    @Override
    public void mostrarErro() {
        setBorder(BORDA_ERRO);
        if (mensagemErro != null && !mensagemErro.isEmpty()) {
            labelErro.setText(mensagemErro);
            labelErro.setVisible(true);
        }
    }
    
    @Override
    public void limparErro() {
        setBorder(BORDA_NORMAL);
        labelErro.setText(" ");
        labelErro.setVisible(false);
    }
    
    /**
     * Define se o campo é obrigatório.
     * Campos obrigatórios exibem * no label.
     * @param obrigatorio true se obrigatório
     */
    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
        atualizarLabel();
    }
    
    /**
     * Verifica se o campo é obrigatório.
     * @return true se obrigatório
     */
    public boolean isObrigatorio() {
        return obrigatorio;
    }
    
    /**
     * Define uma mensagem de erro customizada.
     * @param mensagem mensagem de erro
     */
    protected void setMensagemErro(String mensagem) {
        this.mensagemErro = mensagem;
    }
    
    private void atualizarLabel() {
        String texto = label.getText().replace(" *", "");
        if (obrigatorio) {
            label.setText(texto + " *");
        } else {
            label.setText(texto);
        }
    }
    
    public String getTextLabel() {
        return label.getText().replace(" *", "");
    }
    
    public void setTextLabel(String textLabel) {
        label.setText(textLabel);
        if (obrigatorio) {
            atualizarLabel();
        }
    }
}
