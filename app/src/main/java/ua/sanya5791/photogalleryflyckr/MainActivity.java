package ua.sanya5791.photogalleryflyckr;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.facebook.FacebookSdk;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ua.sanya5791.photogalleryflyckr.FrPhotoGallery.OnFragmentInteractionListener;

public class MainActivity extends Activity implements OnFragmentInteractionListener {

    private static final String MAIN_DIALOG = "MainDialog";

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final boolean isDebug = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //You need to initialize Facebook SDK before you can use it.
        FacebookSdk.sdkInitialize(getApplicationContext());

//        getFacebookHashKey();

        showChooseServicesDialog();
    }

    //use it only once you need to obtain Hash Key, then comment this method
    private void getFacebookHashKey() {

    // Add code to print out the key hash
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "ua.sanya5791.photogalleryflyckr",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void showChooseServicesDialog(){
        String tag = DFrServices.class.getSimpleName();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(tag);
        if(prev != null){
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        DialogFragment dfr = new DFrServices();
        dfr.show(ft, tag);
    }

    private void myLogger(String message){
        if(isDebug)
            Log.i(TAG, message);
    }
}
