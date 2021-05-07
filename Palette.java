import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class Palette extends JPanel implements Observer, Serializable {
	private Model model;
	JButton selectedColor;
	
	public Palette(Model model) {
		this.model = model;
		model.addObserver(this);
		init();
	} // ctor
	
	private void init() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		createColors();
		add(new Strokes(model));
		add(createColorSelected());
	} // init
	
	private void createColors() {
		int width = 150, height = 200;
		String jcolor = "./images/jcolor.png";
		String eraser = "./images/eraser.png";
		Color colors[] = {Color.red,Color.blue,Color.green,Color.yellow,Color.orange,Color.pink,
						  Color.magenta,Color.black};
		JPanel palette = new JPanel();
		JButton eraserBtn = new JButton(new ImageIcon(eraser));
		JButton jcolorBtn = new JButton(new ImageIcon(jcolor));
		eraserBtn.setBackground(Color.white);
		jcolorBtn.setBackground(Color.white);
		panelSetting(palette,"Color",Color.cyan,width,height);
		palette.setLayout(new GridLayout(0,2));

		buttonSetting(palette,colors);
		eraserBtn.addActionListener(new ColorBtnActionListener());
		jcolorBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton btn = (JButton)e.getSource();
				Color background = btn.getBackground();
				Color newColor = JColorChooser.showDialog(null,
			            "JColorChooser", background);
				if (newColor != null) {
					selectedColor.setBackground(newColor);
					model.updateColor(newColor);
				} // if
			} // actionPerformed
		});
		palette.add(eraserBtn);
		palette.add(jcolorBtn);
		//palette.add(new JButton(new ImageIcon(jcolor)));
		add(palette);
	} // createColors
	
	void buttonSetting(JPanel panel, Color [] items) {
		int size = items.length;
		for (int i = 0; i < size; i++) {
			JButton btn = createButton(items[i]);
			btn.addActionListener(new ColorBtnActionListener());
			panel.add(btn);
		} // for
	} // buttonSetting
	
	private JButton createButton(Color color) {
		JButton btn = new JButton();
		btn.setBackground(color);
		btn.setForeground(Color.white);
		btn.setOpaque(true);
		btn.setBorderPainted(false);
		return btn;
	} // createButtons
	
	private JPanel createColorSelected() {
		int width = 150, height = 50;
		JPanel selected = new JPanel(new GridLayout(1,1));
		selectedColor = createButton(Color.white);
		selectedColor.setBackground(Color.black);
		panelSetting(selected,"Selected",Color.pink,width,height);
		selected.add(selectedColor);
		return selected;
	} // createColorSelected
	
	private void panelSetting(JPanel panel, String title, Color color, int width, int height) {
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createMatteBorder(2, 2,
		        2, 2, color), title, TitledBorder.CENTER, TitledBorder.TOP));
		panel.setMinimumSize(new Dimension(width,height));
		panel.setMaximumSize(new Dimension(width,height));
	} // panelSetting
	
	public void update(Object observable) {
	  // XXX Fill this in with the logic for updating the view when the model
	  // changes.
	  System.out.println("Model changed!");
	  
	  selectedColor.setBackground(model.getColor());
	} // update
	
	class ColorBtnActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JButton btn = (JButton)e.getSource();
			Color newColor = btn.getBackground();
			model.updateColor(newColor);
		} // actionPerformed
	} // ColorBtnActionListener
}
