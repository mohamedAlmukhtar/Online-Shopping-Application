/*------------------------------------------------------------- 
// AUTHOR: Mohamed Mokhtar
// SPECIFICATION: Final Project (Online Shop Application) class
// FOR: CSE 110 - Final Project
// TIME SPENT: -
//-----------------------------------------------------------*/
package onlineshop;



public class DynamicArray {
    
    Object array[];
    int size = 0;
    int index = 0;
    
    public DynamicArray(){
        array = new Object[size];
    }
    
    public DynamicArray(int size){
        this.size = size;
        array = new Object[size];
        
    }
    
    public void destroy(){
        this.size = 0;
        this.index = 0;
    }
    
    public void add(Object element){
        
        if(index >= size){
            if(isEmpty()){
                size++;
                array = new Object[size];
            }else{
                Object[] temp = store();
                size++;
                array = new Object[size];
                recover(temp);
            }
            
        }
        
        
        array[index] = element;
        index++;
        
        
    }
    
    public void remove(int i){
        
        if((i + 1) == size){
            
            array[i] = null;
            
        }
        else{
            for(int j=i; j<size-1; j++){
                array[j] = array[j+1];
            }
            
        }
        
        size--;
        this.index--;
        
        
    }
    
    public Object[] store(){
        if(!isEmpty()){
            Object temp[] = new Object[index + 1];
            for(int i=0;i<index;i++){
                temp[i] = array[i];
            }

            return temp;
        }
        return null;
    }
    
    public void recover(Object[] temp){
        if(!isEmpty()){
            for(int i=0;i<index;i++){
                array[i] = temp[i];
            }
        }
    }

    public boolean isEmpty() {
        if(size<=0)
            return true;
        else
            return false;
    }

    
}
