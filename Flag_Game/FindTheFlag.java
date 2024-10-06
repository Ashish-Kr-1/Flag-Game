 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

public class FindTheFlag extends Frame
{
    static String[] Countries;
    static Data_Manager DM = new Data_Manager();
    static String selected;
    static JButton[] Flags = new JButton[6];
    static JButton[] Marks = new JButton[6];
    static int count = 0, rand, score = 1;
    static FindTheFlag FT = new FindTheFlag();
    static Label Correct = new Label("Correct answer :");
    static Label Result = new Label();
    static Label[] Score = new Label[1]; 
    static String[] SelContents = new String[6];
    static String[] SelCountries = new String[6];
    static Icon[] SelImages = new Icon[6];
    static String[] contents;
    static Label Country = new Label();
    static Button Next = new Button("NEXT");
    static Button Level = new Button();
    static boolean Won = false;
    
    public FindTheFlag(){
        setSize(450,540);
        setTitle("Find the flag");
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                System.exit(0);
            }
        });
        setVisible(true);
        setBackground(Color.CYAN);
        setLocationRelativeTo(null);
        
        DM.openFile("Names.txt");
        DM.countElements();
        Countries = DM.returnData("Names.txt");
        DM.closeFile();
    }
    
    public static void main(boolean b,String... args){
        int i;
        File folder = new File("Flags");
        contents = folder.list();
        
        Icon[] Images = new Icon[contents.length];
        for(i=0;i<contents.length;i++)
        Images[i] = new ImageIcon(contents[i]);
        
        String ConCopy[] = contents;
        String CopyNames[] = Countries;
        int c = 0;
        while(c < 6){
            int ran = (int)Math.round(Math.random() * (ConCopy.length - 1));
            SelContents[c] = ConCopy[ran];
            SelCountries[c] = CopyNames[ran];
            ConCopy = FT.removeElm(ConCopy, ConCopy[ran]);
            CopyNames = FT.removeElm(CopyNames, CopyNames[ran]);
            c++;
        }
        
        for(i=0;i<SelContents.length;i++){
            SelImages[i] = new ImageIcon("Flags\\"+SelContents[i]);
        }
        
        rand = (int)Math.round(Math.random() * (SelCountries.length - 1));
        
        selected = SelCountries[rand];
        Button error = new Button();
        error.setVisible(false);
        
        Label Title = new Label("Find The Flag".toUpperCase());
        Title.setForeground(Color.RED);
        Title.setBounds(125,45,250,40);
        Title.setFont(new Font("",Font.BOLD,26));
        
        Correct.setBounds(170,170,350,40);
        Correct.setForeground(Color.RED);
        Correct.setFont(new Font("",Font.PLAIN,15));
        Correct.setVisible(false);
        
        Result.setVisible(false);
        Result.setBounds(143,485,350,40);
        Result.setText("You Won !".toUpperCase());
        Result.setFont(new Font("",Font.PLAIN,29));
        
        Country.setText(selected);
        Country.setBackground(Color.WHITE);
        Country.setBounds(60,110,330,50);
        Country.setAlignment(1);
        Country.setFont(new Font("Algerian",Font.PLAIN,22));
        
        Next.setBackground(Color.YELLOW);
        Next.setBounds(365,48,60,35);
        Next.addActionListener(new NextCommand());
        Next.setVisible(false);
        Next.setName("Next");
        
        Level.setBounds(32,63,40,40);
        Level.setLabel(""+score);
        Level.setEnabled(false);
        Level.setFont(new Font("",Font.BOLD,18));
        Level.setBackground(Color.YELLOW);
        
        for(i=0;i<Flags.length;i++){
            Flags[i] = new JButton();
            Flags[i].setBounds(50 + ((i%2)*190),210+((i/2)*90),160,80);
            Flags[i].setIcon(SelImages[i]);
            Flags[i].setName(SelCountries[i]);
            Flags[i].addActionListener(new Flags());
        }
        
        for(i=0;i<Marks.length;i++){
            Marks[i] = new JButton();
            Marks[i].setBounds(50 + ((i%2)*190)-10,210+((i/2)*90)-10,180,100);
            Marks[i].setBackground(Color.GREEN);
            Marks[i].setEnabled(false);
            Marks[i].setVisible(false);
        }
        
        for(i=0;i<Score.length;i++){
            Score[i] = new Label();
            if(i==0){
                Score[i].setText("Lvl.");
                Score[i].setBounds(35,33,80,30);
            }
            Score[i].setFont(new Font("",Font.PLAIN,24));
        }
        
        for(i=0;i<Flags.length;i++)
        FT.add(Flags[i]);
        for(i=0;i<Marks.length;i++)
        FT.add(Marks[i]);
        FT.add(Next);
        FT.add(Country);
        FT.add(Correct);
        FT.add(Result);
        FT.add(Title);
        FT.add(Level);
        for(i=0;i<Score.length;i++)
        FT.add(Score[i]);
        FT.add(error);
        
        FT.setVisible(b);
    }
    
    public static void Bring(boolean b){
        FT.setVisible(b);
    }
    boolean Contains(String[] Str, String s){
        for(int j=0;j<Str.length;j++)
        if(Str[j].equals(s))
        return true;
        return false;
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
    
    static class Flags implements ActionListener
    {
        int c = 0;
        public void actionPerformed(ActionEvent ae){
            if(count<3 && !Won){
            JButton b = (JButton)ae.getSource();
            String name = b.getName();
            if(name.equals(selected)){
                for(int j=0;j<Flags.length;j++)
                if(!Flags[j].getName().equals(selected)){
                    Flags[j].setIcon(null);
                    Flags[j].setBackground(Color.CYAN);
                }
                Result.setBounds(143,485,350,40);
                Result.setText("You Won !".toUpperCase());
                score++;
                Won = true;
                Result.setVisible(true);
                Next.setName("Next");
                Next.setLabel("NEXT");
                Next.setVisible(true);
                Marks[rand].setVisible(true);
                Correct.setVisible(true);
            }
            else{
                count++;
                b.setIcon(null);
                b.setBackground(Color.CYAN);
            }
            
            if(count>2){
                for(int j=0;j<Flags.length;j++)
                if(!Flags[j].getName().equals(selected)){
                    Flags[j].setIcon(null);
                    Flags[j].setBackground(Color.CYAN);
                }
                Result.setBounds(150,485,350,40);
                Result.setText("You Lost".toUpperCase());
                Next.setLabel("BACK");
                Next.setName("Back");
                Next.setVisible(true);
                Result.setVisible(true);
                Marks[rand].setVisible(true);
                Correct.setVisible(true);
            }
            }
        }
    }
    
    static class NextCommand implements ActionListener
    {
        public void actionPerformed(ActionEvent ae){
            Button b = (Button) ae.getSource();
            if(b.getName().equals("Next")){
            String ConCopy[] = contents;
            Won = false;
            count = 0;
            String CopyNames[] = Countries;
            int c = 0;
            while(c < 6){
                int ran = (int)Math.round(Math.random() * (ConCopy.length - 1));
                SelContents[c] = ConCopy[ran];
                SelCountries[c] = CopyNames[ran];
                ConCopy = FT.removeElm(ConCopy, ConCopy[ran]);
                CopyNames = FT.removeElm(CopyNames, CopyNames[ran]);
                c++;
            }
        
            for(int i=0;i<SelContents.length;i++){
                SelImages[i] = new ImageIcon("Flags\\"+SelContents[i]);
            }
        
            rand = (int)Math.round(Math.random() * (SelCountries.length - 1));
        
            selected = SelCountries[rand];
            Country.setText(selected);
            
            for(int i=0;i<Flags.length;i++){
                Flags[i].setIcon(SelImages[i]);
                Flags[i].setName(SelCountries[i]);
                Marks[i].setVisible(false);
            }
            
            Level.setLabel(""+score);
            Result.setVisible(false);
            Correct.setVisible(false);
            Next.setVisible(false);
            
            DM.openFile("BEST FTF.txt");
            int best = DM.returnData();
            
            if(score>best)
            DM.editData("BEST FTF.txt", score);
            
            DM.closeFile();
            }
            
            if(b.getName().equals("Back")){
                FT.setVisible(false);
                StartUI.Visible = true;
                StartUI.main();
                String ConCopy[] = contents;
                score = 1;
                Won = false;
                count = 0;
            String CopyNames[] = Countries;
            int c = 0;
            while(c < 6){
                int ran = (int)Math.round(Math.random() * (ConCopy.length - 1));
                SelContents[c] = ConCopy[ran];
                SelCountries[c] = CopyNames[ran];
                ConCopy = FT.removeElm(ConCopy, ConCopy[ran]);
                CopyNames = FT.removeElm(CopyNames, CopyNames[ran]);
                c++;
            }
        
            for(int i=0;i<SelContents.length;i++){
                SelImages[i] = new ImageIcon("Flags\\"+SelContents[i]);
            }
        
            rand = (int)Math.round(Math.random() * (SelCountries.length - 1));
        
            selected = SelCountries[rand];
            Country.setText(selected);
            
            for(int i=0;i<Flags.length;i++){
                Flags[i].setIcon(SelImages[i]);
                Flags[i].setName(SelCountries[i]);
                Marks[i].setVisible(false);
            }
            
            Level.setLabel(""+score);
            Result.setVisible(false);
            Correct.setVisible(false);
            Next.setVisible(false);
            
            DM.openFile("BEST FTF.txt");
            int best = DM.returnData();
            
            if(score>best)
            DM.editData("BEST FTF.txt", score);
            
            DM.closeFile();
            }
        }
    }
}