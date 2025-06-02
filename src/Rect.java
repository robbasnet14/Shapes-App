import java.io.Serializable;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Rect extends MyShape {

	/**
	 * Default constructor for Rect.
	 */
	public Rect() {
		super();
	}

	/**
	 * Constructor for Rect that takes two Point2D parameters.
	 * @param p1 The top-left corner of the bounding box of the rectangle.
	 * @param p2 The bottom-right corner of the bounding box of the rectangle.
	 */

	public Rect( Point2D p1, Point2D p2) {

		super(p1, p2);
	}

	/**
	 * Constructor for Rect that takes four double parameters.
	 * @param x1 The x-coordinate of the top-left corner of the bounding box.
	 * @param x2 The x-coordinate of the bottom-right corner of the bounding box.
	 * @param y1 The y-coordinate of the top-left corner of the bounding box.
	 * @param y2 The y-coordinate of the bottom-right corner of the bounding box.
	 */
	public Rect( double x1, double x2, double y1, double y2) {

		super(x1, x2, y1, y2);
	}

	/**
	 * Draws the Rect on the graphics context.
	 * @param gc The GraphicsContext used for drawing.
	 */
	@Override
	public void draw (GraphicsContext gc) {

		drawBounds(gc);

		if (isFilled()) {

			gc.setFill(color);
			gc.fillRect(ulx, uly, width, height);

		} 
		else {

			gc.setStroke(color);
			gc.strokeRect(ulx, uly, width, height);
		}

	}
	/**
	 * @return A string representation of the Rectangle object
	 */
	public String toString() {

		return "Rect " + super.toString();
	}

}


