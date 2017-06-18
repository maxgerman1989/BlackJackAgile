package Players;

import Cards.*;


public class Dealer extends BlackjackPlayer
{
   
    private Deck deck;
    
    public DealerCardHand hand = new DealerCardHand();
    
  
    private boolean firstDeal = true;
    
  
    public static final int DEALER_STANDS_ON = 17;
    public static final int CARD_PACKS = 2;
    
    private boolean gameOver = true;
    private boolean cardsFaceUp = false;
    

    private boolean playerCanDouble = true;
    
    private String said = "Please place your wager.";
    
  
    public Dealer()
    {
        super("Dealer");
        
        deck = new Deck(CARD_PACKS);
    }
    
    public void say(String announcement)
    {
        said = announcement;
        System.out.println(said);
    }
    
    public String says()
    {
        return said;
    }
    
    public boolean isGameOver()
    {
        return gameOver;
    }
    
    public boolean areCardsFaceUp()
    {
        return cardsFaceUp;
    }
    

    public boolean acceptBetFrom(Player player, double bet)
    {
        boolean betSet = player.setBet(bet);
        
        if (player.betPlaced())
        {
            say("Thank you for your bet of $" + player.getBet() + ". Would you like me to deal?");
        }
        else
        {
            say("Please place your bet.");
        }
        
        return (betSet) ? true : false;
    }
    

    public boolean deal(Player player)
    {
        boolean cardsDealt = false;
        
        if (player.betPlaced() && !player.isBankrupt())
        {   
            gameOver = false;
            cardsFaceUp = false;

            playerCanDouble = true;
            
            player.hand = new PlayerCardHand();
            hand = new DealerCardHand();
            
            say("Initial deal made.");
            
            player.hand.add(deck.deal());
            this.hand.add(deck.deal());
            
            player.hand.add(deck.deal());
            this.hand.add(deck.deal());
            
            cardsDealt = true;
            firstDeal = false; 
            
            if (player.hand.hasBlackjack())
            {
                say("Blackjack!");
                go(player);
            }
            
        }
        else if (!player.betPlaced())
        {
            say("Please place your bets.");
            gameOver = true;
        }
        
        return cardsDealt;
    }
    
 
    public void hit(Player player)
    {
        say(player.getName() + " hits.");
        player.hand.add(deck.deal());
        
        playerCanDouble = false;
        
        if (player.hand.isBust())
        {
            say(player.getName() + " busts. Loses $" + player.getBet());
            player.loses();
            gameOver = true;
        }
    }
    

    public void playDouble(Player player)
    {
        if (player.doubleBet() && playerCanDouble)
        {
            player.hand.add(deck.deal());
            say(player.getName() + " plays double.");
            
            if (player.hand.isBust())
            {
                say(player.getName() + " busts. Loses $" + player.getBet());
                player.loses();
                gameOver = true;
            }
            else
            {
                go(player);
            }
        }
        else
        {
            say(player.getName() + ", you can't double. Not enough money.");
        }
    }
    
 
    public void stand(Player player)
    {
        say(player.getName() + " stands. " + this.getName() + " turn.");
        go(player);
    }
    
 
    private void go(Player player)
    {
        cardsFaceUp = true;
        
        if (!hand.hasBlackjack())
        {
            while (hand.getTotal() < DEALER_STANDS_ON)
            {
                hand.add(deck.deal());
                say(this.getName() + " hits.");
            }
            
            if (hand.isBust())
            {
                say(this.getName() + " is BUST");
            }
            else
            {
                say(this.getName() + " stands on " + hand.getTotal());
            }            
        }
        else
        {
            say(this.getName() + " has BLACKJACK!");
        }
        
        if (hand.hasBlackjack() && player.hand.hasBlackjack())
        {
            say("Push");
            player.clearBet();
        }
        else if (player.hand.hasBlackjack())
        {
            double winnings = (player.getBet() * 3) / 2;
            say(player.getName() + " wins with Blackjack $" + winnings);
            player.wins(player.getBet() + winnings);
        }
        else if (hand.hasBlackjack())
        {
            say("Dealer has Blackjack. " + player.getName() + " loses $" + player.getBet());
            player.loses();
        }
        else if (hand.isBust())
        {
            say("Dealer is bust. " + player.getName() + " wins $" + player.getBet());
            player.wins(player.getBet() * 2);
        }
        else if (player.hand.getTotal() == hand.getTotal())
        {
            say("Push");
            player.clearBet();
        }
        else if (player.hand.getTotal() < hand.getTotal())
        {
            say(player.getName() + " loses $" + player.getBet());
            player.loses();
        }
        else if (player.hand.getTotal() > hand.getTotal())
        {
            say(player.getName() + " wins $" + player.getBet());
            player.wins(player.getBet() * 2);
        }
        
        gameOver = true;
    }
    
    public int cardsLeftInPack()
    {
        return deck.size();
    }
    
    public void newDeck()
    {
        deck = new Deck(CARD_PACKS);
    }
    
    public boolean canPlayerDouble(Player player)
    {
        return (playerCanDouble && player.canDouble()) ? true : false;
    }
    
    public DealerCardHand getHand()
    {
        return hand;
    }
}