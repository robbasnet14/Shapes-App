import javafx.scene.input.MouseEvent;

public class RectHandler extends DrawHandler {

	/**
     * Constructs a RectHandler with the specified ShapeCanvas.
     *
     * @param sc The ShapeCanvas to handle rectangle drawing events.
     */
	public RectHandler( ShapeCanvas sc) {

		super( sc);
	}

	/**
     * Handles the mousePressed event to initialize a rectangle shape
     * when the user starts drawing.
     *
     * @param e The MouseEvent representing the mousePressed event.
     */
	@Override
	public void mousePressed(MouseEvent e) {

		shape = new Rect();
		super.mousePressed(e);
	}
}
