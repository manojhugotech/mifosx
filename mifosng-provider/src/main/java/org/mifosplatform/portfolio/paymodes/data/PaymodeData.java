package org.mifosplatform.portfolio.paymodes.data;

import java.util.List;

import org.joda.time.LocalDate;
import org.mifosplatform.infrastructure.core.data.EnumOptionData;

public class PaymodeData {

	private final List<PaymodeTypesData> categoryType;
  private final LocalDate startDate;

	public PaymodeData(final List<PaymodeTypesData> type)
	{

		this.categoryType=type;
		startDate=new LocalDate();
	}





	public List<PaymodeTypesData> getCategoryType() {
		return categoryType;
	}





	public LocalDate getStartDate() {
		return startDate;
	}



}
