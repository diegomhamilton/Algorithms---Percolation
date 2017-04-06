	/*
	 */

	import edu.princeton.cs.algs4.StdRandom;
	import edu.princeton.cs.algs4.StdStats;

	public class PercolationStats {
		private double[] sample;
		
		// perform trials independent experiments on an n-by-n grid
		public PercolationStats(int n, int trials) {
	        if (n <= 0 || trials <= 0)
	        	throw new IllegalArgumentException("Please enter values bigger than 0");
			
	        this.sample = new double[n];
			
			for(int i = 0; i < trials; ++i) {
				Percolation current = new Percolation(n);
				
				while(!current.percolates()) {
					current.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
				}
				this.sample[i] = (double) current.numberOfOpenSites()/(n * n);
			}
		}
		
		// sample mean of percolation threshold
		public double mean() {
			return StdStats.mean(this.sample);
		}
		
		// sample standard deviation of percolation threshold
		public double stddev() {
			return StdStats.mean(this.sample);
		}
		
		// low  endpoint of 95% confidence interval
		public double confidenceLo() {
			 return (this.mean() - (1.96 * this.stddev() / Math.sqrt(this.sample.length)));
		}
		
			// high endpoint of 95% confidence interval
		public double confidenceHi() {
			 return (this.mean() + (1.96 * this.stddev() / Math.sqrt(this.sample.length)));
		}
		
		// test client
		public static void main(String[] args) {
			if(args.length == 2) {
				PercolationStats simulation = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
				
				System.out.printf("mean: %f\n", simulation.mean());
				System.out.printf("standard deviation: %f\n", simulation.stddev());
				System.out.printf("95%% confidence interval: %f, %f\n", simulation.confidenceLo(), simulation.confidenceHi());
			}
		}
	}