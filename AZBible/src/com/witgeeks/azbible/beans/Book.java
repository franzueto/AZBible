package com.witgeeks.azbible.beans;

import java.util.ArrayList;
import java.util.List;

public class Book {
	private int id;
	private String name;
	
	public Book(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/*private String name;
	private List<Chapter> chapters;
	public Book(String name) {
		super();
		this.name = name;
		this.chapters = new ArrayList<Chapter>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Chapter> getChapters() {
		return chapters;
	}
	public void setChapters(List<Chapter> chapters) {
		this.chapters = chapters;
	}
	public Boolean addChapter(Chapter chapter){
		return this.chapters.add(chapter);
	}
	*/
}
