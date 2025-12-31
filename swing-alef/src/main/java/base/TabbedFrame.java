package base;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import ui.DialogUtil;

/**
 * Frame principal com suporte a abas de documentos.
 * 
 * Evolução do BaseFrame que integra o TabbedDocumentPane para
 * gerenciamento de múltiplas janelas/painéis simultâneos.
 * 
 * Características:
 * - Abas fecháveis com indicador de modificações
 * - Barra de navegação/menu opcional
 * - Suporte a F11 para tela cheia
 * - Atalhos de teclado para navegação entre abas
 * 
 * Exemplo de uso:
 * <pre>
 * TabbedFrame frame = new TabbedFrame("Minha Aplicação");
 * frame.adicionarAba("Clientes", new ClientePanel());
 * frame.adicionarAba("Produtos", new ProdutoPanel());
 * frame.setVisible(true);
 * </pre>
 * 
 * @author alefi
 */
public class TabbedFrame extends JFrame {
    
    private final TabbedDocumentPane tabbedPane;
    private final JPanel headerPanel;
    private final JPanel statusBar;
    private final JLabel statusLabel;
    private boolean fullScreen = false;
    
    public TabbedFrame(String titulo) {
        super(titulo);
        
        // Inicializa componentes
        tabbedPane = new TabbedDocumentPane();
        headerPanel = new JPanel(new BorderLayout());
        statusBar = new JPanel(new BorderLayout());
        statusLabel = new JLabel(" ");
        
        configurarLayout();
        configurarStatusBar();
        configurarAtalhos();
        configurarEventos();
        configurarFrame();
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout());
        
        // Header (pode conter menu, toolbar, etc)
        headerPanel.setOpaque(false);
        add(headerPanel, BorderLayout.NORTH);
        
        // Conteúdo principal (abas)
        add(tabbedPane, BorderLayout.CENTER);
        
        // Barra de status
        add(statusBar, BorderLayout.SOUTH);
    }
    
    private void configurarStatusBar() {
        statusBar.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, UIManager.getColor("Separator.foreground")),
            BorderFactory.createEmptyBorder(4, 8, 4, 8)
        ));
        statusBar.setPreferredSize(new Dimension(0, 28));
        statusBar.add(statusLabel, BorderLayout.WEST);
    }
    
    private void configurarAtalhos() {
        // F11 - Tela cheia
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "toggleFullScreen");
        getRootPane().getActionMap().put("toggleFullScreen", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alternarTelaCheia();
            }
        });
        
        // Ctrl+N - Nova aba (pode ser sobrescrito)
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK), "novaAba");
        getRootPane().getActionMap().put("novaAba", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNovaAba();
            }
        });
    }
    
    private void configurarEventos() {
        // Atualiza status quando aba é selecionada
        tabbedPane.setOnTabSelected(comp -> {
            atualizarStatus();
        });
        
        // Confirmação ao fechar janela
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fecharAplicacao();
            }
        });
    }
    
    private void configurarFrame() {
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }
    
    // ==================== MÉTODOS PÚBLICOS ====================
    
    /**
     * Adiciona uma nova aba.
     * @param titulo Título da aba
     * @param componente Componente a exibir
     */
    public void adicionarAba(String titulo, Component componente) {
        tabbedPane.adicionarAba(titulo, componente);
        atualizarStatus();
    }
    
    /**
     * Adiciona uma nova aba com ícone.
     * @param titulo Título da aba
     * @param icone Ícone da aba
     * @param componente Componente a exibir
     */
    public void adicionarAba(String titulo, Icon icone, Component componente) {
        tabbedPane.adicionarAba(titulo, icone, componente);
        atualizarStatus();
    }
    
    /**
     * Adiciona uma aba fixa (não fechável).
     * @param titulo Título da aba
     * @param icone Ícone da aba
     * @param componente Componente a exibir
     */
    public void adicionarAbaFixa(String titulo, Icon icone, Component componente) {
        tabbedPane.adicionarAbaFixa(titulo, icone, componente);
        atualizarStatus();
    }
    
    /**
     * Retorna o painel de abas para manipulação direta.
     * @return TabbedDocumentPane
     */
    public TabbedDocumentPane getTabbedPane() {
        return tabbedPane;
    }
    
    /**
     * Retorna o painel de cabeçalho para adicionar menus/toolbars.
     * @return JPanel do header
     */
    public JPanel getHeaderPanel() {
        return headerPanel;
    }
    
    /**
     * Define o texto da barra de status.
     * @param texto Texto a exibir
     */
    public void setStatus(String texto) {
        statusLabel.setText(texto != null ? texto : " ");
    }
    
    /**
     * Mostra ou oculta a barra de status.
     * @param visivel true para mostrar
     */
    public void setStatusBarVisivel(boolean visivel) {
        statusBar.setVisible(visivel);
    }
    
    /**
     * Alterna entre modo tela cheia e janela normal.
     */
    public void alternarTelaCheia() {
        GraphicsDevice device = GraphicsEnvironment
            .getLocalGraphicsEnvironment()
            .getDefaultScreenDevice();
        
        if (!device.isFullScreenSupported()) {
            return;
        }
        
        if (fullScreen) {
            device.setFullScreenWindow(null);
            dispose();
            setUndecorated(false);
            setVisible(true);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            fullScreen = false;
        } else {
            dispose();
            setUndecorated(true);
            device.setFullScreenWindow(this);
            setVisible(true);
            fullScreen = true;
        }
    }
    
    /**
     * Fecha a aplicação, verificando modificações pendentes.
     */
    public void fecharAplicacao() {
        if (tabbedPane.existeModificacoes()) {
            if (!DialogUtil.confirmar(this, 
                "Existem abas com alterações não salvas.\nDeseja sair mesmo assim?",
                "Confirmação")) {
                return;
            }
        }
        dispose();
        System.exit(0);
    }
    
    // ==================== MÉTODOS PROTEGIDOS ====================
    
    /**
     * Chamado quando Ctrl+N é pressionado.
     * Sobrescreva para criar nova aba do tipo desejado.
     */
    protected void onNovaAba() {
        // Implementação padrão vazia
        // Sobrescreva para adicionar comportamento
    }
    
    /**
     * Atualiza a barra de status com informações das abas.
     */
    protected void atualizarStatus() {
        int total = tabbedPane.getTabCount();
        int index = tabbedPane.getSelectedIndex();
        
        if (total == 0) {
            setStatus("Nenhuma aba aberta");
        } else {
            String titulo = tabbedPane.getTitleAt(index);
            setStatus("Aba " + (index + 1) + " de " + total + " - " + titulo);
        }
    }
}
