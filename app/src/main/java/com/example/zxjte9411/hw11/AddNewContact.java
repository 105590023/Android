package com.example.zxjte9411.hw11;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Objects;

public class AddNewContact extends Fragment {
    private Spinner mSpinner;
    private EditText mEditTextName, mEditTextPhoneNumber;

    public AddNewContact() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_new_contact, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSpinner = Objects.requireNonNull(getView()).findViewById(R.id.phoneType);
        mEditTextName = getView().findViewById(R.id.editTextInputName);
        mEditTextPhoneNumber = getView().findViewById(R.id.editText);
        mSpinner.setAdapter(ArrayAdapter.createFromResource(Objects.requireNonNull(getActivity()),R.array.phone_type,android.R.layout.simple_spinner_dropdown_item));
    }

    public ContentValues getContentValues() {
        ContentValues newData = new ContentValues();
        newData.put("name", mEditTextName.getText().toString());
        newData.put("phoneNumber", mEditTextPhoneNumber.getText().toString());
        newData.put("phoneType", mSpinner.getSelectedItem().toString());
        mEditTextName.setText("");
        mEditTextPhoneNumber.setText("");
        return newData;
    }
}
