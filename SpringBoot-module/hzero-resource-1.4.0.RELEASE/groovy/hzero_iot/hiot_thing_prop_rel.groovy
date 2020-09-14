package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_thing_prop_rel.groovy') {
    changeSet(author: "hzero-iot@hand-china.com", id: "2019-11-25-hiot_thing_prop_rel") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_thing_prop_rel_s', startValue:"1")
        }
        createTable(tableName: "hiot_thing_prop_rel", remarks: "设备与数据点或数据点组关系") {
            column(name: "REL_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "THING_ID", type: "bigint",  remarks: "设备ID, hiot_thing.THING_ID")  {constraints(nullable:"false")}  
            column(name: "ITEM_TYPE", type: "tinyint",  remarks: "关联对象类型, 1-数据点组, 0-数据点")  {constraints(nullable:"false")}  
            column(name: "ITEM_ID", type: "bigint",  remarks: "ITEM_TYPE=1,ITEM_ID=数据点组ID, hiot_property_group.GROUP_ID; ITEM_TYPE=0,ITEM_ID=数据点ID, hiot_property.PROPERTY_ID")  {constraints(nullable:"false")}  
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "组织ID, 实例关系需要隔离到组织, 需要设置值")  {constraints(nullable:"false")}  
            column(name: "TENANT_ID", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"THING_ID,ITEM_TYPE,ITEM_ID",tableName:"hiot_thing_prop_rel",constraintName: "hiot_thing_prop_rel_u1")
    }
}