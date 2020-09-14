package script.db

databaseChangeLog(logicalFilePath: 'script/db/hitf_interface_server.groovy') {

    changeSet(author: "shuangfei.zhu@hand-china.com", id: "2019-02-28-hitf_interface_server") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hitf_interface_server_s', startValue:"1")
        }
        createTable(tableName: "hitf_interface_server", remarks: "服务配置") {
            column(name: "interface_server_id", type: "bigint(20)", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)}
            column(name: "tenant_id", type: "bigint(20)",  remarks: "租户ID")  {constraints(nullable:"false")}
            column(name: "server_code", type: "varchar(" + 30 * weight + ")",  remarks: "服务代码")  {constraints(nullable:"false")}
            column(name: "server_name", type: "varchar(" + 250 * weight + ")",  remarks: "服务名称")  {constraints(nullable:"false")}
            column(name: "service_type", type: "varchar(" + 30 * weight + ")",  remarks: "服务类型，代码：HITF.SERVICE_TYPE")  {constraints(nullable:"false")}
            column(name: "domain_url", type: "varchar(" + 200 * weight + ")",  remarks: "服务地址")  {constraints(nullable:"false")}
            column(name: "auth_type", type: "varchar(" + 30 * weight + ")",   defaultValue:"NONE",   remarks: "认证模式，代码：HITF.AUTH_TYPE")  {constraints(nullable:"false")}
            column(name: "grant_type", type: "varchar(" + 30 * weight + ")",  remarks: "授权模式，代码：HITF.GRANT_TYPE")
            column(name: "access_token_url", type: "varchar(" + 255 * weight + ")",  remarks: "获取Token的URL")
            column(name: "client_id", type: "varchar(" + 255 * weight + ")",  remarks: "客户端ID")
            column(name: "client_secret", type: "varchar(" + 255 * weight + ")",  remarks: "客户端密钥")
            column(name: "auth_username", type: "varchar(" + 80 * weight + ")",  remarks: "认证用户名")
            column(name: "auth_password", type: "varchar(" + 255 * weight + ")",  remarks: "认证密码")
            column(name: "scope", type: "varchar(" + 255 * weight + ")",  remarks: "权限范围")
            column(name: "soap_namespace", type: "varchar(" + 80 * weight + ")",  remarks: "SOAP命名空间")
            column(name: "soap_element_prefix", type: "varchar(" + 30 * weight + ")",  remarks: "SOAP参数前缀标识")
            column(name: "soap_wss_password_type", type: "varchar(" + 30 * weight + ")",  remarks: "SOAP加密类型")
            column(name: "soap_username", type: "varchar(" + 80 * weight + ")",  remarks: "校验用户名")
            column(name: "soap_password", type: "varchar(" + 255 * weight + ")",  remarks: "校验密码")
            column(name: "enabled_flag", type: "tinyint(1)",   defaultValue:"1",   remarks: "是否启用。1启用，0未启用")  {constraints(nullable:"false")}
            column(name: "object_version_number", type: "bigint(20)",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}
            column(name: "created_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}
            column(name: "last_updated_by", type: "bigint(20)",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}

        }

        addUniqueConstraint(columnNames:"server_code,tenant_id",tableName:"hitf_interface_server",constraintName: "hitf_interface_server_u1")
    }

    changeSet(author: "jianbo.li@hand-china.com",id: "2019-07-09-hitf_interface_server"){
        if (helper.isSqlServer()) {
            dropDefaultValue(tableName: 'hitf_interface_server', columnName: 'auth_type')
        }
        dropColumn(tableName: "hitf_interface_server",columnName:"auth_type" )
        dropColumn(tableName: "hitf_interface_server",columnName:"grant_type" )
        dropColumn(tableName: "hitf_interface_server",columnName:"access_token_url" )
        dropColumn(tableName: "hitf_interface_server",columnName:"client_id" )
        dropColumn(tableName: "hitf_interface_server",columnName:"client_secret" )
        dropColumn(tableName: "hitf_interface_server",columnName:"auth_username" )
        dropColumn(tableName: "hitf_interface_server",columnName:"auth_password" )
        dropColumn(tableName: "hitf_interface_server",columnName:"soap_wss_password_type" )
        dropColumn(tableName: "hitf_interface_server",columnName:"soap_username" )
        dropColumn(tableName: "hitf_interface_server",columnName:"soap_password" )
        dropColumn(tableName: "hitf_interface_server",columnName:"scope" )
    }

    changeSet(author: "jianbo.li@hand-china.com",id: "2019-08-15-hitf_interface_server"){
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        addColumn(tableName:"hitf_interface_server"){
            column(name: "service_category",type: "varchar(" + 30 * weight + ")",remarks: "服务类别",defaultValue:"EXTERNAL")
        }
    }
    changeSet(author: "jianbo.li@hand-china.com",id: "2019-08-22-hitf_interface_server"){
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        dropNotNullConstraint(tableName:"hitf_interface_server",columnName:"domain_url",columnDataType:"varchar(" + 200 * weight + ")")
        modifyDataType(tableName:"hitf_interface_server",columnName:"server_code",newDataType:"varchar(" + 128 * weight + ")")
    }

    changeSet(author: "xingxing.wu@hand-china.com", id: "2019-08-23-hitf_interface_server") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        addColumn(tableName: 'hitf_interface_server') {
            column(name: "namespace", type: "varchar(" + 30 * weight + ")", remarks: "命名空间，默认租户编码，与服务代码一起构成唯一", defaultValue:"HZERO"){constraints(nullable:"false")}
        }
    }

    changeSet(author: "jianbo.li@hand-china.com",id: "2019-09-04-hitf_interface_server"){
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        modifyDataType(tableName:"hitf_interface_server",columnName:"soap_namespace",newDataType:"varchar(" + 255 * weight + ")")
    }
    changeSet(author: "xingxing.wu@hand-china.com", id: "2019-09-04-hitf_interface_server") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        addColumn(tableName: 'hitf_interface_server') {
            column(name: "enabled_certificate_flag", type: "tinyint(1)", remarks: "启用证书", defaultValue:"0") {constraints(nullable:"false")}
        }
    }
    changeSet(author: "xingxing.wu@hand-china.com", id: "2019-09-05-hitf_interface_server") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        addColumn(tableName: 'hitf_interface_server') {
            column(name: "certificate_id", type: "bigint(20)", remarks: "CA证书ID")
        }
    }
    changeSet(author: "jianbo.li@hand-china.com", id: "2019-12-11-hitf_interface_server") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        addColumn(tableName: 'hitf_interface_server') {
            column(name: "swagger_url", type: "varchar("+600*weight+")", remarks: "Swagger地址")
        }
    }

    changeSet(author: "wanjun.feng@hand-china.com", id: "2020-03-18-hitf_interface_server") {
        addColumn(tableName: 'hitf_interface_server') {
            column(name: "public_flag", type: "tinyint(1)", remarks: "公开接口标识", defaultValue:"0") {constraints(nullable:"false")}
        }
    }

    // 添加服务调用Content-Type类型配置以及SOAP响应报文标签
    changeSet(author: "xiaolong.li@hand-china.com", id: "2020-06-04-hitf_interface_server") {
        def weight = 1
        if (helper.isSqlServer()) {
            weight = 2
        } else if (helper.isOracle()) {
            weight = 3
        }
        addColumn(tableName: 'hitf_interface_server') {
            column(name: "request_content_type", type: "varchar(" + 80 * weight + ")", remarks: "请求报文Content-Type类型")
            column(name: "response_content_type", type: "varchar(" + 80 * weight + ")", remarks: "响应报文Content-Type类型")
            column(name: "soap_data_node", type: "varchar(" + 100 * weight + ")", remarks: "soap响应数据节点标签")
        }
    }
}