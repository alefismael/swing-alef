package example;

import crud.CrudPanel;
import crud.api.CrudOperations;
import crud.api.CrudFormFactory;
import crud.api.TableRowMapper;
import base.TabbedFrame;
import fields.CampoTexto;
import fields.CampoEmail;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Exemplo de uso do CrudPanel genérico com as interfaces.
 * 
 * Demonstra:
 * - Implementação de CrudOperations (repositório em memória)
 * - Implementação de CrudFormFactory (formulário de cliente)
 * - Implementação de TableRowMapper (mapeamento para tabela)
 * - Uso do CrudPanel integrado com TabbedFrame
 * 
 * @author alefi
 */
public class ExemploCrudGenerico {
    
    public static void main(String[] args) {
        // Configura Look and Feel
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception e) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        SwingUtilities.invokeLater(ExemploCrudGenerico::criarInterface);
    }
    
    private static void criarInterface() {
        TabbedFrame frame = new TabbedFrame("Sistema com CRUD Genérico");
        
        // Cria repositório em memória
        ClienteRepository repository = new ClienteRepository();
        
        // Adiciona alguns clientes iniciais
        repository.criar(new Cliente(null, "João Silva", "joao@email.com", "(11) 99999-1111"));
        repository.criar(new Cliente(null, "Maria Santos", "maria@email.com", "(11) 99999-2222"));
        repository.criar(new Cliente(null, "Pedro Oliveira", "pedro@email.com", "(11) 99999-3333"));
        repository.criar(new Cliente(null, "Ana Costa", "ana@email.com", "(11) 99999-4444"));
        
        // Cria o painel CRUD
        CrudPanel<Cliente, Long> crudPanel = new CrudPanel<>(
            "Clientes",
            repository,
            new ClienteFormFactory(),
            new ClienteTableMapper()
        );
        
        // Carrega dados iniciais
        crudPanel.recarregar();
        
        // Callback quando seleção muda
        crudPanel.setOnSelectionChanged(cliente -> {
            if (cliente != null) {
                frame.setStatus("Selecionado: " + cliente.getNome());
            } else {
                frame.setStatus("Nenhum cliente selecionado");
            }
        });
        
        // Adiciona botão customizado
        crudPanel.adicionarBotao("📧 Enviar Email", () -> {
            Cliente cliente = crudPanel.getEntidadeSelecionada();
            if (cliente != null) {
                JOptionPane.showMessageDialog(frame, 
                    "Email enviado para: " + cliente.getEmail(),
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, 
                    "Selecione um cliente primeiro",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });
        
        // Adiciona ao frame
        frame.adicionarAbaFixa("👥 Clientes", null, crudPanel);
        
        // Painel de instruções
        frame.adicionarAba("📖 Instruções", null, criarPainelInstrucoes());
        
        frame.setVisible(true);
    }
    
    private static JPanel criarPainelInstrucoes() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JTextArea texto = new JTextArea();
        texto.setEditable(false);
        texto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        texto.setText("""
            EXEMPLO DE CRUD GENÉRICO
            ========================
            
            Este exemplo demonstra o uso das novas interfaces genéricas:
            
            📌 CrudOperations<T, ID>
               Interface para operações de persistência (criar, atualizar, deletar, buscar)
            
            📌 CrudFormFactory<T>
               Factory para criar e gerenciar formulários
            
            📌 TableRowMapper<T>
               Mapeador de entidade para linha de tabela
            
            📌 CrudPanel<T, ID>
               Painel completo que integra tudo
            
            ATALHOS DISPONÍVEIS:
            • Insert - Criar novo registro
            • Enter - Editar registro selecionado
            • Delete - Remover registro selecionado
            • F5 - Atualizar lista
            • Double-click - Editar registro
            • Clique direito - Menu de contexto
            
            ESTRUTURA DO CÓDIGO:
            
            // 1. Criar repositório (implementa CrudOperations)
            ClienteRepository repository = new ClienteRepository();
            
            // 2. Criar o painel CRUD
            CrudPanel<Cliente, Long> panel = new CrudPanel<>(
                "Clientes",
                repository,              // CrudOperations
                new ClienteFormFactory(), // CrudFormFactory
                new ClienteTableMapper()  // TableRowMapper
            );
            
            // 3. Carregar dados
            panel.recarregar();
            """);
        
        panel.add(new JScrollPane(texto), BorderLayout.CENTER);
        return panel;
    }
    
    // ==================== ENTIDADE ====================
    
    /**
     * Entidade Cliente simples.
     */
    public static class Cliente {
        private Long id;
        private String nome;
        private String email;
        private String telefone;
        
        public Cliente(Long id, String nome, String email, String telefone) {
            this.id = id;
            this.nome = nome;
            this.email = email;
            this.telefone = telefone;
        }
        
        // Getters e Setters
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getTelefone() { return telefone; }
        public void setTelefone(String telefone) { this.telefone = telefone; }
    }
    
    // ==================== REPOSITÓRIO ====================
    
    /**
     * Implementação de CrudOperations em memória.
     * Em uma aplicação real, usaria banco de dados.
     */
    public static class ClienteRepository implements CrudOperations<Cliente, Long> {
        
        private final Map<Long, Cliente> dados = new HashMap<>();
        private long proximoId = 1;
        
        @Override
        public Cliente criar(Cliente entity) {
            entity.setId(proximoId++);
            dados.put(entity.getId(), entity);
            return entity;
        }
        
        @Override
        public Cliente atualizar(Long id, Cliente entity) {
            entity.setId(id);
            dados.put(id, entity);
            return entity;
        }
        
        @Override
        public boolean deletar(Long id) {
            return dados.remove(id) != null;
        }
        
        @Override
        public Optional<Cliente> buscarPorId(Long id) {
            return Optional.ofNullable(dados.get(id));
        }
        
        @Override
        public List<Cliente> listarTodos() {
            return new ArrayList<>(dados.values());
        }
    }
    
    // ==================== FORM FACTORY ====================
    
    /**
     * Factory para formulário de Cliente.
     */
    public static class ClienteFormFactory implements CrudFormFactory<Cliente> {
        
        @Override
        public JPanel criarFormulario() {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(8, 8, 8, 8);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1;
            gbc.gridx = 0;
            
            CampoTexto nome = new CampoTexto("Nome");
            nome.setName("nome");
            gbc.gridy = 0;
            panel.add(nome, gbc);
            
            CampoEmail email = new CampoEmail("E-mail");
            email.setName("email");
            gbc.gridy = 1;
            panel.add(email, gbc);
            
            CampoTexto telefone = new CampoTexto("Telefone");
            telefone.setName("telefone");
            gbc.gridy = 2;
            panel.add(telefone, gbc);
            
            panel.setPreferredSize(new Dimension(350, 200));
            return panel;
        }
        
        @Override
        public void preencherFormulario(JPanel formulario, Cliente entity) {
            for (Component comp : formulario.getComponents()) {
                if (comp instanceof CampoTexto campo) {
                    switch (campo.getName()) {
                        case "nome" -> campo.setValue(entity.getNome());
                        case "telefone" -> campo.setValue(entity.getTelefone());
                    }
                } else if (comp instanceof CampoEmail campo && "email".equals(comp.getName())) {
                    campo.setValue(entity.getEmail());
                }
            }
        }
        
        @Override
        public Cliente extrairDados(JPanel formulario) {
            String nome = "";
            String email = "";
            String telefone = "";
            
            for (Component comp : formulario.getComponents()) {
                if (comp instanceof CampoTexto campo) {
                    switch (campo.getName()) {
                        case "nome" -> nome = campo.getValue();
                        case "telefone" -> telefone = campo.getValue();
                    }
                } else if (comp instanceof CampoEmail campo && "email".equals(comp.getName())) {
                    email = campo.getValue();
                }
            }
            
            return new Cliente(null, nome, email, telefone);
        }
        
        @Override
        public boolean validar(JPanel formulario) {
            for (Component comp : formulario.getComponents()) {
                if (comp instanceof CampoTexto campo && "nome".equals(campo.getName())) {
                    if (!campo.isValid()) return false;
                }
                if (comp instanceof CampoEmail campo && "email".equals(comp.getName())) {
                    if (!campo.isValid()) return false;
                }
            }
            return true;
        }
        
        @Override
        public String getMensagemErro(JPanel formulario) {
            for (Component comp : formulario.getComponents()) {
                if (comp instanceof CampoTexto campo && "nome".equals(campo.getName())) {
                    if (!campo.isValid()) return "Nome é obrigatório";
                }
                if (comp instanceof CampoEmail campo && "email".equals(comp.getName())) {
                    if (!campo.isValid()) return "E-mail inválido";
                }
            }
            return null;
        }
    }
    
    // ==================== TABLE MAPPER ====================
    
    /**
     * Mapeador de Cliente para tabela.
     */
    public static class ClienteTableMapper implements TableRowMapper<Cliente> {
        
        @Override
        public String[] getColunas() {
            return new String[]{"ID", "Nome", "E-mail", "Telefone"};
        }
        
        @Override
        public Object[] toRowData(Cliente entity) {
            return new Object[]{
                entity.getId(),
                entity.getNome(),
                entity.getEmail(),
                entity.getTelefone()
            };
        }
        
        @Override
        public Object getId(Cliente entity) {
            return entity.getId();
        }
        
        @Override
        public int[] getColumnWidths() {
            return new int[]{50, 150, 200, 120};
        }
    }
}
