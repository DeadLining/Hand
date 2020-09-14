package script.db

databaseChangeLog(logicalFilePath: 'script/db/todo_task.groovy') {
    changeSet(author: "shengzhou.kong@hand-china.com", id: "2020-07-21-todo_task") {
        def weight = 1
        if(helper.isSqlServer()){
            weight = 2
        } else if(helper.isOracle()){
            weight = 3
        }
        if(helper.dbType().isSupportSequence()){
            createSequence(sequenceName: 'todo_task_s', startValue:"1")
        }
        createTable(tableName: "todo_task", remarks: "任务表") {
            column(name: "ID", type: "bigint", autoIncrement: true ,   remarks: "表ID，主键，供其他表做外键")  {constraints(primaryKey: true)} 
            column(name: "EMPLOYEE_ID", type: "bigint",  remarks: "员工ID，TODO_USER.ID")  {constraints(nullable:"false")}  
            column(name: "STATE", type: "varchar(" + 30 * weight + ")",  remarks: "状态，值集：TODO.STATE")  {constraints(nullable:"false")}  
            column(name: "TASK_NUMBER", type: "varchar(" + 60 * weight + ")",  remarks: "任务编号")  {constraints(nullable:"false")}  
            column(name: "TASK_DESCRIPTION", type: "varchar(" + 240 * weight + ")",  remarks: "任务描述")   
            column(name: "TENANT_ID", type: "bigint",  remarks: "租户ID")  {constraints(nullable:"false")}  
            column(name: "object_version_number", type: "bigint",   defaultValue:"1",   remarks: "行版本号，用来处理锁")  {constraints(nullable:"false")}  
            column(name: "creation_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "created_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_updated_by", type: "bigint",   defaultValue:"-1",   remarks: "")  {constraints(nullable:"false")}  
            column(name: "last_update_date", type: "datetime",   defaultValueComputed:"CURRENT_TIMESTAMP",   remarks: "")  {constraints(nullable:"false")}  

        }

        addUniqueConstraint(columnNames:"TASK_NUMBER,TENANT_ID",tableName:"todo_task",constraintName: "TODO_TASK_u1")
    }
}