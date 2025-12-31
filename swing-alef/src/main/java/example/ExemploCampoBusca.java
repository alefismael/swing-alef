package example;

import fields.CampoBusca;
import base.BaseFrame;
import ui.Toast;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Exemplo de uso do componente CampoBusca.
 * Demonstra busca com strings simples e objetos customizados.
 * 
 * @author alefi
 */
public class ExemploCampoBusca {
    
    // Classe de exemplo para demonstrar busca com objetos
    static class Produto {
        int id;
        String nome;
        String categoria;
        double preco;
        
        Produto(int id, String nome, String categoria, double preco) {
            this.id = id;
            this.nome = nome;
            this.categoria = categoria;
            this.preco = preco;
        }
        
        @Override
        public String toString() {
            return nome + " - R$ " + String.format("%.2f", preco);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Tentar usar FlatLaf se disponível
            try {
                UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
            } catch (Exception e) {
                // Usar tema padrão
            }
            
            BaseFrame frame = new BaseFrame("Exemplo CampoBusca");
            frame.setLayout(new BorderLayout(10, 10));
            
            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            // ==================== Exemplo 1: Busca simples ====================
            JLabel label1 = new JLabel("1. Busca simples (strings):");
            label1.setAlignmentX(Component.LEFT_ALIGNMENT);
            label1.setFont(label1.getFont().deriveFont(Font.BOLD, 14f));
            mainPanel.add(label1);
            mainPanel.add(Box.createVerticalStrut(5));
            
            List<String> frutas = Arrays.asList(
                "Maçã", "Banana", "Laranja", "Uva", "Manga", 
                "Abacaxi", "Morango", "Melancia", "Pêra", "Kiwi"
            );
            
            CampoBusca<String> buscaFrutas = new CampoBusca<>(frutas);
            buscaFrutas.setPlaceholder("Buscar fruta...");
            buscaFrutas.setMaximumSize(new Dimension(300, 30));
            buscaFrutas.setAlignmentX(Component.LEFT_ALIGNMENT);
            buscaFrutas.aoSelecionar(fruta -> {
                Toast.success(frame, "Você selecionou: " + fruta);
            });
            mainPanel.add(buscaFrutas);
            mainPanel.add(Box.createVerticalStrut(20));
            
            // ==================== Exemplo 2: Busca com objetos ====================
            JLabel label2 = new JLabel("2. Busca com objetos (Produto):");
            label2.setAlignmentX(Component.LEFT_ALIGNMENT);
            label2.setFont(label2.getFont().deriveFont(Font.BOLD, 14f));
            mainPanel.add(label2);
            mainPanel.add(Box.createVerticalStrut(5));
            
            List<Produto> produtos = Arrays.asList(
                new Produto(1, "Notebook Dell", "Eletrônicos", 3500.00),
                new Produto(2, "Mouse Logitech", "Periféricos", 150.00),
                new Produto(3, "Teclado Mecânico", "Periféricos", 350.00),
                new Produto(4, "Monitor 27\"", "Eletrônicos", 1200.00),
                new Produto(5, "Webcam HD", "Periféricos", 200.00),
                new Produto(6, "Headset Gamer", "Áudio", 280.00),
                new Produto(7, "Caixa de Som", "Áudio", 450.00),
                new Produto(8, "SSD 1TB", "Armazenamento", 400.00)
            );
            
            CampoBusca<Produto> buscaProdutos = new CampoBusca<>(produtos);
            buscaProdutos.setPlaceholder("Buscar produto...");
            buscaProdutos.setMaximumSize(new Dimension(300, 30));
            buscaProdutos.setAlignmentX(Component.LEFT_ALIGNMENT);
            // Busca por nome OU categoria
            buscaProdutos.setFuncaoFiltro((p, texto) -> 
                p.nome.toLowerCase().contains(texto.toLowerCase()) ||
                p.categoria.toLowerCase().contains(texto.toLowerCase())
            );
            buscaProdutos.aoSelecionar(produto -> {
                Toast.info(frame, "Produto: " + produto.nome + "\nCategoria: " + produto.categoria);
            });
            mainPanel.add(buscaProdutos);
            mainPanel.add(Box.createVerticalStrut(20));
            
            // ==================== Exemplo 3: Sem mostrar todos ao focar ====================
            JLabel label3 = new JLabel("3. Só mostra ao digitar (setMostrarTodosAoFocar(false)):");
            label3.setAlignmentX(Component.LEFT_ALIGNMENT);
            label3.setFont(label3.getFont().deriveFont(Font.BOLD, 14f));
            mainPanel.add(label3);
            mainPanel.add(Box.createVerticalStrut(5));
            
            List<String> cidades = Arrays.asList(
                "São Paulo", "Rio de Janeiro", "Belo Horizonte", "Curitiba",
                "Porto Alegre", "Salvador", "Brasília", "Fortaleza", "Recife"
            );
            
            CampoBusca<String> buscaCidades = new CampoBusca<>(cidades);
            buscaCidades.setPlaceholder("Digite para buscar cidade...");
            buscaCidades.setMaximumSize(new Dimension(300, 30));
            buscaCidades.setAlignmentX(Component.LEFT_ALIGNMENT);
            buscaCidades.setMostrarTodosAoFocar(false); // Só mostra ao digitar
            buscaCidades.aoSelecionar(cidade -> {
                Toast.success(frame, "Cidade selecionada: " + cidade);
            });
            mainPanel.add(buscaCidades);
            mainPanel.add(Box.createVerticalStrut(20));
            
            // ==================== Exemplo 4: Display customizado ====================
            JLabel label4 = new JLabel("4. Display customizado (exibe só nome):");
            label4.setAlignmentX(Component.LEFT_ALIGNMENT);
            label4.setFont(label4.getFont().deriveFont(Font.BOLD, 14f));
            mainPanel.add(label4);
            mainPanel.add(Box.createVerticalStrut(5));
            
            CampoBusca<Produto> buscaProdutos2 = new CampoBusca<>(produtos);
            buscaProdutos2.setPlaceholder("Buscar por nome...");
            buscaProdutos2.setMaximumSize(new Dimension(300, 30));
            buscaProdutos2.setAlignmentX(Component.LEFT_ALIGNMENT);
            buscaProdutos2.setFuncaoExibicao(p -> p.nome); // Mostra só o nome
            buscaProdutos2.setMaxLinhasVisiveis(5); // Máximo 5 linhas no dropdown
            buscaProdutos2.aoSelecionar(produto -> {
                JOptionPane.showMessageDialog(frame, 
                    "ID: " + produto.id + "\n" +
                    "Nome: " + produto.nome + "\n" +
                    "Categoria: " + produto.categoria + "\n" +
                    "Preço: R$ " + String.format("%.2f", produto.preco),
                    "Detalhes do Produto",
                    JOptionPane.INFORMATION_MESSAGE);
            });
            mainPanel.add(buscaProdutos2);
            
            // Instruções
            mainPanel.add(Box.createVerticalStrut(30));
            JLabel instrucoes = new JLabel(
                "<html><b>Instruções:</b><br/>" +
                "• Clique no campo e digite para filtrar<br/>" +
                "• Use ↑↓ para navegar na lista<br/>" +
                "• Enter para selecionar<br/>" +
                "• Esc para fechar o dropdown</html>"
            );
            instrucoes.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(instrucoes);
            
            frame.add(mainPanel, BorderLayout.CENTER);
            frame.setVisible(true);
        });
    }
}
