package org.foomaa.jvchat.tools;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class JvTools
{
    public static String getProfileFromBuildDir() throws IOException, URISyntaxException {
        Path buildPath = Paths.get(Objects.requireNonNull(
                JvTools.class.getResource("/")).toURI());
        String dirToProfile = buildPath.toString().substring(0,
                buildPath.toString().lastIndexOf("/classes")) + "/profile/profile.txt";
        List<String> readingFile = Files.readAllLines(Path.of(dirToProfile),
                StandardCharsets.UTF_8);

        String flag = "target=";
        String profile = "";
        for (int i = 0; i < readingFile.size(); i++) {
            String element = readingFile.get(i);
            profile = element.substring(element.lastIndexOf(flag) + flag.length());
        }
        System.out.println(profile);
        return profile;
    }
}
