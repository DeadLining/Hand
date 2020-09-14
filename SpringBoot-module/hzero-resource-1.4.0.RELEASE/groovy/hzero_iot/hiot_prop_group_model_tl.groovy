package script.db

databaseChangeLog(logicalFilePath: 'script/db/hiot_prop_group_model_tl.groovy') {
    changeSet(author: "hzero.iot@hand-china.com", id: "2019-12-20-hiot_prop_group_model_tl") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        createTable(tableName: "hiot_prop_group_model_tl", remarks: "数据点组模版语言表") {
            column(name: "GROUP_MODEL_ID", type: "bigint",  remarks: "表ID，主键")  {constraints(nullable:"false")}  
            column(name: "LANG", type: "varchar(" + 20 * weight + ")",  remarks: "语言")  {constraints(nullable:"false")}  
            column(name: "GROUP_MODEL_NAME", type: "varchar(" + 45 * weight + ")",  remarks: "名称")  {constraints(nullable:"false")}  
            column(name: "DESCRIPTION", type: "varchar(" + 255 * weight + ")",  remarks: "说明")   

        }

        addUniqueConstraint(columnNames:"GROUP_MODEL_ID,LANG",tableName:"hiot_prop_group_model_tl",constraintName: "hiot_prop_group_model_tl_u1")
    }

    changeSet(author:"jianming.zhong01@hand-china.com", id: "2019-12-20_hiot_prop_group_model_tl_fix"){
        sql {
            "INSERT INTO hiot_prop_group_model_tl (  " +
                    "  group_model_id,  " +
                    "  lang,  " +
                    "  group_model_name,  " +
                    "  description  " +
                    ") SELECT  " +
                    "  pgm.GROUP_MODEL_ID,  " +
                    "  'zh_CN',  " +
                    "  pgm.GROUP_MODEL_NAME,  " +
                    "  pgm.DESCRIPTION  " +
                    "FROM  " +
                    "  hiot_prop_group_model pgm  " +
                    "WHERE  " +
                    "  1 = 1  " +
                    "AND NOT EXISTS (  " +
                    "  SELECT  " +
                    "    1  " +
                    "  FROM  " +
                    "    hiot_prop_group_model_tl pgmt  " +
                    "  WHERE  " +
                    "    pgm.GROUP_MODEL_ID = pgmt.GROUP_MODEL_ID  " +
                    ")  " +
                    "UNION  " +
                    "  SELECT  " +
                    "  pgm.GROUP_MODEL_ID,  " +
                    "  'ja_JP',  " +
                    "  pgm.GROUP_MODEL_NAME, " +
                    "  pgm.DESCRIPTION " +
                    "FROM " +
                    "  hiot_prop_group_model pgm " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_prop_group_model_tl pgmt " +
                    "  WHERE " +
                    "    pgm.GROUP_MODEL_ID = pgmt.GROUP_MODEL_ID " +
                    ") " +
                    "  UNION " +
                    "  SELECT " +
                    "  pgm.GROUP_MODEL_ID, " +
                    "  'en_US', " +
                    "  pgm.GROUP_MODEL_NAME, " +
                    "  pgm.DESCRIPTION " +
                    "FROM " +
                    "  hiot_prop_group_model pgm " +
                    "WHERE " +
                    "  1 = 1 " +
                    "AND NOT EXISTS ( " +
                    "  SELECT " +
                    "    1 " +
                    "  FROM " +
                    "    hiot_prop_group_model_tl pgmt " +
                    "  WHERE " +
                    "    pgm.GROUP_MODEL_ID = pgmt.GROUP_MODEL_ID " +
                    ");"
        }

    }
}