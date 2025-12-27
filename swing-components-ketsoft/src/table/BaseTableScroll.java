package table;


import javax.swing.JScrollPane;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author alefi
 */
public class BaseTableScroll extends JScrollPane{

    public BaseTableScroll() {
        setViewportView(new BaseTable());
    }
}
