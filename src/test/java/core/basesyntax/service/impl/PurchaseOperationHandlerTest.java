package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private OperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new PurchaseOperationHandler();
        Storage.getStorage().clear();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void apply_existingFruit_subtractsQuantity() {
        Storage.getStorage().put("apple", 100);
        FruitTransaction transaction = new FruitTransaction("apple",
                FruitTransaction.Operation.PURCHASE, 30);

        handler.apply(transaction);

        assertEquals(70, Storage.getStorage().get("apple"));
    }

    @Test
    void apply_newFruit_createsNegativeBalance() {
        FruitTransaction transaction = new FruitTransaction("apple",
                FruitTransaction.Operation.PURCHASE, 30);

        handler.apply(transaction);

        assertEquals(-30, Storage.getStorage().get("apple"));
    }

    @Test
    void apply_zeroQuantity_noChange() {
        Storage.getStorage().put("apple", 100);
        FruitTransaction transaction = new FruitTransaction("apple",
                FruitTransaction.Operation.PURCHASE, 0);

        handler.apply(transaction);

        assertEquals(100, Storage.getStorage().get("apple"));
    }
}
