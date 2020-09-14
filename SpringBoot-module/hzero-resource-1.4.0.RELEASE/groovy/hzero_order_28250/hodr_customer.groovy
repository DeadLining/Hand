package script.db

databaseChangeLog(logicalFilePath: 'script/db/hodr_customer.groovy') {
    changeSet(author: "shengzhou.kong@hand-china.com", id: "2020-07-27-hodr_customer") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hodr_customer_s', startValue:"1")
        }
        createTable(tableName: "hodr_customer", remarks: "客户主数据") {
            column(name: "customer_id", type: "bigint",  remarks: "客户ID(主键)")  {constraints(primaryKey: true)} 
            column(name: "customer_number", type: "varchar(" + 60 * weight + ")",  remarks: "客户编号")  {constraints(nullable:"false")}  
            column(name: "customer_name", type: "varchar(" + 240 * weight + ")",  remarks: "客户名称")  {constraints(nullable:"false")}  
            column(name: "company_id", type: "bigint",  remarks: "公司ID")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint",   defaultValue:"1",   remarks: "启用标识")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "创建者")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "更新者")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "更新日期")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "版本号")  {constraints(nullable:"false")}  

        }
        createIndex(tableName: "hodr_customer", indexName: "hodr_customer_n1") {
            column(name: "customer_number")
            column(name: "customer_name")
        }

        addUniqueConstraint(columnNames:"company_id,customer_number",tableName:"hodr_customer",constraintName: "hodr_customer_u1")
    }
}