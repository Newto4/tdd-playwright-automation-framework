# 🚀 TDD Playwright Automation Framework

Welcome to the **TDD Playwright Automation Framework**!  
This repository is built to automate UI tests using **Playwright**, **JUnit 5**, and **Maven**, with support for **parallel execution**, **environment configurations**, and **GitLab CI/CD integration**.

---

## 📑 Table of Contents

1. [📌 Project Overview](#-project-overview)
2. [🛠️ Prerequisites](#️-prerequisites)
3. [📦 Installation](#-installation)
4. [📁 Project Structure](#-project-structure)
5. [⚙️ Configuration](#️-configuration)
6. [▶️ Running Tests](#️-running-tests)
7. [📊 Reporting](#-reporting)
8. [🔄 CI/CD Integration](#-cicd-integration)
9. [✅ Best Practices](#-best-practices)
10. [🤝 Contributing](#-contributing)
11. [📝 License](#-license)

---

## 📌 Project Overview

This automation framework enables clean, scalable, and parallel execution of automated test scenarios using:

- **Java 21**
- **Maven 3.9+**
- **Playwright 1.44** (Java client)
- **BDD-style testing with Cucumber**
- **JUnit 5**
- **GitLab CI/CD**

### Key Features

- ✅ Cross-browser testing (Chromium, Firefox, WebKit)
- ✅ Parallel test execution
- ✅ Environment-specific configurations
- ✅ Page Object Model (POM) design pattern
- ✅ Comprehensive reporting
- ✅ CI/CD pipeline integration
- ✅ Data-driven testing support

---

## 🛠️ Prerequisites

Ensure the following are installed and available in your `PATH`:

### Required Software

- **Java JDK 21**
  ```bash
  java -version
  # Expected output: openjdk version "21.x.x"
  ```

- **Maven 3.9+**
  ```bash
  mvn -v
  # Expected output: Apache Maven 3.9.x
  ```

- **Git**
  ```bash
  git --version
  # Expected output: git version 2.x.x
  ```

### Optional (Recommended)
- **Chrome browser** (for local debugging)
- **Visual Studio Code** or **IntelliJ IDEA** (for development)

> **Note:** ChromeDriver is not required as Playwright manages its own browser binaries.

---

## 📦 Installation

### 1. Clone the Repository

```bash
git clone https://gitlab.com/gawaderahul301/bdd-framework.git
cd bdd-automation
```

### 2. Install Maven Dependencies

```bash
mvn clean install
```

### 3. Install Playwright Browsers

Playwright Java will download its driver JAR, but you still need the browser binaries:

```bash
mvn exec:java \
  -Dexec.mainClass=com.microsoft.playwright.CLI \
  -Dexec.args="install"
```

### 4. Verify Installation

```bash
mvn clean compile
```

---

## 📁 Project Structure

```
bdd-automation/
│
├── pom.xml                     # Maven dependencies & plugins
├── junit-platform.properties  # JUnit parallel execution settings
├── fullsuite.sh               # Script to run full test suite
├── .gitlab-ci.yml             # GitLab CI/CD pipeline configuration
├── README.md                  # Project documentation
│
├── src/
│   ├── main/java/com/fast/
│   │   ├── sauceconstant/     # Framework constants and enums
│   │   ├── browserconfig/     # Property/config readers
│   │   ├── pages/             # Page Object Model classes
│   │   └── utils/             # Utility classes (WebDriver, JSON, etc.)
│   │
│   └── test/java/com/fast/
│       ├── hooks/             # JUnit lifecycle hooks (@BeforeAll, @AfterAll)
│       ├── tests/             # Test classes organized by feature/module
│       └── stepDefinitions/   # Cucumber step definitions
│
└── src/test/resources/
    ├── features/              # Cucumber feature files (.feature)
    ├── environment/           # Environment configs (sit.properties, uat.properties)
    ├── testdata/              # JSON test data files
    └── junit-platform.properties  # JUnit configuration
```

---

## ⚙️ Configuration

### Environment Configuration

The framework supports multi-environment testing through property files and Maven CLI parameters.

#### Environment Files Structure
```
src/test/resources/environment/
├── sit.properties      # System Integration Testing
├── uat.properties      # User Acceptance Testing
```

#### Sample Environment Configuration (`sit.properties`)
```properties
# Application URLs
base.uri=https://www.saucedemo.com/
# Default browser
browser=Chrome
# Test User Credentials
username=standard_user
password="secret_sauce
```

#### Using Configuration in Code

```java
import org.fast.configreader.ConfigReaderAgent;

// Load environment-specific properties
Properties props = ConfigReaderAgent.setProperties();
String env = System.getProperty("env", "sit");

// Access configuration values
String baseUrl = props.getProperty("base.url");
String testUser = props.getProperty("username");
```

### Browser Configuration

Supported browsers:
- `chromium` (default)
- `firefox`

### Test Tags

Use Cucumber tags to organize and filter tests:
- `@smoke` - Critical functionality tests
- `@regression` - Full regression suite
- `@login` - Authentication related tests
- `@api` - API testing scenarios
- `@ui` - UI-specific tests

---

## ▶️ Running Tests

### 🧪 Default Test Run

```bash
mvn clean test
```

### 🚀 Custom Test Execution

#### Run tests with specific environment and browser
```bash
mvn test \
  -Denv=uat \
  -Dbrowser=firefox \
  -Dgroups="@smoke"
```

#### Run specific module
```bash
mvn test \
  -Denv=sit \
  -Dtest="login.**.*"
```

#### Run tests with custom tags
```bash
mvn test \
  -Denv=sit \
  -Dgroups="@regression"
```

### 🔁 Parallel Execution

Parallel execution is configured in `junit-platform.properties`:

```properties
# Enable parallel execution
junit.jupiter.execution.parallel.enabled=true
junit.jupiter.execution.parallel.mode.default=concurrent

# Configuration strategy
junit.jupiter.execution.parallel.config.strategy=fixed
junit.jupiter.execution.parallel.config.fixed.parallelism=4

# Class-level parallelism
junit.jupiter.execution.parallel.mode.classes.default=concurrent
```

### 📱 Headless vs Headed Mode

```bash
# Headless mode (default for CI/CD)
mvn test -Dheadless=true

# Headed mode (for debugging)
mvn test -Dheadless=false
```

---

## 📊 Reporting

The framework generates multiple types of reports after test execution:

### 📋 Generated Reports
1. **JUnit Surefire Reports**
   ```
   target/surefire-reports/
   ├── TEST-*.xml
   └── *.txt
   ```

3. **Playwright Traces** (on failure)
   ```
   target/playwright-traces/
   ```

#### CI/CD Report Integration
Reports are automatically published as GitLab Pages and artifacts in the CI/CD pipeline.

---

## 🔄 CI/CD Integration

### GitLab CI/CD Pipeline

The `.gitlab-ci.yml` file defines a comprehensive CI/CD pipeline:

```yaml
stages:
  - build
  - test
  - report
  - deploy

variables:
  MAVEN_OPTS: "-Dmaven.repo.local=${CI_PROJECT_DIR}/.m2/repository"
  MAVEN_CLI_OPTS: "--batch-mode --errors --fail-at-end --show-version"

cache:
  paths:
    - .m2/repository/
    - target/

# Build Stage
build-job:
  stage: build
  image: openjdk:21-slim
  before_script:
    - apt-get update && apt-get install -y maven
  script:
    - echo "🔨 Compiling code..."
    - mvn ${MAVEN_CLI_OPTS} clean compile
  artifacts:
    paths:
      - target/
    expire_in: 1 hour
  rules:
    - if: '$CI_PIPELINE_SOURCE == "push" || $CI_PIPELINE_SOURCE == "merge_request_event"'

# Pre-merge Testing
pre-merge-test:
  stage: test
  image: mcr.microsoft.com/playwright/java:v1.44.0-jammy
  needs: [build-job]
  script:
    - echo "🧪 Running pre-merge tests..."
    - mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
    - mvn test \
        -Denv=sit \
        -Dbrowser=chromium \
        -Dheadless=true \
        -Dgroups="@smoke"
  artifacts:
    when: always
    paths:
      - target/cucumber-reports/
      - target/surefire-reports/
    expire_in: 1 week
    reports:
      junit:
        - target/surefire-reports/TEST-*.xml
  rules:
    - if: '$CI_PIPELINE_SOURCE == "merge_request_event"'

# Post-merge Testing
post-merge-test:
  stage: test
  image: mcr.microsoft.com/playwright/java:v1.44.0-jammy
  needs: [build-job]
  script:
    - echo "🚀 Running post-merge tests..."
    - mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
    - mvn test \
        -Denv=uat \
        -Dbrowser=firefox \
        -Dheadless=true \
        -Dcucumber.filter.tags="@regression"
  artifacts:
    when: always
    paths:
      - target/cucumber-reports/
      - target/surefire-reports/
    expire_in: 1 week
  rules:
    - if: '$CI_PIPELINE_SOURCE == "push" && $CI_COMMIT_BRANCH == "main"'

# Scheduled Full Suite
fullsuite-run-job:
  stage: test
  image: mcr.microsoft.com/playwright/java:v1.44.0-jammy
  tags:
    - rhel8-runner1
    - rhel8-runner2
  script:
    - echo "📋 Running full test suite..."
    - mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"
    - chmod +x fullsuite.sh
    - ./fullsuite.sh
  artifacts:
    when: always
    paths:
      - target/cucumber-reports/
      - target/surefire-reports/
    expire_in: 2 weeks
  rules:
    - if: '$CI_PIPELINE_SOURCE == "schedule"'

# Pages Deployment
pages:
  stage: deploy
  script:
    - mkdir public
    - cp -r target/cucumber-reports/* public/
  artifacts:
    paths:
      - public
  rules:
    - if: '$CI_COMMIT_BRANCH == "main"'
```

### Full Suite Script

The `fullsuite.sh` script for comprehensive testing:

```bash
#!/usr/bin/env bash

echo "🚀 Starting Full Test Suite Execution"
echo "======================================"

# Set error handling
set -e

# Environment variables
ENV=${ENV:-sit}
BROWSER=${BROWSER:-chromium}
PARALLEL_THREADS=${PARALLEL_THREADS:-4}

echo "Environment: $ENV"
echo "Browser: $BROWSER"
echo "Parallel Threads: $PARALLEL_THREADS"
echo "======================================"

# Install Playwright browsers if not already installed
echo "📦 Installing Playwright browsers..."
mvn exec:java -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="install"

# Run the full test suite
echo "🧪 Executing full test suite..."
mvn clean test \
  -Denv=$ENV \
  -Dbrowser=$BROWSER \
  -Dheadless=true \
  -Djunit.jupiter.execution.parallel.config.fixed.parallelism=$PARALLEL_THREADS \
  -Dcucumber.filter.tags="@regression or @smoke"

echo "✅ Full test suite execution completed!"
```

---

## ✅ Best Practices

### 🏗️ Code Organization
- **Page Object Model (POM)**: Abstract UI elements and interactions
- **Single Responsibility**: Each page class handles one page/component
- **DRY Principle**: Reuse common functionality through utility classes

### 🔧 Configuration Management
- **Environment Separation**: Use separate property files for each environment
- **Externalized Data**: Keep test data in JSON files under `testdata/`
- **Parameterized Tests**: Use JUnit's `@ParameterizedTest` for data-driven scenarios

### 🏷️ Test Organization
- **Meaningful Tags**: Use descriptive Cucumber tags (@smoke, @regression, @api)
- **Feature Grouping**: Organize feature files by business functionality
- **Scenario Naming**: Use clear, business-readable scenario names

### 🚀 Performance Optimization
- **Parallel Execution**: Enable parallel test execution for faster feedback
- **Browser Reuse**: Configure browser context reuse where appropriate
- **Smart Waits**: Use Playwright's built-in waiting mechanisms

### 🔍 Debugging and Maintenance
- **Trace on Failure**: Enable Playwright traces for failed tests
- **Screenshot Capture**: Automatically capture screenshots on failures
- **Logging**: Implement structured logging throughout the framework

### 📊 Reporting and Monitoring
- **Comprehensive Reports**: Generate both Cucumber and JUnit reports
- **CI/CD Integration**: Publish reports as pipeline artifacts
- **Trend Analysis**: Track test execution trends over time

---

## 🤝 Contributing

We welcome contributions to improve the framework! Please follow these guidelines:

### 📋 Getting Started
1. **Fork the repository**
2. **Create a feature branch**:
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. **Make your changes**
4. **Add tests** for new functionality
5. **Commit your changes**:
   ```bash
   git commit -m "feat: add new feature description"
   ```
6. **Push to your fork**:
   ```bash
   git push origin feature/your-feature-name
   ```
7. **Create a Merge Request**

### 📝 Commit Message Format
Follow conventional commits:
- `feat:` - New features
- `fix:` - Bug fixes
- `docs:` - Documentation updates
- `test:` - Test additions or modifications
- `refactor:` - Code refactoring
- `chore:` - Maintenance tasks

### 🔍 Code Review Process
- All changes require review from at least one maintainer
- Ensure all tests pass in the CI/CD pipeline
- Update documentation for new features
- Follow existing code style and patterns

### 🐛 Bug Reports
When reporting bugs, please include:
- Steps to reproduce
- Expected vs actual behavior
- Environment details (OS, Java version, browser)
- Screenshots or logs if applicable

---

## 📝 License

This project is licensed under the **MIT License**.

```
MIT License

Copyright (c) 2024 TDD Playwright Automation Framework

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

**Happy Testing! 🎉**