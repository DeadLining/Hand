> @LovValue(
> 		lovCode = "HZERO.28250.ORDER.COMPANY", meaningField = "companyNameMeaning"
> )
> @Transient

```java
@ApiModelProperty(value = "公司名称", required = true)
@NotBlank
@LovValue(
	lovCode = "HZERO.28250.ORDER.COMPANY", meaningField = "companyNameMeaning"
)
private String companyName;
@Transient
private String companyNameMeaning;
```



> `@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)`

```java
//Test1Controller(ResponseEntity)
@ApiOperation(value = "明细")
@Permission(level = ResourceLevel.ORGANIZATION)
@GetMapping("/{companyId}")
@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
public ResponseEntity<HodrCompany> detail(@PathVariable Long organizationId,
                                          @PathVariable Long companyId) {
	HodrCompany hodrCompany = hodrCompanyRepository.selectByPrimaryKey(companyId);
	return Results.success(hodrCompany);
}
```

```java
//Test2Controller(List)
@ApiOperation(value = "条件查询订单汇总")
@Permission(level = ResourceLevel.SITE)
@GetMapping("/list")
@ProcessLovValue(targetField = BaseConstants.FIELD_BODY)
public ResponseEntity<Page<OrderReturnDTO>> list(@PathVariable Long organizationId, 
                                                 OrderConditions orderConditions,
                                                 PageRequest pageRequest) {
	return Results.success(orderRepository.pageOrderList(orderConditions, pageRequest));
}
```

