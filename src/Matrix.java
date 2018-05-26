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
	public  int getX() {
		return x;
	}

	public  void setX(int x) {
		this.x = x;
	}

	public synchronized int getY() {
		return y;
	}

	public synchronized void setY(int y) {
		this.y = y;
	}

	private volatile int y;
	
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
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				s += matrix[i][j] + "\t";
			}
			s += "\n";
		}
		System.out.println("x="+x+" y="+getY());
		return s;
	}

	public synchronized void rotate90NSZ() {
		Matrix mp= new Matrix(x,getY(),rows,cols);
		for (int i = 0; i < mp.getRows(); i++) {
			for (int j = 0; j < mp.getCols(); j++) {
				mp.getMatrix()[i][j] = matrix[matrix.length - j - 1][i];
			}
		}
		matrix = mp.getMatrix();
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

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				if (r.nextDouble() > 0.5) {
					matrix[i][j] = 1;
				}
			}
		}
	}
	
	/**
	 * �����ǵ�̫�����ˣ�ֻ�ڵ�ǰ����ʹ��
	 * this.x,this.y==0
	 * x>0
	 * y>0
	 * �����>1�ĵ� ˵����ײ
	 * @param m
	 * @return
	 */
	public Matrix unionInnerMatrix(Matrix m) {
		Matrix mt = new Matrix(getX(), y, rows, cols);
		if(rows < m.getY() || cols < m.getX()) {
			//TODO out of range.
		}
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				if(i>=m.getY() && i<m.getY()+m.getRows()
						&& j>=m.getX() && j<m.getX()+m.getCols()) {
					mt.getMatrix()[i][j] = matrix[i][j] + m.getMatrix()[i-m.getY()][j-m.getX()];
				} else {
					mt.getMatrix()[i][j] = matrix[i][j];
				}
				if(mt.getMatrix()[i][j]>1) {
					System.out.println("collion");
				}
				
			}
		}
		return mt;
	}
	
	public Matrix unionOuterMatrix(Matrix m) {
		Matrix mt = new Matrix(getX(), y, rows, cols);
		if(rows+y > m.getRows() || cols+getX() > m.getCols()) {
			//TODO out of range.
		}
		for(int i=0;i<rows;i++) {
			for(int j=0;j<cols;j++) {
				if(i+y<m.getRows() && j+getX()<m.getCols()) {
					mt.getMatrix()[i][j] = matrix[i][j] + m.getMatrix()[i+y][j+getX()];
				}
			}
		}
		return mt;
	}
	
	/**
	 * by default 3*3
	 * @param blockType
	 * @return
	 */
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
	
	private static Matrix genBlockL(int x, int y, int rows, int cols) {
		Matrix m = new Matrix(x,y,rows,cols);
		m.getMatrix()[0][0] = 1;
		m.getMatrix()[1][0] = 1;
		m.getMatrix()[2][0] = 1;
		m.getMatrix()[2][1] = 1;
		return m;
	}
	
	public void draw() {
		System.out.println(this);
		StdDraw.clear();
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if(matrix[i][j]==1) {
					if(blockType==BlockType.U) {
//						StdDraw.picture(x+j, RussiaBlockClient.ROWS-y-i, "ground.jpg", 1, 1);
						StdDraw.picture(x+j, RussiaBlockClient.ROWS-y-i, "block.jpg", 1, 1);
					} else {
						StdDraw.picture(x+j, RussiaBlockClient.ROWS-y-i, "block.jpg", 1, 1);
					}
					
				}
			}
		}
		StdDraw.show(10);
	}

	public synchronized void moveLeftIn(Matrix m) {
		// TODO Auto-generated method stub
		if(!checkIfCollisionIn(m)) {
			x -= 1;
		} else {
			x += 1;
		}
		
	}

	public synchronized void moveDownIn(Matrix m) {
		// TODO Auto-generated method stub
		synchronized (this) {
			if(!checkIfCollisionIn(m)) {
				y += 1;
			} else {
				y -= 1;
			}
		}
	}

	public synchronized void moveRightIn(Matrix m) {
		// TODO Auto-generated method stub
		if(!checkIfCollisionIn(m)) {
			x += 1;
		} else {
			x -= 1;
		}
	}
	
	public boolean checkIfCollisionIn(Matrix m) {
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
	
	/**
	 * ����
	 * @param startrow
	 * @param endrow
	 * @param startcol
	 * @param endcol
	 * @return
	 */
	public Matrix split(int startrow, int endrow, int startcol,int endcol) {
		Matrix ma = genBlock(BlockType.U, startrow, startcol, endrow-startrow, endcol-startcol);
		for (int i = startrow; i < endrow  ; i++) {
			for (int j = startcol; j < endcol; j++) {
				ma.getMatrix()[i-startrow][j-startcol] = matrix[i][j];
			}
		}
		return ma;
	}
	
	/**
	 * ����û�����õ�
	 * @param smallOne
	 * @return
	 */
	public Matrix exclude(Matrix smallOne) {
		Matrix ma = genBlock(BlockType.U, getX(), getY(), getRows(), getCols());
		for(int i=smallOne.getY();i<smallOne.getRows();i++) {
			for(int j=smallOne.getX();j<smallOne.getCols();j++) {
				getMatrix()[i][j] = 0;
			}
		}
		return ma;
	}
}
