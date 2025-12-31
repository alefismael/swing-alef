package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Utilitário para diálogos padronizados em português.
 * Compatível com FlatLaf e outros Look and Feels.
 * 
 * <p>Exemplo de uso:</p>
 * <pre>{@code
 * // Confirmação simples
 * if (DialogUtil.confirmar(parent, "Deseja salvar as alterações?")) {
 *     salvar();
 * }
 * 
 * // Confirmação de exclusão
 * if (DialogUtil.confirmarExclusao(parent, "Cliente")) {
 *     excluirCliente();
 * }
 * 
 * // Mensagens
 * DialogUtil.info(parent, "Operação concluída!");
 * DialogUtil.aviso(parent, "Campo obrigatório não preenchido!");
 * DialogUtil.erro(parent, "Erro ao conectar com o banco de dados.");
 * 
 * // Input
 * String nome = DialogUtil.input(parent, "Digite o nome:");
 * }</pre>
 * 
 * @author alefi
 */
public final class DialogUtil {
    
    // Títulos padrão
    private static final String TITULO_CONFIRMAR = "Confirmação";
    private static final String TITULO_INFO = "Informação";
    private static final String TITULO_AVISO = "Aviso";
    private static final String TITULO_ERRO = "Erro";
    private static final String TITULO_INPUT = "Entrada";
    
    // Botões em português
    private static final String[] BOTOES_SIM_NAO = {"Sim", "Não"};
    private static final String[] BOTOES_SIM_NAO_CANCELAR = {"Sim", "Não", "Cancelar"};
    private static final String[] BOTOES_OK_CANCELAR = {"OK", "Cancelar"};
    
    private DialogUtil() {
        // Classe utilitária - não instanciar
    }
    
    // ==================== CONFIRMAÇÕES ====================
    
    /**
     * Exibe diálogo de confirmação Sim/Não.
     * @param parent Componente pai
     * @param mensagem Mensagem a exibir
     * @return true se usuário clicou em Sim
     */
    public static boolean confirmar(Component parent, String mensagem) {
        return confirmar(parent, mensagem, TITULO_CONFIRMAR);
    }
    
    /**
     * Exibe diálogo de confirmação Sim/Não com título customizado.
     * @param parent Componente pai
     * @param mensagem Mensagem a exibir
     * @param titulo Título do diálogo
     * @return true se usuário clicou em Sim
     */
    public static boolean confirmar(Component parent, String mensagem, String titulo) {
        int resultado = JOptionPane.showOptionDialog(
            getWindow(parent),
            mensagem,
            titulo,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            BOTOES_SIM_NAO,
            BOTOES_SIM_NAO[0]
        );
        return resultado == 0; // Sim
    }
    
    /**
     * Exibe diálogo de confirmação para exclusão.
     * @param parent Componente pai
     * @param entidade Nome da entidade (ex: "Cliente", "Produto")
     * @return true se usuário confirmou a exclusão
     */
    public static boolean confirmarExclusao(Component parent, String entidade) {
        String mensagem = "Deseja realmente excluir este registro de " + entidade + "?\n" +
                         "Esta ação não poderá ser desfeita.";
        return confirmar(parent, mensagem, "Confirmar Exclusão");
    }
    
    /**
     * Exibe diálogo de confirmação para exclusão com nome específico.
     * @param parent Componente pai
     * @param entidade Nome da entidade
     * @param nomeRegistro Nome do registro específico
     * @return true se usuário confirmou a exclusão
     */
    public static boolean confirmarExclusao(Component parent, String entidade, String nomeRegistro) {
        String mensagem = "Deseja realmente excluir \"" + nomeRegistro + "\"?\n" +
                         "Esta ação não poderá ser desfeita.";
        return confirmar(parent, mensagem, "Confirmar Exclusão de " + entidade);
    }
    
    /**
     * Exibe diálogo de confirmação Sim/Não/Cancelar.
     * @param parent Componente pai
     * @param mensagem Mensagem a exibir
     * @return 0=Sim, 1=Não, 2=Cancelar, -1=Fechou
     */
    public static int confirmarComCancelar(Component parent, String mensagem) {
        return confirmarComCancelar(parent, mensagem, TITULO_CONFIRMAR);
    }
    
    /**
     * Exibe diálogo de confirmação Sim/Não/Cancelar com título.
     * @param parent Componente pai
     * @param mensagem Mensagem a exibir
     * @param titulo Título do diálogo
     * @return 0=Sim, 1=Não, 2=Cancelar, -1=Fechou
     */
    public static int confirmarComCancelar(Component parent, String mensagem, String titulo) {
        return JOptionPane.showOptionDialog(
            getWindow(parent),
            mensagem,
            titulo,
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            BOTOES_SIM_NAO_CANCELAR,
            BOTOES_SIM_NAO_CANCELAR[0]
        );
    }
    
    /**
     * Exibe diálogo para salvar alterações antes de fechar.
     * @param parent Componente pai
     * @return 0=Salvar, 1=Não Salvar, 2=Cancelar, -1=Fechou
     */
    public static int confirmarSalvarAntesDeSair(Component parent) {
        String[] botoes = {"Salvar", "Não Salvar", "Cancelar"};
        return JOptionPane.showOptionDialog(
            getWindow(parent),
            "Existem alterações não salvas.\nDeseja salvar antes de sair?",
            "Salvar Alterações",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            botoes,
            botoes[0]
        );
    }
    
    // ==================== MENSAGENS ====================
    
    /**
     * Exibe mensagem de informação.
     * @param parent Componente pai
     * @param mensagem Mensagem a exibir
     */
    public static void info(Component parent, String mensagem) {
        info(parent, mensagem, TITULO_INFO);
    }
    
    /**
     * Exibe mensagem de informação com título.
     * @param parent Componente pai
     * @param mensagem Mensagem a exibir
     * @param titulo Título do diálogo
     */
    public static void info(Component parent, String mensagem, String titulo) {
        JOptionPane.showMessageDialog(
            getWindow(parent),
            mensagem,
            titulo,
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Exibe mensagem de aviso.
     * @param parent Componente pai
     * @param mensagem Mensagem a exibir
     */
    public static void aviso(Component parent, String mensagem) {
        aviso(parent, mensagem, TITULO_AVISO);
    }
    
    /**
     * Exibe mensagem de aviso com título.
     * @param parent Componente pai
     * @param mensagem Mensagem a exibir
     * @param titulo Título do diálogo
     */
    public static void aviso(Component parent, String mensagem, String titulo) {
        JOptionPane.showMessageDialog(
            getWindow(parent),
            mensagem,
            titulo,
            JOptionPane.WARNING_MESSAGE
        );
    }
    
    /**
     * Exibe mensagem de erro.
     * @param parent Componente pai
     * @param mensagem Mensagem a exibir
     */
    public static void erro(Component parent, String mensagem) {
        erro(parent, mensagem, TITULO_ERRO);
    }
    
    /**
     * Exibe mensagem de erro com título.
     * @param parent Componente pai
     * @param mensagem Mensagem a exibir
     * @param titulo Título do diálogo
     */
    public static void erro(Component parent, String mensagem, String titulo) {
        JOptionPane.showMessageDialog(
            getWindow(parent),
            mensagem,
            titulo,
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Exibe mensagem de erro com detalhes da exceção.
     * @param parent Componente pai
     * @param mensagem Mensagem principal
     * @param ex Exceção ocorrida
     */
    public static void erro(Component parent, String mensagem, Exception ex) {
        String detalhes = mensagem + "\n\nDetalhes: " + ex.getMessage();
        erro(parent, detalhes, TITULO_ERRO);
    }
    
    // ==================== INPUT ====================
    
    /**
     * Exibe diálogo para entrada de texto.
     * @param parent Componente pai
     * @param mensagem Mensagem/label
     * @return Texto digitado ou null se cancelou
     */
    public static String input(Component parent, String mensagem) {
        return input(parent, mensagem, TITULO_INPUT, "");
    }
    
    /**
     * Exibe diálogo para entrada de texto com valor inicial.
     * @param parent Componente pai
     * @param mensagem Mensagem/label
     * @param valorInicial Valor inicial do campo
     * @return Texto digitado ou null se cancelou
     */
    public static String input(Component parent, String mensagem, String valorInicial) {
        return input(parent, mensagem, TITULO_INPUT, valorInicial);
    }
    
    /**
     * Exibe diálogo para entrada de texto com título e valor inicial.
     * @param parent Componente pai
     * @param mensagem Mensagem/label
     * @param titulo Título do diálogo
     * @param valorInicial Valor inicial do campo
     * @return Texto digitado ou null se cancelou
     */
    public static String input(Component parent, String mensagem, String titulo, String valorInicial) {
        return (String) JOptionPane.showInputDialog(
            getWindow(parent),
            mensagem,
            titulo,
            JOptionPane.QUESTION_MESSAGE,
            null,
            null,
            valorInicial
        );
    }
    
    /**
     * Exibe diálogo para seleção em lista.
     * @param parent Componente pai
     * @param mensagem Mensagem/label
     * @param opcoes Array de opções
     * @return Opção selecionada ou null se cancelou
     */
    public static Object selecionar(Component parent, String mensagem, Object[] opcoes) {
        return selecionar(parent, mensagem, "Selecione", opcoes, opcoes[0]);
    }
    
    /**
     * Exibe diálogo para seleção em lista com título.
     * @param parent Componente pai
     * @param mensagem Mensagem/label
     * @param titulo Título do diálogo
     * @param opcoes Array de opções
     * @param valorInicial Valor inicial selecionado
     * @return Opção selecionada ou null se cancelou
     */
    public static Object selecionar(Component parent, String mensagem, String titulo, 
                                     Object[] opcoes, Object valorInicial) {
        return JOptionPane.showInputDialog(
            getWindow(parent),
            mensagem,
            titulo,
            JOptionPane.QUESTION_MESSAGE,
            null,
            opcoes,
            valorInicial
        );
    }
    
    // ==================== UTILITÁRIOS ====================
    
    /**
     * Obtém a janela pai de um componente.
     */
    private static Window getWindow(Component component) {
        if (component == null) {
            return null;
        }
        if (component instanceof Window) {
            return (Window) component;
        }
        return SwingUtilities.getWindowAncestor(component);
    }
}
