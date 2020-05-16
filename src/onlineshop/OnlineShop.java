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
import java.io.PrintWriter;
import java.util.Scanner;



public class OnlineShop {

    
    static Scanner in = new Scanner(System.in);
    
    static Account session = new Account();
    static DynamicArray employees;
     
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        System.out.println("Welcome to Online Shop.\n---------------------");
        //System.out.println("These are the current available products:");
        
        File login = new File("src/onlineshop/login.txt");
        
        String selection;
        boolean isLogged;
        
        do{
            
            do{
                isLogged = logIn(login);
                if(isLogged){
                    System.out.println("\nWelcome " + session.getType() 
                            + " " + session.getUsername());
                }
                else{
                    System.out.println("\nInvalid Username or Password\n");
                }
            }while(!isLogged);


            //Customer Login
            if(session != null && session.getType().equals("Customer")){
                //order = Customer.fetchOrder(session.getUsername());

                Inventory.inventory = Inventory.fetchItems();
                Customer.customers = Customer.fetchCustomers();

                Customer customer = new Customer();

                customer.customerEvents();

                saveData(Inventory.inventoryFile, Inventory.inventory);
                saveData(Customer.customerFile, Customer.customers);



            }
            //Employee Login
            else if(session != null && session.getType().equals("Employee")){

                Inventory.inventory = Inventory.fetchItems();
                Customer.customers = Customer.fetchCustomers();

                Employee employee = new Employee();

                employee.employeeEvents();

                saveData(Inventory.inventoryFile, Inventory.inventory);
                saveData(Customer.customerFile, Customer.customers);

            }
            //Manager Login
            else if(session != null && session.getType().equals("Manager")){

                Inventory.inventory = Inventory.fetchItems();
                Employee.employees = Employee.fetchEmployees();
                Account.accounts = Account.fetchAccounts();
                Customer.customers = Customer.fetchCustomers();

                Manager manager = new Manager();

                manager.managerEvents();

                saveData(Inventory.inventoryFile, Inventory.inventory);
                saveData(Employee.employeeFile, Employee.employees);
                saveData(Account.logInFile, Account.accounts);
                saveData(Customer.customerFile, Customer.customers);


            }
            System.out.println("\nDo you want to quit (y/n)? ");
            selection = in.next();
            System.out.println("");
            
        }
        while(!selection.equalsIgnoreCase("y"));
        
        System.out.println("\nThank you for using our service.\nGood Bye");
    }//End Method
    
    
    public static boolean logIn(File login) throws FileNotFoundException{
        
        Scanner inFile = new Scanner(new FileReader(login));
        
        String username, password, entry;
        String parts[] = new String[3];
        
        System.out.println("Enter your username : ");
        username = in.next();
        System.out.println("Enter your password : ");
        password = in.next();
        
        while(inFile.hasNextLine()){
            
            entry = inFile.nextLine();
            parts = breakEntry(entry,3);
            
            if(username.equals(parts[0]) && password.equals(parts[1])){
                session.setUsername(username);
                session.setPassword(password);
                session.setType(parts[2]);
                
                return true;
            }
            
        }
        
        inFile.close();
        
        return false;
        
    }//End Method
    
    
    public static void saveData(File file,DynamicArray data) throws FileNotFoundException
    {
        
        PrintWriter outFile = new PrintWriter(file);
        
        
        for(int i=0;i<data.size;i++)
        {
            
            
            outFile.println(data.array[i].toString());
            
        }
        
        
        outFile.close();
        
    }//End Method
    
    
    public static String[] breakEntry(String entry,int num)
    {
        
        Scanner tokens = new Scanner(entry);
        
         tokens.useDelimiter(",");
        
        
       String parts[] = new String[num];
        
        
        for(int i=0; i<num; i++)
        {
            
                parts[i] = tokens.next();
            
        }
        
        
        return parts;
        
        
        
    }//End Method
    
    
    public static Customer addCustomer()
    {
        
        Scanner in = new Scanner(System.in);
        
        
         Customer customer = new Customer();
        
        
        System.out.print("\nPlease enter your First Name: ");
        
        customer.setFirstName(in.next());
        
        
       System.out.print("Please enter your Last Name: ");
        
       customer.setLastName(in.next());
        
        
        System.out.print("Please enter your Address: ");
        in.nextLine();
        String address = in.nextLine();
        customer.setStreetAddress(address);
        
        
        System.out.print("Please enter your City: ");
        
        customer.setCity(in.nextLine());
        
        
        System.out.print("Please enter your State: ");
        
        
        customer.setState(in.nextLine());
        
            System.out.print("Please enter your Zip: ");

            customer.setZip(in.nextLine());
        
            System.out.println(customer.getStreetAddress());
        
        Customer.customers.add(customer);
        
        
        return customer;
        
    }//End Method
    
    
    
}//End Class
