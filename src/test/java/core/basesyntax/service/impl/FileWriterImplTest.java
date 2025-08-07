package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileWriterImplTest {
    @TempDir
    private Path tempDir;

    private FileWriter fileWriter;

    @BeforeEach
    void setUp() {
        fileWriter = new FileWriterImpl();
    }

    @Test
    void write_validReport_ok() throws IOException {
        Path testFile = tempDir.resolve("output.csv");
        String report = "fruit,quantity"
                + System.lineSeparator()
                + "apple,100"
                + System.lineSeparator()
                + "banana,50";

        fileWriter.write(report, testFile.toString());

        List<String> writtenLines = Files.readAllLines(testFile);
        assertEquals(3, writtenLines.size());
        assertEquals("fruit,quantity", writtenLines.get(0));
        assertEquals("apple,100", writtenLines.get(1));
        assertEquals("banana,50", writtenLines.get(2));
    }

    @Test
    void write_singleLineReport_ok() throws IOException {
        Path testFile = tempDir.resolve("single.csv");
        String report = "fruit,quantity";

        fileWriter.write(report, testFile.toString());

        List<String> writtenLines = Files.readAllLines(testFile);
        assertEquals(1, writtenLines.size());
        assertEquals("fruit,quantity", writtenLines.get(0));
    }

    @Test
    void write_invalidPath_throwsRuntimeException() {
        String invalidPath = "/invalid/path/file.csv";
        String report = "test";

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileWriter.write(report, invalidPath));

        assertTrue(exception.getMessage().contains("Cannot write file"));
    }
}
