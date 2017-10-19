package com.myportfolio.httpsmauhdzda.coolbrowser;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
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
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String go_url= "https://www.google.com";
    private WebView webview_main;

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

        //For better webview performance
        webview_main.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webview_main.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webview_main.getSettings().setAppCacheEnabled(true);
        webview_main.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setEnableSmoothTransition(true);


        Button backButton =(Button) findViewById(R.id.backButton);
        Button forwardButton =(Button) findViewById(R.id.forwardButton);

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
                // Go Forward if canGoForward is frue

                if(webview_main.canGoForward()){
                    webview_main.goForward();
                }
            }
        });

        //URL FROM USER INPUT
        final EditText c_url = (EditText) findViewById(R.id.url_bar);
        c_url.setOnKeyListener(new View.OnKeyListener()
        {
            //If user presses Enter you go to URL
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                if (event.getAction() == KeyEvent.ACTION_DOWN)
                {
                    switch (keyCode)
                    {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            go_url=c_url.getText().toString();
                            webview_main.loadUrl(go_url);
                            return true;
                        default:
                            break;
                    }
                }
                return false;
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
                case KeyEvent.KEYCODE_FORWARD:
                    if (webview_main.canGoForward()) {
                        webview_main.goForward();
                    } else {
                        finish();
                    }
                    return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //Returns browser history
    public void get_history(){
        WebBackForwardList history = webview_main.copyBackForwardList();

        for (int i=0; i<history.getSize();i++){
            WebHistoryItem item = history.getItemAtIndex(i);
            String url = item.getUrl();
            System.out.println( "The URL at index: " + Integer.toString(i) + " is " + url );
        }
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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

        if (id == R.id.nav_home) {
            // Handle the camera action
        }
        else if (id == R.id.nav_history) {

        }
        else if (id == R.id.nav_bookmarks) {

        }

        else if (id == R.id.nav_mau)
        {
            go_url= "https://mauhdzda.myportfolio.com/";
            webview_main.loadUrl(go_url);
        }
        else if (id == R.id.nav_share)
        {
            go_url= "https://www.facebook.com/";
            webview_main.loadUrl(go_url);

        } else if (id == R.id.nav_send)
        {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
