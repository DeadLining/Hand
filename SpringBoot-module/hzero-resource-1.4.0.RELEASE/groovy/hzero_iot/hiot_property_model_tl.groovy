package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_property_model_tl.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_property_model_tl") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        createTable(tableName: "hiot_property_model_tl", remarks: "数据点模板语言表") {
            column(name: "PROPERTY_MODEL_ID", type: "bigint",  remarks: "表ID，主键")  {constraints(nullable:"false")}  
            column(name: "LANG", type: "varchar(" + 20 * weight + ")",  remarks: "语言")  {constraints(nullable:"false")}  
            column(name: "PROPERTY_MODEL_NAME", type: "varchar(" + 45 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "varchar(" + 255 * weight + ")",  remarks: "说明")   

        }

        addUniqueConstraint(columnNames:"PROPERTY_MODEL_ID,LANG",tableName:"hiot_property_model_tl",constraintName: "hiot_property_model_tl_u1")
    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-20_hiot_property_model_tl_fix"){
        sql {
            "INSERT INTO hiot_property_model_tl ( " +
                    "  property_model_id, " +
                    "  lang, " +
                    "  property_model_name, " +
                    "  description " +
                    ") SELECT " +
                    "  pm.PROPERTY_MODEL_ID, " +
                    "  'zh_CN', " +
                    "  pm.PROPERTY_MODEL_NAME, " +
                    "  pm.DESCRIPTION " +
                    "FROM " +
                    "  hiot_property_model pm " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_property_model_tl pml " +
                    "  WHERE " +
                    "    pm.PROPERTY_MODEL_ID = pml.PROPERTY_MODEL_ID " +
                    ") " +
                    "UNION " +
                    "   SELECT " +
                    "  pm.PROPERTY_MODEL_ID, " +
                    "  'ja_JP', " +
                    "  pm.PROPERTY_MODEL_NAME, " +
                    "  pm.DESCRIPTION " +
                    "FROM " +
                    "  hiot_property_model pm " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_property_model_tl pml " +
                    "  WHERE " +
                    "    pm.PROPERTY_MODEL_ID = pml.PROPERTY_MODEL_ID " +
                    ") " +
                    "  UNION " +
                    "   SELECT " +
                    "  pm.PROPERTY_MODEL_ID, " +
                    "  'en_US', " +
                    "  pm.PROPERTY_MODEL_NAME, " +
                    "  pm.DESCRIPTION " +
                    "FROM " +
                    "  hiot_property_model pm " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_property_model_tl pml " +
                    "  WHERE " +
                    "    pm.PROPERTY_MODEL_ID = pml.PROPERTY_MODEL_ID " +
                    ");"
        }

    }
}