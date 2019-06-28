package com.example.github;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.github.Models.Item;
import com.example.github.Models.Users;
import com.example.github.network.EndPoint;
import com.example.github.network.RetrofitBase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EndPoint endPoint= (EndPoint) RetrofitBase.getData().create(EndPoint.class);
    private EndlessRecyclerViewScrollListener scrollListener;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView rvItems;
    private ItemsAdapter madapter;
    private List<Item> itemList;
    private Parcelable parcelable;
    private String name;
    EditText mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mName = (EditText) findViewById(R.id.user_search);
        rvItems = (RecyclerView) findViewById(R.id.rv_users);
        linearLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(linearLayoutManager);
        // Retain an instance so that you can call `resetState()` for fresh searches


    }

    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`
        endPoint.Users(name,offset).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
//                List<String> authors = response.body().get(1).getAuthors();
                for (Item item : response.body().getItems()) {

                    String name = item.getLogin();
                    String avatar = item.getAvatarUrl();

                    Log.v("Main Activity :" , name);
                    Log.v("Main Activity :" , avatar);

                    int size =response.body().getItems().size();
                    Log.v("Main Activity :" , String.valueOf(size));

                    itemList.add(item);

                    ItemRepository itemRepository = new ItemRepository(getApplicationContext());
                    itemRepository.insertItem(name, avatar);
                }
                rvItems.setAdapter(madapter);
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.e("Main Activity : " , "Failer at Retrofit");
            }
        });

        madapter.notifyDataSetChanged();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        parcelable = (Parcelable) linearLayoutManager.onSaveInstanceState();
        outState.putParcelable("State", parcelable);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState != null)
            parcelable = savedInstanceState.getParcelable("State");
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (parcelable != null) {
            linearLayoutManager.onRestoreInstanceState(parcelable);
        }
    }

    public void search(View view) {

        name = mName.getText().toString();

        endPoint.Users(name,0).enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
//                List<String> authors = response.body().get(1).getAuthors();
                itemList = response.body().getItems();

                for (Item item : response.body().getItems()) {

                    String name = item.getLogin();
                    String avatar = item.getAvatarUrl();

                    Log.v("Main Activity :" , name);
                    Log.v("Main Activity :" , avatar);
                    int size =response.body().getItems().size();
                    Log.v("Main Activity :" , String.valueOf(size));

                    ItemRepository itemRepository = new ItemRepository(getApplicationContext());
                    itemRepository.insertItem(name, avatar);
                }

                madapter =new ItemsAdapter(itemList , MainActivity.this);
                rvItems.setAdapter(madapter);

            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Log.e("Main Activity : " , "Failer at Retrofit");
            }
        });

        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list

                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        rvItems.addOnScrollListener(scrollListener);
    }
}
