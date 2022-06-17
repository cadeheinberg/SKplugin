### The most important class is [Fighter.java](https://github.com/cadeheinberg/SKplugin/blob/main/SevenKitsPlugin/src/me/cade/PluginSK/Fighter.java).
```
It stores all of the players stats and holds key methods that are used during the game.
As the player plays progresses, their respective Fighter instance is updated accordingly.
```

### A Fighter instance is created for every player that joins the server in [PlayerJoinListener.java](https://github.com/cadeheinberg/SKplugin/blob/main/SevenKitsPlugin/src/me/cade/PluginSK/PlayerJoin/PlayerJoinListener.java).
```
From there you can follow what happens to a player from the moment
they join the server to the moment they leave.
```

```
When the player leaves the server, to save the their stats
the data held by the Fighter instance is uploaded to the MySQL server.
```

```
When the player rejoins, the data is then downloaded from the MySQL server 
and then injected into the newly made Fighter instance for the player!
```

## Email me: cadeheinberg@outlook.com
