//NADA ELSHAMAA -> NHE12
//AAMNA FAROOQ -> AF704

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
		String output = String.format("%-70s %-70s", this.getName(), this.getArtist());
		return output;
//		return (this.getName()+ '\t'+ '\t'+ '\t'+ '\t'+ '\t'+ '\t'+'\t'
//				+ this.getArtist());
	}


}