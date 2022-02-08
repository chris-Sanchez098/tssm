package modelo;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class MD5Test {
    MD5 md5;

    @BeforeEach
    public void init(){
        md5 = new MD5();
    }

    @Test
    @DisplayName(value = "encrypted str tests")
    public void encryptTest(){
        assertAll("several str",
                () -> assertEquals("62059a74e9330e9dc2f537f712b8797c", md5.encrypt("texto")),
                () -> assertEquals("cc99c671f9ea86938e2c6cda65a65bc3", md5.encrypt("Texto")),
                () -> assertEquals("827ccb0eea8a706c4c34a16891f84e7b", md5.encrypt("12345")),
                () -> assertEquals("967377f78acbe9dbeb1933389091d49b", md5.encrypt("texto_simbolos.")),
                () -> assertEquals("e96ca7f8bc7a5654c8a2f4ceb668baf1", md5.encrypt("Texto_combi123."))
        );

    }
}
