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
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Scanner;


public class Employee {
    
    static DynamicArray employees;
    
    Scanner in = new Scanner(System.in);
    
    static File employeeFile = new File("src/onlineshop/employees.txt");
    
    protected int ID;
    protected String firstName;
    protected String lastName;
    protected String position;
    protected String userID;
    
    String  employeeEvents = "\n1) Checkout Customer" + "\n2) Check Inventory"
                            + "\n3) Review current online orders" + "\n4) Review a customer's order"
                            + "\n5) Delete a customer's order" + "\n6) Create customer account";

    
    public Employee(){
        
    }
    
    public Employee(int ID, String firstName, String lastName, String position, String userID) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.userID = userID;
    }
    
    
    
    public void employeeEvents() throws FileNotFoundException, IOException{
        
        do{
            System.out.println("\n\nSelect one of the following options : ");
            System.out.println(employeeEvents);
            System.out.println("7) Log out");

            switch(in.nextInt()){

                case 1: checkoutCustomer();
                        break;
                
                case 2: checkInventory();
                        break;
                        
                case 3: Order.reviewOrders(false, null);
                        break;

                case 4: reviewOrder();
                        break;

                case 5: removeOrder();
                        System.out.println("\nOrder was deleted successfully");
                        break;

                case 6: OnlineShop.addCustomer();
                        System.out.println("\nCustomer was added successfully");
                        break;
                        
                case 7: return;
                default: System.out.println("Invalid Option");
            }
        }while(true);
    }//End Method
    
    
    public static DynamicArray fetchEmployees() throws FileNotFoundException{
        
        DynamicArray employees = new DynamicArray();
        String entry;
        String[] parts;
        Scanner inFile = new Scanner(new FileReader(employeeFile));
        
        while(inFile.hasNextLine()){
            entry = inFile.nextLine();
            parts = OnlineShop.breakEntry(entry, 5);
            
            int ID = Integer.parseInt(parts[0]);
            employees.add(new Employee(ID, parts[1], parts[2], parts[3], parts[4]));
            
        }
        
        inFile.close();
        return employees;
        
    }//End Method
    
    
    protected void checkInventory(){
        
        NumberFormat frm = NumberFormat.getCurrencyInstance();
        
        String name;
        Item item;
        
        System.out.print("\nWhich item do want to check? Please enter product name: ");
        name = in.next();
        
        for(int i=0; i<Inventory.inventory.size; i++){
            item = (Item) Inventory.inventory.array[i];
            if(item.getName().equalsIgnoreCase(name)){
                System.out.println("Name: " + item.getName() + " Number in Stock: "
                        + item.getQuantity() + " Price Per Unit: " + frm.format(item.getPrice()));
            }
        }
        
    }//End Method
    
    
    protected void checkoutCustomer() throws FileNotFoundException{
        
        NumberFormat frm = NumberFormat.getCurrencyInstance();
        String choice;
        
        Item item;
        DynamicArray items = Order.reviewOrders(true,null);
        
        for(int i=0; i<items.size; i++){
            item = (Item) items.array[i];
            System.out.println("\n" + item.getName() + "\t" + item.getQuantity()
                    + "\t" + frm.format(item.getPrice()));
        }
        
        System.out.print("\nWould you like to checkout this order (y/n)? ");
        choice = in.next();
        
        if(choice.equalsIgnoreCase("y")){
            System.out.println("\nOrder was processed successfully");
        }
        else{
            System.out.println("\nOrder is still due for process");
        }
    }//End Method
    
    
    protected void reviewOrder() throws FileNotFoundException {
        
        NumberFormat frm = NumberFormat.getCurrencyInstance();
        
        Item item;
        DynamicArray items = Order.reviewOrders(true, null);
        
        for(int i=0; i<items.size; i++){
            item = (Item) items.array[i];
            System.out.println("\n" + item.getName() + "\t" + item.getQuantity()
                    + "\t" + frm.format(item.getPrice()));
        }
        
    }//End Method
    
    
    protected void removeOrder() throws FileNotFoundException{
        
        int choice;
        File folder = new File("src/onlineshop/");
        File[] listOfFiles = folder.listFiles();
        String pattern = "COMP-";
        boolean flag;
        
        System.out.println("\nSelect Order to delete : ");
        int i = 0;
        for (File file : listOfFiles) {
            flag = file.getName().substring(0, 5).equals(pattern);
            if (file.isFile() && flag) {
                i++;
                System.out.println(i + " " + file.getName());
            }
        }
        
        
        choice = Integer.parseInt(in.next());
        
        listOfFiles[choice].delete();
        
    }//End Method
    
    
    public static int isExist(String userID) {
        
        Employee employee;
        
        for(int i=0; i<employees.size; i++){
            employee = (Employee) employees.array[i];
            if(employee.getUserID().equalsIgnoreCase(userID)){
                return i;
            }
        }
        
        return -1;
    }//End Method
    

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
    
    public String getUserID() {
        return userID;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public String getPosition() {
        return position;
    }
    
    
    @Override
    public String toString(){
        
        return "" + getID() + "," + getFirstName() + "," +
                getLastName() + "," + getPosition() + "," + getUserID();
        
    }
    
}//End Class
