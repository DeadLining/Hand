package script.db

databaseChangeLog(logicalFilePath: 'script/db/hodr_so_header.groovy') {
    changeSet(author: "shengzhou.kong@hand-china.com", id: "2020-07-27-hodr_so_header") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hodr_so_header_s', startValue:"1")
        }
        createTable(tableName: "hodr_so_header", remarks: "销售订单头信息") {
            column(name: "so_header_id", type: "bigint",  remarks: "订单头ID(主键)")  {constraints(primaryKey: true)} 
            column(name: "order_number", type: "varchar(" + 60 * weight + ")",  remarks: "订单编号")  {constraints(nullable:"false")}  
            column(name: "company_id", type: "bigint",  remarks: "公司ID")  {constraints(nullable:"false")}  
            column(name: "order_date", type: "date",  remarks: "订单日期")  {constraints(nullable:"false")}  
            column(name: "order_status", type: "varchar(" + 30 * weight + ")",   defaultValue:"NEW",   remarks: "订单状态")  {constraints(nullable:"false")}  
            column(name: "customer_id", type: "bigint",  remarks: "客户ID")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "创建者")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "更新者")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "更新日期")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "版本号")  {constraints(nullable:"false")}  

        }
        createIndex(tableName: "hodr_so_header", indexName: "hodr_so_header_n1") {
            column(name: "company_id")
            column(name: "order_status")
        }

        addUniqueConstraint(columnNames:"company_id,order_number",tableName:"hodr_so_header",constraintName: "hodr_so_header_u1")
    }
}