/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib;

/**
 *
 * @author khang
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Inputter {
    
    private final Scanner scanner;
    
    public Inputter() {
        this.scanner = new Scanner(System.in);
    }
    
    public Inputter(Scanner scanner) {
        this.scanner = scanner;
    }
    
    public String inputString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
    
    public String inputString(String prompt, String regex, String errorMsg) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.matches(regex)) {
                return input;
            }
            System.out.println(">>> " + errorMsg);
        }
    }
    
    public String inputNonEmptyString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println(">>> Input cannot be empty!");
        }
    }
    
    public int inputInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println(">>> Please enter a number between " + min + " and " + max);
            } catch (NumberFormatException e) {
                System.out.println(">>> Invalid number! Please enter digits only.");
            }
        }
    }
    
    public int inputPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value > 0) {
                    return value;
                }
                System.out.println(">>> Please enter a positive number.");
            } catch (NumberFormatException e) {
                System.out.println(">>> Invalid number! Please enter digits only.");
            }
        }
    }
    
    public Date inputDate(String prompt, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        sdf.setLenient(false);
        
        while (true) {
            System.out.print(prompt + " (" + format + "): ");
            try {
                String input = scanner.nextLine().trim();
                Date date = sdf.parse(input);
                
                Date today = truncateTime(new Date());
                Date inputDate = truncateTime(date);
                
                if (inputDate.before(today)) {
                    System.out.println(">>> Date cannot be in the past!");
                    continue;
                }
                return date;
            } catch (ParseException e) {
                System.out.println(">>> Invalid date format! Please use " + format);
            }
        }
    }
    
    private Date truncateTime(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            return sdf.parse(sdf.format(date));
        } catch (ParseException e) {
            return date;
        }
    }
    
    public boolean inputYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (Y/N): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("Y") || input.equals("YES")) {
                return true;
            }
            if (input.equals("N") || input.equals("NO")) {
                return false;
            }
            System.out.println(">>> Please enter Y or N.");
        }
    }
}
