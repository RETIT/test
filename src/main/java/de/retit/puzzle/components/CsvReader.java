package de.retit.puzzle.components;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CsvReader {

    private static final Logger LOGGER = Logger.getLogger(CsvReader.class.getName());

    private String fileLocation;

    public CsvReader(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public List<String> read() {
        File file = new File(fileLocation);
        if (!file.exists()) {
            throw new IllegalStateException("File does not exist!");
        }

        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading input file file", e);
        }
        return null;
    }
}
