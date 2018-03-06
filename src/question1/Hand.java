package question1;
import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Comparator;
import java.util.Map;
import java.util.HashMap;

public class Hand implements Iterable<Card>, Serializable{
    
    private static final long serialVersionUID = 102;
    private int handTotalSoft;
    private int handTotalHard;
    
    //rank counts are stored in a hashmap, where rank is the key
    private Map <Card.Rank, Integer> rankCounts;
    
    ArrayList<Card> cardColl;
    
    
    public Hand() {
        cardColl = new ArrayList<>();
        rankCounts = new HashMap<>();
    }
    //functions for counting both hard and soft totals
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
        //with each added card soft and hard totals are calculated
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
        for (Card card : cardColl){
            if(card.getSuit() == suit){
                counter++;
            }
        }
        return counter;
    }
    
    public void sortDescending(){
        //for later
    }
    
    public void sortAscending (){
        Comparator comp = new Card.CompareAscending();
        Collections.sort(cardColl, comp);
        for (Card c : cardColl){
            System.out.println(c.toString());
        }
    }
    
    public int countRank(Card.Rank rank){
        int counter = 0;
        for (Card card : cardColl){
            if (card.getRank() == rank){
                counter++;
            }
        }
        return counter;
    }
    
    @Override
    public Iterator<Card> iterator(){
        return cardColl.iterator();
    }
    
    public void serialize() {
        try {
            FileOutputStream fos = new FileOutputStream("hand.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.cardColl);
            fos.close();
            oos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
    public ArrayList<Card> deserialize(){
        ArrayList<Card> ret = new ArrayList();
        try{
            FileInputStream fin = new FileInputStream("hand.ser");
            ObjectInputStream oin = new ObjectInputStream(fin);
            ret = (ArrayList<Card>) oin.readObject(); 
            return ret;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public String toString(){
        String rtn = "";
        for (Card card : this.cardColl){
            rtn = card.toString() + "\n" + rtn;
        }
        return rtn;
    }

    
    public static void main(String[] args){
        Deck deck = new Deck();
        deck.shuffle();
        Hand hand = new Hand();
        String breakl = "------------------";
        for(int i = 0; i < 10; i++){
            hand.addSingle(deck.deal());
        }
        hand.sortAscending();
        for (Card c : hand.cardColl){
            System.out.println(c.toString());
        }
        System.out.println(breakl);
        hand.remAtPos(2);
        for (Card c : hand.cardColl){
            System.out.println(c.toString());
        }
        System.out.println(breakl);
        Hand hand2 = hand.reverseHand(hand);
        for (Card c : hand2.cardColl){
            System.out.println(c.toString());
        }
        
    }    
    
}
