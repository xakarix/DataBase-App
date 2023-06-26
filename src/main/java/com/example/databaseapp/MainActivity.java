package com.example.databaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import  android.widget.Button;
import android.widget.CheckBox;
import  android.widget.EditText;
import  android.view.View;

import  android.util.Log;
import  android.database.Cursor;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btn_insert;
    public Button btn_delete;
    public Button btn_select;
    public Button btn_search;

    public EditText edit_name;
    public EditText edit_surname;
    public EditText edit_delete;
    public EditText edit_search;
    public EditText edit_age;
    public EditText edit_male;
    public EditText edit_nationality;
    public TableLayout table;

    public static Spinner genderSpinner;
    public static CheckBox recordCheckBox;

    DataManager dm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_insert = (Button) findViewById(R.id.insertBtn);
        btn_delete = (Button) findViewById(R.id.deleteBtn);
        btn_select = (Button) findViewById(R.id.selectBtn);
        btn_search = (Button) findViewById(R.id.searchBtn);

        edit_name = (EditText)findViewById(R.id.nameText);
        edit_surname = (EditText)findViewById(R.id.surnameText);
        edit_delete = (EditText)findViewById(R.id.deleteText);
        edit_search = (EditText)findViewById(R.id.searchText);
        edit_age = (EditText)findViewById(R.id.ageText);
        edit_male = (EditText)findViewById(R.id.maleText);
        edit_nationality = (EditText)findViewById(R.id.nationalityText);
        table = (TableLayout)findViewById(R.id.table);

        dm = new DataManager(this);
        btn_select.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        btn_insert.setOnClickListener(this);
        btn_search.setOnClickListener(this);

        genderSpinner = (Spinner)findViewById(R.id.genderSpinner);
        recordCheckBox = (CheckBox) findViewById(R.id.recordCheckBox);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,
                new String[]{"All results","Male", "Female"});
        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genderAdapter);
    }

    public void showData(Cursor c){
        if(c.moveToFirst()) {
            do{
                @SuppressLint("Range") String name = c.getString(c.getColumnIndex(DataManager.TABLE_ROW_NAME));
                @SuppressLint("Range") String surname = c.getString(c.getColumnIndex(DataManager.TABLE_ROW_SURNAME));
                @SuppressLint("Range") String age = c.getString(c.getColumnIndex(DataManager.TABLE_ROW_AGE));
                @SuppressLint("Range") String male = c.getString(c.getColumnIndex(DataManager.TABLE_ROW_MALE));
                @SuppressLint("Range") String nationality = c.getString(c.getColumnIndex(DataManager.TABLE_ROW_NATIONALITY));

                System.out.println("NAME " + name);
                System.out.println("SURNAME " + surname);
                System.out.println("AGE " + age);
                System.out.println("MALE/FEMALE " + male);
                System.out.println("NATIONALITY " + nationality);

            }while(c.moveToNext());
        }

        if (c.moveToFirst()) {
            TableRow headerRow = new TableRow(this);
            headerRow.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT));

            String[] columnNames = c.getColumnNames();
            for (int i = 0; i < columnNames.length; i++) {
                TextView headerTextView = new TextView(this);
                headerTextView.setText(columnNames[i]);

                TableRow.LayoutParams layoutParams;
                if (i == 0) {
                    layoutParams = new TableRow.LayoutParams(
                            0,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            0.3f);
                }else if( i == 1) {
                    layoutParams = new TableRow.LayoutParams(
                            0,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            0.8f);
                }else if( i == 3) {
                    layoutParams = new TableRow.LayoutParams(
                            0,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            0.4f);
                } else if( i == 4) {
                    layoutParams = new TableRow.LayoutParams(
                            0,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            0.7f);
                }else {
                    layoutParams = new TableRow.LayoutParams(
                            0,
                            TableRow.LayoutParams.WRAP_CONTENT,
                            1f);
                }
                headerTextView.setLayoutParams(layoutParams);

                headerRow.addView(headerTextView);
            }

            table.addView(headerRow);

            do {
                TableRow dataRow = new TableRow(this);
                dataRow.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT));

                for (int i = 0; i < columnNames.length; i++) {
                    TextView dataTextView = new TextView(this);
                    @SuppressLint("Range") String value = c.getString(c.getColumnIndex(columnNames[i]));
                    dataTextView.setText(value);

                    TableRow.LayoutParams layoutParams;
                    if (i == 0) {
                        layoutParams = new TableRow.LayoutParams(
                                0,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                0.3f);
                    } else if( i == 1) {
                        layoutParams = new TableRow.LayoutParams(
                                0,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                0.8f);
                    } else if( i == 3) {
                        layoutParams = new TableRow.LayoutParams(
                                0,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                0.4f);
                    } else if( i == 4) {
                        layoutParams = new TableRow.LayoutParams(
                                0,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                0.7f);
                    } else {
                        layoutParams = new TableRow.LayoutParams(
                                0,
                                TableRow.LayoutParams.WRAP_CONTENT,
                                1f);
                    }
                    dataTextView.setLayoutParams(layoutParams);

                    dataRow.addView(dataTextView);
                }

                table.addView(dataRow);
            } while (c.moveToNext());
        }

        c.close();
    }




    public boolean checkInfo(){

        boolean check = false;

        if(edit_name.getText().toString().isEmpty() ) return true;
        else if(edit_surname.getText().toString().isEmpty() ) return true;
        else if(edit_male.getText().toString().isEmpty() || !edit_male.getText().toString().matches("Male") || !edit_male.getText().toString().matches("Female")) return true;
        else if(edit_age.getText().toString().isEmpty() ) return true;
        else if(edit_nationality.getText().toString().isEmpty() ) return true;


        return check;
    }

    @SuppressLint("SetTextI18n")
    public void fillRand(){

        Pattern pattern = Pattern.compile("^[a-zA-Z]+$");
        Pattern patternAge = Pattern.compile("[^0-9]+$");

        Matcher matcherName = pattern.matcher(edit_name.getText().toString());
        Matcher matcherSur = pattern.matcher(edit_surname.getText().toString());

        Matcher matcherAge = patternAge.matcher(edit_age.getText().toString());
        Matcher matcherNat = pattern.matcher(edit_nationality.getText().toString());


        Random rand = new Random();
        String [] randNames = {"Aron", "Adam", "Barbara", "Czesław", "Danuta",
                "Ewa", "Franciszek", "Grażyna", "Henryk", "Irena",
                "Jan", "Krystyna", "Leon", "Maria", "Norbert",
                "Olga", "Paweł", "Renata", "Teresa", "Waldemar",
                "Zofia", "Marcin", "Elżbieta", "Marek", "Julia" };
        String [] randSurnames = {"Kowalski", "Wiśniewski", "Wójcik", "Kowalczyk", "Kamiński",
                "Lewandowski", "Zieliński", "Szymański", "Woźniak", "Dąbrowski",
                "Kozłowski", "Jankowski", "Mazur", "Kwiatkowski", "Krawczyk",
                "Piotrowski", "Grabowski", "Nowakowski", "Pawłowski", "Michalski",
                "Nowicki", "Adamczyk", "Dudek", "Zając", "Kruk"};
        String [] randNationality = {"American", "Australian", "British", "Canadian", "Chinese",
                "Dutch", "Egyptian", "French", "German", "Greek",
                "Indian", "Italian", "Japanese", "Mexican", "Polish",
                "Russian", "South African", "Spanish", "Swedish", "Thai",
                "Turkish", "Ukrainian", "Vietnamese", "Welsh", "Zimbabwean"};

        int rndAge = rand.nextInt(100)+2;
        int ind = rand.nextInt(25);

        if(edit_name.getText().toString().isEmpty() || !matcherName.matches()) edit_name.setText(randNames[ind]);
        if(edit_surname.getText().toString().isEmpty() || !matcherSur.matches()) edit_surname.setText(randSurnames[ind]);
        if(edit_male.getText().toString().isEmpty() || !edit_male.getText().toString().matches("Male") || !edit_male.getText().toString().matches("Female")){
        if(edit_male.getText().toString().endsWith("a")){
                edit_male.setText("Female");
        }
        else{
                edit_male.setText("Male");
        }
        }
        if(edit_age.getText().toString().isEmpty() || matcherAge.matches()) edit_age.setText(String.valueOf(rndAge));
        if(edit_nationality.getText().toString().isEmpty() || !matcherNat.matches()) edit_nationality.setText(randNationality[ind]);


        dm.insert(edit_name.getText().toString(),
                edit_surname.getText().toString(),
                edit_age.getText().toString(),
                edit_male.getText().toString(),
                edit_nationality.getText().toString());

    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.insertBtn:{

                if (checkInfo()){
                    fillRand();
                }
                else{

                    dm.insert(edit_name.getText().toString(),
                            edit_surname.getText().toString(),
                            edit_age.getText().toString(),
                            edit_male.getText().toString(),
                            edit_nationality.getText().toString());
                }

                break;
            }
            case R.id.selectBtn: {
                table.removeAllViews();
                showData(dm.selectAll());
                break;
            }
            case R.id.searchBtn: {
                table.removeAllViews();
                showData(dm.searchName(edit_search.getText().toString()));
                break;
            }
            case R.id.deleteBtn:{
                table.removeAllViews();
                dm.delete(edit_delete.getText().toString());
                showData(dm.selectAll());
                break;
            }

        }
    }
}