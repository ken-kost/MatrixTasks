import java.io.File;

/**
 * Created by Ken on 21.10.2018..
 */
public class task1 {
    public static void main(String[] args) throws Exception {
        File f = new File("/home/ken/Downloads/apr_dz1/example.txt");
        IMatrix m1 = new Matrix(f);
        System.out.println("Matrix 1 is...");
        m1.print();
        IMatrix m2 = new Matrix(m1);
        System.out.println("Matrix 2 is...");
        m2.print();
        System.out.println("Are m1 and m2 equal? " + m1.equal(m2));
        System.out.println("Changing Matrix 2 (multiplying with scalar Pi)...");
        m2.scalarMul(Math.PI);
        System.out.println("Matrix 2 is...");
        m2.print();
        System.out.println("Are m1 and m2 equal? " + m1.equal(m2));
    }
}
