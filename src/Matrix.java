import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import edu.princeton.cs.algs4.StdDraw;

public class Matrix {
	
	public static class BlockType {
		static final int L = 7;
		static final int Z = 2;
		static final int I = 1;
		static final int U = 0;
		static final int FL = 4;
		static final int FZ = 8;
	}
	
	int blockType;
	
	public int getBlockType() {
		return blockType;
	}

	public void setBlockType(int blockType) {
		this.blockType = blockType;
	}

	private int[][] matrix;
	
	private int x;
	public synchronized  int getX() {
		return x;
	}

	public synchronized  void setX(int x) {
		this.x = x;
	}

	public synchronized int getY() {
		return y;
	}

	public synchronized void setY(int y) {
		this.y = y;
	}

	private int y;
	
	private int rows;
	public int[][] getMatrix() {
		return matrix;
	}

	public void setMatrix(int[][] matrix) {
		this.matrix = matrix;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	private int cols;

	public Matrix(int x, int y, int rows, int cols) {
		synchronized (this) {
			this.y = y;
		}
		this.x = x;
		this.rows = rows;
		this.cols = cols;
		matrix = new int[rows][cols];
	}

	public String toString() {
		String s = "";
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				s += getMatrix()[i][j] + "\t";
			}
			s += "\n";
		}
		System.out.println("x="+x+" y="+getY());
		return s;
	}

	public synchronized void rotate90NSZ() {
		Matrix mp= new Matrix(getX(),getY(),getRows(),getCols());
		for (int i = 0; i < mp.getRows(); i++) {
			for (int j = 0; j < mp.getCols(); j++) {
				mp.getMatrix()[i][j] = getMatrix()[getMatrix().length - j - 1][i];
			}
		}
		setMatrix(mp.getMatrix());
	}

	public static void main(String[] args) {
		Matrix m = new Matrix(0, 0, 4, 4);
		m.initRandom();
		System.out.println(m);
		System.out.println("-------------------------------");
		m.rotate90NSZ();
		System.out.println(m);
		System.out.println("-------------------------------");
//		System.out.println(m.unionInnerMatrix(m.rotate90NSZ()));
	}

	private void initRandom() {

		Random r = new Random();

		for (int i = 0; i < getMatrix().length; i++) {
			for (int j = 0; j < getMatrix()[0].length; j++) {
				if (r.nextDouble() > 0.5) {
					getMatrix()[i][j] = 1;
				}
			}
		}
	}

	public Matrix unionInnerMatrix(Matrix m) {
		Matrix mt = new Matrix(getX(), getY(), getRows(), getCols());
		if(getRows() < m.getY() || getCols() < m.getX()) {
			//TODO out of range.
		}
		for(int i=0;i<getRows();i++) {
			for(int j=0;j<getCols();j++) {
				if(i>=m.getY() && i<m.getY()+m.getRows()
						&& j>=m.getX() && j<m.getX()+m.getCols()) {
					mt.getMatrix()[i][j] = getMatrix()[i][j] + m.getMatrix()[i-m.getY()][j-m.getX()];
				} else {
					mt.getMatrix()[i][j] = getMatrix()[i][j];
				}
				if(mt.getMatrix()[i][j]>1) {
					System.out.println("collion");
				}
				
			}
		}
		return mt;
	}
	
	public Matrix unionOuterMatrix(Matrix m) throws GameOverException {
		Matrix mt = new Matrix(getX(), getY(), getRows(), getCols());
		if(getY()<0) {
			//TODO out of range.

			throw new GameOverException();
		}
		for(int i=0;i<getRows();i++) {
			for(int j=0;j<getCols();j++) {
				if(i+getY()<m.getRows() && j+getX()<m.getCols()) {
					mt.getMatrix()[i][j] = getMatrix()[i][j] + m.getMatrix()[i+getY()][j+getX()];
				}
			}
		}
		return mt;
	}

	public static Matrix genBlock(int blockType, int x, int y, int rows, int cols) {
		Matrix mt = null;
		switch(blockType) {
		case BlockType.I : 
			mt = genBlockI(x, y, rows, cols);
			mt.setBlockType(BlockType.I);
			break;
		case BlockType.Z : 
			mt = genBlockZ(x, y, rows, cols);
			mt.setBlockType(BlockType.Z);
			break;
		case BlockType.L : 
			mt = genBlockL(x, y, rows, cols);
			mt.setBlockType(BlockType.L);
			break;
		case BlockType.U : 
			mt = genBlockU(x, y, rows, cols);
			mt.setBlockType(BlockType.U);
			break;
		case BlockType.FZ :
			mt = genBlockFZ(x, y, rows, cols);
			mt.setBlockType(BlockType.FZ);
			break;
		case BlockType.FL :
			mt = genBlockFL(x, y, rows, cols);
			mt.setBlockType(BlockType.FL);
			break;
		}
		
		return mt;
		
	}

	private static Matrix genBlockU(int x, int y, int rows, int cols) {
		Matrix m = new Matrix(x,y,rows,cols);
		for(int i=0;i<cols;i++) {
			m.getMatrix()[rows-1][i] = 1;
		}
		return m;
	}

	private static Matrix genBlockI(int x, int y, int rows, int cols) {
		Matrix m = new Matrix(x,y,rows,cols);
		for(int i=0;i<rows;i++) {
			m.getMatrix()[i][0] = 1;
		}
		return m;
	}
	
	private static Matrix genBlockZ(int x, int y, int rows, int cols) {
		Matrix m = new Matrix(x,y,rows,cols);
		m.getMatrix()[0][0] = 1;
		m.getMatrix()[0][1] = 1;
		m.getMatrix()[1][1] = 1;
		m.getMatrix()[1][2] = 1;
		return m;
	}

	private static Matrix genBlockFZ(int x, int y, int rows, int cols) {
		Matrix m = new Matrix(x,y,rows,cols);
		m.getMatrix()[0][1] = 1;
		m.getMatrix()[0][2] = 1;
		m.getMatrix()[1][1] = 1;
		m.getMatrix()[1][0] = 1;
		return m;
	}

	
	private static Matrix genBlockL(int x, int y, int rows, int cols) {
		Matrix m = new Matrix(x,y,rows,cols);
		m.getMatrix()[0][0] = 1;
		m.getMatrix()[1][0] = 1;
		m.getMatrix()[2][0] = 1;
		m.getMatrix()[2][1] = 1;
		return m;
	}

	private static Matrix genBlockFL(int x, int y, int rows, int cols) {
		Matrix m = new Matrix(x,y,rows,cols);
		m.getMatrix()[0][0] = 1;
		m.getMatrix()[0][1] = 1;
		m.getMatrix()[1][1] = 1;
		m.getMatrix()[2][1] = 1;
		return m;
	}
	
	public void draw() {
		System.out.println(this);
		StdDraw.clear();
		for (int i = 0; i < getRows(); i++) {
			for (int j = 0; j < getCols(); j++) {
				if(getMatrix()[i][j]==1) {
					if(getRows()-1 == i) {
						StdDraw.picture(getX()+j+0.5, RussiaBlockClient.ROWS-getY()-i-0.5, "russiaground.png", 1, 1);
					} else {
						StdDraw.picture(getX()+j+0.5, RussiaBlockClient.ROWS-getY()-i-0.5, "russiablock.png", 1, 1);
					}
				}
			}
		}
		StdDraw.show(5);
	}

	public synchronized void moveLeftIn(Matrix m) throws GameOverException {
		// TODO Auto-generated method stub
//		StdDraw.pause(50);
		int s = getAcutalColStarts();
		if(s<getX()) {
			if (!checkIfCollisionIn(m)) {
				setX(getX() - 1);
			} else {
				setX(getX() + 1);
			}
		} else {
			//重新定位drop矩阵
			setX(getX()+s);
			if(s==0 && getX()==0) {
				return;
			}
			for(int i=0;i<getRows();i++) {
				for(int j=0;j<getCols();j++) {
					if(j+s<getCols()) {
						getMatrix()[i][j] = getMatrix()[i][j+s];
					} else {
						getMatrix()[i][j] = 0;
					}
				}
			}
		}
	}

	public synchronized void moveDownIn(Matrix m) throws GameOverException {
		// TODO Auto-generated method stub
//		StdDraw.pause(100);
		if(!checkIfCollisionIn(m)) {
			setY(getY()+1);
		} else {
			setY(getY()-1);
		}
	}

	public int getAcutalColEnds() {
		for(int i=getCols()-1;i>=0;i--) {
			for(int j=0;j<getRows();j++) {
				if(getMatrix()[j][i]>0) {
					return i;
				}
			}
		}
		return 0;
	}

	public synchronized int getAcutalColStarts() {
		for(int i=0;i<getCols();i++) {
			for(int j=0;j<getRows();j++) {
				if(getMatrix()[j][i]>0) {
					return i;
				}
			}
		}
		return 0;
	}

	public synchronized void moveRightIn(Matrix m) throws GameOverException {
		// TODO Auto-generated method stub
//		StdDraw.pause(50);
		if(getX()+ getAcutalColEnds()<m.getCols()-1) {
			if (!checkIfCollisionIn(m)) {
				setX(getX() + 1);
			} else {
				setX(getX() - 1);
			}
		}
	}
	
	public boolean checkIfCollisionIn(Matrix m) throws GameOverException {
		Matrix unionOuterMatrix = unionOuterMatrix(m);
		for(int i=0;i<unionOuterMatrix.getRows();i++) {
			for(int j=0;j<unionOuterMatrix.getCols();j++) {
				if(unionOuterMatrix.getMatrix()[i][j]>1) {
					return true;
				}
			}
		}
		return false;
	}
	
	public List<Integer> checkIfHasHLineFull() {
		
		List<Integer> list = new ArrayList<Integer>();
		boolean flag = true;
		for(int i=0;i<getRows();i++) {
			for(int j=0;j<getCols();j++) {
				if(i != getRows() - 1 ) {
					if(getMatrix()[i][j]!=1) {
						flag = false;
						break;
					} else {
						flag = true;
					}
				}
			}
			if(flag && i != getRows() - 1 ) {
				list.add(i);
			}
		}
		
		return list;
	}

	public Matrix split(int startrow, int endrow, int startcol,int endcol) {
		Matrix ma = genBlock(BlockType.U, startrow, startcol, endrow-startrow, endcol-startcol);
		for (int i = startrow; i < endrow  ; i++) {
			for (int j = startcol; j < endcol; j++) {
				ma.getMatrix()[i-startrow][j-startcol] = getMatrix()[i][j];
			}
		}
		return ma;
	}

	public Matrix exclude(Matrix smallOne) {
		Matrix ma = genBlock(BlockType.U, getX(), getY(), getRows(), getCols());
		for(int i=0;i<getRows();i++) {
			for(int j=0;j<getCols();j++) {
				if(i<smallOne.getY() || i>smallOne.getY()+smallOne.getRows()
						|| j<smallOne.getX() || j>smallOne.getX()+smallOne.getCols()) {
					ma.getMatrix()[i][j] = getMatrix()[i][j];
				}

			}
		}
		return ma;
	}
}
