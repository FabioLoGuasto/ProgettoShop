package it.shop.shoes.dto;

import lombok.Data;

@Data
public class DtoGetSearchCNP {

	private Long id;
	private Integer numShop;
	private String nameShop;
	private String addressShop;
	private Integer civic;
	private String city;
	private String province;
	private Integer postalCode;
	private String nation;
	
}
