package SKS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

class RangCounter {

    private final List<Integer> listOfOperations;
    private final ArrayList<Integer> listOfClosedOperations = new ArrayList<>();
    private final HashMap<Integer, Integer> mapOfCodeAndRank = new HashMap<>();

    RangCounter(List<String> words) {
        listOfOperations = new ArrayList<>();
        for (String word : words) {
            int temp = Integer.parseInt(word);
            listOfOperations.add(temp);
        }
    }

    public List<Integer> getListOfOperations() {
        return listOfOperations;
    }

    public ArrayList<Integer> getListOfClosedOperations() {
        return listOfClosedOperations;
    }

    HashMap<Integer, Integer> getMapOfCodeAndRank() {
        return mapOfCodeAndRank;
    }

    void calculateRang() {
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
                for (Integer aTempList : tempList) {
                    if (aTempList > 5000 && aTempList < 6000)
                        listOfClosedOperations.add(mapOfCodeAndRank.get(aTempList - 1000));
                    if (aTempList > 4000 && aTempList < 5000)
                        if (aTempList == 4001)
                            mapOfCodeAndRank.put(4001, 2);
                        else {
                            listOfClosedOperations.sort(Collections.reverseOrder());
                            mapOfCodeAndRank.put(aTempList, listOfClosedOperations.get(0) + 1);
                            listOfClosedOperations.clear();
                            open = -1;
                            close = -1;
                        }
                }
        }
    }

    void getRang() {
        mapOfCodeAndRank.forEach((key, value) -> System.out.printf("Operation number:%1$3d Rank:%2$3d\n", key - 4000, value));
    }
}