package com.example.ticket;
import com.example.ticket.Test.GuestAccountValidator;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GuestAccountValidatorTest {
    //Testing for Guest Account class

    //testing email format
    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(GuestAccountValidator.isValidEmail("name_@email.com"));
    }

    //testing email sub domain
    @Test
    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(GuestAccountValidator.isValidEmail("name@email.co.uk"));
    }

    //testing email use invalid pattern
    @Test
    public void emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(GuestAccountValidator.isValidEmail("name@email"));
    }

    //testing email has double dots
    @Test
    public void emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(GuestAccountValidator.isValidEmail("name@email..com"));
    }

    //testing haven't user name for email address
    @Test
    public void emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(GuestAccountValidator.isValidEmail("@email.com"));
    }

    //testing empty
    @Test
    public void emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(GuestAccountValidator.isValidEmail(""));
    }

    //testing null email
    @Test
    public void emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(GuestAccountValidator.isValidEmail(null));
    }

    //testing username
    @Test
    public void gustAccountValidator_Nullusername_ReturnsFalse() {
        assertFalse(GuestAccountValidator.isValidUserName(null));
    }

}
