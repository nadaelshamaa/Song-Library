//NADA ELSHAMAA -> NHE12
//AAMNA FAROOQ -> AF704

package view;

import java.util.Comparator;

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
	
	public static Comparator<SongDetail> NameComparator = new Comparator<SongDetail>() {

		public int compare(SongDetail s1, SongDetail s2) {
		   String songname1 = s1.getName().toUpperCase();
		   String songname2 = s2.getName().toUpperCase();

		   if (songname1.compareTo(songname2)==0) {
			   String artistname1 = s1.getArtist().toUpperCase();
			   String artistname2 = s2.getArtist().toUpperCase();
			   return artistname1.compareTo(artistname2);
		   }
		   //ascending order
		   return songname1.compareTo(songname2);
	    }};


}