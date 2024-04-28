# Translator Web App - Backend

This is the backend component of a Translator web application built with Spring Boot. It provides translation functionalities through a REST API.

## Technologies

- Java 17
- Spring Boot
- RestTemplate
- Jackson ObjectMapper (for JSON parsing)

## Running the Application

- Run the main method, which is inside 'TranslateApplication' class

## API Endpoints

- /translate: This endpoint translates a text from one language to another. It accepts a POST request with a JSON body containing the source and target languages, and the text to be translated.
- /detect: This endpoint detects the source language of a text. It accepts a POST request with a JSON body containing the text to be detected.

## Docker Support

A Dockerfile is included in the project. You can build a Docker image using docker build -t translator-backend . (replace . with the path to your project directory if it's different).
Run the Docker container using docker run -p 8080:8080 translator-backend. This will expose the backend service on port 8080.
