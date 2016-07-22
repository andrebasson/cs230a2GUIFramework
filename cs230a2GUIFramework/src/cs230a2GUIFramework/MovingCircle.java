package cs230a2GUIFramework;

/*  Name: 		Andre Basson
 * 	UPI:		abas396
 * 	Course:		CS230, 2014s1 
 * 	Assignment:	no2
 *	===============================================================================
 *	MovingCircle.java : A shape that is a circle.
 *	A circle has 4 handles shown when it is selected (by clicking on it).
 *	===============================================================================
 */
import java.awt.*;

public class MovingCircle extends MovingSquare {
	private float radius = 20.0f;
	
	public MovingCircle() {
		super();		
	}
	
	/** constuctor to create a circle shape with radius equaling double width OR height
	 */
	public MovingCircle(int x, int y, float r, float bL, float bS, Color lC, Color rC, int mw, int mh, int pathType) {
		// radius (r) x 2 passed to constructor to set both width and height fields
		super(x, y, (r*2), bL, bS, lC, rC, mw, mh, pathType);	 
		radius = r;
	}

	public void setCircleRadius(float r) {
		radius = r;
		super.setHeight(r*2);	
		super.setWidth(r*2);
	}
	
	/** draw the circle with the fill colour
	 *	If it is selected, draw the handles
	 *	@param g	the Graphics control
	 */
	public void draw(Graphics g) {
		// First, convert to new 2D graphics context
		Graphics2D g2d = (Graphics2D)g;		// type cast works, because g is really a Graphics2D object

		// Draw the filled shape (with a gradient pattern)
		// --------------------------------------------
		// define attributes of the fill gradient pattern of a shape using class GradientPaint
		//    @param's:
		//			x1 - x coordinate of the first specified Point in user space
		//			y1 - y coordinate of the first specified Point in user space
		//			color1 - Color at the first specified Point
		//			x2 - x coordinate of the second specified Point in user space
		//			y2 - y coordinate of the second specified Point in user space
		//			color2 - Color at the second specified Point
		GradientPaint leftToRightColor = new GradientPaint(p.x, p.y, leftColor, p.x + width, p.y + height, rightColor);  
		g2d.setPaint(leftToRightColor);		// .setColor(leftToRightColor) does the same
		g2d.fillOval(p.x, p.y, (int)(radius*2.0), (int)(radius*2.0));
		
		// Draw the border of the shape
		// ----------------------------
		// set up a dash pattern to be used for the border
		final float dashPattern[] = {borderDashLength, borderDashSeperation};
   
		// Define attributes of the pen style used to render the line (e.g. a dashed line) using class BasicStroke
		//   @param pen width, end caps, line joins, miter limit, dash pattern, dash phase (see Java Tut)                    
		BasicStroke dashed = new BasicStroke(borderWidth,    // Width
                BasicStroke.CAP_SQUARE,    					// End cap
                BasicStroke.JOIN_MITER,    					// Join style
                10.0f,                     					// Miter limit
                dashPattern, 								// Dash pattern
                0.0f); 
		
		// The rendering attributes defined by BasicStroke describe the shape of the mark made by a pen drawn along 
		// the outline of a Shape and the decorations applied at the ends and joins of path segments of the Shape. 
		g2d.setStroke(dashed);
		
		// draw the border using the stroke set
		g2d.setColor(Color.black);
		g2d.drawOval(p.x, p.y, (int)(radius*2.0), (int)(radius*2.0));		
		
		// lastly draw the handles of the shape
		drawHandles(g2d);		
		
	}
	

	/** Draw the handles of the shape
	 * 	  (overrides super class method) 
	 * 	  @param g 	the Graphics control
	 */
	public void drawHandles(Graphics2D g) {
		// if the shape is selected, then draw the handles
		if (isSelected()) {
			g.setColor(Color.black);
			g.fillRect(p.x -2, p.y-2, 4, 4);
			g.fillRect(p.x + (int)(radius*2.0) -2, p.y + (int)(radius*2.0) -2, 4, 4);
			g.fillRect(p.x -2, p.y + (int)(radius*2.0) -2, 4, 4);
			g.fillRect(p.x + (int)(radius*2.0) -2, p.y-2, 4, 4);
		}
	}	
	
	public boolean contains(Point mousePt) {
		return (p.x <= mousePt.x && mousePt.x <= (p.x + width + 1)	&&	p.y <= mousePt.y && mousePt.y <= (p.y + height + 1));		
	}
	
	

}
