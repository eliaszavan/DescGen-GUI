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

public class DescGen extends JFrame {

    private JComboBox<Brand> brandComboBox;
    private JComboBox<BookType> bookTypeComboBox;
    private JCheckBox assortedCheckBox;
    private JTextField titleField;
    private JTextArea outputArea;
    private JButton generateButton;
    private JButton copyButton;

    private Brand brand = Brand.JANDAIA;
    private BookType bookType = BookType.BROCHURA;
    private boolean sortido = true;
    private String title = "CADERNO BROCHURAO UNIVERSITARIO CAPA DURA BLABLA 96FLS";

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
        inputPanel.setLayout(new GridLayout(5, 2, 10, 10));
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
        assortedCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 18)); // Aumentando o tamanho da fonte do checkbox
        inputPanel.add(assortedCheckBox);

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