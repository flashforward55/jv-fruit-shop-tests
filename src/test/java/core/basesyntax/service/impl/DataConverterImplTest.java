package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DataConverterImplTest {
    private DataConverter dataConverter;

    @BeforeEach
    void setUp() {
        dataConverter = new DataConverterImpl();
    }

    @Test
    void convertToTransaction_validData_ok() {
        List<String> data = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,100",
                "s,banana,50",
                "p,apple,20",
                "r,banana,10"
        );

        List<FruitTransaction> result = dataConverter.convertToTransaction(data);

        assertEquals(4, result.size());

        FruitTransaction first = result.get(0);
        assertEquals("apple", first.getFruit());
        assertEquals(FruitTransaction.Operation.BALANCE, first.getOperation());
        assertEquals(100, first.getQuantity());

        FruitTransaction last = result.get(3);
        assertEquals("banana", last.getFruit());
        assertEquals(FruitTransaction.Operation.RETURN, last.getOperation());
        assertEquals(10, last.getQuantity());
    }

    @Test
    void convertToTransaction_emptyDataWithHeader_ok() {
        List<String> data = Arrays.asList("type,fruit,quantity");

        List<FruitTransaction> result = dataConverter.convertToTransaction(data);

        assertTrue(result.isEmpty());
    }

    @Test
    void convertToTransaction_invalidOperationCode_throwsException() {
        List<String> data = Arrays.asList(
                "type,fruit,quantity",
                "x,apple,100"
        );

        assertThrows(IllegalArgumentException.class,
                () -> dataConverter.convertToTransaction(data));
    }

    @Test
    void convertToTransaction_invalidNumberFormat_throwsException() {
        List<String> data = Arrays.asList(
                "type,fruit,quantity",
                "b,apple,abc"
        );

        assertThrows(NumberFormatException.class,
                () -> dataConverter.convertToTransaction(data));
    }
}

