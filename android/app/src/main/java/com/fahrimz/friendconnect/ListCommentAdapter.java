package com.fahrimz.friendconnect;

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
import com.fahrimz.friendconnect.model.CommentsItem;

import java.util.ArrayList;

public class ListCommentAdapter extends RecyclerView.Adapter<ListCommentAdapter.ViewHolder> {

    private ArrayList<CommentsItem> listComment;

    public ListCommentAdapter(ArrayList<CommentsItem> listComment) {
        this.listComment = listComment;
    }

    public void setData(ArrayList<CommentsItem> data) {
        this.listComment = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListCommentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_list_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCommentAdapter.ViewHolder holder, int position) {
        CommentsItem comment = listComment.get(position);
        holder.txtUsername.setText(comment.getUsername());
        holder.txtBody.setText(comment.getBody());
    }

    @Override
    public int getItemCount() {
        return listComment != null ? listComment.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtUsername, txtBody;

        public ViewHolder(View view) {
            super(view);
            this.txtUsername = view.findViewById(R.id.txtCommentUsername);
            this.txtBody = view.findViewById(R.id.txtCommentBody);
        }
    }
}
