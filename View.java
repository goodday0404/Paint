import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class View extends JFrame implements Observer, Serializable {

    //private static final File File = null;
	private Model model;

    /**
     * Create a new View.
     */
    public View(Model model) {
        // Set up the window.
        this.setTitle("Paint");
        this.setMinimumSize(new Dimension(580, 580));
        this.setSize(1000, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        createMenu();
        // Hook up this observer so that it will be notified when the model
        // changes.
        this.model = model;
        this.model.addObserver(this);
        init();
        setVisible(true);
    } // ctor
    
    private void init() {     
        JPanel panel = new JPanel(new BorderLayout());
        getContentPane().add(panel);
        panel.add(new Palette(model),BorderLayout.WEST);
        panel.add(new JScrollPane(new Canvas(model)),BorderLayout.CENTER);
        panel.add(new Animation(model),BorderLayout.SOUTH);
    } // init
    
    private void createMenu() {
		JMenuBar menuBar = new JMenuBar(); // create menu bar.
		JMenu fileMenu = new JMenu("File"); // create file menu.
		JMenuItem [] menuItems = new JMenuItem[4];
		String [] items = {"New", "Save", "Laod", "Exit"};
		
		for (int i = 0; i < menuItems.length; i++) {
			menuItems[i] = new JMenuItem(items[i]);
			menuItems[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					switch (e.getActionCommand()) {
						case "New": newCanvas(); break;
						case "Save": save(); break;
						case "Laod": load(); break;
						case "Exit": System.exit(0); break;
					} // switch
				} // actionPerformed
			}); // addActionListener
			fileMenu.add(menuItems[i]);
		} // for
	
		// add file & view menu to menu bar.
		menuBar.add(fileMenu);  
		// menuBar.add(new JMenu("View"));
		setJMenuBar(menuBar);
	} // createMenu
    
    private void newCanvas() {
    		String message = "Do you want to save?", title = "Create new canvas";
    		switch (JOptionPane.showConfirmDialog(null,message,title,JOptionPane.YES_NO_CANCEL_OPTION)) {
	    		case JOptionPane.YES_OPTION: save();
	    		case JOptionPane.NO_OPTION: model.createNewCanvas(); break;
	    		case JOptionPane.CANCEL_OPTION: 
	    		default: 
    		} // switch
    } // newCanvas
    
    private void save() {
    		JFileChooser save = new JFileChooser("Save");
		if (save.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File saveFile = save.getSelectedFile();
			saveModel(saveFile);
		} // if
    } // save
    
    private void load() {
    		JFileChooser load = new JFileChooser("Load");
    		load.setAcceptAllFileFilterUsed(false);
    		load.addChoosableFileFilter(new FileNameExtensionFilter("doodle files","ser")); 
    		if (load.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
    			File file = load.getSelectedFile();
    			loadModel(file);
    			// load file
    		} // if
    } // load
    
    public void saveModel(File file) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));
			oos.writeObject(model);
			oos.close();
		} catch (Exception e) {
			System.out.println("Exceptions from saveModel");
			e.printStackTrace();
		} // catch
	} // saveModel 
	
	 void loadModel(File file) {
	System.out.println("LoadModel");
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				Model m = (Model)ois.readObject();
				model.loadData(m);
				ois.close();
			} catch (Exception e) {
				System.out.println("Exceptions");
				e.printStackTrace();
			} // catch
	} // laodModel
	
    public enum MenuItem {
    		New, Save, Load, Exit;
    } // MenuItem
    
    /**
     * Update with data from the model.
     */
    public void update(Object observable) {
        // XXX Fill this in with the logic for updating the view when the model
        // changes.
        System.out.println("Model changed!");
    } // update
}
