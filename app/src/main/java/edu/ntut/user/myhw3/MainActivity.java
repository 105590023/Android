package edu.ntut.user.myhw3;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private RadioButton newSpnSex;
    private RadioGroup sexRadGrp;
    private RadioButton maleBtn;
    private RadioButton femaleBtn;
    private Spinner SpnMaleAge;
    private Spinner SpnFemaleAge;
    private TextView mHobby;

    private CheckBox mChkBoxMusic, mChkBoxSing, mChkBoxDance,
            mChkBoxTravel, mChkBoxReading, mChkBoxWriting,
            mChkBoxClimbing, mChkBoxSwim, mChkBoxEating,
            mChkBoxPainting;

    private Button mBtnOK;
    private TextView mTxtSug;
    String strMaleAge;
    String strFemaleAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sexRadGrp = (RadioGroup) findViewById(R.id.sexBtnGrp);
        maleBtn = (RadioButton) findViewById(R.id.radBtnMale);
        femaleBtn = (RadioButton) findViewById(R.id.radBtnFemale);
        SpnMaleAge = (Spinner)  findViewById(R.id.maleAge);
        SpnFemaleAge = (Spinner)  findViewById(R.id.femaleAge);
        mHobby = (TextView) findViewById(R.id.hobby);

        mChkBoxMusic = (CheckBox)findViewById(R.id.music);
        mChkBoxSing = (CheckBox)findViewById(R.id.sing);
        mChkBoxDance = (CheckBox)findViewById(R.id.dance);
        mChkBoxTravel = (CheckBox)findViewById(R.id.travel);
        mChkBoxReading = (CheckBox)findViewById(R.id.read);
        mChkBoxWriting = (CheckBox)findViewById(R.id.write);
        mChkBoxClimbing = (CheckBox)findViewById(R.id.climb);
        mChkBoxSwim = (CheckBox)findViewById(R.id.swim);
        mChkBoxEating = (CheckBox)findViewById(R.id.eat);
        mChkBoxPainting = (CheckBox)findViewById(R.id.draw);

        mBtnOK = (Button) findViewById(R.id.btnOK);
        mTxtSug = (TextView) findViewById(R.id.txtSug);

        SpnMaleAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strMaleAge= adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        SpnFemaleAge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                strFemaleAge= adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        mBtnOK.setOnClickListener(btnOKOnClick);
    }

    private View.OnClickListener btnOKOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            MarriageSuggestion ms = new MarriageSuggestion();

            //決定性別--------------------------------------------
            String strNewSex = "none";
                switch (sexRadGrp.getCheckedRadioButtonId()) {
                    case R.id.radBtnMale:
                        strNewSex = getString(R.string.male);
                        break;
                    case R.id.radBtnFemale:
                        strNewSex = getString(R.string.female);
                        break;
                }
            //-----------------------------------------------------

            //決定年齡---------------------------------------------
            int iAgeRange = 0;
                switch (strNewSex){
                    case "male":
                        switch (strMaleAge) {
                            case "小於30歲":
                                iAgeRange = 1;
                                break;
                            case "30~40歲":
                                iAgeRange = 2;
                                break;
                            case "大於40歲":
                                iAgeRange = 3;
                                break;
                        }
                        break;
                    case "female":
                        switch (strFemaleAge) {
                            case "小於28歲":
                                iAgeRange = 1;
                                break;
                            case "28~35歲":
                                iAgeRange = 2;
                                break;
                            case "大於35歲":
                                iAgeRange = 3;
                                break;
                        }
                        break;
                }
            //------------------------------------------------------

            //你的興趣----------------------------------------------
            String s = getString(R.string.yourHobby);

            if (mChkBoxMusic.isChecked())
                s += mChkBoxMusic.getText().toString();

            if (mChkBoxSing.isChecked())
                s += mChkBoxSing.getText().toString();

            if (mChkBoxDance.isChecked())
                s += mChkBoxDance.getText().toString();

            if (mChkBoxTravel.isChecked())
                s += mChkBoxTravel.getText().toString();

            if (mChkBoxReading.isChecked())
                s += mChkBoxReading.getText().toString();

            if (mChkBoxWriting.isChecked())
                s += mChkBoxWriting.getText().toString();

            if (mChkBoxClimbing.isChecked())
                s += mChkBoxClimbing.getText().toString();

            if (mChkBoxSwim.isChecked())
                s += mChkBoxSwim.getText().toString();

            if (mChkBoxEating.isChecked())
                s += mChkBoxEating.getText().toString();

            if (mChkBoxPainting.isChecked())
                s += mChkBoxPainting.getText().toString();
            //-----------------------------------------------------------
            int n = 1;

            mTxtSug.setText(ms.getSuggestion(strNewSex, iAgeRange, n));
            mHobby.setText(s);
        }
    };
}
