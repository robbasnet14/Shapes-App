import java.util.ArrayList;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class GroupHandler implements EventHandler<MouseEvent>{

	private ShapeCanvas canvas;
	private double startX, startY , endX, endY;
	private ShapeGroup group;

	/**
     * Constructs a GroupHandler with the ShapeCanvas
     * 
     * @param sc the ShapeCanvas to handle mouse events
     */
	public GroupHandler( ShapeCanvas sc) {

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

			group = new ShapeGroup();

			startX = event.getX();
			startY = event.getY();
			
			group.setP1(startX, startY);
			
			canvas.setcurrShape(group);

		}

		else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {

			 endX = event.getX();
			 endY = event.getY();
			
			group.setP2(endX, endY);
			
			canvas.paint();
		
		}

		else if (event.getEventType() == MouseEvent.MOUSE_RELEASED) {

			ArrayList<MyShape> groupShapes = new ArrayList<>();
			
			for(MyShape shape : canvas.getshapes()) {

				if (group.within(shape)) {

					group.addMember(shape);
					groupShapes.add(shape);
				}
			}

			if (!groupShapes.isEmpty()) {

				for(MyShape shape : groupShapes) {
					
					canvas.deleteShape(shape);
				}
			}
			canvas.addShape(group);
			canvas.setcurrShape(null);
			
			canvas.paint();
			
			GroupEdit groupEdit = new GroupEdit(canvas, group);
			canvas.addEdit(groupEdit);
		}
	}
	
}

