<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>


<p class="menu_title">
	<bean:message key="menu.head" />
</p>
<div class="menu_ul">
	<ul>
		<li><html:link action="/newslist">
				<bean:message key="menu.newslist" />
			</html:link></li>
		<li><html:link action="/newsadd">
				<bean:message key="menu.newsadd" />
			</html:link></li>
	</ul>
</div>