package enums;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */

/**
 *
 * @author alefi
 */
public enum TypeFieldText {
    CODIGO(8),
    CPF(14),
    TELEFONE(16),
    NOME(25),
    EMAIL(30),
    DESCRICAO(40);

    private final int colunas;

    TypeFieldText(int colunas) {
        this.colunas = colunas;
    }

    public int getColunas() {
        return colunas;
    }
}
