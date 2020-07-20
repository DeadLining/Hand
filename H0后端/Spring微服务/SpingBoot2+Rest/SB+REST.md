#### @Mapper和@Repository的区别

[link]: https://www.cnblogs.com/jtfr/p/10962205.html
[示例]: https://www.cnblogs.com/vwater/p/10315217.html

#### Mapper VS Repository

- Mapper是Mybatis的
- Repository是Spring的



## GET方法

1. `getForObject`：对象
2. `getForEntity`：对象 + Http 状态码 + 请求头

##### `getForObject`代码：

```java
RestTemplate restTemplate;
@GetMapping("/getForObject")
public Map<String, Object> getForObject(){
	String url = "";
	Map<String, Object> param = new HashMap<>();
	Map<String, Object> result = restTemplate.getForObject(url, Map.class, param);
	return result;
}
```

代码解读：

`restTemplate.getForObject(url, Map.class(返回类型), param(必须是Map类型的参数));`

##### `getForEntity`代码：

```java
RestTemplate restTemplate;
@GetMapping("/getForEntity")
public Map<String, Object> getForEntity(){
	String url = "http://localhost:8080/addUser/100000/fencaibc";
	Map<String, Long> paramMap = new HashMap<>();
	ResponseEntity<HashMap> responseEntity = restTemplate.getForEntity(url, HashMap.class, paramMap);(对象:HashMap)
	HttpStatus statusCode = responseEntity.getStatusCode();(状态码)
	int statusCodeValue = responseEntity.getStatusCodeValue();
	HttpHeaders headers = responseEntity.getHeaders();(请求头)
	return responseEntity.getBody();
}
```

代码解读：

`restTemplate.getForEntity(url, HashMap.class(返回类型), paramMap(Map类型的参数));`

**注：**`ResponseEntity`继承于`HttpEntity`，**返回`JSON`数据**



## POST方法

1. `psotForObject`
2. `postForEntity`

**注：**Spring Boot RestTemplate 的Post方法和Get方法的区别是**Post方法传参Map必须是`MultiValueMap`，Get方法只需要`HashMap`**

### `psotForObject`代码

实例1（无`@RequestBody`）

```java
@GetMapping("/postForObject")
public UserDTO postForObject(){
	String url = "http://localhost:8080/addUser";
	MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
	paramMap.add("userId",1000L);
	paramMap.add("userName", "kong");
	UserDTO userDTO = restTemplate.postForObject(url, paramMap, UserDTO.class);
	return userDTO;
}
```



```java
@PostMapping(value = "/addUser")
public UserDTO addUser(UserDTO userDTO){
	return userDTO;
}
//or
@PostMapping(value = "/addUser")
public UserDTO addUser(Long userId, String userName){
	UserDTO userDTO = new UserDTO();
	userDTO.setUserId(userId);
	userDTO.setUserName(userName);
	return userDTO;
}
```

上述代码可正常执行

**注：**Post方法的MultiValueMap既支持基本类型**分开传参**，也支持**实体传参**



实例2（有`@RequestBody`）

```java
@GetMapping("/postForObject")
public UserDTO postForObject(){
	String url = "http://localhost:8080/addUser";
	MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
	paramMap.add("userId",1000L);
	paramMap.add("userName", "kong");
	UserDTO userDTO = restTemplate.postForObject(url, paramMap, UserDTO.class);
	return userDTO;
}
```



```java
@PostMapping(value = "/addUser")
public UserDTO addUser(@RequestBody UserDTO userDTO){
	return userDTO;
}
```

上述代码不可正常运行，报错：500



解决方案：

```java
@GetMapping("/postForObject")
public UserDTO postForObject(){
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);

	String url = "http://localhost:8080/addUser";
	UserDTO userDTO = new UserDTO();
	userDTO.setUserId(1000L);
	userDTO.setUserName("kong");
	HttpEntity<UserDTO> entityParam = new HttpEntity<UserDTO>(userDTO, headers);

	UserDTO result = restTemplate.postForObject(url, entityParam, UserDTO.class);
	return result;
}
```



### `postForEntity`代码

示例1（无`@RequestBody`）

```java
@GetMapping("/postForEntity")
public UserDTO postForEntity() {
	String url = "http://localhost:8080/addUser";
	MultiValueMap<String, Object> paramMap = new LinkedMultiValueMap<>();
	paramMap.add("userId", 1000L);
	paramMap.add("userName", "kong");
	ResponseEntity<UserDTO> userDTOResponseEntity = restTemplate.postForEntity(url, paramMap, UserDTO.class);
	HttpStatus statusCode = userDTOResponseEntity.getStatusCode();
	int statusCodeValue = userDTOResponseEntity.getStatusCodeValue();
	HttpHeaders headers = userDTOResponseEntity.getHeaders();
	return userDTOResponseEntity.getBody();
}
```



示例1（有`@RequestBody`）

```java
@GetMapping("/postForEntity")
public UserDTO postForEntity() {
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);

	String url = "http://localhost:8080/addUser";
	UserDTO userDTO = new UserDTO();
	userDTO.setUserId(1000L);
	userDTO.setUserName("kong");
	HttpEntity<UserDTO> entityParam = new HttpEntity<UserDTO>(userDTO, headers);

	ResponseEntity<UserDTO> result = restTemplate.postForEntity(url, entityParam, UserDTO.class);
	return result.getBody();
}
```

**注：**`postFotEntity`和`postForObject`的唯一区别是返回的时候使用`ResponseEntity<T>`，其他的一样



## 补充

- spring中的`ResponseEntity`理解

  `ResponseEntity`可以分成：

  - `ResponseBody`
  - `ResponseStatus`

  [link]: https://www.cnblogs.com/flypig666/p/11729757.html

- 