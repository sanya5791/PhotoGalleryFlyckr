package ua.sanya5791.photogalleryflyckr.Presenter;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.Gravity;
import android.widget.Toast;

import ua.sanya5791.photogalleryflyckr.DFrServices;
import ua.sanya5791.photogalleryflyckr.FrPhotoGallery;
import ua.sanya5791.photogalleryflyckr.R;

/**
 * Created by sanya on 30.04.2015.
 */
public class ServicesLauncherPresenterImpl implements ServicesLauncherPresenter {
    private Activity mActivity;
    public ServicesLauncherPresenterImpl(Activity curActivity){
        this.mActivity = curActivity;
    }

    @Override
    public void flickr() {
        Toast.makeText(mActivity, "Pressed button: " + mActivity.getString(R.string.bt_flickr),
                Toast.LENGTH_LONG).show();


        FrPhotoGallery frPhotoGallery = new FrPhotoGallery();
        FragmentManager fm = mActivity.getFragmentManager();
        FragmentTransaction ft;

        //show FrPhotoGallery
        ft = fm.beginTransaction().add(R.id.frameLayout, frPhotoGallery,
                FrPhotoGallery.class.getSimpleName());

        //finish DFrServices
        ft.remove(fm.findFragmentByTag(DFrServices.class.getSimpleName()));
        ft.commit();

    }

    @Override
    public void facebook() {
        Toast.makeText(mActivity, "Service: " + mActivity.getString(R.string.bt_facebook)
                + " is under developing yet.\nTry another.",
                Toast.LENGTH_LONG).show();
//                Toast.LENGTH_LONG).setGravity(Gravity.CENTER_HORIZONTAL, 0, 0).show();

    }

    @Override
    public void google() {
                Toast.makeText(mActivity, "Service: " + mActivity.getString(R.string.bt_google)
                        + " is on developing yet. Try another.",
                        Toast.LENGTH_LONG).show();
    }
}
