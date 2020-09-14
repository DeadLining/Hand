package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_prop_group.groovy') {
    changeSet(author: "hzero-iot@hand-china.com", id: "2019-11-25-hiot_prop_group") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_prop_group_s', startValue:"1")
        }
        createTable(tableName: "hiot_prop_group", remarks: "数据点组") {
            column(name: "GROUP_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "GUID", type: "varchar(" + 30 * weight + ")",  remarks: "唯一标识, pg-随机码")  {constraints(nullable:"false")}  
            column(name: "THING_ID", type: "bigint",  remarks: "数据点所属设备ID, hiot_thing.THING_ID")  {constraints(nullable:"false")}  
            column(name: "GROUP_MODEL_ID", type: "bigint",  remarks: "数据点组模板ID, hiot_property_group_model.GROUP_MODEL_ID")  {constraints(nullable:"false")}  
            column(name: "GROUP_CODE", type: "varchar(" + 30 * weight + ")",  remarks: "编码")  {constraints(nullable:"false")}  
            column(name: "GROUP_NAME", type: "varchar(" + 45 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "varchar(" + 100 * weight + ")",  remarks: "说明")   
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "组织ID, 数据点组需要隔离到组织, 需要设置值")  {constraints(nullable:"false")}  
            column(name: "TENANT_ID", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"GUID",tableName:"hiot_prop_group",constraintName: "hiot_prop_group_u1")
    }
}