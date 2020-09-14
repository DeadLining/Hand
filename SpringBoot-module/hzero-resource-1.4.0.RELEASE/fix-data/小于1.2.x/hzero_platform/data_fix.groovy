package script.db

databaseChangeLog(logicalFilePath: 'script/db/data_fix.groovy') {
    changeSet(author: "hzero@hand-china.com", id: "2020-05-28-hiam_doc_type_dimension"){
        update(tableName:'hiam_doc_type_dimension'){
            column(name:'dimension_code', value:'INV_ORGANIZATION')
            where "dimension_code='INVORG'"
        }
        update(tableName:'hiam_doc_type_dimension'){
            column(name:'dimension_code', value:'PURCHASE_ORGANIZATION')
            where "dimension_code='PURORG'"
        }
        update(tableName:'hiam_doc_type_dimension'){
            column(name:'dimension_code', value:'PURCHASE_AGENT')
            where "dimension_code='PURAGENT'"
        }
    }

    changeSet(author: "hzero@hand-china.com", id: "2020-05-28-hiam_role_auth_data"){
        update(tableName:'hiam_role_auth_data'){
            column(name:'authority_type_code', value:'INV_ORGANIZATION')
            where "authority_type_code='INVORG'"
        }
        update(tableName:'hiam_role_auth_data'){
            column(name:'authority_type_code', value:'PURCHASE_ORGANIZATION')
            where "authority_type_code='PURORG'"
        }
        update(tableName:'hiam_role_auth_data'){
            column(name:'authority_type_code', value:'PURCHASE_AGENT')
            where "authority_type_code='PURAGENT'"
        }
    }

    changeSet(author: "hzero@hand-china.com", id: "2020-05-28-hiam_role_authority_line"){
        update(tableName:'hiam_role_authority_line'){
            column(name:'auth_type_code', value:'INV_ORGANIZATION')
            where "auth_type_code='INVORG'"
        }
        update(tableName:'hiam_role_authority_line'){
            column(name:'auth_type_code', value:'PURCHASE_ORGANIZATION')
            where "auth_type_code='PURORG'"
        }
        update(tableName:'hiam_role_authority_line'){
            column(name:'auth_type_code', value:'PURCHASE_AGENT')
            where "auth_type_code='PURAGENT'"
        }
    }

    changeSet(author: "hzero@hand-china.com", id: "2020-05-28-hiam_user_authority"){
        update(tableName:'hiam_user_authority'){
            column(name:'authority_type_code', value:'INV_ORGANIZATION')
            where "authority_type_code='INVORG'"
        }
        update(tableName:'hiam_user_authority'){
            column(name:'authority_type_code', value:'PURCHASE_ORGANIZATION')
            where "authority_type_code='PURORG'"
        }
        update(tableName:'hiam_user_authority'){
            column(name:'authority_type_code', value:'PURCHASE_AGENT')
            where "authority_type_code='PURAGENT'"
        }
    }

    changeSet(author: "hzero@hand-china.com", id: "2020-06-08-tenant_data_fix") {
        if (helper.isSqlServer()) {
            sqlFile(encoding: "UTF-8", path: "mssql_tenant_data_fix.sql", stripComments: "true", splitStatements: "true", relativeToChangelogFile: "true")
        } else {
            sqlFile(encoding: "UTF-8", path: "mysql_tenant_data_fix.sql", stripComments: "true", splitStatements: "true", relativeToChangelogFile: "true")
        }
    }

    changeSet(author: "hzero@hand-china.com", id: "2020-06-18-tenant_data_fix") {
        if (helper.isSqlServer()) {
            sqlFile(encoding: "UTF-8", path: "mssql_tenant_data_fix_2.sql", stripComments: "true", splitStatements: "true", relativeToChangelogFile: "true")
        } else {
            sqlFile(encoding: "UTF-8", path: "mysql_tenant_data_fix_2.sql", stripComments: "true", splitStatements: "true", relativeToChangelogFile: "true")
        }
    }

}
