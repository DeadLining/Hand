package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_thing.groovy') {
    changeSet(author: "hzero-iot@hand-china.com", id: "2019-11-25-hiot_thing") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_thing_s', startValue:"1")
        }
        createTable(tableName: "hiot_thing", remarks: "设备") {
            column(name: "THING_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "GUID", type: "varchar(" + 30 * weight + ")",  remarks: "唯一标识, t-随机码")  {constraints(nullable:"false")}  
            column(name: "THING_CODE", type: "varchar(" + 30 * weight + ")",  remarks: "编码")  {constraints(nullable:"false")}  
            column(name: "THING_NAME", type: "varchar(" + 45 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "THING_MODEL_ID", type: "bigint",  remarks: "设备模板ID, hiot_thing_model.THING_MODEL_ID")  {constraints(nullable:"false")}  
            column(name: "CONNECTED", type: "tinyint",  remarks: "设备在线状态, 0-离线, 1-在线")  {constraints(nullable:"false")}  
            column(name: "STATUS", type: "varchar(" + 30 * weight + ")",  remarks: "设备状态, 取自快码 HIOT.THING_STATUS")  {constraints(nullable:"false")}  
            column(name: "EQUIPMENT", type: "varchar(" + 100 * weight + ")",  remarks: "设备型号")   
            column(name: "MANUFACTURER", type: "varchar(" + 100 * weight + ")",  remarks: "厂家名称")   
            column(name: "BUYING_TIME", type: "datetime",  remarks: "购买时间")   
            column(name: "DESCRIPTION", type: "varchar(" + 100 * weight + ")",  remarks: "说明")   
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "组织ID, 设备需要隔离到组织, 需要设置值")  {constraints(nullable:"false")}  
            column(name: "TENANT_ID", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"GUID",tableName:"hiot_thing",constraintName: "hiot_thing_u1")
        addUniqueConstraint(columnNames:"THING_CODE,TENANT_ID",tableName:"hiot_thing",constraintName: "hiot_thing_u2")
    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-16_hiot_thing_projectId"){
        addColumn(tableName:"hiot_thing"){
            column(name:"PROJECT_ID", type:"bigint", remarks: "项目ID"){
                constraints(nullable:"true")
            }
        }

    }
    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-16_hiot_thing_version"){
        addColumn(tableName:"hiot_thing"){
            column(name:"VERSION", type:"varchar(32)", remarks: "设备固件版本号"){
                constraints(nullable:"true")
            }
        }

    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-20_hiot_thing_fix"){
        sql {
            "UPDATE hiot_thing SET VERSION = '0.0.1' WHERE VERSION IS NULL OR VERSION = '';"
        }

    }
}