package script.db

databaseChangeLog(logicalFilePath: 'script/db/hdtt_db_migration_log.groovy') {
    changeSet(author: "jianbo.li@hand-china.com", id: "2019-07-18-hdtt_db_migration_log") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hdtt_db_migration_log_s', startValue:"1")
        }
        createTable(tableName: "hdtt_db_migration_log", remarks: "表结构变更日志") {
            column(name: "db_migration_log_id", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "source_service", type: "varchar(" + 30 * weight + ")",  remarks: "服务名")  {constraints(nullable:"false")}  
            column(name: "source_ds_id", type: "bigint",  remarks: "数据源ID")   
            column(name: "source_ds", type: "varchar(" + 30 * weight + ")",  remarks: "数据源编码")   
            column(name: "source_db", type: "varchar(" + 150 * weight + ")",  remarks: "DB编码")   
            column(name: "source_table", type: "varchar(" + 30 * weight + ")",  remarks: "处理表，也是消息TOPIC")  {constraints(nullable:"false")}  
            column(name: "target_service", type: "varchar(" + 30 * weight + ")",  remarks: "目标服务名")   
            column(name: "target_ds_id", type: "bigint",  remarks: "数据源ID")   
            column(name: "target_ds", type: "varchar(" + 30 * weight + ")",  remarks: "数据源编码")   
            column(name: "target_db", type: "varchar(" + 150 * weight + ")",  remarks: "DB编码")   
            column(name: "target_table", type: "varchar(" + 30 * weight + ")",  remarks: "消费对应的表，默认跟消息生产表一样")   
            column(name: "messages", type: "longtext",  remarks: "消息内容")   
            column(name: "process_status", type: "varchar(" + 3 * weight + ")",   defaultValue:"P",   remarks: "处理状态，参考：HDTT.EVENT_PROCESS_STATUS")  {constraints(nullable:"false")}  
            column(name: "process_msg", type: "longtext",  remarks: "处理消息")   
            column(name: "process_time", type: "datetime",  remarks: "处理时间")   
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }
   createIndex(tableName: "hdtt_db_migration_log", indexName: "hdtt_db_migration_log_n1") {
            column(name: "source_service")
            column(name: "source_ds")
            column(name: "source_db")
            column(name: "source_table")
        }

    }
}