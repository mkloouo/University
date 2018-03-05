import Data.PromptStrings;
import Data.UniTasksStrings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UniTasks {

    public static void main(String[] args) throws IOException {
        System.out.println(UniTasksStrings.GREET_MSG);
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        handleUserInput(input);
        input.close();
        System.out.println("Bye.");
    }

    private static void handleUserInput(BufferedReader input) throws IOException {
        String choice;
        while (true) {
            System.out.print(String.format(PromptStrings.CHOOSE_ONE_MSG, "subjects")
                    + UniTasksStrings.CHOOSE_SUBJ_MSG
                    + PromptStrings.EXIT_MSG
                    + PromptStrings.YOUR_CHOICE_MSG);
            choice = input.readLine();
            if (choice.matches("(1|[sS][kK][sS])"))
                SKS.InputHandler.handleSKS(input);
            else if (choice.matches("(2|[Tt][kK][iI][Tt][Pp])"))
                TKITP.InputHandler.handleTKITP(input);
            else if (choice.matches("(q(uit)?|e(xit)?)"))
                return;
            else
                System.out.println(PromptStrings.NO_CHOICE_MSG);
        }
    }
}
