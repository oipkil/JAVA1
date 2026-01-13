/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author khang
 */
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String orderCode;
    private String customerId;
    private String menuId;
    private int numOfTables;
    private Date eventDate;

    public Order() {
        this.orderCode = generateOrderCode();
    }

    public Order(String customerId, String menuId, int numOfTables, Date eventDate) {
        this.orderCode = generateOrderCode();
        this.customerId = customerId;
        this.menuId = menuId;
        this.numOfTables = numOfTables;
        this.eventDate = eventDate;
    }

    private String generateOrderCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public int getNumOfTables() {
        return numOfTables;
    }

    public void setNumOfTables(int numOfTables) {
        this.numOfTables = numOfTables;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Order)) return false;
        
        Order other = (Order) obj;
        
        if (customerId == null || menuId == null || eventDate == null) return false;
        if (other.customerId == null || other.menuId == null || other.eventDate == null) return false;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String thisDate = sdf.format(this.eventDate);
        String otherDate = sdf.format(other.eventDate);
        
        return this.customerId.equalsIgnoreCase(other.customerId) &&
               this.menuId.equalsIgnoreCase(other.menuId) &&
               thisDate.equals(otherDate);
    }

    @Override
    public int hashCode() {
        if (customerId == null || menuId == null || eventDate == null) return 0;
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String dateStr = sdf.format(eventDate);
        return Objects.hash(customerId.toUpperCase(), menuId.toUpperCase(), dateStr);
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dateStr = (eventDate != null) ? sdf.format(eventDate) : "N/A";
        return String.format("| %-10s | %-10s | %-8s | %5d ban | %-12s |",
                orderCode, customerId, menuId, numOfTables, dateStr);
    }
}
