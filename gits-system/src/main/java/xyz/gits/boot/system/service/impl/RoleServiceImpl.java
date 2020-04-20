package xyz.gits.boot.system.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import xyz.gits.boot.api.system.entity.Role;
import xyz.gits.boot.system.mapper.RoleMapper;
import xyz.gits.boot.system.service.IRoleService;
import xyz.gits.boot.common.basic.BasicServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author songyinyin
 * @date 2020-02-29
 */
@Service
public class RoleServiceImpl extends BasicServiceImpl<RoleMapper, Role> implements IRoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> getRolesByUserId(String userId) {
        return roleMapper.getRolesByUserId(userId);
    }
}
