package utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidadorTest {
    @Test
    void validarNomeInvalidoTest(){
        assertNull(Validador.validoNome(null));
        assertNull(Validador.validoNome("joao"));
        assertNull(Validador.validoNome("joao2"));
    }
    @Test
    void validarNomeValidoTest(){
        String expectedNome = "João Pedro";
        String actualNome = Validador.validoNome("João Pedro");
        assertEquals(expectedNome, actualNome);
    }
    @Test
    void validarMoradaInvalidoTest(){
        assertNull(Validador.validaMorada(null));
        assertNull(Validador.validaMorada("rua"));
        assertNull(Validador.validaMorada("r23ua,"));
    }
    @Test
    void validarMoradaValidoTest(){
        String expectedMorada = "Rua da Esquina n68";
        String actualMorada = Validador.validaMorada("Rua da Esquina n68");
        assertEquals(expectedMorada, actualMorada);
    }
    @Test
    void validarPasswordInvalidoTest(){
        assertNull(Validador.validaPassword(null));
        assertNull(Validador.validaPassword("sdf"));
        assertNull(Validador.validaPassword("r23ua,"));
    }
    @Test
    void validarPasswordValidoTest(){
        String expectedPassword = "fSH4d2.";
        String actualPassword = Validador.validaPassword("fSH4d2.");
        assertEquals(expectedPassword, actualPassword);
    }
    @Test
    void validarTelefoneInvalidoTest(){
        assertNull(Validador.validaTelefone(null));
        assertNull(Validador.validaTelefone(""));
        assertNull(Validador.validaTelefone("r23ua"));
    }
    @Test
    void validarTelefoneValidoTest(){
        String expectedTelefone = "9184923";
        String actualTelefone = Validador.validaTelefone("9184923");
        assertEquals(expectedTelefone, actualTelefone);
    }
    @Test
    void validarNIFInvalidoTest(){
        assertNull(Validador.validaNIF(null));
        assertNull(Validador.validaNIF(""));
        assertNull(Validador.validaNIF("r23ua"));
    }
    @Test
    void validarNIFValidoTest(){
        String expectedNIF = "23423424";
        String actualNIF = Validador.validaTelefone("23423424");
        assertEquals(expectedNIF, actualNIF);
    }
}