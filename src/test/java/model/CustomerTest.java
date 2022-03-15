package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomerTest {
    Customer customer;
    @BeforeEach
    public void init(){
        customer = new Customer("1004675446","Franklyn","franklin123@gmail.com",1,"Carrea 96 #3b-45","Cali","valle del cauca",
                1,"Natural", 2, "3184226211", 34000, "", "",true,true,10,1,
                "");
    }

    @Test
    @DisplayName(value = "Get phonePlanId")
    public void getTest(){
        assertAll("a get",
                () -> assertEquals("Plan 25 GB", customer.detailsMobilePlan())
        );
    }
}
