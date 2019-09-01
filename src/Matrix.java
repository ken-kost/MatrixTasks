import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Ken on 21.10.2018..
 */
public class Matrix implements IMatrix {

    private final double c = 0.000001;

    private int rows;
    private int cols;
    private double[][] values;

    public Matrix() {

    }

    public Matrix(IMatrix m) {
        this.rows = m.getRowsCount();
        this.cols = m.getColsCount();
        this.values = new double[this.rows][this.cols];
        for(int i = 0; i < this.rows; i++) {
            for(int j = 0; j < this.cols; j++) {
                this.values[i][j] = m.getE(i,j);
            }
        }
    }

    public Matrix(File f) throws Exception {
        Scanner sc = new Scanner(new BufferedReader(new FileReader(f)));
        List<Double> l = new ArrayList<>(100);
        int rows = 0;
        int cols = 0;
        while(sc.hasNextLine()) {
            String[] line = sc.nextLine().trim().split(" ");
            rows++;
            if(rows == 1) cols = line.length;
            for(int k = 0; k < line.length; k++) {
                l.add(Double.parseDouble(line[k]));
            }
        }
        int k = 0;
        double[][] values = new double[rows][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                values[i][j] = l.get(k++);
            }
        }
        this.rows = rows;
        this.cols = cols;
        this.values = values;
    }

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.values = new double[rows][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                this.values[i][j] = 0.0;
            }
        }
    }

    public Matrix(int rows, int cols, double[][] values) {
        this.rows = rows;
        this.cols = cols;
        this.values = new double[rows][cols];
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                try {
                    this.values[i][j] = values[i][j];
                } catch (NullPointerException e) {
                    this.values[i][j] = 0.0;
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public int getRowsCount() {
        return this.rows;
    }

    @Override
    public int getColsCount() {
        return this.cols;
    }

    @Override
    public double getE(int row, int col) {
        return values[row][col];
    }

    @Override
    public IMatrix setE(int row, int col, double value) {
        this.values[row][col] = value;
        return this;
    }

    @Override
    public IMatrix subE(int row, int col, double value) {
        this.values[row][col] -= value;
        return this;
    }

    @Override
    public IMatrix addE(int row, int col, double value) {
        this.values[row][col] += value;
        return this;
    }

    @Override
    public IMatrix divE(int row, int col, double value) {
        this.values[row][col] /= value;
        return this;
    }

    @Override
    public IMatrix add(IMatrix m) {
        if(this.getRowsCount() != m.getRowsCount() || this.getColsCount() != m.getColsCount()) {
            System.err.println("Can not add matrices. Bad dimensions");
            return this;
        }
        for(int i = 0; i < this.getRowsCount(); i++) {
            for(int j = 0; j < this.getColsCount(); j++) {
                this.addE(i, j, m.getE(i,j));
            }
        }
        return this;
    }

    @Override
    public IMatrix sub(IMatrix m) {
        if(this.getRowsCount() != m.getRowsCount() || this.getColsCount() != m.getColsCount()) {
            System.err.println("Can not subtract matrices. Bad dimensions");
            return this;
        }
        for(int i = 0; i < this.getRowsCount(); i++) {
            for(int j = 0; j < this.getColsCount(); j++) {
                this.subE(i, j, m.getE(i,j));
            }
        }
        return this;
    }

    @Override
    public IMatrix scalarMul(double s) {
        for(int i = 0; i < this.getRowsCount(); i++) {
            for(int j = 0; j < this.getColsCount(); j++) {
                this.setE(i, j, s * this.getE(i, j));
            }
        }
        return this;
    }

    @Override
    public IMatrix mul(IMatrix m) {
        if(this.getColsCount() != m.getRowsCount()) {
            System.err.println("Matrices are not compatible for multiplication.");
            return this;
        }
        IMatrix matrix = new Matrix(this.getRowsCount(), m.getColsCount());
        for(int i = 0; i < this.getRowsCount(); i++) {
            for(int j = 0; j < m.getColsCount(); j++) {
                matrix.setE(i, j, 0.0);
                for(int k = 0; k < this.getColsCount(); k++) {
                    matrix.addE(i, j, this.getE(i, k) * m.getE(k, j));
                }
            }
        }
        return matrix;
    }

    @Override
    public IMatrix transpose() {
        double[][] values = new double[this.getColsCount()][this.getRowsCount()];
        for(int i = 0; i < this.getRowsCount(); i++) for(int j = 0; j < this.getColsCount(); j++) values[j][i] = this.getE(i,j);
        IMatrix m = new Matrix(this.getColsCount(), this.getRowsCount(), values);
        return m;
    }

    @Override
    public IMatrix substitutionForward(IMatrix v) {
        if (v.getColsCount() > 1) {
            System.err.println("Bad input. Not a vector.");
            return this;
        }
        if(v.getRowsCount() != this.getRowsCount()) {
            System.err.println("Bad vector dimension. Does not match matrix.");
            return this;
        }
        IMatrix vector = new Matrix(v);
        for(int i = 0; i < this.getRowsCount() - 1; i++) {
            for(int j = i + 1; j < this.getRowsCount(); j++) {
                vector.subE(j, 0, this.getE(j, i) * vector.getE(i,0));
            }
        }
        return vector;
    }

    @Override
    public IMatrix substitutionBackward(IMatrix v) {
        if (v.getColsCount() > 1) {
            System.err.println("Bad input. Not a vector.");
            return this;
        }
        if(v.getRowsCount() != this.getRowsCount()) {
            System.err.println("Bad vector dimension. Does not match matrix.");
            return this;
        }
        IMatrix vector = new Matrix(v);
        for (int i = this.getRowsCount() - 1; i > -1; i--) {
            if(Math.abs(this.getE(i,i)) < this.c) {
                System.err.println("Stoping (subBack)... pivot < min_value_constant");
                return this;
            }
            vector.divE(i, 0, this.getE(i,i));
            for (int j = 0 ; j < i; j++) {
                vector.subE(j, 0, this.getE(j, i) * vector.getE(i,0));
            }
        }
        return vector;
    }

    @Override
    public IMatrix decompositionLU() {
        if(this.getRowsCount() != this.getColsCount()) {
            System.err.println("Not a square matrix. Can not compute.");
            return this;
        }
        IMatrix m = new Matrix(this);
        for (int i = 0; i < m.getRowsCount() - 1; i++) {
            for (int j = i + 1; j < m.getColsCount(); j++) {
                if(Math.abs(m.getE(i,i)) < this.c) {
                    System.err.println("Stopping LU decomposition... pivot < min_value_constant");
                    return this;
                }
                m.divE(j, i, m.getE(i,i));
                for (int k = i + 1; k < this.getRowsCount(); k++) {
                    m.subE(j, k, m.getE(j,i) * m.getE(i,k));
                }
            }
        }
        return m;
    }

    @Override
    public IMatrix[] decompositionLUP() {
        IMatrix m = new Matrix(this);
        IMatrix v = new Matrix(1,this.getRowsCount());
        IMatrix[] set = new IMatrix[2];
        set[0] = m;
        set[1] = v;
        for(int i = 0; i < this.getRowsCount(); i++) v.setE(0, i, i);
        int p;
        for(int i = 0; i < this.getRowsCount() - 1; i++) {
            p = i;
            for(int j = i + 1; j < this.getRowsCount(); j++) {
                if(Math.abs(m.getE(j,i)) > Math.abs(m.getE(p,i))) p = j;
            }
            if(Math.abs(m.getE(p,i)) < this.c) {
                System.err.println("Stopping LUP decomposition... pivot < min_value_constant");
                return set;
            }
            m = new Matrix(m.switchRows(i,p));
            v = new Matrix(v.switchCols(i,p));
            for (int j = i + 1; j < this.getRowsCount(); j++) {
                m.divE(j, i, m.getE(i,i));
                for (int k = i + 1; k < this.getRowsCount(); k++) {
                    m.subE(j, k, m.getE(j,i) * m.getE(i,k));
                }
            }
        }
        set[0] = m;
        set[1] = v;
        return set;
    }

    @Override
    public IMatrix getL() {
        IMatrix l = new Matrix(this);
        for (int i = 0; i < this.getRowsCount(); i++) {
            for (int j = 0; j <this.getColsCount(); j++) {
                if(i == j) l.setE(i,j,1.0);
                else if(i > j) l.setE(i,j,this.getE(i,j));
                else l.setE(i,j,0.0);
            }
        }
        return l;
    }

    @Override
    public IMatrix getU() {
        IMatrix u = new Matrix(this);
        for (int i = 0; i < this.getRowsCount(); i++) {
            for (int j = 0; j <this.getColsCount(); j++) {
                if(i <= j) u.setE(i,j,this.getE(i,j));
                else u.setE(i,j,0.0);
            }
        }
        return u;
    }

    @Override
    public IMatrix print() {
        System.out.print("[");
        for(int i = 0; i < this.getRowsCount(); i++) {
            if(i == 0) System.out.print("[");
            else System.out.print(" [");
            for(int j = 0; j < this.getColsCount(); j++) {
                if(!(j == this.getColsCount()-1)) System.out.printf("%.2f, ", this.values[i][j]);
                else System.out.printf("%.2f", this.values[i][j]);
            }
            if(!(i == this.getRowsCount()-1)) System.out.print("]\n");
            else System.out.print("]");
        }
        System.out.print("]\n");
        return this;
    }

    @Override
    public boolean equal(IMatrix m) {
        if(this.getRowsCount() != m.getRowsCount() || this.getColsCount() != m.getColsCount()) return false;
        for(int i = 0; i < this.getRowsCount(); i++) {
            for(int j = 0; j < this.getColsCount(); j++) {
                if(this.getE(i,j) == m.getE(i,j)) continue;
                else return false;
            }
        }
        return true;
    }

    @Override
    public IMatrix permutated(IMatrix v) {
        IMatrix pV = new Matrix(this);
        int element;
        double value;
        for (int i = 0; i < this.getRowsCount(); i++) {
            element = (int) v.getE(0,i);
            System.out.println(element);
            System.out.println("#############");
            this.print();
            value = this.getE(element, 0);
            pV.setE(i, 0, value);
        }
        pV.print();
        return pV;
    }

    @Override
    public IMatrix switchRows(int i, int j) {
        IMatrix m = new Matrix(this);
        for(int k = 0; k < this.getColsCount(); k++) {
            m.setE(i, k, this.getE(j, k));
            m.setE(j, k, this.getE(i, k));
        }
        return m;
    }
    @Override
    public IMatrix switchCols(int i, int j) {
        IMatrix m = new Matrix(this);
        for(int k = 0; k < this.getRowsCount(); k++) {
            m.setE(k, i, this.getE(k, j));
            m.setE(k, j, this.getE(k, i));
        }
        return m;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                sb.append(this.values[i][j] + " ");
            }
            sb.append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }
}
