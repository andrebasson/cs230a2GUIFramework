package cs230a2GUIFramework;

/*  Name: 		Andre Basson
 * 	UPI:		abas396
 * 	Course:		CS230, 2014s1 
 * 	Assignment:	no2
 * 
 *	===============================================================================
 *	MovingSquare.java : A shape that is a square.
 *	A square has 4 handles shown when it is selected (by clicking on it).
 *	===============================================================================
 */
import java.awt.*;
public class MovingSquare extends MovingRectangle {
	
	// no class or instance variables - inherits all fields from class MovingShape

	/** constuctor to create a square with default values
	 */
	public MovingSquare() {
		super();
	}

	/** constuctor to create a square shape with equal width and height
	 */
	public MovingSquare(int x, int y, float s, float bL, float bS, Color lC, Color rC, int mw, int mh, int pathType) {
		// s passed to constructor for both width and height fields
		super(x, y, s, s, bL, bS, lC, rC, mw, mh, pathType);
	}

	
	/** draw the square with the fill colour
	 *	If it is selected, draw the handles
	 *	@param g	the Graphics control
	 */
	public void draw(Graphics g) {
		super.draw(g);	// a square is just a type of rectangle; i.e. call the super class draw().	
	}


	/** Returns whether the point is in the rectangle or not
	 * @return true if and only if the point is in the rectangle, false otherwise.
	 */
	public boolean contains(Point mousePt) {
		return (p.x <= mousePt.x && mousePt.x <= (p.x + width + 1)	&&	p.y <= mousePt.y && mousePt.y <= (p.y + height + 1));
	}
}