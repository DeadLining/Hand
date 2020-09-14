package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_predict_rule.groovy') {
    changeSet(author: "hzero-iot@hand-china.com", id: "2019-11-25-hiot_predict_rule") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_predict_rule_s', startValue:"1")
        }
        createTable(tableName: "hiot_predict_rule", remarks: "设备或设备模板预警规则") {
            column(name: "PREDICT_RULE_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "PREDICT_CODE", type: "varchar(" + 30 * weight + ")",  remarks: "编码")  {constraints(nullable:"false")}  
            column(name: "PREDICT_NAME", type: "varchar(" + 45 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "ITEM_TYPE", type: "tinyint",  remarks: "关联对象类型, 1-设备, 0-设备模板")  {constraints(nullable:"false")}  
            column(name: "ITEM_ID", type: "bigint",  remarks: "ITEM_TYPE=1,ITEM_ID=设备ID, hiot_thing.THING_ID;ITEM_TYPE=0,ITEM_ID=设备模板ID,iot_thing_model.MODLE_ID")  {constraints(nullable:"false")}  
            column(name: "ALERT_MODEL_ID", type: "bigint",  remarks: "告警模板ID, hiot_alert_model.ALERT_MODEL_ID")  {constraints(nullable:"false")}  
            column(name: "FORMULAR", type: "text",  remarks: "公式")  {constraints(nullable:"false")}  
            column(name: "FORMULAR_JSON", type: "text",  remarks: "公式json, 用于前端展示")  {constraints(nullable:"false")}  
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "组织ID, 设备预警规则隔离到组织, 需要设置值")  {constraints(nullable:"false")}  
            column(name: "TENANT_ID", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"PREDICT_CODE,TENANT_ID",tableName:"hiot_predict_rule",constraintName: "hiot_predict_rule_u1")
    }
}