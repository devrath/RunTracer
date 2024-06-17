# Instructions

- [Setup Instructions](#setup-instructions)

## Setup Instructions

### 1. Android Studio settings

#### Code Style/Formatter

Make sure you apply the project-specific code style settings stored in version control from **Settings > Code Style >
Kotlin > Scheme**:

![Code Style Settings](https://github.com/devrath/RunTracer/blob/main/Code/docs/images/kotlin-code-style.png)

#### Suggested commit settings

When using the IDE to commit changes to VCS, it's recommended to use the following settings:

![Commit settings](https://github.com/devrath/RunTracer/blob/main/Code/docs/images/suggested-commit-settings.png)

**Important:** When using the terminal or another git management tool, please manually apply
formatting to all modified files with **Cmd+Opt+L** on Mac/Linux or **Ctrl+Alt+L** on Windows.

#### Optimize imports

In order to avoid conflicts or unused imports, apply the *"Optimize imports on the fly"* settings in
**Settings > Editor > General > Auto Import**
(it can also be done manually with **Cmd+Opt+O** on Mac/Linux or **Ctrl+Alt+O** on Windows):

![Optimize Imports](https://github.com/devrath/RunTracer/blob/main/Code/docs/images/optimize-imports.png)
