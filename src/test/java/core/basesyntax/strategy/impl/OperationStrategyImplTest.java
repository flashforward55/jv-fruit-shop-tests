package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.impl.BalanceOperationHandler;
import core.basesyntax.service.impl.SupplyOperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy strategy;
    private OperationHandler balanceHandler;
    private OperationHandler supplyHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceOperationHandler();
        supplyHandler = new SupplyOperationHandler();

        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, balanceHandler);
        handlerMap.put(FruitTransaction.Operation.SUPPLY, supplyHandler);

        strategy = new OperationStrategyImpl(handlerMap);
    }

    @Test
    void get_existingOperation_returnsCorrectHandler() {
        OperationHandler result = strategy.get(FruitTransaction.Operation.BALANCE);

        assertEquals(balanceHandler, result);
        assertNotNull(result);
    }

    @Test
    void get_anotherExistingOperation_returnsCorrectHandler() {
        OperationHandler result = strategy.get(FruitTransaction.Operation.SUPPLY);

        assertEquals(supplyHandler, result);
        assertNotNull(result);
    }

    @Test
    void get_nonExistingOperation_returnsNull() {
        OperationHandler result = strategy.get(FruitTransaction.Operation.PURCHASE);

        assertNull(result);
    }

    @Test
    void get_allConfiguredOperations_returnsHandlers() {
        assertNotNull(strategy.get(FruitTransaction.Operation.BALANCE));
        assertNotNull(strategy.get(FruitTransaction.Operation.SUPPLY));
        assertNull(strategy.get(FruitTransaction.Operation.PURCHASE));
        assertNull(strategy.get(FruitTransaction.Operation.RETURN));
    }
}
