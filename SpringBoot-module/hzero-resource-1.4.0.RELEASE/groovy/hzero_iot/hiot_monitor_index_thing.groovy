package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_monitor_index_thing.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_monitor_index_thing") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_monitor_index_thing_s', startValue:"1")
        }
        createTable(tableName: "hiot_monitor_index_thing", remarks: "监测指标关联设备") {
            column(name: "INDEX_THING_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "INDEX_ID", type: "bigint",  remarks: "监测指标主键")  {constraints(nullable:"false")}  
            column(name: "UNIT_CODE", type: "varchar(" + 20 * weight + ")",  remarks: "单位")   
            column(name: "THING_ID", type: "bigint",  remarks: "设备主键ID")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "tinyint",   defaultValue:"1",   remarks: "是否启用。1启用，0未启用")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "varchar(" + 255 * weight + ")",  remarks: "说明")   
            column(name: "FORMULAR", type: "text",  remarks: "公式")  {constraints(nullable:"false")}  
            column(name: "FORMULAR_JSON", type: "text",  remarks: "公式json, 用于前端展示")  {constraints(nullable:"false")}  
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "所属组织ID")   
            column(name: "TENANT_ID", type: "bigint",   defaultValue:"0",   remarks: "租户id")   
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"TENANT_ID,INDEX_ID,THING_ID",tableName:"hiot_monitor_index_thing",constraintName: "hiot_monitor_index_thing_u1")
    }
}