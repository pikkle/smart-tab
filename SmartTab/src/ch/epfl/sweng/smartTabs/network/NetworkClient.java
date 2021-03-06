package ch.epfl.sweng.smartTabs.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.smartTabs.music.Tab;

/**
 * @author rphkhr Raphael El-Khoury 212765
 */
public class NetworkClient {
    private final static int READTIMEOUT = 10000;
    private final static int CONNECTIONTIMEOUT = 15000;
    private final static int BADURL = 404;

    public Map<String, URL> fetchTabMap(String url) throws IOException, JSONException {
        return parseFromJson(downloadContent(new URL(url)));
    }

    /**
     * @param url
     *            The URL that contains link to all tablatures
     * @return String The String that will be converted to JSON that contains
     *         tablatures. Parsing from JSON will be done later.
     */
    public String downloadContent(URL url) throws IOException {
        InputStream is = null;

        try {
            HttpURLConnection conn = getConnection(url);
            conn.setReadTimeout(READTIMEOUT);
            conn.setConnectTimeout(CONNECTIONTIMEOUT);
            conn.setRequestMethod("GET");
            conn.connect();
            is = conn.getInputStream();

            int response = conn.getResponseCode();
            if (response == BADURL) {
                throw new IOException("Could not connect, check internet connection");
            }
            String stringContent = readStream(is);
            try {
                JSONObject jsonObj = new JSONObject(stringContent);
                if (jsonObj.length() == 0 || jsonObj.names() == null) {
                    throw new JSONException("Invalid content.");
                }
            } catch (JSONException e) {
                // Toast.makeText(appCont, "Please retry fetching Tab",
                // Toast.LENGTH_SHORT).show();
            }

            return stringContent;
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    public HttpURLConnection getConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }

    private String readStream(InputStream is) {
        BufferedReader reader;
        String line;
        String output = "";
        try {
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            while ((line = reader.readLine()) != null) {
                output += line;
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(output);
    }

    public Map<String, URL> parseFromJson(String s) throws JSONException, MalformedURLException {
        JSONArray jArray = new JSONArray(s);
        HashMap<String, URL> map = new HashMap<String, URL>();

        for (int i = 0; i < jArray.length(); i++) {
            JSONObject jObj = new JSONObject();
            jObj = (JSONObject) jArray.get(i);
            map.put(jObj.getString("name"), new URL(jObj.getString("filename")));
        }
        return map;
    }

    public Tab fetchTab(URL myUrl) throws IOException, JSONException {
        try {

            return Tab.parseTabFromJSON(new JSONObject(downloadContent(myUrl)));
        } catch (JSONException e) {
            throw new JSONException("Bad JSON");
        }
    }

}
