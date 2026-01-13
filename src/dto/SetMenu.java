/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author khang
 */
public class SetMenu {
    private String Code;
    private String Name;
    private double Price;
    private String Ingredients;

    public SetMenu() {
    }

    public SetMenu(String Code, String Name, double Price, String Ingredients) {
        this.Code = Code;
        this.Name = Name;
        this.Price = Price;
        this.Ingredients = Ingredients;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String Code) {
        this.Code = Code;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double Price) {
        this.Price = Price;
    }

    public String getIngredients() {
        return Ingredients;
    }

    public void setIngredients(String Ingredients) {
        this.Ingredients = Ingredients;
    }
}
