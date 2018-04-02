package SKS;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

class Sole {

    private double[][] arr;
    private ArrayList<String> lines;

    Sole(BufferedReader input) throws IOException {
        System.out.print("Enter data: ");
        lines = new ArrayList<>();
        String temp;
        while ((temp = input.readLine()) != null && !("".equals(temp)))
            lines.add(temp.replaceAll(";", ""));
        try {
            fillArr();
            straightCourseAlgorism();
            showVariablesMeanings();
            reverseCourseAlgorism();
            showVariablesMeanings();
            fullResult();
        } catch (Invalid_SLAE ex) {
            System.out.println("Invalid SOLE");
        }
    }

    private void fullResult() {
        System.out.println();
        for (double[] anArr : arr)
            for (int j = 0; j < arr[0].length - 1; j++)
                if (anArr[j] != 0 /*ГРАНИЦЫ*/ && (anArr[j] > 0.0005 || anArr[j] < -0.0005)/*ГРАНИЦЫ*/) {
                    double x = anArr[arr[0].length - 1] / anArr[j];
                    System.out.println("x" + (j + 1) + " = " + x);
                }
        System.out.println("Если корней менее чем ожидалось, то корректного решения СЛАР-а не найдено.\nВыведенные корни игнорировать, т.к. процесс был приостановлен не дойдя до успешного завершения");
    }

    private void reverseCourseAlgorism() { // works correct ( probably )
        double multiplier;
        int columIndex = arr[0].length - 2;
        for (int i = arr[0].length - 2; i > 0; i--)
            if (arr[i][columIndex] != 0) {
                for (int k = i - 1; k >= 0; k--) {
                    multiplier = arr[k][columIndex] / arr[i][columIndex] * -1;
                    for (int l = 0; l < arr[0].length; l++)
                        arr[k][l] += (arr[i][l] * multiplier);
                }
                columIndex--;
            }
    }

    private boolean swapLines(int lineNumber, int columNumber) { // works correct
        double[] line;
        boolean result = false;
        for (int i = lineNumber; i < arr.length; i++)
            if (arr[i][columNumber] != 0) {
                line = arr[lineNumber];
                arr[lineNumber] = arr[i];
                arr[i] = line;
                result = true;
                break;
            }

        return result;
    }

    private void straightCourseAlgorism() { // works correct ( probably )
        double multiplier;
        int columIndex = 0;
        for (int i = 0; i < arr[0].length - 2; i++)
            if (arr[i][columIndex] == 0)
                if (swapLines(i, columIndex)) {
                    i--;
                } else {
                    //i--; // added, need test
                    columIndex++;
                }
            else {
                for (int k = i + 1; k < arr.length; k++) {
                    multiplier = arr[k][columIndex] / arr[i][columIndex] * -1;
                    for (int l = 0; l < arr[0].length; l++)
                        arr[k][l] += (arr[i][l] * multiplier);
                }
                columIndex++;
            }
    }

    private void showVariablesMeanings() { // works correct
        for (double[] anArr : arr) {
            for (int j = 0; j < arr[0].length; j++)
                if (j != arr[0].length - 1)
                    if (anArr[j] >= 0)
                        System.out.print("+" + anArr[j] + "*x" + (j + 1) + " ");
                    else
                        System.out.print(anArr[j] + "*x" + (j + 1) + " ");
                else
                    System.out.print(" = " + anArr[j] + ";");
            System.out.println();
        }
    }

    private void fillArr() throws Invalid_SLAE {
        arr = new double[lines.size()][(lines.get(0).split("\\s+")
                .length + 1) / 2];
        int linesCount = 0;
        int numberCount = 0;
        for (String line : lines) {
            String[] elements = line.split("\\s+");

            for (int i = 0; i < elements.length; i++)
                if (i % 2 == 0) {
                    arr[linesCount][numberCount] = Double.parseDouble(
                            elements[i].split("\\*")[0]);

                    if (i != 0 && i != elements.length - 1)
                        switch (elements[i - 1]) {
                            case "-":
                                arr[linesCount][numberCount] *= -1;
                        }

                    numberCount++;
                }

            linesCount++;
            numberCount = 0;
        }
        checkForExceptions();
    }

    private void checkForExceptions() throws Invalid_SLAE {
        int line_count = lines.size();
        int colum_count = (lines.get(0).split("\\s+").length + 1) / 2;

        boolean lineOfZero = true;
        for (double[] anArr : arr) {
            for (double d : anArr) {
                if (d != 0) {
                    lineOfZero = false;
                    break;
                }
            }
            if (lineOfZero) {
                line_count--;
            }
        }

        boolean columsOfZero = true;
        for (int i = 0; i < arr[0].length; i++) {
            for (double[] anArr : arr) {
                if (anArr[i] != 0) {
                    columsOfZero = false;
                    break;
                }
            }
            if (columsOfZero) {
                colum_count--;
            }
        }

        if (colum_count - 1 > line_count)
            throw new Invalid_SLAE("Количество неизвестных больше количества уравнений, "
                    + "так быть не должно\nотредактируйте файл со СЛАР-ом");
    }

    static class Invalid_SLAE extends Exception {
        private String message;

        Invalid_SLAE(String message) {
            this.message = message;
        }

        public String getMessage() {
            return this.message;
        }
    }

}
