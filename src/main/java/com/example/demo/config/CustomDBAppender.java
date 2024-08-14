package com.example.demo.config;

import ch.qos.logback.classic.spi.CallerData;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.db.DBAppenderBase;
import com.example.demo.util.SnowflakeIdGenerator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class CustomDBAppender extends DBAppenderBase<ILoggingEvent> {
    private String insertSQL;
    private static final Method GET_GENERATED_KEYS_METHOD;

    // 对应于数据库字段的插入数据序号
    private static final int ID_INDEX = 1;
    private static final int CREATE_TIME_INDEX = 2;
    private static final int MESSAGE_INDEX = 3;
    private static final int LEVEL_STRING_INDEX = 4;
    private static final int LOGGER_NAME_INDEX = 5;
    private static final int THREAD_NAME_INDEX = 6;
    private static final int REFERENCE_FLAG_INDEX = 7;
    private static final int CALLER_FILENAME_INDEX = 8;
    private static final int CALLER_CLASS_INDEX = 9;
    private static final int CALLER_METHOD_INDEX = 10;
    private static final int CALLER_LINE_INDEX = 11;

    private static final StackTraceElement EMPTY_CALLER_DATA = CallerData.naInstance();

    // 处理主键的自动生成，这里我们使用手工生成，因此下面代码可忽略
    static {
        // PreparedStatement.getGeneratedKeys() method was added in JDK 1.4
        Method getGeneratedKeysMethod;
        try {
            // the
            getGeneratedKeysMethod = PreparedStatement.class.getMethod("getGeneratedKeys", (Class[]) null);
        } catch (Exception ex) {
            getGeneratedKeysMethod = null;
        }
        GET_GENERATED_KEYS_METHOD = getGeneratedKeysMethod;
    }

    @Override
    public void start() {
        insertSQL = buildInsertSQL();
        cnxSupportsBatchUpdates = connectionSource.supportsBatchUpdates();
        // super.start();
        super.started = true;
    }

    // 核心代码，构建插入语句，并对应数据库字段
    private static String buildInsertSQL() {
        return "INSERT INTO t_log_logback " +
                "(id, create_time, message, level_string, logger_name, thread_name, " +
                "reference_flag, caller_filename, caller_class, caller_method, caller_line) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    // 管理每个字段插入的数据
    private void bindLoggingEventWithInsertStatement(PreparedStatement stmt, ILoggingEvent event) throws SQLException {
        // TODO 手工处理ID的生成
        stmt.setString(ID_INDEX, String.format("%d",new SnowflakeIdGenerator(1L, 1L).nextId()));
//        stmt.setString(ID_INDEX, "0");
        stmt.setString(CREATE_TIME_INDEX, LocalDateTime.ofInstant(Instant.ofEpochMilli(event.getTimeStamp()),
                ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        stmt.setString(MESSAGE_INDEX, event.getFormattedMessage());
        stmt.setString(LEVEL_STRING_INDEX, event.getLevel().toString());
        stmt.setString(LOGGER_NAME_INDEX, event.getLoggerName());
        stmt.setString(THREAD_NAME_INDEX, event.getThreadName());
    }

    // 管理每个字段插入的数据
    private void bindCallerDataWithPreparedStatement(PreparedStatement stmt, StackTraceElement[] callerDataArray) throws SQLException {
        StackTraceElement caller = extractFirstCaller(callerDataArray);
        stmt.setInt(REFERENCE_FLAG_INDEX, 0);
        stmt.setString(CALLER_FILENAME_INDEX, caller.getFileName());
        stmt.setString(CALLER_CLASS_INDEX, caller.getClassName());
        stmt.setString(CALLER_METHOD_INDEX, caller.getMethodName());
        stmt.setString(CALLER_LINE_INDEX, Integer.toString(caller.getLineNumber()));
    }

    // 核心方法，插入具体日志数据
    @Override
    protected void subAppend(ILoggingEvent event, Connection connection, PreparedStatement insertStatement) throws Throwable {
        bindLoggingEventWithInsertStatement(insertStatement, event);
        // This is expensive... should we do it every time?
        bindCallerDataWithPreparedStatement(insertStatement, event.getCallerData());
        int updateCount = insertStatement.executeUpdate();
        if (updateCount != 1) {
            addWarn("Failed to insert loggingEvent");
        }
    }

    private StackTraceElement extractFirstCaller(StackTraceElement[] callerDataArray) {
        StackTraceElement caller = EMPTY_CALLER_DATA;
        if (hasAtLeastOneNonNullElement(callerDataArray))
            caller = callerDataArray[0];
        return caller;
    }

    private boolean hasAtLeastOneNonNullElement(StackTraceElement[] callerDataArray) {
        return callerDataArray != null && callerDataArray.length > 0 && callerDataArray[0] != null;
    }

    @Override
    protected Method getGeneratedKeysMethod() {
        return GET_GENERATED_KEYS_METHOD;
    }

    @Override
    protected String getInsertSQL() {
        return insertSQL;
    }

    protected void secondarySubAppend(ILoggingEvent event, Connection connection, long eventId) throws Throwable {
    }

    @Override
    protected long selectEventId(PreparedStatement insertStatement, Connection connection) throws SQLException, InvocationTargetException {
        return 0;
    }
}

