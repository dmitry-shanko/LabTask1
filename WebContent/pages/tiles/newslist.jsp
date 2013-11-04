<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<span class="contentNewsTitle"><html:link action="/newslist"><bean:message
		key="label.newslist.news" /></html:link></span>
&gt;&gt;
<bean:message key="label.newslist.newslist" />
<br>
<br>
<html:form action="/newsdelete" onsubmit="return deleteGroupOfNews()">
	<logic:empty name="newsForm" property="newsList">
		<bean:message key="label.newslist.nonews" />
	</logic:empty>
	<logic:iterate id="news" property="newsList" name="newsForm">
		<table class="newsListTable">
			<tr>
				<td class='newsListTableTitle'><bean:write name="news"
						property="title" /></td>
				<td><bean:write name="news" property="date"
						formatKey="date.pattern" /></td>
			</tr>
			<tr>
				<td><bean:write name="news" property="brief" /></td>
			</tr>
			<tr>
				<bean:define name="news" id="newsId" property="id" />
				<td class='newsListTableLinks' colspan="2"><html:link
						action="newsview" paramName="newsId" paramId="news.id">
						<bean:message key="label.newslist.view"></bean:message>
					</html:link> &nbsp;&nbsp;&nbsp;&nbsp; <html:link action="/newsedit"
						paramName="newsId" paramId="news.id">
						<bean:message key="label.newslist.edit"></bean:message>
					</html:link> &nbsp;&nbsp;&nbsp;&nbsp; <html:multibox property="selectedItems">
						<bean:write name='news' property='id' />
					</html:multibox></td>
			</tr>
		</table>
	</logic:iterate>
	<logic:notEmpty name="newsForm" property="newsList">
		<table class="newsListTable">
			<tr>
				<td class="afterTableButtons"><html:submit>
						<bean:message key="label.newslist.delete" />
					</html:submit></td>
			</tr>
		</table>
	</logic:notEmpty>
</html:form>
<br>