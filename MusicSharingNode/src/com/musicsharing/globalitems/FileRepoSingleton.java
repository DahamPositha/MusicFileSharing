package com.musicsharing.globalitems;

import java.util.ArrayList;
import java.util.List;

public class FileRepoSingleton {

	static FileRepoSingleton fileRepo;
	List<String> musicFiles;

	private FileRepoSingleton() {
		 musicFiles=new ArrayList<String>();
	}

	public static FileRepoSingleton getFileRepoSingleton() {
		if (fileRepo == null) {
			fileRepo = new FileRepoSingleton();

		}
		return fileRepo;

	}

	public List<String> getMusicFiles() {
		return musicFiles;
	}

	public void setMusicFiles(List<String> musicFiles) {
		this.musicFiles = musicFiles;
	}

}
