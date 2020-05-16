/*------------------------------------------------------------- 
// AUTHOR: Mohamed Mokhtar
// SPECIFICATION: Final Project (Online Shop Application) class
// FOR: CSE 110 - Final Project
// TIME SPENT: -
//-----------------------------------------------------------*/

package onlineshop;

import java.io.FileNotFoundException;
import java.util.Scanner;


public class Manager extends Employee
{
    
    
    Scanner in = new Scanner(System.in);

    String  managerEvents = "\n7) Enter an item to inventory" + "\n8) Modify an item"
            
                            + "\n9) Delete an item" + "\n10) Add an employee" + "\n11) Remove an employee"
            
                            + "\n12) Display all Employees"
            
                            + "\n13) Modify an employee" + "\n14) Log out";
    
    
    
    public Manager(int ID, String firstName, String lastName, String position, String userID) {
        super(ID,firstName,lastName,position,userID);
    }
    
    public Manager() {
        
    }
    
    public void managerEvents() throws FileNotFoundException{
        
        Scanner in = new Scanner(System.in);
        
        do{
            System.out.println("\n\nSelect one of the following options : ");
            System.out.println(employeeEvents + managerEvents);

            switch(in.nextInt()){

                case 1: checkoutCustomer();
                        break;
                
                case 2: checkInventory();
                        break;

                case 3: Order.reviewOrders(false,null);
                        break;

                case 4: reviewOrder();
                        break;
                
                case 5: removeOrder();
                        System.out.println("\nOrder was deleted successfully");
                        break;

                case 6: OnlineShop.addCustomer();
                        System.out.println("\nCustomer was added successfully");
                        break;
                
                case 7: if(addItem())
                            System.out.println("\nItem was added succesfully");
                        break;
                       
                case 8: if(modifyItem())
                            System.out.println("\nItem was modifyed succesfully");
                        break;
                        
                case 9: if(deleteItem())
                            System.out.println("\nItem was deleted succesfully");
                        break;
                        
                        
                case 10: if(addEmployee())
                            System.out.println("\nEmployee was added succesfully");
                        break;
                
                case 11: if(removeEmployee())
                            System.out.println("\nEmployee was removed succesfully");
                        break;
                        
                case 12: displayEmployees();
                         break;
                         
                case 13: if(modifyEmployee())
                            System.out.println("\nEmployee was modifyed succesfully");
                         break;
                
                case 14: return;
                default: System.out.println("Invalid Option");
            }
        }while(true);
    }
    
    
    public int submitName()
    {
        
        String name;
        
        int index;
        
        System.out.print("\nEnter item's name : ");
        
        name = in.next();
        
        
         index = Inventory.isExist(name);
        
        
        return index;
        
        
        
    }
    
    
    
    public boolean addItem()
    {
        
        Item item = submitItem();
        
        if(!(item == null))
        {
            
            Inventory.inventory.add(item);
            
            return true;
            
        }
        else
        {
            return false;
            
        }
        
    }
    
    
    
    public boolean deleteItem()
    {
        
        int num = submitName();
        
        
        if(num == -1)
        {
            
            System.out.println("\nItem does not exist in inventory");
            return false;
            
        }
        
        Inventory.inventory.remove(num);
        
        
        return true;
        
    }
    
    
    
    public boolean modifyItem()
    {
        
        Item item;
        
        String input;
        
        int num = submitName();
        
        if(num==-1)
        {
            
            System.out.println("Item does not exist in inventory");
            
            return false;
            
        }
        
        item = (Item) Inventory.inventory.array[num];
        
        
        do{
            
            
            System.out.println("\nWhat to modify : "
                    
                + "\n1 Modify name" + "\n2 Modify quantity" + "\n3 Modify price" + "\n4 save and stop");
            
            
            input = in.next();
            
            
            switch(input)
            {

                case "1": System.out.println("Enter New name : ");
                
                          in.nextLine();
                          
                          item.setName(in.nextLine());
                          
                          break;

                case "2": System.out.println("Enter New quantity : ");
                
                          item.setQuantity(in.nextInt());
                          
                          break;

                case "3": System.out.println("Enter New price : ");
                
                          item.setPrice(in.nextDouble());
                          
                          break;
                        
                case "4": Inventory.inventory.array[num] = item;
                
                          break;

            }
        
        }while(!input.equals("4"));
        
        return true;
        
        
        
    }
    
    public Item submitItem()
    {
        
        Item item;
        
        String name;
        
        int quantity;
        
        double price;
        
        
        System.out.print("\nEnter item's name : ");
        
        name = in.next();
        
        if(Inventory.isExist(name) != -1)
        {
            System.out.println("Item already exist in inventory");
            
            return null;
        }
        
        System.out.print("Enter item's quantity : ");
        
        quantity = in.nextInt();
        
        System.out.print("Enter item's price : ");
        
        price = in.nextDouble();
        
        
        item = new Item(name, quantity, price);
        
        
        return item;
        
        
    }
    
    
    
    public void displayEmployees()
    {
        
        Employee employee;
        
        System.out.println("");
        
        
        for(int i=0; i<Employee.employees.size; i++)
        {
            
                employee = (Employee) Employee.employees.array[i];
            
             System.out.println("Employee ID: " + employee.getID() +
                    
                    " First Name: " + employee.getFirstName() +
                    
                    " Last Name: " + employee.getLastName() + " Position: " +
                    
                    employee.getPosition() + " Login ID: " + employee.userID);
            
        }
        
        
        
    }
    
    
    
    public boolean removeEmployee()
    {
        
        int[] num = submitEmpID();
        
        if(num[0]==-1)
        {
            
            System.out.println("Employee does not exist");
            
            return false;
            
        }
        
        Employee.employees.remove(num[0]);
        Account.accounts.remove(num[1]);
        
        
        return true;
        
        
    }
    
    
    
    public boolean addEmployee()
    {
        
         Employee employee = submitEmployee();
        
        
        if(!(employee==null))
        {
            
                Employee.employees.add(employee);

                return true;
            
        }
        else
        {
            
            return false;
        }
        
        
    }
    
    
    public int[] submitEmpID()
    {
        
        String userID;
        
        int[] num = new int[2];
        
        System.out.print("\nEnter the employee's user ID : ");
        
        userID = in.next();
        
        num[0] = Employee.isExist(userID);
        num[1] = Account.isExist(userID);
        
        return num;
        
        
    }
    
    
    public Employee submitEmployee()
    {
        
        Employee employee;
        
        Account account;
        
        int ID;
        
        String fname, lname, uID, pos, pass;

        System.out.print("\nPlease Enter the employee number : ");
        
        ID = in.nextInt();
        
        
        System.out.print("Please Enter the employee first name : ");
        
        fname = in.next();
        
        System.out.print("Please Enter the employee last name : ");
        
        lname = in.next();
        
        System.out.print("Please Enter the employee position : ");
        
        pos = in.next();
        
            System.out.print("Please enter the employee user ID : ");

            uID = in.next();
        
         System.out.print("Please Enter the employee password : ");
        
        pass = in.next();
        
        
        employee = new Employee(ID, fname, lname, pos, uID);
        
        
        account = new Account(uID,pass,pos);
        
        
        Account.accounts.add(account);
        
        
        return employee;
        
        
        
    }
    
    
   
    public boolean modifyEmployee()
    {
        
        Employee employee;
        Account account;
        String input;
        
        int[] num = submitEmpID();
        
        if(num[0]==-1){
            System.out.println("Employee does not exist.");
            return false;
        }
        
        employee = (Employee) Employee.employees.array[num[0]];
        
        int accountIndex = Account.isExist(employee.getUserID());
        
        account = (Account) Account.accounts.array[accountIndex];
        
        
        
        do{
            
            System.out.println("\nWhat to modify : "
                    
                + "\n1 Modify Employee ID" + "\n2 Modify First Name" + "\n3 Modify Last Name"
                    
                    + "\n4 Modify login" + "\n5 Modify password" + "\n6 Modify position" + "\n7 save and stop");
            
            input = in.next();
            
            switch(input){

                case "1": System.out.println("Enter updated ID : ");
                
                          employee.setID(in.nextInt());
                          
                          break;

                case "2": System.out.println("Enter updated First Name : ");
                
                          employee.setFirstName(in.next());
                          
                          break;

                case "3": System.out.println("Enter updated Last Name : ");
                
                          employee.setLastName(in.next());
                          
                          break;
                          
                case "4": System.out.println("Enter updated LogIn ID : ");
                
                          String newUserID = in.next();
                          
                          employee.setUserID(newUserID);
                          
                          account.setUsername(newUserID);
                          
                          break;
                          
                case "5": System.out.println("Enter updated password : ");
                
                          account.setPassword(in.next());
                          
                          break;
                
                case "6": System.out.println("Enter updated employee position : ");
                
                          String newPosition = in.next();
                          
                          employee.setPosition(newPosition);
                          
                          account.setType(newPosition);
                          
                          break;
                          
                          
                case "7": Employee.employees.array[num[0]] = employee;
                
                          Account.accounts.array[accountIndex] = account;
                          
                          break;

            }
        
            
        }while(!input.equals("7"));
        
        return true;
        
        
    }
    
}
