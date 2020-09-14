package script.db

databaseChangeLog(logicalFilePath: 'script/db/hdtt_distribution_id.groovy') {
    changeSet(author: "xingxing.wu@hand-china.com", id: "2019-05-13-hdtt_distribution_id") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'hdtt_distribution_id_s', startValue:"1")
        }
        createTable(tableName: "hdtt_distribution_id", remarks: "分布式ID最大值信息") {
            column(name: "distribution_id", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键")  {constraints(primaryKey: true)} 
            column(name: "biz_type", type: "varchar(" + 30 * weight + ")",  remarks: "业务类型")  {constraints(nullable:"false")}  
            column(name: "max_value_available", type: "bigint",   defaultValue:"1000",   remarks: "最大可用值")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"biz_type",tableName:"hdtt_distribution_id",constraintName: "hdtt_distribution_id_u1")
    }
}