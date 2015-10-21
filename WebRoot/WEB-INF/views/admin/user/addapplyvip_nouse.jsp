<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>会员新增</title>
    <link rel="stylesheet" href="<%=basePath%>res/styles/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>res/styles/public.css"/>
</head>

<body>
     <div class="row row-flow">
                <form id="addVipForm" class="form-horizontal col-xs-12" role="form">
                    <div class="form-group">
                        <label class="col-xs-2 control-label">真实姓名</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control valid-input"
                                   data-valid-rule="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">社区虚拟身份</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control valid-input"
                                                               data-valid-rule="required">
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">性别</label>

                        <div class="col-xs-5">
                            <select class="form-control">
                                <option value="0">汉子</option>
                                <option value="1">女汉子</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">身份证</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control valid-input"
                                                               data-valid-rule="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">电话号码</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control valid-input"
                                                               data-valid-rule="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">邮箱</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control valid-input"
                                                               data-valid-rule="required">
                        </div>
                    </div>
                    <hr/>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">微信</label>

                        <div class="col-xs-5">
                            <input type="text" class="form-control valid-input"
                                                               data-valid-rule="required">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">兴趣领域</label>

                        <div class="col-xs-5">
                            <select class="form-control">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">擅长领域</label>

                        <div class="col-xs-5">
                            <select class="form-control">
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">自我介绍</label>

                        <div class="col-xs-5">
                            <textarea rows="4" class="form-control valid-input"
                                   data-valid-rule="required"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">团队介绍</label>

                        <div class="col-xs-5">
                            <textarea rows="4" class="form-control valid-input"
                                                               data-valid-rule="required"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">二维码</label>

                        <div class="col-xs-5">
                            <input class="valid-input" data-valid-rule="required" type="file"/>
                        </div>
                    </div>
                    <hr/>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">用户图像</label>

                        <div class="col-xs-5">
                            <input class="valid-input" data-valid-rule="required"  type="file" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">用户大图</label>

                        <div class="col-xs-5">
                            <input class="valid-input" data-valid-rule="required"  type="file" />
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">会员号</label>

                        <div class="col-xs-5">
                            <input data-valid-rule="required" type="text"  class="form-control valid-input"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">昵称</label>

                        <div class="col-xs-5">
                            <input data-valid-rule="required"  type="text" class="form-control valid-input"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">行业</label>

                        <div class="col-xs-5">
                            <select class="form-control valid-input"  data-valid-rule="required"></select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">职业</label>

                        <div class="col-xs-5">
                            <select class="form-control valid-input"
                                    data-valid-rule="required"></select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">所在团队名</label>

                        <div class="col-xs-5">
                            <input class="form-control valid-input" type="text" data-valid-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">团队url</label>

                        <div class="col-xs-5">
                            <input class="form-control valid-input" type="text" data-valid-rule="required"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">会员级别</label>

                        <div class="col-xs-5">
                            <select class="form-control">
                                <option value="0">会员</option>
                                <option value="1">共建者</option>
                                <option value="2">雁行家</option>
                                <option value="3">智库</option>
                                <option value="4">投资人</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-2 control-label">视频链接</label>

                        <div class="col-xs-5">
                            <input class="form-control valid-input" type="text" data-valid-rule="required"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">创建时间</label>

                        <div class="col-xs-5">
                            <input class="form-control valid-input" type="text" data-valid-rule="required"/>
                        </div>
                    </div>
                    <hr/>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">是否空间展示</label>

                        <div class="col-xs-5">
                            <select class="form-control">
                                <option value="0">不展示</option>
                                <option value="1">展示</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-xs-2 control-label">是否会员展示</label>

                        <div class="col-xs-5">

                            <select class="form-control">
                                <option value="0">不展示</option>
                                <option value="1">展示</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="container">
                            <button type="submit" class="col-xs-3 col-xs-offset-3 btn btn-primary">提交</button>
                        </div>
                    </div>

                </form>
    </div>

    <script type="text/javascript" src="<%=basePath%>res/scripts/jquery.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/validation.js"></script>
	<script type="text/javascript" src="<%=basePath%>res/scripts/public.js"></script>
    <script type="text/javascript" src="<%=basePath%>res/scripts/bootstrap.js"></script>
</body>
</html>
