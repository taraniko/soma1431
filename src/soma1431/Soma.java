package soma1431;

import java.awt.Component;
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
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import java.awt.GridBagConstraints;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
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
	
	public static final String PLAYLIST_FILE = "/home/playlist.soma"; 
	public static final String SCRIPT_PATH = "/home/nick/soma.liq";

	static private JPanel cards;
	private JPanel cardPreferences;
	private JTextField textField;
	private JTextField textField_1;
	
	public static PlaylistArray playlists;
	public static JComboBox<String> comboBox;

	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				initialize();
				createAndShowGUI();
			}
		});
	}

	public static void initialize()
	{
		comboBox = new JComboBox<String>();
		playlists = new PlaylistArray();
		try {
			Playlist.readPlaylistsFromFile();
		} catch (IOException e) {
			// TODO Script file unable to read.
			System.out.println("Can't read script file");
			e.printStackTrace();
		}
	}
	
	private static void createAndShowGUI(){
		
		//Create and set up the window.
		JFrame frame = new JFrame();
		frame = new JFrame();
		frame.setBounds(100, 100, 640, 480);
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
		cardTable.setLayout(new BorderLayout());
		JTable table = new JTable(new MyTableModel());
		JScrollPane scrollPane = new JScrollPane(table);
		initColumnSizes(table);
		addComboboxAtTable(table);
		cardTable.add(scrollPane);
		
	}
	
	public static void initColumnSizes(JTable table){
		MyTableModel model = (MyTableModel)table.getModel();
        TableColumn column = null;
        Component comp = null;
        int headerWidth = 0;
        int cellWidth = 0;
        Object[] longValues = model.longValues;
        TableCellRenderer headerRenderer =
            table.getTableHeader().getDefaultRenderer();
 
        for (int i = 0; i < 5; i++) {
            column = table.getColumnModel().getColumn(i);
 
            comp = headerRenderer.getTableCellRendererComponent(
                                 null, column.getHeaderValue(),
                                 false, false, 0, 0);
            headerWidth = comp.getPreferredSize().width;
 
            comp = table.getDefaultRenderer(model.getColumnClass(i)).
                             getTableCellRendererComponent(
                                 table, longValues[i],
                                 false, false, 0, i);
            cellWidth = comp.getPreferredSize().width;
 
            column.setPreferredWidth(Math.max(headerWidth, cellWidth));
        }
	}
	
	
	
	/**
	 * @param table
	 */
	public static void addComboboxAtTable(JTable table) {
		
		DefaultTableCellRenderer renderer =
				new DefaultTableCellRenderer();
		renderer.setToolTipText("Click to change playlist");
		for (int i=1; i<=7; i++){
			TableColumn tempColumn = table.getColumnModel().getColumn(i);
			tempColumn.setCellEditor(new DefaultCellEditor(comboBox));
			tempColumn.setCellRenderer(renderer);
		}
		
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
        final JTextField textPlaylistName = new JTextField();
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
                      lblChosenFile.setText(selectedFile.getAbsolutePath());
                    }  
                }
            }
        ); 
        
        btnSavePlaylist.addActionListener  
        (  
            new ActionListener() {  
                public void actionPerformed(ActionEvent e) {
                	try {
						Playlist newPlaylist = new Playlist(textPlaylistName.getText(),lblChosenFile.getText());
						playlists.add(newPlaylist);
						newPlaylist.writePlaylistToFile();
		
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                	
                	// Message "Data saved"
                	JOptionPane.showMessageDialog(null, "Playlist saved!", "Add playlist", JOptionPane.INFORMATION_MESSAGE);
                	// Activate table card
                	CardLayout cardLayout = (CardLayout) cards.getLayout();
                	cardLayout.show(cards, "table");
                	lblChosenFile.setText(" ");
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
		
		JMenuItem mntmSaveSchedule = new JMenuItem("Save Schedule");
		JMenuItem mntmExit = new JMenuItem("Exit");
		JMenuItem mntmPlaylist = new JMenuItem("Playlist");
		JMenuItem mntmAbout = new JMenuItem("About");
		
		
		mntmSaveSchedule.addActionListener  
        (  
            new ActionListener() {  
                public void actionPerformed(ActionEvent e) { 
                	// TODO Check if full array is completed
                	// TODO Notify if not all array is completed
                	// TODO Write every completed item to the script
                    // TODO Throw message that Schedule saved  
                }  
            }  
        );
		
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
		
		mnFile.add(mntmSaveSchedule);
		mnFile.add(mntmExit);
		mnAdd.add(mntmPlaylist);
		mnHelp.add(mntmAbout);
		
	}
}



class MyTableModel extends AbstractTableModel {
    private String[] columnNames = {"  ",
                                    "Monday",
                                    "Tuesday",
                                    "Wednesday",
                                    "Thursday",
                                    "Friday",
                                    "Saturday",
                                    "Sunday"};
    private Object[][] data = {
    {"00:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"01:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"02:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"03:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"04:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"05:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"06:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"07:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"08:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"09:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"10:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"11:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"12:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"13:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"14:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"15:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"16:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"17:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"18:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"19:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"20:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"21:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"22:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    {"23:00", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>", "<Select>"},
    };

    public final Object[] longValues = {" 23:00 ", "Psychedelic", "Psychedelic", "Psychedelic", 
    		"Psychedelic", "Psychedelic", "Psychedelic", "Psychedelic" };
    
    

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
        //Note that the data/cell address is constant,
        //no matter where the cell appears onscreen.
        if (col < 1) {
            return false;
        } else {
            return true;
        }
    }
    
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }



}

class Playlist{
	private String name;
	private String path;
	public Playlist(String name){
		this.name = name;
	}
	public Playlist(String name, String path) throws IOException
	{
		this.name = name;
		this.path = path;
		// writePlaylistToFile();
	}
	public String getName(){
		return name;
	}
	
	public String getPath()
	{
		return path;
	}
	
	public void writePlaylistToFile() throws IOException
	{
		RandomAccessFile script = new RandomAccessFile(Soma.SCRIPT_PATH,"rw");
		String line = null;
		boolean canWritePlaylists = false;
		boolean endWriting = false;
		long toWritePos;
		while ((line = script.readLine())!= null && (!endWriting))
		{
			if (line.contains("#playlists"))
			{
				canWritePlaylists = true;
			}
			if (canWritePlaylists)
			{
				toWritePos = script.getFilePointer();
				// copy following text to Byte Array
				int remainingByteCount =  (int) (script.length() - toWritePos);
				byte[] buf = new byte[remainingByteCount];
			    script.seek(toWritePos);
			    script.readFully(buf);
				// write the needed bytes
			    script.seek(toWritePos);
			    String lineToWrite = null;
			    lineToWrite = this.name + " = playlist(mode='randomize',reload=3600,reload_mode=\"watch\",\""
			    		+this.path+"\")\n";
			    script.writeBytes(lineToWrite);
				// write the Byte Array
			    script.write(buf);
				endWriting = true;
			}
		}
		script.close();
	}
	
	public static void readPlaylistsFromFile() throws IOException
	{
		File script = new File(Soma.SCRIPT_PATH);
		BufferedReader br = new BufferedReader(new FileReader(script));
		String line = null;
		boolean parsingPlaylists = false;
		boolean finishedPlaylists = false;
		//Read script line by line
		while (((line = br.readLine()) != null) && (!finishedPlaylists)) 
		{
			if (line.contains("#end_playlists"))
			{
				finishedPlaylists = false;
				parsingPlaylists = false;
			}
			if (parsingPlaylists)
			{
				//TODO code to parse playlist name and path from script
				int indexEqual = line.indexOf("=");
				String playlistName = line.substring(0,indexEqual-1);
				int indexSlash = line.indexOf("/");
				String playlistPath = line.substring(indexSlash);
				int indexParenthesis = playlistPath.indexOf(")");
				playlistPath = playlistPath.substring(0,indexParenthesis-1);
				Soma.playlists.add(new Playlist(playlistName,playlistPath));
				
			}
			if (line.contains("#playlists"))
			{
				parsingPlaylists = true;
			}
			
		}
		br.close();
		
	}
	
}

class PlaylistArray{
	private ArrayList<Playlist> playlists;
	
	public PlaylistArray(){
		playlists = new ArrayList<Playlist>();		
	}
	
	public int size(){
		return playlists.size();
	}
	
	public Playlist getPlaylist(int k)
	{
		return playlists.get(k);
	}
	
	public void add(Playlist newPlaylist)
	{
		playlists.add(newPlaylist);
		Soma.comboBox.addItem(newPlaylist.getName());
	}
	
	public String getPathByName(String name)
	{
		for (int i=0; i<playlists.size(); i++)
		{
			if (playlists.get(i).getName() == name)
			{
				return playlists.get(i).getPath();
			}
		}
		return " ";
	}
	
	
}



