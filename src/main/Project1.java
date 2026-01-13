/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

/**
 *
 * @author khang
 */
import controller.FeastController;
import lib.Acceptable;
import lib.Inputter;
import service.DataLoader;

public class Project1 {
    
    private final String menuFile = "FeastMenu.csv";
    private final String customerFile = "customers.dat";
    private final String orderFile = "feast_order_service.dat";

    public static void main(String[] args) {
        Project1 app = new Project1();
        app.run();
    }
    
    public void run() {
        Inputter inputter = new Inputter();
        Acceptable acceptable = new Acceptable();
        DataLoader dataLoader = new DataLoader(customerFile, orderFile);
        
        FeastController controller = new FeastController(inputter, acceptable, dataLoader, menuFile);
        
        int choice;
        do {
            System.out.println("\n");
            System.out.println("+----------------------------------------------+");
            System.out.println("|       TRADITIONAL FEAST ORDER MANAGEMENT     |");
            System.out.println("+----------------------------------------------+");
            System.out.println("|  1. Register Customer                        |");
            System.out.println("|  2. Update Customer Information              |");
            System.out.println("|  3. Search Customer by Name                  |");
            System.out.println("|  4. Display Feast Menus (sorted by price)    |");
            System.out.println("|  5. Place a Feast Order                      |");
            System.out.println("|  6. Update Order Information                 |");
            System.out.println("|  7. Display Customers (sorted by name)       |");
            System.out.println("|  8. Display Orders (sorted by event date)    |");
            System.out.println("|  9. Save Data to File                        |");
            System.out.println("|  0. Exit                                     |");
            System.out.println("+----------------------------------------------+");
            
            choice = inputter.inputInt("Enter your choice: ", 0, 9);
            
            switch (choice) {
                case 1:
                    controller.registerCustomer();
                    break;
                case 2:
                    controller.updateCustomer();
                    break;
                case 3:
                    controller.searchCustomer();
                    break;
                case 4:
                    controller.displayMenus();
                    break;
                case 5:
                    controller.placeOrder();
                    break;
                case 6:
                    controller.updateOrder();
                    break;
                case 7:
                    controller.displayCustomers();
                    break;
                case 8:
                    controller.displayOrders();
                    break;
                case 9:
                    controller.saveData();
                    break;
                case 0:
                    if (inputter.inputYesNo("Save data before exit?")) {
                        controller.saveData();
                    }
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println(">>> Invalid choice!");
            }
        } while (choice != 0);
    }
}
