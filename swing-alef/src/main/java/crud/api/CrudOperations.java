package crud.api;

import java.util.List;
import java.util.Optional;

/**
 * Interface genérica para operações CRUD.
 * 
 * Define o contrato para operações básicas de persistência.
 * Implemente esta interface para criar repositórios ou serviços de dados.
 * 
 * Exemplo de uso:
 * <pre>
 * public class ClienteRepository implements CrudOperations&lt;Cliente, Long&gt; {
 *     
 *     {@literal @}Override
 *     public Cliente criar(Cliente entity) {
 *         // Inserir no banco
 *         return entity;
 *     }
 *     
 *     {@literal @}Override
 *     public Optional&lt;Cliente&gt; buscarPorId(Long id) {
 *         // Buscar no banco
 *         return Optional.ofNullable(cliente);
 *     }
 *     
 *     // ... outros métodos
 * }
 * </pre>
 * 
 * @param <T> Tipo da entidade
 * @param <ID> Tipo do identificador (Long, Integer, String, UUID, etc)
 * @author alefi
 */
public interface CrudOperations<T, ID> {
    
    /**
     * Cria uma nova entidade.
     * @param entity Entidade a ser criada
     * @return Entidade criada (pode conter ID gerado)
     */
    T criar(T entity);
    
    /**
     * Atualiza uma entidade existente.
     * @param id Identificador da entidade
     * @param entity Dados atualizados
     * @return Entidade atualizada
     */
    T atualizar(ID id, T entity);
    
    /**
     * Remove uma entidade pelo ID.
     * @param id Identificador da entidade
     * @return true se foi removida, false se não existia
     */
    boolean deletar(ID id);
    
    /**
     * Busca uma entidade pelo ID.
     * @param id Identificador da entidade
     * @return Optional contendo a entidade ou vazio se não encontrada
     */
    Optional<T> buscarPorId(ID id);
    
    /**
     * Lista todas as entidades.
     * @return Lista de todas as entidades
     */
    List<T> listarTodos();
    
    /**
     * Conta o total de entidades.
     * @return Número total de entidades
     */
    default long contar() {
        return listarTodos().size();
    }
    
    /**
     * Verifica se existe uma entidade com o ID informado.
     * @param id Identificador da entidade
     * @return true se existe
     */
    default boolean existe(ID id) {
        return buscarPorId(id).isPresent();
    }
}
