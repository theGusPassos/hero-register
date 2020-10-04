# Hero Project

A simple API made with clojure to create and query heroes.

## Prerequisites

- [jdk](https://www.oracle.com/technetwork/pt/java/javase/downloads/index.html) >= 1.8
- [leiningen](https://leiningen.org/) >= 2.9.x

## Getting Started

### Running through REPL for development

1. start a new REPL `lein repl`
2. use the [user.clj](\dev\user.clj) `go` function to start the server

### Running server

1. `lein run` or for a dev server: `lein run-dev`
2. go to [localhost:8080](localhost:8080)

### Compiling and Running

1. `lein uberjar` will compile the code into a .jar file in `/target/uberjar/`
2. run the .jar file with java -jar {file_path}

## Available endpoints

- ensure the api is running
  `curl -G localhost:8080`
- create a new hero with `curl -H "Content-Type: application/json" -X POST localhost:8080/hero/ -d "{\"Name\":\"hero's name\"}"`
- query the created hero with `curl -G localhost:8080/hero/{hero_id}`
- get all created heroes with `curl -G localhost:8080/heroes/`

## Contributing

1. Fork it
2. Create your feature branch
3. Push to the branch
4. Create a new pull request

## License

[![License](https://img.shields.io/badge/License-Apache%202.0-yellowgreen.svg)](https://opensource.org/licenses/Apache-2.0)

- **[Apache License](http://www.apache.org/licenses/)**
