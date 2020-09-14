package script.db

databaseChangeLog(logicalFilePath: 'script/db/todo_user.groovy') {
    changeSet(author: "shengzhou.kong@hand-china.com", id: "2020-07-21-todo_user") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'todo_user_s', startValue:"1")
        }
        createTable(tableName: "todo_user", remarks: "用户表") {
            column(name: "ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "EMPLOYEE_NAME", type: "varchar(" + 30 * weight + ")",  remarks: "员工名")  {constraints(nullable:"false")}  
            column(name: "EMPLOYEE_NUMBER", type: "varchar(" + 30 * weight + ")",  remarks: "员工编号")  {constraints(nullable:"false")}  
            column(name: "EMAIL", type: "varchar(" + 60 * weight + ")",  remarks: "邮箱")   
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"EMPLOYEE_NUMBER",tableName:"todo_user",constraintName: "TODO_USER_u1")
    }
}