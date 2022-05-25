<%@ page import="com.dormitory.entity.Page" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <!-- 引入 Bootstrap -->
    <script src="https://cdn.bootcss.com/jquery/2.2.4/jquery.min.js"></script>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <!-- 引入 font-awesome -->
    <link href="https://cdn.bootcss.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <title>学生迁出登记</title>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-10">

            <!-- 顶部搜索部分 -->
            <div class="panel panel-default">
                <div class="panel-heading">搜索</div>
                <div class="panel-body">
                    <form role="form" class="form-inline" action="/moveout?method=list" method="post">
                        <div class="form-group">
                            <label>字段：</label>
                            <select name="key" class="form-control">
                                <option value="number">学号</option>
                                <option value="name">姓名</option>
                            </select>
                        </div>
                        <div class="form-group" style="margin-left: 20px">
                            <label>值：</label>
                            <input type="text" class="form-control" name="value" placeholder="字段值" maxlength="12" style="width: 130px">
                        </div>
                        <div class="form-group " style="margin-left: 20px">
                            <button type="submit" class="btn btn-info ">
										<span style="margin-right: 5px"
                                              class="glyphicon glyphicon-search" aria-hidden="true">
										</span>开始搜索
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            <!-- 列表展示-->
            <div class="table-responsive">
                <table class="table table-hover ">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>学号</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>宿舍</th>
                        <th>宿舍楼</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.page.students}" var="student" varStatus="id">
                        <tr>
                            <td>${student.id}</td>
                            <td>${student.number}</td>
                            <td>${student.name}</td>
                            <td>${student.gender}</td>
                            <td>${student.dormitoryName}</td>
                            <td>${student.buildingName}</td>
                            <td>${student.state}</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-danger"
                                            data-id="${student.id}"
                                            data-dormitory-id="${student.dormitoryId}"
                                            data-dormitory-name="${student.dormitoryName}"
                                            data-toggle="modal"
                                            data-target="#delUserModal">
                                        <i class="fa fa-user-o">迁出</i>
                                    </button>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    <tr><td colspan="9">
                        <div align="center">
                            <c:set var="p" value="${requestScope.page}"></c:set>
                            <c:choose>
                                <c:when test="${p.currentPage==1 && p.currentPage==p.totalPage}">
                                    <a href="#">首页</a>
                                    <a href="#">上一页</a>
                                    <a href="#">下一页</a>
                                    <a href="#">尾页</a>
                                </c:when>
                                <c:when test="${p.currentPage==p.totalPage}">
                                    <a href="${pageContext.request.contextPath}/moveout?method=list&currentPage=1&key=${requestScope.key}&value=${requestScope.value}">首页</a>
                                    <a href="${pageContext.request.contextPath}/moveout?method=list&currentPage=${p.currentPage-1}&key=${requestScope.key}&value=${requestScope.value}">上一页</a>
                                    <a href="#">下一页</a>
                                    <a href="${pageContext.request.contextPath}/moveout?method=list&currentPage=${p.totalPage}&key=${requestScope.key}&value=${requestScope.value}">尾页</a>
                                </c:when>
                                <c:when test="${p.currentPage==1}">
                                    <a href="${pageContext.request.contextPath}/moveout?method=list&currentPage=1&key=${requestScope.key}&value=${requestScope.value}">首页</a>
                                    <a href="#">上一页</a>
                                    <a href="${pageContext.request.contextPath}/moveout?method=list&currentPage=${p.currentPage+1}&key=${requestScope.key}&value=${requestScope.value}">下一页</a>
                                    <a href="${pageContext.request.contextPath}/moveout?method=list&currentPage=${p.totalPage}&key=${requestScope.key}&value=${requestScope.value}">尾页</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/moveout?method=list&currentPage=1&key=${requestScope.key}&value=${requestScope.value}">首页</a>
                                    <a href="${pageContext.request.contextPath}/moveout?method=list&currentPage=${p.currentPage-1}&key=${requestScope.key}&value=${requestScope.value}">上一页</a>
                                    <a href="${pageContext.request.contextPath}/moveout?method=list&currentPage=${p.currentPage+1}&key=${requestScope.key}&value=${requestScope.value}">下一页</a>
                                    <a href="${pageContext.request.contextPath}/moveout?method=list&currentPage=${p.totalPage}&key=${requestScope.key}&value=${requestScope.value}">尾页</a>
                                </c:otherwise>
                            </c:choose>
                            <%
                                Page pp = (Page) request.getAttribute("page");
                            %>
                            <select name="currentPage" id="Select" onchange="window.location=this.value">
                                <%for (int i = 1; i <= pp.getTotalPage(); i++) {%>
                                <option value="/moveout?method=list&currentPage=<%=i%>&key=${requestScope.key}&value=${requestScope.value}"
                                        <%=(pp.getCurrentPage() == +i) ? "selected='selected'" : ""%>>
                                    <%=i%>/${requestScope.page.totalPage}
                                </option>
                                <%}%>
                            </select><br>
                            第${requestScope.page.currentPage}页,共${requestScope.page.totalPage}页,
                            ${requestScope.page.totalCount}条数据
                        </div>
                    </td></tr>
                    </tbody>
                </table>

                <!-- 迁出模态框示例（Modal） -->
                <form method="post" action="/moveout?method=moveout"
                      class="form-horizontal" style="margin-top: 0px" role="form"
                      id="form_data" style="margin: 20px;">
                    <div class="modal fade" id="delUserModal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">×</button>
                                    <h4 class="modal-title" id="myModalLabel">用户信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">

                                        <div class="form-group">
                                            <div class="col-sm-9">
                                                <h3 class="col-sm-18 control-label" id="deleteLabel">删除信息</h3>
                                                <input type="hidden" class="form-control" id="tab"
                                                       name="tab" placeholder="" value="dor_admin">
                                                <input
                                                    type="hidden" class="form-control" id="id"
                                                    name="studentId" placeholder="">
                                                <input type="hidden" class="form-control"
                                                       name="dormitoryId" id="dormitoryId">
                                                <input type="hidden" class="form-control"
                                                       name="dormitoryName" id="dormitoryName">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label  class="col-sm-3 control-label">迁出原因</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control"
                                                       name="reason">
                                            </div>
                                        </div>

                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" class="btn btn-danger">迁出</button>
                                    <span id="tip"> </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div>
</div>
</div>

<script>
    $('#delUserModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var id = button.data('id')
        var dormitoryId = button.data('dormitory-id')
        var dormitoryName = button.data('dormitory-name')
        var modal = $(this)
        modal.find('.modal-title').text('学生迁出登记')
        modal.find('#deleteLabel').text('是否迁出学生')
        modal.find('#id').val(id)
        modal.find('#dormitoryId').val(dormitoryId)
        modal.find('#dormitoryName').val(dormitoryName)
    })
</script>
<script>
    function selectPage() {
        var num = $("#select").val();
        window.location.href = "/moveout?method=list&currentPage=" + num;
    }
</script>
</body>
</html>