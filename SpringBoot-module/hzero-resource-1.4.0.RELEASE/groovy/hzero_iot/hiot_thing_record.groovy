package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_thing_record.groovy') {
    changeSet(author: "hzero-iot@hand-china.com", id: "2019-11-25-hiot_thing_record") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_thing_record_s', startValue:"1")
        }
        createTable(tableName: "hiot_thing_record", remarks: "设备事件记录") {
            column(name: "RECORD_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "THING_ID", type: "bigint",  remarks: "设备ID, hiot_thing.THING_ID")  {constraints(nullable:"false")}  
            column(name: "SRC_CONNECTED", type: "tinyint",  remarks: "设备原始在线状态, 0-离线, 1-在线")  {constraints(nullable:"false")}  
            column(name: "CONNECTED", type: "tinyint",  remarks: "设备当前在线状态, 0-离线, 1-在线")  {constraints(nullable:"false")}  
            column(name: "SRC_STATUS", type: "varchar(" + 30 * weight + ")",  remarks: "设备原始状态, 取自快码 HIOT.THING_STATUS")  {constraints(nullable:"false")}  
            column(name: "STATUS", type: "varchar(" + 30 * weight + ")",  remarks: "设备当前状态, 取自快码 HIOT.THING_STATUS")  {constraints(nullable:"false")}  
            column(name: "TS", type: "datetime",  remarks: "事件发生时间")  {constraints(nullable:"false")}  
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "组织ID, 设备事件记录隔离到组织, 需要设置值")  {constraints(nullable:"false")}  
            column(name: "TENANT_ID", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"THING_ID,TS",tableName:"hiot_thing_record",constraintName: "hiot_thing_record_u1")
    }
}