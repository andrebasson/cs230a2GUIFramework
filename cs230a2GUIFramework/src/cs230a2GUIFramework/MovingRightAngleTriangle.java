package cs230a2GUIFramework;

/*  Name: 		Andre Basson
 * 	UPI:		abas396
 * 	Course:		CS230, 2014s1 
 * 	Assignment:	no2
 * 
 *	===============================================================================
 *	MovingRightAngleTriangle.java : A shape that is a right-angle triangle.
 *	A right-angle triangle has 4 handles shown when it is selected (by clicking on it).
 *	===============================================================================
 */
import java.awt.*;

public class MovingRightAngleTriangle extends MovingRectangle {

	/* class and/or instance variables 	 
	 */
	private int npoints = 3;
	protected double[] xRatio;
  	protected double[] yRatio;

	/** constuctor to create a right-angle Triangle with default values
	 */
	public MovingRightAngleTriangle() {
		super();
	}

	/** constuctor to create a right-angle Triangle shape
	 */
	public MovingRightAngleTriangle(int x, int y, float w, float h, float bL, float bS, Color lC, Color rC, int mw, int mh, int pathType) {
		super(x, y, w, h, bL, bS, lC, rC, mw, mh, pathType);	
		xRatio = new double[] {0, 1, 0};
		yRatio = new double[] {0, 1, 1};
	}

	/** Returns whether the point is in the right-angle triangle or not
	 * @return true if and only if the point is in the right-angle triangle, false otherwise.
	 */
	public boolean contains(Point mousePt) {
		return polyShape().contains(mousePt);
	}

	/** Returns the Polygon object
	   * @return poly
	*/
	private Polygon polyShape() {
		int x, y;
		Polygon poly = new Polygon();
		for (int i = 0; i< npoints; i++) {
			x = p.x + (int) (xRatio[i] * width);
			y = p.y + (int) (yRatio[i] * height);
			poly.addPoint(x, y);
		}
		return poly;
	}

	/** draw the right-angle Triangle with the fill colour
	 *  If it is selected, draw the handles
	 *  @param g	the Graphics control
	 */
	public void draw(Graphics g) {
		// First, convert to new 2D graphics context
		Graphics2D g2d = (Graphics2D)g;		// type cast works, because g is really a Graphics2D object	
	
		// Draw the filled shape (with a gradient pattern)
		// --------------------------------------------
		Polygon polygon = polyShape();
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
		g2d.fillPolygon(polygon); 			// draw the outer triangles		
		
		// set up a dash pattern to be used for the border
		final float dashPattern[] = {borderDashLength, borderDashSeperation};

		// Define attributes of the pen style used to render the line (e.g. a dashed line) using class BasicStroke
		//   @param pen width, end caps, line joins, miter limit, dash pattern, dash phase (see Java Tut)                    
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
		g2d.drawPolygon(polygon);  	// draw the inner diamond
		drawHandles(g2d);		
	}
}