import java.awt.*;
import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.StdDraw;


public class DropDown implements Runnable {
	
	private Matrix dropdown;

	public synchronized Matrix getM() {
		return m;
	}

	public synchronized void setM(Matrix m) {
		this.m = m;
	}

	private Matrix m;
	
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
		try {
		while(true) {

				getDropdown().moveDownIn(getBackground());
				if (!getDropdown().checkIfCollisionIn(getBackground())) {
					m = getBackground().unionInnerMatrix(getDropdown());
					m.draw();
				} else {
					setBackground(m);
					getBackground().draw();

					List<Integer> checkIfHasHLineFull = getBackground().checkIfHasHLineFull();

					if (checkIfHasHLineFull.size() > 0) {
						for (int i = 0; i < checkIfHasHLineFull.size(); i++) {

							for (int j = 0; j < RussiaBlockClient.COLS; j++) {
								getBackground().getMatrix()[checkIfHasHLineFull.get(i)][j] = 0;
							}
							getBackground().draw();
							StdDraw.pause(200);
							setDropdown(getBackground().split(0, checkIfHasHLineFull.get(i), 0, RussiaBlockClient.COLS));
							setBackground(getBackground().exclude(getDropdown()));
							getDropdown().moveDownIn(getBackground());
							m = getBackground().unionInnerMatrix(getDropdown());

							setBackground(m);
							getBackground().draw();
						}
						//dropdown =
						//background =
					}
					m = getBackground();
					setDropdown(Matrix.genBlock(blocktypes[r.nextInt(blocktypes.length)], RussiaBlockClient.DROPX, RussiaBlockClient.DROPY, RussiaBlockClient.DROPROWS, RussiaBlockClient.DROPCOLS));
				}

				try {
					Thread.sleep(RussiaBlockClient.SPEED);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}catch (GameOverException e) {
			StdDraw.clear();
			StdDraw.setPenColor(Color.RED);
			StdDraw.setPenRadius(2);
			StdDraw.text(RussiaBlockClient.COLS/2,RussiaBlockClient.ROWS/2,"GAME OVER");
			StdDraw.show();
		}
	}

}
