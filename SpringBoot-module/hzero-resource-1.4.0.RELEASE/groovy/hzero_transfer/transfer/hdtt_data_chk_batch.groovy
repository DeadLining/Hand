package script.db

databaseChangeLog(logicalFilePath: 'script/db/hdtt_data_chk_batch.groovy') {
    changeSet(author: "jianbo.li@hand-china.com", id: "2019-08-05-hdtt_data_chk_batch") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hdtt_data_chk_batch_s', startValue:"1")
        }
        createTable(tableName: "hdtt_data_chk_batch", remarks: "数据核对批次") {
            column(name: "data_chk_batch_id", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "batch_num", type: "varchar(" + 128 * weight + ")",  remarks: "批次编号，YYYYMMDDHHMMSS")  {constraints(nullable:"false")}  
            column(name: "chk_level", type: "varchar(" + 30 * weight + ")",  remarks: "核对层级，代码HDTT.DATA_CHK_LEVEL")  {constraints(nullable:"false")}  
            column(name: "remark", type: "longtext",  remarks: "备注说明")   
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"batch_num",tableName:"hdtt_data_chk_batch",constraintName: "hdtt_data_chk_batch_u1")
    }
}