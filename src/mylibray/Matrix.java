/*
    
    Refatora objeto inventendo a ordem de rows e cols para ganhar performece por System.copyarry();

 */
package mylibray;

import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thales
 */
public final class Matrix {
    
    public final double[][] matrix;
    public final int rows;
    public final int cols;
    
    public Matrix(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        matrix = new double[rows][cols];
    }
    public Matrix(double[][] input){
        this.rows = input.length;
        this.cols = input[0].length;
        matrix = input;
    }
    public Matrix(double[] input){
        this.rows = input.length;
        this.cols = 1;
        matrix = new double[rows][cols];
        arrayToCol(input,0);
    }
    
    public Matrix randomize(double min, double max){
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                matrix[r][c] = Math.random()*(max-min)+min;
            }
        }
        return this;
    }
    
    //SCALAR
    public Matrix add(double v){
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                matrix[r][c] += v;
            }
        }
        return this;
    }
    public Matrix sub(double v){
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                matrix[r][c] -= v;
            }
        }
        return this;
    }

    /**
     *
     * @param v
     * @return escalar multiplication
     */
    public Matrix mult(double v){
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                matrix[r][c] *= v;
            }
        }
        return this;
    }
    /**
     *
     * @param v
     * @return escalar division
     */
    public Matrix div(double v){
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                matrix[r][c] /= v;
            }
        }
        return this;
    }
    
    //ELEMENT WISE
    public Matrix fill(double v){
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                matrix[r][c] = v;
            }
        }
        return this;
    }
    /**
     *
     * @param b matrix
     * @return element wise addition
     */
    public Matrix add(Matrix b){
        if (b.cols != cols || b.rows != rows) try {
            throw new Exception("Matrix dimensions need to be the same");
        } catch (Exception ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        } 
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                matrix[r][c] += b.matrix[r][c];
            }
        }
        return this;
    }
    /**
     *
     * @param a matrix
     * @param b matrix
     * @return element wise addition
     */
    public static Matrix add(Matrix a, Matrix b){
        if (a.cols != b.cols || a.rows != b.rows) try {
            throw new Exception("Matrix dimensions need to be the same");
        } catch (Exception ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        } 
        Matrix m = a.copy();
        for (int r = 0; r < m.rows; r++){
            for (int c = 0; c < m.cols; c++){
                m.matrix[r][c] += b.matrix[r][c];
            }
        }
        return m;
    }
    /**
    *
    * @param b matrix
    * @return element wise subtraction
    */
    public Matrix sub(Matrix b){
        if (b.cols != cols || b.rows != rows) try {
            throw new Exception("Matrix dimensions need to be the same");
        } catch (Exception ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        } 
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                matrix[r][c] -= b.matrix[r][c];
            }
        }
        return this;
    }
    /**
    *
    * @param a matrix
    * @param b matrix
    * @return element wise subtraction
    */
    public static Matrix sub(Matrix a, Matrix b){
        if (a.cols != b.cols || a.rows != b.rows) try {
            throw new Exception("Matrix dimensions need to be the same");
        } catch (Exception ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        } 
        Matrix m = a.copy();
        for (int r = 0; r < m.rows; r++){
            for (int c = 0; c < m.cols; c++){
                m.matrix[r][c] -= b.matrix[r][c];
            }
        }
        return m;
    }
    /**
    * Hadamard product 
    *  
    * @param b matrix
    * @return element wise multiplication
    */
    public Matrix mult(Matrix b){
        if (b.cols != cols || b.rows != rows) try {
            throw new Exception("Matrix dimensions need to be the same");
        } catch (Exception ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        } 
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                matrix[r][c] *= b.matrix[r][c];
            }
        }
        return this;
    }
    /**
    * Hadamard product 
    *  
    * @param a matrix
    * @param b matrix
    * @return element wise multiplication
    */
    public static Matrix mult(Matrix a, Matrix b){
        if (a.cols != b.cols || a.rows != b.rows) try {
            throw new Exception("Matrix dimensions need to be the same");
        } catch (Exception ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        } 
        Matrix m = a.copy();
        for (int r = 0; r < m.rows; r++){
            for (int c = 0; c < m.cols; c++){
                m.matrix[r][c] *= b.matrix[r][c];
            }
        }
        return m;
    }
    /**
    * Hadamard division 
    *  
    * @param b matrix
    * @return element wise division
    */
    public Matrix div(Matrix b){
        if (b.cols != cols || b.rows != rows) try {
            throw new Exception("Matrix dimensions need to be the same");
        } catch (Exception ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        } 
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                matrix[r][c] /= b.matrix[r][c];
            }
        }
        return this;
    }
    /**
    * Hadamard division 
    *  
    * @param a matrix
    * @param b matrix
    * @return element wise division
    */
    public static Matrix div(Matrix a, Matrix b){
        if (a.cols != b.cols || a.rows != b.rows) try {
            throw new Exception("Matrix dimensions need to be the same");
        } catch (Exception ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        } 
        Matrix m = a.copy();
        for (int r = 0; r < m.rows; r++){
            for (int c = 0; c < m.cols; c++){
                m.matrix[r][c] /= b.matrix[r][c];
            }
        }
        return m;
    }
    
    /**
     *
     * @param a
     * @param b
     * @return Matrix multiplication
     */
    public static Matrix product(Matrix a, Matrix b){
        if (a.cols != b.rows) try {
            throw new Exception("The number of cols in 'a' must be the same of rows of 'b'");
        } catch (Exception ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        } 
        Matrix m = new Matrix(a.rows,b.cols);
        for (int r = 0; r < m.rows; r++){
            for (int c = 0; c < m.cols; c++){
                double dot = 0;
                for (int i = 0; i < a.cols; i++){
                    dot += a.matrix[r][i]*b.matrix[i][c];
                }
                m.matrix[r][c] = dot;
            }
        }
        return m;
    }
    
    /**
     * transpose a matrix
     * @param a
     * @return
     */
    public static Matrix transpose(Matrix a){
        Matrix m = new Matrix(a.cols, a.rows);
        for (int ar = 0; ar < a.rows; ar++){
            for (int ac = 0; ac < a.cols; ac++){
                m.matrix[ac][ar] = a.matrix[ar][ac];
            }
        }
        return m;
    }

    /**
     * Element wise aplication of a given function to the matrix
     * @param f function reference f(double x);
     * @return
     */
    public Matrix map(Function<Double,Double> f){
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                matrix[r][c] = f.apply(matrix[r][c]);
            }
        }
        return this;
    }
    
    /**
     * Element wise aplication of a given function to the matrix
     * @param a input matrix
     * @param f function reference f(double x);
     * @return
     */
    public static Matrix map(Matrix a, Function<Double,Double> f){
        Matrix m = a.copy();
        for (int r = 0; r < m.rows; r++){
            for (int c = 0; c < m.cols; c++){
                m.matrix[r][c] = f.apply(m.matrix[r][c]);
            }
        }
        return m;
    }
    
    /**
     * Return a row of the matrix at a given index as a array
     * @param row
     * @return
     */
    public double[] rowToArray(int row){
        return matrix[row].clone();
    }
    /**
     * Return a column of the matrix at a given index as a array
     * @param col
     * @return
     */
    public double[] colToArray(int col){
        double[] colArray = new double[rows];
        for (int i = 0; i < rows; i++){
            colArray[i] = matrix[i][col];
        }
        return colArray;
    }
    /**
     * Return the entire matrix as a single array row by row
     * @return
     */
    public double[] toArray(){
        double[] array = new double[cols*rows];
        int index = 0;
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                array[index] = matrix[r][c];
                index++;
            }
        }
        return array;
    }
    /**
     * define every number in the matrix by a single array of numbers row by row
     * @param array
     */
    public void fromArray(double[] array){
        if (array.length != cols*rows) try {
            throw new Exception("the array size: "+array.length+" must be the same of as the total numbers in the matrix: "+cols*rows);
        } catch (Exception ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        }
        int index = 0;
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                matrix[r][c] = array[index];
                index++;
            }
        }
    }
    /**
     * return a single row in the matrix at a given index as a array
     * @param array
     * @param row
     */
    public void arrayToRow(double[] array, int row){
        if (array.length != cols) try {
            throw new Exception("the array size: "+array.length+" must be the same of cols of matrix: "+cols);
        } catch (Exception ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        } 
        matrix[row] = array;
    }
    /**
     * return a single column in the matrix at a given index as a array
     * @param array
     * @param col
     */
    public void arrayToCol(double[] array, int col){
        if (array.length != rows) try {
            throw new Exception("the array size: "+array.length+" must be the same of rows of matrix: "+rows);
        } catch (Exception ex) {
            Logger.getLogger(Matrix.class.getName()).log(Level.SEVERE, null, ex);
        } 
        for (int i = 0; i < rows; i++){
            matrix[i][col] = array[i];
        }
    }
    
    //OBJ
    /**
     * return a copy of the matrix
     * @return matrix copy
     */
    public Matrix copy(){
        Matrix m = new Matrix(rows, cols);
        for (int r = 0; r < rows; r++){
            System.arraycopy(matrix[r], 0, m.matrix[r], 0, cols);
        }
        return m;
    }
    
    /**
     * write the matrix to console in a ordered way
     * @return
     */
    @Override
    public String toString(){
        String s = "";
        for (int r = 0; r < rows; r++){
            String row = "";
            for (int c = 0; c < cols; c++){
                row += "| "+String.format("%5.2f",matrix[r][c])+" ";
            }
            s += row+"|\n";
        }
        return s;
    }
}