package cs230a2GUIFramework;

/*  Name: 		Andre Basson
 * 	UPI:		abas396
 * 	Course:		CS230, 2014s1 
 * 	Assignment:	no2
 * 
 *	===============================================================================
 *	MovingRectangle.java : A shape that is a rectangle.
 *	A rectangle has 4 handles shown when it is selected (by clicking on it).
 *	===============================================================================
 */
import java.awt.*;

public class MovingRectangle extends MovingShape {
	
	// no class or instance variables - inherits all fields from class MovingShape	

	/** constuctor to create a rectangle with default values
	 */
	public MovingRectangle() {
		super();
	}

	/** constuctor to create a rectangle shape
	 */
	public MovingRectangle(int x, int y, float w, float h, float bL, float bS, Color lC, Color rC, int mw, int mh, int pathType) {
		super(x, y, w, h, bL, bS, lC, rC, mw, mh, pathType);
	}

	/** draw the rectangle with the fill colour
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
		g2d.fillRect(p.x, p.y, (int)width, (int)height);
		

		// Draw the border of the shape
		// ----------------------------------
		// set up a dash pattern to be used for the border
		final float dashPattern[] = {borderDashLength, borderDashSeperation};

		// Define attributes of the pen style used to render the line (e.g. a dashed line) using class BasicStroke
		//   @param  pen width, end caps, line joins, miter limit, dash pattern, dash phase (see Java Tut)                    
		BasicStroke dashed = new BasicStroke(borderWidth,    // Width
                BasicStroke.CAP_SQUARE,    					// End cap
                BasicStroke.JOIN_MITER,    					// Join style
                10.0f,                     					// Miter limit
                dashPattern, 								// Dash pattern
                0.0f);                     					// Dash phase		
		
		// The rendering attributes defined by BasicStroke describe the shape of the mark made by a pen drawn along 
		// the outline of a Shape and the decorations applied at the ends and joins of path segments of the Shape.
		g2d.setStroke(dashed);
		
		// draw the border using the stroke set
		g2d.setColor(Color.black);
		g2d.drawRect(p.x, p.y, (int)width, (int)height);
	
		// lastly draw the handles of the shape
		drawHandles(g2d);		
	}

	/** Returns whether the point is in the rectangle or not
	 * @return true if and only if the point is in the rectangle, false otherwise.
	 */
	public boolean contains(Point mousePt) {
		return (p.x <= mousePt.x && mousePt.x <= (p.x + width + 1)	&&	p.y <= mousePt.y && mousePt.y <= (p.y + height + 1));
	}
}