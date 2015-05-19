package ua.sanya5791.photogalleryflyckr;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.plus.PlusOneButton;

import ua.sanya5791.photogalleryflyckr.Presenter.ServicesLauncherPresenter;
import ua.sanya5791.photogalleryflyckr.Presenter.ServicesLauncherPresenterImpl;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link DFrServices.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DFrServices#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DFrServices extends DialogFragment implements Button.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    ServicesLauncherPresenter presenter;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DFrServices.
     */
    // TODO: Rename and change types and number of parameters
    public static DFrServices newInstance(String param1, String param2) {
        DFrServices fragment = new DFrServices();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public DFrServices() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        setStyle(DialogFragment.STYLE_NO_TITLE, 0);

        presenter =  new ServicesLauncherPresenterImpl(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dfr_services, container, false);
        Button btFlickr = (Button) view.findViewById(R.id.bt_flickr);
        Button btFacebook = (Button) view.findViewById(R.id.bt_facebook);
        Button btGoogle = (Button) view.findViewById(R.id.bt_google);

        btFlickr.setOnClickListener(this);
        btFacebook.setOnClickListener(this);
        btGoogle.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
//        try {
//            mListener = (OnFragmentInteractionListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        mListener = null;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id){
            case R.id.bt_flickr:
                presenter.flickr();

                break;
            case R.id.bt_facebook:
                presenter.facebook();
                break;
            case R.id.bt_google:
                presenter.google();
                break;
        }

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

}
