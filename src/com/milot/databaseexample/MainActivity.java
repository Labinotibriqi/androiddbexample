package com.milot.databaseexample;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends ActionBarActivity {

	DocumentDbHandler db;
	List<Document> documents = new ArrayList<Document>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		db = new DocumentDbHandler(this);	         
        
        db.addDocument(new Document("test.pdf", "/Downloads/Tmp"));        
        db.addDocument(new Document("Invoice.doc", "/Documents/Invoices"));
        db.addDocument(new Document("Thor 1080p.mkv", "/Movies"));
        db.addDocument(new Document("Game of Thrones Season 4 Episode 2 720p.mp4", "/Movies"));
        
     
        new AsyncKlasa().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	public class AsyncKlasa extends AsyncTask</*doInBg*/ Void, /*progressUpdate*/ Integer, /*postExec*/ String> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(Void... params) {
			documents = db.getAllDocuments();
			return null;
		}
		
		@Override
		protected void onPostExecute(String result) {			
			super.onPostExecute(result);
			
			for (Document doc : documents) {
	            String document = "Id: "+doc.getID()+" ,Name: " + doc.getName() + " ,Phone: " + doc.getPath();
	            Log.e("Document: ", document);
	        }
			
			// p.sh. adapteri.notifyDataSetChanged();
		}
		
		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}
		
		
	}
	
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
}
