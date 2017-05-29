package com.example.mike.ecareapp.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mike.ecareapp.Adapter.MainAdapter;
import com.example.mike.ecareapp.Custom.ProcessUser;
import com.example.mike.ecareapp.Database.DatabaseHandler;
import com.example.mike.ecareapp.Interfaces.NavigationInterface;
import com.example.mike.ecareapp.Pojo.AppiontmentItem;
import com.example.mike.ecareapp.Pojo.DoctorItem;
import com.example.mike.ecareapp.Pojo.MainObject;
import com.example.mike.ecareapp.Pojo.PatientItem;
import com.example.mike.ecareapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements NavigationInterface{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam1;
    private String mParam2;

    Spinner hospital, specialties;

    private OnFragmentInteractionListener mListener;
    private MainAdapter mainAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(int param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.viewHomeItems);
        hospital = (Spinner) view.findViewById(R.id.spinnerHospital);
        specialties = (Spinner) view.findViewById(R.id.spinnerSpecialty);
        List<MainObject> itemList = new ArrayList<>();
        switch (mParam1){
            case 0:
                if (prepareDoctorItemList() != null)
                    itemList = prepareDoctorItemList();

                break;
            case 1:
                hospital.setVisibility(View.GONE);
                specialties.setVisibility(View.GONE);
                if (preparePatientItemList() != null)
                    itemList = preparePatientItemList();
                break;
        }

        mainAdapter = new MainAdapter(getContext(), itemList, this );

        recyclerView.setAdapter(mainAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        return view;
    }

    public List<MainObject> prepareHome( int type){
        switch (type){
            case 0:
                String REGISTER_URL = "https://footballticketing.000webhostapp.com/doctor";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    parseDoctorData(new JSONArray(response));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
                break;
            case 1:

                 REGISTER_URL = "https://footballticketing.000webhostapp.com/doc_shedule";
                 stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    parsePatientData(new JSONArray(response));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);
                break;
        }


        return mainObjectList;
    }


    //This method will parse json data
    private void parseDoctorData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            DoctorItem doctorItem = new DoctorItem();
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);
                doctorItem.setName(json.getString("name"));
                doctorItem.setEmail(json.getString("email"));
                doctorItem.setPassword(json.getString("password"));
                doctorItem.setHospital(json.getString("hospital"));
                doctorItem.setSpecialty(json.getString("specialty"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
            mainObjectList.add(doctorItem);
        }

        //Notifying the adapter that data has been added or changed
        mainAdapter.notifyDataSetChanged();
        setMainList(mainObjectList);
    }

    //This method will parse json data doctors patients only
    private void parsePatientData(final JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            //Creating the superhero object
            JSONObject json = null;
            try {
                //Getting json
                json = array.getJSONObject(i);
                final int pat_id  = json.getInt("pat_id");


                String REGISTER_URL = "https://footballticketing.000webhostapp.com/patient";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONArray array1 = new JSONArray(response);
                                    JSONObject object = array1.getJSONObject(0);
                                    PatientItem patientItem = new PatientItem();
                                    patientItem.setName(object.getString("name"));
                                    patientItem.setEmail(object.getString("email"));
                                    patientItem.setLocation(object.getString("location"));
                                    mainObjectList.add(patientItem);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });
                RequestQueue requestQueue = Volley.newRequestQueue(getContext());
                requestQueue.add(stringRequest);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Adding the superhero object to the list
        }

        //Notifying the adapter that data has been added or changed
        mainAdapter.notifyDataSetChanged();
        setMainList(mainObjectList);
    }

    List<MainObject> mainObjectList = new ArrayList<>();

    private void setMainList(List<MainObject> mainObjectList) {
        this.mainObjectList = mainObjectList;
    }

    public List<MainObject> prepareDoctorItemList(){
        DatabaseHandler handler = new DatabaseHandler(getContext());
        List<MainObject> mainObjectList = new ArrayList<>();
        for (DoctorItem doctorItem : handler.getAllDoctors())
            mainObjectList.add(doctorItem);
        return mainObjectList;
    }

    public List<MainObject> preparePatientItemList(){
        DatabaseHandler handler = new DatabaseHandler(getContext());
        List<AppiontmentItem> appiontmentItemList = new ArrayList<>();
        List<PatientItem> patientItemList = new ArrayList<>();
        List<MainObject> mainObjectList = new ArrayList<>();

        for (AppiontmentItem appiontmentItem : handler.getPatientAppointmets(mParam2))
            appiontmentItemList.add(appiontmentItem);
        for (AppiontmentItem appiontmentItem: appiontmentItemList){
            if (!patientItemList.isEmpty()) {
                for (PatientItem patientItem : patientItemList) {
                    if (!patientItem.getPat_id().contentEquals(handler.getPatient(appiontmentItem.getPat_id()).getPat_id())) {
                        patientItemList.add(handler.getPatient(appiontmentItem.getPat_id()));
                    }
                }
            }else
                patientItemList.add(handler.getPatient(appiontmentItem.getPat_id()));
        }
        for (PatientItem patientItem : patientItemList)
            mainObjectList.add(patientItem);

        return mainObjectList;
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

    @Override
    public void fragmentNavigation(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        AppointmentBookingFragment appointmentBookingFragment = (AppointmentBookingFragment) fragment;
        appointmentBookingFragment.show(manager,"Appointments");
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
