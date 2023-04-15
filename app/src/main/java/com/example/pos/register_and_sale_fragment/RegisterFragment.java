package com.example.pos.register_and_sale_fragment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.pos.PosDB;
import com.example.pos.R;
import com.google.android.material.textfield.TextInputLayout;


public class RegisterFragment extends Fragment {
    
     private Context context;
    private TextInputLayout reg_code;
    private TextInputLayout reg_name;
    private TextInputLayout reg_unit;
    private TextInputLayout reg_price;
    private  Button Btn_reset;
    private SQLiteDatabase database;
    private  Button btn_reset,btn_save;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        // Initialize database
        PosDB dbHelper = new PosDB(getContext());
        database = dbHelper.getWritableDatabase();
         btn_save = view.findViewById(R.id.btn_save);
         btn_reset = view.findViewById(R.id.btn_reset);


        reg_code = view.findViewById(R.id.code_value);
        reg_name = view.findViewById(R.id.item_value);
        reg_unit = view.findViewById(R.id.unit_value);
        reg_price = view.findViewById(R.id.unit_price_value);



        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reg_code.getEditText().setText("");
                reg_name.getEditText().setText("");
                reg_unit.getEditText().setText("");
                reg_price.getEditText().setText("");
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rcode = reg_code.getEditText().getText().toString();
                String rname = reg_name.getEditText().getText().toString();
                String runit = reg_unit.getEditText().getText().toString();
                String rprice = reg_price.getEditText().getText().toString();

                // Validate input for item_code
                int item_code = 0;
                try {
                    item_code = Integer.parseInt(rcode);
                } catch (NumberFormatException e) {
                    reg_code.setError("Invalid input: please enter a valid integer.");
                    reg_code.requestFocus();
                    return;
                }

                // Validate input for item_name
                if (rname.isEmpty()) {
                    reg_name.setError("Item name is required.");
                    reg_name.requestFocus();
                    return;
                }

                // Validate input for unit
                if (runit.isEmpty()) {
                    reg_unit.setError("unit is required.");
                    reg_unit.requestFocus();
                    return;
                }

                // Validate input for item_price
                double item_price = 0.0;
                try {
                    item_price = Double.parseDouble(rprice);
                } catch (NumberFormatException e) {
                    reg_price.setError("Invalid input: please enter a valid  number.");
                    reg_price.requestFocus();
                    return;
                }

                // Add item to database
                dbHelper.addItem(item_code, rname, runit, item_price);
                // Clear input fields
                reg_code.getEditText().setText("");
                reg_name.getEditText().setText("");
                reg_unit.getEditText().setText("");
                reg_price.getEditText().setText("");
            }
        });


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Close the database when the fragment is destroyed
        if (database != null) {
            database.close();
        }
    }
}
