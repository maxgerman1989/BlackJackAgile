import javax.swing.*;

import Players.Player;

import java.awt.*;

import java.awt.event.*;
import java.awt.image.BufferedImage;


public class AppWindow extends JFrame 
    implements ActionListener, ComponentListener
{
    private GamePanel gamePanel;
    private Color defaultTableColour = new Color(12, 226, 168);
    private Player player;
    
    
    final int WIDTH = 600;
    final int HEIGHT = 500;

	public AppWindow(String playerName)
    {
		
        super("Blackjack");
        //this.setContentPane(new JLabel(new ImageIcon("C:\\Users\\Max\\Desktop\\bb.png")));
        addComponentListener(this);
        
        Dimension windowSize = new Dimension(WIDTH, HEIGHT);
        setSize(windowSize);
        setLocationRelativeTo(null); // put game in centre of screen
        
        this.setBackground(defaultTableColour);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu scoresMenu = new JMenu("Scores");
        JMenuItem topTen = new JMenuItem("View Top 10 scores");
        scoresMenu.add(topTen);
        scoresMenu.addSeparator();
        menuBar.add(scoresMenu);
        
        JMenu betMenu = new JMenu("Bet");
        JMenuItem oneChip = new JMenuItem("$1");
        JMenuItem fiveChip = new JMenuItem("$5");
        JMenuItem tenChip = new JMenuItem("$10");
        JMenuItem twentyFiveChip = new JMenuItem("$25");
        JMenuItem hundredChip = new JMenuItem("$100");
        betMenu.add(oneChip);
        betMenu.add(fiveChip);
        betMenu.add(tenChip);
        betMenu.add(twentyFiveChip);
        betMenu.add(hundredChip);
        menuBar.add(betMenu);
 
        
        setJMenuBar(menuBar);
        
        // keyboard shortcuts
        
        topTen.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_S,                                            
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        oneChip.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_1,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        fiveChip.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_2,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        tenChip.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_3,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        twentyFiveChip.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_4,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        hundredChip.setAccelerator(
            KeyStroke.getKeyStroke( java.awt.event.KeyEvent.VK_5,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        
        
		// action listeners
        topTen.addActionListener(this);
		oneChip.addActionListener(this);
        fiveChip.addActionListener(this);
        tenChip.addActionListener(this);
        twentyFiveChip.addActionListener(this);
        hundredChip.addActionListener(this);
        player = new Player(playerName);
        gamePanel = new GamePanel(playerName);
        gamePanel.setBackground(defaultTableColour);
		add(gamePanel);
        setVisible(true);
    }

	public void actionPerformed(ActionEvent evt)
    {
        String act = evt.getActionCommand();
        
        if (act.equals("$1"))
        {
            gamePanel.increaseBet(1);
        }
        else if (act.equals("$5"))
        {
            gamePanel.increaseBet(5);
        }
        else if (act.equals("$10"))
        {
            gamePanel.increaseBet(10);
        }
        else if (act.equals("$25"))
        {
            gamePanel.increaseBet(25);
        }
        else if (act.equals("$100"))
        {
            gamePanel.increaseBet(100);
        }
        else if (act.equals("Deal"))
        {
            gamePanel.newGame();
        }
        else if (act.equals("Hit"))
        {
            gamePanel.hit();
        }
        else if (act.equals("Double"))
        {
            gamePanel.playDouble();
        }
        else if (act.equals("Stand"))
        {
            gamePanel.stand();
        }
        else if (act.equals("View Top 10 scores"))
        {
            gamePanel.updatePlayer();
        }
		
		gamePanel.updateValues();
	}
	
	public void componentResized(ComponentEvent e)
	{
	    int currentWidth = getWidth();
	    int currentHeight = getHeight();
	    
	    boolean resize = false;
	    
	    if (currentWidth < WIDTH)
	    {
	        resize = true;
	        currentWidth = WIDTH;
	    }
	    
	    if (currentHeight < HEIGHT)
	    {
	        resize = true;
	        currentHeight = HEIGHT;
	    }
	    
	    if (resize)
	    {
	        setSize(currentWidth, currentHeight);
	    }
	}
	
	public void componentMoved(ComponentEvent e) { }
	public void componentShown(ComponentEvent e) { }
	public void componentHidden(ComponentEvent e) { }
}