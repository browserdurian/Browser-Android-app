package com.myportfolio.httpsmauhdzda.coolbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import static com.myportfolio.httpsmauhdzda.coolbrowser.R.id.webview_main;

/**
 * Created by macuser on 22/10/17.
 */

public class BookmarksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bookmark_activity);

        //Receives bookmarks from Main Activity
        Intent receive_bookmarks=getIntent();
        ArrayList<String> bookmarksArray= receive_bookmarks.getExtras().getStringArrayList("bookmarks_url");

        //Puts the url list into the ListView
        final ListAdapter l_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                bookmarksArray);
        final ListView bookmarksList= (ListView) findViewById(R.id.bookmarksList);
        bookmarksList.setAdapter(l_adapter);

        //Sends back the URL
        final Intent send_url= new Intent();

        bookmarksList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url_picked= String.valueOf(bookmarksList.getItemAtPosition(position));
                send_url.putExtra("URL_clicked", url_picked);
                setResult(RESULT_OK,send_url);
                finish();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == android.view.KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case android.view.KeyEvent.KEYCODE_BACK:
                 finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
