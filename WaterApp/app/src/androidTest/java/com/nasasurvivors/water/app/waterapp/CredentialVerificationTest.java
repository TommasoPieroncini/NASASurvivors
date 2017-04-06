package com.nasasurvivors.water.app.waterapp;


import org.junit.Assert;
import org.junit.Test;

import static com.nasasurvivors.water.app.waterapp.model.CredentialVerification.verifyEmail;
import static com.nasasurvivors.water.app.waterapp.model.CredentialVerification.verifyPassword;
import static org.junit.Assert.*;

/**
 * TESTS for 2 methods in CredentialsVerification: verifyPassword (Emma Flynn) and verifyEmail (Meha Agrawal)
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CredentialVerificationTest {
    @Test
    public void testVerifyPassword()

    {
        Assert.assertEquals(verifyPassword("Abcde123"), "");
        Assert.assertEquals(verifyPassword("Ab1"), "Password is too short");
        Assert.assertEquals(verifyPassword("abcde123"), "\nPassword does not contain an uppercase");
        Assert.assertEquals(verifyPassword("ABCDE123"), "\nPassword does not contain a lowercase");
        Assert.assertEquals(verifyPassword("Abcdefgh"), "\nPassword does not contain a number");
        Assert.assertEquals(verifyPassword("Abcde1234frhg"), "");
        Assert.assertEquals(verifyPassword("abc12"), "Password is too short\nPassword does not contain an uppercase");
        Assert.assertEquals(verifyPassword("A123"), "Password is too short\nPassword does not contain a lowercase");
        Assert.assertEquals(verifyPassword(""), "Password is too short\nPassword does not contain an uppercase" +
                "\nPassword does not contain a lowercase\nPassword does not contain a number");
        Assert.assertEquals(verifyPassword("abcde"), "Password is too short" +
                "\nPassword does not contain an uppercase\nPassword does not contain a number");

    }

    @Test
    public void testVerifyEmail() {
        Assert.assertEquals(verifyEmail("abc"), false);
        Assert.assertEquals(verifyEmail("abc@"), false);
        Assert.assertEquals(verifyEmail("abc@."), false);
        Assert.assertEquals(verifyEmail("abc@.c"), false);
        Assert.assertEquals(verifyEmail("abc@.@"), false);
        Assert.assertEquals(verifyEmail("abc@c.@.c"), false);
        Assert.assertEquals(verifyEmail("abc@c.v"), true);
        Assert.assertEquals(verifyEmail("abc@c.v.f"), true);
        Assert.assertEquals(verifyEmail("meha@gatech.edu"), true);

    }
}


