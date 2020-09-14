package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_prop_group_rel.groovy') {
    changeSet(author: "hzero-iot@hand-china.com", id: "2019-11-25-hiot_prop_group_rel") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_prop_group_rel_s', startValue:"1")
        }
        createTable(tableName: "hiot_prop_group_rel", remarks: "数据点组与数据点关系") {
            column(name: "REL_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "THING_ID", type: "bigint",  remarks: "设备ID, hiot_thing.THING_ID")  {constraints(nullable:"false")}  
            column(name: "GROUP_ID", type: "bigint",  remarks: "数据点组ID, hiot_property_group.GROUP_ID")  {constraints(nullable:"false")}  
            column(name: "PROPERTY_ID", type: "bigint",  remarks: "数据点ID, hiot_property.PROPERTY_ID")  {constraints(nullable:"false")}  
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "组织ID, 实例关系需要隔离到组织, 需要设置值")  {constraints(nullable:"false")}  
            column(name: "TENANT_ID", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"GROUP_ID,PROPERTY_ID",tableName:"hiot_prop_group_rel",constraintName: "hiot_prop_group_rel_u1")
    }
}