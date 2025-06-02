
public abstract class Edit {

	protected MyShape shape;
	protected ShapeCanvas canvas;
	
	/**
	 * Constructor for Edit
     * 
     * @param c The ShapeCanvas 
     * @param s The MyShape object 
	 */
	public Edit(ShapeCanvas c, MyShape s) {
		
		canvas = c;
		shape = s;
	}
	
	/**
	 * (abstract) Undo the edit operation
	 */
	public abstract void undo();

	/**
	 * (abstract) Redo the edit operation
	 */
    public abstract void redo();
	
}
