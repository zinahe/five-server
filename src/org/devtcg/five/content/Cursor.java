package org.devtcg.five.content;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Simple wrapper around ResultSet which offers greater functionality.
 */
public class Cursor
{
	private final ResultSet mSet;
	private final ColumnsMap mColumnsMap;

	public Cursor(ResultSet set, ColumnsMap columnsMap)
	{
		if (set == null)
			throw new IllegalArgumentException("ResultSet may not be null.");

		mSet = set;
		mColumnsMap = columnsMap;
	}

	public Cursor(ResultSet set) throws SQLException
	{
		this(set, ColumnsMap.fromResultSet(set));
	}

	public ColumnsMap getColumnsMap()
	{
		return mColumnsMap;
	}

	public ResultSet getResultSet()
	{
		return mSet;
	}

	/**
	 * Wrapper to call close on the underlying ResultSet object.
	 */
	public void close() throws SQLException
	{
		mSet.close();
	}

	/**
	 * Wrapper to call getColumnIndex on the underlying ColumnsMap object.
	 */
	public int getColumnIndex(String columnName)
	{
		if (mColumnsMap == null)
			throw new UnsupportedOperationException("ColumnsMap is null; did you pass null to the Cursor constructor?");

		return mColumnsMap.getColumnIndex(columnName);
	}
}
