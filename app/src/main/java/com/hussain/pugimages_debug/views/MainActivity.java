package com.hussain.pugimages_debug.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.hussain.pugimages_debug.R;
import com.hussain.pugimages_debug.data.FetchPhotosTask;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvMaijnActivity;
    FetchPhotosTask fetchPhotosTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMaijnActivity = (RecyclerView) findViewById(R.id.recyclerView);

        fetchPhotosTask = new FetchPhotosTask(getApplicationContext(), rvMaijnActivity);
        fetchPhotosTask.execute();
    }
}
