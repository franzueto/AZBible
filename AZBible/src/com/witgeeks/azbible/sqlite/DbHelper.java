package com.witgeeks.azbible.sqlite;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.witgeeks.azbible.beans.Book;
import com.witgeeks.azbible.beans.Chapter;
import com.witgeeks.azbible.beans.Verse;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DbHelper extends SQLiteOpenHelper {

	private static final String DB_NAME = "RV1960.sqlite";
	//Ruta por defecto de las bases de datos en el sistema Android
	private static String DB_PATH = "/data/data/com.witgeeks.azbible/databases/";
	private static final int DB_SCHEME_VERSION = 1;
	
	private SQLiteDatabase myDataBase;
	private final Context myContext;
	
	public DbHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, DB_NAME, null, DB_SCHEME_VERSION);
		this.myContext = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	* Crea una base de datos vacía en el sistema y la reescribe con nuestro fichero de base de datos.
	* */
	public void createDataBase() throws IOException{
	 
		boolean dbExist = checkDataBase();
		 
		if(dbExist){
			//la base de datos existe y no hacemos nada.
		}else{
			//Llamando a este método se crea la base de datos vacía en la ruta por defecto del sistema
			//de nuestra aplicación por lo que podremos sobreescribirla con nuestra base de datos.
			this.getReadableDatabase();
			try {
				copyDataBase();
			} catch (IOException e) {
				throw new Error("Error copiando Base de Datos");
			}
		}
	}
	 
	/**
	* Comprueba si la base de datos existe para evitar copiar siempre el fichero cada vez que se abra la aplicación.
	* @return true si existe, false si no existe
	*/
	private boolean checkDataBase(){
		File fileDB = null;
		fileDB = myContext.getApplicationContext().getDatabasePath(DB_NAME);
		boolean checkDB = fileDB.exists();
		return (checkDB);
		/*SQLiteDatabase checkDB = null;
		 
		try{ 
			String myPath = DB_PATH + DB_NAME;
			Log.w("SQL", "VA 1");
			checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
			Log.w("SQL", "VA 2");
		}catch(SQLiteException e){
			Log.w("SQL", "VA 3");
			//si llegamos aqui es porque la base de datos no existe todavía.
		}
		Log.w("SQL", "VA 4");
		if(checkDB != null){ 
			Log.w("SQL", "VA 5");
			checkDB.close();
		}
		return checkDB != null ? true : false;*/
	}
	 
	/**
	* Copia nuestra base de datos desde la carpeta assets a la recién creada
	* base de datos en la carpeta de sistema, desde dónde podremos acceder a ella.
	* Esto se hace con bytestream.
	* */
	private void copyDataBase() throws IOException{
		//Abrimos el fichero de base de datos como entrada
		InputStream myInput = myContext.getAssets().open(DB_NAME);
		 
		//Ruta a la base de datos vacía recién creada
		String outFileName = DB_PATH + DB_NAME;
		 
		//Abrimos la base de datos vacía como salida
		OutputStream myOutput = new FileOutputStream(outFileName);
		 
		//Transferimos los bytes desde el fichero de entrada al de salida
		byte[] buffer = new byte[1024];
		int length;
		while ((length = myInput.read(buffer))>0){
		myOutput.write(buffer, 0, length);
		}
		 
		//Liberamos los streams
		myOutput.flush();
		myOutput.close();
		myInput.close();
	}
	 
	public void open() throws SQLException{	 
		//Abre la base de datos
		try {
			createDataBase();
		} catch (IOException e) {
			throw new Error("Ha sido imposible crear la Base de Datos");
		}
		 
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
	}
	 
	@Override
	public synchronized void close() {
	if(myDataBase != null)
	myDataBase.close();
	super.close();
	}
	
	public Book getBook(long _rowIndex) {
		Book book = new Book(-1,null);
		Cursor result = myDataBase.query(true, DataBaseManager.TABLE_BOOK,
				DataBaseManager.BOOK_COLS , DataBaseManager.BOOK_KEY_ID + "=" + _rowIndex, null, null, null,
		null, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) {
		//Si la alarma no existe, devuelve una alarma con valores -1 y -1
		book = new Book(-1,null);
		 
		} else {
		if (result.moveToFirst()) {
			book = new Book(
			result.getInt(result.getColumnIndex(DataBaseManager.BOOK_KEY_ID)),
			result.getString(result.getColumnIndex(DataBaseManager.BOOK_KEY_NAME))
			);
		}
		}
		return book;
	}
	
	public List<Book> getBooks() {
		ArrayList<Book> books = new ArrayList<Book>();
		Cursor result = myDataBase.query(DataBaseManager.TABLE_BOOK,
				DataBaseManager.BOOK_COLS, null, null, null, null, DataBaseManager.BOOK_KEY_ID);
		if (result.moveToFirst())
		do {
			books.add(new Book(
			result.getInt(result.getColumnIndex(DataBaseManager.BOOK_KEY_ID)),
			result.getString(result.getColumnIndex(DataBaseManager.BOOK_KEY_NAME))
			)
			);
		} while(result.moveToNext());
		return books;
	}
	
	public List<Chapter> getChapters(int _bookId) {
		ArrayList<Chapter> chapters = new ArrayList<Chapter>();
		Cursor result = myDataBase.query(DataBaseManager.TABLE_CHAPTER,
				DataBaseManager.CHAPTER_COLS, DataBaseManager.CHAPTER_KEY_ID_BOOK + "=" + _bookId, null, null, null, DataBaseManager.CHAPTER_KEY_ID);
		if (result.moveToFirst())
		do {
			chapters.add(new Chapter(
			result.getInt(result.getColumnIndex(DataBaseManager.CHAPTER_KEY_ID)),
			result.getInt(result.getColumnIndex(DataBaseManager.CHAPTER_KEY_ID_BOOK)),
			result.getInt(result.getColumnIndex(DataBaseManager.CHAPTER_KEY_NUMBER))
			)
			);
		} while(result.moveToNext());
		return chapters;
	}
	
	public List<Verse> getVerses(int _chapterId) {
		ArrayList<Verse> verses = new ArrayList<Verse>();
		Cursor result = myDataBase.query(DataBaseManager.TABLE_VERSE,
				DataBaseManager.VERSE_COLS, DataBaseManager.VERSE_KEY_ID_CHAPTER + "=" + _chapterId, null, null, null, DataBaseManager.VERSE_KEY_ID);
		if (result.moveToFirst())
		do {
			verses.add(new Verse(
			result.getInt(result.getColumnIndex(DataBaseManager.VERSE_KEY_ID)),
			result.getInt(result.getColumnIndex(DataBaseManager.VERSE_KEY_ID_CHAPTER)),
			result.getInt(result.getColumnIndex(DataBaseManager.VERSE_KEY_NUMBER)),
			result.getString(result.getColumnIndex(DataBaseManager.VERSE_KEY))
			)
			);
		} while(result.moveToNext());
		return verses;
	}

}
