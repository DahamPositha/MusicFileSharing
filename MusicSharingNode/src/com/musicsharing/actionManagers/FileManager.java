package com.musicsharing.actionManagers;

import java.util.List;

import com.musicsharing.dtos.TableRecord;

public interface FileManager {
	
	public void initiateFilesOfTheNode();
	
	public List<String> getMatchingFiles(String prefix);
	
}
