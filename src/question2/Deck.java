
package question2;

import java.io.Serializable;
import java.util.Random;
import java.util.Iterator;
import java.util.LinkedList;

public class Deck implements Iterable<Card>, Serializable {
    
    private static final long serialVersionUID = 111;
    
    private final LinkedList<Card> cards;
    
    public Deck(){
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

}
