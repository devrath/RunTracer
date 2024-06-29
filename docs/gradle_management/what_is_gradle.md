* When we add code in individual files in the app, It does not create an executable application
* So creating the executable app by organizing the files like a `task runner` that makes the files run in the right order and with the right input to get the executable app.

## `Dependency management`
* It has to directly look into all the `dependencies` mentioned in your project and download it into the project if not downloaded. It looks into the remote containers that we have defined in the project like `maven`, and `j-center`.
* Also if the `dependencies` you are including contain other dependencies, They also will be downloaded by the gradle.
* If the module is linked to the project, It has to get the dependencies from the module.

## `Compilation management`
* Gradle advices all the compilers what to compile.

## `Runs gradle plugins`
* Example if you add a dependency that does the code-generation. That involves a plugin that takes care of code generation. The gradle also manages the plugins by running in the right order.

## `Build optimization`
* Gradle also has the responsibility of optimizing the build generation by running and following the compilation strategy.
* It will run the modules in the right order and in parallel as needed.

## `Build configuration management`
* It goes and checks the configs for each grade defined in modules like `compile-SDK`, `namespaces` etc ..., Defining the `BuildTypes` that involves `code-obfuscation` etc... 