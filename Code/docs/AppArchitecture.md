# App Architecture

## Overview

This document outlines the modular architecture of our Android application. This is the practice of breaking the concept of a monolithic (single module codebase) into loosely coupled modules. The application is structured into multiple modules, each responsible for a distinct aspect of the app’s functionality. This modular approach enhances code reusability, simplifies testing, and facilitates easier maintenance and scalability.

## Modules Description

- **App Module (`app`) :** This module acts as the entry point of the application. Within this module, features of the app are organized into distinct package. Each package would contain all necessary components for that feature, such as Activities, Fragments, ViewModels, etc.

- **Core Module (`core`):** This module contains essential building blocks and utilities required across the application.

## Module Breakdown

Using this modularization strategy, the app has the following modules:

<table>
  <tr>
   <td><strong>Name</strong>
   </td>
   <td><strong>Responsibilities</strong>
   </td>
  </tr>
  <tr>
   <td><code>app</code>
   </td>
   <td>The main entry point for our application. It contains various feature packages, which encapsulate the functionality and UI related to specific features of the app. This also includes navigation. 
   </td>
  </tr>
  <tr>
   <td><code>core:analytics</code>
   </td>
   <td>Responsible for collecting and analyzing user interaction within the app. It integrates analytics providers (e.g. Firebase Analytics, New Relic, etc.) and defines tracking events. 
   </td>
  </tr>
  <tr>
   <td><code>core:common</code>
   </td>
   <td>Contains utility classes, extension functions, and common classes used across modules. This module is designed to reduce code duplication and provide a centralized location for shared functionality. 
   </td>
  </tr>
  <tr>
   <td><code>core:config</code>
   </td>
   <td>Manage the app’s configuration settings, feature flags, and environment-specific parameters. It also handles configurations that can be updated in the backend without requiring an app update (e.g. remote config, A/B testing).
   </td>
  </tr>
  <tr>
   <td><code>core:data</code>
   </td>
   <td>Act as the data layer of the app, abstracting the sources of data the app interacts with. It includes repositories, data sources (remote and local), and the logic to fetch, cache, and synchronize data across the app.
   </td>
  </tr>
  <tr>
   <td><code>core:database</code>
   </td>
   <td>Encapsulates the local database implementation (e.g. Room, SQLite). This module defines the database schema, entities, DAOs, and migration strategies.
   </td>
  </tr>
  <tr>
   <td><code>core:datastore</code>
   </td>
   <td>Manages key-value pairs or typed objects storage, typically used for <code>SharedPreferences</code>, <code>Jetpack DataStore</code> or lightweight local storage.
   </td>
  </tr>
  <tr>
   <td><code>core:designsystem</code>
   </td>
   <td>Defines the app’s UI components, styles, and theming conventions. It ensures a consistent look and feel across the app by providing reusable UI elements.
   </td>
  </tr>
  <tr>
   <td><code>core:domain</code>
   </td>
   <td>Contains the business logic of the app. It defines use cases and act as a bridge between the data component and the view/presentation layer.
   </td>
  </tr>
  <tr>
   <td><code>core:environment</code>
   </td>
   <td>Specifies environment-specific configurations and settings (e.g. <code>development</code>, <code>staging/qa</code>, <code>production</code>). It helps in managing API endpoints, keys, and other parameters that vary between different app environments.
   </td>
  </tr>
  <tr>
   <td><code>core:model</code>
   </td>
   <td>Defines the data models and entities used throughout the app.
   </td>
  </tr>
  <tr>
   <td><code>core:network</code>
   </td>
   <td>Handles all network operations, including API calls, network response parsing, and error handling. It defines service interfaces with backend APIs using tools like Retrofit, OkHttp, Apollo, etc.
   </td>
  </tr>
  <tr>
   <td><code>core:ui</code>
   </td>
   <td>Contains the app’s screens, widgets (UI components), and resources used by app or feature modules.
   </td>
  </tr>
  <tr>
   <td><code>player</code>
   </td>
   <td>Manages media playback within the app. This can include, video, or streaming content, integrating player libraries (e.g. <code>ExoPlayer</code>code>) and handling playback controls and session management. It can also includes analytics stuff related to playback (e.g. Conviva).
   </td>
  </tr>
  <tr>
   <td><code>sync</code>
   </td>
   <td>Responsible for synchronizing data between local storage and remote server. It handles periodic data syncs, background updates, and ensures data consistency across the app.
   </td>
  </tr>
</table>

Modular architecture enables the team to develop, test, and maintain features in isolation, improving the app’s quality and scalability. Each module has a clear responsibility, facilitating a clean separation of concerns and making the codebase more manageable.

## Dependency Graph Overview

The dependency graph within the modular architecture is crucial for understanding how the modules interact with each other and overall app structure. It illustrates the relationships and dependencies among the different modules, ensuring that the app remains highly maintainable and scalable.

<center>
<img src="https://github.com/EconomistDigitalSolutions/mobile-liskov/assets/136551731/ece2c62b-7505-4dcc-ba34-4cba344916eb" width="800px" />
</center>

### Key Consideration

- **Circular Dependencies:** Avoid circular dependencies where two modules depend on each other directly or indirectly through other modules. This can be mitigated by using abstraction layers or interfaces defined in a common or domain module.

- **Isolation:** Keeping modules isolated simplifies testing and development. For instance, the `database` or `network` module should not depend directly on the `app` or `ui` module.

- **Scalability:** As the app grows, some modules might need to be split further or combined, depending on their complexity and functionality.

## Architecture Overview

The architecture is structured around 3 layers (data, domain, and ui).

<center>
<img src="https://github.com/EconomistDigitalSolutions/mobile-liskov/assets/136551731/d055df58-2baf-46ea-810d-342270c7b822" width="400px" />
</center>

- **Data Layer:** Manages raw data (remote and local), including data sources like databases, network calls, preference storage, etc. It’s responsible for data retrieval, storage, and manipulation. It is implemented as an offline-first source of app data (source of truth of all data in the app).

- **Domain Layer:** Contains business logic and use cases. It acts as intermediary between UI and data layers, encapsulating the rules of the app. These use cases are used to simplify and remove duplicate logic from `ViewModels`. They typically combine and transform data from repositories.

- **UI Layer:** Handles presentation and user interaction, displaying data to the user and capturing user inputs.

### Reactive Programming and Unidirectional Data Flow

The architecture adopts a reactive programming model to handle data flow, which is unidirectional from Data layer up to the UI layer. This model facilitates easier [state management](https://developer.android.com/topic/architecture/ui-layer#udf), debugging, and testing by making data flow predictable and easy to follow.

- **Reactive Programming:** Utilizes observable streams for data handling, allowing app to react to changes in data asynchronously and maintain a consistent state across the app.

- **Unidirectional Data Flow:** Data moves in a single direction, from the Data layer, through the Domain layer (_optional_), and finally to the UI layer. This ensures that all state changes are centralized and managed in a coherent manner. The data flow is achieved using streams, implemented using [Kotlin Flows](https://developer.android.com/kotlin/flow).

### Event and Data Flow Diagram

Below is simplified diagram to visualize the architecture and data flow:

<center>
<img src="https://github.com/EconomistDigitalSolutions/mobile-liskov/assets/136551731/d898f353-66ef-4f84-8826-f0b56b641aeb" width="800px" />
</center>

- **UI Layer:** Process the events,  and if data needs to be fetched or submitted, it makes a request to the Domain layer. The UI layer subscribes to observable data streams to reactively update the user interface based on the latest data.

- **Domain Layer:** Receives requests from the UI layer, performs business logic operations, and interacts with the Data layer to retrieve or store data. It then emits the results back to the UI layer. It uses `suspend` functions for executing single operations and return Flow for continuous data streams.

- **Data Layer:** Handles the actual data retrieval from or data persistence to the appropriate sources (APIs, databases). Once the operation is complete, it notifies the Domain layer of the outcome.
