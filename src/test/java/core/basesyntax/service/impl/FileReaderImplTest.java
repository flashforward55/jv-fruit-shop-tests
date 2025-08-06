package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.service.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileReaderImplTest {
    private FileReader fileReader;

    @TempDir
    private Path tempDir;

    @BeforeEach
    void setUp() {
        fileReader = new FileReaderImpl();
    }

    @Test
    void read_existingFile_ok() throws IOException {
        Path testFile = tempDir.resolve("test.txt");
        List<String> content = Arrays.asList("line1", "line2", "line3");
        Files.write(testFile, content);

        List<String> result = fileReader.read(testFile.toString());

        assertEquals(content, result);
    }

    @Test
    void read_emptyFile_ok() throws IOException {
        Path testFile = tempDir.resolve("empty.txt");
        Files.createFile(testFile);

        List<String> result = fileReader.read(testFile.toString());

        assertTrue(result.isEmpty());
    }

    @Test
    void read_nonExistentFile_throwsRuntimeException() {
        String nonExistentFile = tempDir.resolve("nonexistent.txt").toString();

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fileReader.read(nonExistentFile));

        assertTrue(exception.getMessage().contains("Cannot read file"));
    }
}
