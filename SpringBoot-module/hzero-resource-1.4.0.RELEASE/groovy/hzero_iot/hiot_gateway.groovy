package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_gateway.groovy') {
    changeSet(author: "hzero-iot@hand-china.com", id: "2019-11-25-hiot_gateway") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_gateway_s', startValue:"1")
        }
        createTable(tableName: "hiot_gateway", remarks: "网关") {
            column(name: "GATEWAY_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "GUID", type: "varchar(" + 30 * weight + ")",  remarks: "唯一标识, gw-随机码")  {constraints(nullable:"false")}  
            column(name: "GATEWAY_CODE", type: "varchar(" + 30 * weight + ")",  remarks: "编码")  {constraints(nullable:"false")}  
            column(name: "GATEWAY_NAME", type: "varchar(" + 45 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "CONNECTED", type: "tinyint",  remarks: "网关在线状态, 0-离线, 1-在线")  {constraints(nullable:"false")}  
            column(name: "STATUS", type: "varchar(" + 30 * weight + ")",  remarks: "网关状态, 取自快码 HIOT.THING_STATE，只有未注册，已注册两种状态")  {constraints(nullable:"false")}  
            column(name: "MODEL", type: "varchar(" + 100 * weight + ")",  remarks: "网关型号")   
            column(name: "DESCRIPTION", type: "varchar(" + 100 * weight + ")",  remarks: "说明")   
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "组织ID, 网关隔离到组织, 需要设置值")  {constraints(nullable:"false")}  
            column(name: "TENANT_ID", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"GUID",tableName:"hiot_gateway",constraintName: "hiot_gateway_u1")
        addUniqueConstraint(columnNames:"GATEWAY_CODE,TENANT_ID",tableName:"hiot_gateway",constraintName: "hiot_gateway_u2")
    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-16_hiot_gateway_projectId"){
        addColumn(tableName:"hiot_gateway"){
            column(name:"PROJECT_ID", type:"bigint", remarks: "项目ID"){
                constraints(nullable:"true")
            }
        }

    }
    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-16_hiot_gateway_version"){
        addColumn(tableName:"hiot_gateway"){
            column(name:"VERSION", type:"varchar(32)", remarks: "固件版本号"){
                constraints(nullable:"true")
            }
        }

    }
    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-16_hiot_gateway_thingModelId"){
        addColumn(tableName:"hiot_gateway"){
            column(name:"THING_MODEL_ID", type:"bigint", remarks: "设备模板主键，仅可以选择网关类的模板主键"){
                constraints(nullable:"true")
            }
        }

    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-20_hiot_gateway_fix"){
        sql {
            "UPDATE hiot_gateway SET VERSION = '0.0.1' WHERE VERSION IS NULL OR VERSION = '';"
        }

    }
}