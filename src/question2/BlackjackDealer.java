
package question2;
import java.util.*;
import java.util.ListIterator;
import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;


public class BlackjackDealer implements Dealer, Serializable {
    
    private Deck deck;
    private Hand hand;
    private int deckCount;
    private double gains;
    final private List<Player> p;
    final private List<Double> gainsL;
    final private List<Card> cardsPlayed;
    
    public BlackjackDealer(){
        this.gainsL = new ArrayList();
        this.cardsPlayed = new ArrayList();
        this.p = new ArrayList();
        this.hand = new Hand();
        this.deck = new Deck();
    }
    
    public void assignPlayers(List<Player> p){
        p.stream().forEach((player) -> {
            this.p.add(player);
        });
    }
    
    public void takeBets(){
        this.p.stream().forEach((Player player) -> {
            
            if (player.hasBalance() && player.getBalance() >= player.getBet()){
                System.out.println("Bet accepted.");
                
            } else {
                
                System.out.println("Bet cannot  be made. "
                        + "Player kicked out of the game.");
                
            }
        });
    }
    
    public void dealFirstCards(){
        this.deck.shuffle();
        Card dealt;
        if (this.deck.size() <= (double)52/4){
            
            deckCount++;
            this.deck = new Deck();
            this.gainsL.add(this.gains/this.p.size());
            
        }
        
        for (Player player : this.p){
            
            for (int i = 0; i < 2; i++){
                
                dealt = deck.deal();
                player.takeCard(dealt);
                cardsPlayed.add(dealt);
                
            }
        }

        dealt = deck.deal();
        this.hand.addSingle(dealt);
        cardsPlayed.add(dealt);
    }
    
    public int play(Player p){
        Card dealt;
        p.viewDealerCard(this.hand.cardColl.get(0));
        
        while(p.hit()){
            
            dealt = deck.deal();
            p.takeCard(dealt);
            cardsPlayed.add(dealt);
            
            if(p.isBust()){
                return p.getHandTotal();
            } 
        }
        return p.getHandTotal();
    }
        
    public int playDealer(){
        
        Card dealt;
        
        while(scoreHand(this.hand) <= 17){
            
            dealt = deck.deal();
            this.hand.addSingle(dealt);
            
        }
        
        return scoreHand(this.hand);
    }
    
    @Override 
    public int scoreHand(Hand h){
        if (h.getHandTotalHard() > 21){
            return h.getHandTotalSoft();
        } 
        return h.getHandTotalHard();
    }
    
    @Override 
    public void settleBets(){

       ListIterator<Player> it = p.listIterator();

       p.stream().map((player) -> {
           
        return player;
        
        }).map((Player player) -> {
            
            if (player.getHandTotal() > scoreHand(BlackjackDealer.this.hand)
                    && player.getHandTotal() <= 21) {
                
                player.settleBet(player.getBet());
                gains += player.getBet();
                
                
            }
            
            return player;
            
       }).map((Player player) -> player).map((Player player) -> {
           
           if (player.blackjack()){
               player.settleBet(2*player.getBet() + player.getBet());
               gains += 2*player.getBet() + player.getBet();
           }
           
           else {

               player.settleBet(-player.getBet());
               
           } return player;
           
       }).map((player) -> {
           
            player.viewCards(cardsPlayed);
            return player;
            
        }).forEach((player) -> {
            
            player.newHand();
            
        });
       
       while(it.hasNext())  {
           if(!it.next().hasBalance()){
               it.remove();
           }
       }
      
       this.hand = new Hand();
       this.dealFirstCards();
       
    }

    public List<Player> getCurrentPlayers(){
        return this.p;
    }
    
    public int getDeckCount(){
        return this.deckCount;
    }
    public List<Double> getAvgGains(){
        return this.gainsL;
    }

    
}
