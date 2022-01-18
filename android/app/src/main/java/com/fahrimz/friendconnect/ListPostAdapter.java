package com.fahrimz.friendconnect;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.fahrimz.friendconnect.model.PostsItem;

import java.util.ArrayList;

public class ListPostAdapter extends RecyclerView.Adapter<ListPostAdapter.ViewHolder> {

    private ArrayList<PostsItem> listPost;
    public RecyclerViewClickListener clickListener = null;

    public ListPostAdapter(ArrayList<PostsItem> listPost) {
        this.listPost = listPost;
    }

    public void setData(ArrayList<PostsItem> data) {
        this.listPost = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListPostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPostAdapter.ViewHolder holder, int position) {
        PostsItem post = listPost.get(position);

        holder.tvUsername.setText(post.getUsername());
        holder.tvBody.setText(post.getBody());
        holder.tvLikes.setText(String.valueOf(post.getLikes()) + " likes");

        String date = Utils.formatDateFromDatabaseString(post.getCreatedAt());
        holder.tvDate.setText(date);

        String url = post.getAvatarUrl();

        // problem with via placeholder: https://stackoverflow.com/q/62425649
        GlideUrl glideUrl = new GlideUrl(url, new LazyHeaders.Builder()
                .addHeader("User-Agent", "okhttp/4.2.1")
                .build());

        Glide.with(holder.itemView)
                .load(glideUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .centerCrop()
                .into(holder.imgAvatar);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClicked(v, post);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listPost != null ? listPost.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvUsername, tvDate, tvBody, tvLikes;
        private ImageView imgAvatar;

        public ViewHolder(View view) {
            super(view);
            this.tvUsername = view.findViewById(R.id.tvUsername);
            this.tvBody = view.findViewById(R.id.tvBody);
            this.tvDate = view.findViewById(R.id.tvDate);
            this.imgAvatar = view.findViewById(R.id.imgAvatar);
            this.tvLikes = view.findViewById(R.id.tvLikes);
        }
    }
}
