package crud;

import crud.api.CrudOperations;
import crud.api.CrudFormFactory;
import crud.api.TableRowMapper;
import base.BasePanel;
import base.BaseButton;
import base.BaseTextField;
import table.BaseTable;
import ui.DialogUtil;
import ui.Toast;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Painel CRUD genérico completo com tipagem forte.
 * 
 * Integra CrudOperations, CrudFormFactory e TableRowMapper para
 * criar um painel CRUD funcional com mínimo de código.
 * 
 * Características:
 * - Toolbar com botões Novo, Editar, Deletar
 * - Menu de contexto (clique direito)
 * - Double-click para editar
 * - Atalhos de teclado (Delete, Insert, Enter)
 * - Filtro de busca
 * - Indicador de total de registros
 * 
 * Exemplo de uso:
 * <pre>
 * // Criar o painel
 * CrudPanel&lt;Cliente, Long&gt; panel = new CrudPanel&lt;&gt;(
 *     "Gestão de Clientes",
 *     clienteRepository,      // CrudOperations
 *     clienteFormFactory,     // CrudFormFactory
 *     clienteTableMapper      // TableRowMapper
 * );
 * 
 * // Adicionar ao frame
 * frame.add(panel);
 * 
 * // Carregar dados
 * panel.recarregar();
 * </pre>
 * 
 * @param <T> Tipo da entidade
 * @param <ID> Tipo do identificador
 * @author alefi
 */
public class CrudPanel<T, ID> extends BasePanel {
    
    private final String titulo;
    private final CrudOperations<T, ID> operations;
    private final CrudFormFactory<T> formFactory;
    private final TableRowMapper<T> tableMapper;
    
    private final BaseTable tabela;
    private final DefaultTableModel tableModel;
    private final JLabel labelTitulo;
    private final JLabel labelStatus;
    private final BaseTextField campoFiltro;
    private final JPanel toolbar;
    
    private Consumer<T> onSelectionChanged;
    private Consumer<T> onDoubleClick;
    
    /**
     * Cria um novo painel CRUD.
     * 
     * @param titulo Título do painel
     * @param operations Implementação de CrudOperations
     * @param formFactory Factory para formulários
     * @param tableMapper Mapeador entidade-tabela
     */
    public CrudPanel(String titulo, 
                     CrudOperations<T, ID> operations,
                     CrudFormFactory<T> formFactory,
                     TableRowMapper<T> tableMapper) {
        super();
        this.titulo = titulo;
        this.operations = operations;
        this.formFactory = formFactory;
        this.tableMapper = tableMapper;
        
        // Inicializa componentes
        this.tabela = new BaseTable();
        this.tableModel = new DefaultTableModel(tableMapper.getColunas(), 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return tableMapper.isColumnEditable(column);
            }
        };
        this.labelTitulo = new JLabel(titulo);
        this.labelStatus = new JLabel("0 registros");
        this.campoFiltro = new BaseTextField(20);
        this.toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        
        configurarLayout();
        configurarTabela();
        configurarToolbar();
        configurarEventos();
        configurarAtalhos();
    }
    
    private void configurarLayout() {
        setLayout(new BorderLayout(0, 8));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        
        // Header: título + filtro
        JPanel header = new JPanel(new BorderLayout(12, 0));
        header.setOpaque(false);
        
        labelTitulo.setFont(labelTitulo.getFont().deriveFont(Font.BOLD, 18f));
        header.add(labelTitulo, BorderLayout.WEST);
        
        // Filtro
        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 0));
        filtroPanel.setOpaque(false);
        filtroPanel.add(new JLabel("🔍"));
        filtroPanel.add(campoFiltro);
        header.add(filtroPanel, BorderLayout.EAST);
        
        add(header, BorderLayout.NORTH);
        
        // Centro: toolbar + tabela
        JPanel centro = new JPanel(new BorderLayout(0, 8));
        centro.setOpaque(false);
        
        toolbar.setOpaque(false);
        centro.add(toolbar, BorderLayout.NORTH);
        
        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Component.borderColor")));
        centro.add(scroll, BorderLayout.CENTER);
        
        add(centro, BorderLayout.CENTER);
        
        // Footer: status
        JPanel footer = new JPanel(new BorderLayout());
        footer.setOpaque(false);
        footer.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        labelStatus.setForeground(Color.GRAY);
        footer.add(labelStatus, BorderLayout.WEST);
        
        add(footer, BorderLayout.SOUTH);
    }
    
    private void configurarTabela() {
        tabela.setModel(tableModel);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.getTableHeader().setReorderingAllowed(false);
        
        // Configura larguras das colunas se definidas
        int[] widths = tableMapper.getColumnWidths();
        if (widths != null) {
            for (int i = 0; i < widths.length && i < tabela.getColumnCount(); i++) {
                tabela.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
            }
        }
    }
    
    private void configurarToolbar() {
        // Botão Novo
        BaseButton btnNovo = new BaseButton("➕ Novo");
        btnNovo.setToolTipText("Criar novo registro (Insert)");
        btnNovo.addActionListener(e -> abrirDialogoNovo());
        toolbar.add(btnNovo);
        
        // Botão Editar
        BaseButton btnEditar = new BaseButton("✏️ Editar");
        btnEditar.setToolTipText("Editar registro selecionado (Enter)");
        btnEditar.addActionListener(e -> abrirDialogoEditar());
        toolbar.add(btnEditar);
        
        // Botão Deletar
        BaseButton btnDeletar = new BaseButton("🗑️ Deletar");
        btnDeletar.setToolTipText("Remover registro selecionado (Delete)");
        btnDeletar.addActionListener(e -> deletarSelecionado());
        toolbar.add(btnDeletar);
        
        toolbar.add(Box.createHorizontalStrut(16));
        
        // Botão Recarregar
        BaseButton btnRecarregar = new BaseButton("🔄 Atualizar");
        btnRecarregar.setToolTipText("Recarregar dados (F5)");
        btnRecarregar.addActionListener(e -> recarregar());
        toolbar.add(btnRecarregar);
    }
    
    private void configurarEventos() {
        // Double-click para editar
        tabela.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && tabela.getSelectedRow() >= 0) {
                    if (onDoubleClick != null) {
                        T entity = getEntidadeSelecionada();
                        if (entity != null) {
                            onDoubleClick.accept(entity);
                        }
                    } else {
                        abrirDialogoEditar();
                    }
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {
                mostrarMenuContextoSeNecessario(e);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) {
                mostrarMenuContextoSeNecessario(e);
            }
        });
        
        // Seleção alterada
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && onSelectionChanged != null) {
                T entity = getEntidadeSelecionada();
                onSelectionChanged.accept(entity);
            }
        });
        
        // Filtro
        campoFiltro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filtrar(); }
        });
    }
    
    private void configurarAtalhos() {
        // Insert - Novo
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0), "novo");
        getActionMap().put("novo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDialogoNovo();
            }
        });
        
        // Delete - Deletar
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "deletar");
        getActionMap().put("deletar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deletarSelecionado();
            }
        });
        
        // Enter - Editar
        tabela.getInputMap(WHEN_FOCUSED)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "editar");
        tabela.getActionMap().put("editar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDialogoEditar();
            }
        });
        
        // F5 - Recarregar
        getInputMap(WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
            .put(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0), "recarregar");
        getActionMap().put("recarregar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recarregar();
            }
        });
    }
    
    private void mostrarMenuContextoSeNecessario(MouseEvent e) {
        if (e.isPopupTrigger()) {
            int row = tabela.rowAtPoint(e.getPoint());
            if (row >= 0) {
                tabela.setRowSelectionInterval(row, row);
            }
            mostrarMenuContexto(e);
        }
    }
    
    private void mostrarMenuContexto(MouseEvent e) {
        JPopupMenu menu = new JPopupMenu();
        
        JMenuItem itemNovo = new JMenuItem("Novo");
        itemNovo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0));
        itemNovo.addActionListener(ev -> abrirDialogoNovo());
        menu.add(itemNovo);
        
        JMenuItem itemEditar = new JMenuItem("Editar");
        itemEditar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));
        itemEditar.addActionListener(ev -> abrirDialogoEditar());
        itemEditar.setEnabled(tabela.getSelectedRow() >= 0);
        menu.add(itemEditar);
        
        JMenuItem itemDeletar = new JMenuItem("Deletar");
        itemDeletar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
        itemDeletar.addActionListener(ev -> deletarSelecionado());
        itemDeletar.setEnabled(tabela.getSelectedRow() >= 0);
        menu.add(itemDeletar);
        
        menu.addSeparator();
        
        JMenuItem itemAtualizar = new JMenuItem("Atualizar");
        itemAtualizar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        itemAtualizar.addActionListener(ev -> recarregar());
        menu.add(itemAtualizar);
        
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
    
    // ==================== MÉTODOS PÚBLICOS ====================
    
    /**
     * Recarrega todos os dados da tabela.
     */
    public void recarregar() {
        tableModel.setRowCount(0);
        List<T> entities = operations.listarTodos();
        for (T entity : entities) {
            tableModel.addRow(tableMapper.toRowData(entity));
        }
        atualizarStatus();
    }
    
    /**
     * Adiciona uma entidade na tabela (sem persistir).
     * @param entity Entidade a adicionar
     */
    public void adicionarNaTabela(T entity) {
        tableModel.addRow(tableMapper.toRowData(entity));
        atualizarStatus();
    }
    
    /**
     * Abre o diálogo para criar nova entidade.
     */
    public void abrirDialogoNovo() {
        JPanel form = formFactory.criarFormulario();
        
        int resultado = JOptionPane.showConfirmDialog(
            this,
            form,
            "Novo " + titulo,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (resultado == JOptionPane.OK_OPTION) {
            if (formFactory.validar(form)) {
                T entity = formFactory.extrairDados(form);
                T saved = operations.criar(entity);
                adicionarNaTabela(saved);
                Toast.show(this, "Registro criado com sucesso!");
            } else {
                String erro = formFactory.getMensagemErro(form);
                DialogUtil.aviso(this, erro);
            }
        }
    }
    
    /**
     * Abre o diálogo para editar entidade selecionada.
     */
    public void abrirDialogoEditar() {
        int row = tabela.getSelectedRow();
        if (row < 0) {
            DialogUtil.aviso(this, "Selecione um registro para editar");
            return;
        }
        
        T entity = getEntidadeSelecionada();
        if (entity == null) return;
        
        JPanel form = formFactory.criarFormulario();
        formFactory.preencherFormulario(form, entity);
        
        int resultado = JOptionPane.showConfirmDialog(
            this,
            form,
            "Editar " + titulo,
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (resultado == JOptionPane.OK_OPTION) {
            if (formFactory.validar(form)) {
                T updated = formFactory.extrairDados(form);
                @SuppressWarnings("unchecked")
                ID id = (ID) tableMapper.getId(entity);
                operations.atualizar(id, updated);
                
                // Atualiza linha na tabela
                Object[] rowData = tableMapper.toRowData(updated);
                for (int i = 0; i < rowData.length; i++) {
                    tableModel.setValueAt(rowData[i], row, i);
                }
                Toast.show(this, "Registro atualizado com sucesso!");
            } else {
                String erro = formFactory.getMensagemErro(form);
                DialogUtil.aviso(this, erro);
            }
        }
    }
    
    /**
     * Deleta a entidade selecionada.
     */
    public void deletarSelecionado() {
        int row = tabela.getSelectedRow();
        if (row < 0) {
            DialogUtil.aviso(this, "Selecione um registro para deletar");
            return;
        }
        
        if (DialogUtil.confirmarExclusao(this, titulo)) {
            T entity = getEntidadeSelecionada();
            if (entity != null) {
                @SuppressWarnings("unchecked")
                ID id = (ID) tableMapper.getId(entity);
                if (operations.deletar(id)) {
                    tableModel.removeRow(row);
                    Toast.show(this, "Registro deletado com sucesso!");
                    atualizarStatus();
                }
            }
        }
    }
    
    /**
     * Retorna a entidade selecionada na tabela.
     * @return Entidade ou null se nenhuma selecionada
     */
    @SuppressWarnings("unchecked")
    public T getEntidadeSelecionada() {
        int row = tabela.getSelectedRow();
        if (row < 0) return null;
        
        // Reconstrói a entidade a partir da linha
        // Nota: Isso requer que o ID esteja na primeira coluna
        ID id = (ID) tableModel.getValueAt(row, 0);
        return operations.buscarPorId(id).orElse(null);
    }
    
    /**
     * Define callback quando seleção muda.
     * @param callback Consumer que recebe a entidade selecionada (ou null)
     */
    public void setOnSelectionChanged(Consumer<T> callback) {
        this.onSelectionChanged = callback;
    }
    
    /**
     * Define callback para double-click.
     * @param callback Consumer que recebe a entidade clicada
     */
    public void setOnDoubleClick(Consumer<T> callback) {
        this.onDoubleClick = callback;
    }
    
    /**
     * Retorna a tabela para customizações.
     * @return JTable interno
     */
    public JTable getTabela() {
        return tabela;
    }
    
    /**
     * Adiciona um botão customizado na toolbar.
     * @param texto Texto do botão
     * @param acao Ação ao clicar
     */
    public void adicionarBotao(String texto, Runnable acao) {
        BaseButton btn = new BaseButton(texto);
        btn.addActionListener(e -> acao.run());
        toolbar.add(btn);
    }
    
    // ==================== MÉTODOS PRIVADOS ====================
    
    private void filtrar() {
        String texto = campoFiltro.getText().toLowerCase().trim();
        tabela.filtrar(texto);
        atualizarStatus();
    }
    
    private void atualizarStatus() {
        int total = tableModel.getRowCount();
        int visiveis = tabela.getRowCount();
        
        if (total == visiveis) {
            labelStatus.setText(total + " registro" + (total != 1 ? "s" : ""));
        } else {
            labelStatus.setText(visiveis + " de " + total + " registros");
        }
    }
}
