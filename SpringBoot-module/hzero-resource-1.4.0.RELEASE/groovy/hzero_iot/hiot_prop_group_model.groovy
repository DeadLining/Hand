package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_prop_group_model.groovy') {
    changeSet(author: "hzero-iot@hand-china.com", id: "2019-11-25-hiot_prop_group_model") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_prop_group_model_s', startValue:"1")
        }
        createTable(tableName: "hiot_prop_group_model", remarks: "数据点组模版") {
            column(name: "GROUP_MODEL_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "GROUP_MODEL_CODE", type: "varchar(" + 30 * weight + ")",  remarks: "编码")  {constraints(nullable:"false")}  
            column(name: "GROUP_MODEL_NAME", type: "varchar(" + 45 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "ENABLED", type: "tinyint",   defaultValue:"1",   remarks: "启用, 1-启用, 0-禁用")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "varchar(" + 100 * weight + ")",  remarks: "说明")   
            column(name: "ORGANIZATION_ID", type: "bigint",   defaultValue:"-1",   remarks: "组织ID, 数据点组模版不隔离到组织, 使用默认值即可")  {constraints(nullable:"false")}  
            column(name: "TENANT_ID", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"GROUP_MODEL_CODE,TENANT_ID",tableName:"hiot_prop_group_model",constraintName: "hiot_prop_group_model_u1")
    }
}