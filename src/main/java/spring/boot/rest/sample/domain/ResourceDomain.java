package spring.boot.rest.sample.domain;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.boot.rest.sample.component.Transformer;
import spring.boot.rest.sample.constant.CommonsConstant;
import spring.boot.rest.sample.enums.ErrorType;
import spring.boot.rest.sample.exception.CommonsException;
import spring.boot.rest.sample.param.ResourceParam;
import spring.boot.rest.sample.po.Resource;
import spring.boot.rest.sample.po.User;
import spring.boot.rest.sample.repo.CustomRepository;
import spring.boot.rest.sample.repo.ResourceRepository;
import spring.boot.rest.sample.tools.Assert;
import spring.boot.rest.sample.tools.ErrorMsgHelper;
import spring.boot.rest.sample.vo.ResourceVO;

/**
 * Domain of {@link Resource}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@Service
@Transactional(readOnly = true)
public class ResourceDomain extends BaseDomain<Resource, Long> {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  @Transactional public ResourceVO create(ResourceParam param, User currentUser) throws Exception {
    return super.createByPO(ResourceVO.class, resourceParam2PO(param, new Resource(), currentUser));
  }

  public List<ResourceVO> all() {
    return resourceRepository.findAll().stream()
        .map(resource -> {
          try {
            return transformer.po2VO(ResourceVO.class, resource);
          } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        }).collect(Collectors.toList());
  }

  @Transactional public ResourceVO update(ResourceParam param, User currentUser) throws Exception {
    Resource resource = findById(param.getId());
    return super.updateByPO(ResourceVO.class, resourceParam2PO(param, resource, currentUser));
  }

  public Resource findById(Long id) {
    return resourceRepository.findById(id).orElse(null);
  }

  @Transactional @Override public void deepDelete(Long id) throws Exception {
    Resource resource = findById(id);
    if (resource == null) {
      throw new CommonsException(ErrorType.SYS0122, ErrorMsgHelper
          .getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName().toLowerCase(),
              CommonsConstant.ID));
    }
    resourceRepository.delete(resource);
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  private final ResourceRepository resourceRepository;

  public ResourceDomain(CustomRepository<Resource, Long> repository, Transformer transformer,
      ResourceRepository resourceRepository) {
    super(repository, transformer);
    Assert.defaultNotNull(resourceRepository);
    this.resourceRepository = resourceRepository;
  }

  private Resource resourceParam2PO(ResourceParam param, Resource resource, User currentUser)
      throws Exception {
    return transformer.param2PO(getClassT(), param, resource, currentUser);
  }
}
