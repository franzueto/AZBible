package com.witgeeks.azbible;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.witgeeks.azbible.beans.Book;
import com.witgeeks.azbible.beans.Chapter;
import com.witgeeks.azbible.sqlite.DbHelper;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class ChaptersActivity extends ActionBarActivity {

	ArrayList<String> chaptersTitles;
	String bookIdSelected;
	String bookTitleSelected;
	
	private DbHelper db;
	
	private int[] chaptersId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chapters);

		/**Obtencion del parametro enviado de Books**/
		Bundle bundle = getIntent().getExtras();
		bookIdSelected = bundle.getString("bookIdSelected");
		bookTitleSelected = bundle.getString("bookTitleSelected");
		
		setTitle(getString(R.string.app_name) + " - " + bookTitleSelected);
		
		//Creamos y abrimos la base de datos
		db = new DbHelper(this, "", null, 1);
		db.open();
		
		if (savedInstanceState == null) {
			
			if(chaptersTitles==null||chaptersTitles.size()<=0){
				chaptersTitles = new ArrayList<String>();
				
				//Obtenemos un listado de todas las alarmas
				ArrayList<Chapter> chapters = new ArrayList<Chapter>();
				chapters = (ArrayList<Chapter>) db.getChapters(Integer.parseInt(bookIdSelected));
				
				chaptersId = new int[chapters.size()];
				
				for(int i=0;i<chapters.size();i++){
					chaptersId[i] = chapters.get(i).getId();
					chaptersTitles.add(getString(R.string.chapter) + " " + String.valueOf(chapters.get(i).getChapterNumber()));
				}
				
				/*Iterator<Chapter> iterator = chapters.iterator();
				
				while(iterator.hasNext()){
					chaptersTitles.add(getString(R.string.chapter) + " " + String.valueOf(iterator.next().getChapterNumber()));
				}*/
				
				/*TODO ARREGLAR LUEGO
				List<Chapter> bibleChapters = BooksActivity.biblia.getBooks().get(Integer.parseInt(bookIdSelected)).getChapters();
				chapters = new ArrayList<String>();
				for(int i=0; i<bibleChapters.size(); i++){
					chapters.add(getString(R.string.chapter) + " " + bibleChapters.get(i).getNumber());
				}
				*/
			}
			showList();
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
		getMenuInflater().inflate(R.menu.chapters, menu);
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
	
	private void showList(){
		ListView chaptersList = (ListView) findViewById(R.id.chaptersList);
		//arrayListSubCategorias.addAll(Arrays.asList(getResources().getStringArray(R.array.booksList)));
		ArrayAdapter<String> booksListAdapter = new ArrayAdapter<String>(this, R.layout.item_list_books, chaptersTitles);
		chaptersList.setAdapter(booksListAdapter);
		setUpListItemAction(chaptersList, this);
	}
	
	private void setUpListItemAction(final ListView chaptersList, final Activity context){
		chaptersList.setOnItemClickListener(new OnItemClickListener() {
			 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
            	Intent intent = new Intent(context, LessonActivity.class);
        		intent.putExtra("chapterIdSelected", String.valueOf(chaptersId[position]));
        		intent.putExtra("chapterTitleSelected", chaptersTitles.get(position));
        		intent.putExtra("bookIdSelected", bookIdSelected);
        		intent.putExtra("bookTitleSelected", bookTitleSelected);
        		startActivity(intent);
            }
		}); 
	}

}
