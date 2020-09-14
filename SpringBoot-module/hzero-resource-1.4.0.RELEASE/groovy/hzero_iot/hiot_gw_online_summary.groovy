package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_gw_online_summary.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_gw_online_summary") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hiot_gw_online_summary_s', startValue:"1")
        }
        createTable(tableName: "hiot_gw_online_summary", remarks: "网关在线统计表") {
            column(name: "SUMMARY_ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "PROJECT_ID", type: "bigint",  remarks: "项目主键ID")  {constraints(nullable:"false")}  
            column(name: "SUMMARY_DATE", type: "datetime",  remarks: "统计时间")  {constraints(nullable:"false")}  
            column(name: "ONLINE_COUNT", type: "bigint",  remarks: "在线数量")   
            column(name: "REAL_COUNT", type: "bigint",  remarks: "实时数量")   
            column(name: "ORGANIZATION_ID", type: "bigint",  remarks: "所属组织ID")   
            column(name: "TENANT_ID", type: "bigint",   defaultValue:"0",   remarks: "租户id")   
            column(name: "OBJECT_VERSION_NUMBER", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"TENANT_ID,PROJECT_ID,SUMMARY_DATE",tableName:"hiot_gw_online_summary",constraintName: "hiot_gw_online_summary_u1")
    }
}