package script.db

databaseChangeLog(logicalFilePath: 'script/db/hdtt_data_chk_batch_dtl.groovy') {
    changeSet(author: "jianbo.li@hand-china.com", id: "2019-08-05-hdtt_data_chk_batch_dtl") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hdtt_data_chk_batch_dtl_s', startValue:"1")
        }
        createTable(tableName: "hdtt_data_chk_batch_dtl", remarks: "数据核对批次明细") {
            column(name: "data_chk_dtl_id", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "data_chk_line_id", type: "bigint",  remarks: "批次行ID")  {constraints(nullable:"false")}  
            column(name: "data_chk_batch_id", type: "bigint",  remarks: "批次ID")  {constraints(nullable:"false")}  
            column(name: "data_id", type: "bigint",  remarks: "数据ID")  {constraints(nullable:"false")}  
            column(name: "source_line", type: "longtext",  remarks: "来源行JSON数据")  {constraints(nullable:"false")}  
            column(name: "target_line", type: "longtext",  remarks: "目标行JSON数据")   
            column(name: "diff_type", type: "varchar(" + 30 * weight + ")",  remarks: "差异类型，代码HDTT.DATA_DIFF_TYPE")   
            column(name: "diff_content", type: "longtext",  remarks: "差异内容")   
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"data_chk_line_id,data_chk_batch_id,data_id",tableName:"hdtt_data_chk_batch_dtl",constraintName: "hdtt_data_chk_batch_dtl_u1")
    }
}