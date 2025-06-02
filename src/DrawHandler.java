import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DrawHandler implements EventHandler<MouseEvent> {

	protected MyShape 	  shape;
	protected ShapeCanvas canvas;

	/**
     * Constructor for creating a DrawHandler with a specified ShapeCanvas
     *
     * @param sc The ShapeCanvas object to associate with this handler
     */
	public DrawHandler(ShapeCanvas sc) {

		canvas = sc;
	}

	/**
     * Handles the mouse pressed event
     *
     * @param e The MouseEvent representing the mouse press event
     */
	protected void mousePressed(MouseEvent e) {

		System.out.print(" Event type : Mouse Pressed, Event Name:" + e.getEventType().getName());

		if (shape != null) {

			//shape = new MyShape();
			shape.setP1(e.getX(), e.getY());
			canvas.setcurrShape(shape);	
		}
	}

	 /**
     * Handles the mouse dragged event
     *
     * @param e The MouseEvent representing the mouse drag event
     */
	protected void mouseDragged(MouseEvent e) {

		System.out.print(" Event type : Mouse Dragged, Event Name:" + e.getEventType().getName());

		if (shape != null ) {

			shape.setP2(e.getX(), e.getY());
			canvas.paint();
		}
	}

	/**
     * Handles the mouse released event
     *
     * @param e The MouseEvent representing the mouse release event
     */
	protected void mouseReleased(MouseEvent e) {

		System.out.print(" Event type : Mouse Released, Event Name:" + e.getEventType().getName());

		if (shape != null) {

			canvas.addShape(shape);
			canvas.setcurrShape(null);
			DrawEdit drawEdit = new DrawEdit(canvas, shape);
			canvas.addEdit(drawEdit);

			shape = null;				//shape should be null because we have completed drawing the shape and want new shape in next drawing
		}
	}

	/**
     * Handles the mouse event by delegating to specific methods based on the event type
     *
     * @param event The MouseEvent to handle
     */
	@Override
	public void handle(MouseEvent event) {
		
		switch(event.getEventType().getName()) {

		case"MOUSE_PRESSED":
			mousePressed(event);
			break;

		case"MOUSE_DRAGGED":
			mouseDragged(event);
			break;

		case"MOUSE_RELEASED":
			mouseReleased(event);
			break;
		}

	}
}
