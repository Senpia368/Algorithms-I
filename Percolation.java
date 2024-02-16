import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    WeightedQuickUnionUF uf;
    int n;
    boolean open[];
    int numOSites = 0;
    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        this.n = n;
        open = new boolean[n*n];
        uf = new WeightedQuickUnionUF(n*n + 2);
        

        // Connect all top nums to root 0
        for (int i = 1; i <= n; i++) {
            uf.union(0, i);
        }
        
        // int lastElement = n*n + 1;

        // Connect all bottom nums to root last element
        /* 
        for (int i = 1; i <=n; i++) {            
            uf.union(lastElement, lastElement - i);            
        }*/
        
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) throws IllegalArgumentException {
        int element = n*(row-1) + col;;
        if (!this.isOpen(row, col)) {
            //System.out.println(""+row+" " + col);
            open[element-1] = true;
            numOSites++;

            if(element%n != 0 && this.isOpen(row, col+1)){                
                uf.union(element+1, element);}

            if(element%n != 1 && this.isOpen(row, col-1)){
                uf.union(element-1, element);}

            if((element - n) > 0 && this.isOpen(row-1, col)){
                uf.union(element-n, element);}
            
            if((element + n) <= n*n && this.isOpen(row+1, col)){
                uf.union(element+n, element);}
        }
        
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) throws IllegalArgumentException {
        int element = n*(row-1) + col;        
        return open[element-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) throws IllegalArgumentException {
        int element = n*(row-1) + col;
        while(uf.find(element) != element) {
            if (uf.find(element) == uf.find(0))
                return true;
            else element = uf.find(element);
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numOSites;
    }

    // does the system percolate?
    public boolean percolates() {
        for (int i = 1; i <=n; i++) {
            if (this.isFull(n,i)) {
                return true;
            }
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args) throws IllegalArgumentException{
        double[] probability = new double[100];
        for (int i = 0; i < probability.length; i++) {
            int n = 200;
            Percolation p = new Percolation(n);


            while(!p.percolates()) {
                int x = StdRandom.uniformInt(1,n+1);
                int y =StdRandom.uniformInt(1,n+1);
                p.open(x,y);
                //System.out.println("opened");

            }

            //System.out.println(p.numberOfOpenSites());
            //System.out.println(p.percolates());

            probability[i] = (double) p.numberOfOpenSites()/(n*n);
        }

        System.out.println(StdStats.mean(probability));

        //System.out.println(p.isFull(20,21));

        
        

        
        
    }
}
