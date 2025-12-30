package crud;

import base.BaseFormularioDialog;
import javax.swing.JFrame;

/**
 * Fabrica diálogos para operações CRUD.
 */
public interface CrudDialogFactory {
    BaseFormularioDialog createNovoDialog(JFrame owner);
    BaseFormularioDialog createEditarDialog(JFrame owner, int linha, Object[] dadosLinha);
}
