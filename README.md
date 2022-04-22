# DiscordMCBridge [![Java CI with Maven](https://github.com/ximf-moe/DiscordMCBridge/actions/workflows/maven.yml/badge.svg)](https://github.com/ximf-moe/DiscordMCBridge/actions/workflows/maven.yml)

Bridge your Minecraft chat with your discord server.

Compatible with Spigot.

Latest version : `1.3.1`

Usage
---
0. Setup a bot via Discord Developers, create it as a bot and add it to your server of choice. Copy the token and channel id.
1. Insert the jar file from Releases into your `./plugins/` directory in your Bukkit or Spigot server.
2. Add a `./plugins/DiscordMCBridge/` folder and inside it create a file called `settings.yml` with the following inside:
```yml
discord_channel: <channel without quotes>
discord_token: <token with quotes>
minecraft_discord_announce_name: <name with quotes>
discord_bot_name: <bot name from discord developer control panel>
allow_self_whitelist: true
announce_advancement: false
```
3. Start/Reload your server and whitelist yourself.
4. Chat in your server, and in your discord to see if your text appears.
5. Done!
