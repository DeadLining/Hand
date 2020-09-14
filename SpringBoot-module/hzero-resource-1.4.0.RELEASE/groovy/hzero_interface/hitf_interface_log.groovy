package script.db

databaseChangeLog(logicalFilePath: 'script/db/hitf_interface_log.groovy') {

    changeSet(author: "shuangfei.zhu@hand-china.com", id: "2019-02-28-hitf_interface_log") {
        def weight = 1
        if (helper.isSqlServer()) {
            weight = 2
        } else if (helper.isOracle()) {
            weight = 3
        }
        if (helper.dbType().isSupportSequence()) {
            createSequence(sequenceName: 'hitf_interface_log_s', startValue: "1")
        }
        createTable(tableName: "hitf_interface_log", remarks: "") {
            column(name: "interface_log_id", type: "bigint(20)", autoIncrement: true, remarks: "") {
                constraints(primaryKey: true)
            }
            column(name: "tenant_id", type: "bigint(20)", remarks: "") { constraints(nullable: "false") }
            column(name: "invoke_key", type: "varchar(" + 255 * weight + ")", remarks: "") {
                constraints(nullable: "false")
            }
            column(name: "application_code", type: "varchar(" + 30 * weight + ")", remarks: "")
            column(name: "application_name", type: "varchar(" + 250 * weight + ")", remarks: "")
            column(name: "server_code", type: "varchar(" + 30 * weight + ")", remarks: "")
            column(name: "server_name", type: "varchar(" + 250 * weight + ")", remarks: "")
            column(name: "client_id", type: "varchar(" + 255 * weight + ")", remarks: "")
            column(name: "interface_type", type: "varchar(" + 30 * weight + ")", remarks: "")
            column(name: "interface_url", type: "varchar(" + 255 * weight + ")", remarks: "")
            column(name: "interface_request_method", type: "varchar(" + 30 * weight + ")", remarks: "")
            column(name: "interface_request_time", type: "datetime", remarks: "")
            column(name: "interface_response_time", type: "bigint(20)", remarks: "")
            column(name: "interface_response_code", type: "varchar(" + 10 * weight + ")", remarks: "")
            column(name: "interface_response_status", type: "varchar(" + 255 * weight + ")", remarks: "")
            column(name: "request_method", type: "varchar(" + 30 * weight + ")", remarks: "")
            column(name: "request_time", type: "datetime", remarks: "")
            column(name: "response_time", type: "bigint(20)", remarks: "")
            column(name: "response_code", type: "varchar(" + 10 * weight + ")", remarks: "")
            column(name: "response_status", type: "varchar(" + 255 * weight + ")", remarks: "")
            column(name: "ip", type: "varchar(" + 40 * weight + ")", remarks: "")
            column(name: "referer", type: "varchar(" + 250 * weight + ")", remarks: "")
            column(name: "user_agent", type: "varchar(" + 250 * weight + ")", remarks: "")
            column(name: "object_version_number", type: "bigint(20)", defaultValue: "1", remarks: "") {
                constraints(nullable: "false")
            }
            column(name: "creation_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "") {
                constraints(nullable: "false")
            }
            column(name: "created_by", type: "bigint(20)", defaultValue: "-1", remarks: "") {
                constraints(nullable: "false")
            }
            column(name: "last_updated_by", type: "bigint(20)", defaultValue: "-1", remarks: "") {
                constraints(nullable: "false")
            }
            column(name: "last_update_date", type: "datetime", defaultValueComputed: "CURRENT_TIMESTAMP", remarks: "") {
                constraints(nullable: "false")
            }
        }
    }
    changeSet(author: "xingxing.wu@hand-china.com", id: "2019-06-11-hitf_interface_log") {
        def weight = 1
        if (helper.isSqlServer()) {
            weight = 2
        } else if (helper.isOracle()) {
            weight = 3
        }
        addColumn(tableName: 'hitf_interface_log') {
            column(name: "invoke_type", type: "varchar(" + 30 * weight + ")", defaultValue: "NORMAL", remarks: "调用类型，代码HITF.INVOKE_TYPE") {
                constraints(nullable: "false")
            }
        }
        addColumn(tableName: 'hitf_interface_log') {
            column(name: "invoke_source_id", type: "bigint(20)", remarks: "调用来源ID，例如，测试用例类型，此处即为测试用例ID")
        }
        addColumn(tableName: 'hitf_interface_log') {
            column(name: "interface_code", type: "varchar(" + 30 * weight + ")", remarks: "接口代码")
        }
    }
    changeSet(author: "jianbo.li@hand-china.com",id: "2019-08-29-hitf_interface_log"){
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        modifyDataType(tableName:"hitf_interface_log",columnName:"server_code",newDataType:"varchar(" + 128 * weight + ")")
        modifyDataType(tableName:"hitf_interface_log",columnName:"interface_code",newDataType:"varchar(" + 128 * weight + ")")
    }

    changeSet(author: "junlin.zhu@hand-china.com", id: "2020-03-31-update-hitf_interface_log") {
        def weight = 1
        if (helper.isSqlServer()) {
            weight = 2
        } else if (helper.isOracle()) {
            weight = 3
        }
        addColumn(tableName: 'hitf_interface_log') {
            column(name: "interface_id", type: "bigint", remarks: "接口主键, hitf_interface.interface_id")
            column(name: "interface_server_id", type: "bigint", remarks: "服务主键, hitf_interface_server.interface_server_id")
            column(name: "interface_name", type: "varchar(" + 250 * weight + ")", remarks: "接口名称")
        }
    }
}