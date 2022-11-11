package com.nasd4q.swapped;

import static org.junit.Assert.assertEquals;

import android.util.Log;

import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

public class TestClass {
    ClassLoader classLoader = TestClass.class.getClassLoader();

    @Test
    public void extractText() throws URISyntaxException {
        URI uri = classLoader.getResource("textExtractionImages" + File.separator + "myscreen_0.png").toURI();
        System.out.println(uri.toString());
        File input = new File(uri);

        System.out.println(input.getAbsolutePath());
        assertEquals(1, 1);
    }

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue() {
        System.out.println("hi");
        assertEquals(1,1);
    }
}
