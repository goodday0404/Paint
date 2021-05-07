import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.Serializable;

public class Points implements Serializable {
	private int bx,by,ex,ey,thick, index;
	private Color color;
	
	public Points(int bx, int by, int ex, int ey, int thick, int index, Color c) {
		this.bx = bx;
		this.by = by;
		this.ex = ex;
		this.ey = ey;
		this.thick = thick;
		this.index = index;
		color = c;
	} // ctor
	
	public int getIndex() {
		return index;
	} // getIndex
	
	protected void drawPoints(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		g2.setPaint(color);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(thick, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.drawLine(bx, by, ex, ey);
	} // paintComponent 
} // Points
