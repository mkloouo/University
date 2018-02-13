package SKS;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class APC {

    APC(BufferedReader input) throws IOException
    {
        System.out.print("Enter data: ");
        List<String> strings = new ArrayList<>();
        String temp;
        while ((temp = input.readLine()) != null && !("".equals(temp)))
            Collections.addAll(strings, temp.trim().split("\\s+"));
        OperationString obj = new OperationString(strings);
        obj.calculateRank();
        obj.getRang();
    }

    static class OperationString {

        private final List<Integer> listOfOperations;
        private final ArrayList<Integer> listOfClosedOperations = new ArrayList<>();
        private final HashMap<Integer, Integer> mapOfCodeAndRank = new HashMap<>();

        OperationString(List<String> words) {
            listOfOperations = new ArrayList<>();
            for (String word : words) {
                int temp = Integer.parseInt(word);
                listOfOperations.add(temp);
            }
        }

        void calculateRank() {
            ArrayList<Integer> tempList = new ArrayList<>();
            int open = -1, close = -1;
            for (int i = 0; i < listOfOperations.size(); i++) {
                if (listOfOperations.get(i) != 2000 && listOfOperations.get(i) != 3000)
                    tempList.add(listOfOperations.get(i));
                if (listOfOperations.get(i) == 2000)
                    open = i;
                if (listOfOperations.get(i) == 3000)
                    close = i;
                if (open != -1 && close != -1)
                    for (Integer elem : tempList) {
                        if (elem == 4001)
                            mapOfCodeAndRank.put(4001, 2);
                        else if (elem > 4000 && elem < 5000) {
                            listOfClosedOperations.sort(Collections.reverseOrder());
                            mapOfCodeAndRank.put(elem, listOfClosedOperations.get(0) + 1);
                            listOfClosedOperations.clear();
                            open = -1;
                            close = -1;
                        } else if (elem > 5000 && elem < 6000)
                            listOfClosedOperations.add(mapOfCodeAndRank.get(elem - 1000));
                    }
            }
        }

        void getRang() {
            mapOfCodeAndRank.forEach((key, value) -> System.out.printf("Operation number:%1$3d Rank:%2$3d\n", key-4000, value));
        }

    }
}
