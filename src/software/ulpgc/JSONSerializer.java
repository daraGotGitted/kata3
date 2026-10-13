package software.ulpgc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class JSONSerializer {
    static private String metadata(int count) {
        return String.format(
                "\"Metadata\":{\"date\":\"%s\",\"count\":\"%d\"}",
                LocalDate.now(),
                count
        );
    }

    static private String serializeLaptop(Laptop l) {
        return String.format("{\"Manufacturer\":\"%s\",\"Model\":\"%s\",\"Price\":\"%.2f\"}", l.manufacturer, l.model_name, l.price);
    }

    static public String serializeLaptopList(List<Laptop> ll) {
        String res = "{\"Laptops\":[";
        for (Laptop laptop : ll) {
            res += serializeLaptop(laptop) + ",";
        }
        char[] rc = res.toCharArray();
        rc[res.length()-1] = ']';
        return String.valueOf(rc) + ',' + metadata(ll.size()) + '}';
    }

    static public int exportJSON(List<Laptop> ll, String path) {
        File f = new File(path);
        try {
            if(f.createNewFile()) {
                OutputStream os = new FileOutputStream(f);
                ByteBuffer b = ByteBuffer.wrap(
                        serializeLaptopList(ll).getBytes(StandardCharsets.UTF_8)
                );
                os.write(b.array());
            } else throw new FileAlreadyExistsException(path);
        } catch (IOException e) {
            return -1;
        }
        return 0;
    }
}
