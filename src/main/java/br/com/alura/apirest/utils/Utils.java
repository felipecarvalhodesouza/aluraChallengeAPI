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
	
	public static boolean equalsIgnoreCase(String base, String compara) {
		if( (base == null && compara != null) || (base != null && compara == null) ){
			return false;
		}
		
		if( (base.trim().isEmpty() && !compara.trim().isEmpty()) || (!base.trim().isEmpty() && compara.trim().isEmpty()) ) {
			return false;
		}
		
		return base.trim().equalsIgnoreCase(compara.trim());
	}
}
