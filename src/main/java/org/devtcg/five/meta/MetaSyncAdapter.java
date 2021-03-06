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

package org.devtcg.five.meta;

import org.devtcg.five.content.AbstractTableMerger;
import org.devtcg.five.content.SyncAdapter;

public class MetaSyncAdapter extends SyncAdapter<MetaProvider>
{
	public MetaSyncAdapter(MetaProvider provider)
	{
		super(provider);
	}

	public AbstractTableMerger getMerger(String name)
	{
		if (name.equals("artists"))
			return mProvider.getArtistDAO().new TableMerger();
		else if (name.equals("albums"))
			return mProvider.getAlbumDAO().new TableMerger();
		else if (name.equals("songs"))
			return mProvider.getSongDAO().new TableMerger();
		else if (name.equals("playlists"))
			return mProvider.getPlaylistDAO().new TableMerger();
		else if (name.equals("playlistSongs"))
			return mProvider.getPlaylistSongDAO().new TableMerger();

		return null;
	}
}
