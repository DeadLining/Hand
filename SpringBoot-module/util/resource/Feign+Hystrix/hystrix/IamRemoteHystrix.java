package com.hzero.order.api.controller.remote.hystrix;

import com.hzero.order.api.controller.remote.IamRemote;
import org.hzero.iam.domain.vo.RoleVO;
import org.springframework.stereotype.Component;

/**
 * description
 *
 * @author shengzhou.kong@hand-china.com 2020/07/30 9:33
 */
@Component
public class IamRemoteHystrix implements IamRemote {
    @Override
    public RoleVO queryRoleDetails(Long organizationId, Long roleId) {
        return null;
    }
}