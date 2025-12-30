package base;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import table.BaseTable;

/**
 * Painel base para operações CRUD.
 * Fornece uma estrutura com barra de ferramentas e tabela.
 * 
 * Uso:
 * <pre>
 * PainelCRUD painel = new PainelCRUD("Clientes");
 * painel.adicionarBotao("Novo", () -> criarNovoCliente());
 * painel.adicionarBotao("Editar", () -> editarClienteSelecionado());
 * painel.adicionarBotao("Deletar", () -> deletarClienteSelecionado());
 * </pre>
 * 
 * @author alefi
 */
public class BaseCrudPanel extends BasePanel {
    
    private final JPanel barraCrud;
    private final BaseTable tabela;
    private final BaseLabel labelTitulo;
    private final BaseLabel labelStatus;
    private final JScrollPane scrollPane;
    
    public BaseCrudPanel(String titulo) {
        super();
        
        // === TÍTULO ===
        labelTitulo = new BaseLabel(titulo);
        labelTitulo.setFont(labelTitulo.getFont().deriveFont(java.awt.Font.BOLD, 18.0f));

        // === BARRA DE CONTROLES (filtro à esquerda, botões à direita) ===
        barraCrud = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        barraCrud.setOpaque(false);
        
        // Campo de filtro
        BaseTextField filtroField = new BaseTextField(20);
        filtroField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            private void update() {
                String texto = filtroField.getText();
                tabela.filtrar(texto);
                atualizarStatus();
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { update(); }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { update(); }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { update(); }
        });
        
        // Container de controles com borda - responsivo
        JPanel containerControles = new JPanel(new BorderLayout(8, 0));
        containerControles.setOpaque(true);
        containerControles.setBackground(new java.awt.Color(250, 250, 250));
        containerControles.setBorder(javax.swing.BorderFactory.createCompoundBorder(
            javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 215, 215), 1),
            javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        
        // Filtro à esquerda
        JPanel pnlFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        pnlFiltro.setOpaque(false);
        pnlFiltro.add(new BaseLabel("🔍 Filtrar:"));
        pnlFiltro.add(filtroField);
        
        // Botões com tamanho mínimo garantido
        barraCrud.setMinimumSize(new java.awt.Dimension(280, 30));
        
        containerControles.add(pnlFiltro, BorderLayout.CENTER);
        containerControles.add(barraCrud, BorderLayout.EAST);
        
        // === TABELA ===
        tabela = new BaseTable();
        scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(215, 215, 215), 1));
        
        // Status bar (rodapé)
        labelStatus = new BaseLabel("0 registros");
        labelStatus.setFont(labelStatus.getFont().deriveFont(11.0f));
        JPanel pnlStatus = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 2));
        pnlStatus.setOpaque(false);
        pnlStatus.add(labelStatus);
        
        // === LAYOUT ===
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 8, 0);
        getContent().add(labelTitulo, gbc);
        
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 8, 0);
        getContent().add(containerControles, gbc);

        // Tabela ocupa todo o espaço disponível
        gbc.gridy = 2;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 4, 0);
        getContent().add(scrollPane, gbc);
        
        // Status bar no rodapé
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0);
        getContent().add(pnlStatus, gbc);
    }
    
    /**
     * Atualiza o label de status com contagem de registros.
     */
    public void atualizarStatus() {
        int total = tabela.getModel().getRowCount();
        int visiveis = tabela.getRowCount();
        if (total == visiveis) {
            labelStatus.setText(total + " registro" + (total != 1 ? "s" : ""));
        } else {
            labelStatus.setText(visiveis + " de " + total + " registros (filtrado)");
        }
    }
    
    /**
     * Adiciona um botão à barra CRUD.
     * 
     * @param texto texto do botão
     * @param acao ação a executar ao clicar
     */
    public void adicionarBotao(String texto, Runnable acao) {
        BaseButton botao = new BaseButton(texto);
        botao.addActionListener(e -> acao.run());
        botao.setFocusPainted(false);
        botao.setPreferredSize(new java.awt.Dimension(90, 30));
        
        barraCrud.add(botao);
        barraCrud.revalidate();
        barraCrud.repaint();
    }
    
    /**
     * Obtém a tabela base.
     * 
     * @return BaseTable
     */
    public BaseTable obterTabela() {
        return tabela;
    }

    /**
     * Aplica filtro de texto na tabela deste painel.
     * @param texto texto do filtro (vazio remove filtro)
     */
    public void filtrar(String texto) {
        tabela.filtrar(texto);
    }
    
    /**
     * Define as colunas da tabela.
     * 
     * @param colunas array com nomes das colunas
     */
    public void definirColunas(String[] colunas) {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setColumnIdentifiers(colunas);
    }
    
    /**
     * Limpa todos os dados da tabela.
     */
    public void limparTabela() {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.setRowCount(0);
        atualizarStatus();
    }
    
    /**
     * Adiciona uma linha à tabela.
     * 
     * @param dados array de dados para a linha
     */
    public void adicionarLinha(Object[] dados) {
        DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
        modelo.addRow(dados);
        atualizarStatus();
    }
    
    /**
     * Remove a linha selecionada.
     * 
     * @return true se removeu, false se nenhuma linha selecionada
     */
    public boolean removerLinhaAtual() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
            modelo.removeRow(linhaSelecionada);
            atualizarStatus();
            return true;
        }
        return false;
    }
    
    /**
     * Obtém os dados da linha selecionada.
     * 
     * @return array de objetos ou null se nenhuma linha selecionada
     */
    public Object[] obterLinhaAtual() {
        int linhaSelecionada = tabela.getSelectedRow();
        if (linhaSelecionada >= 0) {
            DefaultTableModel modelo = (DefaultTableModel) tabela.getModel();
            Object[] dados = new Object[modelo.getColumnCount()];
            for (int i = 0; i < modelo.getColumnCount(); i++) {
                dados[i] = modelo.getValueAt(linhaSelecionada, i);
            }
            return dados;
        }
        return null;
    }
    
    /**
     * Obtém o índice da linha selecionada.
     * 
     * @return índice da linha ou -1 se nenhuma selecionada
     */
    public int obterLinhaSelecionada() {
        return tabela.getSelectedRow();
    }
    
    /**
     * Define o título do painel.
     * 
     * @param titulo novo título
     */
    public void definirTitulo(String titulo) {
        labelTitulo.setText(titulo);
    }
}
