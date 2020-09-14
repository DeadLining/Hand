package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_monitor_index_tl.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_monitor_index_tl") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }

        createTable(tableName: "hiot_monitor_index_tl", remarks: "监测指标语言表") {
            column(name: "INDEX_ID", type: "bigint",  remarks: "表ID，主键")  {constraints(nullable:"false")}
            column(name: "LANG", type: "varchar(" + 20 * weight + ")",  remarks: "语言")  {constraints(nullable:"false")}  
            column(name: "INDEX_NAME", type: "varchar(" + 64 * weight + ")",  remarks: "监测指标名称")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"INDEX_ID,LANG",tableName:"hiot_monitor_index_tl",constraintName: "hiot_monitor_index_tl_u1")
    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-20_hiot_monitor_index_tl_fix"){
        sql {
            "INSERT INTO hiot_monitor_index_tl ( " +
                    "  index_id, " +
                    "  lang, " +
                    "  index_name " +
                    ") SELECT " +
                    "  hmi.INDEX_ID, " +
                    "  'zh_CN', " +
                    "  hmi.INDEX_NAME " +
                    "FROM " +
                    "  hiot_monitor_index hmi " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_monitor_index_tl hmit " +
                    "  WHERE " +
                    "    hmit.INDEX_ID = hmi.INDEX_ID " +
                    ") " +
                    "UNION " +
                    "  SELECT " +
                    "  hmi.INDEX_ID, " +
                    "  'ja_JP', " +
                    "  hmi.INDEX_NAME " +
                    "FROM " +
                    "  hiot_monitor_index hmi " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_monitor_index_tl hmit " +
                    "  WHERE " +
                    "    hmit.INDEX_ID = hmi.INDEX_ID " +
                    ") " +
                    "  UNION " +
                    "SELECT " +
                    "  hmi.INDEX_ID, " +
                    "  'en_US', " +
                    "  hmi.INDEX_NAME " +
                    "FROM " +
                    "  hiot_monitor_index hmi " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_monitor_index_tl hmit " +
                    "  WHERE " +
                    "    hmit.INDEX_ID = hmi.INDEX_ID " +
                    ");"
        }

    }
}