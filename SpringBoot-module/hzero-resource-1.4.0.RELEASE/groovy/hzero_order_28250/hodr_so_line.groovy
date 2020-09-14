package script.db

databaseChangeLog(logicalFilePath: 'script/db/hodr_so_line.groovy') {
    changeSet(author: "shengzhou.kong@hand-china.com", id: "2020-07-27-hodr_so_line") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hodr_so_line_s', startValue:"1")
        }
        createTable(tableName: "hodr_so_line", remarks: "销售订单行信息") {
            column(name: "so_line_id", type: "bigint",  remarks: "订单行ID(主键)")  {constraints(primaryKey: true)} 
            column(name: "so_header_id", type: "bigint",  remarks: "订单头ID")  {constraints(nullable:"false")}  
            column(name: "line_number", type: "int",  remarks: "行号")  {constraints(nullable:"false")}  
            column(name: "item_id", type: "bigint",  remarks: "产品ID")  {constraints(nullable:"false")}  
            column(name: "order_quantity", type: "decimal(20,6)",  remarks: "数量")  {constraints(nullable:"false")}  
            column(name: "order_quantity_uom", type: "varchar(" + 60 * weight + ")",  remarks: "产品单位")  {constraints(nullable:"false")}  
            column(name: "unit_selling_price", type: "decimal(20,10)",  remarks: "销售单价")  {constraints(nullable:"false")}  
            column(name: "description", type: "varchar(" + 240 * weight + ")",  remarks: "备注")   
            column(name: "addition1", type: "varchar(" + 150 * weight + ")",  remarks: "附加信息1")   
            column(name: "addition2", type: "varchar(" + 150 * weight + ")",  remarks: "附加信息2")   
            column(name: "addition3", type: "varchar(" + 150 * weight + ")",  remarks: "附加信息3")   
            column(name: "addition4", type: "varchar(" + 150 * weight + ")",  remarks: "附加信息4")   
            column(name: "addition5", type: "varchar(" + 150 * weight + ")",  remarks: "附加信息5")   
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "创建者")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "更新者")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "更新日期")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "版本号")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"so_header_id,line_number",tableName:"hodr_so_line",constraintName: "hodr_so_line_u1")
    }
}