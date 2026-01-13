/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author khang
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import dto.SetMenu;

public class SetMenuList extends ArrayList<SetMenu> {
    
    private final int COLUMN_COUNT = 4;
    
    public boolean readFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("Cannot read data from " + filename + ". Please check it.");
            return false;
        }
        
        try (FileReader fr = new FileReader(filename);
             BufferedReader br = new BufferedReader(fr)) {
            
            br.readLine();
            
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] columns = line.split(",", COLUMN_COUNT);
                
                if (columns.length < COLUMN_COUNT) {
                    System.out.println(">>> Warning: Skipping malformed row: " + line);
                    continue;
                }
                
                try {
                    String code = columns[0].trim();
                    String name = columns[1].trim();
                    double price = Double.parseDouble(columns[2].trim());
                    String ingredients = columns[3].trim();
                    
                    SetMenu menu = new SetMenu(code, name, price, ingredients);
                    add(menu);
                } catch (NumberFormatException e) {
                    System.out.println(">>> Warning: Invalid price format in row: " + line);
                }
            }
            return true;
            
        } catch (FileNotFoundException e) {
            System.out.println("Cannot read data from " + filename + ". Please check it.");
            return false;
        } catch (IOException e) {
            System.out.println("Cannot read data from " + filename + ". Please check it.");
            return false;
        }
    }
    
    public void sortByPrice() {
        Collections.sort(this, new Comparator<SetMenu>() {
            @Override
            public int compare(SetMenu m1, SetMenu m2) {
                return Double.compare(m1.getPrice(), m2.getPrice());
            }
        });
    }
    
    public void displayAll() {
        if (this.isEmpty()) {
            System.out.println("No menu available.");
            return;
        }
        
        System.out.println("==================================================================================");
        System.out.println("                     LIST OF SET MENUS FOR ORDERING PARTY                        ");
        System.out.println("==================================================================================");
        
        int index = 1;
        for (SetMenu m : this) {
            System.out.println("----------------------------------------------------------------------------------");
            System.out.printf("  [%d] Code      : %s%n", index++, m.getCode());
            System.out.printf("      Name      : %s%n", m.getName());
            System.out.printf("      Price     : %,.0f VND%n", m.getPrice());
            System.out.println("      Ingredients:");
            displayIngredients(m.getIngredients());
        }
        System.out.println("==================================================================================");
        System.out.println("Total: " + this.size() + " menu(s)");
    }
    
    private void displayIngredients(String ingredients) {
        String[] parts = ingredients.split("#");
        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                if (trimmed.startsWith("+")) {
                    System.out.println("        " + trimmed);
                } else {
                    System.out.println("        + " + trimmed);
                }
            }
        }
    }
    
    public SetMenu findByCode(String code) {
        for (SetMenu m : this) {
            if (m.getCode().equalsIgnoreCase(code)) {
                return m;
            }
        }
        return null;
    }
}
