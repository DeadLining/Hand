package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_project.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_project") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_project_s', startValue:"1")
        }
        createTable(tableName: "hiot_project", remarks: "项目信息") {
            column(name: "PROJECT_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "PROJECT_CODE", type: "varchar(" + 64 * weight + ")",  remarks: "项目编码")  {constraints(nullable:"false")}  
            column(name: "PROJECT_NAME", type: "varchar(" + 64 * weight + ")",  remarks: "项目名称")  {constraints(nullable:"false")}  
            column(name: "ENABLED_FLAG", type: "tinyint",   defaultValue:"1",   remarks: "是否启用。1启用，0未启用")  {constraints(nullable:"false")}  
            column(name: "ORDER_SEQ", type: "int",  remarks: "排序号")   
            column(name: "DESCRIPTION", type: "varchar(" + 255 * weight + ")",  remarks: "说明")   
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "所属组织ID")   
            column(name: "TENANT_ID", type: "bigint",   defaultValue:"0",   remarks: "租户id")   
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"TENANT_ID,PROJECT_CODE",tableName:"hiot_project",constraintName: "hiot_project_u1")
    }
}