/**
 * Created by Ken on 3.11.2018..
 */
public class F1 implements IFunctions {

    private int counter = 0;

    @Override
    public double value(double[] x) {
        double x1, x2;
        if(x.length==2) {
            x1 = x[0];
            x2 = x[1];
        } else return -1.;
        counter++;
        return 100 * Math.pow((x2-Math.pow(x1,2)),2) + Math.pow(1-x1,2);
    }

    public int getCounter() {
        return counter;
    }
}
