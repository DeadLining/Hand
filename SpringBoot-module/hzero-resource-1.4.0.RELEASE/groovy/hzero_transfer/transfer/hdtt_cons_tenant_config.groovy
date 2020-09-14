package script.db

databaseChangeLog(logicalFilePath: 'script/db/hdtt_cons_tenant_config.groovy') {
    changeSet(author: "xingxing.wu@hand-china.com", id: "2019-05-13-hdtt_cons_tenant_config") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hdtt_cons_tenant_config_s', startValue:"1")
        }
        createTable(tableName: "hdtt_cons_tenant_config", remarks: "数据消息消费租户配置") {
            column(name: "cons_tenant_config_id", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "cons_db_config_id", type: "bigint",  remarks: "DB配置ID")  {constraints(nullable:"false")}  
            column(name: "producer_config_id", type: "bigint",  remarks: "消息生产配置ID,hdtt_producer_config.producer_config_id")  {constraints(nullable:"false")}  
            column(name: "tenant_id", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "consumer_offset", type: "bigint",  remarks: "消费消息偏移起始数")  {constraints(nullable:"false")}  
            column(name: "process_time", type: "datetime",  remarks: "处理时间")   
            column(name: "process_status", type: "varchar(" + 30 * weight + ")",  remarks: "处理状态，参考：HDTT.EVENT_PROCESS_STATUS")  {constraints(nullable:"false")}  
            column(name: "process_msg", type: "varchar(" + 600 * weight + ")",  remarks: "初始化处理消息")   
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"cons_db_config_id,tenant_id",tableName:"hdtt_cons_tenant_config",constraintName: "hdtt_cons_tenant_config_u1")
    }

    changeSet(author: "jianbo.li@han-china.com",id:"2019-07-16-hdtt_cons_tenant_config"){
        if(helper.isOracle()){
			addColumn(tableName:"hdtt_cons_tenant_config"){
				column(name: "process_msg_dump", type: "longtext",  remarks: "初始化处理消息")   
			}
			update(tableName:"hdtt_cons_tenant_config"){
				column(name:"process_msg_dump",type:"longtext",valueComputed:"to_char(process_msg)")
			}
			dropColumn(tableName:"hdtt_cons_tenant_config",columnName:"process_msg")
			renameColumn(oldColumnName:"process_msg_dump",newColumnName:"process_msg",columnDataType:"longtext",tableName:"hdtt_cons_tenant_config")
		}else{
		    modifyDataType(tableName: "hdtt_cons_tenant_config",columnName:"process_msg",newDataType:"longtext")
		}
    }
}