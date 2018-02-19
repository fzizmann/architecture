package de.thb.fz.example;

public class MailMain {

  public static void main(String[] args) {
    Mail mail = new Mail();
    mail.from("from@test.de");
    mail.to("to@test.de");
    mail.subject("test");
    mail.message("Testmessage");
    mail.send();
  }

}
