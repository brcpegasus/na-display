<%@page import="java.text.NumberFormat"%>
<%@page import="de.stephanus.netatmo.model.Device"%>
<%@page import="java.util.List"%>
<%@page import="de.stephanus.netatmo.controller.NetAtmoIntegrator"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%
		NetAtmoIntegrator integrator = NetAtmoIntegrator.getInstance();
		List<Device> devices = integrator.getDevices();

		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(1);
		nf.setMinimumFractionDigits(1);
	%>
<html>
<head>
<meta http-equiv="refresh" content="300">
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="viewport" content="width=device-width">
<style type="text/css">
body {
	-webkit-text-size-adjust: 100;
}
</style>
</head>
<body style="background-color: #000000">
	<p style="font-size: 2em; font-family:Verdana;color : #FFFFFF;text-align: center;">
		<nobr><img src="/NetAtmoDisplay/glyphicons_free/glyphicons/png/white/glyphicons-21-home.png" height="50px" /> <%=nf.format(devices.get(0).getDashboardData().getTemperature())%>°</nobr>
	</p>
	<hr/>
	<p style="font-size: 2em; font-family:Verdana;color: #FFFFFF;text-align: center;">
		<nobr><img src="/NetAtmoDisplay/glyphicons_free/glyphicons/png/white/glyphicons-318-tree-deciduous.png" height="50px" /> <%=nf.format(devices.get(0).getModules().get(0).getDashboardData().getTemperature())%>°</nobr>
	</p>
</body>
</html>