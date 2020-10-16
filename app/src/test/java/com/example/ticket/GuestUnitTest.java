package com.example.ticket;

import com.example.ticket.Test.GuestValidator;
import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GuestUnitTest {
    private static final String RESULT="Credit Card Successfull";

    @Test
    public void cardValidator_CorrectCardSimple_ReturnsTrue() {
        assertTrue(GuestValidator.isValidCreditCard("4321 1234 1234 1236"));
    }

    @Test
    public void cardValidator_EmptyString_ReturnsFalse() {
        assertFalse(GuestValidator.isValidCreditCard(""));
    }
}
