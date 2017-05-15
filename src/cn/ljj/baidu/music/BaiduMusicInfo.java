
package cn.ljj.baidu.music;

import com.google.gson.JsonObject;

public class BaiduMusicInfo {
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
		info.title = textRefix(object.get("title").getAsString());
		info.songId = object.get("song_id").getAsInt();
		info.author = textRefix(object.get("author").getAsString());
		info.artistId = object.get("artist_id").getAsInt();
		info.allArtistId = object.get("all_artist_id").getAsString();
		info.albumTitle = textRefix(object.get("album_title").getAsString());
		info.appendix = object.get("appendix").getAsString();
		info.albumId = object.get("album_id").getAsInt();
		info.lrcLink = object.get("lrclink").getAsString();
		if (info.lrcLink != null && info.lrcLink.length() > 0) {
			info.lrcLink = "http://ting.baidu.com" + info.lrcLink;
		}
		info.resourceType = object.get("resource_type").getAsInt();
		info.content = textRefix(object.get("content").getAsString());
		info.relateStatus = object.get("relate_status").getAsInt();
		info.haveHigh = object.get("havehigh").getAsInt();
		info.copyType = object.get("copy_type").getAsInt();
		info.delStatus = object.get("del_status").getAsInt();
		info.allRate = object.get("all_rate").getAsString();
		info.hasMv = object.get("has_mv").getAsInt();
		info.hasMvMobile = object.get("has_mv_mobile").getAsInt();
		info.mvProvider = textRefix(object.get("mv_provider").getAsString());
		info.charge = object.get("charge").getAsInt();
		info.toneId = object.get("toneid").getAsString();
		info.info = textRefix(object.get("info").getAsString());
		info.dataSource = object.get("data_source").getAsInt();
		info.learn = object.get("learn").getAsInt();
		return info;
	}

	public void setDetailInfos(JsonObject object) {
		songPicSmall = fixLink(object.get("songPicSmall").getAsString());
		songPicBig = fixLink(object.get("songPicBig").getAsString());
		songPicRadio = fixLink(object.get("songPicRadio").getAsString());
		songLink = fixLink(object.get("songLink").getAsString());
		format = object.get("format").getAsString();
		rate = object.get("rate").getAsInt();
		size = object.get("size").getAsInt();
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
		if(songPicRadio != null && songPicRadio.length() > 0){
			link = songPicRadio;
		}else if(songPicBig != null && songPicBig.length() > 0){
			link = songPicBig;
		}else if(songPicSmall != null && songPicSmall.length() > 0){
			link = songPicSmall;
		}
		if(link != null){ //http://musicdata.baidu.com/data2/pic/246707883/246707883.jpg@s_0,w_300
			if(songPicRadio == null || songPicRadio.length() <= 0){
				int index = link.indexOf("@s_");
				if(index != -1){
					songPicRadio = link.substring(0, index - 1) + "@s_0,w_300";
				}else{
					songPicRadio = link;
				}
			}
			if(songPicSmall == null || songPicSmall.length() <= 0){
				int index = link.indexOf("@s_");
				if(index != -1){
					songPicSmall = link.substring(0, index - 1) + "@s_0,w_100";
				}else{
					songPicSmall = link;
				}
			}
			if(songPicBig == null || songPicBig.length() <= 0){
				int index = link.indexOf("@s_");
				if(index != -1){
					songPicBig = link.substring(0, index - 1) + "@s_0,w_600";
				}else{
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
