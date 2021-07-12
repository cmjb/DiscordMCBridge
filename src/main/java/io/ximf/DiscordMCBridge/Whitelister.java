package io.ximf.DiscordMCBridge;

import org.bukkit.Bukkit;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

public class Whitelister {

    private final String playerNameRegex = "/^\\w{3,16}$/i";
    private final Pattern UUID_Pattern = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");

    public Whitelister() {

    }

    public String getPlayerUUID(String playerName) throws Exception {
        JSONParser parser = new JSONParser();
        URL apiUrl = new URL("https://api.mojang.com/users/profiles/minecraft/" + playerName);
        URLConnection apiConnection = apiUrl.openConnection();
        BufferedReader jsonIn = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));
        String inputLine;

        while((inputLine = jsonIn.readLine()) != null) {
            JSONObject player = (JSONObject) parser.parse(inputLine);
            String playerUUID = (String) player.get("id");

            return UUID_Pattern.matcher(playerUUID.replace("-", "")).replaceAll("$1-$2-$3-$4-$5");
        }

        throw new Exception("Can't find user.");
    }

    public boolean whitelistPlayer(String playerName) throws Exception {
        String playerNameFormatted = playerName.replaceAll(playerNameRegex, "");
        String playerUUID = this.getPlayerUUID(playerNameFormatted);

        JSONParser parser = new JSONParser();

        JSONArray whitelistArray = (JSONArray) parser.parse(new FileReader("whitelist.json"));

        for(Object whitelistObject : whitelistArray) {
            JSONObject whitelistPerson = (JSONObject) whitelistObject;
            String name = (String) whitelistPerson.get("name");
            if (name.equals(playerNameFormatted)) {
                return false;
            }
        }

        JSONObject playerJsonObject = new JSONObject();
        playerJsonObject.put("name", playerNameFormatted);
        playerJsonObject.put("uuid", playerUUID.toString());


        whitelistArray.add(playerJsonObject);

        FileWriter file = new FileWriter("whitelist.json");
        file.write(whitelistArray.toJSONString());
        file.flush();
        file.close();

        Bukkit.reloadWhitelist();
        return true;
    }
}
