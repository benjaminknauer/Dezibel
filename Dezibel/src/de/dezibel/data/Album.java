package de.dezibel.data;

public class Album extends Playlist {

	private String coverPath;

	private static ImageLoader imageLoader;

	public Album(Medium medium, String titel) {

	}

	public ErrorCode uploadCover(String path) {
		return null;
	}

	public Image getCover() {
		return null;
	}

}
