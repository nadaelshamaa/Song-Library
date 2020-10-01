package view;

public class SongDetail{
	private String name;
	private String artist;
	private String album;
	private String year;

	public SongDetail(String name, String artist, String album, String year) {
		this.name = name;
		this.artist = artist;
		this.album = album;
		this.year = year;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getArtist() {
		return artist;
	}
	public void setArtist(String artist) {
		this.artist = artist;
	}

	public String getAlbum() {
		return album;
	}
	public void setAlbum(String album) {
		this.album = album;
	}

	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}

	public String toString() {
		return ("OBJECT SongName:"+this.getName()+
				" Artist: "+ this.getArtist() +
				" Album: "+ this.getAlbum() +
				" Year : " + this.getYear());
	}


}