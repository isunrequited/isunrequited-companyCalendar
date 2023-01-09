<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>

<!-- jqueary cdn -->
<script
  src="https://code.jquery.com/jquery-3.6.2.js"
  integrity="sha256-pkn2CUZmheSeyssYw3vMp1+xyub4m+e+QK4sQskvuo4="
  crossorigin="anonymous">
</script>

<!-- jqueary UI cdn -->
<script 
  src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.13.2/jquery-ui.min.js" 
  integrity="sha512-57oZ/vW8ANMjR/KQ6Be9v/+/h6bq9/l3f0Oc7vn6qMqyhvPd1cvKBRWWpzu0QoneImqr2SkmO4MSqU+RpHom3Q==" 
  crossorigin="anonymous" 
  referrerpolicy="no-referrer">
</script>

<!-- context path 변수 선언 -->
<script>
    var ctx = '<%=request.getContextPath()%>';
</script>

<!-- icon(fontawesome) cdn -->
<script 
  src="https://kit.fontawesome.com/4fa2583009.js" 
  crossorigin="anonymous">
</script>

<!-- console.log -->
<script>
  var console_log_flag = true;

  if (!console_log_flag) {
    console.log = function(){};
    console.warn = function(){};
    console.error = function(){};
  }
</script>

<!-- datepicker -->
<script>
$(function(){
  $.datepicker.regional["ko"] = {
    closeText: "CLOSE",
    prevText: "이전달",
    nextText: "다음달",
    currentText: "TODAY",
    monthNames: ["01월","02월","03월","04월","05월","06월", "07월","08월","09월","10월","11월","12월"],
    monthNamesShort: ["01","02","03","04","05","06", "07","08","09","10","11","12"],
    dayNames: ["SUN","MON","TUE","WED","THU","FRI","SAT"],
    dayNamesShort: ["SUN","MON","TUE","WED","THU","FRI","SAT"],
    dayNamesMin: ["SUN","MON","TUE","WED","THU","FRI","SAT"],
    weekHeader: "Wk",
    dateFormat: "yy-mm-dd",
    firstDay: 0,
    isRTL: false,
    showMonthAfterYear: true,
    yearSuffix: "년"
  };
  $.datepicker.setDefaults($.datepicker.regional["ko"]);
  

  $('.datepicker > input').datepicker({
    dateFormat:'yy년 mm월 dd일',
    showOn: "both", 
    buttonImage: ctx + "/resources/img/datepicker/calendar-week-solid-grey.png", 
    buttonImageOnly: true
  });

})

</script>


