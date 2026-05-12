# Online Store Management System

## Description

This project is an Online Store Management System that provides functionalities for managing products, clients, and store owners. It allows users to add, remove, and sort products, as well as handle client and owner information. The system supports file handling for reading and writing data, sorting and grouping of products, and includes robust exception handling and input validation.

## Table of Contents

- [Features](#features)
- [How to Install and Run the Project](#how-to-install-and-run-the-project)
- [Classes](#classes)
- [Interfaces](#interfaces)
- [Exceptions](#exceptions)

## Features

### Product Management
- Add, remove, and sort products.

### Owner and Client Management
- Handle owner and client information.

### File Handling
- Read and write data to files.

### Sorting and Grouping
- Sort and group products.

### Exception Handling
- Handle exceptions related to product and person attributes.

### Input Validation
- Validate user input.

## How to Install and Run the Project

### Prerequisites

Before running the application, ensure that you have the following installed:

- [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html)
- Git (optional for cloning the repository)

### Installation

1. Clone the repository:

    ```bash
    git clone <repository-url>
    ```

2. Navigate to the project directory:

    ```bash
    cd onlineStore-java
    ```

3. Compile the Java files:

    ```bash
    javac *.java
    ```

### Running the Application

To run the application, use the following commands:

- To create a store from scratch:

    ```bash
    java Main scratch
    ```

- To start the program from a previous state:

    ```bash
    java Main
    ```

    After executing this command, the program will prompt you to enter the file name (including the extension) to load the previous state.

## Classes

### `Client`

- Represents a store client.
- Implements `Person` and `Comparable<Client>` interfaces.
- Manages client information such as name, phone number, and email.
- Provides methods for checking and validating client attributes.

### `Owner`

- Represents the store owner.
- Implements `Person` and `Comparable<Owner>` interfaces.
- Manages owner information such as name, phone number, and email.
- Provides methods for checking and validating owner attributes.

### `Product`

- Represents a generic product.
- Implements `Comparable<Product>` interface.
- Manages product attributes such as name, size, color, quantity, and price.
- Provides methods for checking and validating product attributes.

### `BottomWear`

- Subclass of `Product` representing bottomwear.
- Manages specific attributes for bottomwear products.

### `TopWear`

- Subclass of `Product` representing topwear.
- Manages specific attributes for topwear products.

### `OnlineStore`

- Represents the store.
- Manages a list of products and an owner.
- Provides methods for adding, removing, and sorting products.
- Implements functionality for grouping products by type and counting products.

### `Application`

- Used to test the functionality of the `OnlineStore` class and its methods.
- Implements functions for creating a store, adding products, removing products, and testing various store operations.

### `InputDevice`

- Manages input from the user, file input, and random product generation.
- Implements functionality for reading owner information and products from files.

### `OutputDevice`

- Manages output to the console and file output.
- Implements functionality for printing store information and writing store data to a file.

### `Main`

- Main class for the application that creates a store and runs it.
- Implements functionality to start the program either from scratch or from a previous state.

## Interfaces

### `Person`

- Interface for managing person attributes.
- Includes methods for checking and validating person attributes.

### `ProductHandling`

- Interface for managing product attributes.
- Includes methods for checking and validating product attributes.

## Exceptions

### `InvalidProductTypeException`

- Thrown when an invalid product type is encountered.

### `InvalidProductAttribute`

- Used to signal errors related to invalid product attributes.

### `InvalidProductColor`

- Thrown for invalid product color attributes.

### `InvalidProductPrice`

- Thrown for invalid product price attributes.

### `InvalidProductSize`

- Thrown for invalid product size attributes.

### `InvalidPersonAttribute`

- Used to handle errors related to invalid person attributes.

### `InvalidPersonEmail`

- Thrown for invalid person email attributes.

### `InvalidPersonName`

- Thrown for invalid person name attributes.

### `InvalidPersonPhoneNumber`

- Thrown for invalid person phone number attributes.
