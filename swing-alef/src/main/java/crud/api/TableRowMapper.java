package crud.api;

/**
 * Interface para conversão de entidade para linha de tabela.
 * 
 * Define como uma entidade deve ser convertida para exibição em JTable.
 * 
 * Exemplo de uso:
 * <pre>
 * public class ClienteTableMapper implements TableRowMapper&lt;Cliente&gt; {
 *     
 *     {@literal @}Override
 *     public String[] getColunas() {
 *         return new String[]{"ID", "Nome", "Email", "Telefone"};
 *     }
 *     
 *     {@literal @}Override
 *     public Object[] toRowData(Cliente entity) {
 *         return new Object[]{
 *             entity.getId(),
 *             entity.getNome(),
 *             entity.getEmail(),
 *             entity.getTelefone()
 *         };
 *     }
 *     
 *     {@literal @}Override
 *     public Object getId(Cliente entity) {
 *         return entity.getId();
 *     }
 * }
 * </pre>
 * 
 * @param <T> Tipo da entidade
 * @author alefi
 */
public interface TableRowMapper<T> {
    
    /**
     * Retorna os nomes das colunas da tabela.
     * @return Array com nomes das colunas
     */
    String[] getColunas();
    
    /**
     * Converte uma entidade para dados de linha.
     * @param entity Entidade a converter
     * @return Array de objetos representando a linha
     */
    Object[] toRowData(T entity);
    
    /**
     * Retorna o identificador da entidade.
     * @param entity Entidade
     * @return ID da entidade
     */
    Object getId(T entity);
    
    /**
     * Retorna a classe de cada coluna (opcional).
     * Útil para formatação e ordenação na tabela.
     * @return Array de classes ou null para usar Object
     */
    default Class<?>[] getColumnClasses() {
        return null;
    }
    
    /**
     * Retorna se uma coluna é editável (opcional).
     * @param columnIndex Índice da coluna
     * @return true se editável
     */
    default boolean isColumnEditable(int columnIndex) {
        return false;
    }
    
    /**
     * Retorna a largura preferida de cada coluna (opcional).
     * @return Array de larguras ou null para usar automático
     */
    default int[] getColumnWidths() {
        return null;
    }
}
