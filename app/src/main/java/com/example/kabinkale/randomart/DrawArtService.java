package com.example.kabinkale.randomart;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Random;

/**
 * Created by Kabin Kale on 7/27/2016.
 */
public class DrawArtService extends IntentService {
    Random random= new Random();
    public static final String IN_HEIGHT="inHeight";
    public static final String IN_WIDTH="inWidth";
    public static final String OUT_BITMAP = "outBitmap";
    public static final String TOTAL_PROGRESS = "totalProgress";
    public static final String UPDATE_PROGRESS = "updateProgress";

    public static final String ACTION_RESP = MainActivity.class.getSimpleName() + "ART_PROCESSED";
    public static final String ACTION_TOTAL = MainActivity.class.getSimpleName() + "MAX_PROGRESS_PROCESSED";
    public static final String ACTION_PROGRESS = MainActivity.class.getSimpleName() + "PROGRESS_UPDATE";



    public DrawArtService() {
        super(DrawArtService.class.getName());
    }

    public DrawArtService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int height = intent.getIntExtra(IN_HEIGHT,0);
        int width = intent.getIntExtra(IN_WIDTH,0);
        int i,j;

        Intent broadcastArt = new Intent();
        Intent broadcastMax = new Intent();
        Intent broadcastUpdate = new Intent();

        broadcastMax.setAction(ACTION_TOTAL);
        broadcastMax.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastMax.putExtra(TOTAL_PROGRESS,height);
        sendBroadcast(broadcastMax);


        Bitmap Art = Bitmap.createBitmap(height,width, Bitmap.Config.ARGB_8888);



        broadcastUpdate.setAction(ACTION_PROGRESS);
        broadcastUpdate.addCategory(Intent.CATEGORY_DEFAULT);

        for(i=0;i<Art.getWidth();i++){
            for (j=0;j<Art.getHeight();j++){

                Art.setPixel(i,j, Color.argb(255,getRand(),getRand(),getRand()));

            }
            broadcastUpdate.putExtra(UPDATE_PROGRESS,i+1);
            sendBroadcast(broadcastUpdate);

        }

        broadcastArt.setAction(ACTION_RESP);
        broadcastArt.putExtra(OUT_BITMAP,Art);
        sendBroadcast(broadcastArt);
    }

    /**
     * generate random numbers
     *
     * @return random number %255
     */
    private int getRand(){
        int rand=random.nextInt(255);
        if(rand<100){
            rand+=random.nextInt(100);
        }
        return rand;
    }
}
