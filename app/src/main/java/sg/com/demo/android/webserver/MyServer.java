package sg.com.demo.android.webserver;

import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Mya on 2016-06-17.
 */
public class MyServer extends NanoHTTPD
{
    private final static int PORT = 8080;
    private TextView logView;

    private String staticIP;

    public MyServer(String staticIP) throws IOException
    {
        super(PORT);
        this.staticIP = staticIP;
        // start();
    }

    public MyServer() throws IOException
    {
        super(PORT);
       // start();
    }

    @Override
    public void start() throws IOException
    {
        log("Starting server...");
        log("Running! Point your browers to http://" + staticIP + ":" + PORT + "/");
        super.start();
    }

    private void log(final String s)
    {
        Log.d("Web Server", s);
        if (logView != null)
        {
            final TextView lv = logView;
            lv.post(new Runnable() {
                @Override
                public void run() {
                    lv.append(s + "\n");
                }
            });
        }
    }

    public void setLogView(TextView logView) {
        this.logView = logView;
    }

    @Override
    public Response serve(IHTTPSession session)
    {
        String msg = "<html><body><h1>Hello server</h1>\n";

        Map<String, String> parms = session.getParms();
        if(parms.get("tagid") != null){
            msg = parms.get("tagid");
            log("Tag ID: " + parms.get("tagid"));
        }
        else if (parms.get("username") == null)
        {
            msg += "<form action='?' method='get'>\n" + "  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
            log("Current remote machine: " +session.getRemoteHostName());
            log("Current remote ip address: " +session.getRemoteIpAddress());
        }
        else
        {
            msg += "<p>Hello, " + parms.get("username") + "!</p>";
            log("Current user: " + parms.get("username"));
            log("Current remote machine: " +session.getRemoteHostName());
            log("Current remote ip address: " +session.getRemoteIpAddress());
        }

        msg += "</body></html>\n";

        return newFixedLengthResponse( msg + "</body></html>\n" );
    }
}