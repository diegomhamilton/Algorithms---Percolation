	/*
	
	*/

	import edu.princeton.cs.algs4.StdRandom;
	import edu.princeton.cs.algs4.StdStats;
	import edu.princeton.cs.algs4.WeightedQuickUnionUF;
	
	public class Percolation {
		private WeightedQuickUnionUF matrix, matrixAux;
		private int top, bot, size, openSites = 0;
		private boolean[] open;
		
		// create n-by-n grid, with all sites blocked
		public Percolation(int n) {
			matrix = new WeightedQuickUnionUF(n*n+2);
			matrixAux = new WeightedQuickUnionUF(n*n+1); //matrix but without bottom to be used on isFull
			size = n;		   
			open = new boolean[n*n];
			top = n * n;				
			bot = n * n + 1;
		}
		// open site (row, col) if it is not open already
		public void open(int row, int col) {
			if(!isOpen(row, col)) {
				open[(row-1)*size + col - 1] = true;
				
				if(row == 1) {
					this.matrix.union((row-1)*size + col - 1, top);
					this.matrixAux.union((row-1)*size + col - 1, top);
				}
				else if(row == size) {
					this.matrix.union((row-1)*size + col - 1, bot);
				}
				if(row > 1 && isOpen(row-1, col)) {
					this.matrix.union((row-1)*size + col - 1, (row-2)*size + col - 1);
					this.matrixAux.union((row-1)*size + col - 1, (row-2)*size + col - 1);
				}
				if(row < size && isOpen(row+1, col)) {
					this.matrix.union((row-1)*size + col - 1, row*size + col - 1);
					this.matrixAux.union((row-1)*size + col - 1, row*size + col - 1);
				}
				if(col > 1 && isOpen(row, col-1)) {
					this.matrix.union((row-1)*size + col - 1, (row-1)*size + col - 2);
					this.matrixAux.union((row-1)*size + col - 1, (row-1)*size + col - 2);
				}
				if(col < size && isOpen(row, col+1)) {
					this.matrix.union((row-1)*size + col - 1, (row-1)*size + col);
					this.matrixAux.union((row-1)*size + col - 1, (row-1)*size + col);
				}
				
				openSites++;
			}
			
			else { }
		}
		// return if site (row, col) is open
		public boolean isOpen(int row, int col) {
			return open[(row-1)*size + col - 1];
		}
		// returns if site (row, col) is connected to top
		public boolean isFull(int row, int col) {
			return matrixAux.connected((row-1)*size + col - 1, top);
		}
		// return the number of open sites
		public int numberOfOpenSites() {			
			return openSites;
		}
		public boolean percolates(){				// return if the system percolates (top is connected to bottom)
			return matrix.connected(top, bot);
		}
	}