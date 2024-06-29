There are 2 main challenges

## `Dependency Management`
* Say now we have a dependency say `material-dependency` added app module and also the same dependency is mentioned in 20 other modules.
* So there comes a scenario where we want to bump up the version. Here it becomes hard to go into each module and increase the version.
* Someone might miss some and might need to errors that are hard to spot.

## `Config Management`
* Defining a custom gradle plugin for `android module` and `library module`
* So it is easy to apply from a single place.
* This would reduce the duplication of code across the modules and management becomes easier.

## _**`Solution`**_
Have a single source of truth for dependency management so it is easy to manage the dependencies from one place.