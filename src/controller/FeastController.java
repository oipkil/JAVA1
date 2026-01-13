/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author khang
 */
import dto.Customer;
import dto.Order;
import dto.SetMenu;
import java.util.*;
import lib.Acceptable;
import lib.Inputter;
import service.CustomerList;
import service.DataLoader;
import service.OrderList;
import service.SetMenuList;

public class FeastController {
    private final CustomerList customers;
    private final SetMenuList menus;
    private final OrderList orders;
    private final Inputter inputter;
    private final Acceptable acceptable;
    private final String menuFile;
    
    public FeastController(Inputter inputter, Acceptable acceptable, DataLoader dataLoader, String menuFile) {
        this.inputter = inputter;
        this.acceptable = acceptable;
        this.menuFile = menuFile;
        
        menus = new SetMenuList();
        menus.readFromFile(menuFile);
        
        customers = dataLoader.loadCustomers();
        orders = dataLoader.loadOrders();
    }
    
    public void registerCustomer() {
        System.out.println("\n========== REGISTER NEW CUSTOMER ==========");
        
        String id;
        while (true) {
            id = inputter.inputString(
                    "Enter Customer ID (C/G/K + 4 digits): ",
                    acceptable.getCusIdValid(),
                    "Invalid format! ID must start with C, G, or K followed by 4 digits."
            );
            
            if (customers.findById(id) == null) {
                break;
            }
            System.out.println(">>> Customer ID already exists! Please enter a different ID.");
        }
        
        String name = inputter.inputString(
                "Enter Customer Name (2-25 chars): ",
                acceptable.getNameValid(),
                "Invalid name! Must be 2-25 characters, letters and spaces only."
        );
        
        String phone = inputter.inputString(
                "Enter Phone (10 digits, VN operator): ",
                acceptable.getPhoneValid(),
                "Invalid phone! Must be 10 digits starting with 03/05/07/08/09."
        );
        
        String email = inputter.inputString(
                "Enter Email: ",
                acceptable.getEmailValid(),
                "Invalid email format!"
        );
        
        Customer customer = new Customer(id, name, phone, email);
        customers.addCustomer(customer, acceptable);
    }
    
    public void updateCustomer() {
        System.out.println("\n========== UPDATE CUSTOMER ==========");
        
        if (customers.isEmpty()) {
            System.out.println(">>> No customers to update!");
            return;
        }
        
        String id = inputter.inputNonEmptyString("Enter Customer ID to update: ");
        Customer existing = customers.findById(id);
        
        if (existing == null) {
            System.out.println(">>> This customer does not exist.");
            return;
        }
        
        System.out.println("Current info:");
        System.out.println(existing);
        
        System.out.println("\nEnter new values (press Enter to keep current):");
        
        String newName;
        while (true) {
            newName = inputter.inputString("New Name [" + existing.getName() + "]: ");
            if (newName.isEmpty() || newName.matches(acceptable.getNameValid())) {
                break;
            }
            System.out.println(">>> Invalid name format! Please try again.");
        }
        
        String newPhone;
        while (true) {
            newPhone = inputter.inputString("New Phone [" + existing.getPhone() + "]: ");
            if (newPhone.isEmpty() || newPhone.matches(acceptable.getPhoneValid())) {
                break;
            }
            System.out.println(">>> Invalid phone format! Please try again.");
        }
        
        String newEmail;
        while (true) {
            newEmail = inputter.inputString("New Email [" + existing.getEmail() + "]: ");
            if (newEmail.isEmpty() || newEmail.matches(acceptable.getEmailValid())) {
                break;
            }
            System.out.println(">>> Invalid email format! Please try again.");
        }
        
        Customer updated = new Customer();
        updated.setName(newName.isEmpty() ? null : newName);
        updated.setPhone(newPhone.isEmpty() ? null : newPhone);
        updated.setEmail(newEmail.isEmpty() ? null : newEmail);
        
        customers.updateCustomer(id, updated);
    }
    
    public void searchCustomer() {
        System.out.println("\n========== SEARCH CUSTOMER ==========");
        
        if (customers.isEmpty()) {
            System.out.println(">>> No customers registered yet!");
            return;
        }
        
        String keyword = inputter.inputNonEmptyString("Enter name to search: ");
        ArrayList<Customer> results = customers.searchByName(keyword);
        customers.displaySearchResults(results, keyword);
    }
    
    public void displayMenus() {
        System.out.println("\n");
        if (menus.isEmpty()) {
            System.out.println("Cannot read data from " + menuFile + ". Please check it.");
            return;
        }
        menus.sortByPrice();
        menus.displayAll();
    }
    
    public void placeOrder() {
        System.out.println("\n========== PLACE FEAST ORDER ==========");
        
        if (customers.isEmpty()) {
            System.out.println(">>> No customers registered! Please register a customer first.");
            return;
        }
        
        if (menus.isEmpty()) {
            System.out.println(">>> No menus available! Cannot place order.");
            return;
        }
        
        String customerId = inputter.inputNonEmptyString("Enter Customer ID: ");
        Customer customer = customers.findById(customerId);
        if (customer == null) {
            System.out.println(">>> Customer not found!");
            return;
        }
        System.out.println(">>> Customer found: " + customer.getName() + " (" + customer.getEmail() + ")");        
        System.out.println("\n--- Available Menus ---");
        menus.sortByPrice();
        menus.displayAll();
        
        String menuId = inputter.inputNonEmptyString("\nEnter Menu Code: ");
        SetMenu menu = menus.findByCode(menuId);
        if (menu == null) {
            System.out.println(">>> Menu not found!");
            return;
        }
        
        int numOfTables = inputter.inputPositiveInt("Enter number of tables: ");
        
        Date eventDate = inputter.inputDate("Enter event date", "dd/MM/yyyy");
        
        double total = menu.getPrice() * numOfTables;
        String dateStr = new java.text.SimpleDateFormat("dd/MM/yyyy").format(eventDate);
        
        Order tempOrder = new Order(customerId, menuId, numOfTables, eventDate);
        
        System.out.println("\n--------------------------------------------------------------");
        System.out.printf("Customer order information [Order ID: %s]%n", tempOrder.getOrderCode());
        System.out.println("--------------------------------------------------------------");
        System.out.printf("  Code          : %s%n", customer.getId());
        System.out.printf("  Customer name : %s%n", customer.getName());
        System.out.printf("  Phone number  : %s%n", customer.getPhone());
        System.out.printf("  Email         : %s%n", customer.getEmail());
        System.out.println("--------------------------------------------------------------");
        System.out.printf("  Code of Set Menu: %s%n", menu.getCode());
        System.out.printf("  Set menu name   : %s%n", menu.getName());
        System.out.printf("  Event date      : %s%n", dateStr);
        System.out.printf("  Number of tables: %d%n", numOfTables);
        System.out.printf("  Price           : %,.0f Vnd%n", menu.getPrice());
        System.out.println("  Ingredients:");
        String[] ingredients = menu.getIngredients().split("#");
        for (String part : ingredients) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                if (trimmed.startsWith("+")) {
                    System.out.println("  " + trimmed);
                } else {
                    System.out.println("  + " + trimmed);
                }
            }
        }
        System.out.println("--------------------------------------------------------------");
        System.out.printf("  Total cost      : %,.0f Vnd%n", total);
        System.out.println("--------------------------------------------------------------");
        
        if (!inputter.inputYesNo("Confirm order?")) {
            System.out.println(">>> Order cancelled.");
            return;
        }
        
        orders.addOrder(tempOrder, customers, menus);
    }
    
    public void updateOrder() {
        System.out.println("\n========== UPDATE ORDER ==========");
        
        if (orders.isEmpty()) {
            System.out.println(">>> No orders to update!");
            return;
        }
        
        String orderCode = inputter.inputNonEmptyString("Enter Order Code to update: ");
        Order existing = orders.findByCode(orderCode);
        
        if (existing == null) {
            System.out.println(">>> Order not found!");
            return;
        }
        
        System.out.println("Current order: " + existing);
        
        System.out.println("\nEnter new values (leave empty/0 to keep current):");
        
        System.out.println("\n--- Available Menus ---");
        menus.sortByPrice();
        menus.displayAll();
        String newMenuId = inputter.inputString("New Menu Code (Enter to skip): ");
        
        int newTables = inputter.inputInt("New number of tables (0 to skip): ", 0, 1000);
        
        Date newDate = null;
        if (inputter.inputYesNo("Update event date?")) {
            newDate = inputter.inputDate("New event date", "dd/MM/yyyy");
        }
        
        orders.updateOrder(orderCode, newMenuId, newTables, newDate, menus);
    }
    
    public void displayCustomers() {
        System.out.println("\n");
        customers.displayAll();
    }
    
    public void displayOrders() {
        System.out.println("\n");
        orders.displayAll(customers, menus);
    }
    
    public void saveData() {
        System.out.println("\n========== SAVE DATA ==========");
        customers.saveToFile();
        orders.saveToFile();
    }
}
