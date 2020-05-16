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


public class Customer {

    Scanner in = new Scanner(System.in);
    
    static DynamicArray customers = new DynamicArray();
    
    static File customerFile = new File("src/onlineshop/customer.txt");
    
    private static Order order;
    static String  customerEvents = "\n\nSelect one of the following options : "
                            + "\n1) Show Products" + "\n2) Search for a product"
                            + "\n3) Shop" + "\n4) Load saved Order" + "\n5) Checkout"
                            + "\n6) Create Account" + "\n7) Log out";

    private String firstName;
    private String lastName;
    private String streetAddress;
    private String city;
    private String state;
    private String zip;
    
    
    public Customer(){
        
    }
    
    public Customer(String firstName, String lastName, String streetAddress, String city, String state, String zip) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.streetAddress = streetAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }
    
    
    
    public void customerEvents() throws FileNotFoundException, IOException {
        
        
        do{
            System.out.println(customerEvents);

            switch(in.nextInt()){

                case 1: Inventory.printInventory();
                        break;

                case 2: searchProduct();
                        break;

                case 3: boolean flag = placeOrder();
                        shop(flag);
                        break;

                case 4: order = fetchOrder(OnlineShop.session.getUsername());
                        if(order != null)
                            order.printOrder();
                        else
                            System.out.println("\nNo order in records");
                        break;
                        
                case 5: checkout();
                        break;
                    
                case 6: OnlineShop.addCustomer();
                        System.out.println("\nAccount created succesfully");
                        break;
                        
                case 7: if(order != null && !order.saved){
                            warning();
                        }
                        return;
                        
                default: System.out.println("Invalid Option");
            }
        }while(true);
        
        
    }
    
    
    public static DynamicArray fetchCustomers() throws FileNotFoundException{
        
        DynamicArray custs = new DynamicArray();
        String entry;
        String[] parts;
        Scanner inFile = new Scanner(new FileReader(customerFile));
        
        while(inFile.hasNextLine()){
            entry = inFile.nextLine();
            parts = OnlineShop.breakEntry(entry, 6);
            
            custs.add(new Customer(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]));
            
        }
        
        inFile.close();
        return custs;
    }
    
    
    public Order fetchOrder(){
        
        return order;
        
    }
    
    
    public Order fetchOrder(String customerName) {
        try {
            order = new Order(customerName);
            order.fetchOrder();
        } catch (FileNotFoundException ex) {
            order = null;
        }
            
        return order;
        
    }
    
    
    public void shop(boolean flag) throws IOException {
        
        String choice;
        Object[] input;
        Scanner in = new Scanner(System.in);
        
        if(!flag){
            order.deleteCurrentOrder();
        }
        
        do{
            System.out.println("\nPress : \n1 to add an item");
            System.out.println("2 to remove an item");
            System.out.println("3 to review order");
            System.out.println("4 to delete order");
            System.out.println("5 to save order");
            System.out.println("6 to stop");
            choice = in.next();
            switch(choice){
                case "1": input = order.submitProduct();
                          order.addItem((String) input[0], (int) input[1]);
                          break;
                case "2": input = order.submitProduct();
                          order.removeItem((String) input[0], (int) input[1]);
                          break;
                case "3": order.printOrder();
                          break;
                case "4": order.deleteCurrentOrder();
                          break;
                case "5": order.saveOrder();
                          break;
                
                case "6": return;
                
                default: System.out.print("Invalid Option");
            }
        }while(true);
        
    }
    
    
    public void searchProduct() throws FileNotFoundException{
        
        NumberFormat frm = NumberFormat.getCurrencyInstance();
        
        Item item;
        String search;
        
        System.out.println("\nEnter name of product : ");
        search = in.next();
        
        item = Inventory.searchItem(search);
        
        if(item == null){
            System.out.println("\nItem NOT found.");
        }
        else{
            System.out.println("\nItem found : ");
            System.out.println(item.getName() + "\t" + item.getQuantity() + "\t" + frm.format(item.getPrice()));
        }
                
    }
    
    
    public boolean placeOrder() throws FileNotFoundException, IOException{
        
        boolean flag = true;
        boolean isValid;
        Scanner in = new Scanner(System.in);
        
        if(order != null && !order.orders.isEmpty()){
            System.out.println("\nYou already have a loaded order.\n");
            order.printOrder();
            do{
                System.out.println("\nPress\n1) to resume order\n2) to overwrite order");
                switch(in.next()){
                    case "1": flag = true;
                            isValid = true;
                            break;
                    case "2": flag = false;
                            isValid = true;
                            break;
                    default: System.out.println("Invalid Input, Please try again:");
                             isValid = false;
                }
            }while(!isValid);
        }
        else{
            System.out.println("\nCreating new order");
            order = new Order(OnlineShop.session.getUsername());
        }
        
        return flag;
        
        
    }
    
    
    
    public void checkout() throws IOException{
        
        if(order == null || order.orders.isEmpty()){
            System.out.println("\nNo order found, add and item or load a saved order");
            return;
        }
        
        
        boolean found = false;
        Customer customer = null;
        String fname,lname,answer;
        
        System.out.print("\nDo you already have an account (y/n)?");
        answer = in.next();
        
        if(answer.equalsIgnoreCase("n")){
            customer = OnlineShop.addCustomer();
        }
        else{
        
            System.out.print("Please enter your first name: ");
            fname = in.next();

            System.out.print("Please enter your last name: ");
            lname = in.next();
            
            for(int i = 0; i<customers.size; i++){
            
                customer = (Customer) customers.array[i];

                if(customer.getFirstName().equalsIgnoreCase(fname)){
                    if(customer.getLastName().equalsIgnoreCase(lname)){
                        found = true;
                        break;

                    }
                }
                
            }
            
        }
        
        if(found){
            printReceipt(customer,order.orders);
            order.completeOrder(OnlineShop.session.getUsername());
            order.deleteCurrentOrder();
        }
        else{
            System.out.println("\nNo Customer with such first or last name");
        }
        
    }
    
    
    public void printReceipt(Customer customer, DynamicArray items){
        
        double total = 0;
        double finalTotal = 0;
        double tax = 0.04;
        Item item;
        
        NumberFormat frm = NumberFormat.getCurrencyInstance();
        
        System.out.println("\nCustomer: " + customer.getFirstName() + " " + customer.getLastName());
        System.out.println(customer.getStreetAddress());
        System.out.println(customer.getCity() + ", " + customer.getState() + " " + customer.getZip());
        System.out.println("\nItems to be purchased: ");
        System.out.println("\nNo.\t\tItem\t\tPrice");
        
        for(int i=0; i<items.size; i++){
            
            item = (Item) items.array[i];
            
            total += item.getPrice();
            System.out.println(item.getQuantity() + "\t\t" + item.getName() + "\t\t" + frm.format(item.getPrice()));
                    
        }
        
        finalTotal = (total * tax) + total;
        
        System.out.println("----------------------------------------");
        System.out.println("Total:\t\t\t\t" + frm.format(total));
        System.out.println("Discount:\t\t\t" + frm.format(0.00));
        System.out.println("Tax:\t\t\t\t" + (tax*100) + "%");
        System.out.println("----------------------------------------");
        System.out.println("Total purchase price:\t\t" + frm.format(finalTotal));
        
    }
    
    
    public void warning() throws IOException{
        
        String choice;
        
        do{
            System.out.println("\nYou have unsaved changes\nSelect: ");
            System.out.println("1 to save and exit\n2 to exit without saving: ");
            choice = in.next();
            switch(choice){
                case "1": order.saveOrder();
                          break;

                case "2": order.deleteCurrentOrder();
                          return;

                default: System.out.println("\nInvalid Option");
            }
        }while(!choice.equals("1"));
        
    }
    
    
    public Order getOrder(){
        return this.order;
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

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
    
    @Override
    public String toString(){
        
        return "" + getFirstName() + "," + getLastName() + "," + getStreetAddress()
                + "," + getCity() + "," + getState() + "," + getZip();
        
    }
    
}
