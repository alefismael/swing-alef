package crud;

import javax.swing.*;

/**
 * Presets para BaseFormularioDialog.
 * Dado que BaseFormularioDialog já provê OK/Cancelar em mostrarDialogo,
 * os presets focam em tamanho e validação.
 */
public final class CrudDialogPresets {
    private CrudDialogPresets() {}

    public static void applyDefaultSize(base.BaseFormularioDialog dialog, int w, int h) {
        dialog.definirTamanho(w, h);
    }

    public static boolean validateRequired(javax.swing.JComponent parent, boolean... checks) {
        for (boolean c : checks) {
            if (!c) {
                JOptionPane.showMessageDialog(parent, "Preencha todos os campos obrigatórios!", "Validação", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        }
        return true;
    }
}
