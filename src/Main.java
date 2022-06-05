import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner input = new Scanner ( System.in );
        System.out.println("Розрахунок СЛАР по варіанту");

        double [][] Mat ={      {-7,-10,-9,113},
                                {-2,-5,9,-60},
                                {5,1,7,-35},
                                {-2, -7, 2, 8, -47}};

        System.out.println("");
        LUP.print(Mat);
        System.out.println("");

        for (double[][] m : LUP.lu(Mat))
            LUP.print(m);
        System.out.println("2 рівень");

        ArrayList<double[]> result = Second.Solve(new double[]{0.7,7,-100, 0}, 1, 0.05);

        String[] Y = stringFormat(result.get(0));
        String[] X = stringFormat(result.get(1));

        System.out.println("\tX\t\t\tY");

        for(int i = 0; i < X.length; i++) {
            System.out.println("\t" +  X[i] + "\t\t" + Y[i]);

        }
    }
    private static String[] stringFormat(double[] X){
        String[] lines = new String[X.length];

        for(int i = 0; i < X.length; i++){
            String line = String.format("%.5f", X[i]);
            lines[i] = line;
        }

        return lines;
    }
}
