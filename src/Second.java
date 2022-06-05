import java.util.ArrayList;
public class Second {

    public static ArrayList<double[]> Solve(double[] ArrCoef, double xk, double h) {
        int n = (int) (xk  / h + 1);
        double[] X = new double[n];
        double[] Y = new double[n];

        X[0] = 0;
        Y[0] = 0;

        for (int i = 1; i < n; i++) {
            Y[i] = RK(Y[i - 1], h, ArrCoef);
            X[i] = X[i - 1] + h;
        }

        ArrayList<double[]> res = new ArrayList<>();
        res.add(Y);
        res.add(X);
        return res;
    }

    private static double DdY(double[] ArrCoef, double y) {
        return -(ArrCoef[2] + ArrCoef[1] * y) / ArrCoef[0];
    }

    private static double RK(double yi, double h, double[] ArrCoef) {
        double k1 = h * DdY(ArrCoef, yi);
        return yi + (1.0/6.0 * k1);
    }
}
