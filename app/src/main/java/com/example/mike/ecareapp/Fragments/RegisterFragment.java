package com.example.mike.ecareapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.mike.ecareapp.Activities.AccountActivity;
import com.example.mike.ecareapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegisterFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RegisterFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegisterFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterFragment newInstance(int param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getInt(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_register, container, false);

        TextInputEditText name = (TextInputEditText) view.findViewById(R.id.edNames);
        TextInputEditText email = (TextInputEditText) view.findViewById(R.id.edEmail);
        TextInputEditText location = (TextInputEditText) view.findViewById(R.id.edLocation);
        TextInputEditText password = (TextInputEditText) view.findViewById(R.id.edPassword);

        android.support.v7.widget.AppCompatTextView signin = (android.support.v7.widget.AppCompatTextView) view.findViewById(R.id.tvSignIn);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment login = LoginFragment.newInstance(mParam1,"");
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment,login);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        AppCompatButton signup = (AppCompatButton) view.findViewById(R.id.btnSignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment login = LoginFragment.newInstance(mParam1,"");
                FragmentTransaction transaction1 = getFragmentManager().beginTransaction();
                transaction1.replace(R.id.fragment,login);
                transaction1.addToBackStack(null);
                transaction1.commit();

                try {
                    finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
