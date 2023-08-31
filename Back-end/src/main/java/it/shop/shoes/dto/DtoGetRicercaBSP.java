package it.shop.shoes.dto;

import lombok.Data;

@Data
public class DtoGetRicercaBSP {
	
	private Long id;
	private String code;
	private Integer size;
	private Long negozio;
	private String brand;
	private String category;
	private double price;
	private int discount;
	private String season;
	private Integer sellOut;
	private Long supplier;
	private String model;
	private String modelDescription;

}
