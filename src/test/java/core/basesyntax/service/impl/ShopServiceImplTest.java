package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ShopService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShopServiceImplTest {
    private ShopService shopService;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, core.basesyntax.service.OperationHandler>
                handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperationHandler());

        operationStrategy = new OperationStrategyImpl(handlers);
        shopService = new ShopServiceImpl(operationStrategy);
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void process_multipleTransactions_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction("apple", FruitTransaction.Operation.BALANCE, 100),
                new FruitTransaction("banana", FruitTransaction.Operation.SUPPLY, 50),
                new FruitTransaction("apple", FruitTransaction.Operation.PURCHASE, 20)
        );

        shopService.process(transactions);

        assertEquals(80, Storage.getStorage().get("apple"));
        assertEquals(50, Storage.getStorage().get("banana"));
    }

    @Test
    void process_emptyList_ok() {
        shopService.process(List.of());

        assertTrue(Storage.getStorage().isEmpty());
    }

    @Test
    void process_singleTransaction_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction("orange", FruitTransaction.Operation.BALANCE, 75)
        );

        shopService.process(transactions);

        assertEquals(75, Storage.getStorage().get("orange"));
        assertEquals(1, Storage.getStorage().size());
    }

    @Test
    void process_allOperationTypes_ok() {
        List<FruitTransaction> transactions = List.of(
                new FruitTransaction("apple", FruitTransaction.Operation.BALANCE, 100),
                new FruitTransaction("apple", FruitTransaction.Operation.SUPPLY, 30),
                new FruitTransaction("apple", FruitTransaction.Operation.PURCHASE, 40),
                new FruitTransaction("apple", FruitTransaction.Operation.RETURN, 10)
        );

        shopService.process(transactions);

        assertEquals(100, Storage.getStorage().get("apple"));
    }
}
