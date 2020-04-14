/*------------------------------------------------------------- 
// AUTHOR: Mohamed Mokhtar
// FILENAME: OnlineShop.java
// SPECIFICATION: Final Project (Online Shop Application) class
// FOR: CSE 110 - Final Project
// TIME SPENT: -
//-----------------------------------------------------------*/
package onlineshop;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author abdel
 */
public class Inventory {
    
    static File inventoryFile = new File("src/onlineshop/datafiles/inventory.txt");
    
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
        
        return items;
    }
}
