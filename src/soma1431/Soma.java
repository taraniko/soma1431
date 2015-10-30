package soma1431;

import java.awt.Container;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.CardLayout;

import javax.swing.*;

public class Soma {

	private JPanel cards;

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	
	private static void createAndShowGUI(){
		
		//Create and set up the window.
		JFrame frame = new JFrame();
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 460);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create and set up the content pane.
		Soma window = new Soma();
		window.addCardsToPane(frame.getContentPane());
		
		window.createMenu(frame);
		
		 //Display the window.
        frame.pack();
        frame.setVisible(true);
		
	}
	
	public void addCardsToPane(Container pane) {
         
        //Create the "cards".
        JPanel card1 = new JPanel();
        JPanel card2 = new JPanel();
        fillTableCard(card1);
        fillPreferencesCard(card2);
         
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(card1,"table");
        cards.add(card2,"preferences");
        pane.add(cards, BorderLayout.CENTER);
    }
	
	public static void fillTableCard(JPanel card){
		card.add(new JButton("Button 1"));
        card.add(new JButton("Button 2"));
        card.add(new JButton("Button 3"));
	}
	
	public static void fillPreferencesCard(JPanel card){
		card.add(new JTextField("TextField", 20));
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void createMenu(JFrame frame) {
		
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu mnFile = new JMenu("File");
		JMenu mnAdd = new JMenu("Add");
		JMenu mnHelp = new JMenu("Help");
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		JMenuItem mntmPlaylist = new JMenuItem("Playlist");
		JMenuItem mntmAbout = new JMenuItem("About");
		
		// Add action listener.for the menu button  
        mntmExit.addActionListener  
        (  
            new ActionListener() {  
                public void actionPerformed(ActionEvent e) {  
                    System.exit(0);  
                }  
            }  
        );
        
        mntmPlaylist.addActionListener  
        (  
            new ActionListener() {  
                public void actionPerformed(ActionEvent e) { 
                	// Activate preferences card
                	CardLayout cardLayout = (CardLayout) cards.getLayout();
                	cardLayout.show(cards, "preferences");
                }  
            }  
        );   
		
		frame.setJMenuBar(menuBar);
		
		menuBar.add(mnFile);
		menuBar.add(mnAdd);
		menuBar.add(mnHelp);
		
		
		mnFile.add(mntmExit);
		mnAdd.add(mntmPlaylist);
		mnHelp.add(mntmAbout);
		
	}

}
