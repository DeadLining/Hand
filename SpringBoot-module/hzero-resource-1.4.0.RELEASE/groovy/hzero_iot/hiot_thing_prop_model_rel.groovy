package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_thing_prop_model_rel.groovy') {
    changeSet(author: "hzero-iot@hand-china.com", id: "2019-11-25-hiot_thing_prop_model_rel") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_thing_prop_model_rel_s', startValue:"1")
        }
        createTable(tableName: "hiot_thing_prop_model_rel", remarks: "设备模板与数据点模板或数据点组模板关系") {
            column(name: "MODEL_REL_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "THING_MODEL_ID", type: "bigint",  remarks: "设备模板ID, hiot_thing_model.THING_MODEL_ID")  {constraints(nullable:"false")}  
            column(name: "ITEM_TYPE", type: "tinyint",  remarks: "关联对象类型, 1-数据点组模板, 0-数据点模板")  {constraints(nullable:"false")}  
            column(name: "ITEM_ID", type: "bigint",  remarks: "ITEM_TYPE=1,ITEM_ID=数据点组模板ID, hiot_property_group_model.GROUP_MODEL_ID; ITEM_TYPE=0,ITEM_ID=数据点模板ID, hiot_property_model.PROPERTY_MODEL_ID")  {constraints(nullable:"false")}  
            column(name: "ORGANIZATION_ID", type: "bigint",   defaultValue:"-1",   remarks: "组织ID, 模板关系不隔离到组织, 使用默认值即可")  {constraints(nullable:"false")}  
            column(name: "TENANT_ID", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"THING_MODEL_ID,ITEM_TYPE,ITEM_ID",tableName:"hiot_thing_prop_model_rel",constraintName: "hiot_thing_prop_model_rel_u1")
    }
}