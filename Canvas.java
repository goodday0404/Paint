import java.io.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.*;
import java.awt.*;
import javax.swing.*;

public class Canvas extends JPanel implements Observer, Serializable {
	private Model model;
	
	public Canvas(Model model) {
		this.model = model;
		//index = 0;
		model.addObserver(this);
		setLayout(new SpringLayout());
		setBackground(Color.white);
		setPreferredSize(new Dimension(2000,2000));
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				System.out.println("mouse is pressed");
				model.setX(e.getX());
				model.setY(e.getY());
			} // mousePressed
			
			public void mouseReleased(MouseEvent e) {
				model.notDrawing();
				model.countDrawnLines(); // count up the # of the drawn lines
			} // mouseReleased
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			public void mouseMoved(MouseEvent e) {}
			public void mouseDragged(MouseEvent e) {
				model.IamDrawing();
		
				if (model.isMoveDown() == true) {
					model.updateNewPoints(); // delete points that are not drawn on the screen.
					model.playOFF(); // noticing playing animation is stopped.
					model.setMoveDown(false);
				} // if
				Color c = model.getColor();
				int thick = model.getThickness();
				model.addPoints(new Points(model.getX(),model.getY(),e.getX(),e.getY(),
						thick,model.getNumPoints(),c));
				model.setX(e.getX());
				model.setY(e.getY());
			} // mouseDragged
		});
	} // ctor
	
	protected void paintComponent(Graphics g) {
		Vector<Points> points = model.getPoints();
		Iterator<Points> it = points.iterator();
		boolean playingAnimation = model.isPlayingAnimation();
		boolean isDrawing = model.areYouDrawing();
		int sliderValue = model.getSliderValue();
		System.out.println("playingAnimation = " + playingAnimation);
		System.out.println("slider value is " + sliderValue);
		
		if (playingAnimation == false && isDrawing == true) {
			while (it.hasNext()) {
				it.next().drawPoints(g);
			} // while
		} else if ((playingAnimation == true || isDrawing == false) && sliderValue > 0) {
System.out.println("playing");
			for (int i = 0; i < sliderValue; i++) {
				points.elementAt(i).drawPoints(g);
			} // for
		} // if
	} // paintComponent 
	
	public void update(Object observable) {
		repaint();
	} // update
}
