<%page contentType="text/html"%>
<%page pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html><head><title>Download Help Page</title></head>
<body>
<h1>Download Help Page</h1>
<br/>A client will start by calling capabilities (&lt;server&gt;:&lt;port&gt;/&lt;api-endpoint&gt;/capabilities/{metadataUuid}).
<br/>This is the root API call for a dataset as described at <a href=\"http://nedlasting.geonorge.no/Help\">Geonorge</a>
<br/>Capabilties will announce the rest of the resources with links (hrefs) and relation (rel)
<br/><br/>
<table>
<tr><th bgcolor=grey>Relation (rel)</th><th bgcolor=grey>Description</th></tr>
<tr><td>http://rel.geonorge.no/download/projection</td><td>Announce where (href) to GET a list of supported projections for a dataset</td></tr>
<tr><td>http://rel.geonorge.no/download/format</td><td>Announce where (href) to GET a list of supported formats for a dataset</td></tr>
<tr><td>http://rel.geonorge.no/download/are</td><td>Announce where (href) to GET a list of supported areas for a dataset</td></tr>
<tr><td>http://rel.geonorge.no/download/order</td><td>Announce where (href) to POST the order for a dataset</td></tr>		
</table>
<br/><br/>
<footer>Licence: <a href=\"http://data.norge.no/nlod/en\">NLOD</a>  <br/>Written by <a href=\"http://www.ngu.no\">Geological survey of Norway</a>, 2016		
</footer>
</body>