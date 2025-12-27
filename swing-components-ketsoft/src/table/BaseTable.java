/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package table;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alefi
 */
public class BaseTable extends JTable{

    public BaseTable() {
        setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Coluna 1", "Coluna 2"}
        ));

        setRowHeight(28);
        setFillsViewportHeight(true);
    }
}
