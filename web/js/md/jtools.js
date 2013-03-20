  function async_get(addr,func) {
    $.ajax({
    url: addr,
    success: func
    });
  }