package it.shop.shoes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.shop.shoes.model.Article;
import jakarta.transaction.Transactional;


@Repository
public interface ArticleRepository extends JpaRepository<Article,Long>{
	
	// sono tutte API, ognuna che prende in ingresso determinati parametri
	
	
	// ------------------------------- QUERY CON DTO -----------------------------------------------
	
	/*
	 * This method return an article by choosen id of article
	 * There's <DtoArticleByID> for this API
	 */
	@Transactional
	@Query(value = "SELECT * FROM article WHERE id_articolo =:ID", nativeQuery = true)
	public List <Article> queryArticleByID (@Param("ID") int idArticolo);

	
	
	/*
	 * This method return the list of articles by choosen idNegozio and article code
	 * * There's <DtoCodeShop> for this API
	 */
	@Transactional
	@Query(value = "SELECT * FROM article WHERE negozio_id =:idNegozio AND codice =:codice ", nativeQuery = true)
	public List <Article> queryNegCode (@Param("idNegozio") int negozio_id, @Param("codice") String codice);
	
	
	/*
	 * Devo tornare un array di oggetti dove ho solo il brand, non mi interessa di tornare l'articolo. 
	 * This method return all distinct brand of my articles
	 */
	@Transactional
	@Query(value = "SELECT DISTINCT brand FROM article", nativeQuery = true)
	public List <Object[]> queryGetAllBrands();
	
	
	/*
	 * This method return all distinct sizes of my articles
	 */
	@Transactional
	@Query(value = "SELECT DISTINCT taglia FROM article", nativeQuery = true)
	public List <Object[]> queryGetAllSize();
	
	
	/*
	 * This method return all distinct prices of my articles
	 */
	@Transactional
	@Query(value = "SELECT DISTINCT prezzo FROM article", nativeQuery = true)
	public List <Object[]> queryGetAllPrices();
	
	
	/*
	 * Chiamando questa API, inserendo tutti e 3 i valori, se non metto le () sul prezzo, la query includerà i record
	 * con brand e size in base al prezzo inserito ed il filtro è come se non fosse applicato, quindi devo includere le ()
	 * Se non metto le () in (:prezzo is null or prezzo=:prezzo) il filtro sul prezzo non viene applicato.
	 * 
	 * This method return a list of articles by price, brand and size choosen. 
	 * N.B. prices is not mandatory
	 */
	@Transactional
	@Query (value = "SELECT * FROM article WHERE brand =:brand AND taglia=:taglia AND (:prezzo is null or prezzo=:prezzo)", nativeQuery = true)
	public List <Article> queryGetRicercaBSP(@Param("brand") String brand, @Param("taglia") int size, @Param("prezzo") Double price);
	
	
	
	/**
	 * This method returns the location, store name, city, and state of the store for an item searched by the user
	 * @param localita
	 * @param nome
	 * @param city
	 * @param province
	 * @return List <DtoProva>
	 */
	@Transactional
	@Query(value = "SELECT s.localita, s.nome, s.city, s.province FROM article a INNER JOIN shop s ON a.negozio_id=s.id_univoco_negozio"
					+ " WHERE a.brand=:brand AND a.taglia=:taglia AND (:model_description is null or a.model_description=:model_description) GROUP BY s.localita",nativeQuery = true)
	public List <Object[]>querySearchLocCityNameProv(@Param("brand") String brand, @Param("taglia") int taglia, @Param("model_description") String modelDescription);
	
	
	
	/*
	 * This method returns all shoe model descriptions
	 */
	@Transactional
	@Query(value = "SELECT DISTINCT model_description FROM article", nativeQuery = true)
	public List <Object[]> queryGetAllModels();
	
	
	// ------------------------------- QUERY SENZA DTO -----------------------------------------------
	
	/**
	 * Return list of brand by a brand insert from the user
	 * @param brand
	 * @return List <Article>
	 */
	@Transactional
	@Query(value = "SELECT * FROM article WHERE brand LIKE %:brand%", nativeQuery = true)
	public List <Article> queryForBrand (@Param("brand") String brand);
	
	
	// ------------------------------- QUERY CON IL FETCH -----------------------------------------------
	
	
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
	@Transactional
	@Query("SELECT a FROM Article a JOIN FETCH a.negozioId JOIN FETCH a.supplierId JOIN FETCH a.transactionId WHERE a.idArticolo =:id")
    public Article queryFetchGetArticleByID(@Param("id") Long id);
	
	
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
	@Transactional
	@Query("SELECT a FROM Article a LEFT JOIN FETCH a.negozioId LEFT JOIN FETCH a.supplierId LEFT JOIN FETCH a.transactionId")
    public List<Article> queryGetAllArticleFetchSupplier();
	

}
