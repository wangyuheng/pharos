package com.crick.business.pharos.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class EncryptUtilTest {

    @Test
    public void test_sha() {
        String source = "this_is_a_string_source!";
        String sha1Str = EncryptUtil.sha1(source);
        String sha256Str = EncryptUtil.sha256(source);
        assertFalse("".equalsIgnoreCase(sha1Str));
        assertFalse("".equalsIgnoreCase(sha256Str));
        assertEquals(sha1Str.length(), EncryptUtil.sha1("").length());
        assertEquals(sha256Str.length(), EncryptUtil.sha256("").length());
    }

}