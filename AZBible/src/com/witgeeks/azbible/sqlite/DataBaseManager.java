package com.witgeeks.azbible.sqlite;

public class DataBaseManager {

	public static final String TABLE_BOOK = "Book";
	
	public static final String BOOK_KEY_ID = "_id";
	public static final String BOOK_KEY_NAME = "name";
	
	//Array de strings para su uso en los diferentes métodos
	public static final String[] BOOK_COLS = new String[] { BOOK_KEY_ID, BOOK_KEY_NAME };
	
	public static final String TABLE_CHAPTER = "Chapter";
	
	public static final String CHAPTER_KEY_ID = "_id";
	public static final String CHAPTER_KEY_ID_BOOK = "id_book";
	public static final String CHAPTER_KEY_NUMBER = "chapter_number";
	
	//Array de strings para su uso en los diferentes métodos
	public static final String[] CHAPTER_COLS = new String[] { CHAPTER_KEY_ID, CHAPTER_KEY_ID_BOOK, CHAPTER_KEY_NUMBER };
	
	public static final String TABLE_VERSE = "Verse";
	
	public static final String VERSE_KEY_ID = "_id";
	public static final String VERSE_KEY_ID_CHAPTER = "id_chapter";
	public static final String VERSE_KEY_NUMBER = "verse_number";
	public static final String VERSE_KEY = "verse";
	
	//Array de strings para su uso en los diferentes métodos
	public static final String[] VERSE_COLS = new String[] { VERSE_KEY_ID, VERSE_KEY_ID_CHAPTER, VERSE_KEY_NUMBER, VERSE_KEY };
}
