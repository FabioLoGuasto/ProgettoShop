package it.shop.shoes.service;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import it.shop.shoes.dto.DtoGetCityShop;
import it.shop.shoes.dto.DtoGetNameShop;
import it.shop.shoes.dto.DtoGetProvinceShop;
import it.shop.shoes.dto.DtoGetSearchCNP;
import it.shop.shoes.model.Shop;

@Service
public interface ShopService {
	
	/**
	 * insert new shop
	 * @param shop
	 * @return new shop
	 */
	public Shop insert(Shop shop); 
	
	/**
	 * get list of shop
	 * @return
	 */
	public List<Shop> getShops();
	
	/**
	 * update one field of shop by id
	 * @param id : id of the shop
	 * @param shop
	 */
	public void update(Long id,Shop shop);
	
	/**
	 * delete one shop by id
	 * @param id : id of the shop
	 */
	public void delete (Long id);
	
	//----------------------------------- Query ------------------------------------------- //
	
	/**
	 * Return one record, one shop
	 * @return Shop s
	 */
	public Shop getShopById(@Param("ID") int idShop);
	
	/*
	 * This method return all distinct province of my shop
	 */
	public List <DtoGetProvinceShop> getAllProvincesShop();
	
	/*
	 * This method return all distinct city of my shop
	 */
	public List <DtoGetCityShop> getAllCityShop();
	
	/*
	 * This method return all distinct shop name
	 */
	public List <DtoGetNameShop> getAllNamesShop();
	
	/**
	 * This query return list of shop.
	 * For this search there is field city (mandatory) and field nome and province (optional)
	 * @param city
	 * @param nome
	 * @param province
	 * @return List <DtoGetSearchCNP> : list of shops
	 */
	public List <DtoGetSearchCNP> getSearchCNP(@Param("city") String city, @Param("nome") String nome, @Param("province") String province);

}
