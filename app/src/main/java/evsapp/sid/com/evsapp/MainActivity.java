package evsapp.sid.com.evsapp;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    TextView display;

    private class ReadWebpageContents extends AsyncTask<String,Void,Document> {

        @Override
        protected Document doInBackground(String... params) {
            try {
                Document doc = Jsoup.connect(params[0]).get();
                //display.setText(doc.title());
                return doc;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Document document){
            if(document!=null)
            display.setText(document.title());      //PRINTING THE TITLE OF THE WEBPAGE
            else
                display.setText("null");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        display = (TextView)findViewById(R.id.resultDisplay);
        String website = "http://www.cpcb.gov.in/CAAQM/mapPage/frmindiamap.aspx";  //HOMEPAGE OF THE WEBSITE
        ReadWebpageContents mReadWebpageContents = new ReadWebpageContents();
        //try {
            //URL mUrl = new URL(website);
            mReadWebpageContents.execute(website);
       /* } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/


        //display.setText(doc.toString());
        //Elements scriptTag = doc.getElementsByTag("script");
        //display.setText((CharSequence) scriptTag);
        //display.setText(doc.html());

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
