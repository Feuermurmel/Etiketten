package ch.feuermurmel.ettiketten;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import ch.feuermurmel.json.JsonMap;
import ch.feuermurmel.json.JsonParseException;

import static ch.feuermurmel.json.Json.map;

public final class Settings {
	public int rowCount = 2;
	public int columnCount = 2;
	public double labelWidth = .05;
	public double labelHeight = .05;
	public double leftOffset = 0;
	public double topOffset = 0;
	public double fontSize = 12;
	
	public String currentAddress = "";
	public int currentRow = 0;
	public int currentColumn = 0;
	
	{
		try {
			JsonMap data = Helpers.loadJsonFile(path);
			
			rowCount = data.get("rowCount").asInt();
			columnCount = data.get("columnCount").asInt();
			labelWidth = data.get("labelWidth").asDouble();
			labelHeight = data.get("labelHeight").asDouble();
			leftOffset = data.get("leftOffset").asDouble();
			topOffset = data.get("topOffset").asDouble();
			currentAddress = data.get("currentAddress").asString();
			currentRow = data.get("currentRow").asInt();
			currentColumn = data.get("currentColumn").asInt();
		} catch (IOException | JsonParseException | RuntimeException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("OverlyBroadCatchBlock")
	public void trySave() {
		JsonMap data = map()
			.put("rowCount", rowCount)
			.put("columnCount", columnCount)
			.put("labelWidth", labelWidth)
			.put("labelHeight", labelHeight)
			.put("leftOffset", leftOffset)
			.put("topOffset", topOffset)
			.put("currentAddress", currentAddress)
			.put("currentRow", currentRow)
			.put("currentColumn", currentColumn);
		
		try {
			Helpers.writeJsonFile(path, data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void rollPosition() {
		currentColumn += 1;
		
		if (currentColumn >= columnCount) {
			currentColumn = 0;
			currentRow += 1;
			
			if (currentRow >= rowCount) {
				currentRow = 0;
			}
		}
	}
	
	private static final Path path = Paths.get(System.getProperty("user.home"), "Library", "Preferences", Settings.class.getPackage().getName() + ".json");
}
