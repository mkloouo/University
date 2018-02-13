package SKS;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

class ADC {

    private static final String PASS_MSG = "Passed";
    private static final String INF_MSG = "Loop";

    private int countOfBO = 0;
    private int countOfLO = 0;

    private String cycleInfo = PASS_MSG;
    private StringBuilder cycleActions = new StringBuilder();
    private TreeMap<Integer, Integer> mapOfBinaryActions;

    ADC(BufferedReader input) {
        ArrayList<Integer> listOfAO = createAndFillList(input);
        if (listOfAO != null) {
            ArrayList<Integer> listOfLO = getListOfLO(listOfAO);
            mapOfBinaryActions = mapBinaryActions(listOfAO, listOfLO);
            showResult(listOfAO, listOfLO);
        }
    }

    private ArrayList<Integer> createAndFillList(BufferedReader input) {
        ArrayList<Integer> list = new ArrayList<>();
        System.out.println("Enter data:");
        try {
            String sequence;
            while (true) {
                if ((sequence = input.readLine()) == null || sequence.equals(""))
                    break;
                for (String word : sequence.split("\\s+"))
                    list.add(Integer.parseInt(word));
            }
        } catch (NumberFormatException | IOException e) {
            System.out.println(e.toString());
            list = null;
        }
        return list;
    }

    private void showAOInSymbols(ArrayList<Integer> list) {
        int i;
        for (Integer num : list) {
            if (num >= 1000 && num <= 1100) {
                i = num % 1000;
                System.out.print("A" + i + " ");
            }
            if (num >= 2000 && num <= 2100) {
                System.out.print("( ");
            }
            if (num >= 3000 && num <= 3100) {
                System.out.print(") ");
                System.out.println();
            }
            if (num >= 4001 && num <= 4100) {
                i = num % 4000;
                System.out.print("OP" + i + " ");
            }
            if (num >= 5001 && num <= 5100) {
                i = num % 5000;
                System.out.print("FO" + i + " ");
            }
            if (num >= 6001 && num <= 6100) {
                i = num % 6000;
                System.out.print("LT" + i + " ");
            }
            if (num >= 7001 && num <= 7100) {
                i = num % 7000;
                System.out.print("LF" + i + " ");
            }
        }
        System.out.println();
    }

    private TreeMap<Integer, Integer> mapBinaryActions(ArrayList<Integer> listOfAO,
                                                       ArrayList<Integer> listOfLO) {
        TreeMap<Integer, Integer> mappedActions = new TreeMap<>();
        for (int i = 0, j = 0; i < listOfAO.size(); i++) {
            if (!listOfLO.contains(listOfAO.get(i)) && listOfAO.get(i) >= 4001 && listOfAO.get(i) <= 4100) {
                mappedActions.put(listOfAO.get(i), j);
                j++;
            }
        }
        return mappedActions;
    }

    private static ArrayList<Integer> getListOfLO(ArrayList<Integer> listOfAO) {
        ArrayList<Integer> listOfLO = new ArrayList<>();
        int indexLO;
        for (Integer operation : listOfAO) {
            if (operation >= 6001 && operation <= 6100) {
                indexLO = operation - 6000;
                listOfLO.add(4000 + indexLO);
            }
        }
        return listOfLO;
    }

    private void countO(ArrayList<Integer> list, ArrayList<Integer> listLO, int x1, int x2, int x3, int x4) {
        ArrayList<Integer> listOfPastO = new ArrayList<>();
        int[] logicMeanings = new int[]{x1, x2, x3, x4};
        int code, indexLO, meaning;
        boolean isLogic;
        for (int i = 0; i < list.size(); i++) {
            code = list.get(i);
            if (code >= 4001 && code <= 4100) {
                isLogic = isLO(listLO, code);
                if (!isLogic) {
                    if (isRepeating(listOfPastO, code)) {
                        cycleInfo = INF_MSG;
                        break;
                    }
                    cycleActions.append((char) (mapOfBinaryActions.get(code) + 65));
                    listOfPastO.add(code);
                    countOfBO++;
                } else {

                    if (isRepeating(listOfPastO, code)) {
                        cycleInfo = INF_MSG;
                        break;
                    }
                    countOfLO++;
                    listOfPastO.add(code);
                    indexLO = code - 4000;

                    meaning = logicMeanings[listLO.indexOf(code)];
                    if (meaning == 1) {
                        i = findO(list, 6000 + indexLO);
                    } else {
                        i = findO(list, 7000 + indexLO);
                    }
                }
            }
        }
    }

    private boolean isLO(ArrayList<Integer> logOperList, int oper) {

        for (Integer logOperation : logOperList) {
            if (oper == logOperation)
                return true;
        }
        return false;
    }

    private int findO(ArrayList<Integer> list, int code) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == code)
                return i;
        }
        return -1;
    }

    private boolean isRepeating(ArrayList<Integer> indexesOfDoneLO, int code) {
        if (!indexesOfDoneLO.isEmpty()) {
            for (Integer index : indexesOfDoneLO) {
                if (index == code)
                    return true;
            }
        }
        return false;
    }

    private void showResultForOneCombination(ArrayList<Integer> listOfAO, ArrayList<Integer> listOfLO,
                                             int x1, int x2, int x3, int x4) {
        countO(listOfAO, listOfLO, x1, x3, x4, x2);

        System.out.printf("|%1$2d|%2$2d|%3$2d|%4$2d| %5$-15s | %6$-13s | %7$-6s |\n",
                x1, x2, x3, x4, cycleActions.toString(),
                (countOfBO + "+" + countOfLO + " logical"),
                cycleInfo);
        countOfBO = 0;
        countOfLO = 0;
        cycleInfo = PASS_MSG;
        cycleActions = new StringBuilder();
    }

    private void delimiter() {
        for (int i = 0; i < 56; i++) {
            System.out.print("-");
            if (i == 55) System.out.println();
        }
    }

    private void showResult(ArrayList<Integer> listOfAO, ArrayList<Integer> listOfLO) {
        delimiter();
        System.out.println("|x1|x2|x3|x4| Actions         | Actions count | Info   |");
        delimiter();
        int i1 = 2, i2 = 4, i3 = 8, i4 = 16;
        while (i4 != 0) {
            showResultForOneCombination(listOfAO, listOfLO,
                    (i1 % 2 == 0) ? 0 : 1,
                    (i2 % 2 == 0) ? 0 : 1,
                    (i3 % 2 == 0) ? 0 : 1,
                    (i4 % 2 == 0) ? 0 : 1);
            delimiter();
            if (--i4 % 2 == 0) {
                if (--i3 % 2 == 0) {
                    if (--i2 % 2 == 0) {
                        --i1;
                    }
                }
            }
        }
    }
}
