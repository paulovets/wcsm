<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="<c:url value='/resources/js/jquery-2.2.3.min.js'/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap-theme.min.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/resources/css/bootstrap.min.css'/>" />
    <script src="<c:url value='/resources/js/bootstrap.min.js'/>"></script>
    <script src="<c:url value='/resources/js/ace/ace.js'/>"></script>
    <script src="<c:url value='/resources/js/beautify/beautify-css.js'/>"></script>
    <script src="<c:url value='/resources/js/beautify/beautify-html.js'/>"></script>
    <script src="<c:url value='/resources/js/beautify/beautify.js'/>"></script>
    <script src="<c:url value='/resources/js/custom.js'/>"></script>
</head>

<body>
<table style="width: 100%; height: 100%;">
    <tr>
        <td colspan="2">
            <tiles:insertAttribute name="header" />
        </td>
    </tr>
    <tr>
        <td class="custom-body" style="width: 100%; height: 100%;">
            <tiles:insertAttribute name="body" />
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <tiles:insertAttribute name="footer" />
        </td>
    </tr>
</table>
</body>
</html>