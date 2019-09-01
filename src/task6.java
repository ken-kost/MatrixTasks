import java.io.File;

/**
 * Created by Ken on 21.10.2018..
 */
public class task6 {
    public static void main(String[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        File f = new File("m6.txt");
        IMatrix m = new Matrix(f);
        System.out.println("Matrix is...");
        m.print();
        sb.append("\nMatrix is...\n");
        sb.append(m.toString());
        File f_v = new File("v6.txt");
        IMatrix v = new Matrix(f_v);
        System.out.println("Free vector is...");
        v.transpose().print();
        sb.append("\nFree vector is...\n");
        sb.append(v.transpose().toString());
        System.out.println("#####################");
        System.out.println("Trying to decompose (LUP)...");
        IMatrix[] solutions = new IMatrix[2];
        solutions = m.decompositionLUP();
        IMatrix decompM = new Matrix(solutions[0]);
        IMatrix permVec = new Matrix(solutions[1]);
        System.out.println("Decomposed matrix...");
        decompM.print();
        System.out.println("Permutated vector...");
        permVec.print();
        sb.append("\n\n\nLUP\nDecomposed matrix...");
        sb.append(decompM.toString());
        sb.append("\nPermutated vector...\n");
        sb.append(permVec.toString());
        System.out.println("Getting L matrix...");
        IMatrix L = decompM.getL();
        System.out.println("Getting U matrix...");
        IMatrix U = decompM.getU();
        sb.append("\nGetting L matrix...\n");
        sb.append(L.toString());
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
        sb.append("\nSubstitution backward on matrix U with Y...\n Not possible \n\n Transforming the system...");
        for(int i = 0; i < m.getRowsCount(); i++) {
            for(int j = 0; j < m.getColsCount(); j++) {
                if(i == 0) m.divE(i,j,1000000000);
                if(i == 2) m.setE(i,j,m.getE(i,j)*1000000000);
            }
        }
        v.divE(0,0,1000000000);
        v.setE(2,0,v.getE(2,0)*1000000000);
        System.out.println("Matrix is...");
        m.print();
        sb.append("\nMatrix is...\n");
        sb.append(m.toString());
        System.out.println("#####################");
        System.out.println("Trying to decompose (LUP)...");
        solutions = new IMatrix[2];
        solutions = m.decompositionLUP();
        decompM = new Matrix(solutions[0]);
        permVec = new Matrix(solutions[1]);
        System.out.println("Decomposed matrix...");
        decompM.print();
        System.out.println("Permutated vector...");
        permVec.print();
        sb.append("\n\n\nLUP\nDecomposed matrix...");
        sb.append(decompM.toString());
        sb.append("\nPermutated vector...\n");
        sb.append(permVec.toString());
        System.out.println("Getting L matrix...");
        L = decompM.getL();
        System.out.println("Getting U matrix...");
        U = decompM.getU();
        sb.append("\nGetting L matrix...\n");
        sb.append(L.toString());
        sb.append("\nGetting U matrix...\n");
        sb.append(U.toString());
        System.out.println("Permuting the free vector...");
        pV = v.permutated(permVec);
        pV.print();
        sb.append("\nPermuting the free vector...\n");
        sb.append(pV.toString());
        Y = L.substitutionForward(pV);
        Y.print();
        X = U.substitutionBackward(Y);
        X.print();
        sb.append("Substitution forward on matrix L with permutated free vector...\n\tY\n");
        sb.append(Y.toString());
        sb.append("\nSubstitution backward on matrix U with Y...\n");
        sb.append("\n\nSolution\n\tX\n");
        sb.append(X.toString());
        FilePrinter.printToFile(sb.toString(), f);
        FilePrinter.printToFile(sb.toString(), f);
    }
}
