import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.StdDraw;


public class DropDown implements Runnable {
	
	private Matrix dropdown;
	
	public synchronized Matrix getDropdown() {
		return dropdown;
	}

	public synchronized void setDropdown(Matrix dropdown) {
		this.dropdown = dropdown;
	}

	public synchronized Matrix getBackground() {
		return background;
	}

	public synchronized void setBackground(Matrix background) {
		this.background = background;
	}

	private Matrix background;
	
	public DropDown(Matrix dropdown,Matrix background) {
		this.dropdown = dropdown;
		this.background = background;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		int[] blocktypes = {Matrix.BlockType.L,Matrix.BlockType.I,Matrix.BlockType.Z};
		Random r = new Random();
		Matrix m = null;
		while(true) {
			dropdown.moveDownIn(background);
			if(!dropdown.checkIfCollisionIn(background)) {
				m = background.unionInnerMatrix(dropdown);
				m.draw();
			} else {
				background = m;
				background.draw();
				
				List<Integer> checkIfHasHLineFull = background.checkIfHasHLineFull();
				
				if(checkIfHasHLineFull.size()>0) {
					for(int i=0;i<checkIfHasHLineFull.size();i++) {
						
						for(int j=0;j<RussiaBlockClient.COLS;j++) {
							background.getMatrix()[checkIfHasHLineFull.get(i)][j] = 0;
						}
						background.draw();
						StdDraw.pause(200);
						dropdown = background.split(0, checkIfHasHLineFull.get(i), 0, RussiaBlockClient.COLS);
						background = background.exclude(dropdown);
						dropdown.moveDownIn(background);
						m = background.unionInnerMatrix(dropdown);
						
						background = m;
						background.draw();
					}
					//dropdown = 
					//background =
				}
				dropdown = Matrix.genBlock(blocktypes[r.nextInt(blocktypes.length)], RussiaBlockClient.DROPX, RussiaBlockClient.DROPY, RussiaBlockClient.DROPROWS, RussiaBlockClient.DROPCOLS);
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
