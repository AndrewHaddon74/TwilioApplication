import java.util.concurrent.TimeUnit;
import static spark.Spark.*;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import spark.Request;


//TODO issue with time being in 12 hour incroments with no am/pm

public class main {
    public static void main(String[] args) {
        String number;
        String date;
        String time;


        //required input data
        final String senderNumber = "+16692020472";
        String message = "Your appointment with The GhostDog is at, bring him snacks ";
        String massMessage;
        String hookPath;
        String passcode = "EXP";




        //starts message sender with sender phone number
        SMSSend sender = new SMSSend(senderNumber);



        get("/", (request, response) -> {
                    return "The GhostDog Will see you now";
        });




        post("/", (req, res) -> {

            String incoming = getBody(req);
            //TODO passcode
         //   if(incoming.substring(incoming.indexOf("Body")+5,incoming.indexOf("\\&",incoming.indexOf("Body")+5)) != passcode) {


                incoming = incoming.substring(incoming.indexOf("Body") + 5, incoming.indexOf("FromCountry") - 1);
                incoming = incoming.replaceAll("\\+", " ");
                System.out.println(incoming);

                // massAlert(sender);

                res.type("application/xml");
                Body body = new Body.Builder(incoming).build();
                Message sms = new Message.Builder().body(body).build();
                MessagingResponse twiml = new MessagingResponse.Builder().message(sms).build();
                return twiml.toXml();
           // }
           // return null;
        });





        //Starts CSV reader for apointments
        csvReader csvReader = new csvReader("C:\\Users\\rogue\\IdeaProjects\\Rogue-Code-Twilio\\src\\main\\java\\RogueCodeTestCSV.csv");
        //while loop to run program endlessly
        while(true) {




            //takes a number of days away and looks for apointments that happen before that date, returns the date and phone number
            String[] t = csvReader.scanAppointment(2,15);

            System.out.println(t.length);

            for(String a: t){
                if(a != null) {
                    //Spliter
                    int commaL = a.indexOf(',');
                    date = a.substring(0, commaL);
                    number = a.substring(commaL + 1, a.length());
                    number = "+1"+number;
                    int space = date.indexOf(' ');
                    time = date.substring(space + 1, date.length());
                    date = date.substring(0, space);
                    //builds message
                    message = "Your appointment with The GhostDog is at " + time + " on " + date + " , bring him snacks ";
                    System.out.println(message);
                    System.out.println(number);
                    //TODO log for if the message sent and when
                    sender.sendMessage(message,number);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        //skipping for loop in case time unit fails
                        System.out.println("Time unit failed to skip manual override enabled");
                        for(int i = 0; i < 1000; i++){for(int j = 0; j < 1000; j++){ } }
                    }
                }
            }

            try {
                TimeUnit.MINUTES.sleep(15);
            } catch (InterruptedException e) {
                //skipping for loop in case time unit fails
                System.out.println("Time unit failed to skip manual override enabled");
                for(int i = 0; i < 100000; i++){ for(int j = 0; j < 100000; j++){ } }
            }
        }
        //end main while loop
    }

    public static void massAlert(SMSSend sender){
        //needs to get mass alert message somewhere in here
        String massAlertMessage = "Maxium Reeeee";
        int count =0;

        //starts the mass text csv reader and gets the array of number so text
        csvReader mass = new csvReader("C:\\Users\\rogue\\IdeaProjects\\Rogue-Code-Twilio\\src\\main\\java\\RogueCodeMassTextCSV.csv");
        String[] m = mass.massAlert();

        System.out.println("Did Somthing");

        for(String a:m){
            System.out.println(a);
            sender.sendMessage(massAlertMessage,a);
            count++;

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                System.out.println("Time unit failed to skip manual override enabled");
                //skipping for loop in case time unit fails
                for(int i = 0; i < 1000; i++){ for(int j = 0; j < 1000; j++){} }
            }
        }
    }

    private static String getBody(Request request){
        return request.body();
    }
}
