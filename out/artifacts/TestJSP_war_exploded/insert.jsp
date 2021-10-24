<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="model.DAO" %>
<%@ page import="model.DAO" %>

<jsp:useBean id="db" class="db.DB"/>
<%
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");

        DAO DAO = new DAO(name, Double.parseDouble(latitude),
                Double.parseDouble(longitude));

        db.insertDB(DAO);
%>
