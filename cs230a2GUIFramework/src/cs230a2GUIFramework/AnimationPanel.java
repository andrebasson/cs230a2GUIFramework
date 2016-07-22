package cs230a2GUIFramework;

/*  Name: 		Andre Basson
 * 	UPI:		abas396
 * 	Course:		CS230, 2014s1 
 * 	Assignment:	no2
 * 
 *  ====================================================================== 
 *	AnimationPanel.java : Moves shapes around on the screen according to different paths.
 *	It is the main drawing area where shapes are added and manipulated.
 *	It also contains a popup menu to clear all shapes.
 *	======================================================================
 */

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class AnimationPanel extends JComponent implements Runnable {
	// FIELDS
	// ======
	private Thread animationThread = null;	// the thread for animation
	private ArrayList<MovingShape> shapes;	// the arraylist to store all shapes
	private int delay = 30; 				// the current animation speed	
	private int currentShapeType, 			// the current shape type
		currentPath; 						// the current path type
	private float currentWidth = 50,		// the current width of a shape
		currentHeight = 20,					// the current height of a shape
		currentRadius = 20;					// the current radius of a shape that is a circle

	private float currentBorderDashLength = 3.0f,		// the current dash length of a shape's border
		currentBorderDashSeperation = 0.0f;				// the current dash seperation of a shape's border
	
	private Color currentLeftColor = Color.BLUE,	// current fill (left and right) colours of a shape 
			currentRightColor = Color.BLUE;
	
	JPopupMenu popup;				// popup menu
	

	// METHODS
	// =======
	 /** Constructor of the AnimationPanel
		*/
	 public AnimationPanel() {
		shapes = new ArrayList<MovingShape>(); 	//create the vector to store shapes
		popup = new JPopupMenu(); 				//create the popup menu
		makePopupMenu();

		// add the mouse event to handle popup menu and create new shape - uses anonymous type MouseAdapter to implement event handlers
		addMouseListener( new MouseAdapter() {
			// event handler 
			public void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}
			// event handler 
			public void mouseReleased(MouseEvent e) {
				maybeShowPopup(e);
			}
			// event handler helper method
			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
			// event handler 			
			public void mouseClicked( MouseEvent e ) {
				if (animationThread != null) {	// if the animation has started, then
					boolean found = false;
					MovingShape currentShape = null;
					for (int i = 0; i < shapes.size(); i++) {
						currentShape = shapes.get(i);
						if ( currentShape.contains( e.getPoint()) ) { // if the mousepoint is within a shape, then set the shape to be selected/deselected
							found = true;
							currentShape.setSelected( ! currentShape.isSelected() );
							System.out.println(currentShape);	// output to console (i.e. not to AnimationPanel!!)
						}
					}
					if (! found) createNewShape(e.getX(), e.getY()); // if the mousepoint is not within a shape, then create a new one according to the mouse position
				}
			}
		});
	}

	/** create a new shape
	 * @param x 	the x-coordinate of the mouse position
	 * @param y	the y-coordinate of the mouse position
	 */
	protected void createNewShape(int x, int y) {
		// get the margin of the frame
		Insets insets = getInsets();
		int marginWidth = getWidth() - insets.left - insets.right;
		int marginHeight = getHeight() - insets.top - insets.bottom;
		// create a new shape dependent on all current properties and the mouse position
		switch (currentShapeType) {
			case 0: { //rectangle
				shapes.add( new MovingRectangle(x, y, currentWidth,	currentHeight, currentBorderDashLength, currentBorderDashSeperation, currentLeftColor, currentRightColor, marginWidth, marginHeight, currentPath));
				break;
			} case 1: { //square
				float currentSize = Math.min(currentWidth, currentHeight);
				shapes.add( new MovingSquare(x, y, currentSize, currentBorderDashLength, currentBorderDashSeperation, currentLeftColor, currentRightColor, marginWidth, marginHeight, currentPath));
				break;
			} case 2: { //triangle
				shapes.add( new MovingRightAngleTriangle(x, y, currentWidth, currentHeight, currentBorderDashLength, currentBorderDashSeperation, currentLeftColor, currentRightColor, marginWidth, marginHeight, currentPath));
				break;
			} case 3: { //circle
				//float rad = currentWidth;
				shapes.add( new MovingCircle(x, y, currentRadius, currentBorderDashLength, currentBorderDashSeperation, currentLeftColor, currentRightColor, marginWidth, marginHeight, currentPath));
				break;
			}
		}
	}

	/** set the current shape type
	 * @param s	the new shape type
	 */
	public void setCurrentShapeType(int s) {
		currentShapeType = s;
	}

	/** set the current path type and the path type for all currently selected shapes
	 * @param t	the new path type
	 */
	public void setCurrentPathType(int t) {
		currentPath = t;
		MovingShape currentShape = null;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			if ( currentShape.isSelected()) {
				currentShape.setPath(currentPath);
			}
		}
	}

	/** set the current width and the width for all currently selected shapes (excl. circle)
	 * @param w	the new width value
	 */
	public void setCurrentWidth(float w) {
		MovingShape currentShape = null;
		currentWidth = w;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			if ( currentShape.isSelected() && !(currentShape instanceof MovingCircle)) {
				currentShape.setWidth(currentWidth);
			}
		}
	}

	/** set the current height and the height for all currently selected shapes (excl. circle)
	 * @param h	the new height value
	 */
	public void setCurrentHeight(float h) {
		MovingShape currentShape = null;
		currentHeight = h;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			if ( currentShape.isSelected() && !(currentShape instanceof MovingCircle)) {
				currentShape.setHeight(currentHeight);
			}
		}
	}
	
	// *** STAGE3 ***	
	// --------------
	/** set the CURRENT dashed border dash length and the dashed border dash length for all currently selected shapes
	 * @param dL the new dash length value
	 */
	public void setCurrentBorderDashLength(float dL) {
		MovingShape currentShape = null;	// null for now, and in case no shape has been 'clicked' on
		currentBorderDashLength = dL;

		// and now for all currently selected shapes		
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			if ( currentShape.isSelected()) {
				currentShape.setBorderDashLength(currentBorderDashLength);
			}
		}
	}	

	// *** STAGE3 ***	
	// --------------	
	/** set the CURRENT dashed border seperation and the dashed border seperation for all currently selected shapes
	 * @param dL the new dash length value
	 */
	public void setCurrentBorderDashSeperation(float dS) {
		MovingShape currentShape = null;	// null for now, and in case no shape has been 'clicked' on
		currentBorderDashSeperation = dS;	
		
		// and now for all currently selected shapes
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			if ( currentShape.isSelected()) {
				currentShape.setBorderDashSeperation(currentBorderDashSeperation);
			}
		}
	}	
	
	// *** STAGE4 ***	
	// --------------	
	/** set the CURRENT left fill color  and the left fill color for all currently selected shapes
	 * @param c the new left fill color
	 */
	public void setCurrentLeftColor(Color c) {
		MovingShape currentShape = null;	// null for now, and in case no shape has been 'clicked' on
		currentLeftColor = c;
		
		// and now set left fill color for all currently selected shapes
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			if (currentShape.isSelected()) {
				currentShape.setLeftColor(c);
			}			
		}
	}
	
	// *** STAGE4 ***	
	// --------------
	/** set the CURRENT right fill color and the right fill color for all currently selected shapes
	 * @param c the new right fill color
	 */
	public void setCurrentRightColor(Color c) {
		MovingShape currentShape = null;	// null for now, and in case no shape has been 'clicked' on
		currentRightColor = c;
		
		// and now set left fill color for all currently selected shapes
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			if (currentShape.isSelected()) {
				currentShape.setRightColor(c);
			}			
		}
	}	
	
	// *** STAGE5 ***	
	// --------------
	/** set the CURRENT right fill color and the right fill color for all currently selected shapes
	 * @param c the new right fill color
	 */
	public void setCurrentCircleRadius(float r) {
		MovingShape currentShape = null;	// null for now, and in case no shape has been 'clicked' on
		currentRadius = r;
		
		// and now set the radius for all currently selected circle shapes only
		for (int i = 0; i < shapes.size(); i++) {
			currentShape = shapes.get(i);
			if ( (currentShape.isSelected()) && (currentShape instanceof MovingCircle)) {
				((MovingCircle)currentShape).setCircleRadius(r);
			}
		}
	}		

	/** get the current width
	 * @return currentWidth - the width value
	 */
	public float getCurrentWidth() {
		return currentWidth;
	}

	/** get the current height
	 * @return currentHeight - the height value
	 */
	public float getCurrentHeight() {
		return currentHeight;
	}
	
	// *** STAGE3 ***	
	// --------------
	/** get the current border dash length
	 * @return currentBorderDashLength value
	 */
	public float getCurrentBorderDashLength() {
		return currentBorderDashLength;
	}	

	// *** STAGE3 ***	
	// --------------
	/** get the current border dash seperation
	 * @return currentBorderDashSeperation value
	 */
	public float getCurrentBorderDashSeperation() {
		return currentBorderDashSeperation;
	}		

	// *** STAGE4 ***
	// --------------	
	/** get the current left fill colour
	 * @return currentLeftColor value
	 */
	public Color getCurrentLeftColor() {
		return currentLeftColor;
	}		
	
	// *** STAGE4 ***	
	// --------------	
	/** get the current right fill colour
	 * @return currentRightColor value
	 */
	public Color getCurrentRightColor() {
		return currentRightColor;
	}	
	
	// *** STAGE5 ***
	// --------------	
	/** get the current circle radius
	 * @return current width/2
	 */
	public float getCurrentCircleRadius() {
		return currentRadius;
	}
	
 	/** remove all shapes from our vector
	 */
	public void clearAllShapes() {
		shapes.clear();
	}

	// you don't need to make any changes after this line 
	// ______________

	/** create the popup menu for our animation program
	 */
	protected void makePopupMenu() {
		JMenuItem menuItem;
		// clear all
		menuItem = new JMenuItem("Clear All");
		menuItem.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearAllShapes();
			}
		});
		popup.add(menuItem);
	 }

	/** reset the margin size of all shapes from our vector
	 */
	public void resetMarginSize() {
		Insets insets = getInsets();
		int marginWidth = getWidth() - insets.left - insets.right;
		int marginHeight = getHeight() - insets.top - insets.bottom ;
		 for (int i = 0; i < shapes.size(); i++)
			( shapes.get(i)).setMarginSize(marginWidth, marginHeight);
	}

	/**	update the painting area
	 * @param g	the graphics control
	 */
	public void update(Graphics g){
		paint(g);
	}

	/**	move and paint all shapes within the animation area
	 * @param g	the Graphics control
	 */
	public void paintComponent(Graphics g) {
		MovingShape currentShape;
		for (int i = 0; i < shapes.size(); i++) {
			currentShape =  shapes.get(i);
			currentShape.move();
			currentShape.draw(g);
		}
	}

	/** change the speed of the animation
	 * @param newValue 	the speed of the animation in ms
	 */
	public void adjustSpeed(int newValue) {
		if (animationThread != null) {
			stop();
			delay = newValue;
			start();
		}
	}

	/**	When the "start" button is pressed, start the thread
	 */
	public void start() {
		animationThread = new Thread(this);
		animationThread.start();
	}

	/**	When the "stop" button is pressed, stop the thread
	 */
	public void stop() {
		if (animationThread != null) {
			animationThread = null;
		}
	}

	/** run the animation
	 */
	public void run() {
		Thread myThread = Thread.currentThread();
		while(animationThread==myThread) {
			repaint();
			pause(delay);
		}
	}

	/** Sleep for the specified amount of time
	 */
	private void pause(int milliseconds) {
		try {
			Thread.sleep((long)milliseconds);
		} catch(InterruptedException ie) {}
	}
	
} // e/o class
