# CLI based Email Client Java Application

This Java application demonstrates the use of the **Factory Method** design pattern for creating different types of recipients. It also employs the **Serialization** pattern for persisting email objects.

## Functionality

This application allows you to manage recipients and send emails. It provides the following functionalities:

### Adding a New Recipient

To add a new recipient, follow the format below:

- Official: name,email,position
- Office_friend: name,email,position,yyyy/MM/dd
- Personal: name,nick-name,email,yyyy/MM/dd

### Sending an Email

Input format: email, subject, content

### Printing Recipients with Birthdays

Input format: yyyy/MM/dd (e.g., 2018/09/17)

### Printing Details of Sent Emails

Input format: yyyy/MM/dd (e.g., 2018/09/17)

### Printing Total Number of Recipients

To view the total number of recipient objects in the application, select option 5.

### Exiting the Email Client

To exit the Email Client, select option 6.

## Design Patterns

### Factory Method Pattern

The Factory Method pattern is used in the `MakeRecipient` class to create different types of recipients (Official Employee, Office Friend, Personal Friend) based on user input.

### Serialization Pattern

The Serialization pattern is employed to persist email objects. Serialized email objects are stored in `emailDetails.ser`.

## Dependencies

This application uses Java's built-in libraries and does not require any external dependencies.

## Configuration

Update the sender's email and password in the `SendEmail` class to use your own email account for sending messages.

```java
final String from = "your_email@gmail.com";
final String password = "your_password";
```

## Automatic Birthday Emails

This application automatically sends birthday emails on the recipient's birthday. This functionality is triggered when you run the application on the recipient's birthday.

## Author

- Kryko.

---

**Note:** This README provides an overview of the Java application. Make sure to customize it with specific details relevant to your project.
