    package com.myportfolio.httpsmauhdzda.coolbrowser;

    import android.content.Intent;
    import android.os.Bundle;
    import android.support.annotation.Nullable;
    import android.support.v7.app.AppCompatActivity;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.ArrayAdapter;
    import android.widget.ListAdapter;
    import android.widget.ListView;
    import android.widget.Toast;

    import java.util.ArrayList;

    /**
     * Created by macuser on 21/10/17.
     */

    public class HistoryScreen extends AppCompatActivity {

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            setContentView(R.layout.history_activity);

            //Receives the history from Main Activity
            Intent receive_History=getIntent();
            ArrayList<String> historyArray= receive_History.getExtras().getStringArrayList("history_url");

            //Puts the url list into the ListView
            final ListAdapter l_adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,
                    historyArray);
            final ListView historyList= (ListView) findViewById(R.id.historyList);
            historyList.setAdapter(l_adapter);

            //Sends back the URL
            final Intent send_url= new Intent();

            historyList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String url_picked= String.valueOf(historyList.getItemAtPosition(position));
                    send_url.putExtra("URL_clicked", url_picked);
                    setResult(RESULT_OK,send_url);
                    finish();
                }
            });
        }
    }

