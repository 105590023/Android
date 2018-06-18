package com.example.zxjte9411.hw10;


import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewContact extends Fragment {

    private ArrayAdapter<CharSequence> arrayAdapter;
    private Spinner mSpinner;
    private EditText mEditTextName, mEditTextPhoneNumber;

    public AddNewContact() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_new_contact, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSpinner = (Spinner) getView().findViewById(R.id.phoneType);
        mEditTextName = (EditText) getView().findViewById(R.id.editTextInputName);
        mEditTextPhoneNumber = (EditText) getView().findViewById(R.id.editText);
        // spinner setting
        arrayAdapter = ArrayAdapter.createFromResource(getActivity(),R.array.phone_type,android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);
    }

    public ContentValues getContentValues() {
        ContentValues newData = new ContentValues();
        newData.put("name", mEditTextName.getText().toString());
        newData.put("phoneNumber", mEditTextPhoneNumber.getText().toString());
        newData.put("phoneType", mSpinner.getSelectedItem().toString());
        return newData;
    }
}
