package com.doc24x7;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private Context mContex;
    private List<Uri> mListPhoto;

    public PhotoAdapter(Context mContex) {
        this.mContex = mContex;
    }
       public void setData(List<Uri> list)
       {
        this.mListPhoto=list;
        notifyDataSetChanged();
       }
       @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_show_images,parent,false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
                 Uri uri=mListPhoto.get(position);
                 if(uri==null)
                 {
                     return;
                 }
        try {
            Bitmap bitmap= MediaStore.Images.Media.getBitmap(mContex.getContentResolver(),uri);
            if(bitmap!=null)
             {
                holder.imgPhoto.setImageBitmap(bitmap);
                // UtilMethods.INSTANCE.UploadFile(mContex,uri,loader);
           //     uploadbitmap(uri);
             }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public int getItemCount() {
        if(mListPhoto != null)
        {
           return mListPhoto.size();
        }
        return 0;
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder
    {
         private ImageView imgPhoto;
        public PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPhoto=itemView.findViewById(R.id.media_image);
        }
    }
}
