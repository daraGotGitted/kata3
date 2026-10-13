package software.ulpgc;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface LaptopLoader {
    InputStream retrieveStream();

    default List<Laptop> getAllLaptop() {
        InputStream is = retrieveStream();
        try (Reader reader = new InputStreamReader(is)) {
            return reader.readAllLines().stream().skip(1).map((s) -> {
                String[] ss = s.split(",");
                return new Laptop(
                        ss[0],
                        ss[1],
                        Float.parseFloat(ss[2])
                );
            }).toList();
        } catch(IOException e) {
            System.err.println("Couldn't get reader");
            return null;
        }
    }

    default Laptop getLaptop(int id) {
        if(id < 0) throw new IndexOutOfBoundsException();
        InputStream is = retrieveStream();
        try (Reader reader = new InputStreamReader(is)) {
            Optional<String> r = reader.readAllLines().stream().skip(1 + id).findFirst();
            if(r.isPresent()) {
                String[] ss = r.get().split(",");
                return new Laptop(ss[0], ss[1], Float.parseFloat(ss[2]));
            }
            throw new IndexOutOfBoundsException();
        } catch (IOException e) {
            System.err.println("Couldn't get reader");
            return null;
        }
    }

    default String getManufacturer(int id) {
        return getLaptop(id).manufacturer;
    }

    default String getModel(int id) {
        return getLaptop(id).model_name;
    }

    default float getPrice(int id) {
        return getLaptop(id).price;
    }

    default float getPriceMean() {
        InputStream is = retrieveStream();
        try(Reader reader = new InputStreamReader(is)) {
            double[] priceRead = reader.readAllLines().stream().skip(1)
                    .mapToDouble(s -> Double.parseDouble(s.split(",")[2])).toArray();
            return (float) Arrays.stream(priceRead).sum()/priceRead.length;
        } catch (IOException e) {
            System.err.println("Couldn't get reader");
            return -1;
        }
    }

    default int exportData(String path) {
        File f = new File(path);
        try {
            if(f.createNewFile()) {
                OutputStream os = new FileOutputStream(f);
                String sBuffer = "Sample of %d laptops\nMean price: %.2f€";
                ByteBuffer b = ByteBuffer.wrap(
                        String.format(sBuffer, getAllLaptop().size(), getPriceMean()).getBytes(StandardCharsets.UTF_8)
                );
                os.write(b.array());
            } else throw new FileAlreadyExistsException(path);
        } catch (IOException e) {
            return -1;
        }

        return 0;
    }
}