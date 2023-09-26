//200546G
package com.company;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


interface Recipient {
    String getName();
    String getEmail();
}





class OfficeFriend implements Recipient {
    private String name;
    private String email;
    private String position;
    private String birthday;
    public OfficeFriend(String name, String email, String position, String birthday) {
        this.name = name;
        this.email = email;
        this.position = position;
        this.birthday = birthday;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getEmail() {
        return email;
    }
    public String getPosition() {
        return position;
    }
    public String getBirthday() {
        return birthday;
    }
}
class OfficialEmployee implements Recipient {
    private String name;
    private String email;
    private String position;
    public OfficialEmployee(String name, String email, String position) {
        this.name = name;
        this.email = email;
        this.position = position;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getEmail() {
        return email;
    }
    public String getPosition() {
        return position;
    }
}
class PersonalFriend implements Recipient {
    private String name;
    private String nickname;
    private String email;
    private String birthday;
    public PersonalFriend(String name, String nickname, String email, String birthday) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.birthday = birthday;
    }
    @Override
    public String getName() {
        return name;
    }
    public String getNickname() {
        return nickname;
    }
    @Override
    public String getEmail() {
        return email;
    }
    public String getBirthday() {
        return birthday;
    }
}
class MakeRecipient {
    //integer variable to keep track of number of recipients in the client
    public static int recipients = 0;
    /**
     * Create a Recipient object given the details and return it
     * @param details details contains the details of the recipient
     * @return a Recipient object
     */
    public Recipient make(String[] details) {
        //increment the number of recipients
        recipients++;
        String type = details[0];
        System.out.println(type.equals("Official"));
        if(type.equals("Official")) {
            //details[1] -> name
            //details[2] -> email
            //details[3] -> position
            System.out.println("LOL");
            return new OfficialEmployee(details[1], details[2], details[3]);
        }
        else if (type.equals("Office_friend")) {
            //details[1] -> name
            //details[2] -> email
            //details[3] -> position
            //details[4] -> birthday
            return new OfficeFriend(details[1], details[2], details[3], details[4]);
        }
        else {
            //details[1] -> name
            //details[2] -> nickname
            //details[3] -> email
            //details[4] -> birthday

            return new PersonalFriend(details[1], details[2], details[3], details[4]);
        }
    }
}
class Email implements Serializable {
    private String email;
    private String subject;
    private String content;
    private String date;
    public Email(String email, String subject, String content, String date) {
        this.email = email;
        this.subject = subject;
        this.content = content;
        this.date = date;
    }
    public String getEmail() {
        return email;
    }
    public String getSubject() {
        return subject;
    }
    public String getContent() {
        return content;
    }
    public String getDate() {
        return date;
    }
}
class SendEmail {
    //Sender's email and password
    final String from = "minindurupasinghe@gmail.com";
    final String password = "vhjfymqoujroifsf";
    //Send the email given the receiver's address ,subject and the content
    public void send (String to, String subject, String content) {
        Properties set = new Properties();
        //Set values to the property
        set.put("mail.smtp.starttls.enable", "true");
        set.put("mail.smtp.auth", "true");
        set.put("mail.smtp.host", "smtp.gmail.com");
        set.put("mail.smtp.port", "587");
        Session session = Session.getInstance(set,new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }});
        try {
            Message email = new MimeMessage(session);
            //Sender's email address
            email.setFrom(new InternetAddress(from));
            //Receiver's email address
            email.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            //Subject and the content of the email
            email.setSubject(subject);
            email.setText(content);
            //Sending the email
            Transport.send(email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
/**
 * Email finder class contains 1 method.
 */
class EmailFinder {
    /**
     * Find out the emails sent on given date and print out the recipient email and subject of the email
     * @param date the date
     * @param emails the list of all sent emails
     */
    public void emailDayDetails(String date, ArrayList<Email> emails) {
        for(Email email : emails) {
            if(email.getDate().equals(date)){
                System.out.println(email.getSubject());
                System.out.println(email.getEmail());
                System.out.println();
            }
        }
    }
}
/**
 * the BirthdayFinder class contains two methods
 */
class BirthdayFinder {
    /**
     * Find out who have birthday on the day given by stringDate and print out their names
     * @param stringDate the date
     * @param recipientList list of recipient objects
     */
    public void birthdayDetails(String stringDate, ArrayList<Recipient> recipientList) {
        //To consider the birthday we only need to consider the month and the day
        String date = stringDate.substring(4);
        for(Recipient recipient : recipientList) {
            //Since we only send birthday wishes to PersonalFriends and OfficeFriends
            //we do not need to consider Official Employee
            if(recipient instanceof OfficeFriend) {
                OfficeFriend friend = (OfficeFriend)recipient;
                String birthday = friend.getBirthday().substring(4);
                if(date.equals(birthday)) {
                    System.out.println(friend.getName());
                }
            }
            else if(recipient instanceof PersonalFriend) {
                PersonalFriend friend = (PersonalFriend)recipient;
                String birthday = friend.getBirthday().substring(4);
                if(date.equals(birthday)) {
                    System.out.println(friend.getName());
                }
            }
        }
    }
    /**
     * Find out who have birthday on the day given by stringDate and send emails to them wishing
     birthday
     * and store email objects in emailsList
     * @param stringDate the date
     * @param recipientList list of recipient objects
     * @param emailsList list of to store the details of the emails sent as objects
     */
    public void birthdayEmails(String stringDate, ArrayList<Recipient> recipientList, ArrayList<Email> emailsList) {
        //To consider the birthday we only need to consider the month and the day
        String date = stringDate.substring(4);
        String subject = "Happy Birthday Wish";
        SendEmail sendEmail = new SendEmail();
        for(Recipient recipient : recipientList) {
            //Since we only send birthday wishes to PersonalFriends and OfficeFriends
            // we do not need to consider OfficialEmployee
            if(recipient instanceof OfficeFriend) {
                OfficeFriend friend = (OfficeFriend)recipient;
                String birthday = friend.getBirthday().substring(4);
                if(date.equals(birthday)) {
                    String content = "Wish you a Happy Birthday. Minindu";
                    Email email = new Email(friend.getEmail(), subject, content, stringDate);
                    emailsList.add(email);
                    sendEmail.send(friend.getEmail(), subject, content);
                }
            }
            else if(recipient instanceof PersonalFriend) {
                PersonalFriend friend = (PersonalFriend) recipient;
                String birthday = friend.getBirthday().substring(4);
                if(date.equals(birthday)) {
                    String content = "Hugs and love on your birthday. Minindu";
                    Email email = new Email(friend.getEmail(), subject, content, stringDate);
                    emailsList.add(email);
                    sendEmail.send(friend.getEmail(), subject, content);
                }
            }
        }
    }
}
class TextTraversal {
    //the file we read the data from
    final String filename = "clientList.txt";
    /**
     * Traverse through the text file and retrieve its data and store it in the recipientList
     * @param recipientList list for store the recipients that would get created in the process
     */
    public void traverse(ArrayList<Recipient> recipientList) {
        try {
            FileReader reader = new FileReader(filename);
            BufferedReader bufferedReader = new BufferedReader(reader);
            MakeRecipient makeRecipient = new MakeRecipient();
            String line = "";
            while (true) {
                try {
                    line = bufferedReader.readLine();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                if(line == null)
                    break;
                String[] details = line.split("[:,]");
                System.out.println(details[0]);
                recipientList.add(makeRecipient.make(details));
            }
            try{
                bufferedReader.close();
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            //This happens when there is no file to traverse
            //Thus this error throws
            //But if an item to be saved is given as input
            //If this file is not created it will get created as new file
        }
    }
}
class Serialization {
    //the file we serialize objects into
    final String filename = "emailDetails.ser";
    /**
     * serialize the Email objects that contain in emailsList to the given file
     * @param emailsList contains the objects that need to serialize
     */
    public void serialize(ArrayList<Email> emailsList) {
        try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(file);
            for(Email email : emailsList)
                out.writeObject(email);
            out.close();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class Deserialization {
    //file that we deserialize objects from
    final String filename = "emailDetails.ser";
    /**
     * deserialize the objects one by one and store them in emails list
     * @param emails list to store deserialize objects
     */
    public void deserialize(ArrayList<Email> emails) {
        try{
            FileInputStream file = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(file);
            while (true) {
                try {
                    Email email = (Email) in.readObject();
                    emails.add(email);
                } catch (EOFException e) {
                    break;
                }
            }
            in.close();
            file.close();
        } catch (FileNotFoundException e) {
            //This error will occur if we have not created the file that items will be serialized
            //So there is no file to deserialized from
            //Thus we do nothing when this error throws
            //Because if the file is not created it will get created in the first call of the EmailClient
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

/**
 * Validator class consists of four methods to check the validity of input dates,emailAddresses, inputs of case1 and inputs of case2
 */
class  Validator {
    /**
     * Checks the validity of email address
     * @param emailAddress
     * @return  validity
     */
    public boolean validEmail(String emailAddress) {
        Pattern regEmail = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher matcher = regEmail.matcher(emailAddress);
        if(!matcher.matches()) {
            System.out.println("Invalid Email Address");
            return false;
        }
        return true;
    }

    /**
     * Checks the validity of date format and date
     * @param date
     * @return validity
     */
    public  boolean validDate(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(date.strip());
        } catch (ParseException e) {
            System.out.println("Invalid date or date format");
            return false;
        }
        return true;
    }

    /**
     * Checks if the input in case 1 is valid or not
     * @param details input parameters
     * @return validity of the input parameters
     */
    public boolean validInput1(String[] details) {
        int length = details.length;
        if(!(length == 4 || length == 5)) {
            System.out.println("Details are missing");
            return false;
        }
        String type = details[0];
        if(type.equals("Official") && length == 4) {
            return true;
        } else if(type.equals("Office_friend") && length == 5) {
            return validDate(details[4]) && validEmail(details[2]);
        } else if (type.equals("Personal") && length == 5) {
            return validDate(details[4]) && validEmail(details[3]);
        }
        System.out.println("Invalid Input");
        return false;

    }


    /**
     * Check if the input in case 2 is valid or not
     * @param details input parameters
     * @return validity
     */
    public  boolean validInput2(String[] details) {
        int length = details.length;
        if(!(length == 3)) {
            System.out.println("Details are missing");
            return false;
        }
        return validEmail(details[0]);
    }

}



public class EmailClient {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Initialize objects to serialize objects to the file and deserialize objects from the file
        Serialization serialization = new Serialization();
        Deserialization deserialization = new Deserialization();

        //Initialize the Validator class
        Validator validator = new Validator();

        //get the current date and format it to the desired format
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        //String stringDate = date.format(formatter);
        String localDate = date.format(formatter);

        //Initialize makeRecipient class to make Recipients and a list to store them
        MakeRecipient makeRecipient = new MakeRecipient();
        ArrayList<Recipient> recipientList = new ArrayList<>();

        //traverse the clientList.txt to retain recipient details and store them in recipientList
        TextTraversal textTraversal = new TextTraversal();
        textTraversal.traverse(recipientList);

        //deserialize email objects that is saved in emailDetails.ser and store them in emailList
        ArrayList<Email> emailsList = new ArrayList<>();
        deserialization.deserialize(emailsList);

        //Sent birthday wishes to those who have birthdays in the current day.
        BirthdayFinder birthdayFinder = new BirthdayFinder();
        birthdayFinder.birthdayEmails(localDate, recipientList, emailsList);

        boolean repeat = true;
        int option;
        do {
            System.out.println();
            System.out.println("Enter option type: \n"
                    + "1 - Adding a new recipient\n"
                    + "2 - Sending an email\n"
                    + "3 - Printing out all the recipients who have birthdays\n"
                    + "4 - Printing out details of all the emails sent\n"
                    + "5 - Printing out the number of recipient objects in the application\n"
                    + "6 - Exit from the EmailClient");

            try {
                //to get the user input on which option to select
                option = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                //if Error occurs we get it as a default case
                option = 0;
            }
            switch (option) {
                case 1:
                    // Use a single input to get all the details of a recipient
                    System.out.println("Input format: ");
                    System.out.println("Official: name,email,position");
                    System.out.println("Office_friend: name,email,position,yyyy/MM/dd");
                    System.out.println("Personal: name,nick-name,email,yyyy/MM/dd");
                    System.out.println();
                    String recipientDetail = scanner.nextLine().strip();
                    // code to add a new recipient
                    String[] details = recipientDetail.split("[,:]");
                    StringBuilder temp = new StringBuilder();
                    //Readjust the input save the input as the given input format
                    for (int i = 0; i < details.length; i++) {
                        details[i] = details[i].strip();
                        if (i == 0)
                            temp.append(details[i]).append(": ");
                        else if (i == details.length - 1)
                            temp.append(details[i]);
                        else {
                            temp.append(details[i]).append(",");
                        }
                    }
                    //Validate the input
                    if (!validator.validInput1(details)) {
                        break;
                    }
                    recipientDetail = temp.toString();
                    Recipient recipient = makeRecipient.make(details);
                    recipientList.add(recipient);
                    try {
                        FileWriter writer = new FileWriter("clientList.txt", true);
                        BufferedWriter bufferedWriter = new BufferedWriter(writer);
                        bufferedWriter.write(recipientDetail);
                        bufferedWriter.newLine();
                        bufferedWriter.close();
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    // input format - email, subject, content
                    System.out.println("input format - email, subject, content");
                    System.out.println();
                    String emailDetails = scanner.nextLine().strip();
                    //split the input into following
                    String[] line = emailDetails.split(",");
                    //Validate the input
                    if (!validator.validInput2(line)) {
                        break;
                    }
                    String emailAddress = line[0];
                    String subject = line[1];
                    String content = line[2];
                    //Sending the mail
                    SendEmail sendEmail = new SendEmail();
                    sendEmail.send(emailAddress, subject, content);
                    System.out.println("Email sent successfully");
                    //Create an email object adn storing it in the emailsList
                    Email email = new Email(emailAddress, subject, content, localDate);
                    emailsList.add(email);
                    break;
                case 3:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    System.out.println("input format - yyyy/MM/dd");
                    System.out.println();
                    String stringDate = scanner.nextLine().strip();

                    //check if the given date input marches the input format
                    validator.validDate(stringDate);

                    // Printing out the recipients who have birthdays on the day given by user input
                    birthdayFinder.birthdayDetails(stringDate, recipientList);
                    break;
                case 4:
                    // input format - yyyy/MM/dd (ex: 2018/09/17)
                    System.out.println("input format - yyyy/MM/dd");
                    System.out.println();
                    stringDate = scanner.nextLine().strip();

                    //check if the given date input marches the input format
                    validator.validDate(stringDate);

                    // Printing out the details of the emails that were sent on the day given by user input
                    EmailFinder emailFinder = new EmailFinder();
                    emailFinder.emailDayDetails(stringDate, emailsList);
                    break;
                case 5:
                    //Printing out the number of recipient objects in the application
                    System.out.println(MakeRecipient.recipients);
                    break;
                case 6:
                    //Exit from the EmailClient
                    repeat = false;
                    break;
                default:
                    //No cases match
                    System.out.println("Invalid Input");
            }
        } while (repeat);
        //Serialize the email objects that in the emailsList to the emailDetails.ser file
        //This happens when we exit from the EmailClient
        serialization.serialize(emailsList);
        System.out.println("Exited from the EmailClient Successfully");
    }
}