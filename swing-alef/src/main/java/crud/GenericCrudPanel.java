package crud;

import base.BaseCrudPanel;
import base.BaseFormularioDialog;
import ui.DialogUtil;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Painel CRUD genérico em pacote dedicado.
 */
public abstract class GenericCrudPanel extends BaseCrudPanel {

    protected GenericCrudPanel(String titulo) {
        super(titulo);
        adicionarBotao("Novo", this::onNovo);
        adicionarBotao("Editar", this::onEditar);
        adicionarBotao("Deletar", this::onDeletar);
    }

    private void onNovo() {
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
        BaseFormularioDialog dialog = dialogFactory().createNovoDialog(owner);
        if (dialog == null) return;
        dialog.mostrarDialogo(this::salvarNovo);
    }

    private void onEditar() {
        Object[] dados = obterLinhaAtual();
        if (dados == null) {
            System.out.println("Selecione um registro para editar!");
            return;
        }
        int linha = obterLinhaSelecionada();
        JFrame owner = (JFrame) SwingUtilities.getWindowAncestor(this);
        BaseFormularioDialog dialog = dialogFactory().createEditarDialog(owner, linha, dados);
        if (dialog == null) return;
        dialog.mostrarDialogo(() -> salvarEditar(linha, dados));
    }

    private void onDeletar() {
        Object[] dados = obterLinhaAtual();
        if (dados == null) {
            System.out.println("Selecione um registro para deletar!");
            return;
        }
        int linha = obterLinhaSelecionada();
        if (!DialogUtil.confirmarExclusao(this, "Registro")) {
            return;
        }
        boolean ok = deletarSelecionado(linha, dados);
        if (ok && removerLinhaAtual()) {
            System.out.println("Registro removido!");
        }
    }

    protected abstract CrudDialogFactory dialogFactory();
    protected abstract void salvarNovo();
    protected abstract void salvarEditar(int linha, Object[] dadosOriginais);
    protected abstract boolean deletarSelecionado(int linha, Object[] dadosLinha);
}
