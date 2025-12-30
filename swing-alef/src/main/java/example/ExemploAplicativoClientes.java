package example;

import base.BaseFrame;
import base.BaseCrudPanel;
import base.BaseFormularioDialog;
import components.CampoTexto;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Exemplo de uso da biblioteca Swing Components KetSoft.
 * 
 * Este exemplo demonstra como criar uma aplicação simples com:
 * - BaseFrame (frame principal com F11 para tela cheia)
 * - BaseCrudPanel (painel com tabela e botões)
 * - BaseFormularioDialog (diálogo para entrada de dados)
 * - Campos de formulário (CampoTexto)
 * 
 * @author alefi
 */
public class ExemploAplicativoClientes {
    
    private static CardLayout cardLayout;
    private static JPanel paineisContainer;
    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(() -> {
            BaseFrame frame = new BaseFrame("Aplicativo de Cadastro - KetSoft");
            frame.setLayout(new BorderLayout());

            // CardLayout para alternar painéis
            cardLayout = new CardLayout();
            paineisContainer = new JPanel(cardLayout);

            // Criar painéis de exemplo
            BaseCrudPanel painelClientes = new BaseCrudPanel("Gestão de Clientes");
            painelClientes.definirColunas(new String[]{"ID", "Nome", "Email", "Telefone"});
            painelClientes.adicionarLinha(new Object[]{1, "João Silva", "joao@email.com", "(11) 98765-4321"});
            painelClientes.adicionarLinha(new Object[]{2, "Maria Santos", "maria@email.com", "(11) 98765-4322"});
            painelClientes.adicionarLinha(new Object[]{3, "Pedro Oliveira", "pedro@email.com", "(11) 98765-4323"});
            painelClientes.adicionarBotao("Novo", () -> abrirDialogoNovoCliente(frame, painelClientes));
            painelClientes.adicionarBotao("Editar", () -> abrirDialogoEditarCliente(frame, painelClientes));
            painelClientes.adicionarBotao("Deletar", () -> deletarClienteSelecionado(painelClientes));

            BaseCrudPanel painelFilmes = new BaseCrudPanel("Gestão de Filmes");
            painelFilmes.definirColunas(new String[]{"ID", "Título", "Diretor", "Ano"});
            painelFilmes.adicionarLinha(new Object[]{1, "Matrix", "Wachowski", 1999});
            painelFilmes.adicionarLinha(new Object[]{2, "Inception", "Nolan", 2010});
            painelFilmes.adicionarBotao("Novo", () -> {});
            painelFilmes.adicionarBotao("Editar", () -> {});
            painelFilmes.adicionarBotao("Deletar", () -> {});

            // Adicionar painéis ao container
            paineisContainer.add(painelClientes, "clientes");
            paineisContainer.add(painelFilmes, "filmes");

            // Criar e adicionar NavigationBar
            base.BaseNavigationBar nav = new base.BaseNavigationBar();
            nav.addTab("clientes", "Clientes", () -> exibirPainel("clientes"));
            nav.addTab("filmes", "Filmes", () -> exibirPainel("filmes"));
            
            frame.add(nav, BorderLayout.NORTH);
            frame.add(paineisContainer, BorderLayout.CENTER);

            // Exibir painel inicial
            exibirPainel("clientes");
            frame.setVisible(true);
        });
    }
    
    private static void exibirPainel(String id) {
        cardLayout.show(paineisContainer, id);
    }
    
    /**
     * Abre um diálogo para criar novo cliente.
     */
    private static void abrirDialogoNovoCliente(BaseFrame frame, BaseCrudPanel painel) {
        BaseFormularioDialog dialog = new BaseFormularioDialog(frame, "Novo Cliente");
        
        CampoTexto campoNome = new CampoTexto("Nome");
        CampoTexto campoEmail = new CampoTexto("Email");
        CampoTexto campoTelefone = new CampoTexto("Telefone");
        
        dialog.adicionarCampo(campoNome);
        dialog.adicionarCampo(campoEmail);
        dialog.adicionarCampo(campoTelefone);
        
        dialog.mostrarDialogo(() -> {
            if (campoNome.isValido() && campoEmail.isValido() && campoTelefone.isValido()) {
                int novoId = painel.obterTabela().obterQuantidadeLinhas() + 1;
                painel.adicionarLinha(new Object[]{
                    novoId,
                    campoNome.getValue(),
                    campoEmail.getValue(),
                    campoTelefone.getValue()
                });
                System.out.println("Cliente adicionado com sucesso!");
            } else {
                System.out.println("Preencha todos os campos!");
            }
        });
    }
    
    /**
     * Abre um diálogo para editar cliente selecionado.
     */
    private static void abrirDialogoEditarCliente(BaseFrame frame, BaseCrudPanel painel) {
        Object[] dadosSelecionados = painel.obterLinhaAtual();
        if (dadosSelecionados == null) {
            System.out.println("Selecione um cliente para editar!");
            return;
        }
        
        BaseFormularioDialog dialog = new BaseFormularioDialog(frame, "Editar Cliente");
        
        CampoTexto campoNome = new CampoTexto("Nome");
        CampoTexto campoEmail = new CampoTexto("Email");
        CampoTexto campoTelefone = new CampoTexto("Telefone");
        
        // Preencher com dados atuais
        campoNome.setValue((String) dadosSelecionados[1]);
        campoEmail.setValue((String) dadosSelecionados[2]);
        campoTelefone.setValue((String) dadosSelecionados[3]);
        
        dialog.adicionarCampo(campoNome);
        dialog.adicionarCampo(campoEmail);
        dialog.adicionarCampo(campoTelefone);
        
        int linhaAtual = painel.obterLinhaSelecionada();
        dialog.mostrarDialogo(() -> {
            if (campoNome.isValido() && campoEmail.isValido() && campoTelefone.isValido()) {
                painel.obterTabela().definirValor(linhaAtual, 1, campoNome.getValue());
                painel.obterTabela().definirValor(linhaAtual, 2, campoEmail.getValue());
                painel.obterTabela().definirValor(linhaAtual, 3, campoTelefone.getValue());
                System.out.println("Cliente atualizado com sucesso!");
            } else {
                System.out.println("Preencha todos os campos!");
            }
        });
    }
    
    /**
     * Deleta o cliente selecionado.
     */
    private static void deletarClienteSelecionado(BaseCrudPanel painel) {
        if (painel.removerLinhaAtual()) {
            System.out.println("Cliente removido com sucesso!");
        } else {
            System.out.println("Selecione um cliente para deletar!");
        }
    }
}
