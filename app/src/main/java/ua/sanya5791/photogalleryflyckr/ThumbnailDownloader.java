package ua.sanya5791.photogalleryflyckr;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sanya on 28.04.2015.
 */
public class ThumbnailDownloader<Token> extends HandlerThread{
    private static final String TAG = "ThumbnailDownloader";
    private static final boolean isDebug = true;

    private static final int MESSAGE_DOWNLOAD = 0;

    Handler mHandler;
    Map<Token, String> requestMap =
            Collections.synchronizedMap(new HashMap<Token, String>());

    Handler mRespondHandler;

    Listener<Token> mListener;

    public interface Listener<Token>{
        void onSubnailDownloaded(Token token, Bitmap thumbnail);
    }

    public ThumbnailDownloader(Handler respondHandler){
        super(TAG);
        this.mRespondHandler = respondHandler;
    }

    public void setListener(Listener<Token> listener) {
        this.mListener = listener;
    }

    @Override
    protected void onLooperPrepared() {
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == MESSAGE_DOWNLOAD){
                    @SuppressWarnings("unchecked")
                    Token token = (Token)msg.obj;
                    myLogger("Got a request for url: " + requestMap.get(token));
                    handleRequest(token);
                }
            }
        };
    }

    private void handleRequest(final Token token) {
        final String url = requestMap.get(token);

        if(url == null) return;

        try {
            byte[] bitmapBytes = new FlickrFetcher().getUrlBytes(url);
            final Bitmap bitmap = BitmapFactory
                    .decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            myLogger("Bitmap created.");

            mRespondHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (requestMap.get(token) != url) return;

                    requestMap.remove(token);
                    mListener.onSubnailDownloaded(token, bitmap);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Download failed");
        }
    }

    public void queueThumbnail(Token token, String url){

        myLogger("GOT a url: " + url);
        requestMap.put(token, url);

        mHandler
                .obtainMessage(MESSAGE_DOWNLOAD, token)
                .sendToTarget();

    }

    private void myLogger(String message){
        if(!isDebug) return;

        Log.i(TAG, message);
    }

}
