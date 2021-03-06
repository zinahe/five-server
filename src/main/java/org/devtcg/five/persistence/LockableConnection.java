/*
 * Copyright (C) 2009 Josh Guilfoyle <jasta@devtcg.org>
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 2, or (at your option) any
 * later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 */

package org.devtcg.five.persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Wrapper around Connection which can provide fair locking for database engines
 * that do not support transactions.
 * <p>
 * The lock is to be used and maintained exclusively by the caller. That is,
 * none of the wrapped methods are implicitly locked.
 */
public class LockableConnection
{
	private final Connection mConnection;
	private final ReentrantLock mLock = new ReentrantLock(true);

	public LockableConnection(Connection conn)
	{
		if (conn == null)
			throw new IllegalArgumentException("Wrapped connection must not be null.");

		mConnection = conn;
	}

	public Connection getWrappedConnection()
	{
		return mConnection;
	}

	public void lock()
	{
		mLock.lock();
	}

	public void unlock()
	{
		mLock.unlock();
	}

	public boolean yieldIfContended()
	{
		if (mLock.isHeldByCurrentThread() == false)
			return false;

		if (mLock.getQueueLength() == 0)
			return false;

		mLock.unlock();
		if (mLock.isHeldByCurrentThread())
			throw new IllegalStateException("Connection locked more than once, cannot yield.");
		mLock.lock();

		return true;
	}

	public void clearWarnings() throws SQLException
	{
		mConnection.clearWarnings();
	}

	public void close() throws SQLException
	{
		mConnection.close();
	}

	public void commit() throws SQLException
	{
		mConnection.commit();
	}

	public Statement createStatement() throws SQLException
	{
		return mConnection.createStatement();
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency,
		int resultSetHoldability) throws SQLException
	{
		return mConnection.createStatement(resultSetType, resultSetConcurrency,
			resultSetHoldability);
	}

	public Statement createStatement(int resultSetType, int resultSetConcurrency)
		throws SQLException
	{
		return mConnection.createStatement(resultSetType, resultSetConcurrency);
	}

	public boolean getAutoCommit() throws SQLException
	{
		return mConnection.getAutoCommit();
	}

	public String getCatalog() throws SQLException
	{
		return mConnection.getCatalog();
	}

	public int getHoldability() throws SQLException
	{
		return mConnection.getHoldability();
	}

	public DatabaseMetaData getMetaData() throws SQLException
	{
		return mConnection.getMetaData();
	}

	public int getTransactionIsolation() throws SQLException
	{
		return mConnection.getTransactionIsolation();
	}

	public Map<String, Class<?>> getTypeMap() throws SQLException
	{
		return mConnection.getTypeMap();
	}

	public SQLWarning getWarnings() throws SQLException
	{
		return mConnection.getWarnings();
	}

	public boolean isClosed() throws SQLException
	{
		return mConnection.isClosed();
	}

	public boolean isReadOnly() throws SQLException
	{
		return mConnection.isReadOnly();
	}

	public String nativeSQL(String sql) throws SQLException
	{
		return mConnection.nativeSQL(sql);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency,
		int resultSetHoldability) throws SQLException
	{
		return mConnection.prepareCall(sql, resultSetType, resultSetConcurrency,
			resultSetHoldability);
	}

	public CallableStatement prepareCall(String sql, int resultSetType, int resultSetConcurrency)
		throws SQLException
	{
		return mConnection.prepareCall(sql, resultSetType, resultSetConcurrency);
	}

	public CallableStatement prepareCall(String sql) throws SQLException
	{
		return mConnection.prepareCall(sql);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
		int resultSetConcurrency, int resultSetHoldability) throws SQLException
	{
		return mConnection.prepareStatement(sql, resultSetType, resultSetConcurrency,
			resultSetHoldability);
	}

	public PreparedStatement prepareStatement(String sql, int resultSetType,
		int resultSetConcurrency) throws SQLException
	{
		return mConnection.prepareStatement(sql, resultSetType, resultSetConcurrency);
	}

	public PreparedStatement prepareStatement(String sql, int autoGeneratedKeys)
		throws SQLException
	{
		return mConnection.prepareStatement(sql, autoGeneratedKeys);
	}

	public PreparedStatement prepareStatement(String sql, int[] columnIndexes) throws SQLException
	{
		return mConnection.prepareStatement(sql, columnIndexes);
	}

	public PreparedStatement prepareStatement(String sql, String[] columnNames) throws SQLException
	{
		return mConnection.prepareStatement(sql, columnNames);
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return mConnection.prepareStatement(sql);
	}

	public void releaseSavepoint(Savepoint savepoint) throws SQLException
	{
		mConnection.releaseSavepoint(savepoint);
	}

	public void rollback() throws SQLException
	{
		mConnection.rollback();
	}

	public void rollback(Savepoint savepoint) throws SQLException
	{
		mConnection.rollback(savepoint);
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException
	{
		mConnection.setAutoCommit(autoCommit);
	}

	public void setCatalog(String catalog) throws SQLException
	{
		mConnection.setCatalog(catalog);
	}

	public void setHoldability(int holdability) throws SQLException
	{
		mConnection.setHoldability(holdability);
	}

	public void setReadOnly(boolean readOnly) throws SQLException
	{
		mConnection.setReadOnly(readOnly);
	}

	public Savepoint setSavepoint() throws SQLException
	{
		return mConnection.setSavepoint();
	}

	public Savepoint setSavepoint(String name) throws SQLException
	{
		return mConnection.setSavepoint(name);
	}

	public void setTransactionIsolation(int level) throws SQLException
	{
		mConnection.setTransactionIsolation(level);
	}

	public void setTypeMap(Map<String, Class<?>> map) throws SQLException
	{
		mConnection.setTypeMap(map);
	}
}
