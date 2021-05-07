import java.awt.Color;
import java.io.Serializable;
import java.util.*;


//import Canvas.Points;

public class Model implements Serializable {
    /** The observers that are watching this model for changes. */
    private transient List<Observer> observers;
    private Color color;
    private Vector<Points> points;
    private Vector<Integer> endPoints;
	private int x, y, numPoints, numLines, thick, sliderValue, prevSliderValue;
	private boolean playingAnimation, isDrawing, moveDown, moveDown2;
	
    /**
     * Create a new model.
     */
    public Model() {
        this.observers = new ArrayList();
        this.points = new Vector<Points>();
        this.endPoints = new Vector<Integer>();
        numPoints = numLines = sliderValue = prevSliderValue = 0;	thick = 3;
        playingAnimation = isDrawing = moveDown = moveDown2 = false;
    } // ctor

    /**
     * Add an observer to be notified when this model changes.
     */
    public void addObserver(Observer observer) {
        this.observers.add(observer);
    } // addObserver

    /**
     * Remove an observer from this model.
     */
    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    } // removeObserver

    /**
     * Notify all observers that the model has changed.
     */
    public void notifyObservers() {
        for (Observer observer: this.observers) {
            observer.update(this);
        } // for
    } // notifyObservers
    
    public int getX() {
    		return x;
    } // getX
    
    public int getY() {
    		return y;
    } // getY
    
    public void setX(int x) {
    		this.x = x;
    } // setX
    
    public void setY(int y) {
    		this.y = y;
    } // setY
    
    public Color getColor() {
    		return color;
    } // getColor
    
    public int getThickness() {
    		return thick;
    } // getThickness
    
    public void addPoints(Points point) {
	    	points.add(point);
	    	notifyObservers();
    } // addPoints
    
    public Vector<Points> getPoints() {
    		return points;
    } // getPoints
    
    public Vector<Integer> getEndPoints() {
    		return endPoints;
    } // getEndPoints
    
    public void updateThickness(int thick) {
	    	this.thick = thick;
	    	notifyObservers();
    } // setThickness
    
    public void countDrawnLines() {
	    	++numLines;
	    	//notDrawing();
	    	if (points.size() > 0) endPoints.add(points.lastElement().getIndex()); // add the end point of the new line.
	    	notifyObservers();
    } // countDrawedLines
    
    public int getNumLines() {
    		return numLines;
    } // getNumLines
    
    public int getNumPoints() {
    		return points.size();
    } // getNumPoints
        
    public int getSliderValue() {
    		return sliderValue;
    } // getSliderValue
    
    public void updateSliderValue(int value) {
    		if (sliderValue > value && moveDown == false) moveDown = moveDown2 = true;
    		prevSliderValue = sliderValue;
    		sliderValue = value;
    		//System.out.println("playingAnimation = " + playingAnimation);
    		//System.out.println("slider value is updated to " + sliderValue);
    		notifyObservers();
    } // updateSliderValue
    
    public boolean isPlayingAnimation() {
    		return playingAnimation;
    } // isPlayingAnimation
    
    public boolean areYouDrawing() {
    		return isDrawing;
    } // areYouDrawing
    
    public void IamDrawing() {
    		isDrawing = true;
    } // IamDrawing
    
    public void notDrawing() {
    		isDrawing = false;
    } // notDrawing
    
    public void playON() {
    		playingAnimation = true;
    } // playONorOFF
    
    public void playOFF() {
    		playingAnimation = false;
    } // playOFF
    
    public void updateNewPoints() {
    		int size = points.size(), i;
 
    		points.subList(sliderValue,size).clear();
    		
    		if (points.size() > 0) {
    			size = endPoints.size();
        		for (i = 0; i < size; i++) {
        			if (endPoints.elementAt(i) >= sliderValue) break;
        		} // for
        		endPoints.subList(i,size).clear();
        		endPoints.add(sliderValue);
    		} else {
    			endPoints.removeAllElements();
    		} // if
    		// update the # of line drawn on the screen.
    		
    		numLines = endPoints.size();
    		if (points.size() == 0) numLines = 0;
System.out.println("numLines: " + numLines);
    		//playOFF();
    		notifyObservers();
    } // updateNewPoints
    
    private void reset() {
		color = Color.black;
		points.removeAllElements();
		endPoints.removeAllElements();
		x = y = numPoints = numLines = thick = sliderValue = prevSliderValue = 0;
		playingAnimation = isDrawing = moveDown = moveDown2 = false;
	} // reset
    
    public void createNewCanvas() {
    		reset();
    		notifyObservers();
    } // createNewCanvas

    public void loadData(Model m) {
    		color = m.color;
    		points = m.points;
    		endPoints = m.endPoints; moveDown = m.moveDown; moveDown2 = m.moveDown2;
    		x = m.x;  y = m.y; numPoints = m.numPoints; numLines = m.numLines; 
    		thick = m.thick; sliderValue = m.sliderValue; prevSliderValue = m.prevSliderValue;
    		playingAnimation = m.playingAnimation; isDrawing = m.isDrawing;
		notifyObservers();
    } // loadData
    
    public boolean isMoveDown() {
    		return moveDown;
    } // isMoveDown
    
    public void setMoveDown(boolean value) {
    		moveDown = value;
    } // setMoveDown
    
    public boolean isMoveDown2() {
		return moveDown2;
	} // isMoveDown
	
	public void setMoveDown2(boolean value) {
			moveDown2 = value;
	} // setMoveDown
        
    public void updateColor(Color color) {
	    	this.color = color;
	    	notifyObservers();
    } // updateColor
} // Model
