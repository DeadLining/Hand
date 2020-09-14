package com.hzero.order.api.controller.remote;

import com.hzero.order.api.controller.remote.hystrix.IamRemoteHystrix;
import org.hzero.iam.domain.vo.RoleVO;
import org.hzero.starter.keyencrypt.core.Encrypt;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 远程调用
 *
 * @author shengzhou.kong@hand-china.com 2020/07/29 14:36
 */
@FeignClient(value = "hzero-iam", fallback = IamRemoteHystrix.class)
public interface IamRemote {

    /**
     * description
     *
     * @param [organizationId, roleId]
     * @return org.hzero.iam.domain.vo.RoleVO
     * @author shengzhou.kong@hand-china.com 20:34 2020-07-29
     **/
    @RequestMapping("/hzero/v1/{organizationId}/roles/{roleId}")
    RoleVO queryRoleDetails(@PathVariable("organizationId") Long organizationId, @Encrypt @PathVariable Long roleId);
}
