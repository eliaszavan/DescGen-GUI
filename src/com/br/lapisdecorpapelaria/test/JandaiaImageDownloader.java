package com.br.lapisdecorpapelaria.test;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class JandaiaImageDownloader {
	
	
	
    public static void main(String[] args) {
        // URL da página do caderno
        String url = "https://jandaia.com/produto/caderno-espiral-universitario-star-wars/";

        try {
            // Obtém o caminho da pasta Downloads do usuário
            String userHome = System.getProperty("user.home");
            File downloadsDir = new File(userHome, "Downloads");

            // Conecta e obtém o conteúdo HTML da página
            Document doc = Jsoup.connect(url).get();

            // Seleciona os elementos de imagem que estão na pasta "09"
            Elements images = doc.select("img[src*='/uploads/2024/09/']");

            // Verifica se encontrou imagens
            if (images.isEmpty()) {
                System.out.println("Nenhuma imagem encontrada na pasta '09'.");
                return;
            }

            // Baixar as imagens
            int imageCount = 1;
            for (Element img : images) {
            	if(imageCount > 5) break;
                String imageUrl = img.attr("src");
                System.out.println("Baixando imagem: " + imageUrl);

                // Faz o download da imagem
                File outputFile = new File(downloadsDir, "imagem_" + imageCount + ".png");
                try (InputStream in = new URL(imageUrl).openStream();
                     FileOutputStream out = new FileOutputStream(outputFile)) {
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = in.read(buffer)) != -1) {
                        out.write(buffer, 0, bytesRead);
                    }
                    System.out.println("Imagem " + imageCount + " salva em: " + outputFile.getAbsolutePath());
                    imageCount++;
                }
            }

            System.out.println("Download concluído!");

        } catch (Exception e) {
            System.err.println("Erro ao acessar a página ou baixar as imagens: " + e.getMessage());
        }
    }
}