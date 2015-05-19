package ua.sanya5791.photogalleryflyckr;

import android.net.Uri;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import ua.sanya5791.photogalleryflyckr.Model.FlickrItem;

/**
 * Created by sanya on 25.04.2015.
 * Fetch data from www.flickr.com
 */
public class FlickrFetcher {
    public static final String TAG = "FlickrFetcher";
    public static final String NAME = "sanya5791";
    public static final String API_KEY = "7269e3ec68b5600167ee7c79e30beb1b";
    public static final String ENDPOINT = "https://api.flickr.com/services/rest/";
    public static final String PARM_EXTRAS = "extras";
    public static final String METHOD_GET_RECENT = "flickr.photos.getRecent";
    public static final String EXTRA_SMALL_URL = "url_s";

    public static final String XML_PHOTO = "photo";


    byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try{
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if(connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                return null;
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer)) > 0 ){
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally {
            connection.disconnect();
        }
    }

     public String getUrl(String urlSpec) throws IOException{
        return new String(getUrlBytes(urlSpec));
    }

    public ArrayList<FlickrItem> fetchItems(){

        ArrayList<FlickrItem> items = new ArrayList<FlickrItem>();

        try{
            String url = Uri.parse(ENDPOINT).buildUpon()
                    .appendQueryParameter("method", METHOD_GET_RECENT)
                    .appendQueryParameter("api_key", API_KEY)
                    .appendQueryParameter("extras", EXTRA_SMALL_URL)
                    .build().toString();
            Log.i(TAG, "Has built url: " + url);
            String xmlString = getUrl(url);
            Log.i(TAG, "Has built xmlString: " + xmlString);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(xmlString));

            parseItems(items, parser);
        }catch (IOException ioe){
            Log.e(TAG, "Failed to fetch items", ioe);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        return items;
    }

    void parseItems(ArrayList<FlickrItem> items, XmlPullParser parser)
            throws XmlPullParserException, IOException {
        int eventType = parser.next();

        while (eventType != XmlPullParser.END_DOCUMENT){
            if (eventType == XmlPullParser.START_TAG &&
                    XML_PHOTO.equals(parser.getName())) {
                String id = parser.getAttributeValue(null, "id");
                String caption = parser.getAttributeValue(null, "title");
                String smallUrl = parser.getAttributeValue(null, EXTRA_SMALL_URL);

                FlickrItem item = new FlickrItem();
                item.setId(id);
                item.setCaption(caption);
                item.setUrl(smallUrl);
                items.add(item);
            }
            eventType = parser.next();
        }
    }
}
