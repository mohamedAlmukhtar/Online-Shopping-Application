/*------------------------------------------------------------- 
// AUTHOR: Mohamed Mokhtar
// SPECIFICATION: Final Project (Online Shop Application) class
// FOR: CSE 110 - Final Project
// TIME SPENT: -
//-----------------------------------------------------------*/
package onlineshop;


public class Item 
{
    
    
    private String name;
    
    private int quantity = 0;
    
    private double price;

    
    
    public Item(String name, int quantity, double price) 
    {
        
        this.name = name;
        
        this.quantity = quantity;
        
        this.price = price;
    }

    
    public String getName() 
    {
        
        return name;
        
    }

    public void setName(String name) 
    {
        this.name = name;
        
    }

    public int getQuantity() 
    {
        return quantity;
        
    }

    public void setQuantity(int quantity) 
    {
        this.quantity = quantity;
        
    }

    public double getPrice() 
    {
        
        return price;
        
    }

    public void setPrice(double price) 
    {
        
        this.price = price;
        
    }
    
    @Override
    public String toString()
    {
        
        String string = "" + getName() + "," + getQuantity() + "," + getPrice();
        
        return string;
        
    }
    
    
}
