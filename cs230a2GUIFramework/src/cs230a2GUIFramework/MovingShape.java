package cs230a2GUIFramework;

/*  Name: 		Andre Basson
 * 	UPI:		abas396
 * 	Course:		CS230, 2014s1 
 * 	Assignment:	no2
 * 
 *	===============================================================================
 *	MovingShape.java : The superclass of all shapes.
 *	A shape has a point (top-left corner).
 *	A shape defines various properties, including selected, colour, width and height.
 *	===============================================================================
 */

import java.awt.*;
public abstract class MovingShape {

	public int marginWidth, marginHeight;	// the margin of the animation panel area
	protected Point p; 						// the top left coner of shapes
	protected float width;					// the width of shapes
	protected float height;					// the height of shapes
	protected float borderDashLength;		// the dash length of a shape's border
	protected float borderDashSeperation;	// the dash seperation of a shape's border
	protected final float borderWidth = 3.0f;			// the border width
	protected MovingPath path;				// the moving path of shapes
	protected boolean selected = false;		// draw handles if selected
	protected Color leftColor, rightColor;	// left and right fill colors of a shape

	/** constuctor to create a shape with default values
	 */
	public MovingShape() {
		this(0, 0, 20, 20, 3.0f, 0.0f, Color.BLUE, Color.BLUE, 500, 500, 0); 	// the default properties
	}

	/** constuctor to create a shape
	 * @param x 	the x-coordinate of the new shape
	 * @param y		the y-coordinate of the new shape
	 * @param w 	the width of the new shape
	 * @param h 	the height of the new shape
	 * @param bL	the border dash length of the new shape
	 * @param bS	the border dash seperation of the new shape
	 * @param lC	the left fill colour of the new shape
	 * @param rC	the right fill colour of the new shape
	 * @param mw 	the margin width of the animation panel
	 * @param mh	the margin height of the animation panel
	 * @param typeOfPath 		the path of the new shape
	 */
	public MovingShape(int x, int y, float w, float h, float bL, float bS, Color lC, Color rC, int mw, int mh, int pathType) {
		p = new Point(x,y);
		width = w;
		height = h;
		borderDashLength = bL;
		borderDashSeperation = bS;
		leftColor = lC;
		rightColor = rC;
		marginWidth = mw;
		marginHeight = mh;
		
		setPath (pathType);
	}

	/** Return the x-coordinate of the shape.
	 * @return the x coordinate
	 */
	public int getX() { return p.x; }

	/** Return the y-coordinate of the shape.
	 * @return the y coordinate
	 */
	public int getY() { return p.y;}

	/** Return the selected property of the shape.
	 * @return the selected property
	 */
	public boolean isSelected() { return selected; }

	/** Set the selected property of the shape.
	 *	When the shape is selected, its handles are shown.
	 * @param s 	the selected value
	 */
	public void setSelected(boolean s) { selected = s; }

	/** Set the width of the shape.
	 * @param w 	the width value
	 */
	public void setWidth(float w) { width = w; }

	/** Set the height of the shape.
	 * @param h 	the height value
	 */
	public void setHeight(float h) { height = h; }

	// *** STAGE3 ***
	/** Set the shape's border dash length.
	 * @param dL	the dash length value
	 */	
	public void setBorderDashLength(float dL) { borderDashLength = dL; }	
	
	// *** STAGE3 ***
	/** Set the shape's border dash seperation distance.
	 * @param dS	the dash seperation value
	 */		
	public void setBorderDashSeperation(float dS) { borderDashSeperation = dS; }	
	
	// *** STAGE3 ***
	/** Get the shape's border dash length.
	 * @return the border dash length
	 */		
	public float getBorderDashLength() { return borderDashLength; }

	// *** STAGE3 ***
	/** Get the shape's border dash seperation value.
	 * @return the border dash seperation value
	 */	
	public float getBorderDashSeperation() { return borderDashSeperation; }	

	// *** STAGE4 ***
	/** Set the shape's left fill color
	 * @param c	the left fill color value
	 */	
	public void setLeftColor(Color c) { leftColor = c; }	
	
	// *** STAGE4 ***
	/** Set the shape's right fill color
	 * @param c	the right fill color value
	 */	
	public void setRightColor(Color c) { rightColor = c; }		
	
	// *** STAGE4 ***
	/** get the shape's left fill color
	 * @return the left fill color value
	 */	
	public Color getLeftColor() { return leftColor; }
	
	// *** STAGE4 ***
	/** get the shape's right fill color
	 * @return the right fill color value
	 */	
	public Color getRightColor() { return rightColor; }	
	
	
	/**
	 * Return a string representation of the shape, containing
	 * the String representation of each element.
	 */
	public String toString() {
		return "[" + this.getClass().getName() + "," + p.x + "," + p.y + "]";
	}

	/** Draw the handles of the shape
	 * @param g 	the Graphics control
	 */
	public void drawHandles(Graphics g) {
		// if the shape is selected, then draw the handles
		if (isSelected()) {
			g.setColor(Color.black);
			g.fillRect(p.x -2, p.y-2, 4, 4);
			g.fillRect(p.x + (int)width -2, p.y + (int)height -2, 4, 4);
			g.fillRect(p.x -2, p.y + (int)height -2, 4, 4);
			g.fillRect(p.x + (int)width -2, p.y-2, 4, 4);
		}
	}

	/** Reset the margin for the shape
	 * @param w 	the margin width
	 * @param h 	the margin height
	 */
	public void setMarginSize(int w, int h) {
		marginWidth = w;
		marginHeight = h;
	}

	/** abstract contains method
	 * Returns whether the point p is inside the shape or not.
	 * @param p	the mouse point
	 */
	public abstract boolean contains(Point p);

	/** abstract draw method
	 * draw the shape
	 * @param g 	the Graphics control
	 */
	public abstract void draw(Graphics g);

	/** Set the path of the shape.
	 * @param pathID 	the integer value of the path
	 *	MovingPath.BOUNDARY is the boundary path
	 *	MovingPath.FALLING is the falling path
	 *	MovingPath.CLOCKWISE is similar to boundary path, but clockwise	 *
	 */
	public void setPath(int pathID) {
		switch (pathID) {
			case MovingPath.BOUNDARY : {
				path = new BoundaryPath(10, 10);
				break;
			}
			case MovingPath.FALLING : {
				path = new FallingPath();
				break;
			}
			// ** STAGE6 **, 2 new paths
			case MovingPath.CLOCKWISE : {
				path = new ClockwiseBoundaryPath(10, 10);
				break;
			}
			case MovingPath.RISING : {
				path = new RisingPath();
				break;
			}
			
		}
	}

	/** move the shape by the path
	 */
	public void move() {
		path.move();
	}

	// Inner class ===================================================================== Inner class

	/*
	 *	===============================================================================
	 *	MovingPath : The superclass of all paths. It is an inner class.
	 *	A path can change the current position of the shape.
	 *	===============================================================================
	 */

	public abstract class MovingPath {
		public static final int BOUNDARY = 0; // The Id of the moving path
		public static final int FALLING = 1; // The Id of the moving path

		// ** STAGE6 ** - including 2 new path Id's
		public static final int CLOCKWISE = 2;
		public static final int RISING = 3;
		
		protected int deltaX, deltaY; // moving distance

		/** constructor
		 */
		public MovingPath() { }

		/** abstract move method
		* move the shape according to the path
		*/
		public abstract void move();
	}

	/*
	 *	===============================================================================
	 *	FallingPath : A falling path.
	 *	===============================================================================
	 */
	public class FallingPath extends MovingPath {
		private double am = 0, stx =0, sinDeltax = 0;

		/** constructor to initialise values for a falling path
		 */
		public FallingPath() {
			am = Math.random() * 20; //set amplitude variables
			stx = 0.5; //set step variables
			deltaY = 5;
			//deltaY = -5;
			sinDeltax = 0;
		}

		/** move the shape
		 */
		public void move() {
			sinDeltax = sinDeltax + stx;
			p.x = (int) Math.round(p.x + am * Math.sin(sinDeltax));
			p.y = p.y + deltaY;
			if (p.y > marginHeight) // if it reaches the bottom of the frame, start again from the top
				p.y = 0;

			}
	} // e/o class FallingPath

	/*
	 *	================================= STAGE6 =====================================
	 *	RisingPath : A rising path.
	 *	==============================================================================
	 */
	public class RisingPath extends MovingPath {
		private double am = 0, stx =0, sinDeltax = 0;

		/** constructor to initialise values for a falling path
		 */
		public RisingPath() {
			am = Math.random() * 20; //set amplitude variables
			stx = 0.5; 				 //set step variables
			deltaY = 5;
			sinDeltax = 0;
		}

		/** move the shape
		 */
		public void move() {
			sinDeltax = sinDeltax + stx;
			p.x = (int) Math.round(p.x + am * Math.sin(sinDeltax));
			p.y = p.y - deltaY;
			if (p.y < 0) 			// if it reaches the top of the frame, start again from the bottom
				p.y = marginHeight;
			}
	} // e/o class FallingPath	
	

	/*
	 *	===============================================================================
	 *	BoundaryPath : A boundary path which moves the shape around the boundary of the frame
	 *	===============================================================================
	 */
	public class BoundaryPath extends MovingPath {
		private int direction;

		/** constructor to initialise values for a boundary path
		 */
		public BoundaryPath(int speedx, int speedy) {
			deltaX = (int) (Math.random() * speedx) + 1;
			deltaY = (int) (Math.random() * speedy) + 1;
			direction = 0;
		}
		

		/** move the shape
		 */
		public void move() {
			// to make sure the shape stays completely visible - deduct the height and width of the shape
			int h = marginHeight - (int)height;	
			int w = marginWidth - (int)width;
			
			switch	(direction) {
				// Movements clockwise
				case 0 : { // move downwards
					p.y += deltaY;
					if (p.y > h) {
						p.y = h - 1;
						direction = 90;
					}
					break;
				}
				case 90 : { // move to the right (after moving down)
					p.x += deltaX;
					if (p.x > w) {
						p.x = w - 1;
						direction = 180;
					}
					break;
				}
				case 180 : {
					p.y -= deltaY; // move upwards (after moving right)
					if (p.y < 0) {
						direction = 270;
						p.y = 0;
					}
					break;
				}
				case 270 : { // move to the left (after moving up)
					 p.x -= deltaX;
					 if (p.x < 0) {
							direction = 0;
							p.x = 0;
					 }
					 break;
				}
			} // e/o switch
		} // e/o method
	} // e/o class BoundaryPath


	/*
	 *	========================================= STAGE6 =============================================
	 *	ClockwiseBoundaryPath : A boundary path which moves the shape around the boundary of the frame
	 *							in a clockwise manner
	 *	==============================================================================================
	 */

	public class ClockwiseBoundaryPath extends MovingPath {
		private int direction;

		/** constructor to initialise values for a boundary path
		 */
		public ClockwiseBoundaryPath(int speedx, int speedy) {
			deltaX = (int) (Math.random() * speedx) + 1;
			deltaY = (int) (Math.random() * speedy) + 1;
			direction = 0;
		}


		/** move the shape
		 */
		public void move() {
			// to make sure the shape stays completely visible - deduct the height and width of the shape
			int h = marginHeight - (int)height;
			int w = marginWidth - (int)width;
			switch	(direction) {

				// movements counter clockwise
				case 0 : { 				// move up
					p.y -= deltaY;
					if (p.y < 0 ) {		// top of animation panel reached
						p.y = 0;	// adjust
						direction = -90;
					}
					break;
				}
				case -90 : { 			// move to the right
					p.x += deltaX;
					if (p.x > w) {		// right edge of panel reached
						p.x = w - 1;	// adjust
						direction = -180;
					}
					break;
				}
				case -180 : {			// move downwards
					p.y += deltaY; 		
					if (p.y > h) {		// top of animation panel reached
						p.y = h - 1;	// adjust
						direction = -270;						
					}
					break;
				}
				case -270 : { 			// move to the left
					 p.x -= deltaX;
					 if (p.x < 0) {		// left edge of animation panel reached
							p.x = 0;	// adjust
							direction = 0;	// start the path over								
					 }
					 break;
				}				
			} // e/o switch
		} // e/o method
	} // e/o class ClockwiseBoundaryPath	
	

// ========================================================================================
} // e/o outer class MovingShape