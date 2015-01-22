package enclosing.web.api;

import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import enclosing.application.node.predicate.DescendantsOf;

@WebServlet("/RelatedNodes")
public class RelatedNodes extends HttpServlet{

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
        	response.setHeader("Access-Control-Allow-Origin", "*");
        	final String node = request.getParameter("node");
        	final String relatedInString = DescendantsOf.relatedInString(node);
        	
    		OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream(), "UTF-8");
    		writer.write(relatedInString);
    		writer.flush();
    		writer.close();
        	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
//		response.getWriter().write(conversion.getResult());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
