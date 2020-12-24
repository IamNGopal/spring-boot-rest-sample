package spring.boot.rest.sample.domain;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.boot.rest.sample.exception.CommonsException;
import spring.boot.rest.sample.po.Log;
import spring.boot.rest.sample.repo.LogRepository;
import spring.boot.rest.sample.tools.Assert;

/**
 * Domain of {@link Log}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/28/15
 * @since JDK1.8
 */
@Service
@Transactional(readOnly = true)
public class LogDomain {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  @Async @Transactional public void create(Log log) {
    logRepository.save(log);
  }

  /**
   * Show logs.
   *
   * @return {@link Page}
   * @throws CommonsException {@link spring.boot.rest.sample.enums.ErrorType#SYS0121} No group
   *                          exists.
   */
  @SuppressWarnings("unchecked")
  public Page page(Specification<Log> specification, Pageable pageable) throws Exception {
    Page<Log> logs = logRepository.findAll(specification, pageable);
    if (!logs.hasContent()) {
      return new PageImpl<>(new ArrayList<>(), pageable, logs.getTotalElements());
    }
    return logs;
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  private final LogRepository logRepository;

  @Autowired public LogDomain(LogRepository logRepository) {
    Assert.defaultNotNull(logRepository);
    this.logRepository = logRepository;
  }
}
