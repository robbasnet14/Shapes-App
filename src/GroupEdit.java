
public class GroupEdit extends Edit{

	private ShapeGroup shapeG;
	
	/**
	 * Constructor for GroupEdit
     * 
     * @param c The ShapeCanvas 
     * @param s The MyShape object
   	 */
	public GroupEdit( ShapeCanvas c, MyShape s) {

		super(c, s);
		
	    shapeG = (ShapeGroup) s;
	}

	/**
	 * Undo the group edit
	 */
	@Override
	public void undo() {
		
		for (MyShape shape : shapeG.getMembers()) {
			
			canvas.addShape(shape);
		}
		
		canvas.deleteShape(shapeG);
		canvas.paint();
	}

	/**
	 * Redo the group edit
	 */
	@Override
	public void redo() {

		for (MyShape shape : shapeG.getMembers()) {
            canvas.deleteShape(shape);
        }
		
		canvas.addShape(shapeG);
		canvas.paint();
	}
}
