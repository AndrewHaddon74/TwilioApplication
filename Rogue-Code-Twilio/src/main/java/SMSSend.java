import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;


public class SMSSend {
    final String Account_SID = "AC6c7f7b3285ee0b4a63b25808c917c40c";
    final String Account_AUTH_TOKEN = "d142bcb1f32307c6c420e84d644e1eaf";
    PhoneNumber from;

    public SMSSend(String number){
        Twilio.init(Account_SID,Account_AUTH_TOKEN);
        from = new com.twilio.type.PhoneNumber(number);
    }
    //takes in a message and a number to send the message to said number
    public Boolean sendMessage(String message, String number){

        //try catch incase phone number is invalid or the message can not be send
        try {
            //phonenumber sending to
            PhoneNumber to = new com.twilio.type.PhoneNumber(number);
            //builds message to from message
            Message m = Message.creator(to,from,message).create();
            return true;
        }
        catch(Exception e){
            System.out.println("Failed to create phone number or send the message with : "+number);
            return false;
        }
    }
}
