import static java.util.Arrays.stream;
import java.util.Scanner;
import static java.util.stream.IntStream.range;


public class LUP {
    static double [][] Create(double[][] Matrix){

        System.out.println("Введіть кількість невідомих");
        Scanner input = new Scanner ( System.in );
        int num = 0;
        while(true){
            if(input.hasNextInt()){
                num = input.nextInt();
                break;
            }
            else{
                System.out.println("Ви ввели не число!");
                input.next();
            }
        }

        Matrix = new double[num][(num+1)];
        System.out.println("Введіть розширену матрицю:");
        for(int i = 0; i < num; i++)
        {
            System.out.println( (1 + i) + "Ряд");
            for(int j = 0; j < (num+1); j++)
            {
                while(true)
                {
                    if(input.hasNextInt())
                    {
                        Matrix[i][j] = input.nextInt();
                        break;
                    }
                    else
                    {
                        System.out.println("Ви ввели не число!");
                        input.next();
                    }
                }
            }
        }



        System.out.println();
        return Matrix;
    }





    static double dotPr(double[] a, double[] b) {
        return range(0, a.length).mapToDouble(i -> a[i] * b[i]).sum();
    }

    static double[][] matrixMul(double[][] A, double[][] B) {//  множення матриць
        double[][] result = new double[A.length][B[0].length];
        double[] aux = new double[B.length];

        for (int j = 0; j < B[0].length; j++) {

            for (int k = 0; k < B.length; k++)
                aux[k] = B[k][j];

            for (int i = 0; i < A.length; i++)
                result[i][j] = dotPr(A[i], aux);
        }
        return result;
    }

    static double[][] pivotize(double[][] m) { //P - масив індексів, в вигляді матриці
        int n = m.length;
        double[][] id = range(0, n).mapToObj(j -> range(0, n)
                        .mapToDouble(i -> i == j ? 1 : 0).toArray())
                .toArray(double[][]::new);

        for (int i = 0; i < n; i++) {
            double maxm = m[i][i];
            int row = i;
            for (int j = i; j < n; j++)
                if (m[j][i] > maxm) {
                    maxm = m[j][i];
                    row = j;
                }

            if (i != row) {
                double[] tmp = id[i];
                id[i] = id[row];
                id[row] = tmp;
            }
        }
        return id;
    }

    static double[][][] lu(double[][] A) {
        int n = A.length;

        double[][] L = new double[n][n];
        double[][] U = new double[n][n];
        double[][] P = pivotize(A);
        double[][] A2 = matrixMul(P, A);
        double[] x = new double[n];



        for (int j = 0; j < n; j++) {
            L[j][j] = 1;
            for (int i = 0; i < j + 1; i++) {
                double s1 = 0;
                for (int k = 0; k < i; k++)
                    s1 += U[k][j] * L[i][k];
                U[i][j] = A2[i][j] - s1;
            }
            for (int i = j; i < n; i++) {
                double s2 = 0;
                for (int k = 0; k < j; k++)
                    s2 += U[k][j] * L[i][k];
                L[i][j] = (A2[i][j] - s2) / U[j][j];
            }
        }


        x = calculate(A);
        for(int i = 0; i < x.length; i++){
            System.out.printf("X%d = %.2f \n",(1 + i), x[i]);
        }
        System.out.println("");

        return new double[][][]{L, U, P};
    }

    static void print(double[][] m) {
        stream(m).forEach(a -> {
            stream(a).forEach(n -> System.out.printf( "%5.1f ", n));
            System.out.println();
        });
        System.out.println();
    }


    public static double[] calculate(double[][] array) {// Окремий метод для підрахунку рішень

        int[] p = new int[array.length];
        for (int i = 0; i < p.length; i++) {
            p[i] = i;
        }

        //факторизація матриці A
        for (int k = 0; k < array[0].length - 1; k++) {
            double max = 0;
            int numberRowForReplace = k;
            for (int j = k; j < array.length; j++) {
                if (Math.abs(array[j][k]) > max) {
                    numberRowForReplace = j;
                    max = Math.abs(array[j][k]);
                }
            }
            if (max == 0) {
                throw new IllegalArgumentException("матрица не должна быть вырожденной");
            }

            //міняємо місцями номера строк, які будуть переставлені
            int value = p[k];
            p[k] = p[numberRowForReplace];
            p[numberRowForReplace] = value;

            //переставляємо відповідні строки
            double[] row = array[k];
            array[k] = array[numberRowForReplace];
            array[numberRowForReplace] = row;

            //пряма підстановка
            for (int i = k + 1; i < array.length; i++) {
                //ділимо елементи нижче головної діагоналі на ведущий
                array[i][k] = array[i][k] / array[k][k];
                for (int j = k + 1; j < array.length; j++) {
                    //віднімаємо елементи поточної k-строки помножені на коефіцієнт із елементів строк нижче її
                    array[i][j] = array[i][j] - array[i][k] * array[k][j];
                }
            }
            System.out.println();
        }

        // прямая підстановка
        // вичеслення y системи Ly=Pb
        double[] y = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            double value = 0;
            for (int j = 0; j < i; j++) {
                value += array[i][j] * y[j];
            }
            y[i] = array[i][array[0].length - 1] - value;
        }


        // зворотня підстановка
        // обчислення x системи Ux=y

        double[] x = new double[array.length];
        for (int i = array.length - 1; i >= 0; i--) {
            double value = 0;
            for (int j = i + 1; j < array.length; j++) {
                value += array[i][j] * x[j];
            }
            x[i] = (y[i] - value) / array[i][i];
        }

        return x;
    }
}
