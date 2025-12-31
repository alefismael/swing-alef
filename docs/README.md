# Documentação swing-alef

Biblioteca de componentes Swing para aplicações Java desktop.

## 📁 Estrutura da Documentação

```
docs/
├── README.md           ← Você está aqui
├── GETTING_STARTED.md  ← Guia de início rápido
├── ARCHITECTURE.md     ← Arquitetura do projeto
│
├── fields/             ← Campos de formulário
│   ├── FIELDS.md       ← Campos de formulário
│   └── VALIDATION.md   ← Sistema de validação
│
├── ui/                 ← Componentes de interface
│   ├── TABBED_PANE.md  ← Painel de abas e menus
│   ├── DIALOG.md       ← Diálogos de formulário
│   ├── TOAST.md        ← Notificações toast
│   └── KEYBINDINGS.md  ← Atalhos de teclado
│
└── (outros)            ← Docs específicos/legados
```

## 🚀 Início Rápido

### Dependência Maven

```xml
<dependency>
    <groupId>com.alefi</groupId>
    <artifactId>swing-alef</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Exemplo Mínimo

```java
import ui.TabbedDocumentPane;
import ui.TabbedDocumentPane.MenuOpcao;

public class App {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Minha Aplicação");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        
        // Criar painel de abas
        TabbedDocumentPane tabbedPane = new TabbedDocumentPane();
        
        // Aba inicial
        tabbedPane.adicionarAbaFixa("Principal", new JPanel());
        
        // Menus
        tabbedPane.registrarMenu(new MenuOpcao("Cadastro", "📋", List.of(
            new MenuOpcao("Clientes", "👥", () -> new ClientesPanel())
        )));
        
        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
```

## 📦 Componentes Principais

### Navegação
- **TabbedDocumentPane** - Painel com abas fecháveis e menus hierárquicos
- **MenuOpcao** - Estrutura para menus dropdown aninhados

### Campos de Formulário
- **CampoTexto** - Texto simples com validação de tamanho
- **CampoEmail** - E-mail com validação de formato
- **CampoSenha** - Senha com caracteres ocultos
- **CampoTelefone** - Telefone com máscara (fixo/celular)
- **CampoData** - Data com máscara dd/MM/yyyy
- **CampoMoeda** - Valor monetário (R$)
- **CampoCpf** - CPF com validação
- **CampoCnpj** - CNPJ com validação
- **CampoCep** - CEP com máscara
- **CampoEndereco** - Endereço completo
- **CampoNumeroSpinner** - Número com spinner
- **CampoComboBox** - ComboBox tipado
- **CampoCheckBox** - Checkbox simples
- **CampoRadioGroup** - Grupo de radio buttons

### Diálogos e Notificações
- **BaseFormularioDialog** - Diálogo com validação automática
- **Toast** - Notificações temporárias (success, error, warning, info)
- **KeyBindingManager** - Gerenciador de atalhos de teclado

### Validação
- **Validavel** - Interface para componentes validáveis
- Todos os campos implementam `isValid()`, `getMensagemErro()`, etc.

## 🎨 Temas

O swing-alef suporta temas via FlatLaf:

```java
// Tema claro
FlatLightLaf.setup();

// Tema escuro
FlatDarkLaf.setup();

// Atualizar UI
SwingUtilities.updateComponentTreeUI(frame);
```

## 📖 Guias Detalhados

| Documento | Descrição |
|-----------|-----------|
| [FIELDS.md](fields/FIELDS.md) | Todos os campos de formulário |
| [VALIDATION.md](fields/VALIDATION.md) | Sistema de validação |
| [TABBED_PANE.md](ui/TABBED_PANE.md) | Painel de abas e menus |
| [DIALOG.md](ui/DIALOG.md) | Diálogos de formulário |
| [TOAST.md](ui/TOAST.md) | Notificações toast |
| [KEYBINDINGS.md](ui/KEYBINDINGS.md) | Atalhos de teclado |
| [NETBEANS_INTEGRATION.md](NETBEANS_INTEGRATION.md) | Integração com NetBeans |

## 📝 Changelog Recente

### v1.0.0
- Sistema de menus hierárquicos com `MenuOpcao`
- Toast com tipos (INFO, SUCCESS, WARNING, ERROR) e posições
- Interface `Validavel` para validação de campos
- Validação automática em `BaseFormularioDialog`
- Compatibilidade com NetBeans GUI Builder
- Renomeação de `isValido()` para `isValid()` (convenção inglês)

## 🔗 Links

- [Repositório](https://github.com/alefi/swing-alef)
- [Exemplos](../swing-alef/src/main/java/example/)
- [API Javadoc](../swing-alef/target/reports/apidocs/)
