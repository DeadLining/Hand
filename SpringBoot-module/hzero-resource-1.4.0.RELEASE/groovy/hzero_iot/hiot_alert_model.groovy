package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_alert_model.groovy') {
    changeSet(author: "hzero-iot@hand-china.com", id: "2019-11-25-hiot_alert_model") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_alert_model_s', startValue:"1")
        }
        createTable(tableName: "hiot_alert_model", remarks: "告警模板") {
            column(name: "ALERT_MODEL_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "ALERT_MODEL_CODE", type: "varchar(" + 30 * weight + ")",  remarks: "编码")  {constraints(nullable:"false")}  
            column(name: "ALERT_MODEL_NAME", type: "varchar(" + 45 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "ALERT_LEVEL", type: "varchar(" + 30 * weight + ")",  remarks: "告警级别, 取自快码 HIOT.ALERT_LEVEL")  {constraints(nullable:"false")}  
            column(name: "DUMMY_TIME", type: "bigint",  remarks: "哑火时间, 单位:秒")   
            column(name: "ENABLED", type: "tinyint",   defaultValue:"1",   remarks: "启用, 1-启用, 0-禁用")  {constraints(nullable:"false")}  
            column(name: "MESSAGE_TEMPLATE_ID", type: "bigint",  remarks: "通知模板ID, hzero_message.hmsg_message_template.template_id")  {constraints(nullable:"false")}  
            column(name: "RECEIVER", type: "mediumtext",  remarks: "接收人员, 系统用户ID, 用分号分隔")   
            column(name: "DESCRIPTION", type: "varchar(" + 100 * weight + ")",  remarks: "规则说明")   
            column(name: "ORGANIZATION_ID", type: "bigint",   defaultValue:"-1",   remarks: "组织ID, 告警模板不隔离到组织, 使用默认值即可")  {constraints(nullable:"false")}  
            column(name: "TENANT_ID", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"ALERT_MODEL_CODE,TENANT_ID",tableName:"hiot_alert_model",constraintName: "hiot_alert_model_u1")
    }
}