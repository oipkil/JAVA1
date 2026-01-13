/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib;

/**
 *
 * @author khang
 */
public class Acceptable {
    
    private final String cusIdValid;
    private final String phoneValid;
    private final String emailValid;
    private final String nameValid;
    private final String menuIdValid;
    
    public Acceptable() {
        this.cusIdValid = "^[CcGgKk]\\d{4}$";
        this.phoneValid = "^0[35789]\\d{8}$";
        this.emailValid = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        this.nameValid = "^[a-zA-Z\\s]{2,25}$";
        this.menuIdValid = "^[Pp][Ww]\\d{3}$";
    }
    
    public String getCusIdValid() {
        return cusIdValid;
    }
    
    public String getPhoneValid() {
        return phoneValid;
    }
    
    public String getEmailValid() {
        return emailValid;
    }
    
    public String getNameValid() {
        return nameValid;
    }
    
    public String getMenuIdValid() {
        return menuIdValid;
    }
}
