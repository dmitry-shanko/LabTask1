<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<span class="contentNewsTitle"><html:link action="/newslist"><bean:message
		key="label.newsview.news" /></html:link></span>
&gt;&gt;
<bean:message key="label.newsview.view" />
<br>
<br>
	<logic:empty name="newsForm" property="news.title">
		<bean:message key="label.newsview.nonews" />
	</logic:empty>
	<logic:notEmpty name="newsForm" property="news.title">
<bean:define name="newsForm" property="news" id="news" />
<table>
	<tr>
		<td class="newsTableTitles"><span class="NewsViewTitle"><bean:message
					key="news.title" /></span></td>
		<td><bean:write name="news" property="title" /></td>
	</tr>
	<tr>
		<td><span class="NewsViewTitle"><bean:message
					key="news.date" /></span></td>
		<td><bean:write name="news" property="date"
				formatKey="date.pattern" /></td>
	</tr>
	<tr>
		<td><span class="NewsViewTitle"><bean:message
					key="news.brief" /></span></td>
		<td><bean:write name="news" property="brief" /></td>
	</tr>
	<tr>
		<td><span class="NewsViewTitle"><bean:message
					key="news.content" /></span></td>
		<td><bean:write name="news" property="content" /></td>
	</tr>
</table>
<table class="newsListTable">
	<tr>
		<td class="afterTableButtons"><html:form action="/newsedit">
				<html:submit>
					<bean:message key="news.edit" />
				</html:submit>
				<html:hidden name="newsForm" property="news.id" value="${news.id }" />
			</html:form></td>
		<td><html:form action="/newsdelete"
				onsubmit="return confirmDialog()">
				<html:submit>
					<bean:message key="news.delete" />
				</html:submit>
				<html:hidden name="newsForm" property="selectedItems" value="${news.id }"/>
			</html:form></td>
	</tr>
</table>
</logic:notEmpty>
<br>