package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new BalanceOperationHandler();
        Storage.getStorage().clear();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void apply_newFruit_setsBalance() {
        FruitTransaction transaction = new FruitTransaction("apple",
                FruitTransaction.Operation.BALANCE, 100);

        handler.apply(transaction);

        assertEquals(100, Storage.getStorage().get("apple"));
    }

    @Test
    void apply_existingFruit_replacesBalance() {
        Storage.getStorage().put("apple", 50);
        FruitTransaction transaction = new FruitTransaction("apple",
                FruitTransaction.Operation.BALANCE, 100);

        handler.apply(transaction);

        assertEquals(100, Storage.getStorage().get("apple"));
    }

    @Test
    void apply_zeroQuantity_setsZero() {
        FruitTransaction transaction = new FruitTransaction("apple",
                FruitTransaction.Operation.BALANCE, 0);

        handler.apply(transaction);

        assertEquals(0, Storage.getStorage().get("apple"));
    }
}


