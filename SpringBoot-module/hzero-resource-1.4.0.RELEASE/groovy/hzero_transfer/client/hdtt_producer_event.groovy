package script.db

databaseChangeLog(logicalFilePath: 'script/db/hdtt_producer_event.groovy') {
    changeSet(author: "xingxing.wu@hand-china.com", id: "2019-05-08-hdtt_producer_event") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hdtt_producer_event_s', startValue:"1")
        }
        createTable(tableName: "hdtt_producer_event", remarks: "生产事件") {
            column(name: "event_id", type: "bigint",  remarks: "表ID，主键。全局ID")  {constraints(primaryKey: true)} 
            column(name: "event_type", type: "varchar(" + 30 * weight + ")",  remarks: "事件类型，参考：HDTT.EVENT_TYPE")  {constraints(nullable:"false")}  
            column(name: "source_service", type: "varchar(" + 30 * weight + ")",  remarks: "服务名")  {constraints(nullable:"false")}  
            column(name: "source_table", type: "varchar(" + 30 * weight + ")",  remarks: "处理表")  {constraints(nullable:"false")}  
            column(name: "source_tenant_id", type: "bigint",  remarks: "处理数据租户ID")   
            column(name: "produce_time", type: "datetime",  remarks: "消息产生时间")  {constraints(nullable:"false")}  
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

    }
}