<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="model.DAO" %>
<%@ page import="java.util.List" %>

<%@ page import="org.json.simple.*" %>

<jsp:useBean id="db" class="db.DB"/>
<jsp:useBean id="cc" class="db.Calc"/>
<%
    request.setCharacterEncoding("UTF-8");

    String latitude = request.getParameter("latitude");
    String longitude = request.getParameter("longitude");

    List<DAO> DAOList = cc.circle(Double.parseDouble(latitude), Double.parseDouble(longitude));

    try {
        JSONObject sendObject = new JSONObject();
        JSONArray sendArray = new JSONArray();

        for (DAO model : DAOList) {
            JSONObject modelObject = new JSONObject();
            modelObject.put("id", model.getId());
            modelObject.put("name", model.getName());
            modelObject.put("latitude", model.getLatitude());
            modelObject.put("longitude", model.getLongitude());

            sendArray.add(modelObject);
        }
        sendObject.put("list", sendArray);
        out.print(sendObject);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
