
package question2;
import java.util.Scanner;

public class HumanPlayer extends BasicPlayer {
        
    @Override
    public int makeBet() {
        Scanner scanner = new Scanner(System.in);
        
        super.currentGameBet = scanner.nextInt();
        
        while(super.currentGameBet >= super.getBalance()){
            
            super.currentGameBet = scanner.nextInt();
        }
        while (super.currentGameBet <= 0 || super.currentGameBet > 500){

            super.currentGameBet = scanner.nextInt();
            
        }
        super.balance -= super.currentGameBet;
        
        return super.currentGameBet; 
    }

    public boolean hit() {
        Scanner scanner = new Scanner(System.in);
        
        char choose = scanner.nextLine().charAt(0);
        
        return(choose == 'y' || choose == 'Y');
    }

    public void takeCard(Card c){
        super.hand.addSingle(c);
    }
    
    
}
