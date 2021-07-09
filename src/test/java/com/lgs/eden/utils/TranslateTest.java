package com.lgs.eden.utils;

import com.lgs.eden.api.APIResponseCode;
import com.lgs.eden.utils.config.Config;
import com.lgs.eden.utils.config.Language;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TranslateTest {

    @BeforeAll
    static void init(){
        Config.setLocale(Language.EN);
    }

    @Test
    void getTranslation() {
        String not_a_key = Translate.getTranslation("not_a_key");
        Assertions.assertEquals("not_a_key", not_a_key);

        String back = Translate.getTranslation("back");
        Assertions.assertEquals("Back", back);
    }

    @Test
    void testGetTranslation() {
        String code = Translate.getTranslation(APIResponseCode.REGISTER_OK);
        Assertions.assertEquals(code, Translate.getTranslation("code_20"));
    }

    @Test
    void getDate() {
        // nothing to test
    }

    @Test
    void testGetDate() {
        // nothing to test
    }
}