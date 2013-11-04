<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<span class="contentNewsTitle"><html:link action="/newslist"><bean:message key="label.newsedit.news" /></html:link></span>
&gt;&gt;
<bean:message key="label.newsedit.title" />
<br>
<br>

<html:form action="/newssave"
	onsubmit="return validateAddEditNewsForm(this)">
	<table>
		<tr>
			<td class="newsTableTitles"><bean:message key="news.title" /></td>
			<td><html:textarea rows="1" cols="100" name="newsForm"
					property="news.title" /></td>
		</tr>
		<tr>
			<td><bean:message key="news.date" /></td>
			<td><html:text size="10" name="newsForm" property="dateString"/></td>
		</tr>
		<tr>
			<td><bean:message key="news.brief" /></td>
			<td><html:textarea rows="2" cols="100" name="newsForm"
					property="news.brief" /></td>
		</tr>
		<tr>
			<td><bean:message key="news.content" /></td>
			<td><html:textarea rows="20" cols="100" name="newsForm"
					property="news.content" /></td>
		</tr>
	</table>
	<table class="newsListTable">
		<tr>
			<td class="afterTableButtons"><html:submit>
					<bean:message key="news.save" />
				</html:submit> <html:hidden name="newsForm" property="news.id" /></td>
			<td><input type="button"
				value="<bean:message key="news.cancel"/>"
				onclick="location.replace('cancel.do')" /></td>
		</tr>
	</table>
	<br>
</html:form>