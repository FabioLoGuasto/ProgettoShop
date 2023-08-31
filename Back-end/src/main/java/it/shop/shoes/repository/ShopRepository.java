package it.shop.shoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.shop.shoes.model.Shop;
import jakarta.transaction.Transactional;

@Repository
public interface ShopRepository extends JpaRepository<Shop,Long>{
	
	/*
	 * This method return an shop by choosen id of shop
	 */
	@Transactional
	@Query(value = "SELECT * FROM shop WHERE id_univoco_negozio =:ID", nativeQuery = true)
	public Shop queryShopByID (@Param("ID") int idShop);
	
	
	/*
	 * This method return all distinct province of my shop
	 */
	@Transactional
	@Query(value = "SELECT DISTINCT province FROM shop", nativeQuery = true)
	public List <Object[]> queryGetAllProvince();
	
	
	/*
	 * This method return all distinct city of my shop
	 */
	@Transactional
	@Query(value = "SELECT DISTINCT city FROM shop", nativeQuery = true)
	public List <Object[]> queryGetAllCity();
	
	
	/*
	 * This method return all distinct shop name
	 */
	@Transactional
	@Query(value = "SELECT DISTINCT nome FROM shop", nativeQuery = true)
	public List <Object[]> queryGetAllShopName();
	
	/**
	 * This query return list of shop.
	 * For this search there is field city (mandatory) and field nome and province (optional) 
	 * @param city
	 * @param nome
	 * @param province
	 * @return
	 */
	@Transactional
	@Query (value = "SELECT * FROM shop WHERE city=:city AND (:nome is null or nome=:nome) AND (:province is null or province=:province)", nativeQuery = true)
	public List <Shop> queryGetSearchCNP(@Param("city") String city, @Param("nome") String nome, @Param("province") String province);
	
	
	
	
}
