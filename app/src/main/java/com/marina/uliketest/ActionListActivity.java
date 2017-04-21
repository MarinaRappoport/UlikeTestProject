package com.marina.uliketest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.marina.uliketest.adapters.ActionAdapter;

public class ActionListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new ActionAdapter(this));
    }
}
