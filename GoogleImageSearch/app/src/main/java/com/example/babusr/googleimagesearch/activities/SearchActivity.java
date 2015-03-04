package com.example.babusr.googleimagesearch.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.example.babusr.googleimagesearch.R;
import com.example.babusr.googleimagesearch.adapters.ImageResultsAdapter;
import com.example.babusr.googleimagesearch.listener.EndlessScrollListener;
import com.example.babusr.googleimagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class SearchActivity extends ActionBarActivity {

    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private EditText etSiteFilter;
    private final int REQUEST_CODE = 20;
    private String colorFilter;
    private String siteName;
    private String imageSize;
    private String imageType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setupViews();
        //Creates the data source
        imageResults = new ArrayList<ImageResult>();
        //Attaches the data source to adapter
        aImageResults = new ImageResultsAdapter(this, imageResults);
        //Link the adapter to the Adapterview(gridview)
        gvResults.setAdapter(aImageResults);
        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                LoadMoreData(page);

            }
        });

    }

    //Append more data into the adapter
    private void LoadMoreData(int page) {
        String query = etQuery.getText().toString();
        //HTTP client
        AsyncHttpClient client = new AsyncHttpClient();
        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="+query+"&start=" + (page * 4);
        //Get response
        client.get(searchUrl, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.d("DEBUG", response.toString());
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    //imageResults.clear(); // clear the existing images from the array
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                    Log.i("INFO", imageResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void setupViews(){
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Launch the image display activity
                //Creating an intent
                Intent i = new Intent(SearchActivity.this, ImageDisplay.class);
                //Get the image result to display
                ImageResult result = imageResults.get(position);
                //Pass image result into the intent
                i.putExtra("result",result); // implement serializable or parcelable
                //Launch the new activity
                startActivity(i);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE){
            siteName = data.getExtras().getString("SiteFilter");
            imageSize = data.getExtras().getString("ImageSize");
            colorFilter = data.getExtras().getString("ColorFilter");
            imageType = data.getExtras().getString("ImageType");
        }
    }

    public void onImageSearch(View v) {
        String query = etQuery.getText().toString();
        //HTTPClient
        AsyncHttpClient client = new AsyncHttpClient();

        //https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=car&rsz=8&imgcolor=blue&imgsz=small&imgtype=photo&as_sitesearch=yahoo.com
        //String searchUrl = null;
            //searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8";
        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" +
               query + "&rsz=8&imgcolor=" + colorFilter + "&imgsz=" + imageSize + "&imgtype=" + imageType + "&as_sitesearch=" + siteName;

        //Get response
        client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //Log.d("DEBUG", response.toString());
                JSONArray imageResultsJson = null;
                try {
                    imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    imageResults.clear(); // clear the existing images from the array
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                    Log.i("INFO", imageResults.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
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

            Intent filter = new Intent(SearchActivity.this,AdvanceSearchActivity.class);
            filter.putExtra("mode",2);
            startActivityForResult(filter, REQUEST_CODE);
            return true;
        }
      return super.onOptionsItemSelected(item);
    }
}
