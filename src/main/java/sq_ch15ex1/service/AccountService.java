package sq_ch15ex1.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import sq_ch15ex1.exception.AccountNotFoundException;
import sq_ch15ex1.model.Account;
import sq_ch15ex1.repository.AccountRepository;

@Service
@AllArgsConstructor
public class AccountService {
	private final AccountRepository repository;
	
	public List<Account> findAllAccount() {
		return repository.findAllAccount();
	}
	
	@Transactional
	public void transferMoney(
			Long senderId,
			Long receiverId, 
			BigDecimal transferAmount) {
		var senderAccount = repository.findAccountById(senderId)
				.orElseThrow(() -> new AccountNotFoundException());
		var receiverAccount = repository.findAccountById(receiverId)
				.orElseThrow(() -> new AccountNotFoundException());
		
		var senderAmount = senderAccount.getAmount().subtract(transferAmount);
		var receiverAmount = receiverAccount.getAmount().add(transferAmount);
		
		repository.updateAccountAmount(senderAmount, senderId);
		repository.updateAccountAmount(receiverAmount, receiverId);
	}
	
}
