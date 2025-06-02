
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class MyShape implements Cloneable{
	protected transient Point2D  p1, p2, center;
	protected transient Color    color;
	protected boolean filled;
	protected double ulx, uly, width, height;

	/**
	 * Default constructor for MyShape
	 */
	public MyShape() {

	}

	/**
	 * Constructor for MyShape that takes two Point2D parameters
	 * @param p1 The first point of the shape
	 * @param p2 The second point of the shape
	 */
	public MyShape( Point2D p1, Point2D p2) {

		this.p1 = p1;
		this.p2 = p2;

		color = Color.BLACK;
		filled = false;

		updateBounds();
		updateCenter();
	}

	/**
	 * Constructor for MyShape that takes four double parameters
	 * 
	 * @param x1 The x of the first point
	 * @param x2 The x of the second point 
	 * @param y1 The y of the first point
	 * @param y2 The y of the second point
	 */
	public MyShape( double x1, double x2, double y1, double y2) {

		this(new Point2D(x1, y1), new Point2D(x2, y2));
	}

	/**
	 * 
	 * @return The first point of the shape
	 */
	public Point2D getP1() {

		return p1;
	}

	/**
	 * 
	 * @return The second point of the shape
	 */
	public Point2D getP2() {

		return p2;
	}

	/**
	 * 
	 * @return The color of the shape
	 */
	public Color getColor() {

		return color;
	}

	/**
	 * 
	 * @return True if the shape is filled, false otherwise.
	 */
	public boolean isFilled() {

		return filled;
	}

	/**
	 * 
	 * @return The upper left x-coordinate of the bounding box
	 */
	public double getULX() {

		return ulx;
	}

	/**
	 * 
	 * @return The upper left y-coordinate of the bounding box
	 */
	public double getULY() {

		return uly;
	}

	/**
	 * 
	 * @return The width of the bounding box
	 */
	public double getWidth() {

		return width;
	}

	/**
	 * 
	 * @return The height of the bounding box
	 */
	public double getHeight() {

		return height;
	}

	/**
	 * 
	 * @return The center point of the shape
	 */
	public Point2D getCenter() {

		return center;
	}

	/**
	 * 
	 * @param p1 The new first point of the shape
	 */
	public void setP1( Point2D p1) {

		this.p1 = p1;
	}

	/**
	 * 
	 * @param x The x-coordinate of the new first point
	 * @param y The y-coordinate of the new first point
	 */
	public void setP1( double x, double y) {

		p1 = new Point2D(x, y);
	}

	/**
	 * 
	 * @param p2 The new second point of the shape
	 */
	public void setP2( Point2D p2) {

		this.p2 = p2;

		updateBounds();
		updateCenter();
	}

	/**
	 * 
	 * @param x The x-coordinate of the new second point
	 * @param y The y-coordinate of the new second point
	 */
	public void setP2( double x, double y) {

		p2 = new Point2D(x, y);

		updateBounds();
		updateCenter();
	}

	/**
	 * 
	 * @param color The new color of the shape
	 */
	public void setColor( Color color) {

		this.color = color;
	}

	/**
	 * 
	 * @param filled True if the shape should be filled else false
	 */
	public void setFilled( boolean filled) {

		this.filled = filled;
	}

	/**
	 * Updates the bounding box of the shape
	 */
	protected void updateBounds() {

		ulx = Math.min(p1.getX(), p2.getX());
		uly = Math.min(p1.getY(), p2.getY());
		width = Math.abs(p2.getX() - p1.getX());
		height = Math.abs(p2.getY() - p1.getY());
	}

	/**
	 * Updates the center point of the shape
	 */
	protected void updateCenter() {

		center = p1.midpoint(p2);
	}

	/**
	 * Calculates the Euclidean distance between a point and the center of the shape
	 * 
	 * @param x The x-coordinate of the point
	 * @param y The y-coordinate of the point
	 * @return The distance between the point and the center of the shape
	 **/
	public double distance( double x, double y) {

		return center.distance(x, y);
	}

	/**
	 * Draws the bounding box of the shape.
	 * @param gc The GraphicsContext used for drawing
	 */
	public void drawBounds(GraphicsContext gc) {

		gc.setLineDashes(3);
		gc.setStroke(color);
		gc.strokeRect(ulx, uly, width, height);
		gc.setLineDashes(null);

	}

	/**
	 * Abstract method for drawing the shape
	 * @param gc The GraphicsContext used for drawing
	 */
	public abstract void draw(GraphicsContext gc);

	/**
	 * Return a string representation of the MyShape object
	 */
	@Override
	public String toString() {

		return String.format("%.0f %.0f %.0f %.0f %.3f %.3f %.3f %s",p1.getX(), p1.getY(), p2.getX(), p2.getY(), color.getRed(), color.getGreen(), color.getBlue(), isFilled());
	}

	/**
	 * Custom serialization method to handle serialization of MyShape objects
	 *
	 * @param in The ObjectInputStream to read data from
	 * @throws ClassNotFoundException If the class of a object cannot be found
	 * @throws IOException            If an I/O error occurs while reading
	 */
	private void readObject(ObjectInputStream in) throws ClassNotFoundException, IOException {

		in.defaultReadObject();

		double r = in.readDouble();
		double g = in.readDouble();
		double b = in.readDouble();

		color = Color.color(r, g, b);

		double ulx = in.readDouble();
		double uly = in.readDouble();
		double width = in.readDouble();
		double height = in.readDouble();

		this.p1 = new Point2D(ulx, uly);
		this.p2 = new Point2D(width, height);

		updateCenter();
	}

	/**
	 * Custom serialization method to handleMyShape objects
	 *
	 * @param oos The ObjectOutputStream to write data to
	 * @throws IOException If an I/O error occurs
	 */
	private void writeObject(ObjectOutputStream oos) throws IOException {

		oos.defaultWriteObject();

		oos.writeDouble(color.getRed());
		oos.writeDouble(color.getGreen());
		oos.writeDouble(color.getBlue());

		oos.writeDouble((p1.getX()));
		oos.writeDouble((p1.getY()));
		oos.writeDouble((p2.getX()));
		oos.writeDouble((p2.getY()));
	}

	/**
	 * Moves the shape in the x and y directions.
	 * 
	 * @param dx the amount to move in the x
	 * @param dy the amount to move in the y
	 */
	public void move( double dx, double dy) {

		p1 = new Point2D (p1.getX()+ dx, p1.getY() + dy);
		p2 = new Point2D(p2.getX() + dx, p2.getY() + dy);
		updateBounds();
		updateCenter();
	}

	/**
	 * Overrides the clone method to create a deep copy of the shape
	 */
	@Override
	public Object clone() {

		try {

			MyShape clone = (MyShape) super.clone();
			clone.p1 = new Point2D(p1.getX(), p1.getY());
			clone.p2 = new Point2D(p2.getX(), p2.getY());
			return clone;
		} 

		catch (CloneNotSupportedException e) {

			System.err.println("shapes cloning unsucessful");
			return null;
		}
	}
}

