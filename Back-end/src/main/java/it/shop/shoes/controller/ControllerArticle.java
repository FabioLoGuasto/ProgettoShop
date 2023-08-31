package it.shop.shoes.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.shop.shoes.dto.DtoGetArticles;
import it.shop.shoes.dto.DtoGetBrands;
import it.shop.shoes.dto.DtoGetModel;
import it.shop.shoes.dto.DtoGetPrices;
import it.shop.shoes.dto.DtoGetRicercaBSP;
import it.shop.shoes.dto.DtoGetSizes;
import it.shop.shoes.dto.DtoSearchLocCityNameProv;
import it.shop.shoes.dto.DtoArticleByID;
import it.shop.shoes.dto.DtoCodeShop;
import it.shop.shoes.dto.DtoError;
import it.shop.shoes.model.Article;
import it.shop.shoes.service.ArticleService;


/**
 * @author fabio
 * Class used for map API endPoint
 */
@RestController
@RequestMapping(value = "/api/article")
public class ControllerArticle {
		
	public static final Logger logger = LoggerFactory.getLogger(ControllerArticle.class);
	
	@Autowired ArticleService articleService;

	
	// ------------------------------- CRUD METHODS ------------------------------------------
	
	
	/**
	 * localhost:8080/api/article/insertArticle
	 * This is the method for insert new Article.
	 * @param dto : Object ArticleDto
	 * @return newArticle : return new Article
	 */
	@Operation(summary = "Insert new article", description = "From JpaRepository")
	@ApiResponse(responseCode = "400", description = "Error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@PostMapping(path ="/insertArticle", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Article> insertArticle(@RequestBody Article article){
		logger.info("INSERT ARTICLE");
		try {
			Article newArticle = articleService.insert(article);
			logger.info("INSERT OK");
			return new ResponseEntity<Article>(newArticle,HttpStatus.CREATED);
		}catch (Exception e) {
			logger.error("ERROR: \n", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}  
	}
	

	/**
	 * localhost:8080/api/article/updateArticleById/
	 * This method update one field of article by idArticolo
	 * @param id : field id of Article
	 * @param art : Object Article
	 * @return String 
	 */
	@Operation(summary = "Update article by ID", description = "From JpaRepository")
	@ApiResponse(responseCode = "400", description = "Error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@PutMapping("/updateArticleById/{id}")
	public ResponseEntity <String> updateArticleById(@PathVariable("id") Long id, @RequestBody Article art){
		logger.info("UPDATE ARTICLE BY ID");
		 try { 
			 articleService.update(id, art);
			 return new ResponseEntity<String>("ARTICLE MODIFIED",HttpStatus.OK);
		 }catch(Exception e) {			 
			 logger.error("ERROR: \n", e);
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		 }
	}
	
	
	/**
	 * localhost:8080/api/article/deleteArticleById/
	 * This method delete one field by idArticolo
	 * @param id : id of Article
	 * @return String 
	 */
	@Operation(summary = "Delete article by ID", description = "From JpaRepository")
	@ApiResponse(responseCode = "400", description = "Error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@DeleteMapping(path = "/deleteArticleById/{id}")
	public ResponseEntity <String> deleteArticleById(@PathVariable("id") Long id){
		logger.info("DELETE ONE ROW");
		try {
			articleService.delete(id);
			logger.info("ARTICLE " + id + " DELETE !!!");
			return new ResponseEntity <String>("DELETE",HttpStatus.OK);
		}catch(Exception e) {
			  logger.error("ERROR: \n", e);
			  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		  }
	}
	
	
	/**
	 * localhost:8080/api/article/getAllArticles
	 * This method return a list with all articles from all shops
	 * @return listArticles
	 */
	@Operation(summary = "Gets all articles", description = "From JpaRepository")
	@ApiResponse(responseCode = "400", description = "Error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path ="/getAllArticles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<Article>> getAllArticles(){
		logger.info("GET ALL ARTICLES");
		try {
			List<Article> listArticles = this.articleService.getArticles();
			return new ResponseEntity <List<Article>> (listArticles,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	
	// --------------------------------- QUERY & DTO -----------------------------------------------------------
	
	
	/**
	 * localhost:8080/api/article/getDtoArticles
	 * This method return a list of DtoArticle
	 * @return listDtoArticles : List of DtoArticle class
	 */
	@Operation(summary = "Gets all articles", description = "From DtoGetBrands")
	@ApiResponse(responseCode = "400", description = "Error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path ="/getDtoArticles", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<DtoGetArticles>> getDtoArticles(){
		logger.info("GET ALL ARTICLES DTO");
		try {
			List<DtoGetArticles> listDtoArticles = this.articleService.getDtoArticles();
			return new ResponseEntity <List<DtoGetArticles>> (listDtoArticles,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * localhost:8080/api/article/dtoCodeShop?idNegozio=2&codice=A008
	 * @param negozioId : chosen field negozioId 
	 * @param codice : chosen field codice
	 * @return listDto :this method return a list with the selected field from dtoCodeShop
	 */
	@Operation(summary = "Gets articles by code and shop", description = "From DtoCodeShop")
	@ApiResponse(responseCode = "400", description = "Error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path = "/dtoCodeShop", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<DtoCodeShop>> dtoCodeShop (@RequestParam(value = "idNegozio") int negozioId, @RequestParam(value = "codice") String codice){
		logger.info("RICERCA DTO");
		try {
			List<DtoCodeShop> listDto = articleService.researchNegCode(negozioId, codice);
			return new ResponseEntity<List<DtoCodeShop>>(listDto, HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR: \n", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * localhost:8080/api/article/articleByID?idArticle=12
	 * @param negozioId
	 * @param codice
	 * @return
	 */
	@Operation(summary = "Get article by ID", description = "From DtoArticleByID")
	@ApiResponse(responseCode = "400", description = "Error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path = "/articleByID", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<DtoArticleByID>> DtoArticleByID (@RequestParam(value = "idArticle") int idArticle){
		logger.info("RICERCA DTO");
		try {
			List<DtoArticleByID> listDto = articleService.queryArticleByID(idArticle);
			return new ResponseEntity<List<DtoArticleByID>>(listDto, HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR: \n", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * localhost:8080/api/article/researchForBrand?brand=ADIDAS
	 * This method return a list with all articles from one selected brand
	 * @param brand : chosen brand
	 * @return listArticles
	 */
	@Operation(summary = "Gets articles by brand", description = "From query")
	@ApiResponse(responseCode = "400", description = "Error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path ="/researchForBrand", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<Article>> researchForBrand(@RequestParam(value = "brand") String brand){
		logger.info("GET ALL ARTICLES WITH SELECTED BRAND");
		try {
			List<Article> listArticles = articleService.researchForBrand(brand);
			return new ResponseEntity <List<Article>> (listArticles,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * localhost:8080/api/article/getBrands
	 * This method return all distinct brand of my articles
	 * @return listBrands
	 */
	@Operation(summary = "GGet all brands from list of articles", description = "From DtoGetBrands")
	@ApiResponse(responseCode = "400", description = "Error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path ="/getBrands", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<DtoGetBrands>> getBrands(){
		logger.info("GET ALL BRAND");
		try {
			List<DtoGetBrands> listBrands = articleService.getAllBrands();
			return new ResponseEntity <List<DtoGetBrands>> (listBrands,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * localhost:8080/api/article/getSizes
	 * This method return all distinct sizes of my articles
	 * @return listSizes
	 */
	@Operation(summary = "Get all sizes from list of articles", description = "From DtoGetSizes")
	@ApiResponse(responseCode = "400", description = "Error", 
			content = @Content(schema = @Schema(implementation = DtoError.class), 
			examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
			content = @Content(schema = @Schema(implementation = DtoError.class), 
			examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path ="/getSizes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<DtoGetSizes>> getSizes(){
		logger.info("GET ALL SIZES");
		try {
			List<DtoGetSizes> listSizes = articleService.getAllSizes();
			return new ResponseEntity <List<DtoGetSizes>> (listSizes,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * localhost:8080/api/article/getPrices
	 * This method return all distinct prices of my articles
	 * @return listPrices
	 */
	@Operation(summary = "Gets all prices from list of articles", description = "Gets all prices from list of articles")
	@ApiResponse(responseCode = "400", description = "Error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path ="/getPrices", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<DtoGetPrices>> getPrices(){
		logger.info("GET ALL PRICES");
		try {
			List<DtoGetPrices> listPrices = articleService.getAllPrices();
			return new ResponseEntity <List<DtoGetPrices>> (listPrices,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * localhost:8080/api/article/getBSP
	 * This method return a list of articles by price, brand and size choosen. 
	 * N.B. prices is not mandatory
	 * @return <List<DtoGetRicercaBSP>> : List of articles
	 */
	@Operation(summary = "Gets articles by brand/size/price", description = "brand&size are mandatory while prince optional")
	@ApiResponse(responseCode = "400", description = "Error", 
        		content = @Content(schema = @Schema(implementation = DtoError.class), 
        		examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
            	content = @Content(schema = @Schema(implementation = DtoError.class), 
            	examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path ="/getBSP", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<DtoGetRicercaBSP>> getBSP(@RequestParam(value = "brand", required = true) String brand, @RequestParam(value = "size", required = true) int size, @RequestParam(value = "price", required = false) Double price){
		logger.info("Return articles with field brand & size mandatory and price optional");
		try {
			List<DtoGetRicercaBSP> result = articleService.getRicercaBSP(brand, size, price);
			return new ResponseEntity <List<DtoGetRicercaBSP>> (result,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * This method returns the location, store name, city, and state of the store for an item searched by the user
	 * @param localita
	 * @param nome
	 * @param city
	 * @param province
	 * @return List <DtoSearchLocCityNameProv>
	 */
	@Operation(summary = "Get location, store, name and city of the shops", description = "Get location, store, name and city of the shops for an item searched by the user")
	@ApiResponse(responseCode = "400", description = "Error", 
        		content = @Content(schema = @Schema(implementation = DtoError.class), 
        		examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
            	content = @Content(schema = @Schema(implementation = DtoError.class), 
            	examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path ="/getSearchLocCityNameProv", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<DtoSearchLocCityNameProv>> getSearchLocCityNameProv(@RequestParam(value = "brand", required = true) String brand, @RequestParam(value = "taglia", required = true) int taglia, @RequestParam(value = "modelDescription", required = false) String modelDescription){
		logger.info("Get location, store, name and city of the shops for an item searched by the user");
		try {
			List<DtoSearchLocCityNameProv> result = articleService.getSearchArtByLocCityNameProv(brand, taglia, modelDescription);
			return new ResponseEntity <List<DtoSearchLocCityNameProv>> (result,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * localhost:8080/api/article/getModels
	 * This method returns all shoe model descriptions
	 * @return listModels
	 */
	@Operation(summary = "Gets all models from list of articles", description = "Gets all shoe model descriptions from list of articles")
	@ApiResponse(responseCode = "400", description = "Error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
				content = @Content(schema = @Schema(implementation = DtoError.class), 
				examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path ="/getModels", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<DtoGetModel>> getModels(){
		logger.info("GET ALL MODELS");
		try {
			List<DtoGetModel> listModels = articleService.getAllModels();
			return new ResponseEntity <List<DtoGetModel>> (listModels,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	// --------------------------------- QUERY FETCH -----------------------------------------------------------
	
	
	/**
	 * localhost:8080/api/article/getAllArticleFetchSupplier
	 * This method return a list with all articles by JOIN FETCH with supplierId
	 * @return listArticles
	 */
	@CrossOrigin
	@GetMapping(path ="/getAllArticleFetchSupplier", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<Article>> getAllArticleFetchSupplier(){
		logger.info("GET ALL ARTICLES FETCH");
		try {
			List<Article> listArticles = this.articleService.getAllArticleFetchSupplier();
			return new ResponseEntity <List<Article>> (listArticles,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * localhost:8080/api/article/getArticleByID/{id}
	 * This method return article with JOIN FETCH a.negozioId JOIN FETCH a.supplierId JOIN FETCH a.transactionId
	 * @param id
	 * @return
	 */
	@CrossOrigin
	@GetMapping(path = "/getArticleByID/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <Article> getOneArticleFetchSupplier(@PathVariable("id") Long id){
		logger.info("GET ARTICLE BY ID WITH JOIN FETCH");
		 try { 
			 Article article = articleService.getArticleByID(id);
			 return new ResponseEntity<Article>(article,HttpStatus.OK);
		 }catch(Exception e) {			 
			 logger.error("ERROR: \n", e);
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		 }
	}
		
		
	
}
