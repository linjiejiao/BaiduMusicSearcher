
package cn.ljj.baidu.music;

import com.google.gson.JsonObject;

public class BaiduMusicInfo {
	public static final String KEY_TITLE = "title";
	public static final String KEY_SONG_ID = "song_id";
	public static final String KEY_AUTHOR = "author";
	public static final String KEY_ARTIST_ID = "artist_id";
	public static final String KEY_ALL_ARTIST_ID = "all_artist_id";
	public static final String KEY_ALBUM_TITLE = "album_title";
	public static final String KEY_APPENDIX = "appendix";
	public static final String KEY_ALBUM_ID = "album_id";
	public static final String KEY_LRC_LINK = "lrclink";
	public static final String KEY_RESOURCE_TYPE = "resource_type";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_RELATE_STATUS = "relate_status";
	public static final String KEY_HAVE_HIGH = "havehigh";
	public static final String KEY_COPY_TYPE = "copy_type";
	public static final String KEY_DEL_STATUS = "del_status";
	public static final String KEY_ALL_RATE = "all_rate";
	public static final String KEY_HAS_MV = "has_mv";
	public static final String KEY_HAS_MV_MOBILE = "has_mv_mobile";
	public static final String KEY_MV_PROVIDER = "mv_provider";
	public static final String KEY_CHARGE = "charge";
	public static final String KEY_TONE_ID = "toneid";
	public static final String KEY_INFO = "info";
	public static final String KEY_DATA_SOURCE = "data_source";
	public static final String KEY_LEARN = "learn";
	public static final String KEY_SONG_PIC_SMALL = "songPicSmall";
	public static final String KEY_SONG_PIC_BIG = "songPicBig";
	public static final String KEY_SONG_PIC_RADIO = "songPicRadio";
	public static final String KEY_SONG_LINK = "songLink";
	public static final String KEY_FORMAT = "format";
	public static final String KEY_RATE = "rate";
	public static final String KEY_SIZE = "size";

	public String title;
	public int songId;
	public String author;
	public int artistId;
	public String allArtistId;
	public String albumTitle;
	public String appendix;
	public int albumId;
	public String lrcLink;
	public int resourceType;
	public String content;
	public int relateStatus;
	public int haveHigh;
	public int copyType;
	public int delStatus;
	public String allRate;
	public int hasMv;
	public int hasMvMobile;
	public String mvProvider;
	public int charge;
	public String toneId;
	public String info;
	public int dataSource;
	public int learn;
	// second fetch detail
	public boolean isDetailFetched;
	public String songPicSmall;
	public String songPicBig;
	public String songPicRadio;
	public String songLink;
	public String format;
	public int rate;
	public int size;

	public static BaiduMusicInfo fromJson(JsonObject object) {
		BaiduMusicInfo info = new BaiduMusicInfo();
		info.title = textRefix(object.get(KEY_TITLE).getAsString());
		info.songId = object.get(KEY_SONG_ID).getAsInt();
		info.author = textRefix(object.get(KEY_AUTHOR).getAsString());
		info.artistId = object.get(KEY_ARTIST_ID).getAsInt();
		info.allArtistId = object.get(KEY_ALL_ARTIST_ID).getAsString();
		info.albumTitle = textRefix(object.get(KEY_ALBUM_TITLE).getAsString());
		info.appendix = object.get(KEY_APPENDIX).getAsString();
		info.albumId = object.get(KEY_ALBUM_ID).getAsInt();
		info.lrcLink = object.get(KEY_LRC_LINK).getAsString();
		if (info.lrcLink != null && info.lrcLink.length() > 0) {
			info.lrcLink = "http://ting.baidu.com" + info.lrcLink;
		}
		info.resourceType = object.get(KEY_RESOURCE_TYPE).getAsInt();
		info.content = textRefix(object.get(KEY_CONTENT).getAsString());
		info.relateStatus = object.get(KEY_RELATE_STATUS).getAsInt();
		info.haveHigh = object.get(KEY_HAVE_HIGH).getAsInt();
		info.copyType = object.get(KEY_COPY_TYPE).getAsInt();
		info.delStatus = object.get(KEY_DEL_STATUS).getAsInt();
		info.allRate = object.get(KEY_ALL_RATE).getAsString();
		info.hasMv = object.get(KEY_HAS_MV).getAsInt();
		info.hasMvMobile = object.get(KEY_HAS_MV_MOBILE).getAsInt();
		info.mvProvider = textRefix(object.get(KEY_MV_PROVIDER).getAsString());
		info.charge = object.get(KEY_CHARGE).getAsInt();
		info.toneId = object.get(KEY_TONE_ID).getAsString();
		info.info = textRefix(object.get(KEY_INFO).getAsString());
		info.dataSource = object.get(KEY_DATA_SOURCE).getAsInt();
		info.learn = object.get(KEY_LEARN).getAsInt();
		return info;
	}

	public static String toJson(BaiduMusicInfo musicInfo) {
		JsonObject object = new JsonObject();
		object.addProperty(KEY_TITLE, musicInfo.title);
		object.addProperty(KEY_SONG_ID, musicInfo.songId);
		object.addProperty(KEY_AUTHOR, musicInfo.author);
		object.addProperty(KEY_ARTIST_ID, musicInfo.artistId);
		object.addProperty(KEY_ALL_ARTIST_ID, musicInfo.allArtistId);
		object.addProperty(KEY_ALBUM_TITLE, musicInfo.albumTitle);
		object.addProperty(KEY_APPENDIX, musicInfo.appendix);
		object.addProperty(KEY_ALBUM_ID, musicInfo.albumId);
		object.addProperty(KEY_LRC_LINK, musicInfo.lrcLink);
		object.addProperty(KEY_RESOURCE_TYPE, musicInfo.resourceType);
		object.addProperty(KEY_CONTENT, musicInfo.content);
		object.addProperty(KEY_RELATE_STATUS, musicInfo.relateStatus);
		object.addProperty(KEY_HAVE_HIGH, musicInfo.haveHigh);
		object.addProperty(KEY_COPY_TYPE, musicInfo.copyType);
		object.addProperty(KEY_DEL_STATUS, musicInfo.delStatus);
		object.addProperty(KEY_ALL_RATE, musicInfo.allRate);
		object.addProperty(KEY_HAS_MV, musicInfo.hasMv);
		object.addProperty(KEY_HAS_MV_MOBILE, musicInfo.hasMvMobile);
		object.addProperty(KEY_MV_PROVIDER, musicInfo.mvProvider);
		object.addProperty(KEY_CHARGE, musicInfo.charge);
		object.addProperty(KEY_TONE_ID, musicInfo.toneId);
		object.addProperty(KEY_INFO, musicInfo.info);
		object.addProperty(KEY_DATA_SOURCE, musicInfo.dataSource);
		object.addProperty(KEY_LEARN, musicInfo.learn);
		object.addProperty(KEY_SONG_PIC_SMALL, musicInfo.songPicSmall);
		object.addProperty(KEY_SONG_PIC_BIG, musicInfo.songPicBig);
		object.addProperty(KEY_SONG_PIC_RADIO, musicInfo.songPicRadio);
		object.addProperty(KEY_SONG_LINK, musicInfo.songLink);
		object.addProperty(KEY_FORMAT, musicInfo.format);
		object.addProperty(KEY_RATE, musicInfo.rate);
		object.addProperty(KEY_SIZE, musicInfo.size);
		return object.toString();
	}

	public void setDetailInfos(JsonObject object) {
		songPicSmall = fixLink(object.get(KEY_SONG_PIC_SMALL).getAsString());
		songPicBig = fixLink(object.get(KEY_SONG_PIC_BIG).getAsString());
		songPicRadio = fixLink(object.get(KEY_SONG_PIC_RADIO).getAsString());
		songLink = fixLink(object.get(KEY_SONG_LINK).getAsString());
		format = object.get(KEY_FORMAT).getAsString();
		rate = object.get(KEY_RATE).getAsInt();
		size = object.get(KEY_SIZE).getAsInt();
		isDetailFetched = true;
		fillLinks();
	}

	private static String textRefix(String text) {
		if (text.contains("<em>")) {
			text = text.replace("<em>", "");
		}
		if (text.contains("</em>")) {
			text = text.replace("</em>", "");
		}
		return text;
	}

	private static String fixLink(String link) {
		// "http://c.hiphotos.baidu.com/ting/pic/item/http://qukufile2.qianqian.com/data2/pic/115439298/115439298.jpg.jpg"
		if (link != null && link.contains("http://")) {
			link = link.substring(link.lastIndexOf("http://"));
		}
		return link;
	}

	private void fillLinks() {
		String link = null;
		if (songPicRadio != null && songPicRadio.length() > 0) {
			link = songPicRadio;
		} else if (songPicBig != null && songPicBig.length() > 0) {
			link = songPicBig;
		} else if (songPicSmall != null && songPicSmall.length() > 0) {
			link = songPicSmall;
		}
		if (link != null) { // http://musicdata.baidu.com/data2/pic/246707883/246707883.jpg@s_0,w_300
			if (songPicRadio == null || songPicRadio.length() <= 0) {
				int index = link.indexOf("@s_");
				if (index != -1) {
					songPicRadio = link.substring(0, index - 1) + "@s_0,w_300";
				} else {
					songPicRadio = link;
				}
			}
			if (songPicSmall == null || songPicSmall.length() <= 0) {
				int index = link.indexOf("@s_");
				if (index != -1) {
					songPicSmall = link.substring(0, index - 1) + "@s_0,w_100";
				} else {
					songPicSmall = link;
				}
			}
			if (songPicBig == null || songPicBig.length() <= 0) {
				int index = link.indexOf("@s_");
				if (index != -1) {
					songPicBig = link.substring(0, index - 1) + "@s_0,w_600";
				} else {
					songPicBig = link;
				}
			}
		}
	}

	@Override
	public String toString() {
		return "BaiduMusicInfo [title=" + title + ", songId=" + songId + ", author=" + author + ", artistId=" + artistId
				+ ", allArtistId=" + allArtistId + ", albumTitle=" + albumTitle + ", appendix=" + appendix
				+ ", albumId=" + albumId + ", lrcLink=" + lrcLink + ", resourceType=" + resourceType + ", content="
				+ content + ", relateStatus=" + relateStatus + ", haveHigh=" + haveHigh + ", copyType=" + copyType
				+ ", delStatus=" + delStatus + ", allRate=" + allRate + ", hasMv=" + hasMv + ", hasMvMobile="
				+ hasMvMobile + ", mvProvider=" + mvProvider + ", charge=" + charge + ", toneId=" + toneId + ", info="
				+ info + ", dataSource=" + dataSource + ", learn=" + learn + ", isDetailFetched=" + isDetailFetched
				+ ", songPicSmall=" + songPicSmall + ", songPicBig=" + songPicBig + ", songPicRadio=" + songPicRadio
				+ ", songLink=" + songLink + ", format=" + format + ", rate=" + rate + ", size=" + size + "]";
	}

	public static class AlbumInfo {
		public int albumId;
		public String title;
		public String picSmall;
		public String picBig;
		public String publishTime;
		public String publishCompany;

		public static AlbumInfo fromJson(JsonObject object) {
			AlbumInfo album = new AlbumInfo();
			album.albumId = object.get("album_id").getAsInt();
			album.title = object.get("title").getAsString();
			album.picSmall = object.get("pic_small").getAsString();
			album.picBig = object.get("pic_big").getAsString();
			album.publishTime = object.get("publishtime").getAsString();
			album.publishCompany = object.get("publishcompany").getAsString();
			return album;
		}

		@Override
		public String toString() {
			return "AlbumInfo [albumId=" + albumId + ", title=" + title + ", picSmall=" + picSmall + ", picBig="
					+ picBig + ", publishTime=" + publishTime + ", publishCompany=" + publishCompany + "]";
		}
	}

	public static class ArtistInfo {
		public int artistId;
		public int tingUid;
		public String name;
		public String country;
		public String albumsTotal;
		public String songsTotal;
		public String avatarSmall;
		public String avatarBig;

		public static ArtistInfo fromJson(JsonObject object) {
			ArtistInfo artist = new ArtistInfo();
			artist.artistId = object.get("artist_id").getAsInt();
			artist.tingUid = object.get("ting_uid").getAsInt();
			artist.name = object.get("name").getAsString();
			artist.country = object.get("country").getAsString();
			artist.albumsTotal = object.get("albums_total").getAsString();
			artist.songsTotal = object.get("songs_total").getAsString();
			JsonObject avatar = object.get("avatar").getAsJsonObject();
			artist.avatarSmall = avatar.get("small").getAsString();
			artist.avatarBig = avatar.get("big").getAsString();
			return artist;
		}

		@Override
		public String toString() {
			return "ArtistInfo [artistId=" + artistId + ", tingUid=" + tingUid + ", name=" + name + ", country="
					+ country + ", albumsTotal=" + albumsTotal + ", songsTotal=" + songsTotal + ", avatarSmall="
					+ avatarSmall + ", avatarBig=" + avatarBig + "]";
		}

	}
}
