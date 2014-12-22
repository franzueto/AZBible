package com.witgeeks.azbible;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		/*if(BooksActivity.biblia.getBooks()!=null&&BooksActivity.biblia.getBooks().size()>0){
			Intent intent = new Intent(MainActivity.this, BooksActivity.class);
			startActivity(intent);
			this.finish();
		}*/
	}
	
	/** Called when the user clicks on a category */
	public void goToBooks(View view) {
		String bibleSelected = getBibleSelected(view.getTag().toString());
		
		Intent intent = new Intent(MainActivity.this, BooksActivity.class);
		intent.putExtra("bibleSelected", bibleSelected);
		startActivity(intent);
		this.finish();
	}
	
	private String getBibleSelected(String tag){
		switch(tag){
		case "rv1960":
			return BooksActivity.BIBLE_RV1960;
		case "nvi":
			return BooksActivity.BIBLE_NVI;
		}
		return "";
	}
}
