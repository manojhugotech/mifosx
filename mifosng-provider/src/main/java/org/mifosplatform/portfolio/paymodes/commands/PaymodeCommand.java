package org.mifosplatform.portfolio.paymodes.commands;

public class PaymodeCommand {


			private final String paymode;

			private  String description;

			private final Long id;
			private  String category;
			public PaymodeCommand(
					final Long id,
					final String paymode,
					final String description,
					final String category) {

				this.paymode=paymode;
				this.description=description;
				this.category=category;
				this.id=id;



			}
			public String getDescription() {
				return description;
			}
			public void setDescription(String description) {
				this.description = description;
			}
			public String getCategory() {
				return category;
			}
			public void setCategory(String category) {
				this.category = category;
			}
			public String getPaymode() {
				return paymode;
			}
			public Long getId() {
				return id;
			}








}
