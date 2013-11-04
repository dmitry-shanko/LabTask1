<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="/WEB-INF/Tiles/struts-tiles.tld" prefix="tiles"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<html>
<head>
<title><tiles:getAsString name="title" ignore="true" /></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="static/js/jsMessages.jsp"></script>
<script type="text/javascript" src="static/js/scripts.js"></script>
<link rel="stylesheet" type="text/css" href="static/css/layout.css"/>
</head>
<body>
	<!-- Header -->
	<table class="header">
		<tr>
			<td rowspan="2" class="header_title"><tiles:insert
					attribute="headerTitle" /></td>
		</tr>
		<tr>
			<td class="header_languages"><tiles:insert
					attribute="headerLang" /></td>
		</tr>
	</table>
	<!-- Menu and content -->
	<table class="menu_content">
		<!-- Menu -->
		<tr>
			<td class="menu">
				<div class="menu_div">
					<br>
					<tiles:insert attribute="menu" />
					<br>
				</div>
			</td>
			<!-- Content -->
			<td class="content"><tiles:insert attribute="content" /></td>
		</tr>
	</table>
	<!-- Footer -->
	<table class="footer">
		<tr>
			<td><tiles:insert attribute="footer" /></td>
		</tr>
	</table>
</body>
</html>