package script.db

databaseChangeLog(logicalFilePath: 'script/db/hodr_item.groovy') {
    changeSet(author: "shengzhou.kong@hand-china.com", id: "2020-07-27-hodr_item") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hodr_item_s', startValue:"1")
        }
        createTable(tableName: "hodr_item", remarks: "物料主数据") {
            column(name: "item_id", type: "bigint",  remarks: "物料ID(主键)")  {constraints(primaryKey: true)} 
            column(name: "item_code", type: "varchar(" + 60 * weight + ")",  remarks: "物料编码")  {constraints(nullable:"false")}  
            column(name: "item_uom", type: "varchar(" + 60 * weight + ")",  remarks: "物料单位")  {constraints(nullable:"false")}  
            column(name: "item_description", type: "varchar(" + 240 * weight + ")",  remarks: "物料描述")  {constraints(nullable:"false")}  
            column(name: "saleable_flag", type: "tinyint",   defaultValue:"1",   remarks: "可销售标识")  {constraints(nullable:"false")}  
            column(name: "start_active_date", type: "date",  remarks: "生效起始时间")   
            column(name: "end_active_date", type: "date",  remarks: "生效结束时间")   
            column(name: "enabled_flag", type: "tinyint",   defaultValue:"1",   remarks: "启用标识")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "创建者")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "创建日期")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "更新者")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "更新日期")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "版本号")  {constraints(nullable:"false")}  

        }
        createIndex(tableName: "hodr_item", indexName: "hodr_item_n1") {
            column(name: "item_description")
        }

        addUniqueConstraint(columnNames:"item_code",tableName:"hodr_item",constraintName: "hodr_item_u1")
    }
}