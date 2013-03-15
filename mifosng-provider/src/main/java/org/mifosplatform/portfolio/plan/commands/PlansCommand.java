package org.mifosplatform.portfolio.plan.commands;

import java.util.Set;

import org.joda.time.LocalDate;

public class PlansCommand {


		private final String planCode;

		private final String planDescription;
		private final String contractPeriod;

		 private final LocalDate startDate;
		 private final LocalDate endDate;

		private Long status;
		private final String[] services;
		private String chargeCode;
		private final Set<String> modifiedParameters;
		private final Long billRule;

		public PlansCommand(final Set<String> modifiedParameters,
				final String planCode,final String planDescription,final LocalDate startDate,
				final LocalDate endDate,final Long status,String[] services,
				final Long billRule,final String chargeCode,final String contractPeriod) {
			this.planCode = planCode;
            this.planDescription=planDescription;
			this.startDate = startDate;
			this.endDate = endDate;;
			this.status = status;
			this.services=services;
			this.chargeCode=chargeCode;
			this.billRule=billRule;
			this.contractPeriod=contractPeriod;

			this.modifiedParameters = modifiedParameters;
		}



		public String getplanCode() {
			return planCode;
		}

		public String getplanDescription() {
			return planDescription;
		}

		public LocalDate getStartDate() {
			return startDate;
		}

		public LocalDate getEndDate() {
			return endDate;
		}



		public Long getStatus() {
			return status;
		}



		public Long getBillRule() {
			return billRule;
		}



		public String[] getServices() {
			return services;
		}



		public String getchargeCode() {
			return chargeCode;
		}

		public Set<String> getModifiedParameters() {
			return modifiedParameters;
		}



		public String getContractPeriod() {
			return contractPeriod;
		}



		public Long getbillRule() {
			return billRule;
		}

		public boolean isplanCodeChanged() {
			return this.modifiedParameters.contains("planCode");
		}

		public boolean isplanDescriptionChanged() {
			return this.modifiedParameters.contains("planDescription");
		}
		public boolean isStartDateChanged() {
			return this.modifiedParameters.contains("startDate");
		}
		public boolean isendDateChanged() {
			return this.modifiedParameters.contains("endDate");
		}

		public boolean isPlanStatusChanged() {
			return this.modifiedParameters.contains("status");
		}

		public boolean isBillingCycleChanged() {
			return this.modifiedParameters.contains("anyDayAllowed");
		}

		public boolean isBillingRuleChanged() {
			return this.modifiedParameters.contains("billRule");
		}

		public boolean isServicesChanged() {
			return this.modifiedParameters.contains("services");
		}

		public boolean isContractPeriodChanged() {
			return this.modifiedParameters.contains("contractPeriod");
		}


		}
