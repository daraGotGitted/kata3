import ulpgc.software.FactManager;

import java.io.IOException;

public class Main {
    public static void main() throws IOException, InterruptedException {
        System.out.println(FactManager.getFacts(FactManager.enumType.RANDOM, 5));
    }
}
