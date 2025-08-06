package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new SupplyOperationHandler();
        Storage.getStorage().clear();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void apply_newFruit_addsQuantity() {
        FruitTransaction transaction = new FruitTransaction("apple",
                FruitTransaction.Operation.SUPPLY, 50);

        handler.apply(transaction);

        assertEquals(50, Storage.getStorage().get("apple"));
    }

    @Test
    void apply_existingFruit_addsToExisting() {
        Storage.getStorage().put("apple", 100);
        FruitTransaction transaction = new FruitTransaction("apple",
                FruitTransaction.Operation.SUPPLY, 50);

        handler.apply(transaction);

        assertEquals(150, Storage.getStorage().get("apple"));
    }

    @Test
    void apply_zeroQuantity_noChange() {
        Storage.getStorage().put("apple", 100);
        FruitTransaction transaction = new FruitTransaction("apple",
                FruitTransaction.Operation.SUPPLY, 0);

        handler.apply(transaction);

        assertEquals(100, Storage.getStorage().get("apple"));
    }
}

