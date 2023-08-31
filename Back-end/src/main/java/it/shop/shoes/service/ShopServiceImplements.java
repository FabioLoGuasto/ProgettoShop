package it.shop.shoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import it.shop.shoes.dto.DtoGetCityShop;
import it.shop.shoes.dto.DtoGetNameShop;
import it.shop.shoes.dto.DtoGetProvinceShop;
import it.shop.shoes.dto.DtoGetSearchCNP;
import it.shop.shoes.model.Shop;
import it.shop.shoes.repository.ShopRepository;
import it.shop.shoes.utils.ShopUtils;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ShopServiceImplements implements ShopService{

	@Autowired
	private ShopRepository shopRepository;
	
	/**
	 * insert new shop
	 */
	@Override
	public Shop insert(Shop s) {
		Shop shop = new Shop(s.getIdUnivocoNegozio(),s.getShopNumber(), s.getBranchName(), s.getBranchLocality(), s.getCivicNumber(),
				s.getCity(), s.getProvince(), s.getPostalCode(), s.getNation());
		return shopRepository.save(shop);
	}

	/**
	 * get list of shops
	 */
	@Override
	public List<Shop> getShops() {
		return shopRepository.findAll();
	}

	/**
	 * update field of shop by id
	 */
	@Override
	public void update(Long id, Shop s) {
		s.setIdUnivocoNegozio(id);
		shopRepository.save(s);
	}

	/**
	 * delete field of shop by id
	 */
	@Override
	public void delete(Long id) {
		shopRepository.deleteById(id);
	}
	
	// ------------------------------ Query -------------------------------------------- // 

	/**
	 * Get shop by choosen id
	 */
	@Override
	public Shop getShopById(int idShop) {
		return shopRepository.queryShopByID(idShop);
	}

	/**
	 *  This method return all distinct provinces of the shop
	 */
	@Override
	public List<DtoGetProvinceShop> getAllProvincesShop() {
		return ShopUtils.DtoGetProvinceShopMapper(shopRepository.queryGetAllProvince());
	}

	/**
	 * This method return all distinct city of the shop
	 */
	@Override
	public List<DtoGetCityShop> getAllCityShop() {
		return ShopUtils.DtoGetCityShopMapper(shopRepository.queryGetAllCity());
	}

	/**
	 * This method return all distinct names of the shop
	 */
	@Override
	public List<DtoGetNameShop> getAllNamesShop() {
		return ShopUtils.DtoGetNameShopMapper(shopRepository.queryGetAllShopName());
	}

	/**
	 * This method return a list of shop by city, name and province choosen. 
	 * N.B. province and name is not mandatory
	 */
	@Override
	public List<DtoGetSearchCNP> getSearchCNP(String city, String nome, String province) {
		
		if(city == null || city.isEmpty()) {
			throw new IllegalArgumentException("The field city is mandatory! ");
		}
		
		return ShopUtils.DtoGetSearchCNPMapper(shopRepository.queryGetSearchCNP(city, nome, province));
	}
	
	
	

}
