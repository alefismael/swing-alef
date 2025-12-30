package base;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class BaseNavigationBar extends JPanel {

    private final BaseTextField searchField;
    private final JTabbedPane tabbedPane;
    private final Map<String, String> tabs = new LinkedHashMap<>();
    private final Map<String, Runnable> actions = new LinkedHashMap<>();
    private final JPopupMenu popup;
    private final DefaultListModel<String> listModel;
    private final JList<String> suggestionList;

    public BaseNavigationBar() {
        super(new BorderLayout(6, 0));
        searchField = new BaseTextField(20);

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 4));
        left.setOpaque(false);
        left.add(new BaseLabel("Buscar:"));
        left.add(searchField);
        add(left, BorderLayout.WEST);

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        add(tabbedPane, BorderLayout.CENTER);

        // popup de sugestões (vazia inicialmente)
        popup = new JPopupMenu();
        popup.setFocusable(false);
        listModel = new DefaultListModel<>();
        suggestionList = new JList<>(listModel);
        suggestionList.setFocusable(false);
        suggestionList.setVisibleRowCount(6);
        suggestionList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int idx = suggestionList.getSelectedIndex();
                if (idx >= 0) {
                    String label = listModel.getElementAt(idx);
                    String id = idForLabel(label);
                    if (id != null) selectTab(id);
                    popup.setVisible(false);
                }
            }
        });
        JScrollPane scrollSugestoes = new JScrollPane(suggestionList);
        scrollSugestoes.setPreferredSize(new java.awt.Dimension(250, 150));
        popup.add(scrollSugestoes);

        // Ao trocar de aba, executar a ação associada
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int idx = tabbedPane.getSelectedIndex();
                if (idx < 0) return;
                String id = getIdAt(idx);
                if (id != null) {
                    Runnable r = actions.get(id);
                    if (r != null) r.run();
                }
            }
        });

        // Enquanto digita, atualiza sugestões
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            private void update() {
                String text = searchField.getText();
                if (text == null) text = "";
                text = text.trim().toLowerCase();
                listModel.clear();
                if (text.isEmpty()) {
                    popup.setVisible(false);
                    return;
                }
                for (Map.Entry<String, String> en : tabs.entrySet()) {
                    String id = en.getKey();
                    String label = en.getValue();
                    if (id.toLowerCase().startsWith(text) || label.toLowerCase().startsWith(text)) {
                        listModel.addElement(label);
                    }
                }
                    if (listModel.isEmpty()) {
                    popup.setVisible(false);
                } else {
                    suggestionList.setSelectedIndex(0);
                    if (!popup.isVisible()) {
                        SwingUtilities.invokeLater(() -> {
                            if (!popup.isVisible()) {
                                popup.show(searchField, 0, searchField.getHeight());
                                searchField.requestFocusInWindow();
                            }
                        });
                    }
                }
            }

            @Override
            public void insertUpdate(DocumentEvent e) { update(); }

            @Override
            public void removeUpdate(DocumentEvent e) { update(); }

            @Override
            public void changedUpdate(DocumentEvent e) { update(); }
        });

        // Enter confirma seleção de sugestão (se houver)
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (!listModel.isEmpty()) {
                        String label = listModel.getElementAt(Math.max(0, suggestionList.getSelectedIndex()));
                        String id = idForLabel(label);
                        if (id != null) selectTab(id);
                        popup.setVisible(false);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (!listModel.isEmpty()) {
                        int next = Math.min(suggestionList.getSelectedIndex() + 1, listModel.size() - 1);
                        suggestionList.setSelectedIndex(next);
                    }
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    if (!listModel.isEmpty()) {
                        int prev = Math.max(suggestionList.getSelectedIndex() - 1, 0);
                        suggestionList.setSelectedIndex(prev);
                    }
                }
            }
        });
    }

    public void addTab(String id, String label, Runnable onSelect) {
        if (tabs.containsKey(id)) return;
        tabs.put(id, label);
        actions.put(id, onSelect);
        JPanel placeholder = new JPanel();
        placeholder.setOpaque(false);
        tabbedPane.addTab(label, placeholder);
    }

    public BaseTextField getSearchField() {
        return searchField;
    }

    public void selectTab(String id) {
        int idx = getIndexOf(id);
        if (idx >= 0) tabbedPane.setSelectedIndex(idx);
    }

    private int getIndexOf(String id) {
        int i = 0;
        for (String k : tabs.keySet()) {
            if (k.equals(id)) return i;
            i++;
        }
        return -1;
    }

    private String getIdAt(int idx) {
        int i = 0;
        for (String k : tabs.keySet()) {
            if (i == idx) return k;
            i++;
        }
        return null;
    }

    private String idForLabel(String label) {
        for (Map.Entry<String, String> en : tabs.entrySet()) {
            if (en.getValue().equals(label)) return en.getKey();
        }
        return null;
    }
}
