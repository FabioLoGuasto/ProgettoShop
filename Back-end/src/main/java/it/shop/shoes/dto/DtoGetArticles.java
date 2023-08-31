package it.shop.shoes.dto;

import lombok.Data;

@Data
public class DtoGetArticles {
	
	private Long id;
	private String code;
	private int size;
	private String negozio;
	private String brand;
	private String category;
	private double price;
	private int discount;
	private String season;
	private Integer sellOut;
	private String supplier;
	
	
	
}
