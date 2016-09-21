package sg.com.demo.android.webserver;

import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

import fi.iki.elonen.NanoHTTPD;

/**
 * Created by Mya on 2016-06-15.
 */
public class HelloServer extends NanoHTTPD
{
    private TextView logView;
    private static final Logger LOG = Logger.getLogger(HelloServer.class.getName());
    /**
     * Constructs an HTTP server on given port.
     *
     * @param port
     */
    public HelloServer(int port)
    {
        super(8080);
    }

    public void setLogView(TextView logView) {
        this.logView = logView;
    }

    @Override
    public void start() throws IOException
    {
        log("Starting server...");
        super.start();
    }

    @Override
    public void stop() {
        log("Stopping server...");
        super.stop();
        logView = null;
    }

    private void log(final String s)
    {
        Log.d("Web Server", s);
        if (logView != null) {
            final TextView lv = logView;
            lv.post(new Runnable() {
                @Override
                public void run() {
                    lv.append(s + "\n");
                }
            });
        }
    }


    @Override
    public Response serve(IHTTPSession session)
    {
        Method method = session.getMethod();
        String uri = session.getUri();
        HelloServer.LOG.info(method + " '" + uri + "' ");

        String msg = "<html><body><h1>Hello server</h1>\n";
        Map<String, String> parms = session.getParms();
        if (parms.get("username") == null) {
            msg += "<form action='?' method='get'>\n" + "  <p>Your name: <input type='text' name='username'></p>\n" + "</form>\n";
        } else {
            msg += "<p>Hello, " + parms.get("username") + "!</p>";
        }

        msg += "</body></html>\n";

        return newFixedLengthResponse(msg);
    }

    /**
     * Constructs an HTTP server on given hostname and port.
     *
     * @param hostname
     * @param port
     */
    public HelloServer(String hostname, int port) {
        super(hostname, port);
    }
}
