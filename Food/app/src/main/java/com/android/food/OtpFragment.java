package com.android.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.food.client.ApiUtils;
import com.android.food.manager.AccountManager;
import com.android.food.models.ForgotPasswordResponse;
import com.android.food.models.OtpRequest;
import com.android.food.models.OtpResponse;
import com.android.food.services.YummyFoodService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpFragment extends Fragment {
    public OtpFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        YummyFoodService mService = ApiUtils.getFoodService();

        View v = inflater.inflate(R.layout.fragment_otp, container, false);

        Button btnSend = (Button) v.findViewById(R.id.button_send);
        TextInputEditText otpEditText = v.findViewById(R.id.et_otp);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = Objects.requireNonNull(otpEditText.getText()).toString();
                String email = AccountManager.getInstance().getEmail();
                OtpRequest data = new OtpRequest();
                data.setOtp(otp);
                data.setEmail(email);

                mService.checkOtp(data).enqueue(new Callback<OtpResponse>() {
                    @Override
                    public void onResponse(Call<OtpResponse> call, Response<OtpResponse> response) {
                        if (!response.isSuccessful()) {
                            Toast.makeText(getActivity(), "OTP không đúng!", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            AccountManager.getInstance().setOtp(otp);
                            ResetPasswordFragment resetPasswordFragment = new ResetPasswordFragment();
                            FragmentManager manager = getFragmentManager();
                            assert manager != null;
                            manager.beginTransaction()
                                    .replace(R.id.container, resetPasswordFragment)
                                    .commit();
                        }
                    }

                    @Override
                    public void onFailure(Call<OtpResponse> call, Throwable t) {

                    }
                });
            }
        });

        return v;
    }
}