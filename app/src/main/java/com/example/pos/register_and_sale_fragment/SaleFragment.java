package com.example.pos.register_and_sale_fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pos.PosDB;
import com.example.pos.R;
import com.example.pos.ListAdapter;
import com.example.pos.ListModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class SaleFragment extends Fragment {

    private TextInputLayout searchBox;
    private Button addButton,resetButton,searchButton, paidButton;
    private TableLayout resultTableLayout;
    private ScrollView scrollView;
    private TextInputEditText code_value,item_value,unit_quantity,totalPrice,taxPrice,payablePrice;
    private PosDB dbHelper;
    private String [] item_Info;
    private  double total;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sale, container, false);

        searchBox = view.findViewById(R.id.sale_code_value1);

        searchButton = view.findViewById(R.id.btn_search);
        addButton = view.findViewById(R.id.btn_add);
        resetButton = view.findViewById(R.id.sale_reset);
        paidButton = view.findViewById(R.id.paid);

        //resultTableLayout = view.findViewById(R.id.result_table);
        dbHelper = new PosDB(getActivity());
        code_value = view.findViewById(R.id.unit_price_value);
        item_value = view.findViewById(R.id.sale_item_value1);
        unit_quantity = view.findViewById(R.id.unit_quantity_value1);
        totalPrice = view.findViewById(R.id.total_price1);
        taxPrice = view.findViewById(R.id.tax_price1);
        payablePrice = view.findViewById(R.id.payable_price1);

        String code_v = String.valueOf(code_value.getText());

        RecyclerView mRecyclerView;
        mRecyclerView = view.findViewById(R.id.my_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        List<ListModel> regCardList = new ArrayList<>();

        ListAdapter adapter = new ListAdapter(regCardList);
        mRecyclerView.setAdapter(adapter);


        //resultTableLayout = view.findViewById(R.id.result_table);

        //Search Button Clicked
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = code_value.getText().toString().trim();
                if (code.isEmpty()) {
                    code_value.setError("Code is required.");
                    code_value.requestFocus();
                    return;
                }

                // Validate input for item_code
                int item_code = 0;
                try {
                    item_code = Integer.parseInt(code);
                } catch (NumberFormatException e) {
                    code_value.setError("Invalid input: please enter a valid integer.");
                    code_value.requestFocus();
                    return;
                }

                item_Info = dbHelper.searchItemByCode(code);
                if (item_Info[0] != null) {
                    item_value.setText(item_Info[0]);
                } else {
                    item_value.setText("Item not found");
                    Toast.makeText(getContext(), "Item not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Add Button Clicked
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = code_value.getText().toString().trim();
                String uq = unit_quantity.getText().toString().trim();

                // Validate input for item code
                try {
                    int itemCode = Integer.parseInt(code);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Please Search First", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate input for quantity
                if (uq.length() == 0) {
                    Toast.makeText(getContext(), "Quantity required", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Parse input for quantity and unit price
                int quantity;
                double unitPrice;
                try {
                    quantity = Integer.parseInt(uq);
                    unitPrice = Double.parseDouble(item_Info[1]);
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid quantity or unit price", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Validate input for zero quantity
                if (quantity == 0) {
                    Toast.makeText(getContext(), "Quantity must be greater than zero", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Calculate single item price and total price
                double singleItemPrice = quantity * unitPrice;
                total += singleItemPrice;

                // Create new item and add to list
                regCardList.add(new ListModel(code, item_Info[0], uq, unitPrice, quantity, singleItemPrice));

                // Notify adapter about the new item
                adapter.notifyItemInserted(regCardList.size() - 1);
            }
        });


        //Reset Button Clicked
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Resting...", Toast.LENGTH_SHORT).show();
                code_value.setText("");
                item_value.setText(" ");
                totalPrice.setText(" ");
                taxPrice.setText(" ");
                payablePrice.setText(" ");
                unit_quantity.setText(" ");
                regCardList.clear();
                adapter.notifyDataSetChanged();
            }
        });


        //Paid Button clicked
        paidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (total<=0){
                    Toast.makeText(getContext(), "Please Add Product ", Toast.LENGTH_SHORT).show();
                }else
                {
                    totalPrice.setText(String.valueOf(total));
                    double tax = (total*15)/100;
                    taxPrice.setText(String.valueOf(tax));
                    double payable = tax+total;
                    payablePrice.setText(String.valueOf(payable));
                }

            }
        });

        return view;
    }
}

