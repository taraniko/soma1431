package soma1431;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.GridBagLayout;

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
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.GridBagConstraints;
import java.io.File;
import java.util.ArrayList;

import com.jgoodies.forms.*;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.RowSpec;
//import com.jgoodies.forms.factories.FormFactory;

public class Soma {

	static private JPanel cards;
	private JPanel cardPreferences;
	private JTextField textField;
	private JTextField textField_1;

	

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
		frame.setBounds(100, 100, 500, 360);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Create and set up the content pane.
		Soma window = new Soma();
		window.addCardsToPane(frame.getContentPane());
		window.createMenu(frame);
		
		 //Display the window.
        //frame.pack();
        frame.setVisible(true);
		
	}
	
	public void addCardsToPane(Container pane) {
         
        //Create the "cards".
        JPanel cardTable = new JPanel();
        JPanel cardPreferences = new JPanel();
        fillTableCard(cardTable);
        fillPreferencesCard(cardPreferences);
         
        //Create the panel that contains the "cards".
        cards = new JPanel(new CardLayout());
        cards.add(cardTable,"table");
        cards.add(cardPreferences,"preferences");
        
        
        
        
        pane.add(cards, BorderLayout.CENTER);
    }
	
	public static void fillTableCard(JPanel cardTable){
		cardTable.add(new JButton("Button 1"));
        cardTable.add(new JButton("Button 2"));
        cardTable.add(new JButton("Button 3"));
	}
	
	public static void fillPreferencesCard(JPanel cardPreferences){
		
		//Initialize FormLayout
		cardPreferences.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));
        
        JLabel lblPlaylistName = new JLabel("Playlist Name:");
        JTextField textPlaylistName = new JTextField();
        JLabel lblPlaylistFile = new JLabel("Playlist File: ");
        final JLabel lblChosenFile = new JLabel(" ");
        JButton btnChooseFile = new JButton("Choose File...");
        JButton btnSavePlaylist = new JButton("Save");
        
        textPlaylistName.setColumns(10);
        
        cardPreferences.add(lblPlaylistName, "2, 2, right, default");
        cardPreferences.add(textPlaylistName, "4, 2, 1, 2, fill, default");
        cardPreferences.add(lblPlaylistFile, "2, 4");    
        cardPreferences.add(btnChooseFile, "4, 4");
        cardPreferences.add(lblChosenFile, "4, 6");
        cardPreferences.add(btnSavePlaylist, "2, 8");
        
        btnChooseFile.addActionListener  
        (  
            new ActionListener() {  
                public void actionPerformed(ActionEvent e) { 
                	// Open file chooser
                	JFileChooser fileChooser = new JFileChooser();
                	FileNameExtensionFilter filterm3u = new FileNameExtensionFilter(
                	        "PLaylist file (.m3u)", "m3u");
                	fileChooser.setFileFilter(filterm3u);
                    int returnValue = fileChooser.showOpenDialog(null);
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                      File selectedFile = fileChooser.getSelectedFile();
                      System.out.println(selectedFile.getAbsolutePath());
                      lblChosenFile.setText(selectedFile.getName());
                    }  
                }
            }
        ); 
        
        btnSavePlaylist.addActionListener  
        (  
            new ActionListener() {  
                public void actionPerformed(ActionEvent e) {
                	// Message "Data saved"
                	JOptionPane.showMessageDialog(null, "Playlist saved!", "Add playlist", JOptionPane.INFORMATION_MESSAGE);
                	// Activate table card
                	CardLayout cardLayout = (CardLayout) cards.getLayout();
                	cardLayout.show(cards, "table");
                }
            }
        ); 
		
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

class Playlist{
	private String name;
	private String file;
}

class dataFile{
	String path;
	String fileName;
	public static void createFile()
	{
		
	}
	
	
	
}

