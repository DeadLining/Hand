package script.db

databaseChangeLog(logicalFilePath: 'script/db/hdtt_producer_config.groovy') {
    changeSet(author: "xingxing.wu@hand-china.com", id: "2019-05-13-hdtt_producer_config") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hdtt_producer_config_s', startValue:"1")
        }
        createTable(tableName: "hdtt_producer_config", remarks: "数据消息生产配置") {
            column(name: "producer_config_id", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)}
            column(name: "service_code", type: "varchar(" + 30 * weight + ")",  remarks: "服务编码")  {constraints(nullable:"false")}
            column(name: "table_name", type: "varchar(" + 30 * weight + ")",  remarks: "表名，作为消息topic")  {constraints(nullable:"false")}
            column(name: "tenant_flag", type: "tinyint",   defaultValue:"0",   remarks: "是否按租户分发。1是，0否")  {constraints(nullable:"false")}
            column(name: "init_ds_id", type: "bigint",  remarks: "初始化-数据源ID")  {constraints(nullable:"false")}
            column(name: "init_ds_code", type: "varchar(" + 30 * weight + ")",  remarks: "初始化-数据源编码")  {constraints(nullable:"false")}
            column(name: "init_db_code", type: "varchar(" + 150 * weight + ")",  remarks: "初始化-DB编码")  {constraints(nullable:"false")}
            column(name: "init_ddl_sql", type: "longtext",  remarks: "初始化-ddl语句")  {constraints(nullable:"false")}
            column(name: "init_temp_table", type: "varchar(" + 30 * weight + ")",  remarks: "初始化-导入临时表名，用于批量初始化数据到表")
            column(name: "init_import_data_flag", type: "tinyint",   defaultValue:"1",   remarks: "初始化-是否导入数据。1是，0否")  {constraints(nullable:"false")}
            column(name: "enabled_flag", type: "tinyint",   defaultValue:"1",   remarks: "是否启用。1启用，0未启用")  {constraints(nullable:"false")}
            column(name: "start_date", type: "date",  remarks: "有效期从")
            column(name: "end_date", type: "date",  remarks: "有效期至")
            column(name: "description", type: "varchar(" + 255 * weight + ")",  remarks: "说明")  {constraints(nullable:"false")}
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}

        }

        addUniqueConstraint(columnNames:"table_name,service_code",tableName:"hdtt_producer_config",constraintName: "hdtt_producer_config_u1")
    }

    changeSet(author: "xingxing.wu@hand-china.com",id: "2019-05-21-hdtt_producer_config"){
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        addColumn(tableName: 'hdtt_producer_config'){
            column(name: "topic_generated_time", type: "datetime",  remarks: "Topic创建时间")
        }
		addColumn(tableName: 'hdtt_producer_config'){
            column(name: "topic_generated_status", type: "varchar(" + 30 * weight + ")",   defaultValue:"P",   remarks: "Topic状态，参考：HDTT.TOPIC_GEN_STATUS")  {constraints(nullable:"false")}
        }
		addColumn(tableName: 'hdtt_producer_config'){
            column(name: "topic_generated_msg", type: "varchar(" + 600 * weight + ")",  remarks: "Topic创建消息")
        }
    }

    changeSet(author: "xingxing.wu@hand-china.com",id: "2019-06-20-hdtt_producer_config"){
        dropColumn(tableName: "hdtt_producer_config",columnName:"init_temp_table" )
        dropColumn(tableName: "hdtt_producer_config",columnName:"start_date" )
        dropColumn(tableName: "hdtt_producer_config",columnName:"end_date" )
    }
}