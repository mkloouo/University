package SKS;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class APC {

    APC(BufferedReader input) throws IOException
    {
        System.out.print("Enter data: ");
        List<String> strings = new ArrayList<>();
        String temp;
        while ((temp = input.readLine()) != null && !("".equals(temp)))
            Collections.addAll(strings, temp.trim().split("\\s+"));
        RangCounter obj = new RangCounter(strings);
        obj.calculateRang();
        obj.getRang();
    }
}
