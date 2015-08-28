package ch.feuermurmel.ettiketten;

import java.io.IOException;
import java.io.Writer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

import ch.feuermurmel.json.Json;
import ch.feuermurmel.json.JsonMap;
import ch.feuermurmel.json.JsonParseException;

class Helpers {
	private Helpers() {
	}
	
	static JsonMap loadJsonFile(Path path) throws IOException, JsonParseException {
		return Json.load(path.toFile()).asMap();
	}
	
	static void writeJsonFile(Path path, JsonMap data) throws IOException {
		Path temporaryPath = path.resolveSibling(path.getFileName() + "~");
		
		Files.createDirectories(temporaryPath.getParent());
		
		try (FileChannel channel = FileChannel.open(temporaryPath, StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
			Writer writer = Channels.newWriter(channel, "UTF-8");
			
			data.toString(writer);
			
			writer.flush();
		}
		
		Files.move(temporaryPath, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
	}
}
