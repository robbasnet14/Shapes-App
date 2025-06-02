import java.util.ArrayList;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShapeGroup extends MyShape {

	private ArrayList<MyShape> group;

	/**
	 * default constructor
	 */
	public ShapeGroup() {

		group = new ArrayList<>();
	}

	/**
	 * Checks if the group is empty
	 * 
	 * @return true if the group is empty
	 */
	public boolean isEmpty() {

		return group.isEmpty();
	}

	/**
	 * gives the size of the group
	 * 
	 * @return the number of shapes in the group
	 */
	public int size() {

		return group.size();
	}

	/**
	 * it clones the ShapeGroup
	 * 
	 * @return a clone of the ShapeGroup
	 */
	@Override
	public Object clone() {

		ShapeGroup clone = (ShapeGroup) super.clone();

		ArrayList<MyShape>cloneGroup = new ArrayList<>(group.size());

		for (MyShape shape : group) {

			cloneGroup.add((MyShape) shape.clone());
		}
		return clone;
	}

	/**
	 * add a shape to the group.
	 * 
	 * @param shape it is the shape to add to the group
	 */
	public void addMember(MyShape shape ) {

		if (!group.contains(shape)) {

			group.add((MyShape) shape.clone());
			updateCenter();
			updateBounds();
		}

	}

	/**
	 * remove a shape from the group
     * 
     * @param shape the shape to remove from the group
   	 */
	public void removeMember(MyShape shape) {

		group.remove(shape);
		updateCenter();
	}

	/**
	 * Checks if a shape is within the group
     * 
     * @param shape the shape to check
     * @return true if the shape is within the group
	 */
	public boolean within(MyShape shape) {

		Point2D centerS = shape.getCenter();

		return centerS.getX() >= ulx && centerS.getX() <= (ulx + width) && centerS.getY() >= uly && centerS.getY() <= (uly + height);
	}

	/**
	 * Updates the center of the group
	 */
	@Override
	public void updateCenter() {

		double sumX = 0;
		double sumY = 0;

		for (MyShape shape : group) {

			Point2D centerS = shape.getCenter();

			sumX += centerS.getX();
			sumY += centerS.getY();

		}

		center  = new Point2D (sumX / group.size(),(sumY / group.size()));	      
	}

	/**
	 * Moves the group by the specified amount
	 */
	@Override
	public void move(double dx, double dy) {

		for (MyShape shape : group) {

			shape.move(dx, dy);
		}

		super.move(dx, dy);

		updateCenter();
		updateBounds();
	}

	/**
	 * Draws the group and its bounds on the graphics context
	 */
	@Override
	public void draw(GraphicsContext gc) {

		drawBounds(gc);

		gc.setStroke(Color.LIGHTGRAY);

		for (MyShape shape : group) {

			shape.draw(gc);
		}
	}

	/**
	 * Returns a string representation of the ShapeGroup
	 */
	public String toString() {

		String sb = String.format("ShapeGroup " + " %d %.0f %.0f %.0f %.0f\n ", group.size(), p1.getX(), p1.getY(), p2.getX(), p2.getY());

		for (MyShape shape : group) {

			sb += shape.toString() + "\n";
		}

		return sb;
	}
	
	/**
	 * 
	 * @return An ArrayList of MyShape of the shapes
	 */
	public ArrayList<MyShape> getMembers() {
		
		return group;
	}
}




