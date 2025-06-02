import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ShapeCanvas extends Canvas {

	private GraphicsContext    gc;
	private ArrayList<MyShape> shapes;
	private MyShape 		   currShape;
	private Color  		       currColor; 
	private boolean 	       currFilled;
	private Stack<Edit> 			   stackUndo;
	private Stack<Edit> 			   stackRedo;
	private Edit 			   editted;

	/**
	 * Constructor for creating a ShapeCanvas with specified width and height.
	 *
	 * @param width  The width of the canvas.
	 * @param height The height of the canvas.
	 */	public ShapeCanvas(double width, double height) {

		 super(width, height);
		 gc = getGraphicsContext2D();
		 shapes = new ArrayList<>();
		 currColor = Color.BLACK;
		 currFilled = false;
		 stackRedo = new Stack<>();
		 stackUndo =  new Stack<>();
	 }

	 /**
	  * 
	  * @return The GraphicsContext object
	  */
	 public GraphicsContext getGraphicsContext() {

		 return gc;
	 }

	 /**
	  * 
	  * @return ArrayList of MyShape objects
	  */
	 public ArrayList<MyShape> getshapes(){

		 return shapes;	
	 }

	 /**
	  * 
	  * @return The current MyShape object
	  */
	 public MyShape getcurrShape() {

		 return currShape;
	 }

	 /**
	  * 
	  * @return The current Color object
	  */
	 public Color getcurrColor() {

		 return currColor;
	 }

	 /**
	  * 
	  * @return True if shapes should be filled, false otherwise
	  */
	 public boolean getcurrFilled() {

		 return currFilled;
	 }

	 /**
	  * Sets the current color for drawing
	  * 
	  * @param color The Color object to set
	  */
	 public void setcurrColor( Color color) {

		 currColor = color;
		 if (currShape != null) {
			 currShape.setColor(currColor);
		 }
	 }

	 /**
	  * Sets the current shape being drawn
	  *
	  * @param shape The MyShape object to set as the current shape
	  */
	 public void setcurrShape(MyShape s) {

		 currShape = s;

		 if (s!= null) {
			 currShape.setColor(currColor);
			 currShape.setFilled(currFilled);
		 }
	 }

	 /**
	  * Sets whether shapes should be filled or outlined
	  *
	  * @param filled True to fill shapes, false for outlines only
	  */
	 public void setcurrrFilled( boolean filled) {

		 currFilled = filled;
		 if (currShape != null) {
			 currShape.setFilled(currFilled);
		 }
	 }

	 /**
	  *  Repaints the canvas by drawing all shapes in the shapes list
	  */
	 public void paint() {

		 gc.clearRect(0, 0, getWidth(), getHeight());
		 for ( MyShape shape : shapes) {

			 shape.draw(gc);
		 }
		 if (currShape != null) {

			 currShape.draw(gc);
		 }

	 }

	 /**
	  * Adds a shape to the canvas and repaints it
	  *
	  * @param shape The MyShape object to add
	  */
	 public  void addShape(MyShape s) {

		 shapes.add(s);
	 }

	 /**
	  * Clears the canvas by removing all drawn shapes
	  */
	 public void clear() {

		 shapes.clear();
		 paint();
	 }

	 /**
	  * replaces the current mouse listener (press/release) and mouse motion listener (drag)
	  * with the passed listener object
	  *
	  * @param listener an EventHandler object 
	  */
	 public void replaceMouseHandler(EventHandler listener) {
		 setOnMousePressed(listener);
		 setOnMouseDragged(listener);
		 setOnMouseReleased(listener);
		 setOnMouseClicked(listener);
	 }

	 /**
	  * Saves the drawn shapes to a binary file
	  *
	  * @param newFile The File object that save
	  */
	 public void toBinaryFile(File newFile) {

		 try {
			 FileOutputStream fOS = new FileOutputStream(newFile);
			 ObjectOutputStream fOut = new ObjectOutputStream(fOS);

			 fOut.writeInt(shapes.size());

			 for(MyShape shape : shapes) {

				 fOut.writeObject(shape);
			 }

			 fOut.close();
			 fOS.close();
		 }
		 catch(FileNotFoundException e) {

			 System.out.println(newFile + " could not be opened for reading");
			 e.printStackTrace();		 
		 } 

		 catch (IOException e) {

			 e.printStackTrace();
		 }
	 }

	 /**
	  * Loads shapes from a binary file
	  *
	  * @param newFile The File object to load from
	  */
	 public void fromBinaryFile(File newFile) {

		 try {
			 FileInputStream fIS = new FileInputStream(newFile);
			 ObjectInputStream fIN = new ObjectInputStream(fIS);

			 clear();

			 int n = fIN.readInt();

			 for(int i = 0; i < n; ++i) {

				 MyShape shape = (MyShape) fIN.readObject();

				 shapes.add(shape);
			 }

			 paint();
			 fIN.close();
			 fIS.close();

		 }
		 catch (ClassNotFoundException e) {
			 e.printStackTrace();
		 }
		 catch (FileNotFoundException e2) {

			 e2.printStackTrace();
		 } catch (IOException e) {

			 e.printStackTrace();
		 }
	 }

	 /**
	  * Saves the drawn shapes to a text file
	  *
	  * @param newFile The File object to save to
	  */
	 public void toTextFile(File newFile) {
		 try {
			 PrintWriter fileOut = new PrintWriter(newFile);

			 fileOut.println(shapes.size());

			 for ( MyShape shape : shapes) {

				 fileOut.println(shape.toString());
			 }

			 fileOut.close();
		 }
		 catch(FileNotFoundException e) {

			 System.out.println(newFile + "could not create or open file");
			 e.printStackTrace();
		 }
	 }

	 /**
	  * Loads shapes from a text file.
	  *
	  * @param newFile The File object to load from.
	  */
	 public void fromTextFile(File newFile) {

		 try {

			 Scanner fileIn = new Scanner(newFile);

			 clear();

			 int nShape = fileIn.nextInt();

			 for (int i = 0; i < nShape; ++i) {

				 String type = fileIn.next();

				 if (type.equalsIgnoreCase("ShapeGroup")) {

					 shapes.add(loadGroupText(fileIn));
				 } 
				 else {

					 shapes.add(loadSingletonText(fileIn, type));
				 }

				 //				 shape.setColor(col);
				 //				 shape.setFilled(filled);
				 //				 shapes.add(shape);
			 }

			 fileIn.close();

			 paint();
		 }
		 catch(FileNotFoundException e) {

			 System.out.println(newFile + "could not be opned for reading");
			 e.printStackTrace();
		 }
	 }

	 /**
	  * Loads a single shape from a plain text file
	  * 
	  * @param fIn        The Scanner object used for reading
	  * @param shapeType  The type of the shape to load 
	  * @return           The loaded MyShape
	  */
	 private MyShape loadSingletonText(Scanner fIn, String shapeType) {

		 double ulx = fIn.nextDouble();
		 double uly = fIn.nextDouble();
		 double width = fIn.nextDouble();
		 double height = fIn.nextDouble();

		 double r = fIn.nextDouble();
		 double g = fIn.nextDouble();
		 double b = fIn.nextDouble();
		 boolean filled = fIn.nextBoolean();

		 Color col = Color.color(r, g, b);
		 MyShape shape = null;

		 if (shapeType.equalsIgnoreCase("Line")) {

			 shape = new Line(ulx, uly, width, height);
		 } 
		 else if (shapeType.equalsIgnoreCase("Oval")) {

			 shape = new Oval(ulx, uly, width, height);
		 } 
		 else if (shapeType.equalsIgnoreCase("Rect")) {

			 shape = new Rect(ulx, uly, width, height);
		 }

		 if (shape != null) {

			 shape.setColor(col);
			 shape.setFilled(filled);
		 }

		 return shape;
	 }

	 /**
	  * /**
	  * Loads a shape group from a plain text file
	  * 
	  * @param fIn The Scanner object used for reading
	  * @return    The loaded ShapeGroup object
	  */
	 private MyShape loadGroupText(Scanner fIn) {

		 ShapeGroup group = new ShapeGroup();

		 int nShapes = fIn.nextInt();

		 for(int i = 0; i < nShapes; ++i) {

			 String shapeType = fIn.next();

			 if (shapeType.equalsIgnoreCase("ShapeGroup")) {

				 group.addMember(loadGroupText(fIn)); 
			 } 
			 else {

				 group.addMember(loadSingletonText(fIn, shapeType));
			 }
		 }

		 return group;
	 }

	 /**
	  * Finds the closest shape to a given point (x, y) on the canvas
	  * 
	  * @param x The x of the point
	  * @param y The y of the point
	  * @return The closest MyShape shape
	  */
	 public MyShape closetShape(double x, double y) {

		 MyShape closet = null;
		 double minDistance = Double.MAX_VALUE;

		 for (MyShape shape : shapes) {

			 Point2D center = shape.getCenter();
			 double dx = center.getX() - x;
			 double dy = center.getY() - y;
			 double distance = dx * dx + dy * dy;

			 if ( distance < minDistance) {

				 minDistance = distance;
				 closet = shape;
			 }
		 }

		 return closet; 
	 }

	 /**
	  * Deletes a shape from the canvas
	  * 
	  * @param s The MyShape shape to delete
	  */
	 public void deleteShape(MyShape s) {

		 shapes.remove(s);
	 }

	 /**
	     * Adds a new edit to the canvas
	     *
	     * @param edit The edit to add
	     */
	 public void addEdit(Edit edit) {

		 stackUndo.push(edit);

	 }

	 /**
	     * Undo the latest edit added to the undo stack
	     */

	 public void undo() {

		 if (!stackUndo.isEmpty()) {

			 Edit edit = stackUndo.pop();
			 
			 edit.undo();
			 
			 stackRedo.push(edit);
		 }
	 }

	 /**
	     * Redo the latest undone edit
	     */
	 public void redo() {

		 if (!stackRedo.empty()) {

			 Edit edit = stackRedo.pop();
			 
			 edit.redo();
			 
			 stackUndo.push(edit);
		 }


	 }
}
