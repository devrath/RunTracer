![png](https://github.com/devrath/RunTracer/assets/1456191/9ec164f0-b929-47a5-ac01-669963a12b2a)

# Documentation

## Dokka

[Dokka](https://github.com/Kotlin/dokka) Dokka is an API documentation engine for Kotlin.

To run the Dokka task in your Gradle project, you need to use the correct task name. The task name can vary based on how you have configured Dokka in your project. Here are the common tasks you might use:

### Single-Module Project
For a single-module project, the most commonly used Dokka task is `dokkaHtml`:

```sh
./gradlew dokkaHtml
```

### Other Dokka Tasks
Depending on your Dokka configuration, there might be other tasks available, such as:
- `dokkaGfm` for generating GitHub Flavored Markdown documentation.
- `dokkaJekyll` for generating Jekyll-compatible documentation.

### Finding Available Dokka Tasks
To list all available tasks in your project, you can use the following command:

```sh
./gradlew tasks
```
