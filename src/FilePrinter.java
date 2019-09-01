import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Ken on 21.10.2018..
 */
public class FilePrinter {

    public static void printToFile(String s, File f) throws IOException {
        String name = f.getName().substring(0,f.getName().length()-4) + "-o.txt";
        BufferedWriter o = new BufferedWriter(new FileWriter(name));
        o.write(s);
        o.close();
    }
}
