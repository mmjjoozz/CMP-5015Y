package question2;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Comparator;

public class Hand implements Iterable<Card>, Serializable{
    
    private static final long serialVersionUID = 102;
    private int handTotalSoft;
    private int handTotalHard;
    
    
    ArrayList<Card> cardColl;
    
    
    public Hand() {
        cardColl = new ArrayList<>();
    }

    public void setHandTotalHard(Card c){
        this.handTotalHard += c.getRank().getValue();
    }
    
    public void setHandTotalSoft(Card c){
        if (c.getRank() == Card.Rank.ACE){
            this.handTotalSoft += 1;
        } else {
            this.handTotalSoft += c.getRank().getValue();
        }
    }
    
    public int getHandTotalHard(){
        return this.handTotalHard;
    }
    public int getHandTotalSoft(){
        return this.handTotalSoft;
    }
    
    public Hand(Card[] cards){
        cardColl = new ArrayList<>(Arrays.asList(cards));
    }
    
    public Hand(Hand diffHand){
        this.cardColl = new ArrayList<>(diffHand.cardColl);
    }
    
    
    public void addSingle(Card card){
        setHandTotalSoft(card);
        setHandTotalHard(card);
        this.cardColl.add(card);
    }
    public void addColl(Collection card){
        this.cardColl.addAll(card);
    }
    public void addHand(Hand hand){
        this.cardColl.addAll(hand.cardColl);
    }
    public void remSingle(){
        try {
            int index = this.cardColl.size() - 1;
            this.cardColl.remove(index);
            
        } catch (Exception e){
            System.err.println("This hand is empty.");
        }
        
    }
    public void remAnother(Hand hand){
        this.cardColl.removeAll(hand.cardColl);
    }
    public void remAtPos(int pos){
        try {
            this.cardColl.remove(pos-1);
        } catch (Exception e){
            System.err.println("No card at this position.");
        }
    }
    
    public Hand reverseHand(Hand hand){
        Hand revHand = new Hand();
        for (int i = hand.cardColl.size()-1; i > 0; i--){
            revHand.cardColl.add(hand.cardColl.get(i));
        }
        return revHand;
    }
    
    public boolean isOver(int val){
        int smallest = Integer.MAX_VALUE;
        for (Card card : cardColl){
            if (smallest < card.getRank().getValue()){
                smallest = card.getRank().getValue();
            }
        }
        if (val < smallest){
            return true;
        } return false;
    }
    
    public int countSuit(Card.Suit suit){
        int counter = 0;
        counter = cardColl.stream()
                .filter((card) -> (card.getSuit() == suit))
                .map((_item) -> 1)
                .reduce(counter, Integer::sum);
        return counter;
    }
    
    public void sortAscending (){
        Comparator comp = new Card.CompareAscending();
        Collections.sort(cardColl, comp);
        cardColl.stream()
                .forEach((c) -> {
            System.out.println(c.toString());
        });
    }
    
    public int countRank(Card.Rank rank){
        int counter = 0;
        counter = cardColl.stream()
                .filter((card) -> (card.getRank() == rank))
                .map((_item) -> 1)
                .reduce(counter, Integer::sum);
        return counter;
    }
    
    public Iterator<Card> iterator(){
        return cardColl.iterator();
    }
    

    public String toString(){
        String rtn = "";
        for (Card card : this.cardColl){
            rtn = card.toString() + "\n" + rtn;
        }
        return rtn;
    }   
    
}
