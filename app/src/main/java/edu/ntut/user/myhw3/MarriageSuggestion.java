package edu.ntut.user.myhw3;

import android.widget.RadioGroup;

/**
 * Created by user on 2018/3/20.
 */

public class MarriageSuggestion {

    public String getSuggestion(String strSex, int iAgeRange, int numFamily) {

        String strSug = "建議：";

        if (strSex.equals("male")) {
            switch (iAgeRange) {
                case 1://小於30歲
                    if (numFamily < 4)
                        strSug += "趕快結婚";
                    else if (numFamily >= 4 && numFamily <= 10)
                        strSug += "趕快結婚";
                    else
                        strSug += "還不急";
                    break;
                case 2://30~40歲
                    if (numFamily < 4)
                        strSug += "趕快結婚";
                    else if (numFamily >= 4 && numFamily <= 10)
                        strSug += "開始找對象";
                    else
                        strSug += "還不急";
                    break;
                case 3://大於40歲
                    if (numFamily < 4)
                        strSug += "開始找對象";
                    else if (numFamily >= 4 && numFamily <= 10)
                        strSug += "趕快結婚";
                    else
                        strSug += "開始找對象";
                    break;
            }
        } //女性--------------------
        else {
            switch (iAgeRange) {
                case 1://小於28歲
                    if (numFamily < 4)
                        strSug += "趕快結婚";
                    else if (numFamily >= 4 && numFamily <= 10)
                        strSug += "趕快結婚";
                    else
                        strSug += "還不急";
                    break;
                case 2://28~35歲
                    if (numFamily < 4)
                        strSug += "趕快結婚";
                    else if (numFamily >= 4 && numFamily <= 10)
                        strSug += "開始找對象";
                    else
                        strSug += "還不急";
                    break;
                case 3://大於35歲
                    if (numFamily < 4)
                        strSug += "開始找對象";
                    else if (numFamily >= 4 && numFamily <= 10)
                        strSug += "趕快結婚";
                    else
                        strSug += "開始找對象";
                    break;
            }
        }

        return strSug;
    }
}
