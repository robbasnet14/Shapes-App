import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Line extends MyShape {

	/**
     * Default constructor for Line.
     */

	public Line() {

		super();
	}

	/**
     * Constructor for Line that takes two Point2D parameters.
     * @param p1 The starting point of the line.
     * @param p2 The ending point of the line.
     */
	public Line( Point2D p1, Point2D p2) {

		super(p1, p2);
	}

	/**
     * Constructor for Line that takes four double parameters.
     * @param x1 The x-coordinate of the starting point.
     * @param x2 The x-coordinate of the ending point.
     * @param y1 The y-coordinate of the starting point.
     * @param y2 The y-coordinate of the ending point.
     */
	public Line( double x1, double x2, double y1, double y2) {

		super(x1, x2, y1, y2);
	}

	/**
     * Draws the line on the graphics context.
     * @param gc The GraphicsContext used for drawing.
     */
	@Override
	public void draw (GraphicsContext gc) {

		drawBounds(gc);
		gc.setStroke(color);
		
		gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());

	}
	
	/**
	 * @return A string representation of the Line object
	 */
	@Override 
	public String toString() {
		
		return "Line " + super.toString();
	}
}

