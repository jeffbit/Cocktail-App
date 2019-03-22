package com.example.jeff.mtbtrailapp.UI.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.jeff.mtbtrailapp.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link EditUserInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link EditUserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditUserInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    //UI
    private EditText firstName, lastName, age, phone;
    private RadioButton male, female;
    private RadioGroup sexGroup;
    private Button save, cancel;

    //firestore
    private FirebaseFirestore firebaseFirestore;


    public EditUserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditUserInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditUserInfoFragment newInstance(String param1, String param2) {
        EditUserInfoFragment fragment = new EditUserInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //firebase initialize
        firebaseFirestore = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.fragment_edit_user_info, container, false);
        firstName = view.findViewById(R.id.userFirstNameET);
        lastName = view.findViewById(R.id.userLastNameET);
        age = view.findViewById(R.id.userAgeET);
        sexGroup = view.findViewById(R.id.userRG);
        male = view.findViewById(R.id.userMaleRB);
        female = view.findViewById(R.id.userFemaleRB);
        phone = view.findViewById(R.id.userPhoneET);
        save = view.findViewById(R.id.userSaveBTN);
        cancel = view.findViewById(R.id.userBackBTN);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUserData();
            }
        });


        return view;

    }

    private void validateInput() {


    }


    public void addUserData() {

        String userId = FirebaseAuth.getInstance().getUid();
        String firstNameString = firstName.getText().toString().trim();
        String lastNameString = lastName.getText().toString().trim();
        String ageString = age.getText().toString().trim();
        String phoneString = phone.getText().toString().trim();
        String sexString = null;
        if (male.isChecked()) {
            sexString = "male";
        } else if (female.isChecked()) {
            sexString = "female";

        }
        if (firstNameString == null) {
            firstName.setError("First name required");
            firstName.requestFocus();
            return;
        }
        if (lastNameString == null) {
            lastName.setError("Last name required");
            lastName.requestFocus();
            return;
        }

        if (phoneString == null) {
            phone.setError("Phone number is requried");
            phone.requestFocus();
            return;
        }
        if (ageString == null) {
            age.setError("age is required");
            age.requestFocus();
            return;
        }
        //creates a new user to add to FireStore Database
        Map<String, String> userMap = new HashMap<>();
        userMap.put("firstName", firstNameString);
        userMap.put("lastName", lastNameString);
        userMap.put("age", ageString);
        userMap.put("phone", phoneString);
        userMap.put("sex", sexString);

        //adds a new document with generated ID
        firebaseFirestore.collection("users").document(userId).set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                getActivity().onBackPressed();
            }
        });


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
