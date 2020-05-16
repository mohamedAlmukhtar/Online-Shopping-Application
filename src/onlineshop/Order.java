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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.Scanner;
import java.text.SimpleDateFormat;  
import java.util.Date;  


public class Order {
    
    boolean saved = true;
    
    DynamicArray orders = new DynamicArray();
    File orderFile = null;
    
    public Order(String customerName) throws FileNotFoundException{
        
        orderFile = new File("src/onlineshop/" + customerName + ".txt");
        
    }
    
    
    
    public void printOrder()
    {
        
        
        NumberFormat frm = NumberFormat.getCurrencyInstance();
        
        
        if(this.orders.isEmpty())
        {
            
            
            System.out.println("\nAdd an item, or load a saved order.");
            
            return;
            
            
        }
        
        
        Item item;
        
        
        for(int i=0;i<orders.size;i++)
        {
            
            item = (Item) orders.array[i];
            
                System.out.println("\n" + item.getName() + "\t" + item.getQuantity()
                    
                    + "\t" +frm.format(item.getPrice()));
            
        }
        
        
    }
    
    
    public void deleteCurrentOrder()
    {
        
        
        Item item;
        
        if(orders.isEmpty())
        {
            
            System.out.println("\nAdd an item, or load a saved order.");
            
        }
        
        
        for(int i=0;i<orders.size;i++)
        {
            
            item = (Item) orders.array[i];
            
            Inventory.editItem(item, false);
            
        }
        
        orders.destroy();
        
        saved = true;
        
        System.out.println("\nOrder deleted.");
        
    }
    
    
    
    public void deleteSavedOrder()
    {
        
        orderFile.delete();
        
    }
    
    
    public void saveOrder() throws IOException{
        
        Scanner in = new Scanner(System.in);
        
        String entry;
        String choice;
        Item item;
        
        if(orders.isEmpty()){
            System.out.println("\nAdd an item, or load a saved order.");
            return;
        }
        
        if(orderFile.exists()){
            System.out.print("\nDo you want to overwrite previously saved file (y/n)? ");
            choice = in.next();
            if (choice.equalsIgnoreCase("n")) {
                System.out.println("\nChanges were NOT saved.");
                return;
            }
        }
        
        
        FileWriter writer = new FileWriter(orderFile);
        PrintWriter outFile = new PrintWriter(writer);
        
        
        for(int i=0; i<orders.size; i++){
            item = (Item) orders.array[i];
            entry = item.getName() + "," + item.getQuantity() + "," + item.getPrice();

            outFile.println(entry);

        }
        
        orders.destroy();
        saved = true;
        System.out.println("\nOrder saved successfully");
        outFile.close();
        
    }
    
    
    public void completeOrder(String customerName) throws IOException{
        
        if(orders.isEmpty()){
            System.out.println("\nNo order to complete");
            return;
        }
        
        Item item;
        String entry;
        
        SimpleDateFormat formatter = new SimpleDateFormat("-dd-MM-yyyy-HH-mm-ss");  
        Date date = new Date();  
        
        File complete = new File("src/onlineshop/" +
                    "COMP-" + customerName + formatter.format(date) + ".txt");
        
        complete.createNewFile();
        
        FileWriter writer = new FileWriter(complete);
        
        PrintWriter outFile = new PrintWriter(writer);
        
        for(int i=0; i<orders.size; i++){
            item = (Item) orders.array[i];
            entry = item.getName() + "," + item.getQuantity() + "," + item.getPrice();

            outFile.println(entry);

        }
        
        deleteSavedOrder();
        
        outFile.close();
    }
    
    
    
    public final void fetchOrder() throws FileNotFoundException{
        
        int index = 0;
        String entry;
        String parts[];
        Scanner inFile = new Scanner(orderFile);
        
        while(inFile.hasNextLine()){
            entry = inFile.nextLine();
            parts = OnlineShop.breakEntry(entry, 3);
            
            int quantity = Integer.parseInt(parts[1]);
            double price = Double.parseDouble(parts[2]);
            Item item = new Item(parts[0],quantity,price);
            
            orders.add(item);
            index++;
        }
        
        inFile.close();
    }
    
    
    public static DynamicArray reviewOrders(boolean review, String username) throws FileNotFoundException {
        
        Scanner in = new Scanner(System.in);
        
        int choice, count = 0;
        String pattern;
        String filename;
        boolean flag;
        String[] parts;
        DynamicArray items = new DynamicArray();
        
        
        File folder = new File("src/onlineshop/");
        File[] listOfFiles = folder.listFiles();
        
        if(review)
            System.out.println("\nSelect Order : \n");
        
        System.out.println();
        
        int i = 0;
        for (File file : listOfFiles) {
            
            filename = file.getName();
            
            if(username == null){
                pattern = "COMP-";
                flag = filename.substring(0, 5).equals(pattern);
            }
            else{
                pattern = "COMP-" + username;
                if(filename.length() < (5+username.length()))
                    flag = false;
                else
                    flag = filename.substring(0, (5 + username.length())).equals(pattern);
            }
            
            if (file.isFile() && flag) {
                i++;
                System.out.println(i + " " + filename);
                count++;
            }
        }
        
        if(count == 0)
            System.out.println("\nNo Online orders detected.");
        
        if(review){
            
            choice = Integer.parseInt(in.next());
            Scanner inFile = new Scanner(new FileReader(listOfFiles[choice]));

            while(inFile.hasNextLine()){

                parts = OnlineShop.breakEntry(inFile.nextLine(),3);

                items.add(new Item(parts[0],Integer.parseInt(parts[1]),Double.parseDouble(parts[2])));
            }
            
        }
        
        return items;
        
    }
    
    
    
    public Object[] submitProduct() throws FileNotFoundException, IOException
    {
        
        String product;
        
        int quantity;
        
         Object[] input = new Object[2];
        
        Scanner in = new Scanner(System.in);
        
        System.out.println("\nEnter name of product: ");
        
        
         product = in.next();
        
        System.out.println("Enter quantity: ");
        
        quantity = in.nextInt();
        
        
       input[0] = product;
        
       input[1] = quantity;
        
        
        return input;
        
    }
    
    
    public int checkQuantity(int buy,int stock)
    {
        
        Scanner in = new Scanner(System.in);
        
        if(stock<0)
        {
            stock = 0;
        }
        
        
        if(buy>stock)
        {
            
            
                do{

                    System.out.println("\nItem you want to buy only has " + stock + " in stock");

                    System.out.println("Please change the quantity : ");

                    buy = in.nextInt();



                }while(buy>stock);
            
            
        }
        
        
         return buy;
    }

    
    //-----------------------------------
    // Author of this method
    // Mohamed Mokhtar
    //-----------------------------------
    public void addItem(String product, int quantity) throws FileNotFoundException, IOException{
        
        if(orderFile == null)
            orderFile.createNewFile();
        
        Item item, temp;
        int exist;
        
        item = Inventory.searchItem(product);
        
        if(item == null){
            System.out.println("\nItem (" + product + ") does not exist.");
            return;
        }
        
        if(item.getQuantity() == 0){
            System.out.println("\nUnfortunetly, no " + item.getName() + " in stock at the moment");
            return;
        }
        
        quantity = checkQuantity(quantity, item.getQuantity());
        
        exist = searchItem(product);
        
        if(exist>=0){
            temp = (Item) orders.array[exist];
            temp.setQuantity(temp.getQuantity() + quantity);
            temp.setPrice(item.getPrice() * temp.getQuantity());
            orders.array[exist] = temp;
        }
        else{
            item.setPrice(item.getPrice() * quantity);
            item.setQuantity(quantity);
            orders.add(item);
        }
        
        System.out.println("\nItem was added.");
        
        Inventory.editItem(item,true);
        saved = false;
        
    }
    
    
    
    public void removeItem(String product, int quantity) throws FileNotFoundException{
        
        Item item, temp, temp2;
        int exist;
        
        exist = searchItem(product);
        
        if(exist<0){
            System.out.println("Item (" + product + ") does not exist.");
            return;
        }
        
        item = (Item) orders.array[exist];
        
        double price = item.getPrice() / item.getQuantity();
        
        if(item.getQuantity() <= quantity){
            orders.remove(exist);
            Inventory.editItem(item, false);
        }
        else{
            temp = item;
            temp.setQuantity(temp.getQuantity() - quantity);
            temp.setPrice(price * temp.getQuantity());
            orders.array[exist] = temp;
            temp2 = new Item(temp.getName(),temp.getQuantity(),temp.getPrice());
            temp2.setQuantity(quantity);
            Inventory.editItem(temp2, false);
        }
        
        System.out.println("\nItem(s) removed.");
        
        saved = false;
        
    }
    
    
    
    public int searchItem(String search) throws FileNotFoundException
    {
        
        Item item = null;
        
         int i = 0;
        
        while(i<orders.size)
        {
            
             item = (Item) orders.array[i];
            
                if(item.getName().equalsIgnoreCase(search))
                {

                    return i;


                }
            
            
            i++;
            
        }
        
        
        return -1;
        
    }
    
        
}
