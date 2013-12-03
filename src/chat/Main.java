/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chat;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import java.sql.Timestamp;
import java.util.GregorianCalendar;
import org.joda.time.DateTime;
import org.joda.*;

import org.jsoup.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

/**
 *
 * @author Gowtham
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here

        Thread t = Thread.currentThread ( );
        int g,uid = 0;
        DateTime cal1 = new DateTime(2010,8,5,0,0);
        Timestamp sd = new Timestamp(cal1.getMillis());
        db mysql = new db();
        int hour,minute;


        String html,message,shit,times;
       
        Document go;
        
        do{
       
            org.jsoup.nodes.Document doc = Jsoup.connect("http://chat.stackexchange.com/transcript/118/"+cal1.getYear()+"/"+cal1.getMonthOfYear()+"/"+cal1.getDayOfMonth()).userAgent("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)").get();

        Elements messages = doc.select(".monologue");
        for( Element a : messages){
            go = Jsoup.parse(a.html());
            message=(go.select(".content").html());
            shit = Jsoup.parse((go.select(".username").html())).select("a[href]").attr("href");

            if(shit.length()> 0){
           Scanner s = new Scanner (shit.substring(7));
           s.useDelimiter("/");
           if(s.hasNextInt())
           uid=s.nextInt();
           
            }
            else{
                uid=0;
            }

           times= go.select(".timestamp").html();
           times= times.length()== 8? times: "0"+ times;
           System.err.println(times);
           if(  times.equals("0") ? times == null :times.substring(4).equals("AM") ){
              
               hour =Integer.parseInt (times.substring(0,1));
               minute = Integer.parseInt(times.substring(3,5));
           }else
           {
               System.err.println(times);
                 hour =(Integer.parseInt(times.substring(0,1)))+12;
               minute = Integer.parseInt(times.substring(3,5));
           }
           
           sd.setTime((cal1.withTime(hour,minute,0,0)).getMillis());
                try {
                    db.pushdata(uid, message, sd);
                } catch (Exception ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        cal1 = cal1.plusDays(1);
        
        }
        while(1>0);



       
          
    

        

    }
    

}
