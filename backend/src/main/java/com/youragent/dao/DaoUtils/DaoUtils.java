package com.youragent.dao.DaoUtils;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.sql.Types;

@Slf4j
public class DaoUtils {

    public static String updateQueryBuilder(@NonNull final String sqlQuery, @NonNull final String column) {

        log.info("Creating update query string...");

        final String queryWithParams = String.format("%s = ?", column);
        final String constructedQueryString = String.format(sqlQuery, queryWithParams);

        log.info("Constructed query string = {}", constructedQueryString);

        return constructedQueryString;
    }

    public static int getSqlType(Object value) {
        if (value instanceof String) return Types.VARCHAR;
        if (value instanceof Integer) return Types.INTEGER;
        if (value instanceof Long) return Types.BIGINT;
        if (value instanceof Boolean) return Types.BOOLEAN;
        throw new IllegalArgumentException("Unsupported type: " + value.getClass());
    }
}
