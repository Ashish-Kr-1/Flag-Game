import java.util.*;
import java.io.*;

public class Data_Manager
{
    Scanner in;
    int elements = 0;
    public void openFile(String st){
        try{
            in = new Scanner(new File(st));
        }
        catch(Exception e)
        {
            System.out.println("Could not open "+st);
        }
    }
    
    public void countElements(){
        String s;
        while(in.hasNextLine()){
            s = in.nextLine();
            elements++;
        }
    }
    
    public int returnData(){
        int j = 1;
        while(in.hasNext())
        j = Integer.parseInt(in.next());
        return j;
    }
    
    public void editData(String s, int n){
        try{
            FileWriter x = new FileWriter(new File(s));
            x.write(n+"");
            x.close();
        }
        catch(Exception e){
        }
    }
    
    public String[] returnData(String s){
        in.close();
        openFile(s);
        String[] demo = new String[100];
        int i=0;
        while(in.hasNextLine()){
            demo[i] = in.nextLine();
            i++;
        }
        
        for(int h=0;h<100;h++)
        if(demo[h]==null){
            i = h;
            break;
        }
        
        String[] data = new String[i];
        for(int h=0;h<i;h++){
            data[h] = demo[h];
        }
        return data;
    }
    
    public void closeFile(){
        in.close();
    }
}