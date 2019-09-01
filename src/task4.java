
import java.io.FileNotFoundException;
import java.io.PrintStream;


public class task4 {
    public static void main(String[] args) throws Exception {
        execute();
        try {
            PrintStream fileOut = new PrintStream("./task4 - SOLUTION -.txt");
            System.setOut(fileOut);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        execute();
    }

    private static void execute() {
        System.out.println(" ++++ FUNCTION ONE +++++ ");
        for (int i = 0; i < 10; i++) {
            Simplex simplex = new Simplex(new double[]{0.5, 0.5}, 1E-6, new F1(), 0.125);
            simplex.algorithm();
            System.out.println("______________________");
            System.out.println(simplex.toString());
            System.out.println("Number of iterations: " + simplex.numberOfIterations());

        }
        for (int i = 0; i < 20; i++) {
            Simplex simplex = new Simplex(new double[]{20.5, 20.5}, 1E-6, new F1(), 0.125);
            simplex.algorithm();
            System.out.println("______________________");
            System.out.println(simplex.toString());
            System.out.println("Number of iterations: " + simplex.numberOfIterations());

        }
    }
}
