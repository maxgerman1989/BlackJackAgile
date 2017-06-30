import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Test;

import Players.Player;

public class DBTest {
	private static String username = "Jacob";
	private static String password = "12345";

	@SuppressWarnings("static-access")
	@Test
	public void test() throws IOException {
		Login login = new Login();
		Player player = new Player(username);
		//First Test
		assertEquals(100, (int)player.getWallet());
		//Second Test
		assertEquals(1, login.signUp(username, password));
		String query = "delete from BlackJackT where username='Jacob'";
		PreparedStatement pst;
		try {
			pst = login.connection.prepareStatement(query);
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
