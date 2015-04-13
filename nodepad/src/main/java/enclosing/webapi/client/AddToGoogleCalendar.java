package enclosing.webapi.client;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.google.gdata.client.calendar.CalendarService;
import com.google.gdata.data.DateTime;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.extensions.EventEntry;
import com.google.gdata.data.extensions.When;
import com.google.gdata.data.extensions.Where;
import com.google.gdata.util.AuthenticationException;
import com.google.gdata.util.ServiceException;

import core.model.NodeInterface;

public class AddToGoogleCalendar {
	public AddToGoogleCalendar(NodeInterface node){

		try {
			SAXReader reader = new SAXReader();
				Document staticDataDocument = reader.read("staticData.xml");
				Element staticDataElement = staticDataDocument.getRootElement();
				String googleaccount = staticDataElement.element("googleaccount").getText();
				String  googleaccountpassword = staticDataElement.element("googleaccountpassword").getText();
				
				EventEntry myEntry = new EventEntry();
				myEntry.setTitle(new PlainTextConstruct("<nodelog>"+node.getContent() + "</nodelog>"));
				DateTime startTime = new DateTime(new Date());
				startTime.setTzShift(new Integer(9));
				Date enddate = new Date();
				enddate.setTime(new Date().getTime() + 3600 * 1000);
				DateTime endTime   = new DateTime(enddate);
				endTime.setTzShift(new Integer(9));
				When eventTimes = new When();
				eventTimes.setStartTime(startTime);
				eventTimes.setEndTime(endTime);
				myEntry.addTime(eventTimes);
				
				Where evLocation = new Where();
				evLocation.setValueString("home");
				myEntry.addLocation(evLocation);
				
				CalendarService myService = new CalendarService("Mashup Sample Application - 1.0");
				myService.setUserCredentials(googleaccount, googleaccountpassword);

				URL postUrl = new URL("http://www.google.com/calendar/feeds/default/private/full");
				EventEntry insertedEntry = (EventEntry)myService.insert(postUrl, myEntry);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		}	 




			

			
			

		
		
	}
}
