package com.fahrimz.friendconnect;

import android.view.View;

import com.fahrimz.friendconnect.model.PostsItem;

public interface RecyclerViewClickListener {
    void onItemClicked(View view, PostsItem item);
}
