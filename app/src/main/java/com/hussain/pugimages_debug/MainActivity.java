package com.hussain.pugimages_debug;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private static final String endpoint = "http://private-e1b8f4-getimages.apiary-mock.com/getImages";
    private ArrayList<GridItem> mGridData;
    private ProgressDialog pDialog;
    private GridViewAdapter mGridAdapter;
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGridView = (GridView) findViewById(R.id.gridview);
        mGridData = new ArrayList<>();
        mGridAdapter = new GridViewAdapter(this, R.layout.grid_item, mGridData);
        mGridView.setAdapter(mGridAdapter);

        new fetchImages().execute();
    }

    private class fetchImages extends AsyncTask<Void, Void, Integer> {
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Integer doInBackground(Void... arg0) {
            Integer result = 0;
            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(endpoint);
            Log.v(TAG, "Response from URL: " + jsonStr);
            if (jsonStr != null) {
                parseResult(jsonStr);
                result = 1;
            } else {
                result = 0;
            }
            return result;
        }

        protected void onPostExecute(Integer result) {
            if (result == 1) {
                mGridAdapter.setGridData(mGridData);
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
        }
    }

    private void parseResult(String result) {
        try {
            GridItem item;
            JSONObject jsonObj = new JSONObject(result);
            JSONArray pugs = jsonObj.getJSONArray("pugs");
            for (int i = 0; i < pugs.length(); i++) {
                String post = pugs.getString(i);
                Log.v(TAG,post);
                item = new GridItem();
                item.setImage(post);
                mGridData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
