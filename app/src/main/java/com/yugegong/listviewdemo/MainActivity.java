package com.yugegong.listviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    DemoAdapter mAdapter;
    ListView mListView;
    CachedList<DemoData> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView)this.findViewById(R.id.listview);
        mList = new CachedList<>(new DemoService());

        mAdapter = new DemoAdapter(this, R.id.listview, mList);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
