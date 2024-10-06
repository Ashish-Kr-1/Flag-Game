 
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StartUI extends Frame
{
    static StartUI SU = new StartUI();
    static Label Title = new Label("Flag Game".toUpperCase()); 
    
    static JLabel Img = new JLabel();
    static JButton[] Mode = new JButton[2];
    static int FTF, NTC;
    static Label Best = new Label();
    static JButton Play = new JButton();
    static int ModeCount = 1;
    static boolean F = false, N = false;
    static boolean Visible = true;
    public StartUI(){
        setSize(450,540);
        setTitle("Flag Game");
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent we){
                System.exit(0);
            }
        });
        setBackground(Color.CYAN);
        setVisible(true);
        setLocationRelativeTo(null);
    }
    
    public static void main(String... args)
    {
        int i;
        Data_Manager DM = new Data_Manager();
        
        DM.openFile("BEST NTC.txt");
        NTC = DM.returnData();
        DM.closeFile();
        
        DM.openFile("BEST FTF.txt");
        FTF = DM.returnData();
        DM.closeFile();
        
        SU.setVisible(Visible);
        Title.setForeground(Color.RED);
        Title.setBounds(95,55,270,40);
        Title.setFont(new Font("",Font.BOLD,44));
        
        Button error = new Button();
        error.setVisible(false);
        
        Best.setBounds(150,370,150,35);
        Best.setText("Best score: "+FTF);
        Best.setBackground(Color.yellow);
        Best.setAlignment(1);
        Best.setFont(new Font("",Font.PLAIN,18));
        
        Play.setBounds(30,440,390,55);
        Play.setIcon(new ImageIcon("Play.png"));
        Play.addActionListener(new Play());
        
        Img.setBounds(100,130,250,230);
        Img.setIcon(new ImageIcon("FTF.png"));
        
        for(i=0;i<Mode.length;i++){
            Mode[i] = new JButton();
            if(i==0){
                Mode[i].setBounds(30,220,60,90);
                Mode[i].setIcon(new ImageIcon("left.png"));
                Mode[i].setName("Left");
            }
            if(i==1){
                Mode[i].setBounds(360,220,60,90);
                Mode[i].setIcon(new ImageIcon("right.png"));
                Mode[i].setName("Right");
            }
            Mode[i].addActionListener(new Arrow());
        }
        
        for(i=Mode.length-1;i>=0;i--)
        SU.add(Mode[i]);
        SU.add(Title);
        SU.add(Img);
        SU.add(Best);
        SU.add(Play);
        SU.add(error);
    }
    
    static class Arrow implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            JButton b = (JButton) ae.getSource();
            String action = b.getName();
            if(action.equals("Right")){
                ModeCount++;
            }
            if(action.equals("Left")){
                ModeCount--;
            }
            
            if(ModeCount>2)
            ModeCount = 1;
            if(ModeCount<1)
            ModeCount = 2;
            
            if(ModeCount == 1){
                Img.setIcon(new ImageIcon("FTF.png"));
                Best.setText("Best Score: "+FTF);
            }
            if(ModeCount == 2){
                Img.setIcon(new ImageIcon("NTC.png"));
                Best.setText("Best Score: "+NTC);
            }
        }
    }
    
    static class Play implements ActionListener{
        public void actionPerformed(ActionEvent ae){
            SU.setVisible(false);
            if(ModeCount==1){
                if(!F){ FindTheFlag.main(true); F = true;}
                else FindTheFlag.Bring(true);
            }
            if(ModeCount==2){
                if(!N){ NameTheCountry.main(); N = true;}
                else NameTheCountry.Bring(true);
            }
        }
    }
}