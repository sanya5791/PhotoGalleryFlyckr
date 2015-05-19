package ua.sanya5791.photogalleryflyckr;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
//import ua.sanya5791.photogalleryflyckr.FlickrFetcher;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

import ua.sanya5791.photogalleryflyckr.Model.FlickrItem;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FrPhotoGallery.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FrPhotoGallery#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FrPhotoGallery extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = "FrPhotoGallery";
    private static final boolean isDebug = true;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private ArrayList<FlickrItem> mItems;

    private GridView gridView;

    private ThumbnailDownloader<ImageView> mThumbnailThread;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FrPhotoGallery.
     */
    // TODO: Rename and change types and number of parameters
    public static FrPhotoGallery newInstance(String param1, String param2) {
        FrPhotoGallery fragment = new FrPhotoGallery();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FrPhotoGallery() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setRetainInstance(true);

        new FetchItemsTask().execute();

        mThumbnailThread = new ThumbnailDownloader<ImageView>(new Handler());
        mThumbnailThread.setListener(new ThumbnailDownloader.Listener<ImageView>() {
            @Override
            public void onSubnailDownloaded(ImageView imageView, Bitmap thumbnail) {
                if(isVisible()){
                    imageView.setImageBitmap(thumbnail);
                }
            }
        });
        mThumbnailThread.start();
        mThumbnailThread.getLooper();
        myLogger("Background thread is started");


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mThumbnailThread.quit();
        myLogger("Background thread is destroyed");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fr_photo_gallery, container, false);
        gridView = (GridView) v.findViewById(R.id.gridView2);

        setupAdapter();

        // Inflate the layout for this fragment
        return v;
    }


    private void setupAdapter() {
        if(getActivity() == null || gridView == null)   return;

        if(mItems != null){
            ArrayAdapter<FlickrItem> adapter = new GalleryAdapter(mItems);
//            ArrayAdapter<FlickrItem> adapter = new ArrayAdapter<FlickrItem>(getActivity(),
//                    android.R.layout.simple_gallery_item, mItems);
            gridView.setAdapter(adapter);
        }else
            gridView.setAdapter(null);


    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private class FetchItemsTask extends AsyncTask<Void, Void, ArrayList<FlickrItem>>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected ArrayList<FlickrItem> doInBackground(Void... params) {
//            try{
//                String result = new FlickrFetcher().getUrl("http://www.google.com");
//                Log.i(TAG, "Fetched contents of URL: " + result);
//            }catch (IOException ioe){
//                Log.e(TAG, "Failed to fetch URL: ", ioe);
//            }

//            new FlickrFetcher().fetchItems();
            return new FlickrFetcher().fetchItems();
        }

        @Override
        protected void onPostExecute(ArrayList<FlickrItem> items) {
            super.onPostExecute(items);

            mItems = items;
            setupAdapter();
        }

     }

    private class GalleryAdapter extends ArrayAdapter<FlickrItem> {
        public GalleryAdapter(ArrayList<FlickrItem> items) {
            super(getActivity(), 0, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.gallery_item, parent, false);
            }

            ImageView imageView = (ImageView) convertView
                    .findViewById(R.id.gallery_item_imageView);
            imageView.setImageResource(R.drawable.dog);
            FlickrItem flickrItem = getItem(position);
            mThumbnailThread.queueThumbnail(imageView, flickrItem.getUrl());

            return convertView;
        }
    }

    private void myLogger(String message){
        if(!isDebug) return;

        Log.i(TAG, message);
    }
}
