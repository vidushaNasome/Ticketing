package com.example.ticket;

import com.example.ticket.Test.EmailValidator;

import org.junit.Test;

import static org.junit.Assert.*;

public class PassengerTest {

    //Testing for classes

    //RegisterAct
    //Login

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.com"));
    }

    @Test
    public void emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.co.uk"));
    }

    @Test
    public void emailValidator_InvalidEmailNoTld_ReturnsTrue() {
        assertFalse(EmailValidator.isValidEmail("name@email"));
    }

    @Test
    public void emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(null));
    }

    @Test
    public void isonline_Reg() {
        RegisterAct ob=new RegisterAct();
        assertTrue(ob.isOnline());
    }

    @Test
    public void isonline_Login() {
        Login ob=new Login();
        assertTrue(ob.isOnline());
    }

    @Test
    public void ticketing(){
        Ticketing ob=new Ticketing();
        assertTrue(ob.checkValid("check"));
    }

}