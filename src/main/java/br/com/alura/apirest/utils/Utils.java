package br.com.alura.apirest.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Utils {
	private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static LocalDate fromString(String date) {
		return LocalDate.parse(date, formatter);
	}
	
	public static String toString(LocalDate date) {
		return formatter.format(date);
	}
}
