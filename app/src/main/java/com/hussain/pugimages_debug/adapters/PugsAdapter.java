package com.hussain.pugimages_debug.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hussain.pugimages_debug.R;
import com.hussain.pugimages_debug.models.GridItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by hussain on 6/3/17.
 */

public class PugsAdapter extends RecyclerView.Adapter<PugsAdapter.PugsViewHolder> {
    private static final String TAG = "PugsAdapter";
    Context mContext;
    ArrayList<GridItem> gridItems;

    public PugsAdapter(Context mContext, ArrayList<GridItem> gridItems) {
        this.gridItems = gridItems;
        this.mContext = mContext;
    }

    @Override
    public PugsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new PugsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PugsViewHolder holder, int position) {
        final GridItem currentItem = gridItems.get(position);
        Picasso.with(mContext).load(currentItem.getImage()).into(holder.mImageView);

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+currentItem.getImage());
            }
        });
    }

    @Override
    public int getItemCount()
    {
        if(gridItems!=null) {
            return gridItems.size();
        }
        else{
            return 0;
        }
    }

    public static class PugsViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        public PugsViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.grid_item);
        }
    }

}

