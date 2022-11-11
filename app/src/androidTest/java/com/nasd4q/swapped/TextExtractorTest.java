package com.nasd4q.swapped;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.net.URISyntaxException;
import java.util.regex.Pattern;

@RunWith(AndroidJUnit4.class)
public class TextExtractorTest {

    String relativePath = "Tests screenshots/myscreen_1.png";
    Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
    TextExtractor textExtractor = new TextExtractor(context);

    @Test
    public void extractText(){
        Log.d("AZE", textExtractor.extractText(relativePath));
    }

    String exampleText = textExtractor.extractText(relativePath);


    @Test
    public void extractTarga() {
        assertEquals("AZE", textExtractor.extractTarga("XXX-AZE"));
        assertEquals("AZE", textExtractor.extractTarga(" XXX-AZE "));
        assertEquals("AZE", textExtractor.extractTarga("fehaozhoa paziojep\nXXX-AZE\nAZE-XXX"));
        assertEquals("YRL", textExtractor.extractTarga(exampleText));
    }


}
