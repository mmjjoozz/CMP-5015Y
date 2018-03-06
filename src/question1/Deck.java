
package question1;
import java.io.*;
import java.util.Random;
import java.util.Iterator;
import java.util.LinkedList;

public class Deck implements Iterable<Card>, Serializable {
    
    private static final long serialVersionUID = 111;
    
    private LinkedList<Card> cards;
    
    public Deck(){
        //linkedlist has a useful method 'poll' used for dealing the cards
        cards = new LinkedList();
        for (Card.Suit suit : Card.Suit.values()){
            for (Card.Rank rank : Card.Rank.values()){
                Card newCard = new Card(rank, suit);
                cards.add(newCard);
            }
        }
    }
    
    Card deal(){
        Card retCard = cards.poll();
        return retCard;
    }
    
    int size(){
        return this.cards.size();
    }
    
    final static Deck newDeck() {
       Deck newDeck = new Deck();
       return newDeck;
    }
    
    private class SecondCardIterator<Card> implements Iterator<Card>{
        
        Deck deck = newDeck();
        int index = 0;
        
        @Override
        public boolean hasNext(){
            if (index < deck.size()){
                System.out.println(index);
                return true;
            } return false;
        }
        @Override
        public Card next() {
            if(this.hasNext()){
                Card curr = (Card)cards.get(index);
                index = index + 2;
                return curr;
            } return null;
        }

    }
    
    
    @Override
    public Iterator<Card> iterator(){
        return new SecondCardIterator();
    }
    
    public void shuffle(){
        Random rd = new Random();
        for (int i = this.size()-1; i > 0; i--){
            int index = rd.nextInt(i)+1;
            Card temp = this.cards.get(index);
            this.cards.set(index, this.cards.get(i));
            this.cards.set(i, temp);
        }
    }
    
    public static void serialize (ObjectOutputStream oos, Card c){
        try {
            oos.writeObject(c);
            oos.flush();
        } catch (Exception e){
            System.out.println("Could not serialize error: " + e);
        }
    }
    
    public static Card deserialize (ObjectInputStream in){
        try {
           return (Card) in.readObject();
        } catch(Exception e){
            System.out.println("Could not deserialize error: " + e);
        }
        return null;
    }
    
    
    public static void main(String[] args) throws FileNotFoundException, IOException{
        Deck myDeck = new Deck();
        myDeck = newDeck();
        Iterator<Card> it = myDeck.iterator();

        FileOutputStream fos = new FileOutputStream("deck.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        
        while(it.hasNext()){
            //custom iterator returns every other card
            //which gets serialized in this loop
            serialize(oos, it.next());
        }
    }
}
