package sq_ch15ex1.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import sq_ch15ex1.model.Account;

public interface AccountRepository extends CrudRepository<Account, Long>{
	
	@Query("select * from account where id=:id")
	public Account findAccountById(Long id);	
	
	@Query("select * from account")
	public List<Account> findAllAccount();
	
	@Query("update account set amount=:amount where id=:id")
	public void updateAccountAmount(BigDecimal amount, Long id);
}
