package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_monitor_index.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_monitor_index") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_monitor_index_s', startValue:"1")
        }
        createTable(tableName: "hiot_monitor_index", remarks: "监测指标") {
            column(name: "INDEX_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "INDEX_CODE", type: "varchar(" + 64 * weight + ")",  remarks: "监测指标编码")  {constraints(nullable:"false")}  
            column(name: "INDEX_NAME", type: "varchar(" + 64 * weight + ")",  remarks: "监测指标名称")  {constraints(nullable:"false")}  
            column(name: "UNIT_CODE", type: "varchar(" + 20 * weight + ")",  remarks: "单位")   
            column(name: "ITEM_TYPE", type: "tinyint",  remarks: "适用范围, 0-设备模板")  {constraints(nullable:"false")}  
            column(name: "ITEM_ID", type: "bigint",  remarks: "如果适用范围为模板， 则为模板ID； 如果适用范围为设备，则为设备范围ID。")  {constraints(nullable:"false")}  
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

        addUniqueConstraint(columnNames:"TENANT_ID,INDEX_CODE",tableName:"hiot_monitor_index",constraintName: "hiot_monitor_index_u1")
    }
}