<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>StockQuote Survey</title>
</head>
<body>


Stock Quote Form <br>

<P></P>

<form name="myform" action="servlets/ServletForwardingExample/" method="post">

    <label for="symbol">Please select your stock symbol:  </label>
    <select id="symbol" name="symbol">
        <option value="APPL">APPL</option>
        <option value="GOOG">GOOG</option>
        <option value="AMZN">AMZN</option>
    </select>

    <br>

    <label for="start">Please select the start date range: </label>

    <input type="date" id="start" name="date-start"
           value="2020-10-29"
           min="1990-01-01" max="2020-12-31">

    <br>

    <br>

    <label for="end">Please select the start date range: </label>

    <input type="date" id="end" name="date-end"
           value="2020-10-30"
           min="1990-01-01" max="2020-12-31">

    <br>

    <input type="SUBMIT" value="OK">
    <input type="HIDDEN" name="submit" value="true">
</form>
</body>
</html>
