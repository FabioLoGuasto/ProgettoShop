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
import it.shop.shoes.dto.DtoError;
import it.shop.shoes.dto.DtoGetCityShop;
import it.shop.shoes.dto.DtoGetNameShop;
import it.shop.shoes.dto.DtoGetProvinceShop;
import it.shop.shoes.dto.DtoGetSearchCNP;
import it.shop.shoes.model.Shop;
import it.shop.shoes.service.ShopService;

@RestController
@RequestMapping(value = "api/shop")
public class ControllerShop {
	
	@Autowired ShopService shopService;
	
	public static final Logger logger = LoggerFactory.getLogger(ControllerShop.class);
	
	/**
	 * localhost:8080/api/shop/getAllShop
	 * This method return a list with all shops
	 * @return listShop
	 */
	@CrossOrigin
	@GetMapping(path ="/getAllShop", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<Shop>> getAllShop(){
		logger.info("GET ALL SHOPS");
		try {
			List<Shop> listShop = shopService.getShops();
			return new ResponseEntity <List<Shop>> (listShop,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	/**
	 * localhost:8080/api/shop/insertShop
	 * This method insert a new shop
	 * @param shop : Shop Object
	 * @return insertShop : return new shop
	 */
	@CrossOrigin
	@PostMapping(path ="/insertShop", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Shop> insertShop(@RequestBody Shop shop){
		logger.info("INSERT NEW SHOP");
		try {
			Shop insertShop = shopService.insert(shop);
			logger.info("INSERT NEW SHOP OK");
			return new ResponseEntity<Shop>(insertShop,HttpStatus.CREATED);
		}catch (Exception e) {
			logger.error("ERROR: \n", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}  
	}
	
	
	/**
	 * localhost:8080/api/shop/updateShopById?id=
	 * This method update one field of Shop by idShop
	 * @param id : id of selected shop
	 * @param shop : Shop Object
	 * @return String
	 */
	@CrossOrigin
	@PutMapping("/updateShopById")
	public ResponseEntity <String> updateShopById(@RequestParam(value = "id") Long id, @RequestBody Shop shop){
		logger.info("UPDATE SHOP BY ID");
		 try { 
			 shopService.update(id, shop);
			 return new ResponseEntity<String>("SHOP MODIFIED",HttpStatus.OK);
		 }catch(Exception e) {			 
			 logger.error("ERROR: \n", e);
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		 }
	}
	
	/**
	 * localhost:8080/api/shop/deleteShopById?id=
	 * This method delete one field by idShop
	 * @param id : id of selected Shop
	 * @return String
	 */
	@CrossOrigin
	@DeleteMapping(path = "/deleteShopById")
	public ResponseEntity <String> deleteShopById(@RequestParam(value = "id") Long id){
		logger.info("DELETE ONE SHOP");
		try {
			shopService.delete(id);
			logger.info("SHOP " + id + " DELETE !!!");
			return new ResponseEntity <String>("DELETE",HttpStatus.OK);
		}catch(Exception e) {
			  logger.error("ERROR: \n", e);
			  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		  }
	}
	
	// ------------------------------- Query --------------------------------------- //
	
	/**
	 * 
	 * http://localhost:8080/api/shop/gettingShopById?id=4
	 * @param id : id of selected shop
	 * @param 
	 * @return 
	 */
	@CrossOrigin
	@GetMapping("/gettingShopById")
	public ResponseEntity <Shop> gettingShopById(@RequestParam(value = "id") int idShop){
		logger.info("GET SHOP BY ID");
		 try { 
			 Shop shop = shopService.getShopById(idShop);
			 return new ResponseEntity<Shop>(shop,HttpStatus.OK);
		 }catch(Exception e) {			 
			 logger.error("ERROR: \n", e);
			 return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		 }
	}
	
	
	/**
	 * localhost:8080/api/shop/getprovinces
	 * This method return all distinct provinces of shop
	 * @return <List<DtoGetProvinceShop>>
	 */
	@Operation(summary = "Get all province from list of shop", description = "From DtoGetProvinceShop")
	@ApiResponse(responseCode = "400", description = "Error", 
			content = @Content(schema = @Schema(implementation = DtoError.class), 
			examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
			content = @Content(schema = @Schema(implementation = DtoError.class), 
			examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path ="/getprovinces", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<DtoGetProvinceShop>> getprovinces(){
		logger.info("GET ALL PROVINCE");
		try {
			List<DtoGetProvinceShop> listProvince = shopService.getAllProvincesShop();
			return new ResponseEntity <List<DtoGetProvinceShop>> (listProvince,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * localhost:8080/api/shop/getCity
	 * This method return all distinct city of shop
	 * @return <List<DtoGetCityShop>>
	 */
	@Operation(summary = "Get all city from list of shop", description = "From DtoGetCityShop")
	@ApiResponse(responseCode = "400", description = "Error", 
			content = @Content(schema = @Schema(implementation = DtoError.class), 
			examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
			content = @Content(schema = @Schema(implementation = DtoError.class), 
			examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path ="/getCity", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<DtoGetCityShop>> getCity(){
		logger.info("GET ALL CITY");
		try {
			List<DtoGetCityShop> listCity = shopService.getAllCityShop();
			return new ResponseEntity <List<DtoGetCityShop>> (listCity,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	/**
	 * localhost:8080/api/shop/getNamesShop
	 * This method return all distinct names of shops
	 * @return <List<DtoGetNameShop>>
	 */
	@Operation(summary = "Get all names from list of shop", description = "From DtoGetNameShop")
	@ApiResponse(responseCode = "400", description = "Error", 
			content = @Content(schema = @Schema(implementation = DtoError.class), 
			examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
			content = @Content(schema = @Schema(implementation = DtoError.class), 
			examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path ="/getNamesShop", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<DtoGetNameShop>> getNamesShop(){
		logger.info("GET ALL NAMES OF SHOPS");
		try {
			List<DtoGetNameShop> listNamesShop = shopService.getAllNamesShop();
			return new ResponseEntity <List<DtoGetNameShop>> (listNamesShop,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	
	/**
	 * localhost:8080/api/shop/getCityNameProvince
	 * This method return a list of shops by city, name and province choosen. 
	 * N.B. city is mandatory
	 * @return <List<DtoGetSearchCNP>> : List of shops
	 */
	@Operation(summary = "Gets shops by city/province/nome", description = "city is mandatory while province and nome are optional")
	@ApiResponse(responseCode = "400", description = "Error", 
        		content = @Content(schema = @Schema(implementation = DtoError.class), 
        		examples = @ExampleObject(description = "Bad Request", value = DtoError.error400)))
	@ApiResponse(responseCode = "500", description = "Internal server error", 
            	content = @Content(schema = @Schema(implementation = DtoError.class), 
            	examples = @ExampleObject(description = "Bad Request", value = DtoError.internalError500)))
	@ApiResponse(responseCode = "200", description = "Ok")
	@CrossOrigin
	@GetMapping(path ="/getCityNameProvince", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <List<DtoGetSearchCNP>> getCityNameProvince(@RequestParam(value = "city", required = true) String city, @RequestParam(value = "nome", required = false) String nome, @RequestParam(value = "province", required = false) String province){
		logger.info("Return shop with field city mandatory and province & nome optional");
		try {
			List<DtoGetSearchCNP> result = shopService.getSearchCNP(city, nome, province);
			return new ResponseEntity <List<DtoGetSearchCNP>> (result,HttpStatus.OK);
		}catch(Exception e) {
			logger.error("ERROR " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}
}
