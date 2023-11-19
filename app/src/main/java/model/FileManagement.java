package model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileManagement {

    public static ArrayList<Country> readFile(Context context, String filename) {
        ArrayList<Country> countryList = new ArrayList<Country>();

        AssetManager assMan = context.getResources().getAssets();
        try {
            InputStreamReader isr = new InputStreamReader(assMan.open(filename));

            BufferedReader br = new BufferedReader(isr);
            String oneLine = br.readLine();
            while (oneLine != null) {
                StringTokenizer st = new StringTokenizer(oneLine, ",");
                String cName = st.nextToken();
                String cCapital = st.nextToken();

                Country country = new Country(cName, cCapital);
                countryList.add(country);

                oneLine = br.readLine();
            }
            br.close();
            isr.close();

        } catch (Exception e) {
            Log.d("Error", e.getMessage());
        }

        return countryList;
    }

}
