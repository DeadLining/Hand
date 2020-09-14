package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_alert.groovy') {
    changeSet(author: "hzero-iot@hand-china.com", id: "2019-11-25-hiot_alert") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_alert_s', startValue:"1")
        }
        createTable(tableName: "hiot_alert", remarks: "告警事件") {
            column(name: "ALERT_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "THING_ID", type: "bigint",  remarks: "设备ID, hiot_thing.THING_ID")  {constraints(nullable:"false")}  
            column(name: "ALERT_MODEL_ID", type: "bigint",  remarks: "告警模板ID, hiot_alert_model.ALERT_MODEL_ID")  {constraints(nullable:"false")}  
            column(name: "PREDICT_RULE_ID", type: "bigint",   defaultValue:"-1",   remarks: "预警规则ID,hiot_predict_rule.PREDICT_RULE_ID,如果不是预警规则触发的告警，则存默认值-1即可")  {constraints(nullable:"false")}  
            column(name: "RECOVERED", type: "tinyint",   defaultValue:"0",   remarks: "是否恢复, 0-未恢复, 1-已恢复")   
            column(name: "TS", type: "datetime",  remarks: "告警时间")  {constraints(nullable:"false")}  
            column(name: "RECOVERED_TS", type: "datetime",  remarks: "告警恢复时间")   
            column(name: "MESSAGE", type: "mediumtext",  remarks: "告警事件发送的通知内容")   
            column(name: "RECEIVER", type: "mediumtext",  remarks: "告警事件通知的接收人员, 系统用户ID, 用分号分隔")   
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "组织ID, 告警事件隔离到组织, 需要设置值")  {constraints(nullable:"false")}  
            column(name: "TENANT_ID", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }
}