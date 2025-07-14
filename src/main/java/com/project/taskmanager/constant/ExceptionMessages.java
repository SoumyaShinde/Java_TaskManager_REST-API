package com.project.taskmanager.constant;

import jakarta.servlet.http.PushBuilder;

public class ExceptionMessages {

    public static final String TASK_NOT_FOUND = "Task with the given ID was not found.";
    public static final String NO_TASKS_DUE = "You have no tasks due in the next %d %s.";
    public static final String NO_TASKS_DUE_TODAY = "You have no tasks due today";
    public static final String TASK_DELETED_SUCCESSFULLY = "Data Deleted Successfully";
    public static final String DATA_NOT_FOUND = "Data not found";
    public static final String NO_TASK_FOR_CATEGORY = "No task generated, please add couple of tasks to use this feature";
    public static final String DUE_DATE_BEFORE_CURRENT = " Due date must be today or a future date.";

    public static final String INCORRECT_DATE_FORMAT = "Date format error: expected format is yyyy-MM-dd.";
    public static final String INVALID_STATUS = "Status must be one of: ";
    public static final String INVALID_PRIORITY= "Priority must be one of: ";

}
