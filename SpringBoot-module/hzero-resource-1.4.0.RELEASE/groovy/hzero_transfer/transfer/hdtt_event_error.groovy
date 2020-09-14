package script.db

databaseChangeLog(logicalFilePath: 'script/db/hdtt_event_error.groovy') {
    changeSet(author: "xingxing.wu@hand-china.com", id: "2019-05-13-hdtt_event_error") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hdtt_event_error_s', startValue:"1")
        }
		
		if(helper.isOracle()){
			createTable(tableName: "hdtt_event_error", remarks: "事件错误监控") {
				column(name: "event_id", type: "bigint",  remarks: "表ID，主键。全局ID")  {constraints(nullable:"false")}  
				column(name: "event_type", type: "varchar(" + 30 * weight + ")",  remarks: "事件类型")  {constraints(nullable:"false")}  
				column(name: "source_service", type: "varchar(" + 30 * weight + ")",  remarks: "服务名")  {constraints(nullable:"false")}  
				column(name: "source_table", type: "varchar(" + 30 * weight + ")",  remarks: "处理表，也是消息TOPIC")  {constraints(nullable:"false")}  
				column(name: "source_key_id", type: "bigint",  remarks: "处理数据ID")   
				column(name: "source_tenant_id", type: "bigint",  remarks: "处理数据租户ID")   
				column(name: "target_service", type: "varchar(" + 30 * weight + ")",  remarks: "目标服务名")   
				column(name: "target_ds_id", type: "bigint",  remarks: "数据源ID")   
				column(name: "target_ds", type: "varchar(" + 30 * weight + ")",  remarks: "数据源编码")   
				column(name: "target_db", type: "varchar(" + 150 * weight + ")",  remarks: "DB编码")   
				column(name: "target_table", type: "varchar(" + 30 * weight + ")",  remarks: "消费对应的表，默认跟消息生产表一样")   
				column(name: "produce_time", type: "datetime",  remarks: "消息产生时间")  {constraints(nullable:"false")}  
				column(name: "messages", type: "longtext",  remarks: "消息内容")   
				column(name: "retry_times", type: "int",   defaultValue:"0",   remarks: "重试次数")  {constraints(nullable:"false")}  
				column(name: "error_type", type: "varchar(" + 30 * weight + ")",  remarks: "出错类型，参考HDTT.EVENT_ERR_TYPE")  {constraints(nullable:"false")}  
				column(name: "error_time", type: "datetime",  remarks: "出错时间")   
				column(name: "error_text", type: "longtext",  remarks: "错误消息")   
				column(name: "process_status", type: "varchar(" + 3 * weight + ")",   defaultValue:"P",   remarks: "处理状态，参考：HDTT.EVENT_PROCESS_STATUS")  {constraints(nullable:"false")}  
				column(name: "process_msg", type: "longtext",  remarks: "处理消息")   
				column(name: "process_time", type: "datetime",  remarks: "处理时间")   
				column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
				column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
				column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
				column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
				column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
				column(name: "event_error_id", type: "bigint", autoIncrement: true , remarks: "表ID，主键")  {constraints(primaryKey: true)}
			}
		}else{
			createTable(tableName: "hdtt_event_error", remarks: "事件错误监控") {
				column(name: "event_id", type: "bigint",  remarks: "表ID，主键。全局ID")  {constraints(nullable:"false")}  
				column(name: "event_type", type: "varchar(" + 30 * weight + ")",  remarks: "事件类型")  {constraints(nullable:"false")}  
				column(name: "source_service", type: "varchar(" + 30 * weight + ")",  remarks: "服务名")  {constraints(nullable:"false")}  
				column(name: "source_table", type: "varchar(" + 30 * weight + ")",  remarks: "处理表，也是消息TOPIC")  {constraints(nullable:"false")}  
				column(name: "source_key_id", type: "bigint",  remarks: "处理数据ID")   
				column(name: "source_tenant_id", type: "bigint",  remarks: "处理数据租户ID")   
				column(name: "target_service", type: "varchar(" + 30 * weight + ")",  remarks: "目标服务名")   
				column(name: "target_ds_id", type: "bigint",  remarks: "数据源ID")   
				column(name: "target_ds", type: "varchar(" + 30 * weight + ")",  remarks: "数据源编码")   
				column(name: "target_db", type: "varchar(" + 150 * weight + ")",  remarks: "DB编码")   
				column(name: "target_table", type: "varchar(" + 30 * weight + ")",  remarks: "消费对应的表，默认跟消息生产表一样")   
				column(name: "produce_time", type: "datetime",  remarks: "消息产生时间")  {constraints(nullable:"false")}  
				column(name: "messages", type: "longtext",  remarks: "消息内容")   
				column(name: "retry_times", type: "int",   defaultValue:"0",   remarks: "重试次数")  {constraints(nullable:"false")}  
				column(name: "error_type", type: "varchar(" + 30 * weight + ")",  remarks: "出错类型，参考HDTT.EVENT_ERR_TYPE")  {constraints(nullable:"false")}  
				column(name: "error_time", type: "datetime",  remarks: "出错时间")   
				column(name: "error_text", type: "longtext",  remarks: "错误消息")   
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
   createIndex(tableName: "hdtt_event_error", indexName: "hdtt_event_error_n1") {
            column(name: "event_id")
            column(name: "source_service")
            column(name: "target_service")
        }

    }
    changeSet(author: "jianbo.li@hand-china.com",id: "2019-07-03-hdtt_event_error"){
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
		if(!helper.isOracle()){
			addColumn(tableName: 'hdtt_event_error'){
				column(name: "event_error_id", type: "bigint", autoIncrement: true , remarks: "表ID，主键")  {constraints(primaryKey: true)}
			}
		}
    }
}