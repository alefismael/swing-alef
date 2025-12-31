package example;

import base.BaseFormularioDialog;
import base.BaseFormPanel;
import base.BaseLabel;
import base.BaseTextField;
import fields.CampoTexto;
import javax.swing.*;
import java.awt.*;

import com.formdev.flatlaf.FlatLightLaf;

/**
 * Teste simples para debugar campos.
 */
public class TesteDialog {
    public static void main(String[] args) {
        // Inicializa FlatLaf
        FlatLightLaf.setup();
        
        SwingUtilities.invokeLater(() -> {
            // Teste: Replicar estrutura do BaseFormularioDialog manualmente
            JFrame frame = new JFrame("Teste Manual");
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setSize(500, 400);
            frame.setLayout(new BorderLayout());
            
            // Simular BaseFormPanel com GridBagLayout
            JPanel painelForm = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 20, 10, 20);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            gbc.anchor = GridBagConstraints.NORTHWEST;
            gbc.gridx = 0;
            gbc.gridy = 0;
            
            // Adicionar CampoTexto manualmente
            CampoTexto campo1 = new CampoTexto("Nome");
            CampoTexto campo2 = new CampoTexto("Email");
            
            painelForm.add(campo1, gbc);
            gbc.gridy++;
            painelForm.add(campo2, gbc);
            
            // Simular ScrollPane do dialog
            JScrollPane scrollPane = new JScrollPane(painelForm,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            // Simular painel principal
            JPanel pnlPrincipal = new JPanel(new BorderLayout());
            pnlPrincipal.add(scrollPane, BorderLayout.CENTER);
            
            // Painel de botões
            JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            painelBotoes.add(new JButton("Confirmar"));
            painelBotoes.add(new JButton("Cancelar"));
            pnlPrincipal.add(painelBotoes, BorderLayout.SOUTH);
            
            frame.add(pnlPrincipal);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            
            // Debug após exibição
            SwingUtilities.invokeLater(() -> {
                System.out.println("=== Estrutura Manual ===");
                System.out.println("PainelForm size: " + painelForm.getSize());
                System.out.println("Campo1 size: " + campo1.getSize());
                System.out.println("Campo1 location: " + campo1.getLocation());
                System.out.println("Campo1 bounds: " + campo1.getBounds());
                
                // Verificar componentes internos
                System.out.println("\nCampo1 componentes internos:");
                for (Component c : campo1.getComponents()) {
                    System.out.println("  - " + c.getClass().getSimpleName() + 
                        " visible=" + c.isVisible() + 
                        " bounds=" + c.getBounds() +
                        " showing=" + c.isShowing());
                }
            });
        });
    }
}
