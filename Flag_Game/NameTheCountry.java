 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;

public class NameTheCountry extends Frame{
    static String[] Countries;
    static Data_Manager DM = new Data_Manager();
    static int rand, count = 0, score = 1; static String selected;
    static Icon Selected; static String contents[];
    static JButton[] Names = new JButton[4];
    static Label[] labels = new Label[3];
    static Button Level = new Button();
    static JButton Flag = new JButton();
    static Button Next = new Button("NEXT");
    static boolean Won = false;
    static NameTheCountry NTC = new NameTheCountry();
    
    public NameTheCountry(){
        setSize(450,540);
        setTitle("Name The Country");
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                System.exit(0);
            }
        });
        setBackground(Color.CYAN);
        setVisible(true);
        setLocationRelativeTo(null);
        
        DM.openFile("Names.txt");
        DM.countElements();
        Countries = DM.returnData("Names.txt");
        DM.closeFile();
    }
    
    public static void main(String... args){
        int i;
        
        File folder = new File("Flags NTC");
        contents = folder.list();
        
        String SelCountries[] = new String[4];
        String CopyCountries[] = Countries;
        int c = 0;
        
        while(c<4){
            int ran = (int)(Math.round(Math.random() * (CopyCountries.length - 1)));
            SelCountries[c] = CopyCountries[ran];
            CopyCountries = NTC.removeElm(CopyCountries, CopyCountries[ran]);
            c++;
        }
        
        rand = (int)Math.round(Math.random() * (SelCountries.length - 1));
        selected = SelCountries[rand];
        Selected = new ImageIcon("Flags NTC\\"+contents[NTC.returnIndex(Countries, selected)]);
        
        for(i=0;i<Names.length;i++){
            Names[i] = new JButton(SelCountries[i]);
            Names[i].setName(SelCountries[i]);
            Names[i].setBounds(40+((i%2)*190), 290 + ((i/2)*100),170,80);
            Names[i].setBackground(Color.white);
            Names[i].setBorder(BorderFactory.createLineBorder(Color.RED,1));
            Names[i].addActionListener(new Options());
            Names[i].setFocusPainted(false);
        }
        
        for(i=0;i<labels.length;i++){
            labels[i] = new Label();
            if(i==0){
                labels[i].setText("Name The Country".toUpperCase());
                labels[i].setForeground(Color.RED);
                labels[i].setBounds(85,45,270,40);
                labels[i].setFont(new Font("",Font.BOLD,26));
            }
            if(i==1){
                labels[i].setText("You won !".toUpperCase());
                labels[i].setBounds(150,485,350,40);
                labels[i].setVisible(false);
                labels[i].setFont(new Font("",Font.PLAIN,25));
            }
            if(i==2){
                labels[i].setText("Lvl.");
                labels[i].setBounds(25,33,80,30);
                labels[i].setFont(new Font("",Font.PLAIN,24));
            }
        }
        
        Flag.setBounds(90,110,260,140);
        Flag.setIcon(Selected);

        Button error = new Button();
        error.setVisible(false);
        
        Level.setBounds(22,63,40,40);
        Level.setLabel(""+score);
        Level.setEnabled(false);
        Level.setFont(new Font("",Font.BOLD,18));
        Level.setBackground(Color.YELLOW);
        
        Next.setBackground(Color.YELLOW);
        Next.setBounds(375,42,60,35);
        Next.addActionListener(new NextCommand());
        Next.setName("Next");
        Next.setVisible(false);
        
        for(i=0;i<Names.length;i++)
        NTC.add(Names[i]);
        for(i=0;i<labels.length;i++)
        NTC.add(labels[i]);
        NTC.add(Level);
        NTC.add(Next);
        NTC.add(Flag);
        NTC.add(error);
    }
    
    public static void Bring(boolean b){
        NTC.setVisible(true);
    }
    
    int returnIndex(String[] str, String s){
        for(int j=0;j<str.length;j++)
        if(str[j].equals(s))
        return j;
        return 0;
    }
    
    String[] removeElm(String[] Str, String s){
        String[] x = new String[Str.length - 1];
        for(int j=0,i=0;j<Str.length;j++,i++)
        if(Str[j].equals(s)==true){
            i--;
        }
        else
        x[i] = Str[j];
        return x;
    }
    
    static class Options implements ActionListener
    {
        public void actionPerformed(ActionEvent ae)
        {
            if(count<2&&!Won){
            JButton b = (JButton)ae.getSource();
            if(b.getName().equals(selected)){
                labels[1].setText("You won !".toUpperCase());
                labels[1].setBounds(150,485,350,40);
                labels[1].setVisible(true);
                score++;
                Won = true;
                Next.setLabel("NEXT");
                Next.setName("Next");
                Next.setVisible(true);
                b.setBackground(Color.GREEN);
                b.setBorder(BorderFactory.createLineBorder(new Color(34,139,34),4));
            }
            else{
                count++;
                b.setBackground(Color.RED);
                b.setBorder(BorderFactory.createLineBorder(Color.red,6));
                b.setBackground(Color.cyan);
            }
            
            if(count>1){
                labels[1].setText("You lost".toUpperCase());
                Next.setLabel("BACK");
                Next.setName("Back");
                Next.setVisible(true);
                labels[1].setBounds(155,485,350,40);
                labels[1].setVisible(true);
                Names[rand].setBackground(Color.GREEN);
                Names[rand].setBorder(BorderFactory.createLineBorder(new Color(34,139,34),4));
            }
            }
        }
    }
    
    static class NextCommand implements ActionListener
    {
        public void actionPerformed(ActionEvent ae){
            Button b = (Button) ae.getSource();
            if(b.getName().equals("Next")){
            Level.setLabel(""+score);
            String SelCountries[] = new String[4];
            String CopyCountries[] = Countries;
            int c = 0;
        
            while(c<4){
                int ran = (int)(Math.round(Math.random() * (CopyCountries.length - 1)));
                SelCountries[c] = CopyCountries[ran];
                CopyCountries = NTC.removeElm(CopyCountries, CopyCountries[ran]);
                c++;
            }
        
            rand = (int)Math.round(Math.random() * (SelCountries.length - 1));
            selected = SelCountries[rand];
            Selected = new ImageIcon("Flags NTC\\"+contents[NTC.returnIndex(Countries, selected)]);
            Flag.setIcon(Selected);
            
            for(int i=0;i<Names.length;i++){
                Names[i].setText(SelCountries[i]);
                Names[i].setName(SelCountries[i]);
                Names[i].setBackground(Color.white);
                Names[i].setBorder(BorderFactory.createLineBorder(Color.RED,1));
            }
            
            Won = false;
            count = 0;
            labels[1].setVisible(false);
            Next.setVisible(false);
            
            DM.openFile("BEST NTC.txt");
            int best = DM.returnData();
            
            if(score>best)
            DM.editData("BEST NTC.txt", score);
            
            DM.closeFile();
            }
            else if(b.getName().equals("Back"))
            {
                NTC.setVisible(false);
                StartUI.Visible = true;
                StartUI.main();
                String ConCopy[] = contents;
                score = 1;
                Won = false;
                count = 0;
                Level.setLabel(""+score);
            String SelCountries[] = new String[4];
            String CopyCountries[] = Countries;
            int c = 0;
        
            while(c<4){
                int ran = (int)(Math.round(Math.random() * (CopyCountries.length - 1)));
                SelCountries[c] = CopyCountries[ran];
                CopyCountries = NTC.removeElm(CopyCountries, CopyCountries[ran]);
                c++;
            }
        
            rand = (int)Math.round(Math.random() * (SelCountries.length - 1));
            selected = SelCountries[rand];
            Selected = new ImageIcon("Flags NTC\\"+contents[NTC.returnIndex(Countries, selected)]);
            Flag.setIcon(Selected);
            
            for(int i=0;i<Names.length;i++){
                Names[i].setText(SelCountries[i]);
                Names[i].setName(SelCountries[i]);
                Names[i].setBackground(Color.white);
                Names[i].setBorder(BorderFactory.createLineBorder(Color.RED,1));
            }
            
            Won = false;
            count = 0;
            labels[1].setVisible(false);
            Next.setVisible(false);
            
            DM.openFile("BEST NTC.txt");
            int best = DM.returnData();
            
            if(score>best)
            DM.editData("BEST NTC.txt", score);
            
            DM.closeFile();
            }
        }
    }
}