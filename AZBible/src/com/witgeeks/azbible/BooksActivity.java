package com.witgeeks.azbible;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.witgeeks.azbible.beans.Bible;
import com.witgeeks.azbible.beans.Book;
import com.witgeeks.azbible.beans.Chapter;
import com.witgeeks.azbible.beans.Verse;
import com.witgeeks.azbible.sqlite.DbHelper;
import com.witgeeks.azbible.xml.XMLDOMParser;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Build;

public class BooksActivity extends ActionBarActivity {
	
	static final String BIBLE_RV1960 = "RV1960.xml";
	static final String BIBLE_NVI = "NVI.xml";
	
	public static Bible biblia = new Bible("");
	
	// XML node names
    static final String ATTR_N = "n";
    static final String NODE_BIBLE = "bible";
    static final String NODE_BOOK = "b";
    static final String NODE_CHAPTER = "c";
    static final String NODE_VERSE = "v";
    
    ProgressDialog dialog;
    
    static ArrayList<String> booksTitles = new ArrayList<String>();
    
    private String bibleSelected = BIBLE_RV1960;
    
    private DbHelper db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_books);
		
		setTitle(getString(R.string.app_name) + " - " + getString(R.string.books));
		
		/**Obtencion del parametro enviado de Books**/
		//Bundle bundle = getIntent().getExtras();
		//bibleSelected = bundle.getString("bibleSelected");
		
		
		//Creamos y abrimos la base de datos
		db = new DbHelper(this, "", null, 1);
		db.open();
		
		//Obtenemos un listado de todas las alarmas
		ArrayList<Book> books = new ArrayList<Book>();
		books = (ArrayList<Book>) db.getBooks();
		
		Iterator<Book> iterator = books.iterator();
		
		while(iterator.hasNext()){
			booksTitles.add(iterator.next().getName());
		}
		
		showList();
		
		if (savedInstanceState == null) {
			/*if(biblia.getBooks()!=null&&biblia.getBooks().size()>0&&booksTitles.size()>0){
				/*List<Book> books = biblia.getBooks();
				booksTitles.clear();
				for(int i=0; i<books.size(); i++){
					booksTitles.add(books.get(i).getName());
				}*/
				/*TODO showList();*/
			//} else{
				/*TODO
				dialog = ProgressDialog.show(BooksActivity.this, "", "Loading. Please wait...", true);
				
				ReadBible task = new ReadBible();
				task.execute();
				*/
			//}
			
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
		getMenuInflater().inflate(R.menu.books, menu);
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
	
	public ArrayList<String> readBibleAndReturnBooks() {
        if(biblia.getBooks()!=null)
        	biblia.getBooks().clear();
		
		XMLDOMParser parser = new XMLDOMParser();
        AssetManager manager = getAssets();
        InputStream stream;
        
        ArrayList<String> booksReturn = new ArrayList<String>();
        
        try {
            stream = manager.open(bibleSelected);
            Document doc = parser.getDocument(stream);
 
            // Get elements by name employee
            NodeList nodeList = doc.getElementsByTagName(NODE_BOOK);
            
            //LEO LOS LIBROS
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element elementBook = (Element) nodeList.item(i);
                /*TODO ARREGLAR LUEGO
                Book book = new Book(elementBook.getAttribute(ATTR_N));

                //LEO LOS CAPITULOS
                NodeList bookNodeList = elementBook.getElementsByTagName(NODE_CHAPTER);
                for(int i2 = 0; i2< bookNodeList.getLength(); i2++){
                	Element elementChapter = (Element) bookNodeList.item(i2);
                	
                	Chapter chapter = new Chapter(elementChapter.getAttribute(ATTR_N));
                	
                	//LEO LOS VERSOS
                	NodeList chapterNodeList = elementChapter.getElementsByTagName(NODE_VERSE);
                	for(int i3 = 0; i3< chapterNodeList.getLength(); i3++){
                		Element elementVerse = (Element) chapterNodeList.item(i3);
                		
                		Verse verse = new Verse(elementVerse.getAttribute(ATTR_N), elementVerse.getTextContent());
                		
                		chapter.addVerse(verse);
                	}
                	
                	book.addChapter(chapter);
                	
                }
                
                booksReturn.add(book.getName());
                biblia.addBook(book);
                
                
                /*
                Employee employee = new Employee();
 
                employee.setId(Integer.parseInt(e.getAttribute(ATTR_ID)));
                employee.setName(parser.getValue(e, NODE_NAME));
                employee.setDepartment(parser.getValue(e, NODE_DEPT));
                employee.setType(parser.getValue(e, NODE_TYPE));
                employee.setEmail(parser.getValue(e, NODE_EMAIL));
 
                Address address = new Address();
                address.setLine(parser.getValue(e, NODE_LINE1));
                address.setCity(parser.getValue(e, NODE_CITY));
                address.setState(parser.getValue(e, NODE_STATE));
                address.setZipcode(Long.parseLong(parser.getValue(e, NODE_ZIP)));
                employee.setAddress(address);
 
                employees.add(employee);*/
            }
 
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        
        return booksReturn;
    }
	
	/** Called when the user clicks on a category */
	/*public void mostrarLista(View view) {
		Intent intent = new Intent(MainActivity.this, PantallaSecundaria.class);
		intent.putExtra("idCategoria", view.getTag().toString());
		startActivity(intent);
		dialog = ProgressDialog.show(BooksActivity.this, "", "Loading. Please wait...", true);
		books = readBible();
		
		ListView booksList = (ListView) findViewById(R.id.booksList);
		ArrayList<String> books2 = books;
		//arrayListSubCategorias.addAll(Arrays.asList(getResources().getStringArray(R.array.booksList)));
		ArrayAdapter<String> booksListAdapter = new ArrayAdapter<String>(this, R.layout.item_list_books, books2);
		booksList.setAdapter(booksListAdapter);
		//setUpListItemAction(booksList, super.getActivity());
		 
	}*/
	
	private void showList(){
		ListView booksList = (ListView) findViewById(R.id.booksList);
		ArrayList<String> books2 = booksTitles;
		//arrayListSubCategorias.addAll(Arrays.asList(getResources().getStringArray(R.array.booksList)));
		ArrayAdapter<String> booksListAdapter = new ArrayAdapter<String>(this, R.layout.item_list_books, books2);
		booksList.setAdapter(booksListAdapter);
		setUpListItemAction(booksList, this);
	}
	
	private void setUpListItemAction(final ListView booksList, final Activity context){
		booksList.setOnItemClickListener(new OnItemClickListener() {
			 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
              int position, long id) {
            	/*if(position==0){
            		titlePager.setText(tituloCategoria);
	        		showAllFilteredItemData();
            	} else{
            		titlePager.setText(subtitulosCategoria[position-1]);
            		filterElementsBySubcategory(subtitulosCategoria[position-1]);
            	}
            	
            	viewPager.setCurrentItem(position, true);
            	NavDrawerLayout.closeDrawers();    */
            	Intent intent = new Intent(context, ChaptersActivity.class);
        		intent.putExtra("bookIdSelected", String.valueOf(position+1));
        		intent.putExtra("bookTitleSelected", booksTitles.get(position));
        		startActivity(intent);
            }
		}); 
	}
	
	private class ReadBible extends AsyncTask<Object, Object, Object>{

		@Override
		protected Object doInBackground(Object... arg0) {
			booksTitles = readBibleAndReturnBooks();
			
			return null;
		}
		
		@Override
		protected void onPostExecute(Object result) {
			showList();
			dialog.dismiss();
		}
	}

}
