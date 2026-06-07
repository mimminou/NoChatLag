# NoChatLag

THIS IS A FORK TO PORT THIS FIX TO 1.21.11


(This bug was somewhat reintroduced in this version, maybe 1.21.10 too but have not tested there)


While I'm a software developer, I rarely work with Java and even less so the minecraft/fabric api, therefore a large portion of the code changes that were made here are made with AI assistance.


A couple of days ago, something changed in Mojang's blocked users API, causing the client to not properly load the list of blocked users on start. Because of this, the client will attempt to fetch the blocklist every two minutes when the chat is being rendered. This loading is done on the same thread as the rendering, causing the game to freeze until Mojang's API returns a response. Since the requests takes a while to complete, this will cause a noticeable lag spike.

This issue is being tracked in Mojira issue [WEB-5587](https://bugs.mojang.com/browse/WEB-5587).

You are currently viewing the 1.17/1.18 branch. Check out the ver/1.16 branch for the 1.16 version.
