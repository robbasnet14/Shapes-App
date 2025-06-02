import javafx.scene.input.MouseEvent;

public class OvalHandler extends DrawHandler{
	
	/**
     * Constructs an OvalHandler with the ShapeCanvas
     *
     * @param sc The ShapeCanvas to handle oval drawing events
     */
	public OvalHandler( ShapeCanvas sc) {
		
		super( sc);
	}
	
	/**
     * Handles the mousePressed event to initialize an oval shape
     * when the user starts drawing
     *
     * @param e The MouseEvent representing the mousePressed event
     */
	@Override
	public void mousePressed(MouseEvent e) {
		
		shape = new Oval();
		super.mousePressed(e);
	}
}
