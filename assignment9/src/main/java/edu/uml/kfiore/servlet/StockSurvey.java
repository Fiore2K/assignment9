package edu.uml.kfiore.servlet;

import edu.uml.kfiore.model.Symbol;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Simple Example of how a servlet can access form submission data
 * and act on it accordingly.
 */
public class StockSurvey extends HttpServlet {

    private static final String APPLE_PARAMETER_KEY = "APPL";
    private static final String GOOGLE_PARAMETER_KEY = "GOOG";
    private static final String AMAZON_PARAMETER_KEY = "AMZN";
    private static final String CHECK_BOX_IS_CHECKED_VALUE = "on";

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {


        String apple = request.getParameter(APPLE_PARAMETER_KEY);
        String google = request.getParameter(GOOGLE_PARAMETER_KEY);
        String amazon = request.getParameter(AMAZON_PARAMETER_KEY);

        boolean isApple = false;
        // happy and sad will be null, if not se to 'on' when selected.
        if (apple!= null && apple.equals(CHECK_BOX_IS_CHECKED_VALUE)) {
            isApple = true;
        }

        boolean isGoogle = false;
        if (google!= null && google.equals(CHECK_BOX_IS_CHECKED_VALUE)) {
            isGoogle = true;
        }

        boolean isAmazon = false;
        if (amazon!= null && amazon.equals(CHECK_BOX_IS_CHECKED_VALUE)) {
            isAmazon = true;
        }

        HttpSession session = request.getSession();

        /* Here is where use put an instance of Mood in the session.
         * The HTTP session contains a Map (it stores key value pairs)
         * You can put anything you like in that map.
         * When the session terminates the map goes away.
         *
         * You can also store key / values in the request.
         * However, those values will only be around for the duration of the
         * request and not the entire session.
         *
         */
        Symbol symbol = new Symbol(isApple, isGoogle, isAmazon);
        session.setAttribute("symbol", symbol);

        /* here is how a servlet can 'forward' to a specific JSP page
         * NOTE: you could easily have conditional code here that
         * directed to different pages based on values or business rules
         * related to the data this servlet is processing.
         *
         * e.g

          RequestDispatcher dispatcher;
          if (mood.getHappy()) {
             dispatcher = getServletContext().getRequestDispatcher("/goodMood.jsp");
          } else if (mood.getSad()) {
             dispatcher = getServletContext().getRequestDispatcher("/sadMood.jsp");
          } else {
             dispatcher = getServletContext().getRequestDispatcher("/confusedMood.jsp");
          }

         * However, in this example we only dispatch to one place the moodResponse.jsp page
         *
         */

        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher =
                servletContext.getRequestDispatcher("/stockquoteResults.jsp");
        dispatcher.forward(request, response);

    }
}
