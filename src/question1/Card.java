package question1;
import java.util.Comparator;
import java.io.*;

public class Card implements Serializable, Comparable<Card> {
    
    private static final long serialVersionUID = 111;
    
    private final Rank rank;
    private final Suit suit;
    
    public enum Rank { TWO(2), THREE(3), FOUR(4),
                FIVE(5), SIX(6), SEVEN(7),
                EIGHT(8), NINE(9), TEN(10),
                JACK(10), QUEEN(10), KING(10), ACE(11);
                public static Rank[] ranks = values();
                private final int intVal;
                
                Rank(int x){
                    this.intVal = x;
                }
                
                public Rank getPrevious(){
                    if (this.intVal == 2) {
                        return ACE;
                    } else {
                    return ranks[(this.ordinal()-1)%ranks.length];
                        }
                    }
                
                
                public int getValue(){
                    return this.intVal;
                }
            }
    
    public enum Suit { CLUBS, DIAMONDS, HEARTS, SPADES }
    
    public Card(Rank rank, Suit suit){
        this.rank = rank;
        this.suit = suit;
    }
    
    public Rank getRank(){
        return this.rank;
    }
    public Suit getSuit(){
        return this.suit;
    }
    
    public static int sum(Card a, Card b){
        int returnVal = a.rank.getValue() + b.rank.getValue();
        return returnVal;
    }
    
    public static boolean isBlackJack(Card a, Card b){
        return(a.rank.intVal >= 10 && b.rank.intVal >= 10);
    }
    
    @Override
    public String toString(){
        String rtnStr;
        String a = "RANK: " + this.getRank() + "\n";
        String b = "SUIT: " + this.getSuit() + "\n";
        rtnStr = a + b;
        return rtnStr;
    }
    
    @Override
    public int compareTo(Card anotherCard) {
        if(this.rank.getValue() > anotherCard.rank.getValue()){
            return 1;
        } else {
            return 0;
        }
    }
    
    public static class CompareAscending implements Comparator<Card> {
        @Override
        public int compare(Card a, Card b){
            return(a.rank.getValue() < b.rank.getValue() ? -1 : 
                    a.rank.getValue() == b.rank.getValue() ?  0 : 1);
        }
    }
    public static class CompareSuit implements Comparator<Card> {
        @Override 
        public int compare(Card a, Card b){
            return a.suit.compareTo(b.suit);
        }
    }
    
    public static void main(String[] args) {
        
        Card card = new Card(Rank.ACE, Suit.CLUBS);
        Card card2 = new Card(Rank.FOUR, Suit.DIAMONDS);
        System.out.println(card.getRank());
        System.out.println(card.getSuit());
        System.out.println(card.toString());
        System.out.println(sum(card, card2));
        System.out.println(isBlackJack(card, card2));
    }
    
}



