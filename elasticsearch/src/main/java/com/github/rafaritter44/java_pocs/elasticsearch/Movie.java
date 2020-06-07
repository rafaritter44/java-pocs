package com.github.rafaritter44.java_pocs.elasticsearch;

import java.util.List;

public class Movie {
	
	private String title;
	private int year;
	private List<String> genres;
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(final String title) {
		this.title = title;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(final int year) {
		this.year = year;
	}
	
	public List<String> getGenres() {
		return genres;
	}
	
	public void setGenres(final List<String> genres) {
		this.genres = genres;
	}

	@Override
	public String toString() {
		return "Movie [title=" + title + ", year=" + year + ", genres=" + genres + "]";
	}
	
}
