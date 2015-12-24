(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['list'] = template({"1":function(container,depth0,helpers,partials,data) {
    return "<span class=\"signal-bg\"></span>";
},"3":function(container,depth0,helpers,partials,data) {
    return "individual";
},"5":function(container,depth0,helpers,partials,data) {
    var stack1;

  return ((stack1 = helpers["if"].call(depth0 != null ? depth0 : {},(depth0 != null ? depth0.team : depth0),{"name":"if","hash":{},"fn":container.program(6, data, 0),"inverse":container.program(8, data, 0),"data":data})) != null ? stack1 : "");
},"6":function(container,depth0,helpers,partials,data) {
    return "team";
},"8":function(container,depth0,helpers,partials,data) {
    return "company";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : {}, alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<div class=\"list-box\" data-action=\""
    + alias4(((helper = (helper = helpers.contextUrl || (depth0 != null ? depth0.contextUrl : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"contextUrl","hash":{},"data":data}) : helper)))
    + "\">\r\n    <div class=\"signal\">\r\n        "
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.individual : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\r\n        <img src=\"/images/pages/server/"
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.individual : depth0),{"name":"if","hash":{},"fn":container.program(3, data, 0),"inverse":container.program(5, data, 0),"data":data})) != null ? stack1 : "")
    + ".png\" alt=\"\"/>\r\n    </div>\r\n    <div class=\"list-box-left\" style=\"background: url("
    + alias4(((helper = (helper = helpers.headPic || (depth0 != null ? depth0.headPic : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"headPic","hash":{},"data":data}) : helper)))
    + ") no-repeat center center/auto 100%\">\r\n        <div class=\"location\"><span class=\"city\">"
    + alias4(((helper = (helper = helpers.city || (depth0 != null ? depth0.city : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"city","hash":{},"data":data}) : helper)))
    + "</span></div>\r\n    </div>\r\n    <div class=\"list-box-right\">\r\n        <p class=\"title\">"
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "</p>\r\n        <p class=\"user\">"
    + alias4(((helper = (helper = helpers.user || (depth0 != null ? depth0.user : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"user","hash":{},"data":data}) : helper)))
    + "</p>\r\n        <div class=\"text\">\r\n            "
    + alias4(((helper = (helper = helpers.context || (depth0 != null ? depth0.context : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"context","hash":{},"data":data}) : helper)))
    + "\r\n        </div>\r\n    </div>\r\n</div>\r\n\r\n\r\n";
},"useData":true});
})();