package com.witgeeks.azbible.beans;

public class Verse {
	private int id;
	private int idChapter;
	private int verseNumber;
	private String verse;
	public Verse(int id, int idChapter, int verseNumber, String verse) {
		super();
		this.id = id;
		this.idChapter = idChapter;
		this.verseNumber = verseNumber;
		this.verse = verse;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdChapter() {
		return idChapter;
	}
	public void setIdChapter(int idChapter) {
		this.idChapter = idChapter;
	}
	public int getVerseNumber() {
		return verseNumber;
	}
	public void setVerseNumber(int verseNumber) {
		this.verseNumber = verseNumber;
	}
	public String getVerse() {
		return verse;
	}
	public void setVerse(String verse) {
		this.verse = verse;
	}
	
	/*TODO private String number;
	private String verse;
	
	@Override
	public String toString() {
		return "" + number + ". " + verse + "";
	}

	public Verse(String number, String verse) {
		super();
		this.number = number;
		this.verse = verse;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getVerse() {
		return verse;
	}

	public void setVerse(String verse) {
		this.verse = verse;
	}*/
}
