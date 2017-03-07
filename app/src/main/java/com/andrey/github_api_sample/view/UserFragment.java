package com.andrey.github_api_sample.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andrey.github_api_sample.App;
import com.andrey.github_api_sample.R;
import com.andrey.github_api_sample.adapter.UsersListAdapter;
import com.andrey.github_api_sample.data.User;
import com.andrey.github_api_sample.endpoint.GitHubClient;
import com.andrey.github_api_sample.endpoint.GitHubService;

import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class UserFragment extends Fragment {

    private static final String TAG = "UserFragment";
    private RecyclerView mRecyclerView;

//    private Realm realm = null;
    private GitHubService service;
    private Observable<List<User>> usersList;
    private Subscription subscription;
//    private Observable<User> userObservable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_user);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(App.getContext()));

        createSubscription();

        return view;
    }

    public void createSubscription(){
        service = GitHubClient.getGitHubService();
        usersList = service.getUsersList();
        subscription = usersList.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<User>>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onNext(List<User> users) {
                        Log.i(TAG, "onNext: List size = " + users.size());
                        // data to realm
                        /*try {
                            realm = Realm.getDefaultInstance();
                            Realm finalRealm = realm;
                            realm.executeTransaction(realm1 -> {
                                for (User user : users) {
                                    Log.i(TAG, "onNext: User id = " + user.getId());
                                    finalRealm.copyToRealmOrUpdate(user);
                                }
                            });
                        } finally {
                            if (realm != null) {
                                realm.close();
                            }
                        }*/
                        mRecyclerView.setAdapter(new UsersListAdapter(users));
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause: Subscription is " + subscription.isUnsubscribed());
        if (!subscription.isUnsubscribed()){
        subscription.unsubscribe();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
        createSubscription();
    }
}
