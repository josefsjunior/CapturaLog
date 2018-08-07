package com.example.josefernandes.capturalog;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class LoginActivityTest {

    LoginActivityUtils utils;

    @Before
    public void setUp(){
        utils = new LoginActivityUtils();
    }

    @Test
    public void emailComArroba() throws Exception {
        assertTrue( utils.temArroba("josefsjunior@outlook.com") );
    }


}