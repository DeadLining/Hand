package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_cloud_config_tl.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_cloud_config_tl") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        createTable(tableName: "hiot_cloud_config_tl", remarks: "云配置信息语言表") {
            column(name: "CONFIG_ID", type: "bigint",  remarks: "表ID，主键")  {constraints(nullable:"false")}
            column(name: "LANG", type: "varchar(" + 20 * weight + ")",  remarks: "语言")  {constraints(nullable:"false")}  
            column(name: "CONFIG_NAME", type: "varchar(" + 20 * weight + ")",  remarks: "配置文件名称，识别配置唯一标志")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"CONFIG_ID,LANG",tableName:"hiot_cloud_config_tl",constraintName: "hiot_cloud_config_tl_u1")
    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-20_hiot_cloud_config_tl_fix"){
        sql {
            "INSERT INTO hiot_cloud_config_tl (  " +
                    "  config_id, " +
                    "  lang, " +
                    "  config_name " +
                    ") SELECT " +
                    "  hcc.CONFIG_ID, " +
                    "  'zh_CN', " +
                    "  hcc.CONFIG_NAME " +
                    "FROM " +
                    "  hiot_cloud_config hcc " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_cloud_config_tl hccl " +
                    "  WHERE " +
                    "    hccl.CONFIG_ID = hcc.CONFIG_ID " +
                    ") " +
                    "UNION " +
                    "   SELECT " +
                    "  hcc.CONFIG_ID, " +
                    "  'ja_JP', " +
                    "  hcc.CONFIG_NAME " +
                    "FROM " +
                    "  hiot_cloud_config hcc " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_cloud_config_tl hccl " +
                    "  WHERE " +
                    "    hccl.CONFIG_ID = hcc.CONFIG_ID " +
                    ") " +
                    "  UNION " +
                    " SELECT " +
                    "  hcc.CONFIG_ID, " +
                    "  'en_US', " +
                    "  hcc.CONFIG_NAME " +
                    "FROM " +
                    "  hiot_cloud_config hcc " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_cloud_config_tl hccl " +
                    "  WHERE " +
                    "    hccl.CONFIG_ID = hcc.CONFIG_ID " +
                    ");"
        }

    }
}