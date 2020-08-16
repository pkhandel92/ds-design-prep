package com.prep.ds.design;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionPool extends ConnectionPool<Connection> {

    private String usr;
    private String dsn;
    private String pwd;
    public DatabaseConnectionPool(String driver,int corePoolSize, int expiryTime,String usr,String dsn,String pwd) {
        super(corePoolSize, expiryTime);
        try {
            Class.forName(driver).newInstance();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.usr=usr;
        this.dsn=dsn;
        this.pwd=pwd;
    }

    @Override
    protected void killConnection(Connection temp) throws SQLException {
        temp.close();
    }

    @Override
    protected Connection create() throws SQLException {
        try {
            return (DriverManager.getConnection(dsn, usr, pwd));
        }
        catch (SQLException e) {
            throw e;
        }
    }

    @Override
    protected boolean isValid(Connection temp) throws SQLException {
        return temp.isClosed();
    }

    public static void main(String[] args) throws Throwable {
        DatabaseConnectionPool connectionPool=new DatabaseConnectionPool("org.hsqldb.jdbcDriver",100,100,"usr","dsn","pwd");
            Connection connection=connectionPool.getConnection();
            connectionPool.releaseConnection(connection);


    }
}
