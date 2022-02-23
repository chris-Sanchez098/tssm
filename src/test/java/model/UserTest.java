package model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class UserTest {
    User user;
    User user1;
    User user2;
    @BeforeEach
    public void init(){
        user = new User("1193075514", "Victor Sapuyes", "neveu", "Victor123@","Gerente", true);
        user1 = new User("1193075514", "Victor Sapuyes", "neveu", "Victor123@","Administrador", true);
        user2 = new User("1193075514", "Victor Sapuyes", "neveu", "Victor123@","Operador", true);

    }

    @Test
    @DisplayName(value = "Get tests")

    public void getTest(){
        assertAll("several gets",
                () -> assertEquals("1193075514", user.getCc()),
                () -> assertEquals("Victor Sapuyes", user.getName()),
                () -> assertEquals("neveu", user.getUser()),
                () -> assertEquals("722b70566647a753d42031ca15bfdd46", user.getPwd()),
                () -> assertEquals("Gerente", user.getRol()),
                () -> assertEquals(true, user.getStatus()),
                () -> assertEquals("Habilitado", user.statusToString())
        );
    }

    @Test
    @DisplayName(value = "Set tests")
    public void setTest(){
        user.setCc("1004675446");
        user.setName("Franklyn Narvaez");
        user.setUser("donal");
        user.setPwd("Fran1234@");
        user.setRol("Administrador");
        user.setStatus(false);

        user1.setName("");
        user1.setUser("");
        user1.setPwd("");
        user1.setRol("");
        user1.setStringStatus("");

        user2.setPwdNoEncrypt("123");
        user2.setStringStatus("Inhabilitado");


        assertAll("Several sets",
                () -> assertEquals("1004675446", user.getCc()),
                () -> assertEquals("Franklyn Narvaez", user.getName()),
                () -> assertEquals("donal", user.getUser()),
                () -> assertEquals("8d47b2268251748326a921f1cb46a004", user.getPwd()),
                () -> assertEquals("Administrador", user.getRol()),
                () -> assertEquals(false, user.getStatus()),
                () -> assertEquals("Victor Sapuyes", user1.getName()),
                () -> assertEquals("neveu", user1.getUser()),
                () -> assertEquals("722b70566647a753d42031ca15bfdd46", user1.getPwd()),
                () -> assertEquals("Administrador", user1.getRol()),
                () -> assertTrue(user1.getStatus()),
                () -> assertFalse(user2.getStatus()),
                () -> assertEquals("123", user2.getPwd())
        );
    }

    @Test
    @DisplayName(value = "Password check tests")
    public void pwdTests(){
        assertAll("Several password checks",
                () -> assertFalse(User.checkPwd("neveu")),
                () -> assertTrue(User.checkPwd("Victor123@*-as-")),
                () -> assertTrue(User.checkPwd("ABC2021-A-*a"))
        );
    }

    @Test
    @DisplayName(value = "RolFxml tests")
    public void rolFxmlTest(){
        assertAll("Several rolFxml",
                () -> assertEquals("administrator", user1.rolFxml()),
                () -> assertEquals("manager", user.rolFxml()),
                () -> assertEquals("operator", user2.rolFxml())
        );
    }

    @Test
    @DisplayName(value = "Update User tests")
    public void getUpdateTest(){
        user.getUpdate(user1);
        user1.getUpdate(user2);
        assertAll("Several update Test",
                () -> assertEquals("Administrador", user.getRol()),
                () -> assertEquals("Operador", user1.getRol())
        );
    }

}
