package com.br.lapisdecorpapelaria.others;

public class Util {
    public static String titleCase(String input) { 
        String[] words = input.split("\\s"); 
        StringBuilder result = new StringBuilder(); 
  
        for (String word : words) { 
            result.append(Character.toTitleCase(word.charAt(0))) 
                  .append(word.substring(1)) 
                  .append(" "); 
        } 
        return result.toString().trim(); 
    } 
}
