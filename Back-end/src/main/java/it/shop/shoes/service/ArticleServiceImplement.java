package it.shop.shoes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import it.shop.shoes.dto.DtoGetArticles;
import it.shop.shoes.dto.DtoGetBrands;
import it.shop.shoes.dto.DtoGetModel;
import it.shop.shoes.dto.DtoGetPrices;
import it.shop.shoes.dto.DtoGetRicercaBSP;
import it.shop.shoes.dto.DtoGetSizes;
import it.shop.shoes.dto.DtoSearchLocCityNameProv;
import it.shop.shoes.dto.DtoArticleByID;
import it.shop.shoes.dto.DtoCodeShop;
import it.shop.shoes.model.Article;
import it.shop.shoes.repository.ArticleRepository;
import it.shop.shoes.utils.ArticleUtils;
import jakarta.transaction.Transactional;


@Service
public class ArticleServiceImplement implements ArticleService{
	
	@Autowired private ArticleRepository articleRepository;
	
	// ----------------------------------------- CRUD METHODS ------------------------------------------------------------
	
	/**
	 * method for insert new Article.
	 * Transaction non devo inserirlo quando faccio la insert xkè non funziona la insert
	 */
	@Transactional
	public Article insert(Article a) {
		Article art = new Article(a.getIdArticolo(), a.getCode(), a.getSize(), a.getNegozioId(), a.getBrand(), a.getCategory(), 
				a.getPrice(),a.getDiscount(), a.getSeason(), a.getSellOut(),a.getSupplierId(), a.getTransactionId(),
				a.getModel(), a.getModelDescription());
		return articleRepository.save(art);
	}
	
	
	/**
	 * this method get a list of all articles from all shops
	 */
	@Transactional
	public List<Article> getArticles() {
		return articleRepository.findAll();
	}
	
	
	/**
	 * this method update a selected field of article by idArticolo
	 */
	@Transactional
	public void update(Long id, Article art) {
		art.setIdArticolo(id);
		articleRepository.save(art);
	}
	
	
	/**
	 * this method delete an article by idArticolo
	 */
	@Transactional
	public void delete(Long id) {
		articleRepository.deleteById(id);		
	}
	
	
	// ----------------------------------------- QUERY & DTO ------------------------------------------------------------
	

	/**
	 * Return all articles from DtoArticle class
	 * This class have : (id - code - size - negozio - brand - category - price - discount - sellOut - supplier) 
	 */
	@Transactional
	@Override
	public List<DtoGetArticles> getDtoArticles() {
		return ArticleUtils.dtoArticleMapper(articleRepository.findAll());
	}
	
	
	/**
	 * This method invokes the query "queryRicerca" which returns all the fields of the Article table, 
	 * which have a specific code and shopId (chosen by the user).
	 */
	@Override
	@Transactional
	public List<DtoCodeShop> researchNegCode(int negozioId, String codice) {
		return ArticleUtils.researchForCodeShopMapper(articleRepository.queryNegCode(negozioId, codice));
	}
	
	
	/*
	 * 
	 */
	@Override
	@Transactional
	public List<DtoArticleByID> queryArticleByID(int idArticle) {
		return ArticleUtils.dtoGetArticleByIDMapper(articleRepository.queryArticleByID(idArticle));
	}
	

	/**
	 * This method takes as input a String, which is a user entered brand.
	 * 
	 * The method invokes the "queryForBrand" query which will return all the article that have that brand entered by the user.
	 */
	@Override
	@Transactional
	public List<Article> researchForBrand(String brand) {
		return articleRepository.queryForBrand(brand);
	}

	
	/*
	 * This method return all distinct brand of my articles
	 */
	@Override
	@Transactional
	public List <DtoGetBrands> getAllBrands(){
		return ArticleUtils.dtoGetBrandMapper(articleRepository.queryGetAllBrands());
	}
	
	
	/*
	 * This method return all distinct sizes of my articles
	 */
	@Override
	@Transactional
	public List <DtoGetSizes> getAllSizes(){
		return ArticleUtils.dtoGetSizesMapper(articleRepository.queryGetAllSize());
	}
	
	
	/*
	 * This method return all distinct prices of my articles
	 */
	@Override
	@Transactional
	public List <DtoGetPrices> getAllPrices(){
		return ArticleUtils.dtoGetPricesMapper(articleRepository.queryGetAllPrices());
	}
	
	
	/*
	 * This method return a list of articles by price, brand and size choosen. 
	 * N.B. prices is not mandatory  
	 */
	@Override
	@Transactional
	public List <DtoGetRicercaBSP> getRicercaBSP(@Param("brand") String brand, @Param("taglia") int size, @Param("prezzo") Double price){
		
		if(brand == null || brand.isEmpty()) {
			throw new IllegalArgumentException("Il campo Brand è obbligatrio !!! ");
		}
		
		if (size <= 0) {
            throw new IllegalArgumentException("Il parametro taglia è obbligatrio !!! ");
        }
		
		return ArticleUtils.dtoGetRicercaBSPMapper(articleRepository.queryGetRicercaBSP(brand, size, price));
	}
	
	
	/**
	 * This method returns the location, store name, city, and state of the store for an item searched by the user
	 * @param localita
	 * @param nome
	 * @param city
	 * @param province
	 * @return List <DtoProva>
	 */
	@Override
	public List<DtoSearchLocCityNameProv> getSearchArtByLocCityNameProv(String brand, int taglia, String modelDescription) {
		return ArticleUtils.getSearchArtByLocCityNameProvMapper(articleRepository.querySearchLocCityNameProv(brand, taglia, modelDescription));
	}
	
	
	/*
	 * This method returns all shoe model descriptions
	 */
	@Override
	@Transactional
	public List <DtoGetModel> getAllModels(){
		return ArticleUtils.dtoGetModelMapper(articleRepository.queryGetAllModels());
	}
	
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
	@Override
	@Transactional
	public Article getArticleByID(Long id) {
		Article articolo = articleRepository.queryFetchGetArticleByID(id);
		return articolo;
	}


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
	@Override
	@Transactional
	public List<Article> getAllArticleFetchSupplier() {
		return articleRepository.queryGetAllArticleFetchSupplier();
	}

}
