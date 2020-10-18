package com.example.ticket;

import com.example.ticket.Test.EmailValidator;
import com.example.ticket.Test.GuestAccountValidator;

import org.junit.Test;

import static org.junit.Assert.*;

public class PassengerTest {

    //Testing for classes

    //RegisterAct
    //Login

    //Testing email vaidation in Register Activity
    //Included false data and correct data
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

    //Testing Online Validation
    @Test
    public void isonline_Reg() {
        RegisterAct ob=new RegisterAct();
        assertTrue(ob.isOnline());
    }

    //Testing Online Validation
    @Test
    public void isonline_Login() {
        Login ob=new Login();
        assertTrue(ob.isOnline());
    }

    //Testing Ticketing Validation
    @Test
    public void ticketing(){
        Ticketing ob=new Ticketing();
        assertTrue(ob.checkValid("check"));
    }

}