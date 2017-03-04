package com.andrey.github_api_sample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andrey.github_api_sample.App;
import com.andrey.github_api_sample.R;
import com.andrey.github_api_sample.data.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UserViewHolder> {

    private List<User> usersList;

    public UsersListAdapter(List<User> usersList) {
        this.usersList = usersList;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        User user = usersList.get(position);
        if (user != null){
            holder.login.setText(user.getLogin());
            Picasso.with(App.getContext())
                    .load(user.getAvatarUrl())
                    .resizeDimen(R.dimen.avatar_width, R.dimen.avatar_height)
                    .into(holder.userAvatar);
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView login;
        ImageView userAvatar;

        public UserViewHolder(View itemView) {
            super(itemView);

            login = (TextView) itemView.findViewById(R.id.user_login);
            userAvatar = (ImageView) itemView.findViewById(R.id.user_avatar);
        }
    }
}
