package script.db

databaseChangeLog(logicalFilePath: 'script/db/hodr_company.groovy') {
    changeSet(author: "shengzhou.kong@hand-china.com", id: "2020-07-27-hodr_company") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hodr_company_s', startValue:"1")
        }
        createTable(tableName: "hodr_company", remarks: "公司主数据") {
            column(name: "company_id", type: "bigint",  remarks: "公司ID(主键)")  {constraints(primaryKey: true)} 
            column(name: "company_number", type: "varchar(" + 60 * weight + ")",  remarks: "公司编号")  {constraints(nullable:"false")}  
            column(name: "company_name", type: "varchar(" + 240 * weight + ")",  remarks: "公司名称")  {constraints(nullable:"false")}  
            column(name: "enabled_flag", type: "tinyint",   defaultValue:"1",   remarks: "启用标识")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "创建者")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "更新者")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "更新日期")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "版本号")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"company_number",tableName:"hodr_company",constraintName: "hodr_company_u1")
        addUniqueConstraint(columnNames:"company_name",tableName:"hodr_company",constraintName: "hodr_company_u2")
    }
}