package sq_ch15ex1.service.account;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import sq_ch15ex1.model.Account;
import sq_ch15ex1.repository.AccountRepository;
import sq_ch15ex1.service.AccountService;

public class TransferMoneyUnitTests {

		@Test
		@DisplayName("오류나 예외가 없을 때, 계좌이체가 예상대로 이뤄진다")
		public void happyMoneyTransfer() {
			AccountRepository accountRepository = mock(AccountRepository.class);
			
			AccountService accountService = new AccountService(accountRepository);
			
			// 두 관련 계좌(sender, receiver) 객체를 만든다
			Account sender = new Account();
			sender.setId(1);
			sender.setName("철수");
			sender.setAmount(new BigDecimal(100_0000));
			
			Account receiver = new Account();
			receiver.setId(2);
			receiver.setName("영희");
			receiver.setAmount(new BigDecimal(50_0000));
			
			// 두 계좌를 저장소에서 각 ID로 찾았을 때 계좌가 찾아지게 지정한다
			given(accountRepository.findById(sender.getId()))
				.willReturn(Optional.of(sender));
			given(accountRepository.findById(receiver.getId()))
			.willReturn(Optional.of(receiver));
			
			// 시험 대상 메소드를 준비한 자료 실인자를 사용하여 호출한다.
			accountService.transferMoney(
					sender.getId(), receiver.getId(), new BigDecimal(10_0000));
			
			// 시대메가 두 계좌의 예상 값으로 갱신하는 메소드를 호출하는지 검증한다.
			verify(accountRepository).updateAccountAmount(new BigDecimal(90_0000), 1);
			verify(accountRepository).updateAccountAmount(new BigDecimal(60_0000), 2);			
		}
}
