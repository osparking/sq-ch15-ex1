package sq_ch15ex1.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import sq_ch15ex1.model.Account;
import sq_ch15ex1.model.TransferRequest;
import sq_ch15ex1.service.AccountService;

@RestController
@AllArgsConstructor
public class AccountController {
	
	private final AccountService service;
	
	@GetMapping("/accounts")
	public List<Account> findAllAccount() {
		return service.findAllAccount();
	}

	@PostMapping("/transfer")
	public void transferMoney(@RequestBody TransferRequest request) {
		service.transferMoney(request.getSenderId(), 
				request.getReceiverId(), request.getTransferAmount());
	}
}
