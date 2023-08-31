package it.shop.shoes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data // Getters/Setters/ToString
@Entity
@Table(name="shop", schema="negozio_scarpe")
public class Shop {
	
	/**
	 * unique id of shop
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_univoco_negozio")
	private Long idUnivocoNegozio;
	
	/**
	 * number of shop
	 */
	@Column(nullable = true, name = "numero_negozio")
	private int shopNumber;
	
	/**
	 * shop branch name
	 */
	@Column(nullable = true, name = "nome")
	private String branchName;
	
	/**
	 * address where there the shop branch
	 */
	@Column(nullable = true, name = "localita")
	private String branchLocality;
	
	/**
	 * Civic number of shop
	 */
	@Column(nullable = true, name = "civic")
	private Integer civicNumber;
	
	/**
	 * Civic of the shop
	 */
	@Column(nullable = true, name = "city")
	private String city;
	
	/**
	 * Province of the shop
	 */
	@Column(nullable = true, name = "province")
	private String province;
	
	/**
	 * Postal code of the shop
	 */
	@Column(nullable = true, name = "postal_code")
	private Integer postalCode;
	
	/**
	 * Nation of the shop
	 */
	@Column(nullable = true, name = "nation")
	private String nation;


	public Shop(Long idUnivocoNegozio, int shopNumber, String branchName, String branchLocality, Integer civicNumber, 
			String city, String province, Integer postalCode, String nation) {
		
		this.idUnivocoNegozio = idUnivocoNegozio;
		this.shopNumber = shopNumber;
		this.branchName = branchName;
		this.branchLocality = branchLocality;
		this.civicNumber = civicNumber;
		this.city = city;
		this.province = province;
		this.postalCode = postalCode;
		this.nation = nation;
	}

	
}
