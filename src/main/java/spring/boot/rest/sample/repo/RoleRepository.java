package spring.boot.rest.sample.repo;

import java.util.Optional;

import spring.boot.rest.sample.enums.ValidFlag;
import spring.boot.rest.sample.po.Role;

/**
 * Role's repository.
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/16/15
 * @since JDK1.8
 */
public interface RoleRepository extends CustomRepository<Role, Long> {

  Optional<Role> findByNameAndValidFlag(String name, ValidFlag validFlag);

}
