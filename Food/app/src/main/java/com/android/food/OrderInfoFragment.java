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
import com.android.food.domain.DetailDomain;
import com.android.food.manager.AccountManager;
import com.android.food.manager.CartManager;
import com.android.food.models.AddOrderRequest;
import com.android.food.models.AddOrderResponse;
import com.android.food.models.ProductsRespone;
import com.android.food.services.YummyFoodService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderInfoFragment extends Fragment {
    YummyFoodService mService = ApiUtils.getFoodService();

    public OrderInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_order_info, container, false);

        TextInputEditText nameEditText, phoneEditText, addressEditText;
        Button btnSubmit;

        nameEditText = v.findViewById(R.id.et_name);
        phoneEditText = v.findViewById(R.id.et_phone);
        addressEditText = v.findViewById(R.id.et_address);
        btnSubmit = v.findViewById(R.id.btn_order);

        btnSubmit.setOnClickListener(v1 -> {
            String name = nameEditText.getText().toString();
            String phone = phoneEditText.getText().toString();
            String address = addressEditText.getText().toString();

            if (name.isEmpty() || phone.isEmpty() || address.isEmpty()) {
                Toast.makeText(getActivity(), "Thông tin không được bỏ trống", Toast.LENGTH_SHORT).show();
            }
            else {
                AddOrderRequest data = new AddOrderRequest();
                data.setName(name);
                data.setAddress(address);
                data.setUsername(AccountManager.getInstance().getUsername());
                data.setPhone(phone);

                ArrayList<DetailDomain> detailList = new ArrayList<>();

                ArrayList<ProductsRespone> cartList = CartManager.getInstance().getCartList();
                Integer total = 0;
                for (int i = 0; i < cartList.size(); i++) {
                    DetailDomain detailDomain = new DetailDomain();
                    detailDomain.setProductId(Integer.parseInt(cartList.get(i).getId()));
                    detailDomain.setQuantity(1);
                    detailList.add(detailDomain);
                    total += cartList.get(i).getPrice();
                }

                data.setDetail(detailList);
                data.setTotalPrice(total + 30000);

                mService.addOrder(data).enqueue(new Callback<AddOrderResponse>() {
                    @Override
                    public void onResponse(Call<AddOrderResponse> call, Response<AddOrderResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getActivity(), "Đã đặt hàng thành công", Toast.LENGTH_SHORT).show();
                            CartManager.getInstance().setCartListEmty();
                            HomeFragment homeFragment = new HomeFragment();
                            FragmentManager manager = getFragmentManager();
                            assert manager != null;
                            manager.beginTransaction()
                                    .replace(R.id.container, homeFragment)
                                    .commit();
                        }
                        else {
                            Toast.makeText(getActivity(), "Đã xảy ra lỗi trong quá trình đặt hàng", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AddOrderResponse> call, Throwable t) {

                    }
                });
            }
        });


        return v;
    }
}