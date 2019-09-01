import java.util.Arrays;

/**
 * Created by Ken on 4.11.2018..
 */
public class Simplex {

    private double[] x0;
    private double epsilon = 1E-6;
    private IFunctions f;
    private double shift = 0.5;
    private double alpha = 1.0;
    private double beta = 0.5;
    private double gamma = 2.0;
    private double sigma = 0.5;
    private int noi;

    private double[] solution;

    public Simplex(double[] x0, IFunctions f) {
        this.x0 = x0;
        this.solution = new double[x0.length];
        this.f = f;
    }

    public Simplex(double[] x0, double epsilon, IFunctions f, double shift) {
        this.x0 = x0;
        this.solution = new double[x0.length];
        this.epsilon = epsilon;
        this.f = f;
        this.shift  = shift;
    }

    public void algorithm() {
        double[][] simplexPoints = calculateSimplexPoints();
        double check = 1.0;
        int h = -1; int l = -1;
        double[] xC = new double[x0.length];
        int n = 0;
        do {
            n++;
            for (int i = 0; i < x0.length; i++) {
                int[] hl = calculateMinMax(simplexPoints);
                h = hl[0]; l = hl[1];
                //System.out.println(Arrays.toString(simplexPoints[l]));
                xC = getCentroid(simplexPoints, h);
                //System.out.println(Arrays.toString(xC));
                double[] xR = reflection(xC, simplexPoints[h]);
                if(f.value(xR) < f.value(simplexPoints[l])) {
                    double[] xE = expansion(xC, xR);
                    if(f.value(xE) < f.value(simplexPoints[l])) for(int k = 0; k < x0.length; k++) simplexPoints[h][k] = xE[k];
                    else for (int k = 0; k < x0.length; k++) simplexPoints[h][k] = xR[k];
                } else {
                    for(int j = 0; j <= x0.length; j++) {
                        if(j != h) {
                            if(f.value(xR) > f.value(simplexPoints[j])) {
                                if(f.value(xR) < f.value(simplexPoints[h])) for (int k = 0; k < x0.length; k++) simplexPoints[h][k] = xR[k];
                                double[] xK = contraction(xC, simplexPoints[h]);
                                if(f.value(xK) < f.value(simplexPoints[h])) for (int k = 0; k < x0.length; k++) simplexPoints[h][k] = xK[k];
                                else simplexPoints = shiftToBest(simplexPoints, l);
                            } else for (int k = 0; k < x0.length; k++) simplexPoints[h][k] = xR[k];
                        }
                    }
                }
            }
        check = calculateError(simplexPoints, xC);
        } while(check > epsilon);
        this.noi = n;
        for (int i = 0; i < solution.length; i++) {
            this.solution[i] = simplexPoints[l][i];
        }
    }

    private double calculateError(double[][] simplexPoints, double[] xC) {
        double sum = 0.0;
        int n = 0;
        for(int i = 0; i < simplexPoints.length; i++) {
            sum += Math.pow(f.value(simplexPoints[i]) - f.value(xC), 2);
            n++;
        }
        double result = sum / n;
        //System.out.println(Arrays.toString(simplexPoints[0]));
        return Math.sqrt(result);
    }

    private double[][] shiftToBest(double[][] simplexPoints, int l) {
        double[][] shiftedSimplexPoints = new double[simplexPoints.length][x0.length];
        double[] b = new double[x0.length];
        for (int i = 0; i < x0.length; i++) b[i] = simplexPoints[l][i];
        //System.out.println("Best solution " + Arrays.toString(b));
        for (int i = 0; i < simplexPoints.length; i++) {
            for (int j = 0; j < x0.length; j++) shiftedSimplexPoints[i][j] = sigma * (simplexPoints[i][j] + b[j]);
        }
        return shiftedSimplexPoints;
    }

    private double[] contraction(double[] xC, double[] x) {
        double[] xK = new double[x0.length];
        for (int i = 0; i < x0.length; i++) {
            xK[i] = (1 - beta) * xC[i] + beta * x[i];
        }
        return xK;
    }

    private double[] expansion(double[] xC, double[] xR) {
        double[] xE = new double[x0.length];
        for (int i = 0; i < x0.length; i++) {
            xE[i] = (1 - gamma) * xC[i] + gamma * xR[i];
        }
        return xE;
    }

    private double[] reflection(double[] xC, double[] x) {
        double[] xR = new double[x0.length];
        for (int i = 0; i < x0.length; i++) {
            xR[i] = (1 + alpha) * xC[i] - alpha * x[i];
        }
        return xR;
    }

    private double[] getCentroid(double[][] simplexPoints, int h) {
        double[] xC = new double[x0.length];
        double sum = 0.0;
        int n = 0;
        for (int i = 0; i < x0.length; i++) {
            for (int j = 0; j < simplexPoints.length; j++) {
                if (j != h) {
                    sum += simplexPoints[j][i];
                    n++;
                }
            }
            xC[i] = sum / n;
            sum = 0.0;
            n = 0;
        }
        return xC;
    }

    private int[] calculateMinMax(double[][] simplexPoints) {
        int[] hl = new int[2];
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.MAX_VALUE;
        for(int i = 0; i < simplexPoints.length; i++) {
            double v = f.value(simplexPoints[i]);
            if (v > max) max = v;
            if (v < min) min = v;
        }
        for(int i = 0; i < simplexPoints.length; i++) {
            if(max == f.value(simplexPoints[i])) hl[0] = i;
            if(min == f.value(simplexPoints[i])) hl[1] = i;
        }
        return hl;
    }

    private double[][] calculateSimplexPoints() {
        double[][] simplexPoints = new double[x0.length+1][x0.length];
        for (int k = 0; k < x0.length; k++) {
            simplexPoints[0][k] = x0[k];
        }
        int n = 0;
        for(int i = 0; i < simplexPoints.length; i++) {
            for (int j = 0; j < x0.length; j++) {
                simplexPoints[i][j] = x0[j];
                if (n == j) simplexPoints[i][j] += shift;
            }
            n++;
        }
        return simplexPoints;
    }

    @Override
    public String toString() {
        return  " --- Nelder-Mead algorithm --- \nMinimum in " + Arrays.toString(solution) +
                "\nValue of function in minimum " + f.value(solution);
    }

    public String numberOfIterations() {
        return Integer.toString(this.noi);
    }
}
