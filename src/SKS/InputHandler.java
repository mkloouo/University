package SKS;

import Data.PromptStrings;
import Data.SKSStrings;

import java.io.BufferedReader;
import java.io.IOException;

public class InputHandler {


    public static void handleSKS(BufferedReader input) throws IOException {
        String choice;
        while (true) {
            System.out.print(String.format(PromptStrings.CHOOSE_ONE_MSG, "tasks")
                    + SKSStrings.CHOOSE_TASK_MSG
                    + PromptStrings.EXIT_MSG
                    + PromptStrings.YOUR_CHOICE_MSG);
            choice = input.readLine();
            if (choice.matches("(1|[aA][dD][cC])"))
                new ADC(input);
            else if (choice.matches("(2|[aA][pP][cC])"))
                new APC(input);
            else if (choice.matches("(e(xit)?|q(uit)?)"))
                return;
            else
                System.out.println(PromptStrings.NO_CHOICE_MSG);
        }
    }

}
