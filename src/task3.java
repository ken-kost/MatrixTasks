import java.io.File;

/**
 * Created by Ken on 21.10.2018..
 */
public class task3 {
    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        File f = new File("m3.txt");
        double[][] vValues = new double[3][1];
        vValues[0][0] = 1; vValues[1][0] = 2; vValues[2][0] = 3;
        IMatrix v = new Matrix(3,1, vValues);
        IMatrix m = new Matrix(f);
        m.print();
        sb.append(m.toString());
        FilePrinter.printToFile(sb.toString(), f);
        System.out.println("Trying to decompose (LUP)...");
        IMatrix[] solutions = new IMatrix[2];
        solutions = m.decompositionLUP();
        IMatrix decompM = new Matrix(solutions[0]);
        IMatrix permVec = new Matrix(solutions[1]);
        System.out.println("Decomposed matrix...");
        decompM.print();
        System.out.println("Permutated vector...");
        permVec.print();
        sb.append("\n\nLUP\nDecomposed matrix...");
        sb.append(decompM.toString());
        sb.append("\nPermutated vector...\n");
        sb.append(permVec.toString());
        System.out.println("Getting L matrix...");
        IMatrix L = decompM.getL();
        System.out.println("Getting U matrix...");
        sb.append("\nGetting L matrix...\n");
        sb.append(L.toString());
        IMatrix U = decompM.getU();
        sb.append("\nGetting U matrix...\n");
        sb.append(U.toString());
        System.out.println("Permuting the free vector...");
        IMatrix pV = v.permutated(permVec);
        pV.print();
        sb.append("\nPermuting the free vector...\n");
        sb.append(pV.toString());
        IMatrix Y = L.substitutionForward(pV);
        Y.print();
        IMatrix X = U.substitutionBackward(Y);
        sb.append("Substitution forward on matrix L with permutated free vector...\n\tY\n");
        sb.append(Y.toString());
        sb.append("\nSubstitution backward on matrix U with Y...\n Not possible, every possible pivot is < min_value_constant");
        FilePrinter.printToFile(sb.toString(), f);    }
}
