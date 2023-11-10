package sq_ch15ex1.service.account;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.Test;

import sq_ch15ex1.repository.AccountRepository;
import sq_ch15ex1.service.AccountService;

public class TransferMoneyUnitTests {

		@Test
		public void happyMoneyTransfer() {
			AccountRepository accountRepository = mock(AccountRepository.class);
			
			AccountService accountService = new AccountService(accountRepository);
		}
}
