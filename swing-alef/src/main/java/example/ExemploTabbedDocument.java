package example;

import base.TabbedFrame;
import base.TabbedDocumentPane;
import base.BaseCrudPanel;
import base.BasePanel;
import base.BaseButton;
import base.BaseLabel;
import fields.CampoTexto;
import fields.CampoEmail;
import ui.Toast;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Exemplo demonstrando o uso do TabbedDocumentPane.
 * 
 * Características demonstradas:
 * - Múltiplas abas abertas simultaneamente
 * - Abas fecháveis com botão X
 * - Indicador de modificações (•)
 * - Menu de contexto nas abas
 * - Atalhos de teclado (Ctrl+W, Ctrl+Tab)
 * - Confirmação ao fechar com modificações
 * 
 * @author alefi
 */
public class ExemploTabbedDocument {
    
    private static TabbedFrame frame;
    private static int contadorClientes = 0;
    private static int contadorProdutos = 0;
    
    public static void main(String[] args) {
        // Configura Look and Feel (FlatLaf recomendado)
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        SwingUtilities.invokeLater(ExemploTabbedDocument::criarInterface);
    }
    
    private static void criarInterface() {
        // Cria o frame principal
        frame = new TabbedFrame("Sistema de Gestão - Exemplo TabbedDocumentPane") {
            @Override
            protected void onNovaAba() {
                // Ctrl+N abre novo cliente por padrão
                abrirNovoCliente();
            }
        };
        
        // Adiciona toolbar no header
        frame.getHeaderPanel().add(criarToolbar(), BorderLayout.CENTER);
        
        // Adiciona aba inicial (fixa - home)
        frame.adicionarAbaFixa("🏠 Home", null, criarPainelHome());
        
        // Configura callback quando aba é fechada
        frame.getTabbedPane().setOnTabClosed(comp -> {
            System.out.println("Aba fechada: " + comp.getClass().getSimpleName());
        });
        
        frame.setVisible(true);
    }
    
    private static JToolBar criarToolbar() {
        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);
        toolbar.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        
        // Botão Novo Cliente
        JButton btnCliente = new JButton("👤 Novo Cliente");
        btnCliente.addActionListener(e -> abrirNovoCliente());
        toolbar.add(btnCliente);
        
        toolbar.addSeparator();
        
        // Botão Novo Produto
        JButton btnProduto = new JButton("📦 Novo Produto");
        btnProduto.addActionListener(e -> abrirNovoProduto());
        toolbar.add(btnProduto);
        
        toolbar.addSeparator();
        
        // Botão Lista de Clientes
        JButton btnListaClientes = new JButton("📋 Lista Clientes");
        btnListaClientes.addActionListener(e -> abrirListaClientes());
        toolbar.add(btnListaClientes);
        
        toolbar.add(Box.createHorizontalGlue());
        
        // Informação de atalhos
        JLabel lblAtalhos = new JLabel("Ctrl+W: Fechar | Ctrl+Tab: Próxima | Ctrl+N: Novo");
        lblAtalhos.setForeground(Color.GRAY);
        lblAtalhos.setFont(lblAtalhos.getFont().deriveFont(11f));
        toolbar.add(lblAtalhos);
        
        return toolbar;
    }
    
    private static JPanel criarPainelHome() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        
        // Conteúdo central
        JPanel centro = new JPanel();
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        
        JLabel titulo = new JLabel("Bem-vindo ao Sistema");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        centro.add(titulo);
        
        centro.add(Box.createVerticalStrut(20));
        
        JLabel subtitulo = new JLabel("Demonstração do TabbedDocumentPane");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitulo.setForeground(Color.GRAY);
        subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        centro.add(subtitulo);
        
        centro.add(Box.createVerticalStrut(40));
        
        // Instruções
        String[] instrucoes = {
            "• Clique nos botões da toolbar para abrir novas abas",
            "• Cada aba pode ser fechada com o botão X ou Ctrl+W",
            "• Abas com modificações mostram um indicador (•)",
            "• Clique com botão direito nas abas para menu de contexto",
            "• Use Ctrl+Tab para navegar entre abas",
            "• A aba Home é fixa e não pode ser fechada"
        };
        
        for (String instrucao : instrucoes) {
            JLabel lbl = new JLabel(instrucao);
            lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            centro.add(lbl);
            centro.add(Box.createVerticalStrut(8));
        }
        
        panel.add(centro, BorderLayout.CENTER);
        
        // Botões de ação rápida
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        
        JButton btnCliente = new JButton("Cadastrar Cliente");
        btnCliente.setPreferredSize(new Dimension(180, 40));
        btnCliente.addActionListener(e -> abrirNovoCliente());
        botoesPanel.add(btnCliente);
        
        JButton btnProduto = new JButton("Cadastrar Produto");
        btnProduto.setPreferredSize(new Dimension(180, 40));
        btnProduto.addActionListener(e -> abrirNovoProduto());
        botoesPanel.add(btnProduto);
        
        panel.add(botoesPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private static void abrirNovoCliente() {
        contadorClientes++;
        String titulo = "Cliente #" + contadorClientes;
        
        JPanel painelCliente = criarFormularioCliente(titulo);
        frame.adicionarAba(titulo, null, painelCliente);
        
        Toast.show(frame.getRootPane(), "Nova aba de cliente aberta");
    }
    
    private static void abrirNovoProduto() {
        contadorProdutos++;
        String titulo = "Produto #" + contadorProdutos;
        
        JPanel painelProduto = criarFormularioProduto(titulo);
        frame.adicionarAba(titulo, null, painelProduto);
        
        Toast.show(frame.getRootPane(), "Nova aba de produto aberta");
    }
    
    private static void abrirListaClientes() {
        // Verifica se já existe uma aba de lista
        TabbedDocumentPane tabs = frame.getTabbedPane();
        for (int i = 0; i < tabs.getTabCount(); i++) {
            if (tabs.getTitleAt(i).equals("📋 Clientes")) {
                tabs.setSelectedIndex(i);
                return;
            }
        }
        
        // Cria nova lista
        BaseCrudPanel lista = new BaseCrudPanel("Lista de Clientes");
        lista.definirColunas(new String[]{"ID", "Nome", "Email", "Telefone"});
        lista.adicionarLinha(new Object[]{1, "João Silva", "joao@email.com", "(11) 99999-1111"});
        lista.adicionarLinha(new Object[]{2, "Maria Santos", "maria@email.com", "(11) 99999-2222"});
        lista.adicionarLinha(new Object[]{3, "Pedro Oliveira", "pedro@email.com", "(11) 99999-3333"});
        
        lista.adicionarBotao("Novo", ExemploTabbedDocument::abrirNovoCliente);
        lista.adicionarBotao("Editar", () -> {
            Object[] dados = lista.obterLinhaAtual();
            if (dados != null) {
                abrirEdicaoCliente((Integer) dados[0], (String) dados[1]);
            }
        });
        
        frame.adicionarAba("📋 Clientes", null, lista);
    }
    
    private static void abrirEdicaoCliente(int id, String nome) {
        String titulo = "Editar: " + nome;
        JPanel painel = criarFormularioCliente(titulo);
        
        // Preenche com dados existentes (simulado)
        // Em uma aplicação real, buscaria do banco
        
        frame.adicionarAba(titulo, null, painel);
    }
    
    private static JPanel criarFormularioCliente(String titulo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título do formulário
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        // Campos
        JPanel campos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        
        CampoTexto campoNome = new CampoTexto("Nome");
        CampoEmail campoEmail = new CampoEmail("E-mail");
        CampoTexto campoTelefone = new CampoTexto("Telefone");
        CampoTexto campoEndereco = new CampoTexto("Endereço");
        
        gbc.gridx = 0; gbc.gridy = 0;
        campos.add(campoNome, gbc);
        
        gbc.gridy = 1;
        campos.add(campoEmail, gbc);
        
        gbc.gridy = 2;
        campos.add(campoTelefone, gbc);
        
        gbc.gridy = 3;
        campos.add(campoEndereco, gbc);
        
        // Espaço extra
        gbc.gridy = 4;
        gbc.weighty = 1;
        campos.add(new JPanel(), gbc);
        
        panel.add(campos, BorderLayout.CENTER);
        
        // Botões
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            // Marca como não modificado após salvar
            frame.getTabbedPane().marcarModificado(panel, false);
            Toast.show(frame.getRootPane(), "Cliente salvo com sucesso!");
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> {
            int index = frame.getTabbedPane().indexOfComponent(panel);
            if (index >= 0) {
                frame.getTabbedPane().fecharAba(index);
            }
        });
        
        botoesPanel.add(btnCancelar);
        botoesPanel.add(btnSalvar);
        panel.add(botoesPanel, BorderLayout.SOUTH);
        
        // Marca como modificado quando digitar algo
        adicionarListenerModificacao(panel, campoNome.getField(), campoEmail.getField(), 
                                     campoTelefone.getField(), campoEndereco.getField());
        
        return panel;
    }
    
    private static JPanel criarFormularioProduto(String titulo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Título
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);
        
        // Campos
        JPanel campos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        
        CampoTexto campoDescricao = new CampoTexto("Descrição");
        CampoTexto campoPreco = new CampoTexto("Preço");
        CampoTexto campoEstoque = new CampoTexto("Estoque");
        CampoTexto campoCategoria = new CampoTexto("Categoria");
        
        gbc.gridx = 0; gbc.gridy = 0;
        campos.add(campoDescricao, gbc);
        
        gbc.gridy = 1;
        campos.add(campoPreco, gbc);
        
        gbc.gridy = 2;
        campos.add(campoEstoque, gbc);
        
        gbc.gridy = 3;
        campos.add(campoCategoria, gbc);
        
        gbc.gridy = 4;
        gbc.weighty = 1;
        campos.add(new JPanel(), gbc);
        
        panel.add(campos, BorderLayout.CENTER);
        
        // Botões
        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> {
            frame.getTabbedPane().marcarModificado(panel, false);
            Toast.show(frame.getRootPane(), "Produto salvo com sucesso!");
        });
        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> {
            int index = frame.getTabbedPane().indexOfComponent(panel);
            if (index >= 0) {
                frame.getTabbedPane().fecharAba(index);
            }
        });
        
        botoesPanel.add(btnCancelar);
        botoesPanel.add(btnSalvar);
        panel.add(botoesPanel, BorderLayout.SOUTH);
        
        // Listener de modificação
        adicionarListenerModificacao(panel, campoDescricao.getField(), campoPreco.getField(), 
                                     campoEstoque.getField(), campoCategoria.getField());
        
        return panel;
    }
    
    private static void adicionarListenerModificacao(JPanel panel, JTextField... campos) {
        KeyAdapter keyListener = new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                // Marca a aba como modificada quando o usuário digita
                frame.getTabbedPane().marcarModificado(panel, true);
            }
        };
        
        for (JTextField campo : campos) {
            campo.addKeyListener(keyListener);
        }
    }
}
