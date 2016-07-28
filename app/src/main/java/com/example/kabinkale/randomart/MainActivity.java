package com.example.kabinkale.randomart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static ImageView imageView;
    private Random random = new Random();
    private ProgressBar progressBar;
    private TextView sizeDisp;
    private ResponseReceiver receiver;
    private ProgressBarReceiver progressMax;
    private ProgressUpdateReceiver progressUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.artDisplay);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        sizeDisp = (TextView) findViewById(R.id.sizeDisp);


        IntentFilter filter1= new IntentFilter(DrawArtService.ACTION_TOTAL);
        filter1.addCategory(Intent.CATEGORY_DEFAULT);
        progressMax = new ProgressBarReceiver();
        registerReceiver(progressMax,filter1);

        IntentFilter filter2= new IntentFilter(DrawArtService.ACTION_PROGRESS);
        filter2.addCategory(Intent.CATEGORY_DEFAULT);
        progressUpdate = new ProgressUpdateReceiver();
        registerReceiver(progressUpdate,filter2);

        IntentFilter filter = new IntentFilter(DrawArtService.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new ResponseReceiver();
        registerReceiver(receiver,filter);

    }

    @Override
    protected void onStop()
    {
        unregisterReceiver(progressMax);
        unregisterReceiver(progressUpdate);
        unregisterReceiver(receiver);
        super.onStop();
    }

    public void CreateArt(View view){
        int i,j;
        int height= random.nextInt(400);
        int width = random.nextInt(400);


        Intent whDrawIntent = new Intent(this,DrawArtService.class);
        whDrawIntent.putExtra(DrawArtService.IN_HEIGHT,height);
        whDrawIntent.putExtra(DrawArtService.IN_WIDTH,width);

        startService(whDrawIntent);

    }

    public class ResponseReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            Bitmap finalArt = intent.getParcelableExtra(DrawArtService.OUT_BITMAP);
            imageView.setImageBitmap(finalArt);
        }
    }

    public class ProgressBarReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int progressMax = intent.getIntExtra(DrawArtService.TOTAL_PROGRESS,22);
            progressBar.setMax(progressMax);
            sizeDisp.setText(Integer.toString(progressMax));

        }
    }

    public class ProgressUpdateReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            int progress = intent.getIntExtra(DrawArtService.UPDATE_PROGRESS,22);
            progressBar.setProgress(progress);
            Log.i(MainActivity.class.getSimpleName(),Integer.toString(progress));
//            sizeDisp.setText(Integer.toString(progress));
        }
    }
    /**
     * generate random numbers
     *
     * @return random number %255
     */
    private int getRand(){
        int rand=random.nextInt(255);
        if(rand<100){
            rand+=100;
        }
        return rand;
    }

}
