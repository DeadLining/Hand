package script.db

databaseChangeLog(logicalFilePath: 'script/db/hitf_interface_log_dtl.groovy') {
    changeSet(author: "shuangfei.zhu@hand-china.com", id: "2019-02-28-hitf_interface_log_dtl") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hitf_interface_log_dtl_s', startValue:"1")
        }
        createTable(tableName: "hitf_interface_log_dtl", remarks: "") {
            column(name: "interface_log_dtl_id", type: "bigint", autoIncrement: true ,   remarks: "")  {constraints(primaryKey: true)} 
            column(name: "tenant_id", type: "bigint",  remarks: "")  {constraints(nullable:"false")}  
            column(name: "interface_log_id", type: "bigint",  remarks: "")  {constraints(nullable:"false")}  
            column(name: "invoke_key", type: "varchar(" + 255 * weight + ")",  remarks: "")  {constraints(nullable:"false")}  
            column(name: "interface_req_header_param", type: "varchar(1800)",  remarks: "")
            column(name: "interface_req_body_param", type: "longtext",  remarks: "")   
            column(name: "interface_resp_content", type: "longtext",  remarks: "")   
            column(name: "req_header_param", type: "varchar(1800)",  remarks: "")
            column(name: "req_body_param", type: "longtext",  remarks: "")   
            column(name: "resp_content", type: "longtext",  remarks: "")   
            column(name: "stacktrace", type: "longtext",  remarks: "")   
            column(name: "remark", type: "longtext",  remarks: "")   
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }
}