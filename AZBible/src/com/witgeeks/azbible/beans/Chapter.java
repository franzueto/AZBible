package com.witgeeks.azbible.beans;

import java.util.ArrayList;
import java.util.List;

public class Chapter {
	private int id;
	private int idBook;
	private int chapterNumber;
	public Chapter(int id, int idBook, int chapterNumber) {
		super();
		this.id = id;
		this.idBook = idBook;
		this.chapterNumber = chapterNumber;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdBook() {
		return idBook;
	}
	public void setIdBook(int idBook) {
		this.idBook = idBook;
	}
	public int getChapterNumber() {
		return chapterNumber;
	}
	public void setChapterNumber(int chapterNumber) {
		this.chapterNumber = chapterNumber;
	}
	
	/*TODO private String number;
	private List<Verse> verses;
	
	public Chapter(String number) {
		super();
		this.number = number;
		this.verses = new ArrayList<Verse>();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public List<Verse> getVerses() {
		return verses;
	}

	public void setVerses(List<Verse> verses) {
		this.verses = verses;
	}
	
	public Boolean addVerse(Verse verse){
		return this.verses.add(verse);
	}*/
}
