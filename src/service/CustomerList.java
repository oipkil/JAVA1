/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author khang
 */
import dto.Customer;
import lib.Acceptable;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class CustomerList extends ArrayList<Customer> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String dataFile = "customers.dat";
    
    public CustomerList() {
    }
    
    public CustomerList(String dataFile) {
        this.dataFile = dataFile;
    }
    
    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }
    
    public boolean addCustomer(Customer customer, Acceptable acceptable) {
        if (findById(customer.getId()) != null) {
            System.out.println(">>> Error: Customer ID already exists!");
            return false;
        }
        
        if (!customer.getId().matches(acceptable.getCusIdValid())) {
            System.out.println(">>> Error: Invalid customer ID format!");
            return false;
        }
        
        add(customer);
        System.out.println(">>> Customer added successfully!");
        return true;
    }
    
    public boolean updateCustomer(String id, Customer updatedCustomer) {
        Customer existing = findById(id);
        if (existing == null) {
            System.out.println(">>> This customer does not exist.");
            return false;
        }
        
        if (updatedCustomer.getName() != null && !updatedCustomer.getName().isEmpty()) {
            existing.setName(updatedCustomer.getName());
        }
        if (updatedCustomer.getPhone() != null && !updatedCustomer.getPhone().isEmpty()) {
            existing.setPhone(updatedCustomer.getPhone());
        }
        if (updatedCustomer.getEmail() != null && !updatedCustomer.getEmail().isEmpty()) {
            existing.setEmail(updatedCustomer.getEmail());
        }
        
        System.out.println(">>> Customer updated successfully!");
        return true;
    }
    
    public Customer findById(String id) {
        for (Customer c : this) {
            if (c.getId().equalsIgnoreCase(id)) {
                return c;
            }
        }
        return null;
    }
    
    public ArrayList<Customer> searchByName(String name) {
        ArrayList<Customer> results = new ArrayList<>();
        String searchLower = name.toLowerCase();
        
        for (Customer c : this) {
            if (c.getName().toLowerCase().contains(searchLower)) {
                results.add(c);
            }
        }
        
        Collections.sort(results, new Comparator<Customer>() {
            @Override
            public int compare(Customer c1, Customer c2) {
                return c1.getName().compareToIgnoreCase(c2.getName());
            }
        });
        
        return results;
    }
    
    public void sortByName() {
        Collections.sort(this, new Comparator<Customer>() {
            @Override
            public int compare(Customer c1, Customer c2) {
                return c1.getName().compareToIgnoreCase(c2.getName());
            }
        });
    }
    
    public void displayAll() {
        if (this.isEmpty()) {
            System.out.println("No data in the system.");
            return;
        }
        
        sortByName();
        
        System.out.println("================================================================================");
        System.out.println("                         CUSTOMER LIST (Sorted by Name A-Z)                     ");
        System.out.println("================================================================================");
        System.out.printf("| %-8s | %-25s | %-12s | %-25s |%n", 
                "ID", "NAME", "PHONE", "EMAIL");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (Customer c : this) {
            System.out.println(c.toString());
        }
        System.out.println("================================================================================");
        System.out.println("Total: " + this.size() + " customer(s)");
    }
    
    public void displaySearchResults(ArrayList<Customer> results, String keyword) {
        if (results.isEmpty()) {
            System.out.println(">>> No one matches the search criteria!");
            return;
        }
        
        System.out.println("================================================================================");
        System.out.println("Search results for: \"" + keyword + "\"");
        System.out.println("================================================================================");
        System.out.printf("| %-8s | %-25s | %-12s | %-25s |%n", 
                "ID", "NAME", "PHONE", "EMAIL");
        System.out.println("--------------------------------------------------------------------------------");
        
        for (Customer c : results) {
            System.out.println(c.toString());
        }
        System.out.println("================================================================================");
        System.out.println("Found: " + results.size() + " customer(s)");
    }
    
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(dataFile))) {
            oos.writeObject(this);
            System.out.println(">>> Customers saved to " + dataFile);
        } catch (IOException e) {
            System.out.println(">>> Error saving customers: " + e.getMessage());
        }
    }
}
