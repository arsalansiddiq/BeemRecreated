package com.example.arsalansiddiq.beem.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AppUtils {

    private Date date;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;

    private Context context;

    public AppUtils(Context context) {
        this.context = context;
    }

    public String getDate() {

        date = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = df.format(date);
        Log.i("Current time => ", currentDate);

        return currentDate;
    }

    public String getTime() {

        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss a");

        String startTime = simpleDateFormat.format(calendar.getTime());

        Log.i("date", startTime);

        return startTime;
    }

    public File getImageFile (Bitmap imageBitmap) {
        File userImageFile = null;
        try {
            int size = 0;
            ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArr = bos.toByteArray();
            bos.flush();
            bos.close();

            FileOutputStream fos = context.openFileOutput("imageFile.png", Context.MODE_PRIVATE);
            fos.write(bArr);
            fos.flush();
            fos.close();

            userImageFile = new File(context.getFilesDir().getAbsolutePath(), "imageFile.png");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userImageFile;
    }

    public byte[] imageBitmapToBiteConversion (Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public File imageByteToFileConversion (byte[] bytesImage) {

        return getImageFile(BitmapFactory.decodeByteArray(bytesImage, 100, bytesImage.length));

    }

}
