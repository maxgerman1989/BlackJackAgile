import static org.junit.Assert.*;

import org.junit.Test;

import Cards.Card;
import Cards.DealerCardHand;
import Cards.Face;
import Cards.Suit;

public class BlackJackHandTest {

	@Test
	public void test() {
		Face face1 = new Face(1);
		Suit suit1 = new Suit("Hearts");
		Face face2 = new Face(10);
		Suit suit2 = new Suit("Hearts");
		Card card1 = new Card(face1, suit1, 0);
		Card card2 = new Card(face2, suit2, 0);
		DealerCardHand hand = new DealerCardHand();
		hand.add(card1);
		hand.add(card2);
		assertEquals(true, hand.hasBlackjack());
	}

}
