package spring.boot.rest.sample.repo;

import java.util.Optional;

import spring.boot.rest.sample.po.Account;

/**
 * Repository for {@link Account}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 08/02/2017
 * @since JDK1.8
 */
public interface AccountRepository extends CustomRepository<Account, Long> {

  Optional<Account> findByAccount(String account);
}
