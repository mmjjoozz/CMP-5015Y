package question2;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class BasicPlayer implements Player, Serializable {
    
    protected Hand hand = new Hand();
    protected int balance = 200;
    protected int currentGameBet;
    protected Card dealerCard;
    protected List<Card> cardsPlayed = new ArrayList();
    
    
    public Hand newHand(){
        Hand oldHand = this.hand;
        this.hand = new Hand();
        return oldHand;
    }
    
    public int makeBet(){
        currentGameBet = 10;
        return currentGameBet;
    }
    
    public boolean hasBalance (){
        return (this.getBalance() > 0);
    }
    
    public int getBet(){
        return this.currentGameBet;
    }
    
    public int getBalance(){
        return this.balance;
    }
    
    public boolean hit(){
       return(this.getHandTotal() <= 17);
    }
    
    public int getHandTotal(){
        if(this.hand.getHandTotalHard() <= 21){
            return this.hand.getHandTotalHard();
        }
        else if (this.hand.getHandTotalSoft() <= 21){
            return this.hand.getHandTotalSoft();
        }
        return this.hand.getHandTotalSoft();
    }
    
    public void takeCard(Card c){
        this.hand.addSingle(c);
    }
    
    public boolean settleBet(int p){
        balance += p;
        return (p > 0);
    }
    
    public boolean blackjack(){
        return (this.hand.cardColl.size() == 2 && this.getHandTotal() == 21);
    }
    public boolean isBust(){
        if (this.getHandTotal() > 21){
            return true;
        }
        return false;
    }
    
    public Hand getHand(){
        return this.hand;
    }
    
    public void viewDealerCard(Card c){
        this.dealerCard = c;
    }
    
    public void viewCards(List<Card> cards){
        this.cardsPlayed = cards;
    }
    
    public void newDeck(){
        System.out.println("New deck.");
    }
    
}
