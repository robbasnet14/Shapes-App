import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class DeleteHandler implements EventHandler<MouseEvent>{

	private ShapeCanvas canvas;
	
	/**
     * Constructs a DeleteHandler with the ShapeCanvas
     * 
     * @param sc the ShapeCanvas to handle mouse events
     */
	public DeleteHandler (ShapeCanvas sc) {
		
		canvas = sc;
	}
	
	/**
     * Handles mouse events
     * 
     * @param event the MouseEvent to handle
     */
	@Override
	public void handle(MouseEvent event) {
		
		if ( event.getEventType() == MouseEvent.MOUSE_CLICKED) {
			
			double x = event.getX();
			double y = event.getY();
			
			MyShape closetShape = canvas.closetShape(x, y);
					
			if (closetShape != null) {
				
				canvas.deleteShape(closetShape);
				canvas.paint();
				DeleteEdit deleteEdit = new DeleteEdit(canvas, closetShape);
				canvas.addEdit(deleteEdit);
			}
		}
	}
}
