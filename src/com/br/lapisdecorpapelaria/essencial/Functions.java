package com.br.lapisdecorpapelaria.essencial;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.br.lapisdecorpapelaria.enums.BookType;
import com.br.lapisdecorpapelaria.enums.Brand;
import com.br.lapisdecorpapelaria.others.Util;

public class Functions {
	
    private Brand brand;
    private BookType bookType;
    private boolean sortido;
    private String title;
    
    private int folhas = 0;
    private int materias = 0;
    
    public Functions() {}
    
    public Functions(Brand brand, BookType bookType, boolean sortido, String title) {
    	this.brand = brand;
    	this.bookType = bookType;
    	this.sortido = sortido;
    	this.title = title;
    }
    
    public void set(Brand brand, BookType bookType, boolean sortido, String title) {
    	this.brand = brand;
    	this.bookType = bookType;
    	this.sortido = sortido;
    	this.title = title;
    }

	public String getWebsiteDescription() {
        String titleCased = title
                .replace("UNIVERSITARIO", "UNIVERSITÁRIO").replace("BROCHURAO", "BROCHURA")
                .replace(" MM ", " MILIMETRADO ");

        Pattern patternFls = Pattern.compile("(\\d+)FLS");
        Matcher matcherFls = patternFls.matcher(title);

        if (matcherFls.find()) {
            folhas = Integer.parseInt(matcherFls.group(1));
            titleCased = titleCased.replace(folhas + "FLS", folhas + " FOLHAS");
        } else {
            titleCased = titleCased
                    .replace("40FLS", "40 FOLHAS").replace("48FLS", "48 FOLHAS").replace("60FLS", "60 FOLHAS")
                    .replace("80FLS", "80 FOLHAS").replace("96FLS", "96 FOLHAS").replace("160FLS", "160 FOLHAS")
                    .replace("192FLS", "192 FOLHAS").replace("200FLS", "200 FOLHAS").replace("224FLS", "224 FOLHAS")
                    .replace("256FLS", "256 FOLHAS").replace("300FLS", "300 FOLHAS").replace("320FLS", "320 FOLHAS")
                    .replace("400FLS", "400 FOLHAS");
        }

        Pattern patternM = Pattern.compile("(\\d+)M");
        Matcher matcherM = patternM.matcher(title);

        if (matcherM.find()) {
            materias = Integer.parseInt(matcherM.group(1));
            titleCased = titleCased.replace(materias + "M", materias + " MATÉRIAS");
        } else {
            titleCased = titleCased
                    .replace("1M", "1 MATÉRIA").replace("10M", "10 MATÉRIAS").replace("12M", "12 MATÉRIAS")
                    .replace("15M", "15 MATÉRIAS").replace("16M", "16 MATÉRIAS").replace("20M", "20 MATÉRIAS");
        }
        titleCased = titleCased.toLowerCase();

        return Util.titleCase(titleCased)
                .replace(" E ", " e ").replace(" De ", " de ").replace(" Do ", " do ").replace(" Da ", " da ");
    }

    public List<String> getDescription() {
        List<String> stringList = new ArrayList<>();
        stringList.add((getWebsiteDescription() + " " + brand.value).toUpperCase()
                .replace(" 40 FOLHAS", "").replace(" 48 FOLHAS", "").replace(" 60 FOLHAS", "").replace(" 80 FOLHAS", "")
                .replace(" 96 FOLHAS", "").replace(" 160 FOLHAS", "").replace(" 192 FOLHAS", "")
                .replace(" 200 FOLHAS", "").replace(" 224 FOLHAS", "").replace(" 256 FOLHAS", "")
                .replace(" 300 FOLHAS", "").replace(" 400 FOLHAS", "").replace(" (SORTIDO)", ""));

        String idealMsg;
        switch (bookType) {
            case CARTOGRAFIA:
                idealMsg = "Ideal para o dia a dia na escola, auxiliando o aluno nas atividades de desenho e colorir.";
                break;
            case UNIVERSITÁRIO:
            case ESPIRAL:
                idealMsg = "Ideal para o dia a dia na escola ou na faculdade.";
                break;
            case BROCHURA:
                idealMsg = "Ideal para o dia a dia na escola.";
                break;
            case CALIGRAFIA:
                idealMsg = "Ideal para desenvolver habilidades de caligrafia.";
                break;
            default:
                idealMsg = "Ideal para o dia a dia.";
                break;
        }
        stringList.add(getWebsiteDescription().replace(" (sortido)", "") + " possui capa dura com a parte interna decorada, folhas "
                + (bookType == BookType.CARTOGRAFIA ? "sem pautas" : "pautadas") + " e espiral colorido. " + idealMsg + "\n\n");
        stringList.add("DETALHES");
        stringList.add("• Alta qualidade.");
        stringList.add("• Espiral: colorido.");
        stringList.add("• Folhas: " + (bookType == BookType.CARTOGRAFIA ? "sem pauta" : "pautadas") + ".");
        if (title.toLowerCase().contains("dura")) stringList.add("• Material: capa dura.");
        if (bookType == BookType.UNIVERSITÁRIO) stringList.add("• Número de matérias: " + materias + " " + (materias <= 1 ? "matéria" : "matérias") + ".");
        stringList.add("• Laminação brilho.");
        if (sortido) {
            if (bookType == BookType.CARTOGRAFIA) stringList.add("• 2 estampas.\n\n");
            else stringList.add("• 4 estampas.\n\n");

            stringList.add("*Item sortido: não garantimos a entrega de todas as estampas/cores mostradas.");
            stringList.add("*Item sortido pode variar a cor ou estampa e é enviado conforme a disponibilidade de estoque. "
                    + "Se desejar uma cor ou estampa específica, entre em contato conosco através do chat do site (WhatsApp) e indique seu nome, número do pedido e a cor ou estampa do item desejado.");
        }
        String[] keywords = title.toLowerCase().split(" ");
        String keywordsText = brand.name().toLowerCase() + ", " + 
            (bookType == BookType.UNIVERSITÁRIO ? (materias + "m, " + materias + ", " + (materias == 1 ? "matéria" : "matérias") + ", ") : "");

        for (int iKeyword = 0; iKeyword < keywords.length; iKeyword++) {
            keywordsText += keywords[iKeyword] + (iKeyword == keywords.length - 1 ? "" : ", ");
        }
        keywordsText = keywordsText.replace(" de,", "").replace(" e,", "") + ", " + folhas + ", folhas, fls";
        stringList.add(keywordsText);

        return stringList;
    }
}