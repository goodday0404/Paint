import java.io.*;
import java.util.*;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import javax.swing.*;
import java.util.Hashtable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Animation extends JPanel implements Observer, Serializable {
	private Model model;
	private JSlider slider;
	private Hashtable<Integer, JLabel> table;
	
	public Animation(Model model) {
		this.model = model;
		this.model.addObserver(this);
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		init();
	} // ctor
	
	private void init() {
		String play = "./images/play.png";
		String begin = "./images/rewind2.png";
		String end = "./images/rewind1.png";
		JButton playBtn = new JButton(new ImageIcon(play));
		JButton beginBtn = new JButton(new ImageIcon(begin));
		JButton endBtn = new JButton(new ImageIcon(end));
		slider = new JSlider();
		slider.setValue(slider.getMaximum());
		table = new Hashtable<Integer, JLabel>();
	
		playBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("timer starts");
				int numPoints = model.getPoints().size();
				if (model.getNumLines() > 0) {
					model.playON(); // noticing the play button is pressed.
					Timer timer = new Timer();
					timer.scheduleAtFixedRate(new TimerTask() {
						@Override
						public void run() {
							if (slider.getValue() < slider.getMaximum()) {
								slider.setValue(slider.getValue() + 1);
							} else {
								slider.setValue(slider.getValue() + 1);
								System.out.println("timer is canceled");
								timer.cancel(); // stop timer
								timer.purge(); // delete timer
							} // if
						} // run		
					}, 1, numPoints/10);
				} // if
				//slider.setValue(slider.getMinimum());
			} // ActionPerformed
		});
		
		beginBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slider.setValue(slider.getMinimum());
			} // ActionPerformed
		});
		
		endBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				slider.setValue(slider.getMaximum());
			} // ActionPerformed
		});
		
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				System.out.println("slider value is changed to " + slider.getValue());
				model.updateSliderValue(slider.getValue());
			} // stateChanged
		});
		
		add(playBtn);
		add(slider);
		add(beginBtn);
		add(endBtn);	
	} // init
	
	// re-draw tick using end points of drawn lines on the canvas.
	private void updateTicks() {
		Vector<Integer> endPoints = model.getEndPoints();
		int numPoints = model.getPoints().size();
		slider.setMaximum(numPoints-1);
		int size = endPoints.size();
		table.clear(); // erase all ticks to draw new ticks.
		table.put(0,new JLabel("|"));
		int endPoint = 0;
		if (endPoints.size() > 0 && model.isMoveDown2() == false) ++endPoint;
		if (size == 0) endPoint = 0;
		for (int i = 0; i < size; i++) {
			table.put(endPoints.elementAt(i)+endPoint,new JLabel("|"));
		} // for
		table.put(numPoints-1,new JLabel("|"));
		slider.setLabelTable(table);
		slider.setPaintLabels(true);
		slider.setSnapToTicks(true);
	} // updateTicks
	
	public void update(Object observable) {
		//int numLines = model.getNumLines();
		int numPoints = model.getNumPoints();
		boolean isDrawing = model.areYouDrawing();
		if (numPoints > 0) {
			if (isDrawing == true) slider.setValue(slider.getValue()+1);
			// re-draw ticks only when not playing animation.
			if (model.isPlayingAnimation() == false || isDrawing == true) {
				System.out.println(slider.getValue());
				updateTicks();
			}
		} else {
			table.clear();
		} // if
	} // update
} // Animation
