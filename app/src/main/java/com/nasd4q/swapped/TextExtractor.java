package com.nasd4q.swapped;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;
import android.util.SparseArray;

import androidx.annotation.RequiresApi;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TextExtractor {
    private final Context context;
    private final TextRecognizer textRecognizer;

    public TextExtractor(Context context) {

        this.context = context;
        this.textRecognizer = new TextRecognizer.Builder(context).build();
    }

    protected Bitmap toBitmap(String relativePath) {
        File input = new File(context.getExternalFilesDir(null), relativePath);
        Bitmap bitmap = BitmapFactory.decodeFile(input.toString());
        return bitmap;
    }

    protected String extractText(String relativePath) {
        String imageText = "";
        Bitmap bitmap = toBitmap(relativePath);
        Log.d("AZE", bitmap == null ? "null" : "ok");

        if (bitmap != null) {
            Frame imageFrame = new Frame.Builder()
                    .setBitmap(bitmap)                 // your image bitmap
                    .build();

            SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);

            for (int i = 0; i < textBlocks.size(); i++) {
                TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
                imageText += textBlock.getValue() + "\n";// return string
            }
        }

        return imageText;
    }

    protected String extractText(Bitmap bitmap) {
        String imageText = null;
        if (bitmap != null) {
            Frame imageFrame = new Frame.Builder()
                    .setBitmap(bitmap)                 // your image bitmap
                    .build();

            SparseArray<TextBlock> textBlocks = textRecognizer.detect(imageFrame);

            for (int i = 0; i < textBlocks.size(); i++) {
                TextBlock textBlock = textBlocks.get(textBlocks.keyAt(i));
                imageText += textBlock.getValue() + "\n";// return string
            }
        }
        return imageText;
    }

    protected String extractTarga(String imageText) {
        Log.d("AZE", "extracting Targa on " + imageText);
        Pattern pattern = Pattern.compile("XXX-(...)");
        Matcher matcher = pattern.matcher(imageText);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
