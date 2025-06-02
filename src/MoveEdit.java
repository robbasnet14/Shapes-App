
public class MoveEdit extends Edit {

	private double dx, dy;
	
	/**
	 * Constructor for CopyEdit
     * 
     * @param c  The ShapeCanvas 
     * @param s  The MyShape object 
     * @param dx  The change in x-cordinate from moving
     * @param dy  The change in y-cordinate from moving
	 */
	public MoveEdit(ShapeCanvas c, MyShape s, double dx, double dy) {

		super(c, s);
		this.dx = dx;
		this.dy = dy;
	}
	
	/**
	 * Undo the copy edit
	 */
	@Override
	public void undo() {
		
		shape.move( -dx, - dy);
	}
	
	/**
	 *  Redo the copy edit
	 */
	@Override
	public void redo() {
		
		shape.move(dx, dy);
		canvas.paint();
	}
	
	
}
