import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class CopyHandler implements EventHandler<MouseEvent> {

	private ShapeCanvas canvas;
	private double x0, y0, x1, y1;
	private MyShape copiedS;
	
	/**
     * Constructs a CopyHandler with the ShapeCanvas
     * 
     * @param sc the ShapeCanvas to handle mouse events
     */
	public CopyHandler( ShapeCanvas sc) {
		
		canvas = sc;
	}
	
	/**
     * Handles mouse events
     * 
     * @param event the MouseEvent to handle
     */
	@Override
	public void handle( MouseEvent event ) {		
		
		if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
			
			 x0 = event.getX();
			 y0 = event.getY();
			
			MyShape closetShape = canvas.closetShape(x0, y0);
			
			if (closetShape != null) {
				
				copiedS = (MyShape) closetShape.clone();
				canvas.addShape(copiedS);
				
				x1 = x0;
				y1 = y0;
			}
		}
		
		else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
			
			double curX = event.getX();
			double curY = event.getY();
			
			double dx = curX - x1;
			double dy = curY - y1;
			
			copiedS.move(dx, dy);
			
			x1 = curX;
			y1 = curY;
			
			canvas.paint();			
		}
		
		else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {
			
			CopyEdit copyEdit = new CopyEdit(canvas, copiedS);
			canvas.addEdit(copyEdit);
		}
	}
}
