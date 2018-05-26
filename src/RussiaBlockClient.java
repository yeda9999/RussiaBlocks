import java.awt.event.KeyEvent;
import java.nio.charset.Charset;

import edu.princeton.cs.algs4.StdDraw;





public class RussiaBlockClient {
	
	public static final int ROWS = 20;
	public static final int COLS = 10;
	public static final int DROPX = 5;
	public static final int DROPY = 0; 
	public static final int DROPROWS = 3;
	public static final int DROPCOLS = 3;

	public static void main(String[] args) {
		
		StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(0, COLS);
        StdDraw.setYscale(0, ROWS);   
        
		Matrix drop = Matrix.genBlock(Matrix.BlockType.I,DROPX, DROPY, DROPROWS, DROPCOLS);
		Matrix background = Matrix.genBlock(Matrix.BlockType.U,0, 0, ROWS, COLS);
		DropDown dd = new DropDown(drop, background);
		new Thread(dd).start();
		Matrix m = null;
		while(true) {
			drop = dd.getDropdown();
			background = dd.getBackground();
			if(StdDraw.isKeyPressed(KeyEvent.VK_A)) {
				StdDraw.clear();
				drop.moveLeftIn(background);
				
				if(!drop.checkIfCollisionIn(background)) {
					m = background.unionInnerMatrix(drop);
					m.draw();
				}
			} else if(StdDraw.isKeyPressed(KeyEvent.VK_S)) {
				StdDraw.clear();
				drop.moveDownIn(background);
				if(!drop.checkIfCollisionIn(background)) {
					m = background.unionInnerMatrix(drop);
					m.draw();
				}
			} else if(StdDraw.isKeyPressed(KeyEvent.VK_D)) {
				StdDraw.clear();
				drop.moveRightIn(background);
//				drop.draw();
				if(!drop.checkIfCollisionIn(background)) {
					m = background.unionInnerMatrix(drop);
					m.draw();
				}
			} else if(StdDraw.isKeyPressed(KeyEvent.VK_J)) {
				StdDraw.clear();
				drop.rotate90NSZ();
//				drop.draw();
				m = background.unionInnerMatrix(drop);
				m.draw();
			}
			StdDraw.pause(100);
		}
	}
}
