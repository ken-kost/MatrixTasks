import java.io.File;

/**
 * Created by Ken on 21.10.2018..
 */
public class task2 {
    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        File f = new File("m2.txt");
        IMatrix m = new Matrix(f);
        System.out.println("Matrix is...");
        m.print();
        sb.append("\nMatrix is...\n");
        sb.append(m.toString());
        File f_v = new File("v2.txt");
        IMatrix v = new Matrix(f_v);
        System.out.println("Free vector is...");
        v.transpose().print();
        sb.append("\nFree vector is...\n");
        sb.append(v.transpose().toString());
        System.out.println("Trying to decompose (LU)...");
        IMatrix solution = m.decompositionLU();
        if(solution.equal(m)) {
            System.out.println("Failed to decompose using LU decomposition.");
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
            System.out.println("Solutions are...");
            X.print();
            sb.append("Substitution forward on matrix L with permutated free vector...\n\tY\n");
            sb.append(Y.toString());
            sb.append("\nSubstitution backward on matrix U with Y...\n");
            sb.append("\n\nSolution\n\tX\n");
            sb.append(X.toString());
            FilePrinter.printToFile(sb.toString(), f);
        } else {
            System.out.println("Successfully decomposed using LU.");
        }
    }
}
