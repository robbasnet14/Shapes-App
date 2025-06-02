
public class DeleteEdit extends Edit {

	/**
	 * Constructor for CopyEdit
     * 
     * @param c The ShapeCanvas 
     * @param s The MyShape object 
	 */
	public DeleteEdit(ShapeCanvas c, MyShape s) {

		super(c, s);
	}
	
	/**
	 * Undo the copy edit
	 */
	@Override
	public void undo() {
		
		canvas.addShape(shape);
		
		canvas.paint();
		
	}
	
	/**
	 *  Redo the copy edit
	 */
	@Override
	public void redo() {
		
		canvas.deleteShape(shape);
		canvas.paint();
	}
}
