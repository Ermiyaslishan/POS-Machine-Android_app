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

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Close the database when the fragment is destroyed
        if (database != null) {
            database.close();
        }
    }
}