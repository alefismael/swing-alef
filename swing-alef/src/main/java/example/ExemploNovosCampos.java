package example;

import base.BaseFrame;
import base.BaseFormPanel;
import components.CampoTexto;
import components.CampoEmail;
import components.CampoSenha;
import components.CampoTelefone;
import components.CampoData;
import components.CampoNumeroSpinner;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Exemplo demonstrando os novos campos do package components.
 * 
 * Mostra o uso de:
 * - CampoEmail com validação visual em tempo real
 * - CampoTelefone (fixo e celular)
 * - CampoData com formato brasileiro
 * - CampoSenha com validação de segurança
 * 
 * @author alefi
 */
public class ExemploNovosCampos {
    
    public static void main(String[] args) {
        // Configurar tema FlatLaf
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception ex) {
            System.err.println("Erro ao carregar FlatLaf: " + ex.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            BaseFrame frame = new BaseFrame("Exemplo - Novos Campos");
            
            // Campo de texto normal
            CampoTexto nome = new CampoTexto("Nome Completo");
            
            // Campo de e-mail com validação visual (NOVO!)
            CampoEmail email = new CampoEmail("E-mail");
            // Quando digitar, mostra borda verde se válido, vermelha se inválido
            
            // Campo de senha com validação de segurança
            CampoSenha senha = new CampoSenha("Senha");
            
            // Campos de telefone formatados (NOVO!)
            CampoTelefone telefoneFixo = new CampoTelefone("Telefone Fixo", false);
            CampoTelefone celular = new CampoTelefone("Celular", true);
            
            // Campo de data com formato brasileiro (NOVO!)
            CampoData dataNascimento = new CampoData("Data de Nascimento");
            
            // Campo numérico
            CampoNumeroSpinner idade = new CampoNumeroSpinner("Idade", 18, 0, 150, 1);
            
            // Criar painel de formulário customizado
            BaseFormPanel form = new BaseFormPanel() {
                {
                    // Adicionar campos ao formulário
                    adicionarCampo(nome);
                    adicionarCampo(email);
                    adicionarCampo(senha);
                    adicionarCampo(telefoneFixo);
                    adicionarCampo(celular);
                    adicionarCampo(dataNascimento);
                    adicionarCampo(idade);
                }
            };
            
            // Botão para validar
            form.adicionarBotao("Validar e Exibir", () -> {
                System.out.println("=== DADOS DO FORMULÁRIO ===");
                System.out.println("Nome: " + nome.getValue());
                System.out.println("E-mail: " + email.getValue());
                System.out.println("E-mail válido? " + email.isValido());
                System.out.println("Senha: " + senha.getValue());
                System.out.println("Senha segura (min 8)? " + senha.isSegura(8));
                System.out.println("Telefone Fixo: " + telefoneFixo.getValue());
                System.out.println("Celular: " + celular.getValue());
                System.out.println("Data Nascimento: " + dataNascimento.getValueAsString());
                System.out.println("Idade: " + idade.getValue());
                System.out.println("===========================");
                
                // Validar todos os campos
                boolean todosValidos = nome.isValido() 
                    && email.isValido() 
                    && senha.isSegura(8)
                    && telefoneFixo.isValido()
                    && celular.isValido()
                    && dataNascimento.isValido()
                    && idade.isValido();
                
                if (todosValidos) {
                    javax.swing.JOptionPane.showMessageDialog(frame, 
                        "Todos os campos são válidos!", 
                        "Validação OK", 
                        javax.swing.JOptionPane.INFORMATION_MESSAGE);
                } else {
                    javax.swing.JOptionPane.showMessageDialog(frame, 
                        "Existem campos inválidos. Verifique:\n" +
                        "- E-mail deve ser válido\n" +
                        "- Senha deve ter no mínimo 8 caracteres\n" +
                        "- Telefones devem estar completos\n" +
                        "- Data deve estar no formato dd/MM/yyyy", 
                        "Validação Falhou", 
                        javax.swing.JOptionPane.WARNING_MESSAGE);
                }
            });
            
            // Botão para preencher exemplo
            form.adicionarBotao("Preencher Exemplo", () -> {
                nome.setValue("João da Silva");
                email.setValue("joao.silva@example.com");
                senha.setValue("senha123456");
                telefoneFixo.setValue("(11) 3456-7890");
                celular.setValue("(11) 98765-4321");
                dataNascimento.setValueFromString("15/03/1990");
                idade.setValue(33);
            });
            
            // Adicionar ao frame
            frame.add(form);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
