```java
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
```



```java
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
```



```java
//xxx.java
public class IamController {
    @Autowired
    private IamRemote IamRemoteHystrix;
```



```java
//OrderApplication
@EnableFeignClients
```



```java
//application.yml
feign:
  hystrix:
    enabled: true
```

