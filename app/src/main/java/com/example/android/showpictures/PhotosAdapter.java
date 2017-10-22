package com.example.android.showpictures;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosAdapterViewHolder> {

    private String[] mImageData;

    class PhotosAdapterViewHolder extends RecyclerView.ViewHolder {

        public final ImageView mImageImageView;

        PhotosAdapterViewHolder(View view) {
            super(view);
            mImageImageView = (ImageView) view.findViewById(R.id.iv_photo_image);
        }
    }

    @Override
    public PhotosAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        int layoutIdForListItem = R.layout.photos_list_item;
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        return new PhotosAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotosAdapterViewHolder holder, int position) {
        Context context = holder.mImageImageView.getContext();
        Picasso.with(context).load(mImageData[position]).into(holder.mImageImageView);
    }

    @Override
    public int getItemCount() {
        if (null == mImageData) return 0;
        return mImageData.length;
    }

    public void setImageData(String[] imageData) {
        mImageData = imageData;
        notifyDataSetChanged();
    }
}
