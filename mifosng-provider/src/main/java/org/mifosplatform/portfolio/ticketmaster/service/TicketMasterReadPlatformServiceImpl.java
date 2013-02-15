package org.mifosplatform.portfolio.ticketmaster.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import org.mifosplatform.infrastructure.core.data.EnumOptionData;
import org.mifosplatform.infrastructure.core.service.TenantAwareRoutingDataSource;
import org.mifosplatform.infrastructure.security.service.PlatformSecurityContext;
import org.mifosplatform.portfolio.servicemaster.api.TicketMasterStatus;
import org.mifosplatform.portfolio.ticketmaster.data.ProblemsData;
import org.mifosplatform.portfolio.ticketmaster.data.TicketMasterData;
import org.mifosplatform.portfolio.ticketmaster.domain.PriorityType;
import org.mifosplatform.portfolio.ticketmaster.domain.PriorityTypeEnum;
import org.mifosplatform.portfolio.ticketmaster.domain.TicketMasterStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

@Service
public class TicketMasterReadPlatformServiceImpl  implements TicketMasterReadPlatformService{
	
	private final JdbcTemplate jdbcTemplate;
	private final PlatformSecurityContext context;

	@Autowired
	public TicketMasterReadPlatformServiceImpl(final PlatformSecurityContext context,
			final TenantAwareRoutingDataSource dataSource) {
		this.context = context;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public TicketMasterData retrieveTicketMasterata() {
		EnumOptionData active = TicketMasterStatusEnum.statusType(TicketMasterStatus.ACTIVE);
		EnumOptionData closed = TicketMasterStatusEnum.statusType(TicketMasterStatus.CLOSED);
		EnumOptionData resolved = TicketMasterStatusEnum.statusType(TicketMasterStatus.RESOLVED);
		EnumOptionData testing= TicketMasterStatusEnum.statusType(TicketMasterStatus.TESTING);
		EnumOptionData working = TicketMasterStatusEnum.statusType(TicketMasterStatus.WORKING);
		List<EnumOptionData> statusType = Arrays.asList(active, closed,resolved,testing,working);
		
		EnumOptionData low=PriorityTypeEnum.priorityType(PriorityType.LOW);
		EnumOptionData medium=PriorityTypeEnum.priorityType(PriorityType.MEDIUM);
		EnumOptionData high=PriorityTypeEnum.priorityType(PriorityType.HIGH);
		List<EnumOptionData> priorityType=Arrays.asList(low,medium,high);
		return new TicketMasterData(statusType,priorityType);
	}

	@Override
	public List<ProblemsData> retrieveProblemData() {

		context.authenticatedUser();

		DataMapper mapper = new DataMapper();

		String sql = "select " + mapper.schema();

		return this.jdbcTemplate.query(sql, mapper, new Object[] {});
	}

	private static final class DataMapper implements
			RowMapper<ProblemsData> {

		public String schema() {
			return "p.id as id,p.problem_code as problemCode,p.problem_description as problemDescription from problems p";

		}

		@Override
		public ProblemsData mapRow(ResultSet rs, int rowNum)
				throws SQLException {

			Long id = rs.getLong("id");
			String problemCode = rs.getString("problemCode");
			String problemDescription = rs.getString("problemDescription");

			ProblemsData data = new ProblemsData(id, problemCode,problemDescription);

			return data;

		}

	}
	}


