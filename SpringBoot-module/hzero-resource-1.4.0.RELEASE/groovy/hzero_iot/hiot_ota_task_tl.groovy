package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_ota_task_tl.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_ota_task_tl") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        createTable(tableName: "hiot_ota_task_tl", remarks: "OTA升级任务语言表") {
            column(name: "TASK_ID", type: "bigint",  remarks: "表ID，主键")  {constraints(nullable:"false")}  
            column(name: "LANG", type: "varchar(" + 20 * weight + ")",  remarks: "语言")  {constraints(nullable:"false")}  
            column(name: "TASK_NAME", type: "varchar(" + 64 * weight + ")",  remarks: "任务名称")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "varchar(" + 255 * weight + ")",  remarks: "说明")   

        }

        addUniqueConstraint(columnNames:"TASK_ID,LANG",tableName:"hiot_ota_task_tl",constraintName: "hiot_ota_task_tl_u1")
    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-20_hiot_ota_task_tl_fix"){
        sql {
            "INSERT INTO hiot_ota_task_tl ( " +
                    "  task_id, " +
                    "  lang, " +
                    "  task_name, " +
                    "  description " +
                    ") SELECT " +
                    "  hot.TASK_ID, " +
                    "  'zh_CN', " +
                    "  hot.TASK_NAME, " +
                    "  hot.DESCRIPTION " +
                    "FROM " +
                    "  hiot_ota_task hot " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_ota_task_tl hotl " +
                    "  WHERE " +
                    "    hotl.task_id = hot.task_id " +
                    ") " +
                    "UNION " +
                    "  SELECT " +
                    "  hot.TASK_ID, " +
                    "  'ja_JP', " +
                    "  hot.TASK_NAME, " +
                    "  hot.DESCRIPTION " +
                    "FROM " +
                    "  hiot_ota_task hot " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_ota_task_tl hotl " +
                    "  WHERE " +
                    "    hotl.task_id = hot.task_id " +
                    ") " +
                    "  UNION " +
                    " SELECT " +
                    "  hot.TASK_ID, " +
                    "  'en_US', " +
                    "  hot.TASK_NAME, " +
                    "  hot.DESCRIPTION " +
                    "FROM " +
                    "  hiot_ota_task hot " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_ota_task_tl hotl " +
                    "  WHERE " +
                    "    hotl.task_id = hot.task_id " +
                    ");"
        }

    }
}