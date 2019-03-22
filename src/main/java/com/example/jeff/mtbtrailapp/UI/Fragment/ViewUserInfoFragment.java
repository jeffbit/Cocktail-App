package com.example.jeff.mtbtrailapp.UI.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.jeff.mtbtrailapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViewUserInfoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViewUserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewUserInfoFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //UI
    private TextView firstName, lastName, age, phone, sex;
    private Button edit, save, cancel, delete;

    private static final EditUserInfoFragment sEditUserInfoFragment = new EditUserInfoFragment();

    //Firebase
    FirebaseFirestore firebaseFirestore;
    String userId;


    public ViewUserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewUserInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewUserInfoFragment newInstance(String param1, String param2) {
        ViewUserInfoFragment fragment = new ViewUserInfoFragment();
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

        firebaseFirestore = FirebaseFirestore.getInstance();


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_user_info, container, false);
        firstName = view.findViewById(R.id.userViewFirstNameTV2);
        lastName = view.findViewById(R.id.userViewLastNameTV2);
        age = view.findViewById(R.id.userViewAgeTV2);
        sex = view.findViewById(R.id.userViewSexTV2);
        phone = view.findViewById(R.id.userViewPhoneTV2);
        edit = view.findViewById(R.id.userViewEditBTN);
        cancel = view.findViewById(R.id.userViewBackBTN);
        delete = view.findViewById(R.id.userViewDeleteBTN);

        //loads data
        loadData();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                firebaseFirestore.collection("users").document(userId).delete();

            }
        });


        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.userActivityFrame, sEditUserInfoFragment);
                ft.commit();
            }
        });


        return view;
    }


    public void loadData() {
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DocumentReference users = firebaseFirestore.collection("users").document(userId);
        users.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot documentSnapshot = task.getResult();
                    if (documentSnapshot.exists()) {
                        String firstNameString = documentSnapshot.get("firstName").toString();
                        String lastNameString = documentSnapshot.get("lastName").toString();
                        String ageString = documentSnapshot.get("age").toString();
                        String sexString = documentSnapshot.get("sex").toString();
                        String phoneString = documentSnapshot.get("phone").toString();
                        //Toast.makeText(getContext(), firstNameString, Toast.LENGTH_SHORT).show();
                        firstName.setText(firstNameString);
                        lastName.setText(lastNameString);
                        age.setText(ageString);
                        sex.setText(sexString);
                        phone.setText(phoneString);
                    }

                }
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
