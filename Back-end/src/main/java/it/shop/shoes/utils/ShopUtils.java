package it.shop.shoes.utils;

import java.util.List;

import it.shop.shoes.dto.DtoGetCityShop;
import it.shop.shoes.dto.DtoGetNameShop;
import it.shop.shoes.dto.DtoGetProvinceShop;
import it.shop.shoes.dto.DtoGetSearchCNP;
import it.shop.shoes.exception.ExceptUtils;
import it.shop.shoes.model.Shop;


public class ShopUtils {
	
	
	/**
	 * This method return all distinct province of my shop
	 * @param List <Object[]> listProvince : list come from query 
	 * @return List<DtoGetProvinceShop> : list of province
	 */
	public static List<DtoGetProvinceShop> DtoGetProvinceShopMapper (List <Object[]> listProvince){
		return listProvince.stream()
				.map(element ->{
					DtoGetProvinceShop dtoElem = new DtoGetProvinceShop();
					if (element != null) {
						dtoElem.setProvince((String)element[0]);
					} else {
						throw new ExceptUtils("Error in DtoGetProvinceShopMapper method");
					}
					return dtoElem;
				}).toList();
	}
	
	
	/**
	 * This method return all distinct city of my shop
	 * @param List <Object[]> listCity : list come from query 
	 * @return List<DtoGetCityShop> : list of city
	 */
	public static List<DtoGetCityShop> DtoGetCityShopMapper (List <Object[]> listCity){
		return listCity.stream()
				.map(element ->{
					DtoGetCityShop dtoElem = new DtoGetCityShop();
					if (element != null) {
						dtoElem.setCity((String)element[0]);
					} else {
						throw new ExceptUtils("Error in DtoGetCityShopMapper method");
					}
					return dtoElem;
				}).toList();
	}
	
	
	/**
	 * This method return all distinct name of my shop
	 * @param List <Object[]> listNameShop : list come from query 
	 * @return List<DtoGetNameShop> : list of name of shop
	 */
	public static List<DtoGetNameShop> DtoGetNameShopMapper (List <Object[]> listNameShop){
		return listNameShop.stream()
				.map(element ->{
					DtoGetNameShop dtoElem = new DtoGetNameShop();
					if (element != null) {
						dtoElem.setName((String)element[0]);
					} else {
						throw new ExceptUtils("Error in DtoGetNameShopMapper method");
					}
					return dtoElem;
				}).toList();
	}
	
	
	/**
	 * This query return list of shop.
	 * For this search there is field city (mandatory) and field nome and province (optional) 
	 * @param List <Shop> listShops : come from query
	 * @return List<DtoGetSearchCNP> 
	 */
	public static List<DtoGetSearchCNP> DtoGetSearchCNPMapper (List <Shop> listShops) {
		return listShops.stream()
				.map(shop ->{
					DtoGetSearchCNP dto = new DtoGetSearchCNP();
					if (shop != null) {
						dto.setId(shop.getIdUnivocoNegozio());
						dto.setNumShop(shop.getShopNumber());
						dto.setNameShop(shop.getBranchName());
						dto.setAddressShop(shop.getBranchLocality());
						dto.setCivic(shop.getCivicNumber());
						dto.setCity(shop.getCity());
						dto.setProvince(shop.getProvince());
						dto.setPostalCode(shop.getPostalCode());
						dto.setNation(shop.getNation());
					} else {
						throw new ExceptUtils("Error in DtoGetSearchCNPMapper method");
					}
					return dto;
				}).toList();
	}
	
}
