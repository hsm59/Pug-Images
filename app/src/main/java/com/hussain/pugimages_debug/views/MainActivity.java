package com.hussain.pugimages_debug.views;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.hussain.pugimages_debug.R;
import com.hussain.pugimages_debug.adapters.PugsAdapter;
import com.hussain.pugimages_debug.models.GridItem;
import com.hussain.pugimages_debug.models.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private String TAG = MainActivity.class.getSimpleName();
    private static final String endpoint = "http://private-e1b8f4-getimages.apiary-mock.com/getImages";
    private ArrayList<GridItem> mGridData;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGridData = new ArrayList<>();
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
                setGridData(mGridData);
            } else {
                Toast.makeText(MainActivity.this, "Failed to fetch data!", Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();
        }
    }

    private void parseResult(String result) {
        try {

            JSONObject jsonObj = new JSONObject(result);
            JSONArray pugs = jsonObj.getJSONArray("pugs");
            for (int i = 0; i < pugs.length(); i++) {
                String post = pugs.getString(i);
                GridItem item = new GridItem();
                item.setImage(post);
                mGridData.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setGridData(ArrayList<GridItem> gridData){
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager();
        layoutManager.setFlexWrap(FlexWrap.WRAP);
        layoutManager.setAlignItems(AlignItems.STRETCH);
        layoutManager.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new PugsAdapter(this, gridData);
        recyclerView.setAdapter(adapter);
    }
}
