package com.example.android.showpictures;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotosAdapterViewHolder> {

    private String[] mImageData;

    class PhotosAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView mImageTextView;

        PhotosAdapterViewHolder(View view) {
            super(view);
            mImageTextView = (TextView) view.findViewById(R.id.tv_image_data);
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
        holder.mImageTextView.setText(mImageData[position]);
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
