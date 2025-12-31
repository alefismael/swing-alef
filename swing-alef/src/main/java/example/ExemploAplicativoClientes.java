package example;

import base.BaseFrame;
import base.BaseCrudPanel;
import base.BaseFormularioDialog;
import base.TabbedDocumentPane;
import base.TabbedDocumentPane.MenuOpcao;
import com.formdev.flatlaf.FlatLightLaf;
import fields.CampoTexto;
import java.awt.BorderLayout;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import ui.DialogUtil;
import ui.PainelTemas;
import ui.Toast;

/**
 * Exemplo de uso da biblioteca Swing Alef.
 * 
 * Este exemplo demonstra como criar uma aplicação com:
 * - BaseFrame (frame principal com F11 para tela cheia)
 * - TabbedDocumentPane (abas fecháveis com busca integrada)
 * - Menu hierárquico com dropdown
 * - BaseCrudPanel (painel com tabela e botões CRUD)
 * - BaseFormularioDialog (diálogo para entrada de dados)
 * - DialogUtil (diálogos em português)
 * - Toast (notificações visuais)
 * 
 * @author alefi
 */
public class ExemploAplicativoClientes {
    
    private static TabbedDocumentPane tabbedPane;
    private static BaseFrame frame;
    
    public static void main(String[] args) {
        // Inicializar FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.err.println("Erro ao inicializar FlatLaf: " + e.getMessage());
        }
        
        SwingUtilities.invokeLater(() -> {
            frame = new BaseFrame("Sistema de Cadastro - Swing Alef");
            frame.setLayout(new BorderLayout());

            // Criar TabbedDocumentPane (abas fecháveis)
            tabbedPane = new TabbedDocumentPane();
            
            // ==================== MENU SIMPLIFICADO ====================
            
            // Menu Cadastro
            MenuOpcao menuCadastro = new MenuOpcao("Cadastro", "📋", Arrays.asList(
                new MenuOpcao("Cliente", "👥", () -> criarPainelClientes()),
                new MenuOpcao("Fornecedor", "🏭", () -> criarPainelFornecedores())
            ));
            
            // Menu Sistema
            MenuOpcao menuSistema = new MenuOpcao("Sistema", "⚙️", Arrays.asList(
                new MenuOpcao("Temas", "🎨", () -> criarPainelTemas()),
                new MenuOpcao("Sobre", "ℹ️", () -> criarPainelSobre())
            ));
            
            // Registrar menus
            tabbedPane.registrarMenu(menuCadastro);
            tabbedPane.registrarMenu(menuSistema);
            
            // ==================== TOOLBAR ====================
            
            JToolBar toolbar = new JToolBar();
            toolbar.setFloatable(false);
            toolbar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
            
            // Campo de busca
            JPanel painelBusca = tabbedPane.criarPainelBusca("Buscar painel...");
            toolbar.add(painelBusca);
            toolbar.addSeparator();
            
            // Botões dropdown
            toolbar.add(tabbedPane.criarBotaoMenu(menuCadastro));
            toolbar.add(tabbedPane.criarBotaoMenu(menuSistema));
            
            frame.add(toolbar, BorderLayout.NORTH);
            frame.add(tabbedPane, BorderLayout.CENTER);

            // Abrir aba inicial (fixa)
            tabbedPane.adicionarAbaFixa("Principal", null, criarPainelPrincipal());
            
            frame.setVisible(true);
        });
    }
    
    /**
     * Cria painel principal/home.
     */
    private static JPanel criarPainelPrincipal() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("<html><center>" +
            "<h1>🏠 Bem-vindo ao Sistema</h1>" +
            "<p style='font-size:14px'>Use os menus <b>Cadastro</b> e <b>Sistema</b> para navegar.</p>" +
            "<br>" +
            "<p style='font-size:12px; color:gray'>Dica: Use o campo de busca ou pressione F11 para tela cheia.</p>" +
            "</center></html>");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
    
    // ==================== PAINEL CLIENTES ====================
    
    /**
     * Cria o painel de gestão de clientes com CRUD completo.
     */
    private static BaseCrudPanel criarPainelClientes() {
        BaseCrudPanel painel = new BaseCrudPanel("Gestão de Clientes");
        painel.definirColunas(new String[]{"ID", "Nome", "Email", "Telefone"});
        
        // Dados de exemplo
        painel.adicionarLinha(new Object[]{1, "João Silva", "joao@email.com", "(11) 98765-4321"});
        painel.adicionarLinha(new Object[]{2, "Maria Santos", "maria@email.com", "(11) 98765-4322"});
        painel.adicionarLinha(new Object[]{3, "Pedro Oliveira", "pedro@email.com", "(11) 98765-4323"});
        
        // Botões CRUD
        painel.adicionarBotao("Novo", () -> novoCliente(painel));
        painel.adicionarBotao("Editar", () -> editarCliente(painel));
        painel.adicionarBotao("Excluir", () -> excluirCliente(painel));
        
        return painel;
    }
    
    private static void novoCliente(BaseCrudPanel painel) {
        BaseFormularioDialog dialog = new BaseFormularioDialog(frame, "Novo Cliente");
        
        CampoTexto campoNome = new CampoTexto("Nome");
        CampoTexto campoEmail = new CampoTexto("Email");
        CampoTexto campoTelefone = new CampoTexto("Telefone");
        
        dialog.adicionarCampo(campoNome);
        dialog.adicionarCampo(campoEmail);
        dialog.adicionarCampo(campoTelefone);
        
        dialog.mostrarDialogo(() -> {
            if (campoNome.isValid() && campoEmail.isValid() && campoTelefone.isValid()) {
                int novoId = painel.obterTabela().obterQuantidadeLinhas() + 1;
                painel.adicionarLinha(new Object[]{
                    novoId,
                    campoNome.getValue(),
                    campoEmail.getValue(),
                    campoTelefone.getValue()
                });
                Toast.success(frame, "Cliente cadastrado com sucesso!");
            } else {
                DialogUtil.aviso(frame, "Preencha todos os campos!");
            }
        });
    }
    
    private static void editarCliente(BaseCrudPanel painel) {
        Object[] dados = painel.obterLinhaAtual();
        if (dados == null) {
            DialogUtil.aviso(frame, "Selecione um cliente para editar!");
            return;
        }
        
        BaseFormularioDialog dialog = new BaseFormularioDialog(frame, "Editar Cliente");
        
        CampoTexto campoNome = new CampoTexto("Nome");
        CampoTexto campoEmail = new CampoTexto("Email");
        CampoTexto campoTelefone = new CampoTexto("Telefone");
        
        // Preencher com dados atuais
        campoNome.setValue((String) dados[1]);
        campoEmail.setValue((String) dados[2]);
        campoTelefone.setValue((String) dados[3]);
        
        dialog.adicionarCampo(campoNome);
        dialog.adicionarCampo(campoEmail);
        dialog.adicionarCampo(campoTelefone);
        
        int linha = painel.obterLinhaSelecionada();
        dialog.mostrarDialogo(() -> {
            if (campoNome.isValid() && campoEmail.isValid() && campoTelefone.isValid()) {
                painel.obterTabela().definirValor(linha, 1, campoNome.getValue());
                painel.obterTabela().definirValor(linha, 2, campoEmail.getValue());
                painel.obterTabela().definirValor(linha, 3, campoTelefone.getValue());
                Toast.success(frame, "Cliente atualizado com sucesso!");
            } else {
                DialogUtil.aviso(frame, "Preencha todos os campos!");
            }
        });
    }
    
    private static void excluirCliente(BaseCrudPanel painel) {
        Object[] dados = painel.obterLinhaAtual();
        if (dados == null) {
            DialogUtil.aviso(frame, "Selecione um cliente para excluir!");
            return;
        }
        
        String nomeCliente = (String) dados[1];
        if (DialogUtil.confirmarExclusao(frame, "Cliente", nomeCliente)) {
            painel.removerLinhaAtual();
            Toast.success(frame, "Cliente excluído com sucesso!");
        }
    }
    
    // ==================== PAINEL FORNECEDORES ====================
    
    /**
     * Cria o painel de gestão de fornecedores com CRUD completo.
     */
    private static BaseCrudPanel criarPainelFornecedores() {
        BaseCrudPanel painel = new BaseCrudPanel("Gestão de Fornecedores");
        painel.definirColunas(new String[]{"ID", "Razão Social", "CNPJ", "Contato"});
        
        // Dados de exemplo
        painel.adicionarLinha(new Object[]{1, "Tech Solutions Ltda", "12.345.678/0001-90", "(11) 3456-7890"});
        painel.adicionarLinha(new Object[]{2, "Global Imports S.A.", "98.765.432/0001-10", "(11) 3456-7891"});
        
        // Botões CRUD
        painel.adicionarBotao("Novo", () -> novoFornecedor(painel));
        painel.adicionarBotao("Editar", () -> editarFornecedor(painel));
        painel.adicionarBotao("Excluir", () -> excluirFornecedor(painel));
        
        return painel;
    }
    
    private static void novoFornecedor(BaseCrudPanel painel) {
        BaseFormularioDialog dialog = new BaseFormularioDialog(frame, "Novo Fornecedor");
        
        CampoTexto campoRazao = new CampoTexto("Razão Social");
        CampoTexto campoCnpj = new CampoTexto("CNPJ");
        CampoTexto campoContato = new CampoTexto("Contato");
        
        dialog.adicionarCampo(campoRazao);
        dialog.adicionarCampo(campoCnpj);
        dialog.adicionarCampo(campoContato);
        
        dialog.mostrarDialogo(() -> {
            if (campoRazao.isValid() && campoCnpj.isValid() && campoContato.isValid()) {
                int novoId = painel.obterTabela().obterQuantidadeLinhas() + 1;
                painel.adicionarLinha(new Object[]{
                    novoId,
                    campoRazao.getValue(),
                    campoCnpj.getValue(),
                    campoContato.getValue()
                });
                Toast.success(frame, "Fornecedor cadastrado com sucesso!");
            } else {
                DialogUtil.aviso(frame, "Preencha todos os campos!");
            }
        });
    }
    
    private static void editarFornecedor(BaseCrudPanel painel) {
        Object[] dados = painel.obterLinhaAtual();
        if (dados == null) {
            DialogUtil.aviso(frame, "Selecione um fornecedor para editar!");
            return;
        }
        
        BaseFormularioDialog dialog = new BaseFormularioDialog(frame, "Editar Fornecedor");
        
        CampoTexto campoRazao = new CampoTexto("Razão Social");
        CampoTexto campoCnpj = new CampoTexto("CNPJ");
        CampoTexto campoContato = new CampoTexto("Contato");
        
        campoRazao.setValue((String) dados[1]);
        campoCnpj.setValue((String) dados[2]);
        campoContato.setValue((String) dados[3]);
        
        dialog.adicionarCampo(campoRazao);
        dialog.adicionarCampo(campoCnpj);
        dialog.adicionarCampo(campoContato);
        
        int linha = painel.obterLinhaSelecionada();
        dialog.mostrarDialogo(() -> {
            if (campoRazao.isValid() && campoCnpj.isValid() && campoContato.isValid()) {
                painel.obterTabela().definirValor(linha, 1, campoRazao.getValue());
                painel.obterTabela().definirValor(linha, 2, campoCnpj.getValue());
                painel.obterTabela().definirValor(linha, 3, campoContato.getValue());
                Toast.success(frame, "Fornecedor atualizado com sucesso!");
            } else {
                DialogUtil.aviso(frame, "Preencha todos os campos!");
            }
        });
    }
    
    private static void excluirFornecedor(BaseCrudPanel painel) {
        Object[] dados = painel.obterLinhaAtual();
        if (dados == null) {
            DialogUtil.aviso(frame, "Selecione um fornecedor para excluir!");
            return;
        }
        
        String razaoSocial = (String) dados[1];
        if (DialogUtil.confirmarExclusao(frame, "Fornecedor", razaoSocial)) {
            painel.removerLinhaAtual();
            Toast.success(frame, "Fornecedor excluído com sucesso!");
        }
    }
    
    // ==================== PAINÉIS DO SISTEMA ====================
    
    /**
     * Cria o painel de seleção de temas.
     */
    private static JPanel criarPainelTemas() {
        return new PainelTemas(frame);
    }
    
    /**
     * Cria o painel Sobre.
     */
    private static JPanel criarPainelSobre() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("<html><center>" +
            "<h2>📚 Swing Alef</h2>" +
            "<p>Biblioteca de componentes Swing em português</p>" +
            "<br>" +
            "<p><b>Recursos:</b></p>" +
            "<p>• BaseFrame com suporte a F11 (tela cheia)</p>" +
            "<p>• TabbedDocumentPane com abas fecháveis e busca</p>" +
            "<p>• BaseCrudPanel para operações CRUD</p>" +
            "<p>• BaseFormularioDialog para formulários</p>" +
            "<p>• DialogUtil para diálogos em português</p>" +
            "<p>• Toast para notificações visuais</p>" +
            "<p>• Compatível com FlatLaf</p>" +
            "<br>" +
            "<p style='color:gray'>Versão 1.0.0</p>" +
            "</center></html>");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }
}
