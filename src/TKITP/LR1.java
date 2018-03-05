package TKITP;

import java.io.BufferedReader;
import java.io.IOException;

class LR1 {

    private double x;
    private double precision;
    private int n;

    LR1(BufferedReader input) throws IOException {
        System.out.print("Enter x: ");
        x = Double.parseDouble(input.readLine());
        System.out.print("Enter delta: ");
        precision = Double.parseDouble(input.readLine());
        n = 1;
        if (x > 0 && x <= 1 && precision > 0)
            calculate();
        else
            System.out.println("Invalid x or delta");
    }

    private void calculate() {
        double y = Math.log(1 + x);
        double yN = 0;
        do {
            yN += Math.pow(-1, (n + 1)) * (Math.pow(x, n)) / n;
            System.out.printf("y: %f\ny%d: %f\n\n", y, n, yN);
            n++;
        } while (Math.abs(y - yN) > precision);
        System.out.printf("y: %f\ny%d: %f\n", y, n, yN);
    }

}
