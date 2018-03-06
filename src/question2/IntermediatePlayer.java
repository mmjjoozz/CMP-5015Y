package question2;


public class IntermediatePlayer extends BasicPlayer {
    
    private Card dealerCard;
    
    @Override
    public void viewDealerCard(Card c){
        this.dealerCard = c;
    }
    
    @Override
    public boolean hit(){
        if (this.dealerCard.getRank().getValue() >= 7 
                && super.getHandTotal() <= 17) {
            
            return true;
            
        }
        if (this.dealerCard.getRank().getValue() <= 6 && 
                this.dealerCard.getRank() != Card.Rank.ACE &&
                super.getHandTotal() <= 12) {
            
                return true;
                
        } else if (super.isBust()) {
            return false;
        }
        
        return false;
    }
    @Override
    public int getHandTotal(){
        boolean hasAce = false;
        for (Card c : super.hand.cardColl){
            if (c.getRank() == Card.Rank.ACE){
                hasAce = true;
            }
        }
        if(hasAce){
            return super.hand.getHandTotalSoft();
        } else {
        return super.hand.getHandTotalHard();
        }
    }
    
}
