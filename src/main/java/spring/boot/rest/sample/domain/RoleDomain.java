package spring.boot.rest.sample.domain;

import com.google.common.collect.Sets;

import spring.boot.rest.sample.component.Transformer;
import spring.boot.rest.sample.constant.CommonsConstant;
import spring.boot.rest.sample.enums.ErrorType;
import spring.boot.rest.sample.enums.ValidFlag;
import spring.boot.rest.sample.exception.CommonsException;
import spring.boot.rest.sample.param.RoleParam;
import spring.boot.rest.sample.po.Resource;
import spring.boot.rest.sample.po.Role;
import spring.boot.rest.sample.po.User;
import spring.boot.rest.sample.repo.CustomRepository;
import spring.boot.rest.sample.repo.RoleRepository;
import spring.boot.rest.sample.tools.Assert;
import spring.boot.rest.sample.tools.ErrorMsgHelper;
import spring.boot.rest.sample.vo.ResourceVO;
import spring.boot.rest.sample.vo.RoleVO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Domain of {@link Role}
 *
 * @author <a href="http://github.com/saintdan">Liao Yifan</a>
 * @date 10/17/15
 * @since JDK1.8
 */
@Service
@Transactional(readOnly = true)
public class RoleDomain extends BaseDomain<Role, Long> {

  // ------------------------
  // PUBLIC METHODS
  // ------------------------

  @Transactional public RoleVO create(RoleParam param, User currentUser) throws Exception {
    return po2Vo(super.createByPO(param2Po(param, new Role(), currentUser)));
  }

  public List<RoleVO> all() {
    return roleRepository.findAll().stream()
        .map(role -> {
          try {
            return po2Vo(role);
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        }).collect(Collectors.toList());
  }

  @Transactional public RoleVO update(RoleParam param, User currentUser) throws Exception {
    Role role = findById(param.getId());
    if (role == null) {
      throw new CommonsException(ErrorType.SYS0122, ErrorMsgHelper
          .getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName(), CommonsConstant.ID));
    }
    return po2Vo(super.updateByPO(param2Po(param, role, currentUser)));
  }

  public RoleVO getById(Long id) throws Exception {
    return po2Vo(roleRepository.findById(id).orElse(null));
  }

  public Role findById(Long id) {
    return roleRepository.findById(id).orElse(null);
  }

  @Transactional @Override public void deepDelete(Long id) throws Exception {
    Role role = findById(id);
    if (role == null) {
      throw new CommonsException(ErrorType.SYS0122, ErrorMsgHelper
          .getReturnMsg(ErrorType.SYS0122, getClassT().getSimpleName().toLowerCase(),
              CommonsConstant.ID));
    }
    roleRepository.delete(role);
  }

  public RoleVO po2Vo(Role role) throws Exception {
    if (role == null) {
      return null;
    }
    RoleVO vo = transformer.po2VO(RoleVO.class, role);
    vo.setResources(role.getResources().stream()
        .map(resource -> {
          try {
            return transformer.po2VO(ResourceVO.class, resource);
          } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
          }
        }).collect(Collectors.toSet()));
    return vo;
  }

  // --------------------------
  // PRIVATE FIELDS AND METHODS
  // --------------------------

  private final RoleRepository roleRepository;
  private final ResourceDomain resourceDomain;

  @Autowired public RoleDomain(CustomRepository<Role, Long> repository, Transformer transformer,
      RoleRepository roleRepository, ResourceDomain resourceDomain) {
    super(repository, transformer);
    Assert.defaultNotNull(roleRepository);
    Assert.defaultNotNull(resourceDomain);
    this.roleRepository = roleRepository;
    this.resourceDomain = resourceDomain;
  }

  private Role param2Po(RoleParam param, Role role, User currentUser) throws Exception {
    transformer.param2PO(getClassT(), param, role, currentUser);
    if (StringUtils.isNotBlank(param.getResourceIds())) {
      Set<Resource> resources = Sets
          .newHashSet(resourceDomain.getAllByIds(transformer.idsStr2List(param.getResourceIds())));
      role.setResources(resources);
    } else {
      role.setResources(new HashSet<>());
    }
    return role;
  }

  private void nameExists(String name) throws Exception {
    if (roleRepository.findByNameAndValidFlag(name, ValidFlag.VALID).isPresent()) {
      // Throw role already existing exception, name taken.
      throw new CommonsException(ErrorType.SYS0111, ErrorMsgHelper
          .getReturnMsg(ErrorType.SYS0111, getClassT().getSimpleName().toLowerCase(),
              CommonsConstant.NAME));
    }
  }
}
