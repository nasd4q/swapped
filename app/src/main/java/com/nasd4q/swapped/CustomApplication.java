package com.nasd4q.swapped;

import android.app.Application;
import android.graphics.Bitmap;
import android.media.Image;
import android.media.ImageReader;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class CustomApplication extends Application {
    protected static boolean newImageWanted = false;
    protected static ImageReader mImageReader;
    protected static String storeDir;
    protected static int mWidth;
    protected static int mHeight;
    protected static int IMAGES_PRODUCED;


    protected void FloatingButtonClicked() {
        newImageWanted = true;
        Log.d("AZE", "FloatingButtonClicked");
        writeLatestImage();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        File externalFilesDir = getExternalFilesDir(null);
        if (externalFilesDir != null) {
            storeDir = externalFilesDir.getAbsolutePath() + "/screenshots/";
            File storeDirectory = new File(storeDir);
            if (!storeDirectory.exists()) {
                boolean success = storeDirectory.mkdirs();
                if (!success) {
                    Log.d("AZE", "failed to create file storage directory.");
                }
            }
        } else {
            Log.d("AZE", "failed to create file storage directory, getExternalFilesDir is null.");
        }
    }


    private void writeLatestImage() {
        FileOutputStream fos = null;
        Bitmap bitmap = null;
        try (Image image = mImageReader.acquireLatestImage()) {
            if (image != null) {
                Log.d("AZE", "onImageAvailable, saving image");

                Image.Plane[] planes = image.getPlanes();
                ByteBuffer buffer = planes[0].getBuffer();
                int pixelStride = planes[0].getPixelStride();
                int rowStride = planes[0].getRowStride();
                int rowPadding = rowStride - pixelStride * mWidth;

                // create bitmap
                bitmap = Bitmap.createBitmap(mWidth + rowPadding / pixelStride, mHeight, Bitmap.Config.ARGB_8888);
                bitmap.copyPixelsFromBuffer(buffer);

                // write bitmap to a file
                fos = new FileOutputStream(storeDir + "/myscreen_" + IMAGES_PRODUCED + ".png");
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);

                IMAGES_PRODUCED++;
                Log.d("AZE", "captured image: " + IMAGES_PRODUCED + " to " + storeDir);
                Toast.makeText(this, "hm mm", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }

            if (bitmap != null) {
                bitmap.recycle();
            }
        }
    }
}
