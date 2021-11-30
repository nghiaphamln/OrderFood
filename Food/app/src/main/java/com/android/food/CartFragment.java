package com.android.food;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.food.adapter.CartAdapter;
import com.android.food.manager.AccountManager;
import com.android.food.manager.CartManager;
import com.android.food.models.CategoriesResponse;
import com.android.food.models.ProductsRespone;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private RecyclerView recyclerViewCartList;
    private CartAdapter cartAdapter;


    public CartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerViewCartList = v.findViewById(R.id.recyclerview);
        recyclerViewCategory(v);

        TextView btnOrder = v.findViewById(R.id.textView16);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!AccountManager.getInstance().isLogin()) {
                    Toast.makeText(getActivity(), "Bạn chưa đăng nhập!", Toast.LENGTH_SHORT).show();
                }
                else if (CartManager.getInstance().getCartList() != null) {
                    Toast.makeText(getActivity(), "Đã đặt sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    CartManager.getInstance().setCartListEmty();
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentManager manager = getFragmentManager();
                    assert manager != null;
                    manager.beginTransaction()
                            .replace(R.id.container, homeFragment)
                            .commit();
                }
                else {
                    Toast.makeText(getActivity(), "Giỏ hàng rỗng!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        if (CartManager.getInstance().getCartList() != null){
            TextView totalFree, shipMoney, totalMoney;
            totalFree = v.findViewById(R.id.totalFeeTxt);
            shipMoney = v.findViewById(R.id.shipMoney);
            totalMoney = v.findViewById(R.id.totalTxt);

            shipMoney.setText("30000");
            double total = 0.0;
            for (int i = 0; i < CartManager.getInstance().getCartList().size(); i++) {
                total += CartManager.getInstance().getCartList().get(i).getPrice();
            }
            totalFree.setText(String.valueOf(total));
            totalMoney.setText(String.valueOf(total + 30000));
        }
        return v;
    }

    private void recyclerViewCategory(View v) {
        ArrayList<ProductsRespone> cartList = CartManager.getInstance().getCartList();
        cartAdapter = new CartAdapter(getActivity(), cartList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(linearLayoutManager.VERTICAL);
        recyclerViewCartList.setLayoutManager(linearLayoutManager);
        recyclerViewCartList.setAdapter(cartAdapter);
    }

}