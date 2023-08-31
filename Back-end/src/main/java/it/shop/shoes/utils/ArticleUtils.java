package it.shop.shoes.utils;


import java.util.ArrayList;
import java.util.List;

import it.shop.shoes.dto.DtoGetArticles;
import it.shop.shoes.dto.DtoGetBrands;
import it.shop.shoes.dto.DtoGetModel;
import it.shop.shoes.dto.DtoGetPrices;
import it.shop.shoes.dto.DtoGetRicercaBSP;
import it.shop.shoes.dto.DtoGetSizes;
import it.shop.shoes.dto.DtoSearchLocCityNameProv;
import it.shop.shoes.exception.ExceptUtils;
import it.shop.shoes.dto.DtoArticleByID;
import it.shop.shoes.dto.DtoCodeShop;
import it.shop.shoes.model.Article;


public class ArticleUtils {
	

	/**
	 * This method takes as parameter a list of articles determined by the "queryRicerca" query which returns all the fields 
	 * of the article table that have a specific code (chosen by the user) and a specific id Shop (chosen by the user).
	 * 
	 * This method instead will return specific fields that have been declared in the "DtoCodeShop" class
	 * 
	 * This is the second method of method dtoCodeShop of ControllerArticle
	 * @param List<Article> listArticles
	 * @return List<DtoCodeShop>
	 */
	public static List<DtoCodeShop> researchForCodeShopMapper(List<Article> listArticles) {
	    return listArticles.stream()
	        .map(article -> {
	            DtoCodeShop dto = new DtoCodeShop();
	            if (article != null) {
		            dto.setCode(article.getCode());
		            dto.setNumberShop(article.getNegozioId().getShopNumber());
		            dto.setSize(article.getSize());
	            } else {
					throw new ExceptUtils("Error in researchForCodeShopMapper method");
				}
	            return dto;
	        }).toList();
	}

	
	/**
	 * This method take as parameter a list of article and return a list of DtoArticle 
	 * @param List <Article> listArticle
	 * @return List<DtoGetArticles>
	 * 
	 * dal return listArticle mi sono aperto uno stream per aprire lo stream sulla lista, il map
	 * va a trasformare la colleziona di dati in un altra (infatti torno una lista di dtoArticle
	 * per quelo ci sono 2 return nel metodo) Il map ha bisogno di un return.
	 * article è l'entità singola come su facessi il mio foreach, e li dentro avrò quello che
	 * succederebbe con un ciclo foreach
	 * Per ogni ciclo mi creo un oggetto dto sul quale faccio il mapping che poi mi ritornerà finchè 
	 * non finisce di fare il mapping e mi ritorna la lista.
	 */
	public static List<DtoGetArticles> dtoArticleMapper (List <Article> listArticle) {
		return listArticle.stream()
				.map(article -> {
					DtoGetArticles dto = new DtoGetArticles();
					if (article != null) {
						dto.setId(article.getIdArticolo());
						dto.setCode(article.getCode());
						dto.setSize(article.getSize());
						dto.setNegozio(article.getNegozioId().getBranchName());  
						dto.setBrand(article.getBrand());
						dto.setCategory(article.getCategory());
						dto.setPrice(article.getPrice());
						dto.setDiscount(article.getDiscount());
						dto.setSeason(article.getSeason());
						dto.setSellOut(article.getSellOut());
						dto.setSupplier(article.getSupplierId().getCompanyName()); 
					} else {
						throw new ExceptUtils("Error in dtoArticleMapper method");
					}
					return dto;
				}).toList();
	}
	

	/**
	 * This method return a list of Articles by choose ID
	 * @param List <Article> listArticle
	 * @return List<DtoArticleByID> 
	 */
	public static List<DtoArticleByID> dtoGetArticleByIDMapper (List <Article> listArticle) {
		List<DtoArticleByID> listDto = new ArrayList<>();
		for(Article article : listArticle) {
			DtoArticleByID dto = new DtoArticleByID();
			if (article != null) {
				dto.setId(article.getIdArticolo());
				dto.setCode(article.getCode());
				dto.setSize(article.getSize());
				dto.setNegozio(article.getNegozioId().getBranchName());  
				dto.setBrand(article.getBrand());
				dto.setCategory(article.getCategory());
				dto.setPrice(article.getPrice());
				dto.setDiscount(article.getDiscount());
				dto.setSeason(article.getSeason());
				dto.setSellOut(article.getSellOut());
				dto.setSupplier(article.getSupplierId().getCompanyName()); 
				//dto.setTransaction(article.getTransactionId().getIdTransazione());
			} else {
				throw new ExceptUtils("Error in dtoGetArticleByIDMapper method");
			}
			listDto.add(dto);
		}
		return listDto;
	}
	
	
	/*
	 * Article
	 * ---------------------------------------------------------------------
	 * |Col. brand  |             |           |          |                   | ---> questo è un singolo articolo
	 * --------------------------------------------------------------------- 
	 * row[0]		  row[1]		row[2]		row[3]			row[4]
	 * - Quando ciclo nella lambda questa è una iterazione del ciclo.
	 * - in row[0] c'è il primo elemento, in row[1] il secondo elemento, ecc .....
	 * 
	 * - Questi elementi li prendevo con il metodo dell'entity, prendevo getId, getCode ecc...
	 * - Se nella query avessi fatto SELECT * , ci sarebbe stata prefetta corrispondenza tra la entity 
	 * ed il risultato della query.
	 * 
	 * 
	 * - Se al posto dell'articolo prendessi solo la colonna stagione e brand, in questo caso avrei : 
	 * ------------------------------
	 * |  stagione  |   brand       |
	 * ------------------------------
	 * row[0]			row[1]
	 * 
	 * - Avrei solo row[0] e row [1] e non è l'entita articolo. 
	 * - Questo è un sottoinsieme dell'entità articolo.
	 * 
	 * - QUESTA E' L'ITERAZIONE IESIMA DELLA LAMBDA EXPRESSION. HO UN ARRAY DI OGGETTI, UN ARRAY DI ARRAY.
	 * - LA LAMBDA/FOR CHE VADO A SCRIVERE CICLA SULLE RIGHE, MA LA RIGA SONO N° COLONNE.
	 * - N° COLONNE IN JAVA E' UN ALTRO ARRAY, UN ARRAY DI OGGETTI CHE HANNO TIPI DIVERSI X QUELLO 
	 * E' OBJECT DI ARRAY.
	 * - LE COLONNE DEVO POI ANDARE A PRENDERLE. SE AVESSI TUTTA L'ENTITA' ARTICOLO FACENDO SELECT*
	 * POSSO USARE L'ENTITA' ARTICOLO, MA SE PRENDO COME IN QUESTO CASO UN SOTTOINSIEME DELL'ENTITA' ARTICOLO 
	 * E' DIVERSO.
	 * - IO SBAGLIAVO PERCHE' CERCAVO DI MAPPARE L'ARTICOLO -> public List <Article> queryGetAllSize();
	 * -> public static List<DtoGetBrands> dtoGetBrandMapper(List <Articolo> listBrands)
	 * 
	 * 
	 * 
	 * From listBrands will come my brand that return from the query.
	 * Using Object[] as a generic type, because if i had 2 columns to return the values of which
	 * one is String and the other in Integer, i really need a List of Object.
	 * In this case, i could write List<String> because i need the brand field and his type is String.
	 * 
	 * 
	 * row[0] because i have only one column.
	 */
	public static List<DtoGetBrands> dtoGetBrandMapper(List <Object[]> listBrands){
		return listBrands.stream()
				.map(row ->{
					DtoGetBrands result = new DtoGetBrands(); // Creo le mie righe con i brand
					if (row != null) {
						result.setBrand((String) row[0]);
					} else {
						throw new ExceptUtils("Error in dtoGetBrandMapper method");
					}
					return result;
				}).toList();
	}
	
	
	/**
	 * This method take a List of Object from query. There will be all my size without duplicate
	 * @param List <Object[]> listSizes
	 * @return List<DtoGetSizes>
	 */
	public static List<DtoGetSizes> dtoGetSizesMapper(List <Object[]> listSizes){
		return listSizes.stream()
				.map(row ->{
					DtoGetSizes result = new DtoGetSizes();
					if (row != null) {
						result.setSize((Integer) row[0]);
					} else {
						throw new ExceptUtils("Error in dtoGetSizesMapper method");
					}
					return result;
				}).toList();
	}
	

	/**
	 * This method take a List of Object from query. There will be all my price without duplicate
	 * @param List<Object[]>listPrices
	 * @return List<DtoGetPrices>
	 */
	public static List<DtoGetPrices> dtoGetPricesMapper(List<Object[]>listPrices){
		return listPrices.stream()
				.map(element ->{
					DtoGetPrices result = new DtoGetPrices();
					if (element != null) {
						result.setPrice((Double)element[0]);
					} else {
						throw new ExceptUtils("Error in dtoGetPricesMapper method");
					}
					return result;
				}).toList();
	}
	
	
	/**
	 * This method return a list of articles by price, brand and size choosen. 
	 * N.B. prices is not mandatory
	 * @param List <Article> listArticle
	 * @return List<DtoGetRicercaBSP>
	 */
	public static List<DtoGetRicercaBSP> dtoGetRicercaBSPMapper (List <Article> listArticle) {
		return listArticle.stream()
				.map(article -> {
					DtoGetRicercaBSP dto = new DtoGetRicercaBSP();
					if (article != null) {
						dto.setId(article.getIdArticolo());
						dto.setCode(article.getCode());
						dto.setSize(article.getSize());
						dto.setNegozio(article.getNegozioId().getIdUnivocoNegozio());  
						dto.setBrand(article.getBrand());
						dto.setCategory(article.getCategory());
						dto.setPrice(article.getPrice());
						dto.setDiscount(article.getDiscount());
						dto.setSeason(article.getSeason());
						dto.setSellOut(article.getSellOut());
						dto.setSupplier(article.getSupplierId().getIdFornitore()); 
						dto.setModel(article.getModel());
						dto.setModelDescription(article.getModelDescription());
					} else {
						throw new ExceptUtils("Error in dtoGetRicercaBSPMapper method");
					}
					return dto;
				}).toList();
	}
	
	
	/**
	 * This method returns the location, store name, city, and state of the store for an item searched by the user
	 * @param List<Object[]>listObject
	 * @return List<DtoProva>
	 */
	public static List<DtoSearchLocCityNameProv> getSearchArtByLocCityNameProvMapper(List<Object[]>listObject){
		return listObject.stream()
				.map(element ->{
					DtoSearchLocCityNameProv result = new DtoSearchLocCityNameProv();
					if (element != null) {
						result.setLocalita((String)element[0]);
						result.setName((String)element[1]);
						result.setCity((String)element[2]);
						result.setProvince((String)element[3]);
					} else {
						throw new ExceptUtils("Error in getSearchArtByLocCityNameProvMapper method");
					}
					return result;
				}).toList();
	}
	
	
	/**
	 * This method take a List of Object from query. There will be all my models without duplicate
	 * @param List<Object[]>listModel
	 * @return List<DtoGetModel>
	 */
	public static List<DtoGetModel> dtoGetModelMapper(List<Object[]>listModels){
		return listModels.stream()
				.map(element ->{
					DtoGetModel result = new DtoGetModel();
					if (element != null) {
						result.setModel((String)element[0]);
					} else {
						throw new ExceptUtils("Error in dtoGetModelMapper method");
					}
					return result;
				}).toList();
	}
	

}
