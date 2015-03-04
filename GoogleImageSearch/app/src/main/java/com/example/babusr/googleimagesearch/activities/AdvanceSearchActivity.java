package com.example.babusr.googleimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.babusr.googleimagesearch.R;

public class AdvanceSearchActivity extends ActionBarActivity {
    private EditText etSiteFilter;
    private Spinner spImageSize;
    private Spinner spColorFilter;
    private Spinner spImageType;
    public static String SiteFilter;
    public static String valueImageSize;
    public static String valueColorFilter;
    public static String valueImageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_advance_search);

        //Spinner for Image Size
        spImageSize = (Spinner)findViewById(R.id.spImageSize);
        ArrayAdapter imageSizeAdapter = ArrayAdapter.createFromResource(this,R.array.ImageSize,android.R.layout.simple_list_item_1);
        spImageSize.setAdapter(imageSizeAdapter);


        //Spinner for Color Filter
        spColorFilter= (Spinner)findViewById(R.id.spColorFilter);
        ArrayAdapter colorFilterAdapter = ArrayAdapter.createFromResource(this,R.array.ColorFilter,android.R.layout.simple_list_item_1);
        spColorFilter.setAdapter(colorFilterAdapter);


        //Spinner for Image Type
        spImageType= (Spinner)findViewById(R.id.spImageType);
        ArrayAdapter imageTypeAdapter = ArrayAdapter.createFromResource(this,R.array.ImageType,android.R.layout.simple_list_item_1);
        spImageType.setAdapter(imageTypeAdapter);

    }

    public void onSaveFilter(View v){
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
        SiteFilter = etSiteFilter.getText().toString();
        //Toast.makeText(this, "Site filter is:" + SiteFilter, Toast.LENGTH_SHORT).show();

        valueImageSize = spImageSize.getSelectedItem().toString();
        Log.i("ImageSize",valueImageSize);

        valueColorFilter = spColorFilter.getSelectedItem().toString();
        Log.i("ColorFilter",valueColorFilter);

        valueImageType = spImageType.getSelectedItem().toString();
        Log.i("ImageType",valueImageType);

        Intent data = new Intent();
        data.putExtra("SiteFilter",SiteFilter);
        data.putExtra("ImageSize",valueImageSize);
        data.putExtra("ColorFilter",valueColorFilter);
        data.putExtra("ImageType",valueImageType);

        setResult(RESULT_OK,data);

        this.finish();


    }




}
