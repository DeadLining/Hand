package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_gw_edgink_parser.groovy') {
    changeSet(author: "hzero-iot@hand-china.com", id: "2019-11-25-hiot_gw_edgink_parser") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_gw_edgink_parser_s', startValue:"1")
        }
        createTable(tableName: "hiot_gw_edgink_parser", remarks: "Edgink子设备解析配置") {
            column(name: "PARSER_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "EDGINK_ID", type: "bigint",  remarks: "modbus子设备ID, hiot_gateway_modbus.MODBUS_ID")  {constraints(nullable:"false")}  
            column(name: "PROPERTY_ID", type: "bigint",  remarks: "数据点ID, hiot_property.PROPERTY_ID")  {constraints(nullable:"false")}  
            column(name: "REQUEST_INTERVAL", type: "int",  remarks: "请求间隔, 单位: 秒")  {constraints(nullable:"false")}  
            column(name: "EDGINK_ITEM", type: "varchar(" + 45 * weight + ")",  remarks: "Edgink对应采集项")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "varchar(" + 100 * weight + ")",  remarks: "说明")   
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "组织ID, Edgink设备解析配置隔离到组织, 需要设置值")  {constraints(nullable:"false")}  
            column(name: "TENANT_ID", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"EDGINK_ID,PROPERTY_ID",tableName:"hiot_gw_edgink_parser",constraintName: "hiot_gw_edgink_parser_u1")
    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-16_hiot_gw_edgink_parser_requestInterval"){
        dropNotNullConstraint(columnDataType:"int", columnName:"REQUEST_INTERVAL", tableName:"hiot_gw_edgink_parser")
    }
}