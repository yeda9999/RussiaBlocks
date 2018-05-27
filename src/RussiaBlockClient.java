import edu.princeton.cs.algs4.StdDraw;

import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Random;





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
//		Matrix m = null;
		int[] blocktypes = {Matrix.BlockType.L,Matrix.BlockType.I,Matrix.BlockType.Z};
		Random r = new Random();
		while(true) {
//			drop = dd.getDropdown();
//			background = dd.getBackground();
			if(StdDraw.isKeyPressed(KeyEvent.VK_A)) {
				dd.getDropdown().moveLeftIn(dd.getBackground());

				if(!dd.getDropdown().checkIfCollisionIn(dd.getBackground())) {
					dd.setM(dd.getBackground().unionInnerMatrix(dd.getDropdown()));
					dd.getM().draw();
				}
			} else if(StdDraw.isKeyPressed(KeyEvent.VK_S)) {
				dd.getDropdown().moveDownIn(dd.getBackground());
				if (!dd.getDropdown().checkIfCollisionIn(dd.getBackground())) {
					dd.setM(dd.getBackground().unionInnerMatrix(dd.getDropdown()));
					dd.getM().draw();
				} else {
					dd.setBackground(dd.getM());
					dd.getBackground().draw();

					List<Integer> checkIfHasHLineFull = dd.getBackground().checkIfHasHLineFull();

					if (checkIfHasHLineFull.size() > 0) {

						//TODO 只要没消掉最下面一行，总是会有问题
						for (int i = 0; i < checkIfHasHLineFull.size(); i++) {

							for (int j = 0; j < RussiaBlockClient.COLS; j++) {
								dd.getBackground().getMatrix()[checkIfHasHLineFull.get(i)][j] = 0;
							}
							dd.getBackground().draw();
							StdDraw.pause(200);
							dd.setDropdown(dd.getBackground().split(0, checkIfHasHLineFull.get(i), 0, RussiaBlockClient.COLS));
							dd.setBackground(dd.getBackground().exclude(dd.getDropdown()));
							dd.getDropdown().moveDownIn(dd.getBackground());
							dd.setM(dd.getBackground().unionInnerMatrix(dd.getDropdown()));

							dd.setBackground(dd.getM());
							dd.getBackground().draw();
						}
						//dropdown =
						//background =
					}
					dd.setM(dd.getBackground());
					dd.setDropdown(Matrix.genBlock(blocktypes[r.nextInt(blocktypes.length)], RussiaBlockClient.DROPX, RussiaBlockClient.DROPY, RussiaBlockClient.DROPROWS, RussiaBlockClient.DROPCOLS));

				}
			}else if(StdDraw.isKeyPressed(KeyEvent.VK_D)) {
				dd.getDropdown().moveRightIn(dd.getBackground());
//				drop.draw();
				if(!dd.getDropdown().checkIfCollisionIn(dd.getBackground())) {
					dd.setM(dd.getBackground().unionInnerMatrix(dd.getDropdown()));
					dd.getM().draw();
				}
			} else if(StdDraw.isKeyPressed(KeyEvent.VK_J)) {
				dd.getDropdown().rotate90NSZ();
//				drop.draw();
				dd.setM(dd.getBackground().unionInnerMatrix(dd.getDropdown()));
				dd.getM().draw();
			}
			StdDraw.pause(100);
		}
	}
}
