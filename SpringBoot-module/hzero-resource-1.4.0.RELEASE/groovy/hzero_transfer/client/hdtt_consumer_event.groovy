package script.db

databaseChangeLog(logicalFilePath: 'script/db/hdtt_consumer_event.groovy') {
    changeSet(author: "xingxing.wu@hand-china.com", id: "2019-05-08-hdtt_consumer_event") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hdtt_consumer_event_s', startValue:"1")
        }
        createTable(tableName: "hdtt_consumer_event", remarks: "消费事件") {
            column(name: "event_id", type: "bigint",  remarks: "表ID，主键。全局ID")  {constraints(nullable:"false")}  
            column(name: "event_type", type: "varchar(" + 30 * weight + ")",  remarks: "事件类型")  {constraints(nullable:"false")}  
            column(name: "source_service", type: "varchar(" + 30 * weight + ")",  remarks: "服务名")  {constraints(nullable:"false")}  
            column(name: "source_table", type: "varchar(" + 30 * weight + ")",  remarks: "处理表，也是消息TOPIC")  {constraints(nullable:"false")}  
            column(name: "source_key_id", type: "bigint",  remarks: "处理数据ID")  {constraints(nullable:"false")}  
            column(name: "source_tenant_id", type: "bigint",  remarks: "处理数据租户ID")   
            column(name: "produce_time", type: "datetime",  remarks: "消息产生时间")  {constraints(nullable:"false")}  
            column(name: "messages", type: "longtext",  remarks: "消息内容")   
            column(name: "process_status", type: "varchar(" + 3 * weight + ")",  remarks: "处理状态，参考：HDTT.EVENT_PROCESS_STATUS")  {constraints(nullable:"false")}  
            column(name: "process_msg", type: "longtext",  remarks: "处理消息")   
            column(name: "process_time", type: "datetime",  remarks: "处理时间")   
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"event_id,source_service,source_table,source_key_id,process_status",tableName:"hdtt_consumer_event",constraintName: "hdtt_consumer_event_u1")
    }
    changeSet(author: "jianbo.li@hand-china.com",id: "2019-05-16-hdtt_consumer_event-changeIndex"){
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        addColumn(tableName: "hdtt_consumer_event"){
            column(name: "target_service",type: "varchar("+30*weight+")",remarks: "目标服务");
        }
		addColumn(tableName: "hdtt_consumer_event"){
            column(name: "target_ds_id",type: "bigint",remarks: "数据源ID");
        }
		addColumn(tableName: "hdtt_consumer_event"){
            column(name: "target_ds",type: "varchar("+30*weight+")",remarks: "数据源");
        }
		addColumn(tableName: "hdtt_consumer_event"){
            column(name: "target_db",type: "varchar("+150*weight+")",remarks: "DB编码");
        }
		addColumn(tableName: "hdtt_consumer_event"){
            column(name: "target_table",type: "varchar("+30*weight+")",remarks: "消费对应的表，默认跟消息生产表一样");
        }
        dropUniqueConstraint(constraintName: "hdtt_consumer_event_u1",tableName: "hdtt_consumer_event")
        addUniqueConstraint(columnNames:"event_id,source_key_id,target_db,target_table,process_status",tableName:"hdtt_consumer_event",constraintName: "hdtt_consumer_event_u1")
        createIndex(tableName: "hdtt_consumer_event",indexName:"hdtt_consumer_event_n1"){
            column(name:  "event_id")
            column(name:  "source_service")
            column(name:  "source_table")
            column(name:  "source_key_id")
            column(name:  "source_tenant_id")
        }
    }
}