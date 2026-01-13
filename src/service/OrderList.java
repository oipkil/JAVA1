/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author khang
 */
import dto.Order;
import dto.SetMenu;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class OrderList extends ArrayList<Order> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String dataFile = "feast_order_service.dat";
    
    public OrderList() {
    }
    
    public OrderList(String dataFile) {
        this.dataFile = dataFile;
    }
    
    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }
    
    public boolean addOrder(Order order, CustomerList customers, SetMenuList menus) {
        if (customers.findById(order.getCustomerId()) == null) {
            System.out.println(">>> Error: Customer ID not found!");
            return false;
        }
        
        if (menus.findByCode(order.getMenuId()) == null) {
            System.out.println(">>> Error: Menu code not found!");
            return false;
        }
        
        if (this.contains(order)) {
            System.out.println(">>> Dupplicate data !");
            return false;
        }
        
        add(order);
        System.out.println(">>> Order placed successfully! Order Code: " + order.getOrderCode());
        return true;
    }
    
    public boolean updateOrder(String orderCode, String newMenuId, int newNumOfTables, 
                               Date newEventDate, SetMenuList menus) {
        Order order = findByCode(orderCode);
        if (order == null) {
            System.out.println(">>> Error: Order not found!");
            return false;
        }
        
        Date today = new Date();
        if (order.getEventDate().before(truncateTime(today))) {
            System.out.println(">>> Error: Cannot update order with past event date!");
            return false;
        }
        
        if (newMenuId != null && !newMenuId.isEmpty()) {
            if (menus.findByCode(newMenuId) == null) {
                System.out.println(">>> Error: New menu code not found!");
                return false;
            }
            order.setMenuId(newMenuId);
        }
        
        if (newNumOfTables > 0) {
            order.setNumOfTables(newNumOfTables);
        }
        
        if (newEventDate != null) {
            order.setEventDate(newEventDate);
        }
        
        System.out.println(">>> Order updated successfully!");
        return true;
    }
    
    private Date truncateTime(Date date) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            return sdf.parse(sdf.format(date));
        } catch (Exception e) {
            return date;
        }
    }
    
    public Order findByCode(String orderCode) {
        for (Order o : this) {
            if (o.getOrderCode().equals(orderCode)) {
                return o;
            }
        }
        return null;
    }
    
    public void sortByEventDate() {
        Collections.sort(this, new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getEventDate().compareTo(o2.getEventDate());
            }
        });
    }
    
    public void displayAll(CustomerList customers, SetMenuList menus) {
        if (this.isEmpty()) {
            System.out.println("No data in the system.");
            return;
        }
        
        sortByEventDate();
        
        System.out.println("================================================================================");
        System.out.println("                    ORDER LIST (Sorted by Event Date)                          ");
        System.out.println("================================================================================");
        System.out.printf("| %-16s | %-10s | %-8s | %-10s | %-12s | %15s |%n",
                "ORDER CODE", "CUSTOMER", "MENU", "TABLES", "EVENT DATE", "TOTAL (VND)");
        System.out.println("--------------------------------------------------------------------------------");
        
        double grandTotal = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        for (Order o : this) {
            SetMenu menu = menus.findByCode(o.getMenuId());
            double price = (menu != null) ? menu.getPrice() : 0;
            double total = price * o.getNumOfTables();
            grandTotal += total;
            
            System.out.printf("| %-16s | %-10s | %-8s | %10d | %-12s | %,15.0f |%n",
                    o.getOrderCode(),
                    o.getCustomerId(),
                    o.getMenuId(),
                    o.getNumOfTables(),
                    sdf.format(o.getEventDate()),
                    total);
        }
        
        System.out.println("================================================================================");
        System.out.printf("Total Orders: %d | Grand Total: %,.0f VND%n", this.size(), grandTotal);
    }
    
    public void saveToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(dataFile))) {
            oos.writeObject(this);
            System.out.println(">>> Orders saved to " + dataFile);
        } catch (IOException e) {
            System.out.println(">>> Error saving orders: " + e.getMessage());
        }
    }
}
