package com.github.jban;

import org.junit.Test;

import static org.junit.Assert.*;

public class JbanTest {

    @Test
    public void testName() throws Exception {

        Jban tested = new Jban();
        String reverse = tested.reverse(48.357, 2.37);
        System.out.println(reverse);

    }
}