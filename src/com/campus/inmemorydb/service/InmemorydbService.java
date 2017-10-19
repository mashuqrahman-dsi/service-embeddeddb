package com.campus.inmemorydb.service;

import com.campus.inmemorydb.InmemorydbModule;
import static com.campus.inmemorydb.namespace.PropertyNamespace.*;
import static com.campus.inmemorydb.namespace.InmemorydbNamespace.*;
import com.campus.common.ServiceNamespace;
import com.campus.service.AbstractService;
import com.campus.service.InternalAction;
import com.campus.service.InternalRedirect;
import com.campus.util.JSONUtil;
import com.campus.util.Reason;
import com.campus.util.ReasonCode;
import com.campus.util.ReasonException;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@SuppressWarnings("unchecked")
public class InmemorydbService extends AbstractService {
  

  private static final Logger _logger = Logger.getLogger(InmemorydbService.class.getName());
  
  private static final String DB_URL = "jdbc:derby:%s;create=%b;user=guybrush;password=threepwood";
  private static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";

  public InmemorydbService(String name$, String description$, InternalRedirect redirect$, List<InternalAction> actions$) {
    super(name$, description$, redirect$, actions$);
  }

  protected Object runMethod(String method$, Map settings$, Map header$, Map params$) {
    if ("setup".equals(method$))
      return setup(header$, params$);
    else if ("insert".equals(method$))
      return insert(header$, params$);
    else if ("select".equals(method$))
      return select(header$, params$);    
    throw new ReasonException(Reason.at(ReasonCode.NOT_FOUND, "method", method$));
  }

  private Map setup(Map header$, Map params$) {
    try {      
      String databaseName = (String) params$.get(DatabaseName);
      String createQuery = (String) params$.get(CreateQuery);
      String truncateStatement = (String) params$.get(TruncateStatement);
      Class.forName(DRIVER).newInstance();
      Connection connection = DriverManager.getConnection(String.format(DB_URL, databaseName,true));
      Statement statement = connection.createStatement();
      try {
        statement.execute(createQuery);
      } catch (Exception e) {
        if(null != truncateStatement)
          statement.execute(truncateStatement);  
      }
      statement.close();
      connection.close();
    } catch (Exception ex$) {
      ex$.printStackTrace();
      throw new ReasonException(Reason.at(ex$));
    }
    return null;
  }

  private Map insert(Map header$, Map params$) {
    try {
      String databaseName = (String) params$.get(DatabaseName);
      String insertQuery = (String) params$.get(InsertQuery);
      Map params = (Map) params$.get(Params);

      Class.forName(DRIVER).newInstance();
      Connection connection = DriverManager.getConnection(String.format(DB_URL, databaseName,false));

      PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
      setParameters(preparedStatement, params);
      preparedStatement.executeUpdate();

      preparedStatement.close();
      connection.close();
    } catch (Exception ex$) {
      ex$.printStackTrace();
      throw new ReasonException(Reason.at(ex$));
    }
    return null;
  }
  
  private Map select(Map header$, Map params$) {
    try {
      String databaseName = (String) params$.get(DatabaseName);
      String selectQuery = (String) params$.get(SelectQuery);
      Map params = (Map) params$.get(Params);
      
      Class.forName(DRIVER).newInstance();
      Connection connection = DriverManager.getConnection(String.format(DB_URL, databaseName,false));

      PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);
      setParameters(preparedStatement, params);
      ResultSet resultSet = preparedStatement.executeQuery();
      Object result = convertResultSetToList(resultSet);

      preparedStatement.close();
      connection.close();
      
      return Collections.singletonMap(Result, result);
    } catch (Exception ex$) {
      ex$.printStackTrace();
      throw new ReasonException(Reason.at(ex$));
    }
  }

  private void setParameters(PreparedStatement preparedStatement$, Map<Object, Object> params$) throws SQLException {
    if (null == preparedStatement$ || null == params$)
      return;
    for (Map.Entry<Object, Object> entry : params$.entrySet()) {
      if(null != entry.getValue() && entry.getValue() instanceof String)preparedStatement$.setString((Integer) entry.getKey(), (String) entry.getValue());
      if(null != entry.getValue() && entry.getValue() instanceof Integer)preparedStatement$.setInt((Integer) entry.getKey(), (Integer) entry.getValue());
      
    }
  }

  public List<HashMap<String, Object>> convertResultSetToList(ResultSet rs) throws SQLException {
    ResultSetMetaData md = rs.getMetaData();
    int columns = md.getColumnCount();
    List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
    while (rs.next()) {
      HashMap<String, Object> row = new HashMap<String, Object>(columns);
      for (int i = 1; i <= columns; ++i) {
        row.put(md.getColumnName(i), rs.getObject(i));
      }
      list.add(row);
    }

    return list;
  }

}