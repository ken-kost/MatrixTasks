import java.io.File;

/**
 * Created by Ken on 21.10.2018..
 */
public class Example {

    public static void main(String[] args) throws Exception{
        File f = new File("example.txt");
        IMatrix m = new Matrix(f);
        m.print();
    }
}
