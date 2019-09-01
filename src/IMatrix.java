/**
 * Created by Ken on 21.10.2018..
 */
public interface IMatrix {
    int getRowsCount();
    int getColsCount();
    double getE(int row, int col);
    IMatrix setE(int row, int col, double value);
    IMatrix subE(int row, int col, double value);
    IMatrix addE(int row, int col, double value);
    IMatrix divE(int row, int col, double value);
    IMatrix add(IMatrix m);
    IMatrix sub(IMatrix m);
    IMatrix scalarMul(double s);
    IMatrix mul(IMatrix m);
    IMatrix transpose();
    IMatrix substitutionForward(IMatrix v);
    IMatrix substitutionBackward(IMatrix v);
    IMatrix decompositionLU();
    IMatrix[] decompositionLUP();
    IMatrix getL();
    IMatrix getU();
    IMatrix print();
    boolean equal(IMatrix m);
    IMatrix permutated(IMatrix v);
    IMatrix switchRows(int i, int j);
    IMatrix switchCols(int i, int j);
}
