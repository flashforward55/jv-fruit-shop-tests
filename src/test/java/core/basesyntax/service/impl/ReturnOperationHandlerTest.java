package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ReturnOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void apply_existingFruit_addsQuantity() {
        Storage.getStorage().put("apple", 100);
        FruitTransaction transaction = new FruitTransaction("apple",
                FruitTransaction.Operation.RETURN, 20);

        handler.apply(transaction);

        assertEquals(120, Storage.getStorage().get("apple"));
    }

    @Test
    void apply_newFruit_createsEntry() {
        FruitTransaction transaction = new FruitTransaction("apple",
                FruitTransaction.Operation.RETURN, 20);

        handler.apply(transaction);

        assertEquals(20, Storage.getStorage().get("apple"));
    }

    @Test
    void apply_zeroQuantity_noChange() {
        Storage.getStorage().put("apple", 100);
        FruitTransaction transaction = new FruitTransaction("apple",
                FruitTransaction.Operation.RETURN, 0);

        handler.apply(transaction);

        assertEquals(100, Storage.getStorage().get("apple"));
    }
}

