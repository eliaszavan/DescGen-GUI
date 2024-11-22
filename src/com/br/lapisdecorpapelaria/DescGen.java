package com.br.lapisdecorpapelaria;

import javax.swing.*;

import com.br.lapisdecorpapelaria.enums.BookType;
import com.br.lapisdecorpapelaria.enums.Brand;
import com.br.lapisdecorpapelaria.essencial.Functions;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class DescGen extends JFrame {

    private JComboBox<Brand> brandComboBox;
    private JComboBox<BookType> bookTypeComboBox;
    private JCheckBox assortedCheckBox;
    private JTextField titleField;
    private JTextArea outputArea;
    private JButton generateButton;
    private JButton copyButton;
    private JTextField folderPathField;  // Novo JTextField para o caminho da pasta
    private JButton selectFolderButton;  // Novo JButton para abrir o JFileChooser

    private Brand brand = Brand.JANDAIA;
    private BookType bookType = BookType.BROCHURA;
    private boolean sortido = true;
    private String title = "CADERNO BROCHURAO UNIVERSITARIO CAPA DURA BLABLA 96FLS";
    private String imagesPath = "";  // Nova string para armazenar o caminho da pasta selecionada

    private int folhas = 0;
    private int materias = 0;

    private Functions functions;

    public DescGen() {
        this.functions = new Functions();

        // Configuração do JFrame
        setTitle("Gerador de Descrição de Produto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLookAndFeel();

        // Painel de entrada com layout moderno
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2, 10, 10));  // Mudamos para 6 linhas
        inputPanel.setBackground(new Color(45, 45, 45));  // Tema escuro
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        inputPanel.add(createLabel("Título:"));
        titleField = new JTextField(title);
        titleField.setFont(new Font("Arial, Helvetica, sans-serif", Font.PLAIN, 13));
        titleField.setBackground(new Color(60, 63, 65)); // Caixa de texto com fundo escuro
        titleField.setForeground(Color.WHITE); // Texto claro
        titleField.setCaretColor(Color.WHITE); // Cor do cursor
        titleField.setPreferredSize(new Dimension(500, 40)); // Aumentando apenas a largura do JTextField do título
        inputPanel.add(titleField);

        inputPanel.add(createLabel("Marca:"));
        brandComboBox = new JComboBox<>(Brand.values());
        brandComboBox.setBackground(new Color(60, 63, 65));
        brandComboBox.setForeground(Color.BLACK);
        brandComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        inputPanel.add(brandComboBox);

        inputPanel.add(createLabel("Tipo de Caderno:"));
        bookTypeComboBox = new JComboBox<>(BookType.values());
        bookTypeComboBox.setBackground(new Color(60, 63, 65));
        bookTypeComboBox.setForeground(Color.BLACK);
        bookTypeComboBox.setFont(new Font("Arial", Font.PLAIN, 12));
        inputPanel.add(bookTypeComboBox);

        inputPanel.add(createLabel("Sortido:"));
        assortedCheckBox = new JCheckBox();
        assortedCheckBox.setBackground(new Color(45, 45, 45));
        assortedCheckBox.setForeground(Color.WHITE);
        assortedCheckBox.setSelected(sortido);
        assortedCheckBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
        assortedCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // Aumentando o tamanho da fonte do checkbox
        inputPanel.add(assortedCheckBox);

        // Novo campo para selecionar pasta
        inputPanel.add(createLabel("Image Path (caso não queira, deixe em branco):"));
        JPanel folderPanel = new JPanel(new BorderLayout());
        folderPanel.setBackground(new Color(45, 45, 45));

        folderPathField = new JTextField();
        folderPathField.setFont(new Font("Arial", Font.PLAIN, 13));
        folderPathField.setBackground(new Color(60, 63, 65));  // Caixa de texto com fundo escuro
        folderPathField.setForeground(Color.WHITE);  // Texto claro
        folderPathField.setCaretColor(Color.WHITE);  // Cor do cursor
        folderPathField.setPreferredSize(new Dimension(400, 40));  // Ajuste no tamanho
        folderPanel.add(folderPathField, BorderLayout.CENTER);

        // Botão para selecionar a pasta
        selectFolderButton = new JButton();
        selectFolderButton.setBackground(Color.RED);  // Cor vermelha
        selectFolderButton.setForeground(Color.WHITE);
        selectFolderButton.setPreferredSize(new Dimension(40, 40));  // Botão quadrado (1:1)
        ImageIcon icon = new ImageIcon("C:/Users/User/Desktop/Elias/search-icon.png");
        selectFolderButton.setIcon(icon);  // Ícone de "procurar"
        selectFolderButton.setBorderPainted(false);
        selectFolderButton.setFocusPainted(false);
        selectFolderButton.setContentAreaFilled(true);
        selectFolderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));  // Alterando o cursor para "pointer"

        // Efeito de hover para mudar a cor de fundo
        selectFolderButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Muda a cor de fundo para um vermelho escuro ao passar o mouse
            	selectFolderButton.setBackground(new Color(180, 0, 0));  // Um tom de vermelho mais escuro
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Restaura a cor original do botão quando o mouse sai
            	selectFolderButton.setBackground(Color.RED);
            }
        });
        selectFolderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser folderChooser = new JFileChooser();
                folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = folderChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFolder = folderChooser.getSelectedFile();
                    folderPathField.setText(selectedFolder.getAbsolutePath());  // Atualiza o campo com o caminho da pasta
                    imagesPath = selectedFolder.getAbsolutePath();  // Salva o caminho da pasta na variável imagesPath
                }
            }
        });
        folderPanel.add(selectFolderButton, BorderLayout.EAST);
        inputPanel.add(folderPanel);

        // Botões com estilo moderno
        generateButton = createButton("Gerar Descrição", new Color(30, 160, 110), Color.WHITE);
        copyButton = createButton("Copiar para Área de Transferência", new Color(20, 110, 250), Color.WHITE); // Azul

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(new Color(45, 45, 45));
        buttonPanel.add(generateButton);
        buttonPanel.add(copyButton);

        // Área de saída com estilo
        outputArea = new JTextArea(20, 50);
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(30, 30, 30));
        outputArea.setForeground(Color.WHITE);
        outputArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        outputArea.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

        // Adicionando componentes à janela
        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ações dos botões
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                brand = (Brand) brandComboBox.getSelectedItem();
                bookType = (BookType) bookTypeComboBox.getSelectedItem();
                sortido = assortedCheckBox.isSelected();
                title = titleField.getText();
                
                // Aqui, o caminho da pasta é já salvo na variável imagesPath
                generateDescription();
            }
        });

        copyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringSelection selection = new StringSelection(outputArea.getText());
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(selection, null);
                JOptionPane.showMessageDialog(null, "Descrição copiada para a área de transferência!");
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void generateDescription() {
        if (!title.replaceAll("[\\d.]", "").contains("FLS")) title = title + " " + folhas + " FOLHAS";

        this.functions.set(brand, bookType, sortido, title);

        StringBuilder output = new StringBuilder();
        output.append(this.functions.getWebsiteDescription()).append(" ").append(brand.value).append(sortido ? " (Sortido)\n" : "\n");
        output.append("\n");
        for (String line : this.functions.getDescription()) {
            output.append(line).append("\n");
        }

        outputArea.setText(output.toString());

        // Aqui você pode usar o caminho da pasta em imagesPath, se necessário, para salvar ou processar arquivos
        System.out.println("Caminho da pasta selecionada: " + imagesPath);
    }

    // Método para definir o look and feel
    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para criar botões com estilo
    private JButton createButton(String text, Color bgColor, Color txtColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBackground(bgColor);
        button.setForeground(txtColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efeito de hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    // Método para criar labels com estilo
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return label;
    }

    public static void main(String[] args) {
        new DescGen();
    }
}