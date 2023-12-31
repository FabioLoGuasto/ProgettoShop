package it.shop.shoes.service;

import java.util.List;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import it.shop.shoes.model.Article;
import it.shop.shoes.dto.DtoGetArticles;
import it.shop.shoes.dto.DtoGetBrands;
import it.shop.shoes.dto.DtoGetModel;
import it.shop.shoes.dto.DtoGetPrices;
import it.shop.shoes.dto.DtoGetRicercaBSP;
import it.shop.shoes.dto.DtoGetSizes;
import it.shop.shoes.dto.DtoSearchLocCityNameProv;
import it.shop.shoes.dto.DtoArticleByID;
import it.shop.shoes.dto.DtoCodeShop;


@Service
public interface ArticleService {

	// ----------------------------------------- CRUD METHODS ------------------------------------------------------------
	
	/**
	 * Method for insert new Article.
	 * @param article : new Article from browser
	 * @return new Article into database
	 */
	public Article insert(Article article); 
	
	/**
	 * Update one row of article from selected id
	 * @param id : id of selected article
	 * @param article : article from browser
	 */
	public void update(Long id,Article article);
	
	/**
	 * Delete one article (one row)
	 * @param id : id of article selected
	 */
	public void delete (Long id);
	
	/**
	 * Return all articles from Article table
	 * @return list of articles
	 */
	public List<Article> getArticles();
	
	// ----------------------------------- QUERY & DTO ------------------------------------------------------------
	
	/**
	 * Return all articles from DtoArticle class
	 * This class have : (id - code - size - negozio - brand - category - price - discount - sellOut - supplier) 
	 * @return list of DtoArticles
	 */
	public List<DtoGetArticles> getDtoArticles();
	
	/**
	 * This method invokes the query "queryRicerca" which returns all the fields of the Article table, 
	 * which have a specific code and shopId (chosen by the user).
	 * 
	 * @param negozioId : shop selected 
	 * @param codice : code selected
	 * @return list of article with negozioId and code selected
	 */
	public List <DtoCodeShop> researchNegCode (@Param("idNegozio") int negozioId, @Param("codice") String codice);
	
	/**
	 * This method invoke the query "queryArticleByID" in which return the fields of DtoArticleByID
	 * @param ID of idArticolo
	 * @return ----- 
	 */
	public List <DtoArticleByID> queryArticleByID (@Param("ID") int idArticolo);
	
	/**
	 * This method return all the article that have that brand entered by the user.
	 * @param brand : brand choose by the user
	 * @return list of articles for that brand
	 */
	public List <Article> researchForBrand (@Param("brand") String brand);
	
	/*
	 * This method return all distinct brand of my articles
	 */
	public List <DtoGetBrands> getAllBrands();
	
	/*
	 * This method return all distinct sizes of my articles
	 */
	public List <DtoGetSizes> getAllSizes();
	
	/*
	 * This method return all distinct prices of my articles
	 */
	public List <DtoGetPrices> getAllPrices();
	
	/*
	 * This method return a list of articles by price, brand and size choosen. 
	 * N.B. prices is not mandatory
	 */
	public List <DtoGetRicercaBSP> getRicercaBSP(@Param("brand") String brand, @Param("taglia") int size, @Param("prezzo") Double price);
	
	/**
	 * This method returns the location, store name, city, and state of the store for an item searched by the user
	 * @param localita
	 * @param nome
	 * @param city
	 * @param province
	 * @return List <DtoProva>
	 */
	public List <DtoSearchLocCityNameProv> getSearchArtByLocCityNameProv(@Param("brand") String brand, @Param("taglia") int taglia, @Param("model_description") String modelDescription);
	
	/*
	 * This method returns all shoe model descriptions
	 */
	public List <DtoGetModel> getAllModels();
	
	
	
	// -------------------------------- QUERY FETCH --------------------------------------------------------------
	
	/**
	 * In the example code i provided a value of the Article through id. 
	 * 
	 * The JOIN FETCH syntax in the query specifies that JPA should load the supplier/shop/transaction entity 
	 * along with the Article entity in a single query.
	 * The supplierId/negozioId/transactionId field of each article returned by the query will be populated 
	 * with the corresponding Supplier/Shop/Transaction entity, and you can access the properties of the 
	 * Supplier entity through the idSupplier field, 
	 * Shop entity through the negozioId field,
	 * Transaction entity through the transactionId.
	 * 
	 * @param id of article chosen
	 * @return article with JOIN FETCH a.negozioId JOIN FETCH a.supplierId JOIN FETCH a.transactionId
	 */
	public Article getArticleByID(Long id);
	
	
	/**
	 * The LEFT JOIN FETCH syntax tells JPA to fetch the supplier entity with the Article entity in a single query.
	 * The LEFT JOIN FETCH syntax tells JPA to fetch the transaction entity with the Article entity in a single query.
	 * The LEFT JOIN FETCH syntax tells JPA to fetch the shop entity with the Article entity in a single query. 
	 * 
	 * Note that you need to specify the fetch attribute on the @ManyToOne and @OneToMany annotations to control 
	 * the fetch strategy for the related entities. In this example, we use FetchType.LAZY to fetch the related entities lazily, 
	 * which means that they will be loaded only when accessed. 
	 * This can help to reduce the amount of data loaded from the database and improve performance.
	 * 
	 * In the example code i provided, supplierId refers to the supplier field of the Article entity.
	 * In the example code i provided, transactionId refers to the transaction field of the Article entity.
	 * In the example code i provided, negozioId refers to the shop field of the Article entity.
	 * 
	 * a is an alias for the Article entity in the JPA query SELECT a FROM Article a LEFT JOIN FETCH a.supplierId, and supplierId is a field
	 * in the Article entity that is annotated with @ManyToOne to indicate a many-to-one relationship with the Customer entity.
	 * 
	 * The LEFT JOIN FETCH syntax in the query specifies that JPA should load the supplier entity along with the Article entity in a single query.
	 * By doing so, the supplierId field of each article returned by the query will be populated with the corresponding Supplier entity,
	 * and you can access the properties of the Supplier entity through the idSupplier field of the Article entity.
	 * 
	 * The LEFT JOIN FETCH syntax in the query specifies that JPA should load the transaction entity along with the Article entity in a single query.
	 * By doing so, the transactionId field of each article returned by the query will be populated with the corresponding Transaction entity,
	 * and you can access the properties of the Transaction entity through the transactionId field of the Article entity.
	 * 
	 * The LEFT JOIN FETCH syntax in the query specifies that JPA should load the Shop entity along with the Article entity in a single query.
	 * By doing so, the negozioId field of each article returned by the query will be populated with the corresponding Shop entity,
	 * and you can access the properties of the Shop entity through the negozioId field of the Article entity.
	 * 
	 * @return List of article with LEFT JOIN FETCH with supplierId, transactionId, negozioId
	 */
	public List<Article> getAllArticleFetchSupplier();

}
