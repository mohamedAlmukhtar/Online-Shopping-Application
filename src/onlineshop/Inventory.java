/*------------------------------------------------------------- 
// AUTHOR: Mohamed Mokhtar
// SPECIFICATION: Final Project (Online Shop Application) class
// FOR: CSE 110 - Final Project
// TIME SPENT: -
//-----------------------------------------------------------*/
package onlineshop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.NumberFormat;
import java.util.Scanner;



public class Inventory {
    
    
    
    static DynamicArray inventory = new DynamicArray();
    
    static File inventoryFile = new File("src/onlineshop/inventory.txt");
    
    
    public static void printInventory() throws FileNotFoundException
    {
        
       Item item;
        
         NumberFormat frm = NumberFormat.getCurrencyInstance();
        
         System.out.println("\nItem\t\tQuantity\t\tPrice\n----------------------------------------------");
         
        for(int i=0;i<inventory.size;i++)
        {
            
            item =  (Item) inventory.array[i];
            
                System.out.println("\n"+(i+1) +") " +item.getName()+"\t\t"
                    
                    +item.getQuantity() +"\t\t" +frm.format(item.getPrice()));
            
        }
        
        
    }
    
    
    
    public static DynamicArray fetchItems() throws FileNotFoundException{
        
        DynamicArray items = new DynamicArray();
        String entry;
        String[] parts;
        Scanner inFile = new Scanner(new FileReader(inventoryFile));
        
        while(inFile.hasNextLine()){
            entry = inFile.nextLine();
            parts = OnlineShop.breakEntry(entry, 3);
            
            int quantity = Integer.parseInt(parts[1]);
            double price = Double.parseDouble(parts[2]);
            items.add(new Item(parts[0],quantity,price));
            
        }
        
        inFile.close();
        return items;
    }
    
    
    
    public static Item searchItem(String search) throws FileNotFoundException
    {
        
        boolean found = false;
        
         Item item = null;
        
        DynamicArray items = fetchItems();
        
         int i = 0;
        
        while(i<items.size && !found)
        {
            
             item = (Item) items.array[i];
            
            i++;
            
            if(item.getName().equalsIgnoreCase(search)){
                
                found = true;
                
                break;
                
            }
            
        }
        
        if(!found)
        {   
                return null;
        }
        else
        {    
                return item;
        }
        
        
        
        
    }
    
    
    
    public static void editItem(Item item, boolean flag)
    {
        
        Item target = null;
        
        int quantity;
        
        for(int i=0;i<inventory.size;i++)
        {
            
            
            
             target = (Item) inventory.array[i];
            
             if(flag)
            {
                
                 quantity= target.getQuantity() -item.getQuantity();
                
            }
            else
            {
                
                    quantity =target.getQuantity() + item.getQuantity();
                
            }
            
            
            if(quantity<0){
                
                quantity = 0;
                
            }
                
            
            if(item.getName().equals(target.getName()))
            {
                
                target.setQuantity(quantity);
                
                inventory.array[i] = target;
                
            }
            
        }
            
        
    }
    
    
    
    public static int isExist(String name) {
        
        Item item;
        
        for(int i=0; i<inventory.size; i++){
            item = (Item) inventory.array[i];
            if(item.getName().equalsIgnoreCase(name)){
                return i;
            }
        }
        
        return -1;
    }
}
