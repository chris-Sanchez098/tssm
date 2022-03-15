package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayTest {
    Pay pay;
    Pay pay2;
    Pay pay3;
    @BeforeEach
    public void init(){
        pay = new Pay(50, 5.0, 2.0, 15.0, false,
                34990, 6.0 , 4.0, 16.0, 100, 100);
        pay2 = new Pay(50, 5.0, 2.0, 15.0, true,
                34990, 1.0 , 1.0, 1.0, 50, 50);
        pay3 = new Pay(50, 5.0, 2.0, 15.0, true,
                34990, 1.0 , 1.0, 1.0, 50, 100);
    }

    @Test
    @DisplayName(value = "Get tests")
    public void getTest(){
        assertAll("several gets",
                () -> assertEquals(34990.00, pay3.getPrice()),
                () -> assertEquals(0.00, pay3.getExtraMin()),
                () -> assertEquals(0.00, pay3.getExtraGb()),
                () -> assertEquals(6648.1, pay3.getTaxes()),
                () -> assertEquals(41638.1, pay3.getTotal()),
                () -> assertEquals(34990.00, pay2.getPrice()),
                () -> assertEquals(0.00, pay2.getExtraMin()),
                () -> assertEquals(0.00, pay2.getExtraGb()),
                () -> assertEquals(6648.1, pay2.getTaxes()),
                () -> assertEquals(41638.1, pay2.getTotal()),
                () -> assertEquals(34990.00, pay.getPrice()),
                () -> assertEquals(34990.00, pay.getExtraMin()),
                () -> assertEquals(52485.00, pay.getExtraGb()),
                () -> assertEquals(23268.35, pay.getTaxes()),
                () -> assertEquals(145733.35, pay.getTotal())
                );
    }

}
