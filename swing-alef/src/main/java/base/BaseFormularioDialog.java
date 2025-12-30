package base;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Diálogo base para formulários modais.
 * 
 * Uso:
 * <pre>
 * PainelFormularioDialog dialog = new PainelFormularioDialog(framePai, "Novo Cliente");
 * dialog.adicionarCampo(new CampoTexto("Nome"));
 * dialog.adicionarCampo(new CampoTexto("Email"));
 * dialog.mostrarDialogo(() -> {
 *     String nome = dialog.obterCampo(0).getValue();
 *     String email = dialog.obterCampo(1).getValue();
 *     salvarCliente(nome, email);
 * });
 * </pre>
 * 
 * @author alefi
 */
public class BaseFormularioDialog extends JDialog {
    
    private final BaseFormPanel painelFormulario;
    private final JPanel painelBotoes;
    private boolean confirmado = false;
    
    public BaseFormularioDialog(JFrame framePai, String titulo) {
        super(framePai, titulo, true);
        
        painelFormulario = new BaseFormPanel();
        painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        
        configurar();
    }
    
    private void configurar() {
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(getParent());
        setResizable(true);
        
        // Painel principal
        JPanel pnlPrincipal = new JPanel(new BorderLayout());
        pnlPrincipal.add(painelFormulario, BorderLayout.CENTER);
        pnlPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(pnlPrincipal);
    }
    
    /**
     * Adiciona um campo ao formulário do diálogo.
     * 
     * @param campo componente do campo
     */
    public void adicionarCampo(javax.swing.JComponent campo) {
        painelFormulario.adicionarCampo(campo);
    }
    
    /**
     * Define os tamanho do diálogo.
     * 
     * @param largura largura em pixels
     * @param altura altura em pixels
     */
    public void definirTamanho(int largura, int altura) {
        setSize(largura, altura);
    }
    
    /**
     * Mostra o diálogo e aguarda interação do usuário.
     * 
     * @param acaoConfirmar ação executada ao clicar em OK
     */
    public void mostrarDialogo(Runnable acaoConfirmar) {
        painelBotoes.removeAll();
        
        BaseButton botaoOK = new BaseButton("Confirmar");
        botaoOK.addActionListener(e -> {
            confirmado = true;
            if (acaoConfirmar != null) {
                acaoConfirmar.run();
            }
            dispose();
        });
        
        BaseButton botaoCancelar = new BaseButton("Cancelar");
        botaoCancelar.addActionListener(e -> {
            confirmado = false;
            dispose();
        });
        
        painelBotoes.add(botaoOK);
        painelBotoes.add(botaoCancelar);
        
        setVisible(true);
    }
    
    /**
     * Obtém o painel de formulário para acesso direto.
     * 
     * @return BaseFormPanel
     */
    public BaseFormPanel obterPainelFormulario() {
        return painelFormulario;
    }
    
    /**
     * Verifica se foi confirmado.
     * 
     * @return true se clicou OK
     */
    public boolean foiConfirmado() {
        return confirmado;
    }
    
    /**
     * Limpa o formulário.
     */
    public void limpar() {
        painelFormulario.limpar();
    }
}
