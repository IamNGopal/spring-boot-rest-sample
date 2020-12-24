package spring.boot.rest.sample.repo;

import java.util.Optional;

import spring.boot.rest.sample.po.Client;

/**
 * Client's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/23/15
 * @since JDK1.8
 */
public interface ClientRepository extends CustomRepository<Client, Long> {

  Optional<Client> findByClientIdAlias(String clientIdAlias);
}
