package SKS;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

class Vectorizer {

    private static RangCounter rg;

    Vectorizer(BufferedReader input) throws IOException {
        System.out.print("Enter data: ");
        List<String> strings = new ArrayList<>();
        String temp;
        while ((temp = input.readLine()) != null && !("".equals(temp)))
            Collections.addAll(strings, temp.trim().split("\\s+"));
        rg = new RangCounter(strings);
        rg.calculateRang();
        rg.getRang();
        createOperationsGroup(input);
    }

    private static void createOperationsGroup(BufferedReader input) throws IOException {

        //region initialize list of operations
        System.out.println("Random operations? Y/N");
        String answer = input.readLine();

        int operationSize = rg.getMapOfCodeAndRank().size();
        ArrayList<String> operations = new ArrayList<>(operationSize);

        ArrayList<Integer> indexsOfAdd = new ArrayList<>();
        ArrayList<Integer> indexsOfSub = new ArrayList<>();
        ArrayList<Integer> indexsOfMult = new ArrayList<>();
        ArrayList<Integer> indexsOfDev = new ArrayList<>();

        if (answer.equalsIgnoreCase("y")) {
            Random random = new Random();
            int randomNumber;
            for (int i = 0; i < operationSize; i++) {
                randomNumber = random.nextInt(4);
                String symbol = "";
                switch (randomNumber) {
                    case 0: {
                        symbol = "-";
                        indexsOfSub.add(i + 1);
                        break;
                    }
                    case 1: {
                        symbol = "+";
                        indexsOfAdd.add(i + 1);
                        break;
                    }
                    case 2: {
                        symbol = "/";
                        indexsOfDev.add(i + 1);
                        break;
                    }
                    case 3: {
                        symbol = "*";
                        indexsOfMult.add(i + 1);
                        break;
                    }
                }
                operations.add(symbol);
            }
            showOperationsDependency(operations);
        } else {
            System.out.println("Choose operation: + - * /");
            for (int i = 0; i < operationSize; i++) {
                System.out.print("Operation #" + i + " ");
                String symbol = input.readLine();
                operations.add(symbol);
                switch (symbol) {
                    case "+": {
                        indexsOfAdd.add(i + 1);
                        break;
                    }
                    case "-": {
                        indexsOfSub.add(i + 1);
                        break;
                    }
                    case "*": {
                        indexsOfMult.add(i + 1);
                        break;
                    }
                    case "/": {
                        indexsOfDev.add(i + 1);
                        break;
                    }
                }
            }
        }
        //endregion

        //region find groups
        System.out.println("Groups for \"+\"");
        findAndShowGroupsWith(indexsOfAdd);
        System.out.println("Groups for \"\\\"");
        findAndShowGroupsWith(indexsOfDev);
        System.out.println("Groups for \"*\"");
        findAndShowGroupsWith(indexsOfMult);
        System.out.println("Groups for \"-\"");
        findAndShowGroupsWith(indexsOfSub);
        //endregion

    }

    private static void showOperationsDependency(ArrayList<String> operations) {
        for (int i = 0; i < operations.size(); i++) {
            System.out.println("Operation #" + (i + 1) + " is " + operations.get(i));
        }
    }

    private static void findAndShowGroupsWith(ArrayList<Integer> listWithIndex) {

        HashMap<Integer, Integer> map = rg.getMapOfCodeAndRank();
        ArrayList<Integer> rangs = new ArrayList<>();
        for (Integer aListWithIndex : listWithIndex) {
            rangs.add(map.get(4000 + aListWithIndex));
        }
        HashMap<Integer, String> groups = new HashMap<>();
        for (int i = 0; i < rangs.size(); i++) {
            int rang = rangs.get(i);
            groups.put(rang, String.valueOf(listWithIndex.get(i)));
            for (int j = i + 1; j < rangs.size(); j++) {
                if (rang == rangs.get(j)) {
                    groups.replace(rang, groups.get(rang), groups.get(rang)
                            + " " + String.valueOf(listWithIndex.get(j)) + " ");
                    listWithIndex.remove(j);
                    rangs.remove(j);
                    j--;
                }
            }
        }

        Iterator<Map.Entry<Integer, String>> iter = groups.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, String> entry = iter.next();
            String value = entry.getValue();
            if (!value.contains(" "))
                iter.remove();
            else {
                System.out.print("V ");
                String[] temp = value.trim().split("\\s+");
                for (String aTemp : temp) {
                    System.out.print(" " + Integer.parseInt(aTemp) + " ");
                }
                System.out.println();
            }
        }
    }

}
