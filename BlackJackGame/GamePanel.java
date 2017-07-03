import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Players.*;
import sun.java2d.pipe.DrawImage;
import Cards.*;

public class GamePanel extends JPanel implements ActionListener
{
    private Dealer dealer;
    private Player player;
    
    private GameTable table;
    
    private JButton newGameButton = new JButton("Deal");
    private JButton hitButton = new JButton("Hit");
    private JButton doubleButton = new JButton("Double");
	private JButton standButton = new JButton("Stand");
    private JButton add1Chip = new JButton("1");
    private JButton add5Chip = new JButton("5");
    private JButton add10Chip = new JButton("10");
    private JButton add25Chip = new JButton("25");
    private JButton add100Chip = new JButton("100");
    private JButton clearBet =  new JButton("Clear");
    
    private JLabel currentBet = new JLabel("Please set your bet...");
    private JLabel playerWallet = new JLabel("$999.99");
    private JLabel cardsLeft = new JLabel("Cards left...");
    private JLabel dealerSays = new JLabel("Dealer says...");
    
    public GamePanel(String playerName)
    {
        this.setLayout(new BorderLayout());
        table = new GameTable();
        add(table, BorderLayout.CENTER);
        JPanel betPanel = new JPanel();
        betPanel.add(currentBet);
        betPanel.add(clearBet);
        betPanel.add(add1Chip);
        betPanel.add(add5Chip);
        betPanel.add(add10Chip);
        betPanel.add(add25Chip);
        betPanel.add(add100Chip);
        betPanel.add(playerWallet);
        
        JPanel dealerPanel = new JPanel();
        dealerPanel.add(dealerSays);
        
        JPanel optionsPanel = new JPanel();
        optionsPanel.add(newGameButton);
        optionsPanel.add(hitButton);
        optionsPanel.add(doubleButton);
        optionsPanel.add(standButton);
        optionsPanel.add(cardsLeft);
        
        JPanel bottomItems = new JPanel();
        bottomItems.setLayout(new GridLayout(0,1));
        bottomItems.add(dealerPanel);
        bottomItems.add(betPanel);
        bottomItems.add(optionsPanel);
        add(bottomItems, BorderLayout.SOUTH);

        betPanel.setOpaque(false);
        dealerPanel.setOpaque(false);
        optionsPanel.setOpaque(false);
        bottomItems.setOpaque(false);
        
        newGameButton.addActionListener(this);
        hitButton.addActionListener(this);
        doubleButton.addActionListener(this);
		standButton.addActionListener(this);
		clearBet.addActionListener(this);
		add1Chip.addActionListener(this);
		add5Chip.addActionListener(this);
		add10Chip.addActionListener(this);
		add25Chip.addActionListener(this);
		add100Chip.addActionListener(this);
		
        dealer = new Dealer();
        player = new Player(playerName);
        String query = "select points from BlackJackT where username=?";
        PreparedStatement pst;
        int money=0;
		try {
			pst = Login.connection.prepareStatement(query);
			pst.setString(1, playerName);
			ResultSet rs = pst.executeQuery();
			money = rs.getInt("points");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        player.setWallet((double)money); 
        updateValues();
    }
    
    public void actionPerformed(ActionEvent evt)
    {
        String act = evt.getActionCommand();
        
        if (act.equals("Deal"))
        {
            newGame();
        }
        else if (act.equals("Hit"))
        {
            hit();
        }
        else if (act.equals("Double"))
        {
            playDouble();
        }
        else if (act.equals("Stand"))
        {
            stand();
        }
        else if (act.equals("1") || act.equals("5") || act.equals("10") || act.equals("25") || act.equals("100"))
        {
            increaseBet(Integer.parseInt(act));
        }
        else if (act.equals("Clear"))
        {
            System.out.println("clear bet");
            clearBet();
        }
        
        updateValues();
    }
    
    public void newGame()
    {
        dealer.deal(player);
    }
    
    public void hit()
    {
        dealer.hit(player);
    }
    
    public void playDouble()
    {
        dealer.playDouble(player);
    }
    
    public void stand()
    {
        dealer.stand(player);
    }
    
    public void increaseBet(int amount)
    {
        dealer.acceptBetFrom(player, amount + player.getBet());
    }
    
    public void clearBet()
    {
        player.clearBet();
    }
    
    public void updateValues()
    {
        dealerSays.setText("<html><p align=\"center\"><font face=\"Arial\" color=\"white\" style=\"font-size: 20pt\">" + dealer.says() + "</font></p></html>");
        
        if (dealer.isGameOver())
        {
            if (player.betPlaced() && !player.isBankrupt())
            {
                newGameButton.setEnabled(true);
            }
            else
            {
                newGameButton.setEnabled(false);
            }
            hitButton.setEnabled(false);
            doubleButton.setEnabled(false);
            standButton.setEnabled(false);
            
            if (player.betPlaced())
            {
                clearBet.setEnabled(true);
            }
            else
            {
                clearBet.setEnabled(false);
            }
            
            if (player.getWallet() >= 1.0)
            {
                add1Chip.setEnabled(true);
            }
            else
            {
                add1Chip.setEnabled(false);
            }
            
            if (player.getWallet() >= 5)
            {
                add5Chip.setEnabled(true);
            }
            else
            {
                add5Chip.setEnabled(false);
            }
            
            if (player.getWallet() >= 10)
            {
                add10Chip.setEnabled(true);
            }
            else
            {
                add10Chip.setEnabled(false);
            }
            
            if (player.getWallet() >= 25)
            {
                add25Chip.setEnabled(true);
            }
            else
            {
                add25Chip.setEnabled(false);
            }
            
            if (player.getWallet() >= 100)
            {
                add100Chip.setEnabled(true);
            }
            else
            {
                add100Chip.setEnabled(false);
            }
        }
        else
        {
            newGameButton.setEnabled(false);
            hitButton.setEnabled(true);
            if (dealer.canPlayerDouble(player))
            {
                doubleButton.setEnabled(true);
            }
            else
            {
                doubleButton.setEnabled(false);
            }
            
            standButton.setEnabled(true);
            
            clearBet.setEnabled(false);
            add1Chip.setEnabled(false);
            add5Chip.setEnabled(false);
            add10Chip.setEnabled(false);
            add25Chip.setEnabled(false);
            add100Chip.setEnabled(false);
        }
        
        String query = "UPDATE BlackJackT SET points=? where username=?";
        try {
			PreparedStatement pst = Login.connection.prepareStatement(query);
			pst.setInt(1, (int)player.getWallet());
			pst.setString(2, player.getName());
			pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // redraw cards and totals
        table.update(dealer.getHand(), player.getHand(), (dealer.areCardsFaceUp()) ? true : false);
		table.setNames(dealer.getName(), player.getName());
        table.repaint();
        
        cardsLeft.setText("Deck: " + dealer.cardsLeftInPack() + "/" + (dealer.CARD_PACKS * Cards.CardPack.CARDS_IN_PACK));
        
        if (player.isBankrupt())
        {
            moreFunds();
        }
        
        // redraw bet
        currentBet.setText(Double.toString(player.getBet()));
        playerWallet.setText(Double.toString(player.getWallet()));
    }
    
    private void moreFunds()
    {
        int response = JOptionPane.showConfirmDialog(null, "BUSTED!!! your out of money! would you like a 10$ loan from the casino?", "Out of funds", JOptionPane.YES_NO_OPTION);
        
        if (response == JOptionPane.YES_OPTION)
        {
            player.setWallet(10.00);
            updateValues();
        }
    }
    
	
	public void updatePlayer()
	{
		String str="";
		int i=1;
		 String query = "select username,points from BlackJackT order by points DESC limit 10";
	        try {
				PreparedStatement pst = Login.connection.prepareStatement(query);
				ResultSet rs = pst.executeQuery();
				while(rs.next())
				{
					String username = rs.getString("username");
					int points = rs.getInt("points");
					System.out.println("Place No:" + i + " " + username + " Points:" + points);
					str+="Place No:" + i + " " + username + " Points:" + points + "\n";
					i++;
				}
				JOptionPane.showMessageDialog(this, str, "Top 10 BlackJack Players", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	   
	}
	
}