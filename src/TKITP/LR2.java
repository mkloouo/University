package TKITP;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

class LR2 {

    private String fileName;
    private TreeMap<Integer, String[]> data;

    LR2(BufferedReader input) throws IOException {
        System.out.print("Enter file name: ");
        fileName = input.readLine();
        validateFile();
        showFileContents();
    }

    private void showFileContents() {
        if (data != null) {
            String format = "%-10.10s %-20.20s %-20.20s %-10.10s\n";
            System.out.printf(format, "ID", "Class", "Name", "Cost");
            for (Map.Entry<Integer, String[]> entry: data.entrySet()) {
                String[] value = entry.getValue();
                System.out.printf(format, entry.getKey().toString(), value[0],
                        value[1],
                        value[2]);
            }
        }
    }

    private void validateFile() {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(fileName));
            String line;
            String[] lineSplitted;
            int registerNumber;
            String[] otherData;
            data = new TreeMap<>();
            while ((line = fileReader.readLine()) != null && !("".equals(line))) {
                lineSplitted = line.split("\\s+");
                registerNumber = Integer.parseInt(lineSplitted[0].replaceAll("\\s+",""));
                otherData = (lineSplitted.length == 4)
                        ? Arrays.copyOfRange(lineSplitted, 1, 4)
                        : null;
                if (otherData == null)
                    throw new InvalidDataException("Invalid fields: " + line);
                else if (data.containsKey(registerNumber))
                    throw new InvalidDataException("Not unique key: " + registerNumber);
                else if (!(otherData[2].matches("-?[0-9]+(\\.[0-9]+)?")))
                    throw new InvalidDataException("Invalid price field: " + otherData[2]);
                data.put(registerNumber, otherData);
            }
        } catch (NumberFormatException ex) {
            System.out.println("Register number field contains invalid symbols: " + ex.getLocalizedMessage().split(":\\s+")[1]);
            data = null;
        } catch (InvalidDataException ex) {
            System.out.println(ex.getLocalizedMessage());
            data = null;
        } catch (FileNotFoundException ex) {
            System.out.println("Error: " + "File not found.");
            data = null;
        } catch (IOException ex) {
            System.out.println("Error: " + "I/O Exception.");
            data = null;
        }
    }

    static class InvalidDataException extends Exception {
        InvalidDataException(String message) {
            super(message);
        }
    }

}
