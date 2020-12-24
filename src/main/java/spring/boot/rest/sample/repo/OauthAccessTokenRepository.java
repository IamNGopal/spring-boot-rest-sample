package spring.boot.rest.sample.repo;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import spring.boot.rest.sample.po.OauthAccessToken;

/**
 * Repository for {@link OauthAccessToken}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 26/10/2016
 * @since JDK1.8
 */
public interface OauthAccessTokenRepository extends JpaRepository<OauthAccessToken, String> {

  Optional<OauthAccessToken> findByUserName(String userName);
}
