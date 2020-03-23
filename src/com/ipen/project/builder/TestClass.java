package com.ipen.project.builder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestClass {
	public static void main(String args[]) throws IOException {
		modify("resources//application.properties","127.0.0.1","mongo-docker-container");
	}
	
	static void modify(String filePath, String oldString, String newString) throws IOException {
		Path path = Paths.get(filePath);
        Stream <String> lines = Files.lines(path);
        List <String> replaced = lines.map(line -> line.replaceAll(oldString, newString)).collect(Collectors.toList());
        Files.write(path, replaced);
        lines.close();
	}
}
