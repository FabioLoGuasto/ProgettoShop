package it.shop.shoes.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.shop.shoes.utils.LazyFieldsFilter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
//@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Data // Getters/Setters/ToString
@Entity
@NoArgsConstructor
@Table(name="article", schema="negozio_scarpe")
public class Article{
	
	public Article(Long idArticolo, String code, int size, Shop negozioId, String brand, String category, Double price,
			int discount, String season, int sellOut, Supplier supplierId, Transaction transactionId,
			String model, String modelDescription) {
		this.idArticolo = idArticolo;
		this.code = code;
		this.size = size;
		this.negozioId = negozioId;
		this.brand = brand;
		this.category = category;
		this.price = price;
		this.discount = discount;
		this.season = season;
		this.sellOut = sellOut;
		this.supplierId = supplierId;
		this.transactionId = transactionId;
		this.model = model;
		this.modelDescription = modelDescription;
		
	}
	
	
	/**
	 * unique id of article
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_articolo")
	private Long idArticolo;
	
	  
	/**
	 *  code of article type
	 */
	@Column(nullable = true, name = "codice", length=10)
	private String code;
	
	/**
	 * size article
	 */
	@Column(name = "taglia")
	private int size;
	
	/**
	 * id of the shop
	 * there is relation with shop table
	 * 
	 * Fetch is Lazy and to make it work i have to put @JsonInclude.
	 * value is custom and i have to specify the LazyFieldsFilter.class between valueFilter 
	 * 
	 * option = true, it means that the column can have null values
	 */
	@OneToOne(targetEntity = Shop.class,fetch = FetchType.LAZY, optional = true) 
	@JoinColumn(nullable = true,name = "negozio_id")
	@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
	private Shop negozioId;
	
	/**
	 * brand of article
	 */
	@Column(nullable = true,name = "brand")
	private String brand;
	
	/**
	 * category of article between w (woman) - m (man) - k (kids) - s (sport)
	 */
	@Column(nullable = true,name = "categoria")
	private String category;
	
	/**
	 * price of article
	 */
	@Column(nullable = true,name = "prezzo")
	private Double price;
	
	/**
	 * discount of article
	 */
	@Column(nullable = true,name = "sconto")
	private int discount;
	
	/**
	 * season of article between SS23 (spring-summer 2023) and FW22 (fall-winter 2022)
	 */
	@Column(nullable = true,name = "stagione")
	private String season;
	
	/**
	 * sell out of article 
	 * if 1, the article is in the shop
	 * if 0, the article has been sold 
	 */
	@Column(columnDefinition = "default '1'",name = "venduto")
	private int sellOut;
	
	/**
	 * id of supplier
	 * there is a relation with supplier table
	 * 
	 * @ManyToOne of default is EAGER, with configuration EAGER it's works, 
	 * but if it's LAZY without @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) it does not work
	 * 
	 * Fetch is Lazy and to make it work i have to put @JsonInclude.
	 * value is custom and i have to specify the LazyFieldsFilter.class between valueFilter 
	 * 
	 * option = true, it means that the column can have null values
	 */
	@ManyToOne(targetEntity = Supplier.class,fetch = FetchType.LAZY, optional = true) 
	@JoinColumn(nullable = true, name = "fornitore_id")
	@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
	private Supplier supplierId;
	
	/**
	 * id of transaction
	 * there is a relation with transaction table
	 * 
	 * Fetch is Lazy and to make it work i have to put @JsonInclude.
	 * value is custom and i have to specify the LazyFieldsFilter.class between valueFilter 
	 * 
	 * option = true, it means that the column can have null values
	 */
	@ManyToOne(targetEntity = Transaction.class,fetch = FetchType.LAZY, optional = true) 
	@JoinColumn(nullable = true,name = "transazione_id")
	@JsonInclude(value = JsonInclude.Include.CUSTOM, valueFilter = LazyFieldsFilter.class)
	private Transaction transactionId;
	
	/**
	 * category of article between w (woman) - m (man) - k (kids) - s (sport)
	 */
	@Column(nullable = true,name = "model")
	private String model;
	
	/**
	 * category of article between w (woman) - m (man) - k (kids) - s (sport)
	 */
	@Column(nullable = true,name = "model_description")
	private String modelDescription;
	
	

}
