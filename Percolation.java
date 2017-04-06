	/*
	
	*/
	
	import edu.princeton.cs.algs4.WeightedQuickUnionUF;
	
	public class Percolation {
		private WeightedQuickUnionUF matrix, matrixAux;
		private int top, bot, size, openSites = 0;
		private boolean[] state;
		
		// create n-by-n grid, with all sites blocked
		public Percolation(int n) {
			if(n <= 0){
				throw new IllegalArgumentException("Insira um valor de n maior que 0");
			}
			
			this.matrix = new WeightedQuickUnionUF(n*n+2);
			this.matrixAux = new WeightedQuickUnionUF(n*n+1); //matrix but without bottom to be used on isFull
			this.size = n;		   
			this.state = new boolean[n*n];
			this.top = n * n;				
			this.bot = n * n + 1;
		}
		//converts (row, col) coordinate to linear coordinate
		private int toLinear(int row, int col) {
			return (row-1)*this.size + col - 1;
		}
		//check if the index is on the bounds
		private void validates(int row, int col) {
			if(row < 1 || row > this.size || col < 1 || col > this.size) {
				throw new IndexOutOfBoundsException("Please enter a number in [1, " + this.size + "]");
			}
		}
		// open site (row, col) if it is not open already
		public void open(int row, int col) {
			validates(row, col);
			
			if(!isOpen(row, col)) {
				this.state[toLinear(row, col)] = true;
				this.openSites++;
			
				if(row == 1) {
					this.matrix.union(top, toLinear(row, col));
					this.matrixAux.union(top, toLinear(row, col));
				}
				if(row == size) {
					this.matrix.union(bot,toLinear(row, col));
				}
				if(row > 1 && isOpen(row-1, col)) {
					this.matrix.union(toLinear(row, col), toLinear(row-1, col));
					this.matrixAux.union(toLinear(row, col), toLinear(row-1, col));
				}
				if(row < size && isOpen(row+1, col)) {
					this.matrix.union(toLinear(row, col), toLinear(row+1, col));
					this.matrixAux.union(toLinear(row, col), toLinear(row+1, col));
				}
				if(col > 1 && isOpen(row, col-1)) {
					this.matrix.union(toLinear(row, col), toLinear(row, col-1));
					this.matrixAux.union(toLinear(row, col), toLinear(row, col-1));
				}
				if(col < size && isOpen(row, col+1)) {
					this.matrix.union(toLinear(row, col), toLinear(row, col+1));
					this.matrixAux.union(toLinear(row, col), toLinear(row, col+1));
				}
			}
		}
		// return if site (row, col) is open
		public boolean isOpen(int row, int col) {
			validates(row, col);
			
			return state[toLinear(row, col)];
		}
		// returns if site (row, col) is connected to top
		public boolean isFull(int row, int col) {
			validates(row, col);
			
			return matrixAux.connected(toLinear(row, col), top);
		}
		// return the number of open sites
		public int numberOfOpenSites() {			
			return openSites;
		}
		// return if the system percolates (top is connected to bottom)
		public boolean percolates(){
			return matrix.connected(top, bot);
		}
	}