import ulpgc.software.FactManager;

import java.io.IOException;

public class Main {
    public static void main() throws IOException, InterruptedException {
        System.out.println(FactManager.getFacts(FactManager.enumType.RANDOM, 5));
        System.out.printf("Random fact: %s \nFact of the day: %s \n",
                FactManager.getFact(FactManager.enumType.RANDOM).text(),
                FactManager.getFact(FactManager.enumType.TODAY).text());

    }
}
