package com.example.kabinkale.randomart;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private ImageView imageView;
    private Random random = new Random();
    private ProgressBar progressBar;
    private TextView sizeDisp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.artDisplay);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        sizeDisp = (TextView) findViewById(R.id.sizeDisp);
    }

    public void CreateArt(View view){
        int i,j;
        int height= random.nextInt(700);
        int width = random.nextInt(700);
        int total = height*width;
        sizeDisp.setText(Integer.toString(height)+"x"+Integer.toString(width));
        progressBar.setMax(width);
        CreateImageTask EutaTask = new CreateImageTask();
        EutaTask.execute(width,height,width);

    }



    private class CreateImageTask extends AsyncTask<Integer,Integer,Bitmap> {
        private Bitmap Art2D2;

        public Bitmap getArt2D2() {
            return Art2D2;
        }

        public void setArt2D2(Bitmap art2D2) {
            Art2D2 = art2D2;
        }


        @Override
        protected Bitmap doInBackground(Integer... integers) {
            int width=integers[0];
            int i,j,prog;
            int height = integers[1];
            Bitmap Art = Bitmap.createBitmap(height,width, Bitmap.Config.ARGB_8888);

            for(i=0;i<Art.getWidth();i++){
                for (j=0;j<Art.getHeight();j++){

                    Art.setPixel(i,j, Color.argb(255,getRand(),getRand(),getRand()));
                    prog= i;

                    publishProgress(prog);
                }
            }
            setArt2D2(Art);
            return Art;
        }



        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView.setImageBitmap(bitmap);
       }

        @Override
        protected void onProgressUpdate(Integer... values) {
//            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);

        }


    }


    private int getRand(){
        int rand=random.nextInt(255);
        if(rand<100){
            rand+=100;
        }
        return rand;
    }

}
