package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void operation_fromCode_validCodes_ok() {
        assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void operation_fromCode_invalidCode_throwsException() {
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(""));
        assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(null));
    }

    @Test
    void operation_getCode_returnsCorrectCodes() {
        assertEquals("b", FruitTransaction.Operation.BALANCE.getCode());
        assertEquals("s", FruitTransaction.Operation.SUPPLY.getCode());
        assertEquals("p", FruitTransaction.Operation.PURCHASE.getCode());
        assertEquals("r", FruitTransaction.Operation.RETURN.getCode());
    }

    @Test
    void fruitTransaction_constructor_setsFieldsCorrectly() {
        FruitTransaction transaction = new FruitTransaction("apple",
                FruitTransaction.Operation.BALANCE, 100);

        assertEquals("apple", transaction.getFruit());
        assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        assertEquals(100, transaction.getQuantity());
    }
}

