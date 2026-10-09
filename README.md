# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```


## Phase 2 Diagram Link:
Here is the link to the diagram I made for Phase 2:
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2AMQALADMABwATG4gMP7I9gAWYDoIPoYASij2SKoWckgQaJiIqKQAtAB85JQ0UABcMADaAAoA8mQAKgC6MAD0PgZQADpoAN4ARP2UaMAAtihjtWMwYwA0y7jqAO7QHAtLq8soM8BICHvLAL6YwjUwFazsXJT145NQ03PnB2MbqttQu0WyzWYyOJzOQLGVzYnG4sHuN1E9SgmWyYEoAAoMlkcpQMgBHVI5ACU12qojulVk8iUKnU9XsKDAAFUBhi3h8UKTqYplGpVJSjDpagAxJCcGCsyg8mA6SwwDmzMQ6FHAADWkoGME2SDA8QVA05MGACFVHHlKAAHmiNDzafy7gjySp6lKoDyySIVI7KjdnjAFKaUMBze11egAKKWlTYAgFT23Ur3YrmeqBJzBYbjObqYCMhbLCNQbx1A1TJXGoMh+XyNXoKFmTiYO189Q+qpelD1NA+BAIBMU+4tumqWogVXot3sgY87nae1t+7GWoKDgcTXS7QD71D+et0fj4PohQ+PUY4Cn+Kz5t7keC5er9cnvUexE7+4wp6l7FovFqXtYJ+cLtn6pavIaSpLPU+wgheertBAdZoFByyXAmlDtimGD1OEThOFmEwQZ8MDQcCyxwfECFISh+xXOgHCmF4vgBNA7CMjEIpwBG0hwAoMAADIQFkhRYcwTrUP6zRtF0vQGOo+RoFmipzGsvz-BwVygZSQH+iMykoKp+h-Ds0KPHCFRvl2MAIEJ4oYoJwkEkSYCkpZFTDvyDJMlO+lzjS+4VMuMBihKboynKZbvEqmD+O08SGAAZhAfYQDqDgwNkMBzFIkBGMGCDarq+rsMaYDojMsYCrlNmOOKMC5SVoAgL4pB6nIRgQGoMBoBAzBWmiLi1JgKrBhqbrdb1MBJT4TYeeoSblJZ0FjNuhgLR2NSutMl7QEgABeKAcFGMZxoUWnraJaZOAAjAROaqHm8zQUWJb1AAchAkUwP1OQCjqeqRZyDb0cNqoagAkmgzXFig4AwE0RnqatPqgVtFG7QdR3RigsYKeh8IVJdMDprdoygsW0AFmML3QPUCNbDsHVdT1zDHGAID6v9+r6cDs23vyC1LRu7pbm5i3OlZNRIAl8pqTsMXgzAzW9uuIaq7KYMZWg9XxUr+iGG1zB6oYcsAkzqhoAA5Kzcgc2sOinjA4OWzQlZmvKauHfV2wa6NVWfXqmUGOAClu8G5ouMj7n8-SSsTigz7xOel7XnNqiBcKAZrgGKeixL7Y6aWDnihkqgAZghcgdUumEeWnykd8FFUfWDeadXUALUTuH4WTPMN7Bl7N8hreNgxnjeH4-heCg6AxHEiTT7PDm+FgomCqjjTSBG-ERu0EbdD0cmqApwxN4h6D49pZn+mfSEV9f8Li529Q1SvyfwefaCuRL0f+SOXlgETu-Sin8-K8hHBneoIUny53kLKGsn8FYJW1IYEA7BrZTXFOuY29VP7dRnhwL21UhIwBXkNEa6oYBDwmswaafM-4C19BLbsvZ+yWUpBvZk209QY0OsdHGp18aE2QKmYmN07r8ket8GmpY4C+AQOuFmmCJS33QC4XmDE04o2YTnF8edOyUjTvUQh3BjyXmAUPMBC507lCChkGYEBXaJ1fPnBahcXimVhBhQW7cPGX07iI7CMBu5Zjok2cezF-AonXP4bA4oNT8TRDAAA4kqDQa9xKbUaMk3eB97BKlPoPRB51yjuONEUu+hcLI6OQDkVJOYLGgKjuUIxMBGSAPMaor+N4GGLkqEFaBuirzaHgbgu+-hwbIONiiDKAoSqUBLIVAGJVxRUBNEgbBeCdAoHFI4LINA0DkM1pDNZyB1xDx6eAh01Tn7dVYc0zh3D4i8PXPw3G8YSnwECWAK6pNsySPzCCGR9QTnrPOXg-Z9YwmaJjgKHxtznH6KoIOFpsKX5onqWoDEVj9z3kzhkCwqBXYmgKpij0Wj1pC0STkAAPGS7Q5Qo4fgfvUalYBMWqFLuXSulLfEwHGPknMBYGguFFd0NuEkO7CJKD84JeECKCrUMK0VLhxWj0YhPAIHAADsbgnAoCcDECMwQ4BcQAGzwHjikisRRvnrz5VJDoeSClPKHlmRVb0lQSpqBUMpXS1gjA9V6++XiO5P2RS6OOR4UCYoxHAeOmKnJqBcqtX+VzY7tKAV0nFd5IHBSwUM8KCDxmTJ1pQQwmUVloFORssZ6AjmjSdtWsFVDEEUpuRGqyPY+xMvDf6Lh6MoD7T4djd5Z126YW+b8iRuZ8zPQpqWUFZzW3URgNCy51iq4ItgcAZprTDxyFjYq1OsK82QwGMwel8gN0BUyUiNpSpwbSF7Q8UN9R43RsTf+BAgEH7wqyQKx90gDiKqfd6yg0rREhLJqB4DaxYProiZPSwKAUqbDnkgBIYAUNoZgAAKQgHVTFMRkigDVLamV9rJX1GaMyGSPRFWFI-khLM2AEDABQ1AORNkoDwaA+Bx+fqKnoADWxjjlBuPQD43MMDIavwdvvQAK0I2gWNBHxRfsJMm7+nY03WIAVm4T3SKW2MzoMxFcCIqQBLVM+KMzK3a1WS26z9bQajRvbmhTkbu1sNcR2-tTyXlvMEedZMU6xF-LGPdKR87XpNpreC1d6607-vvRZ3dYtWntKPUBnN1zTNQILVe4Aoyn1IJQXrNASiEoFpwYqrWMAtM5AUg2yhxGPrMBFC1GFvS4V3u8-c9hmT-QACEQxJpyMFvGoXKhE3TD3f5s75hAoXfUYjysFE0NlIlbraxoAZWYBwTqAolE-TAOo9dEzbOoOSvtzYhg8AGysLs3BMBlN1TqxWGahDYDxH0GHas23Z61lcxQjUknYCBndlQ8MhR23hvvT51N-nSxQ-DpYMMSEpsfInRdcL6ZMxk2i3Owsq34C3d+-9k00PtnA9h1NfbgcBSKou+qiZOt4k63aqoZ5KttvfR6j4BI30F1KwgIQgUSVYA9U2JHIbqLev1D8FodEsbldyBjUqE9vW8WumwCrwwxWX1lJGJ4r8W6AMCZ9F3eVoxENMUnl4DjGGsNO-lIgYMsBgDYDY4QPIBQYAUfMFRrJDQt47z3gfYwQjSksuWCtHlTDbkgG4HgbFqaqRoqVqnqAbpVDp5M0Keo0hUNMge32SKGh5f7pz0AvLfSi8yFL+iSsBV0t7qzynz3HKC+wt1030x5fSVa6RYOX1cfTdyeAv+3SVuAkypwrbyf7O0FWyNvETnEBkHsAtAuqqG-JczRDgHjb650DKwwBhQ2Yu+fbJoYdNYqhPrOxmFt+wKnHC5XFCnnwhDjRoHlDGDBzGAFDsxQEjhBiAA

I still don't know why these links are so long. Is it because the website directly encodes the diagram in the URL? If so, surely there's a better way to do that, right?
