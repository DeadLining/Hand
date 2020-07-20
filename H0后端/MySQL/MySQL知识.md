[TOC]

### DML/DDL/DCL

**DML用于查询与修改数据记录，包括如下SQL语句：**

- INSERT：添加数据到数据库中。
- UPDATE：修改数据库中的数据。
- DELETE：删除数据库中的数据。
- SELECT：选择（查询）数据，SELECT是SQL语言的基础，最为重要。
  - 多表连接查询：笛卡尔积

<img src="MySQL知识.assets\image-20200713152254929.png" alt="image-20200713152254929"  />

**DDL用于定义数据库的结构，比如创建、修改或删除数据库对象，包括如下SQL语句：**

- CREATETABLE：创建数据库表
- ALTER TABLE：更改表结构、添加、删除、修改列长度
- DROP TABLE：删除表
- CREATE INDEX：在表上建立索引
- DROP INDEX：删除索引

<img src="MySQL知识.assets\image-20200713152546398.png" alt="image-20200713152546398" style="zoom:67%;" />

**DCL用来控制数据库的访问，包括如下SQL语句：**

- lGRANT：授予访问权限。
- lREVOKE：撤销访问权限。
- lCOMMIT：提交事务处理。
- lROLLBACK：事务处理回退。
- lSAVEPOINT：设置保存点。
- lLOCK：对数据库的特定部分进行锁定。

------



### SELECT

```sql
SELECT*|{[DISTINCT] column|expression[alias],...}
FROMtable;
//SELECT  表示要取哪些列 
//FROM 表示要从哪些表中取 
```

**SQL**语句中的数学表达式：对于数值和日期型字段，可以进行 “加减乘除” 

<img src="MySQL知识.assets\image-20200713152725547.png" alt="image-20200713152725547" style="zoom:67%;" />

**列的别名**

- 重命名一个列。
- 便于计算。
- 紧跟列名，也可以在列名和别名之间加入关键字‘AS’，  
    别名使用双引号，以便在别名中包含空格或特殊的字符并 
    区分大小写。

**注意**

- SQL 语言大小写不敏感。
- SQL 可以写在一行或者多行
- 关键字不能被缩写也不能分行
- 各子句一般要分行写。
- 使用缩进提高语句的可读性

------



### WHERE(过滤)

```sql
SELECT    *|{[DISTINCT] column|expression[alias],...}
FROM     table
[WHERE  condition(s)];
```

------



### 比较运算

```sql
SELECT last_name, salary FROM employees WHERE salary <= 3000; 
```

<img src="MySQL知识.assets\image-20200713142305084.png" alt="image-20200713142305084" style="zoom:80%;" />

------



### ORDER BY子句

```sql
SELECT last_name, job_id, department_id,hire_date
FROM employees
ORDER BY hire_date ;
```

- 使用ORDER BY 子句排序(默认为**ASC**)
  - **ASC**（ascend）**:** 升序
  - **DESC**（descend）**:** 降序
- **ORDER BY** 子句在**SELECT**语句的<font color='red'>结尾</font>。

**按别名排序**

```sql
SELECT employee_id, last_name, salary*12 annsal
 FROM employees
ORDER BY annsal;
```

**多个列排序**

```sql
SELECT last_name, department_id, salary
  FROM employees
  ORDER BY department_id, salary DESC;
```

------



### 组函数（例如AVG()）

**非法使用组函数**

- <font color='red'>不能</font>在**WHERE** 子句中使用组函数。
- <font color='red'>可以</font>在**HAVING** 子句中使用组函数。

```sql
SELECT department_id, AVG(salary)
FROM employees
WHERE AVG(salary) > 8000
GROUP BY department_id;

//报错
WHERE AVG(salary) > 8000
*
ERROR at line 3:
ORA-00934: group function is not allowed here
```

------



### HAVING

**过滤分组：HAVING子句**

使用HAVING 过滤分组:

- 行已经被分组。
- 使用了组函数。
- 满足HAVING 子句中条件的分组将被显示

```sql
SELECT       column, group_function
FROM         table
[WHERE      condition]
[GROUP BY group_by_expression]
[HAVING     group_condition]
[ORDER BY  column];
```

------



### 常见函数

[link]: https://mubu.com/doc5CZ7XXjVjkU#mindmap

#### 字符函数

**大小写控制函数**

- 这类函数改变字符的大小写。

<img src="MySQL知识.assets\image-20200713143846246.png" alt="image-20200713143846246" style="zoom: 67%;" />

**字符控制函数**

- 这类函数控制字符。

> **TRIM函数**可以过滤指定的字符串：
>
> 完整格式：TRIM([{BOTH | LEADING | TRAILING} [remstr] FROM] str)
>
> 简化格式：TRIM([remstr FROM] str)
>
> 返回字符串 *str*，其中所有*remstr*<font color='red'>**前缀和/或后缀**</font>都已被删除。若分类符BOTH、LEADIN或TRAILING中没有一个是给定的,则假设为BOTH。*remstr*为可选项，在未指定情况下，可删除空格。
>
> **replace函数**
>
> 语法：replace(object,search,replace) 
>
> 语义：把object对象中出现的的search<font color='red'>**全部**</font>替换成replace。 
>
> 实例：
>
> `update hellotable set 'helloCol' = replace('helloCol','helloSearch','helloReplace')`
>
> **replace into函数**
>
> 为什么会接触到replace into函数，是因为业务需要向数据库中插入数据，前提是重复的不能再次插入。以前用where解决的，今天才知道还有一个更简洁的方法replace。
> **replace**具备替换拥有**唯一索引或者主键索引**重复数据的能力，也就是如果使用replace into插入的数据的唯一索引或者主键索引与之前的数据有重复的情况，将会删除原先的数据，然后再进行添加。 
>  语法：replace into table( col1, col2, col3 ) values ( val1, val2, val3 ) 
>  语义：向table表中col1, col2, col3列replace数据val1，val2，val3
>
> 实例：
>
> `REPLACE INTO users (id,name,age) VALUES(123, ‘chao’, 50);`

<img src="MySQL知识.assets\image-20200713145300180.png" alt="image-20200713145300180" style="zoom: 67%;" />

#### 数字函数

<img src="MySQL知识.assets\image-20200713145639746.png" alt="image-20200713145639746" style="zoom: 67%;" />

#### 日期函数

<img src="MySQL知识.assets\image-20200713145651717.png" alt="image-20200713145651717" style="zoom: 67%;" />

<img src="MySQL知识.assets\image-20200713145801672.png" alt="image-20200713145801672"  />

------



### WHEN-THEN-ELSE(条件表达式)

- 在SQL语句中使用`WHEN-THEN-ELSE`逻辑 使用方法:
  - `CASE-END`表达式

<img src="MySQL知识.assets\image-20200713150150873.png" alt="image-20200713150150873" style="zoom:67%;" />

```sql
SELECT last_name,  job_id,  salary,
    CASE   job_id   WHEN  'IT_PROG‘  THEN 1.10*salary
                    WHEN  'ST_CLERK' THEN 1.15*salary
					WHEN  'SA_REP' THEN  1.20*salary
					ELSE  salary    
	END  "REVISED_SALARY"
FROM employees;
```

------



### INSERT INTO

**插入数据**

- 为每一列添加一个新值。
- 按列的默认顺序列出各个列的值。
- 在INSERT子句中随意列出列名和他们的值。
- <font color='red'>字符和日期型数据</font>应包含在<font color='red'>单引号</font>中。

<img src="MySQL知识.assets\image-20200713150433171.png" alt="image-20200713150433171" style="zoom:67%;" />

**插入空值**

- 隐式方式: 在列名表中省略该列的值。

<img src="MySQL知识.assets\image-20200713150643389.png" alt="image-20200713150643389" style="zoom:67%;" />

- 显示方式: 在VALUES子句中指定空值。

<img src="MySQL知识.assets\image-20200713150652252.png" alt="image-20200713150652252" style="zoom:67%;" />

**从其他表中拷数据**

- 在INSERT 语句中加入子查询。
- <font color='red'>不必书写**VALUES** 子句。</font>
- 子查询中的值列表应与INSERT 子句中的列名对应

<img src="MySQL知识.assets\image-20200713151331713.png" alt="image-20200713151331713" style="zoom:67%;" />

------



### UPDATE-SET

**更新数据**

- 使用UPDATE 语句更新数据。
- <font color='red'>可以一次更新多条数据。</font>
- <font color='red'>如果需要回滚数据，需要保证在DML前，进行设置：`SET AUTOCOMMIT = FALSE;`</font>

<img src="MySQL知识.assets\image-20200713151701939.png" alt="image-20200713151701939" style="zoom:67%;" />

- 使用**WHERE**子句指定需要更新的数据。

<img src="MySQL知识.assets\image-20200713151751684.png" alt="image-20200713151751684" style="zoom:67%;" />

- 如果省略WHERE 子句，则表中的所有数据都将被更新。

<img src="MySQL知识.assets\image-20200713151808897.png" alt="image-20200713151808897" style="zoom:67%;" />

------



### DELETE FROM

- 使用WHERE 子句删除指定的记录。
- 如果省略WHERE 子句，则表中的全部数据将被删除。

<img src="MySQL知识.assets\image-20200713151856830.png" alt="image-20200713151856830" style="zoom: 80%;" />

------



### LIKE

LIKE运算符用于WHERE表达式中，以搜索匹配字段中的指定内容

```sql
WHERE column LIKE pattern
WHERE column NOT LIKE pattern
```

**注意**：X%表示以"X"开头，而后面可以是任意字符，同样，%X，表示以”X”结尾，而%X%则表示包含“X”这个字符(并一同包含"%X"和"X%"这两种情况)

```sql
SELECT * FROM username WHERE LIKE BINARY '%X%'
```

当LIKE匹配时加上BINARY操作符之后，则会严格区分英文大小写，因此检索的内容中如果出现中英文混合且需要忽略英文大小写的时候，就会遇见问题，这个时候可以引入MySQL中的UPPER()和CONCAT()函数

```sql
SELECT * FROM username WHERE UPPER(username) LIKE BINARY CONCAT('%',UPPER('a中文b')，'%');
```

------



### 子查询

**按子查询出现的位置：**

- select后面：
  - 仅仅支持标量子查询
- from后面：
  - 支持表子查询
- where或having后面：(小括号内)
  - 标量子查询（单行子查询）
  - 列子查询（多行子查询）
  - 行子查询（多列多行）
  - 特点：
    - <font color='red'>子查询放在**小括号**内</font>
    - 子查询一般放在条件的右侧
    - 标量子查询，一般搭配着单行操作符使用（\> < >= <= = <>）
    - 列子查询，一般搭配着多行操作符使用
      in、any/some、all  <>all =not in  any=in  any(等于其中的一个)  all(所有满足)
    - <font color='red'>子查询的执行**优先于**主查询的执行，主查询的条件用到了子查询的结果</font>
- exists后面（相关子查询）
- 表子查询

**按结果集的行列数不同：**

- 标量子查询（结果集只有一行一列）
- 列子查询（结果集只有一列多行）
- 行子查询（结果集有一行多列）
- 表子查询（结果集一般为多行多列）

------



### 字符存储类型

**char：**存储定长数据很方便，CHAR字段上的索引效率级高，必须在括号里定义长度，可以有默认值，比如定义char(10)，那么不论你存储的数据是否达到了10个字节，<font color='red'>都要占去10个字节的空间（自动用空格填充）</font>，且在检索的时候后面的<font color='red'>空格会隐藏掉</font>，所以检索出来的数据需要记得<font color='red'>用什么trim之类的函数去过滤空格</font>。

**varchar：**存储变长数据，但存储效率没有CHAR高，必须在括号里定义长度，可以有默认值。保存数据的时候，<font color='red'>不进行空格自动填充</font>，而且如果数据存在空格时，当值保存和检索时<font color='red'>尾部的空格仍会保留</font>。另外，<font color='red'>varchar类型的实际长度是它的值的实际长度+1</font>，这一个字节用于<font color='red'>保存实际使用了多大的长度</font>。

**text：**存储可变长度的非Unicode数据，最大长度为2^31-1个字符。text列不能有默认值，存储或检索过程中，不存在大小写转换，后面如果指定长度，不会报错误，但是<font color='red'>这个长度是不起作用的</font>，意思就是你插入数据的时候，超过你指定的长度还是可以正常插入。

**结论：**

1、经常变化的字段用varchar；

2、知道固定长度的用char；

3、超过255字节的只能用varchar或者text；

4、能用varchar的地方不用text；

5、能够用数字类型的字段尽量选择数字类型而不用字符串类型，这会降低查询和连接的性能，并会增加存储开销。这是因为引擎在处理查询和连接回逐个比较字符串中每一个字符，而对于数字型而言只需要比较一次就够了；

6、同一张表出现多个大字段，能合并时尽量合并，不能合并时考虑分表。

### DB规范

[link]: http://hzerodoc.saas.hand-china.com/zh/docs/development-specification/backent-development-specification/database/db/

1：SQL查询中，尽量不要使用is null 的条件作为获取数据的条件（有其他条件的情况下可以使用is null 过滤结果,因为null是不会建立索引的，使用null作为查询条件无法使用索引，可以用其他特殊值代替null。

2：所有的外键上都必须建立索引，若没有对外键建立索引，则对父表delete操作或者update关联父表的键值操作的时候，会对子表产生全表独占锁，引发性能问题。

3：索引列上不可以使用函数或者表达式，优化器会进行全表扫描

4：使用union all替换union对多个结果集数据做并集操作且无需去重时候，使用union all 而不是union ，因为union会对结果集排序，去重，占用大量的资源。

### 命名规范

#### 建库规范

- 数据库，表，字段，索引全部用小写英文字母，英文单词之间用下划线(_)隔开。

#### 表字段规范

- 列设计规范根据业务区分使用tinyint/int/bigint，分别会占用1/4/8字节。
- 使用tinyint来代替enum,enum增加新值要进行DDL操作。
- 根据业务区分使用char/varchar解读：

1. 字段长度固定，或者长度近似的业务场景，适合使用char，能够减少碎片，查询性能高。
2. 字段长度相差较大，或者更新较少的业务场景，适合使用varchar，能够减少空间 。
3. 使用varchar(20)存储手机号，不要使用整数

- 设置lower_case_table_names=1,是使用大小写不敏感，数据库存储用小写，默认值为0。

#### 建表规范

- 每个表需要指定表主键。
- 字段名称用英文小写字母，单词之间用下划线(_)隔开。
- 列名必须见名知义。
- 每个字段需要有备注，字符串类型字段默认不能为NULL，但数字类型，时间类型的字段可以是NULL。
- 不要指定表存储引擎，字符编码。
- 表备注需清晰：如：run_status:运行状态 | 【0：未运行】【1：已运行】【2：已停止】|checkbox用|分隔开是为了模板自动生成代码时进行前端的JSON构建输出
- 下面5个字段是必须的：
   id:varchar(36):主键唯一ID,新的设计可以考虑自增ID,需要考虑数据迁移方案
   createdtime:datetime,创建时间
   createduser:varchar(36),创建人ID
   lastmodifiedtime:datetime,最后修改时间
   lastmodifieduser:varchar(36),最后修改人ID
   status:int状态【0：正常】【1：删除】
   新的架构
   id:varchar(36):主键唯一ID,新的设计可以考虑自增ID,需要考虑数据迁移方案
   created_time:datetime,创建时间
   created_user:varchar(36),创建人ID
   last_modified_time:datetime,最后修改时间
   last_modified_user:varchar(36),最后修改人ID
   status:int状态【0：正常】【1：删除】

#### 索引规范

##### 建索引规则

- 唯一索引使用uni_[字段名]来命名。
- 非唯一索引使用idx_[字段名]来命名。
- 理解组合索引最左前缀原则，避免重复建设索引，如果建立了(a,b,c)，相当于建立了(a), (a,b), (a,b,c)。

#####  最佳索引规则

- Primary key > Unique key > 一般索引

## 注意事项

- join连接查询的复杂度<font color='red'>**低于**</font>子查询的复杂度（多使用join，少使用子查询）

## 问题解决

**mysql遇见Expression #1 of SELECT list is not in GROUP BY clause and contains nonaggre的问题**

[link]: https://blog.csdn.net/plpldog/article/details/82794459