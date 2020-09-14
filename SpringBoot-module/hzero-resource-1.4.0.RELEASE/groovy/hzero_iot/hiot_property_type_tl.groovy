package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_property_type_tl.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_property_type_tl") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        createTable(tableName: "hiot_property_type_tl", remarks: "数据点类型多语言表") {
            column(name: "TYPE_ID", type: "bigint",  remarks: "表ID，主键")  {constraints(nullable:"false")}  
            column(name: "LANG", type: "varchar(" + 20 * weight + ")",  remarks: "语言")  {constraints(nullable:"false")}  
            column(name: "TYPE_NAME", type: "varchar(" + 45 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "varchar(" + 255 * weight + ")",  remarks: "说明")   

        }

        addUniqueConstraint(columnNames:"TYPE_ID,LANG",tableName:"hiot_property_type_tl",constraintName: "hiot_property_type_tl_u1")
    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-20_hiot_property_type_tl_fix"){
        sql {
            "INSERT INTO hiot_property_type_tl ( " +
                    "  type_id, " +
                    "  lang, " +
                    "  type_name, " +
                    "  description " +
                    ") SELECT " +
                    "  pt.TYPE_ID, " +
                    "  'zh_CN', " +
                    "  pt.TYPE_NAME, " +
                    "  pt.DESCRIPTION " +
                    "FROM " +
                    "  hiot_property_type pt " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_property_type_tl ptt " +
                    "  WHERE " +
                    "    pt.TYPE_ID = ptt.TYPE_ID " +
                    ") " +
                    "UNION " +
                    "    SELECT " +
                    "  pt.TYPE_ID, " +
                    "  'ja_JP', " +
                    "  pt.TYPE_NAME, " +
                    "  pt.DESCRIPTION " +
                    "FROM " +
                    "  hiot_property_type pt " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_property_type_tl ptt " +
                    "  WHERE " +
                    "    pt.TYPE_ID = ptt.TYPE_ID " +
                    ") " +
                    "  UNION " +
                    "   SELECT " +
                    "  pt.TYPE_ID, " +
                    "  'en_US', " +
                    "  pt.TYPE_NAME, " +
                    "  pt.DESCRIPTION " +
                    "FROM " +
                    "  hiot_property_type pt " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_property_type_tl ptt " +
                    "  WHERE " +
                    "    pt.TYPE_ID = ptt.TYPE_ID " +
                    ");"
        }

    }
}