package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class UserTest {
    User user;
    User user1;
    @BeforeEach
    public void init(){
        user = new User(1193075514, "Victor Sapuyes", "neveu", "Victor123@","gerente", true);
        user1 = new User(1193075514, "Victor Sapuyes", "neveu", "Victor123@","gerente", true);
    }

    @Test
    @DisplayName(value = "get tests")

    public void getTest(){
        assertAll("several gets",
                () -> assertEquals(1193075514, user.getCc()),
                () -> assertEquals("Victor Sapuyes", user.getName()),
                () -> assertEquals("neveu", user.getUser()),
                () -> assertEquals("Victor123@", user.getPwd()),
                () -> assertEquals("gerente", user.getRol()),
                () -> assertEquals(true, user.getStatus())
                );
    }

    @Test
    @DisplayName(value = "set tests")

    public void setTest(){
        user.setCc(1004675446);
        user.setName("Franklyn Narvaez");
        user.setUser("donal");
        user.setPwd("Fran1234@");
        user.setRol("administrador");
        user.setStatus(false);

        user1.setName("");
        user1.setUser("");
        user1.setPwd("");
        user1.setRol("");

        assertAll("several sets",
                () -> assertEquals(1004675446, user.getCc()),
                () -> assertEquals("Franklyn Narvaez", user.getName()),
                () -> assertEquals("donal", user.getUser()),
                () -> assertEquals("Fran1234@", user.getPwd()),
                () -> assertEquals("administrador", user.getRol()),
                () -> assertEquals(false, user.getStatus()),
                () -> assertEquals("Victor Sapuyes", user1.getName()),
                () -> assertEquals("neveu", user1.getUser()),
                () -> assertEquals("Victor123@", user1.getPwd()),
                () -> assertEquals("gerente", user1.getRol())
        );
    }

    @Test
    @DisplayName(value = "pwd tests")

    public void pwdTests(){
        user1.setPwd("neveu");
        assertAll("several pwd",
                () -> assertEquals(true, user.checkPwd()),
                () -> assertEquals(false, user1.checkPwd()),
                () -> assertEquals(true, user1.pwdEqualUser()),
                () -> assertEquals(false, user.pwdEqualUser())
        );
    }
}
