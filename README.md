# Eden

[![Java CI with Gradle](https://github.com/lgs-games/eden/actions/workflows/gradle.yml/badge.svg)](https://github.com/lgs-games/eden/actions/workflows/gradle.yml)
[![CodeFactor](https://www.codefactor.io/repository/github/lgs-games/eden/badge/main)](https://www.codefactor.io/repository/github/lgs-games/eden/overview/main)
[![Total alerts](https://img.shields.io/lgtm/alerts/g/lgs-games/eden.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/lgs-games/eden/alerts/)
[![Language grade: Java](https://img.shields.io/lgtm/grade/java/g/lgs-games/eden.svg?logo=lgtm&logoWidth=18)](https://lgtm.com/projects/g/lgs-games/eden/context:java)
![Known Vulnerabilities](https://snyk.io/test/github/lgs-games/eden/badge.svg)

[![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=lgs-games_eden&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=lgs-games_eden)
[![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=lgs-games_eden&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=lgs-games_eden)
[![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=lgs-games_eden&metric=security_rating)](https://sonarcloud.io/dashboard?id=lgs-games_eden)
[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=lgs-games_eden&metric=code_smells)](https://sonarcloud.io/dashboard?id=lgs-games_eden)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=lgs-games_eden&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=lgs-games_eden)
[![Bugs](https://sonarcloud.io/api/project_badges/measure?project=lgs-games_eden&metric=bugs)](https://sonarcloud.io/dashboard?id=lgs-games_eden)

Eden is the 2nd version of the **Legendary Games Studios** game launcher.
You can

* create an account, and login
* view our games
    * download, install, update and play
    * see the number of players in this game, your time played
    and number of achievements
* you got a marketplace to look for games
* you can see your profile (recent games, reputation, ...)
  and change your avatar / description / ...
* you can look for other players, send them a friend request, cancel
  it or accept it
* you can tchat with your friends in a real-time tchat
* when logging, you can see your notifications (friends request
  received, new messages)

We are only supporting Windows / English for now.

## Technologies

The language is **Java 16** with **JavaFX** for the views.
We are using Gradle to build the project and manage our dependencies.

We are using our API **Nexus** to interact with the server.

* You can download the latest version of Eden [here](https://lgs-games.com/en/eden). \
* The git repository is available [there](https://github.com/lgs-games/eden). \
* finally, we have a [Trello](https://trello.com/b/mc5OKuQH/eden) to stay organized.

## Installation

You may use ``gradle build`` to build the project, then
``gradle run``.

## Licence

Licensed under Apache-2.0.