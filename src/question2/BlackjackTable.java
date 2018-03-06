package question2;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import question1.*;

public class BlackjackTable implements Serializable {
    
    public static final int NUM_PLAYERS = 4;
    
    //check if save files exist 
    public static boolean detectSave(){
        File d = new File("dealer.ser");
        File p = new File("players.ser");
        return(d.exists() && p.exists());
    }
    
    
    public static void serialize(List<Player> l, BlackjackDealer d){
        try {
            
            FileOutputStream fosdealer = new FileOutputStream("dealer.ser");
            FileOutputStream fosplay = new FileOutputStream("players.ser");

            ObjectOutputStream oosdealer = new ObjectOutputStream(fosdealer);
            ObjectOutputStream oosplay = new ObjectOutputStream(fosplay);

            oosdealer.writeObject(d);
            oosplay.writeObject(l);
            oosplay.close();
            oosdealer.close();
            
        } catch (Exception e){
            e.printStackTrace();
        }
        
    }
   
    public static List<Player> deserializePlayer(){
        List<Player> players = new ArrayList();
        
        try {
            
            FileInputStream in = new FileInputStream("players.ser");
            ObjectInputStream oin = new ObjectInputStream(in);
            players = (List<Player>) oin.readObject();
            return players;
            
        } catch(Exception e) {
            
            e.printStackTrace();
            return null;
            
        }
    }
    
    public static BlackjackDealer deserializeDealer(){
        try {
            
            FileInputStream in = new FileInputStream("dealer.ser");
            ObjectInputStream oin = new ObjectInputStream(in);
            return (BlackjackDealer)oin.readObject();
            
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void startGame(){
        
        Scanner in = new Scanner(System.in);
        int level = in.nextInt();
        
        BlackjackDealer dealer = new BlackjackDealer();
        List players = new ArrayList();

        switch(level){
            
            case 0:
                
                for(int i = 0; i < NUM_PLAYERS; i++){
                    players.add(new BasicPlayer());
                }
                
                break;
            case 1:
                
                for(int i = 0; i < NUM_PLAYERS; i++){
                    players.add(new IntermediatePlayer());
                }
                
                break;
            case 2:
                
                for(int i  = 0; i < NUM_PLAYERS; i++){
                    players.add(new AdvancedPlayer());
                }
                
                break;
        }
        
        gamePlay(dealer, players);
        
    }
    
    public static void gamePlay(BlackjackDealer dealer, 
                                List<Player> players){
        
        dealer.assignPlayers(players);
        dealer.dealFirstCards();
        
        Scanner in = new Scanner(System.in);

        System.out.println("Set initial number of hands: ");
        int numHands = in.nextInt();
        
        for(int i = 0; i <= numHands; i++) {
            for(Player p : players){
                p.makeBet();
            }
            dealer.takeBets();
            
            dealer.playDealer();
            for (Player p : players){
                dealer.play(p);
            }
            dealer.settleBets();

            players = dealer.getCurrentPlayers();
            
            if (i == numHands){
                System.out.println("Enter 'y' to continue or any other key to quit.");
                char cont = in.next().charAt(0);
                    if (cont == 'y' || cont == 'Y'){
                        System.out.println("How many hands would you like to run?");
                        numHands = in.nextInt();
                        i = 0;
                    }
                    else {
                        break;
                    }
            }
            
            if (players.size() <= 1) {
                System.out.println("No players left. Press 'y' to create new or "
                + " any other key to quit.");
                char newPlayers = in.next().charAt(0);
                if (newPlayers == 'y' || newPlayers == 'Y'){
                    startGame();
                } else {
                    break;
                }
            }
        }
        
        
        
    }
    
    public static void humanGame(){
        
        Scanner in = new Scanner(System.in);
        
        BlackjackDealer dealer = new BlackjackDealer();
        List<Player> players = new ArrayList();
        BasicPlayer a = new BasicPlayer();
        BasicPlayer h = new HumanPlayer();
        
        boolean d = detectSave();
        
        if (d){
            System.out.println("Save file from a previous session has been detected"
                    + ", press 'y' to load it or any key to continue with a new game.");
            char choice = in.next().charAt(0);
            if (choice == 'y' || choice == 'Y'){
                players = deserializePlayer();
                dealer = deserializeDealer();
            } 

        }
        
        if (!d || players.isEmpty()) {
            
            players.add(a);
            players.add(h);
            dealer.assignPlayers(players);
            dealer.dealFirstCards();
            
        }
                
        while(true){
            for(Player p : players){
                p.makeBet();
            }
            dealer.takeBets();
            dealer.playDealer();
            for (Player p : players){
                dealer.play(p);
            }
            dealer.settleBets();
            dealer.getCurrentPlayers();
            serialize(players, dealer);
            if (players.size() < 2){
                break;
            } else if (!h.hasBalance()) {
                System.out.println("No credit left. You lost.");
                break;
            }
            else if (!(h.hasBalance() && a.hasBalance())){
                System.out.println("Nobody wins.");
                break;
            }
            System.out.println("Would you like to save befoe continuing?");
            char choice = in.next().charAt(0);
            if (choice == 'y' || choice == 'Y'){
                serialize(players, dealer);
            }
            
        }
    }
    

    
    

    
    public static void main(String [] args) throws IOException {
        Scanner in = new Scanner(System.in);
        System.out.println("'h' for human game, 'n' for NPC game.");
        char mode = in.next().charAt(0);
        switch(mode){
            case 'h':
                humanGame();
                break;
            case 'n':
                startGame();
                break;
            case 'q':
                break;
        }
        
    }
    
}
