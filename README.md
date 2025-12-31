# Swing Alef

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Maven](https://img.shields.io/badge/Maven-3.9+-blue.svg)](https://maven.apache.org/)
[![License](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

Uma biblioteca Java Swing completa para facilitar o desenvolvimento de aplicações Desktop com padrão de **CRUD** (Create, Read, Update, Delete).

## 🎯 Objetivo

Facilitar o desenvolvimento de aplicações Java Swing para iniciantes brasileiros, fornecendo componentes prontos para construção de aplicativos com interface gráfica padronizada.

## 📥 Instalação

### Maven

```xml
<dependency>
    <groupId>com.alef</groupId>
    <artifactId>swing-alef</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Manual (JAR)

1. Baixe o arquivo `swing-alef-1.0.0.jar` da pasta `target/`
2. Adicione ao classpath do seu projeto

## 🛠️ Build

```bash
# Compilar
mvn clean compile

# Gerar JAR
mvn package

# Executar exemplo
mvn exec:java

# Gerar Javadoc
mvn javadoc:javadoc
```

## ✨ Características Principais

- ✅ **Componentes Base**: Sem conflitos com temas como FlatLaf
- ✅ **BaseFrame**: Frame principal com suporte a navegação por abas
- ✅ **TabbedDocumentPane**: Sistema de abas fecháveis com indicador de modificações
- ✅ **TabbedFrame**: Frame com abas de documentos integrado
- ✅ **BaseLoginDialog**: Diálogo de login reutilizável com autenticação ⭐ NOVO
- ✅ **PainelCRUD**: Painel pronto para operações de CRUD
- ✅ **BaseFormularioDialog**: Diálogos modais para entrada de dados
- ✅ **Campos de Formulário em Português**: CampoTexto, CampoNumero, CampoCep, CampoSenha
- ✅ **Tabelas com Suporte a CRUD**: BaseTable com métodos úteis
- ✅ **DialogUtil**: Utilitário para diálogos em português ⭐ NOVO
- ✅ **ImageUtil**: Utilitário para carregar imagens ⭐ NOVO
- ✅ **Layout Automático**: GridBagLayout para componentes responsivos

## 📦 Estrutura de Packages

```
base/
  ├── BaseButton.java           - Botão base
  ├── BaseLabel.java            - Label base
  ├── BasePanel.java            - Painel base com GridBag
  ├── BaseFormPanel.java        - Painel para formulários
  ├── BaseTextField.java        - Campo de texto base
  ├── BaseSpinner.java          - Spinner para números
  ├── BaseFrame.java            - Frame principal com suporte F11
  ├── BaseLoginDialog.java      - Diálogo de login reutilizável ⭐ NOVO
  ├── TabbedDocumentPane.java   - Abas fecháveis com indicador
  ├── TabbedFrame.java          - Frame com abas de documentos
  ├── BaseCrudPanel.java        - Painel pronto para CRUD
  ├── BaseFormularioDialog.java - Diálogo para formulários
  └── BaseNavigationBar.java    - Barra de navegação

crud/
  ├── GenericCrudPanel.java     - Painel CRUD genérico com hooks
  ├── CrudDialogFactory.java    - Factory para criação de diálogos
  ├── CrudTableModel.java       - Model genérico para tabelas
  ├── CrudDialogPresets.java    - Presets para diálogos CRUD
  ├── CrudPanel.java            - Painel CRUD com interfaces ⭐ NOVO
  └── api/                      - Interfaces genéricas ⭐ NOVO
      ├── CrudOperations.java   - Interface para operações CRUD
      ├── CrudFormFactory.java  - Factory para formulários
      └── TableRowMapper.java   - Mapeador entidade-tabela

components/
  ├── CampoForm.java            - Classe abstrata base para campos
  ├── CampoTexto.java           - Campo de texto com label
  ├── CampoEmail.java           - Campo de e-mail com validação visual
  ├── CampoSenha.java           - Campo com mascaramento de senha
  ├── CampoNumeroSpinner.java   - Campo para números
  ├── CampoCep.java             - Campo específico para CEP (99999-999)
  ├── CampoTelefone.java        - Campo de telefone formatado
  ├── CampoData.java            - Campo de data (dd/MM/yyyy)
  ├── CampoEndereco.java        - Campo composto para endereço
  ├── CampoComboBox.java        - ComboBox com label ⭐ NOVO
  ├── CampoCheckBox.java        - CheckBox estilizado ⭐ NOVO
  ├── CampoRadioGroup.java      - Grupo de RadioButtons ⭐ NOVO
  ├── CampoMoeda.java           - Campo monetário (R$ 1.234,56) ⭐ NOVO
  ├── CampoCpf.java             - CPF com máscara e validação ⭐ NOVO
  └── CampoCnpj.java            - CNPJ com máscara e validação ⭐ NOVO

table/
  └── BaseTable.java            - Tabela base com CRUD

ui/
  ├── LoadingOverlay.java       - Overlay de carregamento
  ├── DialogUtil.java           - Diálogos em português ⭐ NOVO
  └── PainelTemas.java          - Seletor de temas FlatLaf

util/
  ├── ValidationUtil.java       - Utilitários de validação
  ├── DataBinder.java           - Binding de dados DTO->Campo
  └── ImageUtil.java            - Carregar imagens do classpath ⭐ NOVO
  └── DataBinder.java           - Binding de dados DTO->Campo

example/
  ├── ExemploAplicativoClientes.java - Exemplo completo de uso
  └── ExemploTabbedDocument.java     - Exemplo de abas fecháveis ⭐ NOVO
```

## 🚀 Como Usar

### 1. Aplicação com Abas Fecháveis (TabbedFrame)

```java
import base.TabbedFrame;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;

public class MinhaAplicacao {
    public static void main(String[] args) {
        // Usar FlatLaf (opcional, mas recomendado)
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            TabbedFrame frame = new TabbedFrame("Minha Aplicação");

            // Adicionar abas
            frame.adicionarAba("Clientes", new ClientePanel());
            frame.adicionarAba("Produtos", new ProdutoPanel());

            // Aba fixa (não fechável)
            frame.adicionarAbaFixa("Home", null, new HomePanel());

            frame.setVisible(true);
        });
    }
}
```

**Recursos do TabbedDocumentPane:**

- ✅ Botão X para fechar cada aba
- ✅ Indicador de modificações (• no título)
- ✅ Menu de contexto (botão direito): Fechar, Fechar Outras, Fechar Todas
- ✅ Atalhos: `Ctrl+W` (fechar), `Ctrl+Tab` (próxima), `Ctrl+Shift+Tab` (anterior)
- ✅ Confirmação ao fechar com alterações não salvas
- ✅ Abas fixas que não podem ser fechadas

```java
// Marcar aba como modificada (mostra •)
frame.getTabbedPane().marcarModificado(componente, true);

// Fechar programaticamente
frame.getTabbedPane().fecharAbaAtual();

// Callback quando aba é fechada
frame.getTabbedPane().setOnTabClosed(comp -> {
    System.out.println("Aba fechada: " + comp);
});Login com BaseLoginDialog ⭐ NOVO

```java
import base.BaseLoginDialog;

// Criar diálogo de login
BaseLoginDialog login = new BaseLoginDialog(null, "Login do Sistema");

// Configurar autenticador
login.setAutenticador((usuario, senha) -> {
    // Sua lógica de autenticação (ex: banco de dados)
    return usuario.equals("admin") && senha.equals("123");
});

// Mostrar e verificar resultado
if (login.mostrar()) {
    // Login bem sucedido
    System.out.println("Usuário: " + login.getUsuario());
    new MainFrame().setVisible(true);
} else {
    // Login cancelado
    System.exit(0);
}
```

**Recursos do BaseLoginDialog:**

- ✅ Campos CampoTexto e CampoSenha integrados
- ✅ Autenticador configurável via `BiFunction<String, String, Boolean>`
- ✅ Atalhos: `Enter` (entrar), `ESC` (cancelar)
- ✅ Mensagens de erro integradas
- ✅ Labels e textos customizáveis

### 3. Aplicação Básica com BaseFrame

```java
import base.BaseFrame;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;

public class MinhaAplicacao {
    public static void main(String[] args) {
        // Usar FlatLaf (opcional, mas recomendado)
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            BaseFrame frame = new BaseFrame("Minha Aplicação");
            frame.setVisible(true);
        });
    }
}
```

### 4. Criar um Painel CRUD

```java
import base.PainelCRUD;
import base.BaseFrame;

// Dentro da aplicação
BaseFrame frame = new BaseFrame("Minha App");

PainelCRUD painel = new PainelCRUD("Gestão de Clientes");
painel.definirColunas(new String[]{"ID", "Nome", "Email"});

// Adicionar dados
painel.adicionarLinha(new Object[]{1, "João", "joao@email.com"});

// Adicionar botões
painel.adicionarBotao("Novo", () -> criarNovoCliente());
painel.adicionarBotao("Editar", () -> editarCliente());
painel.adicionarBotao("Deletar", () -> deletarCliente());

frame.adicionarPainel("Clientes", painel);
frame.exibirPainel("Clientes");
```

### 5. Criar um Formulário com Diálogo

```java
import base.BaseFormularioDialog;
import components.CampoTexto;
import components.CampoEmail;

BaseFormularioDialog dialog = new BaseFormularioDialog(frame, "Novo Cliente");

CampoTexto campoNome = new CampoTexto("Nome");
CampoEmail campoEmail = new CampoEmail("Email");

dialog.adicionarCampo(campoNome);
dialog.adicionarCampo(campoEmail);

dialog.mostrarDialogo(() -> {
    String nome = campoNome.getValue();
    String email = campoEmail.getValue();

    if (campoNome.isValido() && campoEmail.isValido()) {
        salvarCliente(nome, email);
    }
});
```

### 6. Criar um Formulário Customizado

```java
import base.BaseFormPanel;
import components.CampoTexto;
import components.CampoNumeroSpinner;
import components.CampoEmail;

BaseFormPanel formulario = new BaseFormPanel();

CampoTexto campoNome = new CampoTexto("Nome");
CampoEmail campoEmail = new CampoEmail("Email");
CampoNumeroSpinner campoIdade = new CampoNumeroSpinner("Idade", 0, 0, 150, 1);

formulario.adicionarCampo(campoNome);
formulario.adicionarCampo(campoEmail);
formulario.adicionarCampo(campoIdade);

formulario.adicionarBotao("Salvar", () -> {
    // Ação ao clicar no botão
    System.out.println("Nome: " + campoNome.getValue());
    System.out.println("Email: " + campoEmail.getValue());
});
```

## 📚 Componentes Disponíveis

### BaseFrame

Frame principal com suporte a múltiplos painéis com CardLayout.

**Métodos principais:**

- `adicionarPainel(String nome, JPanel painel)` - Adiciona um painel
- `exibirPainel(String nome)` - Exibe um painel específico
- `removerPainel(String nome)` - Remove um painel
- `obterPainel(String nome)` - Obtém um painel existente

### PainelCRUD

Painel pronto para operações CRUD com tabela e barra de ferramentas.

**Métodos principais:**

- `adicionarBotao(String texto, Runnable acao)` - Adiciona botão à barra
- `definirColunas(String[] colunas)` - Define colunas da tabela
- `adicionarLinha(Object[] dados)` - Adiciona linha
- `removerLinhaAtual()` - Remove linha selecionada
- `obterLinhaAtual()` - Obtém dados da linha selecionada
- `limparTabela()` - Limpa todas as linhas

### BaseTable

Tabela com métodos úteis para CRUD.

**Métodos principais:**

- `definirColunas(String[] colunas)`
- `adicionarLinha(Object[] dados)`
- `removerLinha(int linha)`
- `removerLinhaAtual()`
- `obterValor(int linha, int coluna)`
- `definirValor(int linha, int coluna, Object valor)`
- `obterLinhaAtual()`
- `limpar()`

### Campos de Formulário

#### CampoTexto

```java
CampoTexto campo = new CampoTexto("Seu Rótulo");
String valor = campo.getValue();
campo.setValue("novo valor");
boolean valido = campo.isValido(); // verifica se não está vazio
```

#### CampoEmail

```java
CampoEmail campo = new CampoEmail("E-mail");
String email = campo.getValue();
campo.setValue("user@example.com");
boolean valido = campo.isValido(); // valida formato de e-mail
// Validação visual em tempo real: borda verde se válido, vermelha se inválido
```

#### CampoSenha

```java
CampoSenha campo = new CampoSenha("Senha");
String senha = campo.getValue();
boolean segura = campo.isSegura(8); // verifica se tem no mínimo 8 caracteres
```

#### CampoNumeroSpinner

```java
// Construtor simples (0 a 999999, passo 1)
CampoNumeroSpinner campo = new CampoNumeroSpinner("Idade");

// Construtor completo (valor inicial, min, max, passo)
CampoNumeroSpinner campo = new CampoNumeroSpinner("Quantidade", 10, 1, 100, 5);
int valor = campo.getValue();
```

#### CampoCep

```java
CampoCep campo = new CampoCep();
String cep = campo.getValue(); // formato: 99999-999
boolean valido = campo.isValido(); // verifica formato
```

#### CampoTelefone

```java
// Telefone fixo: (99) 9999-9999
CampoTelefone telefoneFixo = new CampoTelefone("Telefone", false);

// Celular: (99) 99999-9999
CampoTelefone celular = new CampoTelefone("Celular", true);
```

#### CampoData

```java
CampoData campo = new CampoData("Data de Nascimento");
Date data = campo.getValue();
campo.setValue(new Date());
String dataStr = campo.getValueAsString(); // formato: dd/MM/yyyy
campo.setValueFromString("25/12/2025");
```

#### CampoEndereco

```java
CampoEndereco endereco = new CampoEndereco();
// Campos compostos: CEP, logradouro, número, bairro, cidade, país
// Inclui botão "Buscar CEP" para integração futura com API
```

### Utilitários

#### DialogUtil ⭐ NOVO

Diálogos em português compatíveis com FlatLaf:

```java
import ui.DialogUtil;

// Confirmação simples (Sim/Não)
if (DialogUtil.confirmar(parent, "Deseja continuar?")) {
    // Usuário clicou Sim
}

// Confirmação de exclusão
if (DialogUtil.confirmarExclusao(parent, "cliente")) {
    clienteRepository.excluir(cliente);
}

// Confirmação com Cancelar (Sim/Não/Cancelar)
int opcao = DialogUtil.confirmarComCancelar(parent, "Salvar alterações?");
// 0 = Sim, 1 = Não, 2 = Cancelar

// Mensagens informativas
DialogUtil.info(parent, "Operação concluída!");
DialogUtil.aviso(parent, "Campo obrigatório não preenchido");
DialogUtil.erro(parent, "Falha ao conectar no banco");

// Input de texto
String nome = DialogUtil.input(parent, "Digite o nome:");

// Seleção de opções
String[] opcoes = {"Opção A", "Opção B", "Opção C"};
String escolha = DialogUtil.selecionar(parent, "Escolha:", opcoes);
```

#### ImageUtil ⭐ NOVO

Utilitário para carregar imagens do classpath:

```java
import util.ImageUtil;

// Carregar imagem do classpath (pasta resources)
Image imagem = ImageUtil.carregarImagem("/icone.png");

// Carregar como ImageIcon
ImageIcon icone = ImageUtil.carregarIcone("/logo.png");

// Redimensionar mantendo proporção
Image redimensionada = ImageUtil.redimensionarProporcional(imagem, 64, 64);

// Verificar se imagem existe
if (ImageUtil.existe("/foto.jpg")) {
    // ...
}
```

## 🎨 Temas e Customização

A biblioteca foi refatorada para trabalhar perfeitamente com **FlatLaf**. Para usar temas:

```java
// Light Theme
UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");

// Dark Theme
UIManager.setLookAndFeel("com.formdev.flatlaf.FlatDarkLaf");

// Intellij Theme
UIManager.setLookAndFeel("com.formdev.flatlaf.FlatIntelliJLaf");
```

## 📋 Exemplo Completo

Veja `ExemploAplicativoClientes.java` para um exemplo funcional completo de:

- Criação de BaseFrame
- Configuração de PainelCRUD
- Diálogos para criar, editar e deletar clientes

## 🔧 Dependência FlatLaf (Recomendado)

Para melhor aparência, use FlatLaf:

```xml
<dependency>
    <groupId>com.formdev</groupId>
    <artifactId>flatlaf</artifactId>
    <version>3.5.4</version>
</dependency>
```

## 📝 Notas Importantes

- **Compatibilidade**: Java 8, 11, 17, 21
- **Build Tool**: Maven 3.9+
- **FlatLaf**: Não é obrigatório, mas recomendado para melhor aparência
- **Componentes em Português**: A maioria dos nomes segue convenção em português
- **Sem Conflitos**: Todos os componentes permitem que o look and feel gerencie o visual

## 📁 Estrutura do Projeto (Maven)

```
swing-alef/
├── pom.xml
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── base/
│   │       ├── components/
│   │       ├── crud/
│   │       ├── table/
│   │       ├── ui/
│   │       └── util/
│   └── test/
│       └── java/
└── target/
    └── swing-alef-1.0.0.jar
```

## 🐛 Troubleshooting

### Os componentes parecem desalinhados

- Certifique-se de estar usando GridBagLayout
- Use `BasePanel` ou `BaseFormPanel` como base

### Cores estranhas com FlatLaf

- Limpe o cache de compilação
- Reinicie a aplicação
- Verifique se o FlatLaf foi configurado antes de criar componentes

### Componentes muito pequenos

- Os tamanhos padrão já estão configurados
- Customize através de `UIManager` ou sobrescrevendo métodos

## 📄 Licença

MIT License

Copyright (c) 2025 Álef Ismael de Souza

## 👨‍💻 Contribuições

Contribuições são bem-vindas! Por favor, abra uma issue ou pull request.

## 🙏 Agradecimentos

Desenvolvido com o objetivo de facilitar o aprendizado de Java Swing para desenvolvedores brasileiros.
