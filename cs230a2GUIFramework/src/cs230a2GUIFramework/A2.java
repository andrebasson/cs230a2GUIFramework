package cs230a2GUIFramework;

/*  Name: 		Andre Basson
 * 	UPI:		abas396
 * 	Course:		CS230, 2014s1 
 * 	Assignment:	no2
 *
 *  ============================================================= *  
 *  A2.java : Extends JApplet and contains a panel where
 *  shapes move around on the screen. Also contains start and stop
 *  buttons that starts animation and stops animation respectively.
 *  ==============================================================
 */

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.border.*;
import javax.swing.event.*;

import java.util.Vector;

public class A2 extends JApplet {		// JApplet = top-level container
	AnimationPanel panel;  				// panel for bouncing area
	JButton startButton, stopButton;  	// buttons to start and stop the animation
	JButton leftButton, rightButton;
	
	/** main method for A1
	 */
	public static void main(String[] args) {
		
		// Set up applet, frame, panels (AnimationPanel, toolsPanel, buttonsPanel) and other components
		//
		A2 applet = new A2();
		
		// Create a frame with content pane (Java system creates content pane automatically)
		JFrame frame = new JFrame();	// NOTE:  Every JFrame has a pane (content pane) automatically created
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		// initialize frame to exit application completely when closed
		frame.getContentPane().add(applet, BorderLayout.CENTER);	// frame.getContentPane() -> content pane is a container for panels & components
																	// BorderLayout is the layout manager

		// *** STAGE 1 ***
		// ---------------
		frame.setTitle("Bouncing Application by abas396");
		
		applet.init();	// initialize panels & components - overrides init() in Applet
		applet.start();	// informs JApplet object to start execution, always called AFTER init()
		frame.setSize(800, 500);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = frame.getSize();
		frame.setLocation((d.width - frameSize.width) / 2, (d.height - frameSize.height) / 2);
		frame.setVisible(true);
	}

	/** init method to initialise components
	 */
	public void init() {
		// create, initialize and add to the frame an Animations panel, tools panel and buttons panel; each with its own components
		panel = new AnimationPanel();		// panel where all the shapes will be animated in
		add(panel, BorderLayout.CENTER);  	// layout this panel to the center region of panel	
		add(setUpToolsPanel(), BorderLayout.NORTH);	
		add(setUpButtons(), BorderLayout.SOUTH);	
		
		addComponentListener(new ComponentAdapter() { // resize the frame and reset all margins for all shapes
				public void componentResized(ComponentEvent componentEvent) {
					panel.resetMarginSize();
			 }
		 });
	}

	/** Set up the tools panel
	* @return toolsPanel		the Panel
	 */
	public JPanel setUpToolsPanel() {
		// create a panel to show some 'tools' (components)
		JPanel toolsPanel = new JPanel();
		
		// Set up the 'tools' to add to the toolsPanel
		// ===========================================	

		// Set up the shape combo box
		//
		ImageIcon squareButtonIcon = createImageIcon("square.gif");
		ImageIcon rectangleButtonIcon = createImageIcon("rectangle.gif");
		ImageIcon rightAngleButtonIcon = createImageIcon("rightAngle.gif");
		ImageIcon circleButtonIcon = createImageIcon("circle.gif");		
		JComboBox<ImageIcon> shapesComboBox = new JComboBox<ImageIcon>(new ImageIcon[] {rectangleButtonIcon, squareButtonIcon, rightAngleButtonIcon, circleButtonIcon,} );
		shapesComboBox.setToolTipText("Set shape");
		shapesComboBox.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				//set the Current shape type based on the selection: 0 for Circle, 1 for Rectangle etc
				panel.setCurrentShapeType(cb.getSelectedIndex());
			}
		});
		
		//Set up the path combo box
		//
		ImageIcon boundaryButtonIcon = createImageIcon("boundary.gif");
		ImageIcon fallingButtonIcon = createImageIcon("falling.gif");

		// ** STAGE6 ** 2 new images added for extra paths
		ImageIcon clockWiseButtonIcon = createImageIcon("clockWise.gif");	
		ImageIcon risingButtonIcon = createImageIcon("rising.gif");		

		JComboBox<ImageIcon> pathComboBox = new JComboBox<ImageIcon>(new ImageIcon[] {boundaryButtonIcon, 
													fallingButtonIcon, clockWiseButtonIcon, risingButtonIcon });
		pathComboBox.setToolTipText("Set Path");
		pathComboBox.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				//set the Current path type based on the selection from combo box: 0 for Boundary Path, 1 for bouncing Path
				panel.setCurrentPathType(cb.getSelectedIndex());
			}
		});
		
		//Set up the height TextField
		//
		JTextField heightTxt = new JTextField("20");
		heightTxt.setToolTipText("Set Height");
		heightTxt.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				try {
					
					int newValue = Integer.parseInt(tf.getText());
					if (newValue > 0) // if the value is valid, then change the current height
					 	panel.setCurrentHeight(newValue);
					else {
						tf.setText(panel.getCurrentHeight()+"");
						throw new NumberOutOfRangeException("Please enter a height value larger than 0");
					}
				} 
				catch (NumberOutOfRangeException ex) {
					/* show error message for invalid entry
					 *  @param: Component parentComponent, Object message, String title, int messageType)
					 */					
					JOptionPane.showMessageDialog(panel, ex.getMessage(), "Invalid entry", JOptionPane.ERROR_MESSAGE);					
				}
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(panel, "Please enter a numerical value", "Invalid entry", JOptionPane.ERROR_MESSAGE);					
					tf.setText(panel.getCurrentHeight()+""); //if the number entered is invalid, reset it
				}
				catch (Exception ex) {						// catches all general (other) exceptions			
					tf.setText(panel.getCurrentWidth()+"");	// reset value to previous					
				}				
			}			
		});

		//Set up the width TextField
		//
		JTextField widthTxt = new JTextField("50");
		widthTxt.setToolTipText("Set Width");
		widthTxt.addActionListener( new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				try {
					int newValue = Integer.parseInt(tf.getText());
					if (newValue > 0)
					 	panel.setCurrentWidth(newValue);
					else {
						tf.setText(panel.getCurrentWidth()+"");
						throw new NumberOutOfRangeException("Please enter a width value larger than 0");
					}					
				}
				catch (NumberOutOfRangeException ex) {
					/* show error message for invalid entry
					 *  @param: Component parentComponent, Object message, String title, int messageType)
					 */					
					JOptionPane.showMessageDialog(panel, ex.getMessage(), "Invalid entry", JOptionPane.ERROR_MESSAGE);					
				}
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(panel, "Please enter a numerical value", "Invalid entry", JOptionPane.ERROR_MESSAGE);					
					tf.setText(panel.getCurrentWidth()+"");	 // reset value to previous
				}
				catch (Exception ex) {						// catches all general (other) exceptions			
					tf.setText(panel.getCurrentWidth()+"");	// reset value to previous					
				}
			}
		});


		// *** STAGE3 *** - set up the dashed border length TextField
		// ----------------------------------------------------------		
		JTextField tDashLength = new JTextField("3.0");  // text field to change the dashed borders of all shapes
		tDashLength.setToolTipText("Set Border Dash Length");	

		// register an event with this component, by adding some Listener, implementing event handler(s) using an anonymous class (see lecture 12, slide 25)
		tDashLength.addActionListener(new ActionListener() {
			// event handler
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				try {
					float newValue = Float.parseFloat(tf.getText());					
					if (newValue > 0.0)
					 	panel.setCurrentBorderDashLength(newValue);
					else { 
						tf.setText(panel.getCurrentBorderDashLength() + "");
						throw new NumberOutOfRangeException("Please enter a border dash length larger than 0");
					}						
				} 
				catch (NumberOutOfRangeException ex) {
					/* show error message for invalid entry
					 *  @param: Component parentComponent, Object message, String title, int messageType)
					 */					
					JOptionPane.showMessageDialog(panel, ex.getMessage(), "Invalid entry", JOptionPane.ERROR_MESSAGE);					
				}
				catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(panel, "Please enter a numerical value", "Invalid entry", JOptionPane.ERROR_MESSAGE);					
					tf.setText(panel.getCurrentBorderDashLength()+"");	//if the number entered is invalid, reset it
				}
				catch (Exception ex) {									// catches all general (other) exceptions
					tf.setText(panel.getCurrentBorderDashLength()+"");	// if the number entered is invalid, reset it					
				}
			}
		});
		

		// *** STAGE3 *** - set up the dashed border seperation TextField		
		// --------------------------------------------------------------
		JTextField tDashSeperation = new JTextField("0.0");	// Text field to change dashed borders for all shapes
		tDashSeperation.setToolTipText("Set Border Dash Seperation");
		
		// register an event with this componet, by adding some Listener, implementing event handler(s) using an anonymous class (see lecture 12, slide 25)
		tDashSeperation.addActionListener(new ActionListener() {
			// event handler
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				try {
					float newValue = Float.parseFloat(tf.getText());
					if (newValue >= -0.0000001)
					 	panel.setCurrentBorderDashSeperation(newValue);
					else { 						
						tf.setText(panel.getCurrentBorderDashSeperation()+"");
						throw new NumberOutOfRangeException("Please enter a border dash seperation value equal or larger than 0");
					}
				}
				catch (NumberOutOfRangeException ex) {
					/* show error message for invalid entry
					 *  @param: Component parentComponent, Object message, String title, int messageType)
					 */					
					JOptionPane.showMessageDialog(panel, ex.getMessage(), "Invalid entry", JOptionPane.ERROR_MESSAGE);					
				}
				catch (NumberFormatException ex) {
				
					JOptionPane.showMessageDialog(panel, "Please enter a numerical value", "Invalid entry", JOptionPane.ERROR_MESSAGE);					
					tf.setText(panel.getCurrentBorderDashSeperation()+"");	//if the number entered is invalid, reset it
				}
				catch (Exception ex) {									// catches all general (other) exceptions
					tf.setText(panel.getCurrentBorderDashLength()+"");	// if the number entered is invalid, reset it					
				}							
			}
		});		

		// *** STAGE5 *** - set up the circle radius TextField
		// ----------------------------------------------------------		
		JTextField tRadius = new JTextField("20.0");  // text field to change the circle radius of all shapes
		tRadius.setToolTipText("Set Circle Radius");	

		// register an event with this component, by adding some Listener, implementing event handler(s) using an anonymous class (see lecture 12, slide 25)
		tRadius.addActionListener(new ActionListener() {
			// event handler
			public void actionPerformed(ActionEvent e) {
				JTextField tf = (JTextField)e.getSource();
				try {
					float newValue = Float.parseFloat(tf.getText());					
					if (newValue > 0.0)
					 	panel.setCurrentCircleRadius(newValue);
					else {
						tf.setText(panel.getCurrentCircleRadius()+"");
						throw new NumberOutOfRangeException("Please enter a radius value larger than 0");
					}
				}
				catch (NumberOutOfRangeException ex) {
					/* show error message for invalid entry
					 *  @param: Component parentComponent, Object message, String title, int messageType)
					 */					
					JOptionPane.showMessageDialog(panel, ex.getMessage(), "Invalid entry", JOptionPane.ERROR_MESSAGE);					
				}				
				catch (NumberFormatException ex) {
				
					JOptionPane.showMessageDialog(panel, "Please enter a numerical value", "Invalid entry", JOptionPane.ERROR_MESSAGE);
					tf.setText(panel.getCurrentCircleRadius()+"");		//if the number entered is invalid, reset it
				}
				catch (Exception ex) {									// catches all general (other) exceptions
					tf.setText(panel.getCurrentBorderDashLength()+"");	//if the number entered is invalid, reset it					
				}					
			}
		});		
		
		// add to the toolsPanel created the labels and components set up above
		toolsPanel.setLayout(new BoxLayout(toolsPanel, BoxLayout.X_AXIS));
		toolsPanel.add(new JLabel(" Shape: ", JLabel.RIGHT));
		toolsPanel.add(shapesComboBox);
		toolsPanel.add(new JLabel(" Path: ", JLabel.RIGHT));
		toolsPanel.add(pathComboBox);
		toolsPanel.add( new JLabel(" Height: ", JLabel.RIGHT));
		toolsPanel.add(heightTxt);
		toolsPanel.add(new JLabel(" Width: ", JLabel.RIGHT));
		toolsPanel.add(widthTxt);
		toolsPanel.add(new JLabel(" Length: ", JLabel.RIGHT));
		toolsPanel.add(tDashLength);
		toolsPanel.add(new JLabel(" Seperation: ", JLabel.RIGHT));
		toolsPanel.add(tDashSeperation);
		toolsPanel.add(new JLabel(" Radius: ", JLabel.RIGHT));		
		toolsPanel.add(tRadius);
		
		return toolsPanel;
	}


	
	/** Set up the buttons panel
		 * @return buttonPanel		the Panel
	 */
	public JPanel setUpButtons() {
		// create a panel to show some button components
		JPanel buttonPanel= new JPanel(new FlowLayout());
//		buttonPanel= new JPanel(new FlowLayout());

		
		// Set up the buttons to add to the buttonPanel
		// ============================================		

		// Assignment: STAGE4 - set up a 'left' button
		// -------------------------------------------
		leftButton = new JButton("Left");
		leftButton.setToolTipText("Gradient fill - colour on the left");
		leftButton.addActionListener(new ActionListener() {  // register event for this component (i.e. add actionlistener, 
			public void actionPerformed(ActionEvent e) {	 // implementing event handler(s) using an anonymous class (see lecture 12, slide 25)
	            // attempt to retrieve (from a color dialog box) and set a new colour for the leftButton; 
                // throw and catch exceptions when no color returned from color diaglog box (i.e. cancel button pressed)
                try 
                {                
                    // from API doc:  JColorChooser.showDiaglog(Component, String, Color) 
                    //                Create and show a color chooser in a modal dialog. 
                	// @param:		Component - parent of dialog box
                    //              String - the dialog title, 
                	//				Color  - the chooser's initial color.
                    Color newColor = JColorChooser.showDialog(leftButton, "Left Fill Color", leftButton.getForeground());
                                        
                    // should the color returned be null (i.e. CANCEL button clicked) - throw new exception (no changing of colour)
                    if (newColor == null)  
                        throw new NotAColorException("No new colour chosen - colour remain unchanged");
                    
                    // else, update colour variables
                    panel.setCurrentLeftColor(newColor);                      
                    leftButton.setForeground(newColor);
                    
                }
                catch (NotAColorException exception) 
                {
                    System.out.println();
                    System.out.println(exception);  // output to screen 
                                                    // - no update of currentColor in JPanel, or borderButton foreground colour
                }   				
			}
		});

		// Assignment: STAGE4 - set up a 'right' button
		// --------------------------------------------
		rightButton = new JButton("Right");
		rightButton.setToolTipText("Gradient fill - colour on the left");

		rightButton.addActionListener(new ActionListener() {  // register event for this component (i.e. add actionlistener, implementing  
			public void actionPerformed(ActionEvent e) {	  //  event handler(s) using an anonymous class (see lecture 12, slide 25)
	            // attempt to retrieve (from a color dialog box) and set a new colour for the leftButton; 
                // throw and catch exceptions when no color returned from color diaglog box (i.e. cancel button pressed)
                try 
                {                
                    // from API doc:  JColorChooser.showDiaglog(Component, String, Color) 
                    //                Create and show a color chooser in a modal dialog. 
                	// @param:		Component - parent of dialog box
                    //              String - the dialog title, 
                	//				Color  - the chooser's initial color.
                    Color newColor = JColorChooser.showDialog(rightButton, "Left Fill Color", rightButton.getForeground());
                                        
                    // should the color returned be null (i.e. CANCEL button clicked) - throw new exception (no changing of colour)
                    if (newColor == null)  
                        throw new NotAColorException("No new colour chosen - colour remain unchanged");
                    
                    // else, update colour variables
                    panel.setCurrentRightColor(newColor);                      
                    rightButton.setForeground(newColor);
                    
                }
                catch (NotAColorException exception) 
                {
                    System.out.println();
                    System.out.println(exception);  // output to screen 
                                                    // - no update of currentColor in JPanel, or borderButton foreground colour
                }   				
			}
		});			
		
		// Set up the start button
		startButton = new JButton("Start");
		startButton.setToolTipText("Start Animation");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startButton.setEnabled(false);
				stopButton.setEnabled(true);
				panel.start();  //start the animation
			}
		});

		//Set up the stop button
		stopButton = new JButton("Stop");
		stopButton.setToolTipText("Stop Animation");
		stopButton.setEnabled(false);
		stopButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				stopButton.setEnabled(false);
				startButton.setEnabled(true); //stop the animation
				panel.stop();
			 }
		});

		// Set up a slider to adjust the speed of the animation
		JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 200, 30);
		slider.setToolTipText("Adjust Speed");
		slider.addChangeListener(new ChangeListener() {
		 public void stateChanged(ChangeEvent e) {
			 JSlider source = (JSlider)e.getSource();
			 if (!source.getValueIsAdjusting()) {
				 int value = (int) (source.getValue());  // get the value from slider
				 TitledBorder tb = (TitledBorder) source.getBorder();
				 tb.setTitle("Anim delay = " + String.valueOf(value) + " ms"); //adjust the tilted border to indicate the speed of the animation
				 panel.adjustSpeed(value); //set the speed
				 source.repaint();
			 }
			}
		});		
		TitledBorder title = BorderFactory.createTitledBorder("Anim delay = 30 ms");  // set up a titled border and add to the slider
		slider.setBorder(title);

		// Now add buttons and slider control set up above, in the layout order you'd like		
		buttonPanel.add(leftButton);
		buttonPanel.add(new JLabel("  ", JLabel.RIGHT));	// make space using a label		
		buttonPanel.add(startButton);
		buttonPanel.add(new JLabel("  ", JLabel.RIGHT));	// make space using a label		
		buttonPanel.add(stopButton);
		buttonPanel.add(new JLabel("  ", JLabel.RIGHT));	// make space using a label		
		buttonPanel.add(slider);
		buttonPanel.add(new JLabel("  ", JLabel.RIGHT));	// make space using a label	
		buttonPanel.add(rightButton);
		
		return buttonPanel;
	}

	/** create the imageIcon
	 * @param  filename		the filename of the image
	 * @return ImageIcon		the imageIcon
	 */
	protected static ImageIcon createImageIcon(String filename) {
		java.net.URL imgURL = A2.class.getResource(filename);
		return new ImageIcon(imgURL);
	}
	
	
	// ** STAGES 3 THROUGH 6 **
	// Exceptions for storing attributes of specific exceptions occuring
	// -----------------------------------------------------------------

	/** NotAColorException:  a self-defined Exception class for when a Color object does not hold a color
	 * 		The constructor passes the String message received to the constructor of the base class
	 */
	private class NotAColorException extends Exception {
	   public NotAColorException(String errorMessage)
	   {
	       super(errorMessage);
	   }
	}

	/** NumberOutOfRangeException:  a self-defined Exception class for when a numerical primitive type holds a value
	 * 								outside a wanted range.
	 * 		The constructor passes the String message received to the constructor of the base class
	 */	
	private class NumberOutOfRangeException extends Exception {
		public NumberOutOfRangeException(String errorMessage) {
			super(errorMessage);			
		}
	}
	
	
}



