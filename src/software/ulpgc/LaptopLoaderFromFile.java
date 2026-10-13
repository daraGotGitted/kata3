package software.ulpgc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LaptopLoaderFromFile implements LaptopLoader {
    public String path;
    public LaptopLoaderFromFile(String path) { this.path = path; }

    public InputStream retrieveStream() {
        File f = new File(path);
        try { return new FileInputStream(f); } catch(FileNotFoundException e) { System.err.println("Couldn't find file: " + this.path); }
        return null;
    }
}
