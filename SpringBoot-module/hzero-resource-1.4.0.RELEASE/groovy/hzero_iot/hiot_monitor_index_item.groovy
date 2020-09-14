package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_monitor_index_item.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_monitor_index_item") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_monitor_index_item_s', startValue:"1")
        }
        createTable(tableName: "hiot_monitor_index_item", remarks: "监测指标关联项") {
            column(name: "INDEX_ITEM_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "INDEX_ID", type: "bigint",  remarks: "监测指标主键")  {constraints(nullable:"false")}  
            column(name: "PROPERTY_MODEL_ID", type: "bigint",  remarks: "数据点模板主键")   
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "所属组织ID")   
            column(name: "TENANT_ID", type: "bigint",   defaultValue:"0",   remarks: "租户id")   
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"TENANT_ID,INDEX_ID,PROPERTY_MODEL_ID",tableName:"hiot_monitor_index_item",constraintName: "hiot_monitor_index_item_u1")
    }
}