package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_thing_tl.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_thing_tl") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        createTable(tableName: "hiot_thing_tl", remarks: "设备语言表") {
            column(name: "THING_ID", type: "bigint",  remarks: "表ID，主键")  {constraints(nullable:"false")}  
            column(name: "LANG", type: "varchar(" + 20 * weight + ")",  remarks: "语言")  {constraints(nullable:"false")}  
            column(name: "THING_NAME", type: "varchar(" + 45 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "varchar(" + 255 * weight + ")",  remarks: "说明")   

        }

        addUniqueConstraint(columnNames:"THING_ID,LANG",tableName:"hiot_thing_tl",constraintName: "hiot_thing_tl_u1")
    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-20_hiot_thing_tl_fix"){
        sql {
            "INSERT INTO hiot_thing_tl ( " +
                    "  thing_id, " +
                    "  lang, " +
                    "  thing_name, " +
                    "  description " +
                    ") SELECT " +
                    "  t.thing_id, " +
                    "  'zh_CN', " +
                    "  t.thing_name, " +
                    "  t.description " +
                    "FROM " +
                    "  hiot_thing t " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_thing_tl tt " +
                    "  WHERE " +
                    "    tt.thing_id = t.THING_ID " +
                    ") " +
                    "UNION " +
                    "  SELECT " +
                    "    t.thing_id, " +
                    "    'ja_JP', " +
                    "    t.thing_name, " +
                    "    t.description " +
                    "  FROM " +
                    "    hiot_thing t " +
                    "  WHERE " +
                    "    1 = 1 " +
                    "  AND NOT EXISTS ( " +
                    "    SELECT " +
                    "      1 " +
                    "    FROM " +
                    "      hiot_thing_tl tt " +
                    "    WHERE " +
                    "      tt.thing_id = t.THING_ID " +
                    "  ) " +
                    "  UNION " +
                    "    SELECT " +
                    "      t.thing_id, " +
                    "      'en_US', " +
                    "      t.thing_name, " +
                    "      t.description " +
                    "    FROM " +
                    "      hiot_thing t " +
                    "    WHERE " +
                    "      1 = 1 " +
                    "    AND NOT EXISTS ( " +
                    "      SELECT " +
                    "        1 " +
                    "      FROM " +
                    "        hiot_thing_tl tt " +
                    "      WHERE " +
                    "        tt.thing_id = t.THING_ID " +
                    "    );"
        }

    }
}