package com.witgeeks.azbible;

import java.util.ArrayList;
import java.util.List;

import com.witgeeks.azbible.beans.Chapter;
import com.witgeeks.azbible.beans.Verse;
import com.witgeeks.azbible.sqlite.DbHelper;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.text.Html;
import android.util.Log;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class LessonActivity extends ActionBarActivity {

	static String versesText;
	
	private DbHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson);

		/**Obtencion del parametro enviado de Books**/
		Bundle bundle = getIntent().getExtras();
		String bookIdSelected = bundle.getString("bookIdSelected");
		String bookTitleSelected = bundle.getString("bookTitleSelected");
		String chapterIdSelected = bundle.getString("chapterIdSelected");
		String chapterTitleSelected = bundle.getString("chapterTitleSelected");
		
		setTitle(bookTitleSelected + " - " + chapterTitleSelected);
		
		Log.w("CHAPTER ID", chapterIdSelected);
		
		//Creamos y abrimos la base de datos
		db = new DbHelper(this, "", null, 1);
		db.open();
		
		if (savedInstanceState == null) {
			versesText = "";
			
			//Obtenemos un listado de todas las alarmas
			ArrayList<Verse> verses = new ArrayList<Verse>();
			verses = (ArrayList<Verse>) db.getVerses(Integer.parseInt(chapterIdSelected));
			
			for(int i=0;i<verses.size();i++){
				versesText += "<b>"+verses.get(i).getVerseNumber() + ".</b>" + " " + verses.get(i).getVerse() + "<br/>";
			}
			
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
			
			/*TODO ARREGLAR LUEGO
			List<Verse> verses = BooksActivity.biblia.getBooks().get(Integer.parseInt(bookIdSelected))
									.getChapters().get(Integer.parseInt(chapterIdSelected))
									.getVerses();
			versesText = "";
			
			for(int i=0; i<verses.size(); i++){
				versesText += "<b>"+verses.get(i).getNumber() + ".</b>" + " " + verses.get(i).getVerse() + "<br/>";
			}
			
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
					*/
		}
	}

	@Override
	public void onPause() {
	    super.onPause();  // Always call the superclass method first
	    db.close();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lesson, menu);
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
		if(id == android.R.id.home){
	        NavUtils.navigateUpFromSameTask(this);
	        return true;
	    }
		return super.onOptionsItemSelected(item);
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
			View rootView = inflater.inflate(R.layout.fragment_lesson,
					container, false);
			
			TextView lessonText = (TextView)rootView.findViewById(R.id.lessonText);
			lessonText.setText(Html.fromHtml(versesText));
			
			return rootView;
		}
	}

}
