package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorImplTest {
    private ReportGenerator reportGenerator;

    @BeforeEach
    void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void getReport_withData_ok() {
        Storage.getStorage().put("apple", 100);
        Storage.getStorage().put("banana", 50);

        String result = reportGenerator.getReport();

        assertTrue(result.startsWith("fruit,quantity"));
        assertTrue(result.contains("apple,100"));
        assertTrue(result.contains("banana,50"));
        assertFalse(result.endsWith(System.lineSeparator()));
    }

    @Test
    void getReport_emptyStorage_returnsOnlyHeader() {
        String result = reportGenerator.getReport();

        assertEquals("fruit,quantity", result);
    }

    @Test
    void getReport_singleFruit_ok() {
        Storage.getStorage().put("orange", 75);

        String result = reportGenerator.getReport();

        String expected = "fruit,quantity" + System.lineSeparator() + "orange,75";
        assertEquals(expected, result);
    }
}


