package question2;

import java.util.List;

public class AdvancedPlayer extends IntermediatePlayer {
    
    private int prevGameCount;
    
    @Override
    public int makeBet() {
        super.currentGameBet = 10;
        if (prevGameCount > 0){
            super.currentGameBet *= prevGameCount;
            return super.currentGameBet;
        }
        return super.currentGameBet;
    }
    
    @Override
    public void viewCards(List<Card> cards){
        int counter = 0;
        for(Card c : cards){
            if (c.getRank().getValue() <= 6){
                counter++;
            }
            else if (c.getRank().getValue() >= 10){
                counter--;
            }
        }
        this.prevGameCount = counter;
    }
}
