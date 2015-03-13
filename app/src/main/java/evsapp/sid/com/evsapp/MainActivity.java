package evsapp.sid.com.evsapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static android.widget.AdapterView.OnItemSelectedListener;


public class MainActivity extends ActionBarActivity implements OnItemSelectedListener {

    public static final String STATE_AND_CITY = "1";
    TextView display;
    TextView temp;
    Spinner stateList,cityList;
    Button getPollutionData = null;
    ArrayAdapter<CharSequence> mStateArrayAdapter=null,mCityArrayAdapter=null;
    ArrayList<String> cities = null;
    ArrayList<String> delhi = null;
    ArrayList<String> uttar_pradesh = null;
    ArrayList<String> andhra_pradesh = null;
    ArrayList<String> bihar = null;
    ArrayList<String> gujarat = null;
    ArrayList<String> haryana = null;
    ArrayList<String> karnataka = null;
    ArrayList<String> maharashtra = null;
    ArrayList<String> tamil_nadu = null;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        //display = (TextView)findViewById(R.id.resultDisplay);
        switch(parent.getId()){
            case R.id.states_list: /*display.setText("state selected");*/setCityList(position);break;

            case R.id.city_list: /*display.setText("city selected");*/break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void setCityList(int position){

        cities.clear();
        switch(position){
            case 0: cities.addAll(andhra_pradesh);break;
            case 1: cities.addAll(bihar);break;
            case 2: cities.addAll(delhi);break;
            case 3: cities.addAll(gujarat);break;
            case 4: cities.addAll(haryana);break;
            case 5: cities.addAll(karnataka);break;
            case 6: cities.addAll(maharashtra);break;
            case 7: cities.addAll(tamil_nadu);break;
            case 8: cities.addAll(uttar_pradesh);break;
        }

        //mCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mCityArrayAdapter.notifyDataSetChanged();
    }

    /*
    private class ReadWebpageContents extends AsyncTask<String, Void, Document> {
        @Override
        protected Document doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect(params[0]).get();
                return doc;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Document document) {
            super.onPostExecute(document);

            temp.setText("Table\n");
            Element table = document.getElementById("Td1").select("table").get(0); //selecting first table
            Elements rows = table.select("tr");
            for (int i = 1; i < rows.size(); i++) { //first row is the col names so skipping it.
                Element row = rows.get(i);
                Elements cols = row.select("td");
                for(int j = 0; j< cols.size(); j++) {
                    temp.append(cols.get(j).text()+" ");
                }
                temp.append("\n");
            }

        }

        private void printTableContent(Document doc) {
            Element mTable = null;
            Elements mRows=null;

            //if((mTable=doc.getElementById("Td1"))==null)
            //    display.append("mtable null");

            if((mRows = mTable.select("tr"))==null)
                display.append("mrows null");
            for(int i=1; i < mRows.size(); i++){
                Element mRow = mRows.get(i);
                Elements mCols = mRow.select("td");
                display.append(mRow.text()+"\n");
                for(int j=0; j<mCols.size(); i++){
                     display.append(mCols.get(j).text()+"\t");
                }
            }

        }
    }*/

    private void initialiseStates(){
        cities = new ArrayList<>();
        delhi = new ArrayList<>(Arrays.asList("D.C.E.","Shadipur","I.T.O.","Dilshad Garden","Dwarka"));
        uttar_pradesh = new ArrayList<>(Arrays.asList("Agra","Lucknow","Kanpur","Varanasi"));
        andhra_pradesh = new ArrayList<>(Arrays.asList("Hyderabad"));
        bihar = new ArrayList<>(Arrays.asList("Patna"));
        gujarat = new ArrayList<>(Arrays.asList("Ahmedabad"));
        haryana = new ArrayList<>(Arrays.asList("Faridabad"));
        karnataka = new ArrayList<>(Arrays.asList("Bangalore"));
        maharashtra = new ArrayList<>(Arrays.asList("Mumbai"));
        tamil_nadu = new ArrayList<>(Arrays.asList("Chennai"));
        cities.addAll(delhi);
    }

    private void initialiseDropDownMenus(){
        stateList = (Spinner)findViewById(R.id.states_list);
        cityList = (Spinner)findViewById(R.id.city_list);
        mStateArrayAdapter = ArrayAdapter.createFromResource(this,R.array.states_list,android.R.layout.simple_spinner_item);
        mStateArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        stateList.setAdapter(mStateArrayAdapter);

        mCityArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,cities);
        mCityArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cityList.setAdapter(mCityArrayAdapter);
        stateList.setOnItemSelectedListener(this);
        cityList.setOnItemSelectedListener(this);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temp = (TextView)findViewById(R.id.page);
        getPollutionData = (Button)findViewById(R.id.buttonGetData);

        initialiseStates();
        initialiseDropDownMenus();

        getPollutionData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] stateAndCity = new String[]{stateList.getSelectedItem().toString(),cityList.getSelectedItem().toString()};
                Intent displayResult = new Intent(getApplicationContext(),DisplayResult.class);
                displayResult.putExtra(STATE_AND_CITY, stateAndCity);
                startActivity(displayResult);
            }
        });


        //String base = "http://www.cpcb.gov.in/CAAQM/frmCurrentDataNew.aspx?StationName=";


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
