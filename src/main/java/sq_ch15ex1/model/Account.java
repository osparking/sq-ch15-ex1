package sq_ch15ex1.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Account {
	private long id;
	private String name;
	private BigDecimal amount;
}
