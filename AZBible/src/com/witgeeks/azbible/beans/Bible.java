package com.witgeeks.azbible.beans;

import java.util.ArrayList;
import java.util.List;

public class Bible {
	private String name;
	private List<Book> books;
	public Bible(String name) {
		super();
		this.name = name;
		this.books = new ArrayList<Book>();
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Book> getBooks() {
		return books;
	}
	public void setBooks(List<Book> books) {
		this.books = books;
	}
	public Boolean addBook(Book book){
		return this.books.add(book);
	}
}
