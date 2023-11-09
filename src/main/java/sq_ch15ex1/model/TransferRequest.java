package sq_ch15ex1.model;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TransferRequest {
	private Long senderId;
	private Long receiverId;
	private BigDecimal transferAmount;

}
