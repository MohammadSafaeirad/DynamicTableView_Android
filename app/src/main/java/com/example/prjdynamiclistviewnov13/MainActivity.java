package com.example.prjdynamiclistviewnov13;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import model.Country;
import model.FileManagement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener,
        AdapterView.OnItemLongClickListener, DialogInterface.OnClickListener {

    EditText edCName, edCCapital;

    Button btnAdd, btnUpdate, btnSort;

    ListView lvCountries;

    ArrayList<Country> countryList;

    ArrayAdapter<Country> countryAdapter;

    AlertDialog.Builder alertDialog;

    int pos = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {

        edCName = findViewById(R.id.edCName);
        edCCapital = findViewById(R.id.edCCapital);

        btnAdd = findViewById(R.id.btnAdd);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnSort = findViewById(R.id.btnSort);

        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnSort.setOnClickListener(this);

        lvCountries = findViewById(R.id.lvCountries);
        lvCountries.setOnItemClickListener(this);
        lvCountries.setOnItemLongClickListener(this);


        countryList = new ArrayList<Country>();
//        countryList.add(new Country("France", "Paris"));
//        countryList.add(new Country("India", "Delhi"));

        countryList = FileManagement.readFile(this, "countries.txt");

        countryAdapter = new ArrayAdapter<Country>(this, R.layout.one_item, countryList);
        lvCountries.setAdapter(countryAdapter);

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Deletion");
        alertDialog.setMessage("Do you want to want to remove? (Y/N)");
        alertDialog.setPositiveButton("Yes", this );
        alertDialog.setNegativeButton("No", this);



    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btnAdd) {
            addCountry(v);
        }
        if (id == R.id.btnUpdate) {
            updateCountry(v);
        }
        if (id == R.id.btnSort) {
            sortList(v);
        }
    }

    private void sortList(View v) {
        Collections.sort(countryList);
        countryAdapter.notifyDataSetChanged();
    }

    private void updateCountry(View v) {
        String name = edCName.getText().toString();
        String capital = edCCapital.getText().toString();
        Country country = new Country(name, capital);
        int pos = countryExists(country);
        if (pos != -1) {
            countryList.set(pos, country);
            countryAdapter.notifyDataSetChanged();

            Snackbar.make(v, "The country with the name " + country.getcName() + " has been updated successfully!", Snackbar.LENGTH_LONG).show();
        }
        else {
            Snackbar.make(v, "The country with the name " + country.getcName() + " does not exist!", Snackbar.LENGTH_LONG).show();
        }

    }

    private void addCountry(View v) {
        String name = edCName.getText().toString();
        String capital = edCCapital.getText().toString();
        Country country = new Country(name, capital);
        if (countryExists(country) == -1 && !name.equals("") && !capital.equals("")){
            countryList.add(country);
            countryAdapter.notifyDataSetChanged();
            Snackbar.make(v, "The country with the name " + country.getcName() + " has been added successfully!", Snackbar.LENGTH_LONG).show();

            clearWidgets();
        }
        else {
            Snackbar.make(v, "The country with the name " + country.getcName() + " is already added to the list!", Snackbar.LENGTH_LONG).show();

        }
    }

    private void clearWidgets() {
        edCName.setText(null);
        edCCapital.setText(null);
        edCName.requestFocus();
    }

    private int countryExists(Country country) {
        return countryList.indexOf(country);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = countryList.get(position).getcName();
        String capital = countryList.get(position).getcCapital();

        edCName.setText(name);
        edCCapital.setText(capital);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        pos = position;
        alertDialog.create().show();
        return false;
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        if (which == DialogInterface.BUTTON_POSITIVE) {
            countryList.remove(pos);
            countryAdapter.notifyDataSetChanged();
        }
        if (which == DialogInterface.BUTTON_NEGATIVE) {

        }

    }
}