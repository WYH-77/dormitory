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
    <title>宿舍管理</title>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-sm-10">
            <!-- 顶部搜索部分 -->
            <div class="panel panel-default">
                <div class="panel-heading">搜索</div>
                <div class="panel-body">
                    <form role="form" class="form-inline" action="/dormitory?method=list" method="post">
                        <div class="form-group">
                            <label for="name">字段：</label>
                            <select name="key" class="form-control">
                                <option value="name">名称</option>
                                <option value="telephone">电话</option>
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
                        <div class="form-group " style="margin-left: 48px">
                            <button type="button" class="btn btn-default" data-toggle="modal" data-target="#addUserModal">
										<span style="margin-right: 5px" class="" aria-hidden="true">
											<i class="fa fa-user-plus">添加宿舍信息</i>
											</span>
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
                        <th>楼宇</th>
                        <th>名称</th>
                        <th>几人间</th>
                        <th>空余床位</th>
                        <th>电话</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.page.dormitoryList}" var="dormitory" varStatus="id">
                        <tr>
                            <td>${dormitory.id}</td>
                            <td>${dormitory.buildingName}</td>
                            <td>${dormitory.name}</td>
                            <td>${dormitory.type}</td>
                            <td>${dormitory.available}</td>
                            <td>${dormitory.telephone}</td>
                            <td>
                                <div class="btn-group">
                                    <button type="button" class="btn btn-default "
                                            data-id="${dormitory.id}"
                                            data-name="${dormitory.name}"
                                            data-telephone="${dormitory.telephone}"
                                            data-toggle="modal"
                                            data-target="#updateUserModal">
                                        <i class="fa fa-user-o">修改</i>
                                    </button>

                                    <button type="button" class="btn btn-danger "
                                            data-id="${dormitory.id}" data-toggle="modal"
                                            onclick="" data-target="#delUserModal">
                                        <i class="fa fa-user-times">删除</i>
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
                                    <a href="${pageContext.request.contextPath}/dormitory?method=list&currentPage=1&key=${requestScope.key}&value=${requestScope.value}">首页</a>
                                    <a href="${pageContext.request.contextPath}/dormitory?method=list&currentPage=${p.currentPage-1}&key=${requestScope.key}&value=${requestScope.value}">上一页</a>
                                    <a href="#">下一页</a>
                                    <a href="${pageContext.request.contextPath}/dormitory?method=list&currentPage=${p.totalPage}&key=${requestScope.key}&value=${requestScope.value}">尾页</a>
                                </c:when>
                                <c:when test="${p.currentPage==1}">
                                    <a href="${pageContext.request.contextPath}/dormitory?method=list&currentPage=1&key=${requestScope.key}&value=${requestScope.value}">首页</a>
                                    <a href="#">上一页</a>
                                    <a href="${pageContext.request.contextPath}/dormitory?method=list&currentPage=${p.currentPage+1}&key=${requestScope.key}&value=${requestScope.value}">下一页</a>
                                    <a href="${pageContext.request.contextPath}/dormitory?method=list&currentPage=${p.totalPage}&key=${requestScope.key}&value=${requestScope.value}">尾页</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/dormitory?method=list&currentPage=1&key=${requestScope.key}&value=${requestScope.value}">首页</a>
                                    <a href="${pageContext.request.contextPath}/dormitory?method=list&currentPage=${p.currentPage-1}&key=${requestScope.key}&value=${requestScope.value}">上一页</a>
                                    <a href="${pageContext.request.contextPath}/dormitory?method=list&currentPage=${p.currentPage+1}&key=${requestScope.key}&value=${requestScope.value}">下一页</a>
                                    <a href="${pageContext.request.contextPath}/dormitory?method=list&currentPage=${p.totalPage}&key=${requestScope.key}&value=${requestScope.value}">尾页</a>
                                </c:otherwise>
                            </c:choose>
                            <%
                                Page pp = (Page) request.getAttribute("page");
                            %>
                            <select name="currentPage" id="Select" onchange="window.location=this.value">
                                <%for (int i = 1; i <= pp.getTotalPage(); i++) {%>
                                <option value="/dormitory?method=list&currentPage=<%=i%>&key=${requestScope.key}&value=${requestScope.value}"
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
                <!-- add框示例（Modal） -->
                <form method="post" action="/dormitory?method=save" class="form-horizontal" style="margin-top: 0px" role="form"
                      id="form_data1" style="margin: 20px;">
                    <div class="modal fade" id="addUserModal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel1">添加宿舍信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">
                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">楼宇</label>
                                            <div class="col-sm-9">
                                                <select class="form-control" name="buildingId">
                                                    <c:forEach items="${buildingList}" var="building">
                                                        <option class="buildingId" value="${building.id}">${building.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label  class="col-sm-3 control-label">名称</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control"
                                                       name="name" value="" placeholder="请输入姓名">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label  class="col-sm-3 control-label">几人间</label>
                                            <div class="col-sm-9">
                                                <select class="form-control" name="type">
                                                    <option value="4">4</option>
                                                    <option value="6">6</option>
                                                    <option value="8">8</option>
                                                    <option value="10">10</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">联系电话</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control"
                                                       name="telephone" value="" placeholder="">
                                            </div>
                                        </div>

                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" class="btn btn-primary">提交</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>

                <!-- update框示例（Modal） -->
                <form method="post" action="/dormitory?method=update" class="form-horizontal" style="margin-top: 0px" role="form"
                      id="form_data2" style="margin: 20px;">
                    <div class="modal fade" id="updateUserModal" tabindex="-1"
                         role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal"
                                            aria-hidden="true">x</button>
                                    <h4 class="modal-title" id="myModalLabel2">用户信息</h4>
                                </div>
                                <div class="modal-body">
                                    <form class="form-horizontal" role="form">

                                        <div class="form-group">
                                            <label  class="col-sm-3 control-label">ID</label>
                                            <div class="col-sm-9">
                                                <input type="text" readonly required class="form-control" id="id"
                                                       name="id">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label class="col-sm-3 control-label">名称</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="name"
                                                       name="name" value="" placeholder="请输入姓名">
                                            </div>
                                        </div>

                                        <div class="form-group">
                                            <label  class="col-sm-3 control-label">联系电话</label>
                                            <div class="col-sm-9">
                                                <input type="text" required class="form-control" id="telephone"
                                                       name="telephone" value="" placeholder="">
                                            </div>
                                        </div>

                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" class="btn btn-primary">提交</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>

                <!-- 删除模态框示例（Modal） -->
                <form method="post" action="/dormitory?method=delete"
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
                                                       name="tab" placeholder="" value="dor_admin"> <input
                                                    type="hidden" class="form-control" id="id2"
                                                    name="id2" placeholder="">
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                                    <button type="submit" class="btn btn-danger">删除</button>
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

<script>
    $('#updateUserModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var id = button.data('id')
        var name = button.data('name')
        var telephone = button.data('telephone')
        var modal = $(this)

        modal.find('.modal-title').text('修改宿舍信息')
        modal.find('#id').val(id)
        modal.find('#name').val(name)
        modal.find('#telephone').val(telephone)
    })

    $('#delUserModal').on('show.bs.modal', function(event) {
        var button = $(event.relatedTarget)
        var id = button.data('id')
        var modal = $(this)
        modal.find('.modal-title').text('删除宿舍信息')
        modal.find('#deleteLabel').text('是否删除此条信息')
        modal.find('#id2').val(id)
    })
</script>
<script>
    function selectPage() {
        var num = $("#select").val();
        window.location.href = "/dormitory?method=list&currentPage=" + num;
    }
</script>
</body>

</html>