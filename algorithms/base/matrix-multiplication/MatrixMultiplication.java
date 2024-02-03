/**
 * This program implements functions that multiply matrices.
 * @version 1.0 2024-02-03
 * @author Strokin Constantine
 */
 
public class MatrixMultiplication
{
	// Matrices must be m by n and n by p in size (you can pad matrices with zeros)
	// Complexity is O(n^3)
    public static int[][] multiplyMatricesByDefinition(int[][] a, int[][] b)
    {
        int m = a.length;
        int n = a[0].length;
        int p = b[0].length;

        int[][] c = new int[n][p];

        for (int i = 0; i < m; i++)
            for (int j = 0; j < p; j++){
                c[i][j] = 0;
                for(int k = 0; k < n; k++)
                    c[i][j] += a[i][k] * b[k][j];
			}
        return c;
    }

	// Matrices must be 2^n by 2^n in size (you can pad matrices with zeros)
	// Complexity is O(lg(7))
	public static int[][] multiplyMatricesUsingAlgorithmOfStrassen(
            int[][] a, int[][] b)
    {
        int m = a.length;
        int n = a[0].length;
        int p = b[0].length;

        if (m == 1 && n == 1 && p == 1) // End of recursion
            return new int[][]{{a[0][0] * b[0][0]}};

        int[][] a11 = getSubmatrix( a, 0, (m/2) - 1, 0, (n/2) - 1 );
        int[][] a12 = getSubmatrix( a, 0, (m/2) - 1, (n/2), n - 1);
        int[][] a21 = getSubmatrix( a, (m/2), m - 1, 0, (n/2) - 1 );
        int[][] a22 = getSubmatrix( a, (m/2), m - 1, (n/2), n - 1 );

        int[][] b11 = getSubmatrix(b, 0, (n/2) - 1, 0, (p/2) - 1);
        int[][] b12 = getSubmatrix(b, 0, (n/2) - 1, (p/2), p - 1);
        int[][] b21 = getSubmatrix(b, (n/2), n - 1, 0, (p/2) - 1);
        int[][] b22 = getSubmatrix(b, (n/2), n - 1, (p/2), p - 1);

        int[][] s1 = getMatricesDifference( b12, b22 );
        int[][] s2 = getMatricesSum( a11, a12 );
        int[][] s3 = getMatricesSum( a21, a22 );
        int[][] s4 = getMatricesDifference( b21, b11 );
        int[][] s5 = getMatricesSum( a11, a22 );
        int[][] s6 = getMatricesSum( b11, b22 );
        int[][] s7 = getMatricesDifference( a12, a22 );
        int[][] s8 = getMatricesSum( b21, b22 );
        int[][] s9 = getMatricesDifference( a11, a21 );
        int[][] s10 = getMatricesSum( b11, b12 );

		int[][] p1 = multiplyMatricesUsingAlgorithmOfStrassen(a11, s1);
		int[][] p2 = multiplyMatricesUsingAlgorithmOfStrassen(s2, b22);
		int[][] p3 = multiplyMatricesUsingAlgorithmOfStrassen(s3, b11);
		int[][] p4 = multiplyMatricesUsingAlgorithmOfStrassen(a22, s4);
		int[][] p5 = multiplyMatricesUsingAlgorithmOfStrassen(s5, s6);
		int[][] p6 = multiplyMatricesUsingAlgorithmOfStrassen(s7, s8);
		int[][] p7 = multiplyMatricesUsingAlgorithmOfStrassen(s9, s10);
	
		int[][] c11 = getMatricesSum( getMatricesDifference( getMatricesSum(p5, p4), p2), p6);
		int[][] c12 = getMatricesSum( p1, p2 );
		int[][] c21 = getMatricesSum( p3, p4 );
		int[][] c22 = getMatricesDifference( getMatricesDifference( getMatricesSum(p5, p1), p3), p7);
		
        return composeMatrixBlocks(c11, c12, c21, c22);
    }

	// Matrices must be 2^n by 2^n in size (you can pad matrices with zeros)
	// Complexity is O(n^3)
    public static int[][] multiplyMatricesUsingDecomposition(
            int[][] a, int[][] b)
    {
        int m = a.length;
        int n = a[0].length;
        int p = b[0].length;

        if (m == 1 && n == 1 && p == 1)
            return new int[][]{{a[0][0] * b[0][0]}};
		
        int[][] a11 = getSubmatrix( a, 0, (m/2) - 1, 0, (n/2) - 1 );
        int[][] a12 = getSubmatrix( a, 0, (m/2) - 1, (n/2), n - 1);
        int[][] a21 = getSubmatrix( a, (m/2), m - 1, 0, (n/2) - 1 );
        int[][] a22 = getSubmatrix( a, (m/2), m - 1, (n/2), n - 1 );

        int[][] b11 = getSubmatrix(b, 0, (n/2) - 1, 0, (p/2) - 1);
        int[][] b12 = getSubmatrix(b, 0, (n/2) - 1, (p/2), p - 1);
        int[][] b21 = getSubmatrix(b, (n/2), n - 1, 0, (p/2) - 1);
        int[][] b22 = getSubmatrix(b, (n/2), n - 1, (p/2), p - 1);

        int[][] c11 = getMatricesSum( multiplyMatricesUsingDecomposition(a11, b11), multiplyMatricesUsingDecomposition(a12, b21) );
        int[][] c12 = getMatricesSum( multiplyMatricesUsingDecomposition(a11, b12), multiplyMatricesUsingDecomposition(a12, b22) );
        int[][] c21 = getMatricesSum( multiplyMatricesUsingDecomposition(a21, b11), multiplyMatricesUsingDecomposition(a22, b21) );
        int[][] c22 = getMatricesSum( multiplyMatricesUsingDecomposition(a21, b12), multiplyMatricesUsingDecomposition(a22, b22) );
		
        return composeMatrixBlocks(c11, c12, c21, c22);
    }
	
	// Utility functions:

    public static int[][] getSubmatrix(int[][] matrix, int mStartingIndex,
                                        int mEndingIndex, int nStartingIndex, int nEndingIndex)
    {
        int[][] submatrix = new int[mEndingIndex - mStartingIndex + 1][nEndingIndex - nStartingIndex + 1];

        for (int i = 0; i < mEndingIndex - mStartingIndex + 1; i++)
            for (int j = 0; j < nEndingIndex - nStartingIndex + 1; j++)
                submatrix[i][j] = matrix[mStartingIndex + i][nStartingIndex + j];

        return submatrix;
    }

    public static int[][] composeMatrixBlocks( int[][] a11, int[][] a12, int[][] a21, int[][] a22 )
    {
        int m1 = a11.length;
        int m2 = a22.length;
        int n1 = a11[0].length;
        int n2 = a22[0].length;
        int m = m1 + m2;
        int n = n1 + n2;

        int[][] a = new int[m][n];

        for (int i = 0; i < m1; i++)
            for (int j = 0; j < n1; j++)
                a[i][j] = a11[i][j];
			
        for (int i = 0; i < m1; i++)
            for (int j = n1; j < n; j++)
                a[i][j] = a12[i][j - n1];
			
        for (int i = m1; i < m; i++)
            for (int j = 0; j < n1; j++)
                a[i][j] = a21[i - m1][j];
			
        for (int i = m1; i < m; i++)
            for (int j = n1; j < n; j++)
                a[i][j] = a22[i - m1][j - n1];

        return a;
    }


    public static int[][] getMatricesSum(int[][] a, int[][] b)
    {
        int m = a.length;
        int n = a[0].length;

        int[][] c = new int[m][n];

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                c[i][j] = a[i][j] + b[i][j];
        return c;
    }
	
    public static int[][] getMatricesDifference( int[][] a, int[][] b )
    {
        int m = a.length;
        int n = a[0].length;
        int[][] c = new int[m][n];

        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                c[i][j] = a[i][j] - b[i][j];
        return c;
    }

    public static void printMatrixToConsole(int[][] matrix)
    {
        int m = matrix.length;
        int n = matrix[0].length;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (j == 0)
                    System.out.format("%1$3c",'|');
                System.out.format("%1$,3d", matrix[i][j]);
                if (j == (n-1))
                    System.out.format("%1$3c",'|');
            }
            System.out.println();
        }
    }
	
	public static void main(String[] arguments)
    {
		printMatrixToConsole(
		multiplyMatricesUsingAlgorithmOfStrassen(
		new int[][]{
			{2,2,2,2},
			{3,3,3,3},
			{0,0,4,0},
			{0,0,0,5}
			},
		new int[][]{
			{1,0,0,0},
			{0,2,0,0},
			{0,0,3,0},
			{0,0,0,4}
			}
			)
        );
		
		System.out.println();
		
		printMatrixToConsole(
		multiplyMatricesUsingDecomposition(
		new int[][]{
			{2,2,2,2},
			{3,3,3,3},
			{0,0,4,0},
			{0,0,0,5}
			},
		new int[][]{
			{1,0,0,0},
			{0,2,0,0},
			{0,0,3,0},
			{0,0,0,4}
			}
			)
        );
		
		System.out.println();

		printMatrixToConsole(
		multiplyMatricesUsingDecomposition(
		new int[][]{
			{2,0,0,0},
			{0,3,0,0},
			{0,0,4,0},
			{0,0,0,5}
			},
		new int[][]{
			{1,2,3,4},
			{5,6,7,8},
			{0,0,1,0},
			{0,0,0,2}
			}
			)
        );
		
		System.out.println();
		
		printMatrixToConsole(
		multiplyMatricesByDefinition(
		new int[][]{
			{2,0,0,0},
			{0,3,0,0},
			{0,0,4,0},
			{0,0,0,5}
			},
		new int[][]{
			{1,2,3,4},
			{5,6,7,8},
			{0,0,1,0},
			{0,0,0,2}
			}
			)
        );
    }
}