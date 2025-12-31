package example;

import base.*;
import fields.*;
import ui.Toast;
import util.KeyBindingManager;
import util.KeyBindingManager.Atalho;

import javax.swing.*;
import java.awt.*;

/**
 * Exemplo demonstrando o sistema de atalhos de teclado.
 * 
 * Atalhos disponíveis:
 * - Ctrl+S: Salvar
 * - Ctrl+N: Novo registro
 * - Ctrl+F: Buscar
 * - Ctrl+W: Fechar aba atual
 * - Ctrl+Tab: Próxima aba
 * - Ctrl+Shift+Tab: Aba anterior
 * - ESC: Cancelar/Fechar dialog
 * - F5: Atualizar
 * - F11: Tela cheia
 * 
 * @author swing-alef
 */
public class ExemploAtalhos {
    
    private static JFrame frame;
    private static TabbedDocumentPane tabbedPane;
    
    public static void main(String[] args) {
        // Tema FlatLaf
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(ExemploAtalhos::criarInterface);
    }
    
    private static void criarInterface() {
        frame = new JFrame("Exemplo de Atalhos - swing-alef");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        
        // Toolbar com busca
        JToolBar toolbar = criarToolbar();
        
        // TabbedPane principal
        tabbedPane = new TabbedDocumentPane();
        tabbedPane.adicionarAbaFixa("Principal", null, criarPainelPrincipal());
        
        // Registrar atalhos globais no frame
        configurarAtalhosGlobais();
        
        // Layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(toolbar, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        // Toast informativo
        Toast.info(frame, "Pressione F1 para ver todos os atalhos disponíveis");
    }
    
    private static void configurarAtalhosGlobais() {
        // Ctrl+S - Salvar
        KeyBindingManager.registrarGlobal(frame, Atalho.SALVAR, () -> {
            Toast.success(frame, "Salvo! (Ctrl+S)");
        });
        
        // Ctrl+N - Novo
        KeyBindingManager.registrarGlobal(frame, Atalho.NOVO, () -> {
            abrirNovoRegistro();
        });
        
        // F5 - Atualizar
        KeyBindingManager.registrarGlobal(frame, Atalho.ATUALIZAR, () -> {
            Toast.info(frame, "Atualizando... (F5)");
        });
        
        // F11 - Tela cheia
        KeyBindingManager.registrarGlobal(frame, Atalho.TELA_CHEIA, () -> {
            toggleTelaCheia();
        });
        
        // F1 - Ajuda (mostra atalhos)
        KeyBindingManager.registrarGlobal(frame, Atalho.AJUDA, () -> {
            mostrarAjudaAtalhos();
        });
        
        // Ctrl+P - Imprimir
        KeyBindingManager.registrarGlobal(frame, Atalho.IMPRIMIR, () -> {
            Toast.info(frame, "Imprimir... (Ctrl+P)");
        });
    }
    
    private static JToolBar criarToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        
        // Botão Novo com tooltip incluindo atalho
        JButton btnNovo = new JButton("📄 Novo");
        btnNovo.setToolTipText(KeyBindingManager.tooltipComAtalho("Novo registro", Atalho.NOVO));
        btnNovo.addActionListener(e -> abrirNovoRegistro());
        toolbar.add(btnNovo);
        
        // Botão Salvar
        JButton btnSalvar = new JButton("💾 Salvar");
        btnSalvar.setToolTipText(KeyBindingManager.tooltipComAtalho("Salvar alterações", Atalho.SALVAR));
        btnSalvar.addActionListener(e -> Toast.success(frame, "Salvo!"));
        toolbar.add(btnSalvar);
        
        toolbar.addSeparator();
        
        // Botão Atualizar
        JButton btnAtualizar = new JButton("🔄 Atualizar");
        btnAtualizar.setToolTipText(KeyBindingManager.tooltipComAtalho("Atualizar dados", Atalho.ATUALIZAR));
        btnAtualizar.addActionListener(e -> Toast.info(frame, "Atualizando..."));
        toolbar.add(btnAtualizar);
        
        toolbar.addSeparator();
        
        // Campo de busca
        JTextField campoBusca = new JTextField(15);
        campoBusca.setToolTipText(KeyBindingManager.tooltipComAtalho("Buscar", Atalho.BUSCAR));
        campoBusca.setMaximumSize(campoBusca.getPreferredSize());
        toolbar.add(new JLabel(" 🔍 "));
        toolbar.add(campoBusca);
        
        toolbar.add(Box.createHorizontalGlue());
        
        // Botão Ajuda
        JButton btnAjuda = new JButton("❓");
        btnAjuda.setToolTipText(KeyBindingManager.tooltipComAtalho("Ajuda", Atalho.AJUDA));
        btnAjuda.addActionListener(e -> mostrarAjudaAtalhos());
        toolbar.add(btnAjuda);
        
        return toolbar;
    }
    
    private static JPanel criarPainelPrincipal() {
        JPanel painel = new JPanel(new BorderLayout(10, 10));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titulo = new JLabel("<html><h1>🎹 Sistema de Atalhos</h1>" +
                "<p>Esta demonstração mostra como usar atalhos de teclado no swing-alef.</p></html>");
        painel.add(titulo, BorderLayout.NORTH);
        
        // Lista de atalhos
        String[] atalhos = {
            "Ctrl+S - Salvar",
            "Ctrl+N - Novo registro",
            "Ctrl+F - Focar na busca",
            "Ctrl+W - Fechar aba atual",
            "Ctrl+Tab - Próxima aba",
            "Ctrl+Shift+Tab - Aba anterior",
            "ESC - Cancelar/Fechar dialog",
            "F1 - Mostrar ajuda",
            "F5 - Atualizar",
            "F11 - Tela cheia"
        };
        
        JList<String> lista = new JList<>(atalhos);
        lista.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
        lista.setBackground(new Color(245, 245, 245));
        
        painel.add(new JScrollPane(lista), BorderLayout.CENTER);
        
        // Botão para abrir dialog de teste
        JButton btnTestarDialog = new JButton("Testar Dialog com Atalhos");
        btnTestarDialog.addActionListener(e -> abrirDialogTeste());
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBotoes.add(btnTestarDialog);
        painel.add(painelBotoes, BorderLayout.SOUTH);
        
        return painel;
    }
    
    private static void abrirNovoRegistro() {
        // Cria um painel simples para a nova aba
        JPanel painel = new JPanel(new BorderLayout());
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        CampoTexto nome = new CampoTexto("Nome do Registro");
        nome.setObrigatorio(true);
        
        CampoTexto descricao = new CampoTexto("Descrição");
        
        JPanel campos = new JPanel(new GridLayout(2, 1, 5, 5));
        campos.add(nome);
        campos.add(descricao);
        
        painel.add(new JLabel("<html><h2>📝 Novo Registro</h2></html>"), BorderLayout.NORTH);
        painel.add(campos, BorderLayout.CENTER);
        
        // Botão salvar local
        JButton btnSalvar = new JButton("💾 Salvar");
        btnSalvar.setToolTipText(KeyBindingManager.tooltipComAtalho("Salvar este registro", Atalho.SALVAR));
        btnSalvar.addActionListener(e -> {
            if (nome.isValid()) {
                Toast.success(frame, "Registro '" + nome.getValue() + "' salvo!");
            } else {
                nome.mostrarErro();
            }
        });
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.add(btnSalvar);
        painel.add(painelBotoes, BorderLayout.SOUTH);
        
        // Adiciona nova aba
        int numAbas = tabbedPane.getTabCount();
        tabbedPane.adicionarAba("Novo #" + numAbas, painel);
        tabbedPane.setSelectedIndex(tabbedPane.getTabCount() - 1);
    }
    
    private static void abrirDialogTeste() {
        BaseFormularioDialog dialog = new BaseFormularioDialog(frame, "Dialog com Atalhos");
        
        CampoTexto nome = new CampoTexto("Nome");
        nome.setObrigatorio(true);
        
        CampoEmail email = new CampoEmail("E-mail");
        
        dialog.adicionarCampo(nome);
        dialog.adicionarCampo(email);
        dialog.adicionarCampo(new JLabel("<html><br><i>Dica: Use Ctrl+S para confirmar ou ESC para cancelar</i></html>"));
        
        dialog.mostrarDialogo(() -> {
            Toast.success(frame, "Formulário confirmado!");
        });
    }
    
    private static void toggleTelaCheia() {
        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
        if (frame.getExtendedState() == JFrame.MAXIMIZED_BOTH && frame.isUndecorated()) {
            // Sair da tela cheia
            frame.dispose();
            frame.setUndecorated(false);
            frame.setExtendedState(JFrame.NORMAL);
            frame.setVisible(true);
        } else {
            // Entrar em tela cheia
            frame.dispose();
            frame.setUndecorated(true);
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            frame.setVisible(true);
        }
    }
    
    private static void mostrarAjudaAtalhos() {
        StringBuilder sb = new StringBuilder();
        sb.append("<html><body style='padding: 10px; font-family: sans-serif;'>");
        sb.append("<h2>⌨️ Atalhos Disponíveis</h2>");
        sb.append("<table cellpadding='5' cellspacing='0' border='1' style='border-collapse: collapse;'>");
        sb.append("<tr style='background: #f0f0f0;'><th>Atalho</th><th>Ação</th></tr>");
        
        for (Atalho a : Atalho.values()) {
            sb.append("<tr><td><b>").append(a.getTextoTooltip()).append("</b></td>");
            sb.append("<td>").append(a.getDescricao()).append("</td></tr>");
        }
        
        sb.append("</table></body></html>");
        
        JLabel label = new JLabel(sb.toString());
        JScrollPane scroll = new JScrollPane(label);
        scroll.setPreferredSize(new Dimension(400, 400));
        
        JOptionPane.showMessageDialog(frame, scroll, "Ajuda - Atalhos", JOptionPane.INFORMATION_MESSAGE);
    }
}
