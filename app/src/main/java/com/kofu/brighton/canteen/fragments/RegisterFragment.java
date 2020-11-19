package com.kofu.brighton.canteen.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.kofu.brighton.canteen.MainActivityCallBacks;
import com.kofu.brighton.canteen.R;
import com.kofu.brighton.canteen.models.Register;
import com.kofu.brighton.canteen.network.APIService;
import com.kofu.brighton.canteen.network.APIServiceBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private TextInputEditText mFirst_name_field;
    private TextInputEditText mSurname_field;
    private TextInputEditText mEmail_field;
    private TextInputEditText mP_number_field;
    private TextInputEditText mPswrd_field;
    private TextInputEditText mPswrd_confirm_field;
    private APIService mApiService;

    private static final String TAG = "RegisterFragment";

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
    public static RegisterFragment newInstance(String param1, String param2) {
        RegisterFragment fragment = new RegisterFragment();
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
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MainActivityCallBacks activity = (MainActivityCallBacks) getActivity();
        activity.hideFab();

        mFirst_name_field = view.findViewById(R.id.tiet_f_name);
        mSurname_field = view.findViewById(R.id.tiet_surname);
        mEmail_field = view.findViewById(R.id.tiet_email_r);
        mP_number_field = view.findViewById(R.id.tiet_p_number);
        mPswrd_field = view.findViewById(R.id.tiet_password_r);
        mPswrd_confirm_field = view.findViewById(R.id.tiet_re_password);

        mApiService = APIServiceBuilder.buildService(APIService.class);

        view.findViewById(R.id.bn_reg_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleRegistration();
            }
        });
    }

    private void handleRegistration() {
        Log.d(TAG, "handleRegistration: Enter");
        String firstName = mFirst_name_field.getText().toString();
        String surname = mSurname_field.getText().toString();
        String email = mEmail_field.getText().toString();
        String phoneNumber = mP_number_field.getText().toString();
        String password = mPswrd_field.getText().toString();
        String passwordConfirm = mPswrd_confirm_field.getText().toString();

        Log.d(TAG, "handleRegistration: Constructing call");
        Call registerCall =
                mApiService.register(
                        new Register(firstName, surname, email, phoneNumber, password, passwordConfirm
                        )
                );

        Log.d(TAG, "handleRegistration: Enqueuing call");
        registerCall.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d(TAG, "onResponse: Success code: " + response.code() + "");
                if (response.code() == 201) {
                    Toast.makeText(getActivity(), "Registered successfully", Toast.LENGTH_LONG).show();
                    Navigation
                            .findNavController(getActivity().findViewById(R.id.nav_host_fragment))
                            .navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment());
                } else {
                    Toast.makeText(getActivity(), "Failed to create account", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d(TAG, "onResponse: Failed: " + t.getMessage() + "");
                Toast.makeText(getActivity(), "Failure: " + t.getMessage() + "", Toast.LENGTH_LONG).show();
            }
        });
    }
}