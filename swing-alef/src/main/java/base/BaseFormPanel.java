/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package base;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;

/**
 * Painel base para construção de formulários com GridBagLayout.
 * 
 * Uso:
 * <pre>
 * BaseFormPanel formulario = new BaseFormPanel();
 * formulario.adicionarCampo(new CampoTexto("Nome"));
 * formulario.adicionarCampo(new CampoTexto("Email"));
 * formulario.adicionarBotao("Salvar", () -> salvarDados());
 * </pre>
 * 
 * @author alefi
 */
public class BaseFormPanel extends BasePanel{

    protected GridBagConstraints gbc;
    protected List<JComponent> componentes;

    public BaseFormPanel() {
        setLayout(new GridBagLayout());
        setOpaque(false);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        componentes = new ArrayList<>();
    }

    /**
     * Adiciona um campo ao formulário.
     * 
     * @param campo JComponent do campo
     */
    protected void adicionarCampo(JComponent campo) {
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, campo.getPreferredSize().height));
        add(campo, gbc);
        componentes.add(campo);
        gbc.gridy++;
    }
    
    /**
     * Adiciona um botão ao formulário com espaçamento acima.
     * 
     * @param texto texto do botão
     * @param acao ação ao clicar
     */
    public void adicionarBotao(String texto, Runnable acao) {
        // Espaço entre campos e botões
        gbc.insets = new Insets(20, 20, 10, 20);
        
        BaseButton botao = new BaseButton(texto);
        botao.addActionListener(e -> acao.run());
        add(botao, gbc);
        componentes.add(botao);
        gbc.gridy++;
        
        // Restaurar espaçamento normal
        gbc.insets = new Insets(10, 20, 10, 20);
    }
    
    /**
     * Limpa o formulário.
     */
    public void limpar() {
        removeAll();
        componentes.clear();
        gbc.gridy = 0;
        revalidate();
        repaint();
    }
    
    /**
     * Retorna todos os componentes do formulário.
     * 
     * @return lista de componentes
     */
    public List<JComponent> obterComponentes() {
        return new ArrayList<>(componentes);
    }
}
