package com.example.babusr.googleimagesearch.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.babusr.googleimagesearch.R;
import com.example.babusr.googleimagesearch.models.ImageResult;
import com.squareup.picasso.Picasso;

public class ImageDisplay extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        //Hide the action bar
        getSupportActionBar().hide();
        //Pull out the url from the intent
        // String url = getIntent().getStringExtra("url");
        ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
        //Find the image view
        ImageView ivImageResult = (ImageView) findViewById(R.id.ivImageResult);
        //load the image url into the imageview using picasso
        Picasso.with(this).load(result.fullUrl).into(ivImageResult);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_display, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
