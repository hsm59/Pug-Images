package com.hussain.pugimages_debug.data;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.hussain.pugimages_debug.R;
import com.hussain.pugimages_debug.adapters.PugsAdapter;
import com.hussain.pugimages_debug.models.GridItem;
import com.hussain.pugimages_debug.models.HttpHandler;
import com.hussain.pugimages_debug.views.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by hussain on 4/16/17.
 */

public class FetchPhotosTask extends AsyncTask <Void, Void, Integer> {
    private static final String TAG = "FetchPhotosTask";
    private final String ENDPOINT = "http://private-e1b8f4-getimages.apiary-mock.com/getImages";
    private Context context;
    private ProgressDialog pDialog;
    private ArrayList<GridItem> mGridData;
    private RecyclerView recyclerView;

    public FetchPhotosTask(Context context, RecyclerView recyclerView){
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mGridData = new ArrayList<>();
        pDialog = new ProgressDialog(context);
//        pDialog.setMessage("Please wait");
//        pDialog.setCancelable(false);
//        pDialog.show();
    }

    @Override
    protected Integer doInBackground(Void... params) {
        Integer result = 0;
        HttpHandler sh = new HttpHandler();
        String jsonStr = sh.makeServiceCall(ENDPOINT);
        Log.v(TAG, "Response from URL: " + jsonStr);
        if (jsonStr != null) {
            parseResult(jsonStr);
            result = 1;
        } else {
            result = 0;
        }
        return result;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if (result == 1) {
            setGridData(mGridData);
        } else {
            Toast.makeText(context, "Failed to fetch data!", Toast.LENGTH_LONG).show();
        }
    }

    private void parseResult(String result) {
        try {

            JSONObject jsonObj = new JSONObject(result);
            JSONArray pugs = jsonObj.getJSONArray("pugs");
            for (int i = 0; i < pugs.length(); i++) {
                Log.d(TAG, "parseResult: Pugs Images "+pugs.toString());
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
        GridLayoutManager layoutManager = new GridLayoutManager(context, 3);
//        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager();
//        layoutManager.setFlexWrap(FlexWrap.WRAP);
//        layoutManager.setAlignItems(AlignItems.STRETCH);
//        layoutManager.setJustifyContent(JustifyContent.CENTER);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerView.Adapter adapter = new PugsAdapter(context, gridData);
        recyclerView.setAdapter(adapter);
    }
}
