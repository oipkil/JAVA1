/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author khang
 */
import java.io.*;

public class DataLoader {
    
    private final String customerFile;
    private final String orderFile;
    
    public DataLoader() {
        this.customerFile = "customers.dat";
        this.orderFile = "feast_order_service.dat";
    }
    
    public DataLoader(String customerFile, String orderFile) {
        this.customerFile = customerFile;
        this.orderFile = orderFile;
    }
    
    public CustomerList loadCustomers() {
        File file = new File(customerFile);
        if (!file.exists()) {
            System.out.println(">>> No saved customer data found.");
            CustomerList list = new CustomerList();
            list.setDataFile(customerFile);
            return list;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(customerFile))) {
            CustomerList list = (CustomerList) ois.readObject();
            list.setDataFile(customerFile);
            System.out.println(">>> Customers loaded from " + customerFile);
            return list;
        } catch (InvalidClassException e) {
            System.out.println(">>> Error: Customer data file version mismatch!");
            System.out.println(">>> The file '" + customerFile + "' was created with a different class version.");
            System.out.println(">>> Please delete the file and re-enter customer data.");
            CustomerList list = new CustomerList();
            list.setDataFile(customerFile);
            return list;
        } catch (ClassNotFoundException e) {
            System.out.println(">>> Error: Customer class not found!");
            System.out.println(">>> " + e.getMessage());
            CustomerList list = new CustomerList();
            list.setDataFile(customerFile);
            return list;
        } catch (IOException e) {
            System.out.println(">>> Error loading customers: " + e.getMessage());
            CustomerList list = new CustomerList();
            list.setDataFile(customerFile);
            return list;
        }
    }
    
    public OrderList loadOrders() {
        File file = new File(orderFile);
        if (!file.exists()) {
            System.out.println(">>> No saved order data found.");
            OrderList list = new OrderList();
            list.setDataFile(orderFile);
            return list;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(orderFile))) {
            OrderList list = (OrderList) ois.readObject();
            list.setDataFile(orderFile);
            System.out.println(">>> Orders loaded from " + orderFile);
            return list;
        } catch (InvalidClassException e) {
            System.out.println(">>> Error: Order data file version mismatch!");
            System.out.println(">>> The file '" + orderFile + "' was created with a different class version.");
            System.out.println(">>> Please delete the file and re-enter order data.");
            OrderList list = new OrderList();
            list.setDataFile(orderFile);
            return list;
        } catch (ClassNotFoundException e) {
            System.out.println(">>> Error: Order class not found!");
            System.out.println(">>> " + e.getMessage());
            OrderList list = new OrderList();
            list.setDataFile(orderFile);
            return list;
        } catch (IOException e) {
            System.out.println(">>> Error loading orders: " + e.getMessage());
            OrderList list = new OrderList();
            list.setDataFile(orderFile);
            return list;
        }
    }
}
