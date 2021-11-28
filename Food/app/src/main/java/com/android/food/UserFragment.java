package com.android.food;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.food.manager.AccountManager;
import com.android.food.client.ApiUtils;
import com.android.food.models.LoginRequest;
import com.android.food.models.LoginResponse;
import com.android.food.services.YummyFoodService;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    private YummyFoodService mService;

    public UserFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mService = ApiUtils.getFoodService();

        View v = inflater.inflate(R.layout.fragment_user, container, false);

        // chỗ này là xử lý khi nhấn nút đăng nhập nè
        Button btnSignIn = (Button) v.findViewById(R.id.button_signin);
        TextInputEditText userNameEditText = v.findViewById(R.id.et_username);
        EditText passWordEditText = v.findViewById(R.id.et_password);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = Objects.requireNonNull(userNameEditText.getText()).toString();
                String passWord = Objects.requireNonNull(passWordEditText.getText()).toString();
                // Toast.makeText(getActivity(), "username: "+ userName + "\npassword: " + passWord, Toast.LENGTH_SHORT).show();

                LoginRequest data = new LoginRequest(userName, passWord);
                mService.login(data).enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getActivity(), "Bạn đã đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                            // chuyển trạng trái đăng nhập thành True
                            AccountManager.getInstance().setLogin(true);
                            // chuyển sang trang quản lí tài khoản
                            AccountManagerFragment accountManagerFragment = new AccountManagerFragment();
                            FragmentManager manager = getFragmentManager();
                            assert manager != null;
                            manager.beginTransaction()
                                    .replace(R.id.container, accountManagerFragment)
                                    .commit();
                        } else
                            Toast.makeText(getActivity(), "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Log.d("UserLogin", "Fail to get api: " + t.getMessage());
                    }
                });


            }
        });

        // chỗ này là nhấn vào nút đăng ký thì nó mở trang đăng ký nè
        Button btnOpenRegisterFragment = (Button) v.findViewById(R.id.button_signup);
        btnOpenRegisterFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterFragment registerFragment = new RegisterFragment();
                FragmentManager manager = getFragmentManager();
                assert manager != null;
                manager.beginTransaction()
                        .replace(R.id.container, registerFragment)
                        .commit();
            }
        });

        Button btnOpenForgotPasswordFragment = (Button) v.findViewById(R.id.button_forgot_password);
        btnOpenForgotPasswordFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ForgotPasswordFragment forgotPasswordFragment = new ForgotPasswordFragment();
                FragmentManager manager = getFragmentManager();
                assert manager != null;
                manager.beginTransaction()
                        .replace(R.id.container, forgotPasswordFragment)
                        .commit();
            }
        });

        return v;
    }
}