package org.mifosplatform.portfolio.onetimesale.data;

public class ItemData {
	
	private Long id;
	private String itemCode;
	private String units;
	private String unitPrice;

	public ItemData(Long id, String itemCode, String units, String unitPrice) {
		
		this.id=id;
		this.itemCode=itemCode;
		this.units=units;
		this.unitPrice=unitPrice;
		
	}

	public Long getId() {
		return id;
	}

	public String getItemCode() {
		return itemCode;
	}

	public String getUnits() {
		return units;
	}

	public String getUnitPrice() {
		return unitPrice;
	}

	
}
