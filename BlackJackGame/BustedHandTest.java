import static org.junit.Assert.*;

import org.junit.Test;

import Cards.Card;
import Cards.DealerCardHand;
import Cards.Face;
import Cards.Suit;

public class BustedHandTest {

	@Test
	public void test() {
		Face face1 = new Face(9);
		Suit suit1 = new Suit("Hearts");
		Card card1 = new Card(face1, suit1, 0);
		Card card2 = new Card(face1, suit1, 0);
		Card card3 = new Card(face1, suit1, 0);
		DealerCardHand hand = new DealerCardHand();
		hand.add(card1);
		hand.add(card2);
		hand.add(card3);
		assertEquals(true, hand.isBust());
	}

}
