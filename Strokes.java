import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.lang.System.*;

//import Palette.ColorBtnActionListener;

public class Strokes extends JPanel implements Observer, Serializable {
	private Model model;
	
	public Strokes(Model model) {
		this.model = model;
		this.model.addObserver(this);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		init();
	} // ctor
	
	private void init() {
		System.out.println("init is called");
		int width = 150, height = 200;
		String line1 = "./images/line1.png";
		String line2 = "./images/line2.png";
		String line3 = "./images/line3.png";
		String line4 = "./images/line4.png";
		String [] images = {line1,line2,line3,line4};
		Color [] colors = {Color.red,Color.blue,Color.green,Color.yellow};
		JPanel menu = new JPanel(new GridLayout(5,1));
		menu.setMinimumSize(new Dimension(width,height));
		menu.setMaximumSize(new Dimension(width,height));
		menu.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2, 2,
		        2, 2, Color.orange), "Stroke", TitledBorder.CENTER, TitledBorder.TOP));
		menu.setLayout(new GridLayout(0,1));
		buttonSetting(menu,images,colors);
		add(menu);
	} // init
	
	private JButton createImageButton(ImageIcon ic, Color color) {
		JButton btn = new JButton(ic);
		btn.setBackground(color);
		btn.setMinimumSize(new Dimension(80, 30));
		btn.setMaximumSize(new Dimension(80, 30));
		return btn;
	} // createImageIcon
	
	void buttonSetting(JPanel panel, String [] items, Color [] colors) {
		int size = items.length;
		for (int i = 0; i < size; i++) {
			JButton btn = createImageButton(new ImageIcon(items[i]),colors[i]);
			btn.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					JButton btn = (JButton)e.getSource();
					Color background = btn.getBackground();
					
					if (background == Color.red) {
						model.updateThickness(3);
					} else if (background == Color.blue) {
						model.updateThickness(10);
					} else if (background == Color.green) {
						model.updateThickness(20);
					} else if (background == Color.yellow) {
						model.updateThickness(30);
					} // if
				} // actionPerformed
			});
			panel.add(btn);
		} // for
	} // buttonSetting

	public void update(Object observable) {
		
	} // update
} // Strokes
