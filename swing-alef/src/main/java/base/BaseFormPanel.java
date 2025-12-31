/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package base;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JPanel;

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
public class BaseFormPanel extends JPanel {

    protected GridBagConstraints gbc;
    protected List<JComponent> componentes;

    public BaseFormPanel() {
        setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.gridx = 0;
        gbc.gridy = 0;
        
        componentes = new ArrayList<>();
    }

    /**
     * Adiciona um campo ao formulário.
     * 
     * @param campo JComponent do campo
     */
    public void adicionarCampo(JComponent campo) {
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
        gbc.insets = new Insets(20, 20, 10, 20);
        
        BaseButton botao = new BaseButton(texto);
        botao.addActionListener(e -> acao.run());
        add(botao, gbc);
        componentes.add(botao);
        gbc.gridy++;
        
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
