import software.ulpgc.JSONSerializer;
import software.ulpgc.LaptopLoaderFromFile;

public class Main {
    void main() {
        LaptopLoaderFromFile fileLoader = new LaptopLoaderFromFile("laptops.csv");

        System.out.printf("[From file] Laptop 0: %s, %s, %,2f\n",
                fileLoader.getManufacturer(0),
                fileLoader.getModel(0),
                fileLoader.getPrice(0));

        JSONSerializer.exportJSON(fileLoader.getAllLaptop(), "./test.json");

        String jsonString = JSONSerializer.serializeLaptopList(fileLoader.getAllLaptop());

    }
}
