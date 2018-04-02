package SKS;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

class MacroGraph {

    private static RangCounter rg;

    MacroGraph(BufferedReader input) throws IOException {
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
        System.out.print("Randomize operations(Y/n): ");
        String answer = input.readLine();

        int operationSize = rg.getMapOfCodeAndRank().size();
        ArrayList<String> operations = new ArrayList<>(operationSize);

        ArrayList<Integer> indexsOfAdd = new ArrayList<>();
        ArrayList<Integer> indexsOfSub = new ArrayList<>();
        ArrayList<Integer> indexsOfMult = new ArrayList<>();
        ArrayList<Integer> indexsOfDev = new ArrayList<>();
        ArrayList<Integer> indexsOfPow = new ArrayList<>();
        if (answer.equalsIgnoreCase("y")) {
            Random random = new Random();
            int randomNumber;
            for (int i = 0; i < operationSize; i++) {
                randomNumber = random.nextInt(5);
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
                    case 4: {
                        symbol = "^";
                        indexsOfPow.add(i + 1);
                        break;
                    }
                }
                operations.add(symbol);
            }
            showOperationsDependency(operations);
        } else {
            System.out.println("Choose operation: + - * /");
            for (int i = 0; i < operationSize; i++) {
                System.out.print("Operation #" + (i + 1) + " ");
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
                    case "^": {
                        indexsOfPow.add(i + 1);
                    }
                }
            }
        }
        System.out.println("Macro for *");
        createMacrosGroups(rg, indexsOfMult);
        System.out.println("Macro for /");
        createMacrosGroups(rg, indexsOfDev);
        System.out.println("Macro for + and -");
        indexsOfAdd.addAll(indexsOfSub);
        createMacrosGroups(rg, indexsOfAdd);
        System.out.println("Macro for ^");
        createMacrosGroups(rg, indexsOfPow);
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
                    groups.replace(rang, groups.get(rang), groups.get(rang) + " " + String.valueOf(listWithIndex.get(j)) + " ");
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
                String[] temp = value.split("\\s+");
                for (String aTemp : temp) {
                    if (!aTemp.equals(""))
                        System.out.print(" " + Integer.parseInt(aTemp) + " ");
                } // + - * / + - * / + + - * + - * / +
                System.out.println();
            }
        }
    }

    private static void createMacrosGroups(RangCounter obj, ArrayList<Integer> indexs) {
        List<Integer> numbers = obj.getListOfOperations();

        ArrayList<StringBuffer> list = new ArrayList<>();
        ArrayList<Integer> find = new ArrayList<>();
        for (int i = 0; i < indexs.size(); i++) {
            StringBuffer buffer1 = new StringBuffer();
            buffer1.append(indexs.get(i)).append(" ");
            int permission = 0;
            if (!find.contains(indexs.get(i) + 5000)) {
                find = new ArrayList<>();
                find.add(indexs.get(i) + 5000);
                if (func(obj, indexs, numbers.indexOf(indexs.get(i) + 4000)))
                    for (int j = 0; j < numbers.size(); j++) {
                        if (find.contains(numbers.get(j)))
                            for (int k = j; k < numbers.size(); k++) {
                                if (numbers.get(k) == 3000)
                                    break;
                                if (indexs.contains(numbers.get(k) - 4000) && !find.contains(numbers.get(k) + 1000)) {
                                    find.add(numbers.get(k) + 1000);
                                    buffer1.append(numbers.get(k) - 4000).append(" ");
                                    for (int l = 0; l < numbers.size(); l++) {
                                        int m;
                                        if (numbers.get(l) == numbers.get(k) + 1000) {
                                            m = l;
                                            while (numbers.get(m) != 2000)
                                                m--;
                                            for (; m < numbers.size(); m++) {
                                                if (numbers.get(m) == 3000)
                                                    break;
                                                if (numbers.get(m) > 4000 && numbers.get(m) < 5000 &&
                                                        !indexs.contains(numbers.get(m) - 4000)) {
                                                    list.add(new StringBuffer().append(buffer1));
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                    permission = 1;
                                    k = j;
                                }
                            }
                    }
                if (permission == 1)
                    list.add(buffer1);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            int a = 0;
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).toString().equals(list.get(j).toString()))
                    a = 1;
            }
            if (a == 0)
                func2(list.get(i), obj);
        }

    }

    private static boolean func(RangCounter obj, ArrayList<Integer> indexs, int k) {
        List<Integer> numbers = obj.getListOfOperations();

        int p = 0;
        for (int i = k - 1; i >= 0; i--) {
            if (numbers.get(i) == 2000)
                break;
            if (numbers.get(i) > 5000 && numbers.get(i) < 6000 && !indexs.contains(numbers.get(i) - 5000)) {
                p = 1;
                break;
            }
        }
        if (numbers.get(k) == 4001)
            p = 1;
        return p != 0;
    }

    private static void func2(StringBuffer buffer, RangCounter obj) {
        String str[] = buffer.toString().split("\\s+");
        List<Integer> numbers = obj.getListOfOperations();

        int perm;
        for (int i = str.length - 1; i > 0; i--) {
            if (!str[i].equals("")) {
                for (int j = 0; j < numbers.size(); j++) {
                    if (numbers.get(j) == Integer.parseInt(str[i]) + 4000) {
                        perm = 0;
                        for (int k = j; k >= 0; k--) {
                            if (numbers.get(k) == 2000)
                                break;
                            if (numbers.get(k) == Integer.parseInt(str[i - 1]) + 5000)
                                perm = 1;
                        }
                        if (perm == 0) {
                            StringBuffer buffer1 = new StringBuffer();
                            for (int k = 0; k < str.length; k++) {
                                if (k != i - 1)
                                    buffer1.append(str[k]).append(" ");
                            }
                            func2(buffer1, obj);
                            return;
                        }
                    }
                }
            }
        }
        System.out.println(buffer.toString());
    }

}
