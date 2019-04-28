package com.skm.exa.mybatis.intercepts;

import com.skm.exa.common.utils.ReflectUtils;
import com.skm.exa.mybatis.BatchSaveParameter;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;

import java.sql.PreparedStatement;
import java.util.Collection;
import java.util.Properties;

/**
 * @author dhc
 * 2019-03-06 14:57
 */
@Intercepts({@Signature(type = ParameterHandler.class, method = "setParameters", args = {PreparedStatement.class})})
public class SetBatchParameterInterceptor implements Interceptor {

    @Override
    @SuppressWarnings({"rawtypes"})
    public Object intercept(Invocation invocation) throws Throwable {
        if (invocation.getTarget() instanceof DefaultParameterHandler) {
            DefaultParameterHandler parameterHandler = (DefaultParameterHandler) invocation.getTarget();

            Object param = parameterHandler.getParameterObject();
            if (param instanceof BatchSaveParameter) {
                BatchSaveParameter parameter = (BatchSaveParameter) param;

                PreparedStatement statement = (PreparedStatement) invocation.getArgs()[0];
                BoundSql boundSql = (BoundSql) ReflectUtils.getFieldValue(parameterHandler, "boundSql");

                Collection parameterObject = parameter.getData();

                statement.clearBatch();
                statement.clearParameters();

                MappedStatement mappedStatement = (MappedStatement) ReflectUtils.getFieldValue(parameterHandler, "mappedStatement");
                if (mappedStatement == null) throw new IllegalArgumentException("Can't get MappedStatement object!");

                int i = 0;
                for (Object pobject : parameterObject) {
                    DefaultParameterHandler handler = new DefaultParameterHandler(mappedStatement, pobject, boundSql);
                    handler.setParameters(statement);
                    statement.addBatch();
                    i += 1;
                    if (i % parameter.getBatchSize() == 0) {
                        statement.executeBatch();
                    }
                }
                if (parameterObject.size() % parameter.getBatchSize() != 0) {
                    statement.executeBatch();
                }
                return i;
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }
}
