package org.devtcg.five.content;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class ColumnsMap
{
	private final HashMap<String, Integer> mColumns;

	private ColumnsMap()
	{
		mColumns = new HashMap<String, Integer>();
	}

	private ColumnsMap(int size)
	{
		mColumns = new HashMap<String, Integer>(size);
	}

	private static ColumnsMap fromResultSetMetaData(ResultSetMetaData meta)
		throws SQLException
	{
		int n = meta.getColumnCount();
		ColumnsMap map = new ColumnsMap(n);

		while (n-- > 0)
			map.addColumn(meta.getColumnName(n + 1).toLowerCase(), n + 1);

		return map;
	}

	public static ColumnsMap fromResultSet(ResultSet set) throws SQLException
	{
		return fromResultSetMetaData(set.getMetaData());
	}

	public static ColumnsMap fromPreparedStatement(PreparedStatement stmt) throws SQLException
	{
		return fromResultSetMetaData(stmt.getMetaData());
	}

	private void addColumn(String name, int index)
	{
		mColumns.put(name, index);
	}

	public int getColumnIndex(String name)
	{
		Integer index = mColumns.get(name);
		if (index == null)
			throw new IllegalArgumentException("Column " + name + " was not found.");

		return index;
	}
}