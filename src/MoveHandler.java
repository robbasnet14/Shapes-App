import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public  class MoveHandler implements EventHandler<MouseEvent>{

	private ShapeCanvas canvas;
	private MyShape closetShape;
	private double x0, y0, x1, y1;

	/**
	 * Construct a MoveHandler with the ShapeCanvas
	 * 
	 * @param sc the ShapeCanvas to handle mouse events
	 */
	public MoveHandler (ShapeCanvas sc) {

		canvas = sc;
	}

	/**
	 * Handles mouse events
	 */
	@Override
	public void handle(MouseEvent event) {
		
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {

			closetShape = canvas.closetShape(event.getX(), event.getY());

			x0 = event.getX();
			y0 = event.getY();
			x1 = x0;
			y1 = y0;
		}
		else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

			double curX = event.getX();
			double curY = event.getY();

			double dx = curX - x1;
			double dy = curY - y1;

			closetShape.move(dx, dy);

			x1 = curX;
			y1 = curY;

			canvas.paint();
		}

		else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {

			MoveEdit moveEdit = new MoveEdit(canvas, closetShape, x1-x0, y1-y0);
			canvas.addEdit(moveEdit);
		}

	}
}
