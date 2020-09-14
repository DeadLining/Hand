package script.db

databaseChangeLog(logicalFilePath: 'script/db/hdtt_cons_db_config.groovy') {
    changeSet(author: "xingxing.wu@hand-china.com", id: "2019-05-13-hdtt_cons_db_config") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hdtt_cons_db_config_4', startValue:"1")
        }
        createTable(tableName: "hdtt_cons_db_config", remarks: "数据消息消费DB配置") {
            column(name: "cons_db_config_id", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "producer_config_id", type: "bigint",  remarks: "消息生产配置，获取topic")  {constraints(nullable:"false")}  
            column(name: "consumer_table", type: "varchar(" + 30 * weight + ")",  remarks: "消费对应的表，默认跟消息生产表一样")  {constraints(nullable:"false")}  
            column(name: "consumer_service", type: "varchar(" + 128 * weight + ")",  remarks: "消费服务")  {constraints(nullable:"false")}  
            column(name: "consumer_ds_id", type: "bigint",  remarks: "数据源ID")  {constraints(nullable:"false")}  
            column(name: "consumer_ds", type: "varchar(" + 30 * weight + ")",  remarks: "数据源编码")  {constraints(nullable:"false")}  
            column(name: "consumer_db", type: "varchar(" + 150 * weight + ")",  remarks: "DB编码")  {constraints(nullable:"false")}  
            column(name: "consumer_offset", type: "bigint",  remarks: "消费消息偏移起始数")  {constraints(nullable:"false")}  
            column(name: "process_time", type: "datetime",  remarks: "处理时间")   
            column(name: "process_status", type: "varchar(" + 30 * weight + ")",  remarks: "处理状态，参考：HDTT.EVENT_PROCESS_STATUS")  {constraints(nullable:"false")}  
            column(name: "process_msg", type: "varchar(" + 600 * weight + ")",  remarks: "初始化处理消息")   
            column(name: "enabled_flag", type: "tinyint",   defaultValue:"1",   remarks: "是否启用。1启用，0未启用")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"consumer_table,consumer_service,consumer_ds,consumer_db",tableName:"hdtt_cons_db_config",constraintName: "hdtt_cons_db_config_u1")
    }

    changeSet(author: "jianbo.li@han-china.com",id:"2019-07-16-hdtt_cons_db_config"){
		if(helper.isOracle()){
			addColumn(tableName:"hdtt_cons_db_config"){
				column(name: "process_msg_dump", type: "longtext",  remarks: "初始化处理消息")   
			}
			update(tableName:"hdtt_cons_db_config"){
				column(name:"process_msg_dump",type:"longtext",valueComputed:"to_char(process_msg)")
			}
			dropColumn(tableName:"hdtt_cons_db_config",columnName:"process_msg")
			renameColumn(oldColumnName:"process_msg_dump",newColumnName:"process_msg",columnDataType:"longtext",tableName:"hdtt_cons_db_config")
		}else{
		    modifyDataType(tableName: "hdtt_cons_db_config",columnName:"process_msg",newDataType:"longtext")
		}
    }
}