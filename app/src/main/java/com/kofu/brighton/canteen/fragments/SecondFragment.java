package com.kofu.brighton.canteen.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.kofu.brighton.canteen.R;
import com.kofu.brighton.canteen.models.MealBill;
import com.kofu.brighton.canteen.network.APIService;
import com.kofu.brighton.canteen.network.APIServiceBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SecondFragment extends Fragment {

    private static final int REQUEST_CODE = 1;
    private View mView;
    private CodeScanner mCodeScanner;
    private String mStudentRef;
    private APIService mApiService;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mApiService = APIServiceBuilder.buildService(APIService.class);

        mView = view;

        verifyPermissions();
    }

    private void verifyPermissions() {
        int permissionCheck =
                ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            setUpCodeScanner();
        } else {
            String[] permissions = {Manifest.permission.CAMERA};
            requestPermissions(permissions, REQUEST_CODE);
        }
    }

    private void setUpCodeScanner() {
        CodeScannerView codeScannerView = mView.findViewById(R.id.scanner_v);
        mCodeScanner = new CodeScanner(getActivity(), codeScannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mStudentRef = result.getText();
                        Button lendButton = mView.findViewById(R.id.bn_borrow);
                        lendButton.setEnabled(true);
                        lendButton.setVisibility(View.VISIBLE);
                        lendButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Call mealbillCall =
                                        mApiService.mealbill(new MealBill(mStudentRef, 0.0));

                                mealbillCall.enqueue(new Callback() {
                                    @Override
                                    public void onResponse(Call call, Response response) {
                                        if (response.code() == 200) {
                                            Toast.makeText(getActivity(), "Bill paid", Toast.LENGTH_LONG).show();
                                            Navigation
                                                    .findNavController(getActivity().findViewById(R.id.nav_host_fragment))
                                                    .navigate(SecondFragmentDirections.actionSecondFragmentToFirstFragment());
                                        }else {
                                            Toast
                                                    .makeText(getActivity(),
                                                            "Something went wrong in billing the student",
                                                            Toast.LENGTH_LONG)
                                                    .show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call call, Throwable t) {
                                        Toast
                                                .makeText(getActivity(),
                                                        "Failed",
                                                        Toast.LENGTH_LONG)
                                                .show();
                                    }
                                });
                            }
                        });
//                        Toast.makeText(getActivity(), result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        codeScannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        verifyPermissions();
    }

    @Override
    public void onResume() {
        super.onResume();
//        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        mView = null;
        super.onPause();
    }
}