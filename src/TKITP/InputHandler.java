package TKITP;

import Data.PromptStrings;
import Data.TKITPStrings;

import java.io.BufferedReader;
import java.io.IOException;

public class InputHandler {
    public static void handleTKITP(BufferedReader input) throws IOException {
        String choice;
        while (true) {
            System.out.print(String.format(PromptStrings.CHOOSE_ONE_MSG, "tasks")
                    + TKITPStrings.CHOOSE_TASK_MSG
                    + PromptStrings.EXIT_MSG
                    + PromptStrings.YOUR_CHOICE_MSG);
            choice = input.readLine();
            if (choice.matches("([lL][rR])?1"))
                new LR1(input);
            else if (choice.matches("([lL][rR])?2"))
                new LR2(input);
            else if (choice.matches("([lL][rR])?4"))
                new LR4(input);
            else if (choice.matches("(e(xit)?|q(uit)?)"))
                return;
            else
                System.out.println(PromptStrings.NO_CHOICE_MSG);
        }
    }

}
