package spring.boot.rest.sample.repo;

import java.util.Optional;

import spring.boot.rest.sample.enums.ValidFlag;
import spring.boot.rest.sample.po.User;


/**
 * User's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 6/25/15
 * @since JDK1.8
 */
public interface UserRepository extends CustomRepository<User, Long> {

  Optional<User> findByIdAndValidFlag(Long id, ValidFlag validFlag);

  Optional<User> findByUsrAndValidFlag(String usr, ValidFlag validFlag);
}
