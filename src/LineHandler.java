import javafx.scene.input.MouseEvent;

public class LineHandler extends DrawHandler {

	 /**
     * Constructs a LineHandler with the specified ShapeCanvas
     *
     * @param sc The ShapeCanvas to handle line drawing events
     */
	public LineHandler( ShapeCanvas sc) {

		super( sc);
	}

	/**
     * Handles the mousePressed event to initialize a line shape
     * when the user starts drawing
     *
     * @param e The MouseEvent representing the mousePressed event
     */
	@Override
	public void mousePressed(MouseEvent e) {

		shape = new Line();
		super.mousePressed(e);
	}
}
