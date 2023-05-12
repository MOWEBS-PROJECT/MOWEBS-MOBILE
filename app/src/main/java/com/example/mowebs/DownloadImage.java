package com.example.mowebs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

public class DownloadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView imageView;
    public DownloadImage(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        Bitmap bitmapImage = null;
        try {
            InputStream input = new java.net.URL(strings[0]).openStream();
            bitmapImage = BitmapFactory.decodeStream(input);
        } catch (Exception e) {

        }

        return bitmapImage;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }

}
