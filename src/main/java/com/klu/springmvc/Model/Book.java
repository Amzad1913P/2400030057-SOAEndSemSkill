package com.klu.springmvc.Model;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="Book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String author;
	private String isbn_num;

	public Book() {
	}

	public Book(String name, String author, String isbn_num) {
		this.name = name;
		this.author = author;
		this.isbn_num = isbn_num;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsbn_num() {
		return isbn_num;
	}

	public void setIsbn_num(String isbn_num) {
		this.isbn_num = isbn_num;
	}
}
