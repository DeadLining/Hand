package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_thing_model_tl.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_thing_model_tl") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        createTable(tableName: "hiot_thing_model_tl", remarks: "设备模板语言表") {
            column(name: "THING_MODEL_ID", type: "bigint",  remarks: "表ID，主键")  {constraints(nullable:"false")}  
            column(name: "LANG", type: "varchar(" + 20 * weight + ")",  remarks: "语言")  {constraints(nullable:"false")}  
            column(name: "THING_MODEL_NAME", type: "varchar(" + 45 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "varchar(" + 255 * weight + ")",  remarks: "说明")   

        }

        addUniqueConstraint(columnNames:"THING_MODEL_ID,LANG",tableName:"hiot_thing_model_tl",constraintName: "hiot_thing_model_tl_u1")
    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-20_hiot_thing_model_tl_fix"){
        sql {
            "INSERT INTO hiot_thing_model_tl ( " +
                    "  thing_model_id, " +
                    "  lang, " +
                    "  thing_model_name, " +
                    "  description " +
                    ") SELECT " +
                    "  tm.THING_MODEL_ID, " +
                    "  'zh_CN', " +
                    "  tm.THING_MODEL_NAME, " +
                    "  tm.DESCRIPTION " +
                    "FROM " +
                    "  hiot_thing_model tm " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_thing_model_tl tmt " +
                    "  WHERE " +
                    "    tmt.THING_MODEL_ID = tm.THING_MODEL_ID " +
                    ") " +
                    "UNION " +
                    "  SELECT " +
                    "  tm.THING_MODEL_ID, " +
                    "  'ja_JP', " +
                    "  tm.THING_MODEL_NAME, " +
                    "  tm.DESCRIPTION " +
                    "FROM " +
                    "  hiot_thing_model tm " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_thing_model_tl tmt " +
                    "  WHERE " +
                    "    tmt.THING_MODEL_ID = tm.THING_MODEL_ID " +
                    ") " +
                    "  UNION " +
                    "SELECT " +
                    "  tm.THING_MODEL_ID, " +
                    "  'en_US', " +
                    "  tm.THING_MODEL_NAME, " +
                    "  tm.DESCRIPTION " +
                    "FROM " +
                    "  hiot_thing_model tm " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_thing_model_tl tmt " +
                    "  WHERE " +
                    "    tmt.THING_MODEL_ID = tm.THING_MODEL_ID " +
                    ");"
        }

    }
}