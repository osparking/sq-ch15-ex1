package sq_ch15ex1.service.account;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import sq_ch15ex1.exception.AccountNotFoundException;
import sq_ch15ex1.model.Account;
import sq_ch15ex1.repository.AccountRepository;
import sq_ch15ex1.service.AccountService;

@ExtendWith(MockitoExtension.class)
public class TransferMoneyUnitTests {
	
	@Mock
	AccountRepository accountRepository;

	@InjectMocks
	AccountService accountService;

	@Test
	@DisplayName("수신 계좌 찾기 실패, 예외 발생 및 잔액 비 증가된다.")
	public void receiverNotFoundExceptionMoneyTransfer() {
		// 두 관련 계좌(sender, receiver) 객체를 만든다
		Account sender = new Account();
		sender.setId(1);
		sender.setAmount(new BigDecimal(100_0000));

		Account receiver = new Account();
		receiver.setId(2);

		// 보내는 계좌를 ID로 찾았을 때 바른 계좌가 찾아지게 지정한다
		// 받는 계좌를 ID로 찾았을 때 빈 계좌가 찾아지게 지정한다
		given(accountRepository.findAccountById(sender.getId()))
				.willReturn(Optional.of(sender));
		given(accountRepository.findAccountById(receiver.getId()))
				.willReturn(Optional.empty());

		// 시험 대상 메소드를 호출했을 때, 특정 예외가 발생하는 것을 단언한다.
		assertThrows(AccountNotFoundException.class, 
				()->accountService.transferMoney(1L, 2L, new BigDecimal(1000)));

		// 저장소의 잔액 변경 메소드가 전혀 호출되지 않았음을 검증한다.
		verify(accountRepository, never())
				.updateAccountAmount(any(), anyLong());
		
	}
	
	@Test
	@DisplayName("오류나 예외가 없을 때, 계좌이체가 예상대로 이뤄진다")
	public void happyMoneyTransfer() {

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
		given(accountRepository.findAccountById(sender.getId()))
				.willReturn(Optional.of(sender));
		given(accountRepository.findAccountById(receiver.getId()))
				.willReturn(Optional.of(receiver));

		// 시험 대상 메소드를 준비한 자료 실인자를 사용하여 호출한다.
		accountService.transferMoney(sender.getId(), receiver.getId(),
				new BigDecimal(10_0000));

		// 시대메가 두 계좌의 예상 값으로 갱신하는 메소드를 호출하는지 검증한다.
		verify(accountRepository).updateAccountAmount(new BigDecimal(90_0000), 1L);
		verify(accountRepository).updateAccountAmount(new BigDecimal(60_0000), 2L);
	}
}
