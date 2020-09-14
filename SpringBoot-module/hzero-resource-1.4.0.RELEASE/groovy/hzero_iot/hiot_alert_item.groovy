package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_alert_item.groovy') {
    changeSet(author: "hzero-iot@hand-china.com", id: "2019-11-25-hiot_alert_item") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_alert_item_s', startValue:"1")
        }
        createTable(tableName: "hiot_alert_item", remarks: "告警事件关联项") {
            column(name: "ALERT_ITEM_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "THING_ID", type: "bigint",  remarks: "设备ID, hiot_thing.THING_ID")  {constraints(nullable:"false")}  
            column(name: "ALERT_MODEL_ID", type: "bigint",  remarks: "告警模板ID, hiot_alert_model.ALERT_MODEL_ID")  {constraints(nullable:"false")}  
            column(name: "ALERT_ID", type: "bigint",  remarks: "告警事件ID, hiot_alert.ALERT_ID")   
            column(name: "ITEM_TYPE", type: "tinyint",  remarks: "预警规则的项类型, 0-数据点, 1-监测指标(后续版本扩展)")  {constraints(nullable:"false")}  
            column(name: "ITEM_ID", type: "bigint",  remarks: "ITEM_TYPE=0,ITEM_ID=数据点ID, hiot_property.PROPERTY_ID; ITEM_TYPE=1,ITEM_ID=监测指标ID")  {constraints(nullable:"false")}  
            column(name: "ITEM_VALUE", type: "varchar(" + 30 * weight + ")",  remarks: "触发告警当时值")  {constraints(nullable:"false")}  
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "组织ID, 告警事件关联项隔离到组织, 需要设置值")  {constraints(nullable:"false")}  
            column(name: "TENANT_ID", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

    }
}