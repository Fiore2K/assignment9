package edu.uml.kfiore.servlet;

import edu.uml.kfiore.model.StockQuote;
import edu.uml.kfiore.services.ServiceFactory;
import edu.uml.kfiore.services.StockService;
import edu.uml.kfiore.services.StockServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class StockSearchServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        StockService stockService = ServiceFactory.getInstance();
        String symbol = request.getParameter("Symbol");
        String startDate = request.getParameter("start_date");
        String endDate = request.getParameter("end_date");
        List<StockQuote> stockQuoteList = null;
        try {
            stockQuoteList = stockService.getQuote(symbol, startDate, endDate);
        } catch (StockServiceException e) {
            e.printStackTrace();
        }

        HttpSession session = request.getSession();
        session.setAttribute("quotes", stockQuoteList);

        ServletContext servletContext = getServletContext();
        RequestDispatcher dispatcher = servletContext.getRequestDispatcher("/stockquoteResults.jsp");
        dispatcher.forward(request, response);

    }

}
