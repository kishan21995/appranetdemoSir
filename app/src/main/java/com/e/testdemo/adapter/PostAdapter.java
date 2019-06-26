package com.e.testdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e.testdemo.R;
import com.e.testdemo.models.Comment;
import com.e.testdemo.models.Post;
import com.e.testdemo.models.PostResponse;

import java.util.List;

/**
 * {@link PostAdapter}
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {

    private PostResponse postResponse;
    Context context;

    @NonNull
    @Override
    public PostAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_item, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    public PostAdapter(PostResponse postResponse, Context applicationContext) {
        this.context = applicationContext;
        this.postResponse = postResponse;

    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.MyViewHolder myViewHolder, int position) {
        Post post = postResponse.getPosts().get(position);

        myViewHolder.postTV.setText(post.getTitle());

        /**
         * Iterate comments if comments exist
         */
        if (postResponse.getComments()!=null && postResponse.getComments().size()>0)
        for (Comment comment : postResponse.getComments()) {
            if (post.getId() == comment.getPostId()) {
                myViewHolder.commentTV.setText(comment.getBody());
            }
        }


    }

    @Override
    public int getItemCount() {
        if (postResponse.getPosts()!=null){
            return postResponse.getPosts().size();
        }else{
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView postTV, commentTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            postTV = itemView.findViewById(R.id.post);
            commentTV = itemView.findViewById(R.id.comments);


        }
    }
}
