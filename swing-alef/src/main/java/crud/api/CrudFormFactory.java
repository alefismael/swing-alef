package crud.api;

import javax.swing.JPanel;

/**
 * Interface para criação de formulários CRUD.
 * 
 * Define o contrato para factory de formulários, permitindo
 * criar e extrair dados de formulários de forma genérica.
 * 
 * Exemplo de uso:
 * <pre>
 * public class ClienteFormFactory implements CrudFormFactory&lt;Cliente&gt; {
 *     
 *     {@literal @}Override
 *     public JPanel criarFormulario() {
 *         JPanel form = new JPanel();
 *         // Adicionar campos...
 *         return form;
 *     }
 *     
 *     {@literal @}Override
 *     public void preencherFormulario(JPanel formulario, Cliente entity) {
 *         // Preencher campos com dados da entidade
 *     }
 *     
 *     {@literal @}Override
 *     public Cliente extrairDados(JPanel formulario) {
 *         // Criar entidade a partir dos campos
 *         return new Cliente(...);
 *     }
 * }
 * </pre>
 * 
 * @param <T> Tipo da entidade
 * @author alefi
 */
public interface CrudFormFactory<T> {
    
    /**
     * Cria um novo formulário vazio.
     * @return Painel contendo os campos do formulário
     */
    JPanel criarFormulario();
    
    /**
     * Preenche o formulário com dados de uma entidade existente.
     * @param formulario Painel do formulário
     * @param entity Entidade com os dados
     */
    void preencherFormulario(JPanel formulario, T entity);
    
    /**
     * Extrai os dados do formulário e cria uma entidade.
     * @param formulario Painel do formulário
     * @return Nova entidade com os dados do formulário
     */
    T extrairDados(JPanel formulario);
    
    /**
     * Valida os dados do formulário.
     * @param formulario Painel do formulário
     * @return true se todos os campos são válidos
     */
    boolean validar(JPanel formulario);
    
    /**
     * Retorna mensagem de erro de validação.
     * @param formulario Painel do formulário
     * @return Mensagem descrevendo erros ou null se válido
     */
    default String getMensagemErro(JPanel formulario) {
        return validar(formulario) ? null : "Preencha todos os campos obrigatórios";
    }
    
    /**
     * Limpa todos os campos do formulário.
     * @param formulario Painel do formulário
     */
    default void limparFormulario(JPanel formulario) {
        // Implementação padrão vazia
        // Sobrescreva para limpar campos específicos
    }
}
