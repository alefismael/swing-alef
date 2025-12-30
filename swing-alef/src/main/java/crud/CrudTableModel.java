package crud;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Modelo de tabela genérico para listas de objetos.
 * Define colunas por nome e providers de valor.
 */
public class CrudTableModel<T> extends AbstractTableModel {
    private final String[] columnNames;
    private final Function<T, ?>[] valueProviders;
    private final List<T> rows = new ArrayList<>();

    @SafeVarargs
    public CrudTableModel(String[] columnNames, Function<T, ?>... valueProviders) {
        if (columnNames == null || valueProviders == null || columnNames.length != valueProviders.length) {
            throw new IllegalArgumentException("Column names and providers must be non-null and same length");
        }
        this.columnNames = columnNames;
        this.valueProviders = valueProviders;
    }

    public void setRows(List<T> data) {
        rows.clear();
        if (data != null) rows.addAll(data);
        fireTableDataChanged();
    }

    public void addRow(T item) {
        rows.add(item);
        int idx = rows.size() - 1;
        fireTableRowsInserted(idx, idx);
    }

    public T getRow(int index) {
        return rows.get(index);
    }

    public void updateRow(int index, T item) {
        rows.set(index, item);
        fireTableRowsUpdated(index, index);
    }

    public boolean removeRow(int index) {
        if (index < 0 || index >= rows.size()) return false;
        rows.remove(index);
        fireTableRowsDeleted(index, index);
        return true;
    }

    @Override
    public int getRowCount() {
        return rows.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T row = rows.get(rowIndex);
        return valueProviders[columnIndex].apply(row);
    }
}
