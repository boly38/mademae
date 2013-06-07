function async_get(addr,func) {
    $.ajax({
        url: addr,
        success: func
    });
}

/*
  http://www.jquery4u.com/snippets/url-parameters-jquery/
 */
$.urlParam = function(name){
  var results = new RegExp('[\\?&amp;]' + name + '=([^&amp;#]*)').exec(window.location.href);
  if (results == null) {
    return 0;
  }
  return results[1] || 0;
}

$.urlParameter = function(name){
  return decodeURIComponent($.urlParam(name));
}

function consoleInfo(msg) {
  if (typeof console == 'undefined') {
    return;
  }
  console.info(msg);
}