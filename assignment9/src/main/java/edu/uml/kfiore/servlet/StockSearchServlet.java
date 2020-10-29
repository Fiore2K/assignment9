package edu.uml.kfiore.servlet;

import edu.uml.kfiore.database.QuoteDAO;
import edu.uml.kfiore.database.StockSymbolDAO;
import edu.uml.kfiore.model.StockQuote;
import edu.uml.kfiore.services.StockService;
import edu.uml.kfiore.services.StockServiceException;
import edu.uml.kfiore.util.DatabaseUtils;
import edu.uml.kfiore.util.Interval;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServlet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StockSearchServlet extends HttpServlet implements StockService {

    @Override
    public StockQuote getQuote(String symbol) throws StockServiceException {
        StockQuote stockQuote;

        StockSymbolDAO stockSymbolDAO = DatabaseUtils.findUniqueResultBy("symbol", symbol, StockSymbolDAO.class, true);
        List<QuoteDAO> quotes = DatabaseUtils.findResultsBy("stockSymbolBySymbolId", stockSymbolDAO, QuoteDAO.class, true);

        if (quotes.isEmpty()) {
            throw new StockServiceException("Could not find any stock quotes for: " + symbol);
        }

        QuoteDAO quoteDAO = quotes.get(0);
        // pojo conversion
        long time = quoteDAO.getTime().getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        Date quoteTime = calendar.getTime();
        stockQuote = new StockQuote(quoteDAO.getPrice(), quoteTime, stockSymbolDAO.getSymbol());

        return stockQuote;
    }

    @Override
    public List<StockQuote> getQuote(String symbol, Calendar from, Calendar until, Interval interval) throws StockServiceException {
        List<StockQuote> stockQuotes = null;
        Session session = null;
        Transaction transaction = null;

        try {
            StockSymbolDAO stockSymbolDAO = DatabaseUtils.findUniqueResultBy("symbol", symbol, StockSymbolDAO.class, true);
            session = DatabaseUtils.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Timestamp fromTimeStamp = new Timestamp(from.getTimeInMillis());
            Timestamp untilTimestamp = new Timestamp(until.getTimeInMillis());
            Criteria criteria = session.createCriteria(QuoteDAO.class);
            criteria.add(Restrictions.eq("stockSymbolBySymbolId", stockSymbolDAO));
            criteria.add(Restrictions.between("time", fromTimeStamp,untilTimestamp));
            List<QuoteDAO> quoteDAOs = (List<QuoteDAO>) criteria.list();
            transaction.commit();
            stockQuotes = new ArrayList<>(quoteDAOs.size());

            for (QuoteDAO quoteDAO : quoteDAOs) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(quoteDAO.getTime().getTime());
                stockQuotes.add(new StockQuote(quoteDAO.getPrice(),
                        calendar.getTime(),
                        quoteDAO.getStockSymbolBySymbolId().getSymbol()));
            }
        } catch (HibernateException e)  {
            if (transaction != null) {
                transaction.rollback();
            }
            if (session != null) {
                session.close();
            }
            session  = null;

        } finally {
            if (session != null) {
                session.close();
            }
        }

        return stockQuotes;
    }
}
