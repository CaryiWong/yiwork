(function() {
  var template = Handlebars.template, templates = Handlebars.templates = Handlebars.templates || {};
templates['list'] = template({"1":function(container,depth0,helpers,partials,data) {
    return "now";
},"compiler":[7,">= 4.0.0"],"main":function(container,depth0,helpers,partials,data) {
    var stack1, helper, alias1=depth0 != null ? depth0 : {}, alias2=helpers.helperMissing, alias3="function", alias4=container.escapeExpression;

  return "<div class=\"activity\">\r\n    <div class=\"left_date "
    + ((stack1 = helpers["if"].call(alias1,(depth0 != null ? depth0.add_now : depth0),{"name":"if","hash":{},"fn":container.program(1, data, 0),"inverse":container.noop,"data":data})) != null ? stack1 : "")
    + "\">\r\n    <div class=\"time_wrap\">\r\n        <p class=\"noon\">"
    + alias4(((helper = (helper = helpers.Noon || (depth0 != null ? depth0.Noon : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"Noon","hash":{},"data":data}) : helper)))
    + "</p>\r\n        <p class=\"time\">"
    + alias4(((helper = (helper = helpers.time || (depth0 != null ? depth0.time : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"time","hash":{},"data":data}) : helper)))
    + "</p>\r\n    </div>\r\n        <p class=\"year\">"
    + alias4(((helper = (helper = helpers.year || (depth0 != null ? depth0.year : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"year","hash":{},"data":data}) : helper)))
    + "</p>\r\n        <p class=\"month\">"
    + alias4(((helper = (helper = helpers.month || (depth0 != null ? depth0.month : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"month","hash":{},"data":data}) : helper)))
    + "</p>\r\n        <P class=\"date\">"
    + alias4(((helper = (helper = helpers.date || (depth0 != null ? depth0.date : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"date","hash":{},"data":data}) : helper)))
    + "</P>\r\n        <p class=\"status "
    + alias4(((helper = (helper = helpers.s_class || (depth0 != null ? depth0.s_class : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"s_class","hash":{},"data":data}) : helper)))
    + "\">"
    + alias4(((helper = (helper = helpers.status || (depth0 != null ? depth0.status : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"status","hash":{},"data":data}) : helper)))
    + "</p>\r\n    </div>\r\n    <a class=\"right_banner\" href=\""
    + alias4(((helper = (helper = helpers.h5url || (depth0 != null ? depth0.h5url : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"h5url","hash":{},"data":data}) : helper)))
    + "\">\r\n        <div class=\"banner_box\"\r\n             style=\"background-image: url('"
    + alias4(((helper = (helper = helpers.cover || (depth0 != null ? depth0.cover : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"cover","hash":{},"data":data}) : helper)))
    + "')\">\r\n        </div>\r\n        <div class=\"title\">\r\n            <div class=\"title_pic\"><img src=\""
    + alias4(((helper = (helper = helpers.spacelogo || (depth0 != null ? depth0.spacelogo : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"spacelogo","hash":{},"data":data}) : helper)))
    + "\" alt=\"\"/></div>\r\n            <div class=\"title_text\">"
    + alias4(((helper = (helper = helpers.title || (depth0 != null ? depth0.title : depth0)) != null ? helper : alias2),(typeof helper === alias3 ? helper.call(alias1,{"name":"title","hash":{},"data":data}) : helper)))
    + "</div>\r\n        </div>\r\n    </a>\r\n</div>";
},"useData":true});
})();