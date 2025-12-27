package form;

import base.BaseButton;
import dto.EnderecoDTO;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

/**
 *
 * @author alefi
 */
public class CampoEndereco extends CampoForm<EnderecoDTO> {

    private CampoCep cep;
    private CampoTexto logradouro;
    private CampoTexto numero;
    private CampoTexto bairro;
    private CampoTexto cidade;
    private CampoTexto pais;

    private BaseButton btnBuscarCep;

    public CampoEndereco() {
        super("Endereço");
        label.setFont(new Font("Segoe UI", Font.BOLD, 16)); // maior e mais forte
        label.setForeground(new Color(30, 30, 30));         // tom mais escuro
        label.setBorder(BorderFactory.createEmptyBorder(8, 4, 12, 4)); // espaçamento extra
        label.setOpaque(true);
        label.setBackground(new Color(240, 240, 240));

        setOpaque(false);
        setBorder(BorderFactory.createLineBorder(new Color(153, 153, 153), 2));
        buildLayout();
    }

    private void buildLayout() {
        JPanel content = new JPanel(new GridBagLayout());
        content.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Número
        numero = new CampoTexto("Número");
        numero.field.setPreferredSize(new Dimension(100, 28));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        content.add(numero, gbc);

        // CEP + botão
        cep = new CampoCep();
        btnBuscarCep = new BaseButton("Buscar");
        btnBuscarCep.setPreferredSize(new Dimension(100, 28));
        btnBuscarCep.addActionListener(e -> buscarCep());

        JPanel cepPanel = new JPanel(new BorderLayout(5, 0));
        cepPanel.setOpaque(false);
        cepPanel.add(cep, BorderLayout.CENTER);
        cepPanel.add(btnBuscarCep, BorderLayout.EAST);

        gbc.gridx = 1;
        content.add(cepPanel, gbc);

        // Logradouro
        logradouro = new CampoTexto("Logradouro");
        logradouro.field.setPreferredSize(new Dimension(180, 28));
        gbc.gridx = 0;
        gbc.gridy++;
        content.add(logradouro, gbc);

        // Bairro
        bairro = new CampoTexto("Bairro");
        bairro.field.setPreferredSize(new Dimension(180, 28));
        gbc.gridx = 1;
        content.add(bairro, gbc);

        // Cidade
        cidade = new CampoTexto("Cidade");
        cidade.field.setPreferredSize(new Dimension(180, 28));
        gbc.gridx = 0;
        gbc.gridy++;
        content.add(cidade, gbc);

        // País
        pais = new CampoTexto("País");
        pais.field.setPreferredSize(new Dimension(180, 28));
        gbc.gridx = 1;
        content.add(pais, gbc);

        add(content, BorderLayout.CENTER);
    }

    @Override
    public EnderecoDTO getValue() {
        return null;
    }

    @Override
    public void setValue(EnderecoDTO enderecoDto) {
    }

    @Override
    public boolean isValido() {
        return cep.isValido()
                && !logradouro.getValue().isBlank()
                && !numero.getValue().isBlank();
    }

    private void buscarCep() {

    }
}
