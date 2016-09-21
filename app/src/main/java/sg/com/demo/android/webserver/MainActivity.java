package sg.com.demo.android.webserver;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity
{
/*
    private WebServer server;
*/

    String ipAddress;

    private MyServer server;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
        int ip = wifiInfo.getIpAddress();
        ipAddress = Formatter.formatIpAddress(ip);

       /* Switch serverSwitch = (Switch) findViewById(R.id.server_status_button);
        serverSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                if (on)
                    try
                    {
                        startServer();

                    } catch (IOException e) {
                        Log.e("Web Server", "Couldn't start server", e);
                    }
                else
                    stopServer();
            }
        });*/
    }

    @Override
    public void onResume()
    {
        super.onResume();
        try
        {
            server = new MyServer(ipAddress);
            server.setLogView((TextView) findViewById(R.id.server_log));
            server.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if(server != null)
        {
            server.stop();
        }
    }

//    private void startServer() throws IOException {
//        server = new WebServer();
//        server.setLogView((TextView) findViewById(R.id.server_log));
//        server.start();
//    }
//
//    private void stopServer() {
//        server.stop();
//        server = null;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }
}
