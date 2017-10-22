package com.myportfolio.httpsmauhdzda.coolbrowser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.webkit.WebIconDatabase;
import android.widget.Button;
import android.widget.SearchView;
import android.view.KeyEvent;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebBackForwardList;
import android.webkit.WebHistoryItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String go_url= "https://mauhdzda.myportfolio.com/";
    private WebView webview_main;
    private ArrayList<String> bookmarks= new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Webview code
        webview_main = (WebView)findViewById(R.id.webview_main);
        WebSettings webSettings = webview_main.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview_main.setWebViewClient(new WebViewClient());
        webview_main.loadUrl(go_url);

        WebIconDatabase.getInstance().open(getDir("icons", MODE_PRIVATE).getPath());
        webview_main.getFavicon();

        //For better webview performance
        webview_main.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webview_main.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview_main.getSettings().setAppCacheEnabled(true);
        webview_main.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setEnableSmoothTransition(true);

        //Header buttons
        Button homeButton = (Button)navigationView.getHeaderView(0).findViewById(R.id.homeButton);
        Button backButton = (Button)navigationView.getHeaderView(0).findViewById(R.id.backButton);
        Button forwardButton =(Button) navigationView.getHeaderView(0).findViewById(R.id.forwardButton);
        Button bookmarkButton = (Button)navigationView.getHeaderView(0).findViewById(R.id.bookmarkButton);

        //Home Button Action
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_url= "https://mauhdzda.myportfolio.com/";
                webview_main.loadUrl(go_url);
            }
        });

        //Back Button Action
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Going back if canGoBack true
                if (webview_main.canGoBack()) {
                    webview_main.goBack();
                }
            }
        });

        //Forward Button Action
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webview_main.canGoForward()) {
                    webview_main.goForward();
                }
            }
        });

        //bookmark Button Action
        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go_url=webview_main.getUrl();
                bookmarks.add(go_url);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webview_main.canGoBack()) {
                        webview_main.goBack();
                        get_history();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //Returns browser history
    public ArrayList<String> get_history(){
        WebBackForwardList history = webview_main.copyBackForwardList();
        ArrayList<String> history_url= new ArrayList<String>();

        for (int i=0; i<history.getSize();i++){
            WebHistoryItem item = history.getItemAtIndex(i);
            String url = item.getUrl();
            history_url.add(url);
            System.out.println( "The URL at index: " + Integer.toString(i) + " is " + url );
        }
        return history_url;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        MenuItem url_bar_f= menu.findItem(R.id.cool_bar);

        SearchView searchView = (SearchView)url_bar_f.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                go_url= query.toString();
                if (!go_url.contains(".") && (!go_url.equalsIgnoreCase("google"))){
                    go_url = "http://www.google.com/#q=" + go_url;
                }
                webview_main.loadUrl(go_url);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_history) {
            ArrayList<String> history_url= get_history();
            Intent historyScreenIntent= new Intent(this,HistoryScreen.class);
            final int result =1;
            historyScreenIntent.putExtra("history_url",history_url);
            startActivityForResult(historyScreenIntent,result);
        }
        else if (id == R.id.nav_bookmarks) {
            Intent bookmarksScreenIntent= new Intent(this,BookmarksActivity.class);
            final int result =1;
            bookmarksScreenIntent.putExtra("bookmarks_url",bookmarks);
            startActivityForResult(bookmarksScreenIntent,result);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        go_url= data.getStringExtra("URL_clicked");
        webview_main.loadUrl(go_url);
    }
}
