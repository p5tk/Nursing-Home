//Get shift type from helper name
class DailySchedule {
	  constructor(startTime,endTime,activity) {
	    this.startTime = startTime;
	    this.endTime = endTime;
	    this.activity = activity;
	  }
	  getTime() {
		    return this.time;
		}
	}

function doit_onkeypress(event){
	 if (event.keyCode == 13 || event.which == 13){
         event.preventDefault();      
         fire_ajax_submit();
         ajax_date();
         
         $('.buttons').hide();
         $('.tab-content').hide();
         
         //make elder name empty
         $('.checkedElder1').empty();
         $('.checkedElder2').empty();
         $('.checkedElder3').empty();
         $('.checkedElder4').empty();
         $('.checkedElder5').empty();
         
         //make modal checkbox empty
         $('.elderNameList1').attr('checked', false);
         $('.elderNameList2').attr('checked', false);
         $('.elderNameList3').attr('checked', false);
         $('.elderNameList4').attr('checked', false);
         $('.elderNameList5').attr('checked', false);
         
         //make remark empty
         $('.remarks').val('');
         
         //remove other activity value
         $('#txtactivity').val('');
         $('#txtstart').val('');
         $('#txtend').val('');
         
         var active_tab_selector = $('.nav-tabs > li.active > a').attr('href');
 	    $(active_tab_selector).removeClass('active');
	    $(active_tab_selector).addClass('hide');
     }
}

//Tab and activity names according to shift types
function getTimes(shift){
	if(shift=="morning"){
		return [
			   new DailySchedule("7:00","9:00","出勤・起床介助、洗面介助・排せつ介助・朝食介助・口腔ケア"),
			   new DailySchedule("9:00","11:00","入浴介助　（1人15分）"),
			   new DailySchedule("11:30","13:00","昼食の配膳を介助・食休みのためにベッドに横になってくださる"),
			   new DailySchedule("13:30","15:00","おやつとお茶の準備・ラジオ体操・リハビリなど・退社"),
			   ];
	}
	
     if(shift=="day"){
		return [
			   new DailySchedule("8:30","11:00","出勤・入浴介助　（1人15分）"),
			   new DailySchedule("11:30","13:00","昼食の配膳を介助・食休みのためにベッドに横になってくださる"),
			   new DailySchedule("13:30","15:00","おやつとお茶の準備・ラジオ体操・リハビリなど"),
			   new DailySchedule("15:00","17:30","おやつの配膳・多少の食事介助カーテンを閉めて落ち着く環境づくり・夜勤者に申し送りをして退社"),
			   ];
	}
	
	if(shift=="evening"){
		return [
			   new DailySchedule("11:30","13:00","出勤・昼食の配膳を介助・食休みのためにベッドに横になってくださる"),
			   new DailySchedule("13:30","15:00","おやつとお茶の準備・ラジオ体操・リハビリなど"),
			   new DailySchedule("15:00","17:30","おやつの配膳・多少の食事介助・カーテンを閉めて落ち着く環境づく"),
			   new DailySchedule("17:30","20:00","離床介助・夕食介助・口腔ケア・排せつ介助・就寝介助・服薬介助・退社"),
			   ];
	}
	
	if(shift=="night"){
		return  [
			   new DailySchedule("17:30","19:00","申し送り・離床介助・夕食介助・口腔ケア"),
			   new DailySchedule("19:00","21:00","排せつ介助・就寝介助・服薬介助"),
			   new DailySchedule("21:00","7:00","巡視・体位変換（寝返り介助）・おむつ交換・トイレ誘導・コール対応"),
			   new DailySchedule("7:00","9:00","起床介助、洗面介助・排せつ介助・朝食介助・口腔ケア・申し送り・退社"),
			   ];
	}
	
	return null;
	 
}
//Validate task date has already inputted or not
function dateValidation(dateErr){
	if(dateErr.length != 0){
	alert(dateErr);
	 $('#date').val('');
	 $('#helperName').val('');
	 $('.nav-tabs').empty();
	}
	
}
//Generate tabs according to shift type
function generateTab(shift){
	var tabName = getTimes(shift);
	
	$('.nav-tabs').empty();
	$('.activity-tab').empty();
	if(tabName == null){	
	}else{
	
		for (i = 0; i < tabName.length; i++) {
			
		  $('.nav-tabs').append('<li class="nav-item"><a class="nav-link border" data-toggle="tab" href="#tab'+i+'">'+tabName[i].startTime+'-'+tabName[i].endTime+'</a></li>');
		  $('.activity-tab'+i).text(tabName[i].activity);

		}
	
		  
		 $('.nav-tabs').append('<li class="nav-item"><a class="nav-link border" data-toggle="tab" href="#tab">他の作業</a></li>');

	}
	     $('.buttons').html('<div class="row">'+
	  			'<div class="btn-group">'+
	  			'<button type="submit" id="btnSubmit" class="btn btn-lg btn-spacing btnSubmit btn-success">登録</button>'+
	  			'<button type="reset"  id="btnClear" class="btn btn-lg btnClear btn-secondary">クリア</button>'+ 
	  			'</div>'+
	  			'</div>');

	
	  
	  $('.nav-tabs').on('click','li > a',function(event){
	    event.preventDefault();//stop browser to take action for clicked anchor
	     
	    //get displaying tab content jQuery selector
	    var active_tab_selector = $('.nav-tabs > li.active > a').attr('href');
	        
	    //find actived navigation and remove 'active' css
	   /* var actived_nav = $('.nav-tabs > li.active');
	    actived_nav.removeClass('active');*/
	   
	    //add 'active' css into clicked navigation
	    //$(this).parents('li').addClass('active');

	    //hide displaying tab content
	    $(active_tab_selector).removeClass('active');
	    $(active_tab_selector).addClass('hide');

	    //show target tab content
	    var target_tab_selector = $(this).attr('href');
	    
	    $(target_tab_selector).removeClass('hide');
	    $(target_tab_selector).addClass('active');
	   
	    
	    $('.tab-content').show();
	    $('.buttons').show();
	    
	    
	  });
}
//modal checkbox validation and show elders
$("#chkbox1").click(function(){
	 checked = $(".elderNameList1:checkbox:checked").length;

     if(!checked) {
       alert("Please choose one or more elders!");
       return false;
     }
    
	 var elderList= [];	
       $.each($('.elderNameList1:checked'), function(){            
           elderList.push($(this).parent().find("label").text());
      });
        $('.checkedElder1').text(elderList.join(", "));       
});

$("#chkbox2").click(function(){
	 checked = $(".elderNameList2:checkbox:checked").length;

     if(!checked) {
       alert("Please choose one or more elders!");
       return false;
     }
	var elderList= [];	
    $.each($('.elderNameList2:checked'), function(){            
        elderList.push($(this).parent().find("label").text());
    });
  
    $('.checkedElder2').text(elderList.join(", "));        
});

$("#chkbox3").click(function(){
	 checked = $(".elderNameList3:checkbox:checked").length;

     if(!checked) {
       alert("Please choose one or more elders!");
       return false;
     }
	var elderList= [];	
    $.each($('.elderNameList3:checked'), function(){            
        elderList.push($(this).parent().find("label").text());
    });
  
    $('.checkedElder3').text(elderList.join(", "));          
});

$("#chkbox4").click(function(){
	 checked = $(".elderNameList4:checkbox:checked").length;

     if(!checked) {
       alert("Please choose one or more elders!");
       return false;
     }
	var elderList= [];	
    $.each($('.elderNameList4:checked'), function(){            
        elderList.push($(this).parent().find("label").text());
    });
  
    $('.checkedElder4').text(elderList.join(", "));          
});

$("#chkbox5").click(function(){
	var elderList= [];	
    $.each($('.elderNameList5:checked'), function(){            
        elderList.push($(this).parent().find("label").text());
    });
  
    $('.checkedElder5').text(elderList.join(", "));       
});

//submit button validation
$('.buttons').on('click', '.btnSubmit', function(){

	 checked1 = $(".elderNameList1:checkbox:checked").length;
	 checked2 = $(".elderNameList2:checkbox:checked").length;
	 checked3 = $(".elderNameList3:checkbox:checked").length;
	 checked4 = $(".elderNameList4:checkbox:checked").length;

    if(!checked1 || !checked2 || !checked3 || !checked4 ) {
      alert("Please choose one or more elders for daily activities!");
      return false;
    }
    //check when other activity elder list is checked, other task information must be filled
    if($(".elderNameList5:checkbox:checked").length>0){
    	if($('#txtactivity').val().length == 0 || $('#txtstart').val().length == 0 || $('#txtend').val().length == 0){
    	  	   alert("Please enter activity name, start time and end time for other activity!");
    	  	   return false;
    	}
    }
    if($('#txtactivity').val().length == 0 && $('#txtstart').val().length == 0 && $('#txtend').val().length == 0){
    	   return true;
   }else{
    if ( $('#txtactivity').val() != null ){
  	   if($('#txtstart').val().length == 0 || $('#txtend').val().length == 0 ){
  	   alert("Please enter activity name, start time and end time for other activity!");
  	   return false;
  	   }
     }
    if ( $('#txtstart').val() != null ){
 	   if($('#txtactivity').val().length == 0 || $('#txtend').val().length == 0 ){
 	   alert("Please enter activity name, start time and end time for other activity!");
 	   return false;
 	   }
    }
    if ( $('#txtend').val() != null ){
  	   if($('#txtactivity').val().length == 0 || $('#txtstart').val().length == 0 ){
  	   alert("Please enter activity name, start time and end time for other activity!");
  	   return false;
  	   }
     }
   }
    
    var startTime = $('#txtstart').val();   
    var endTime   = $('#txtend').val(); 
    
    st = minFromMidnight(startTime);
    et = minFromMidnight(endTime);
    if(st>et){
        alert("End time should be greater than start time!");
        return false;
    }
    
    function minFromMidnight(tm){
     var ampm= tm.substr(-2)
     var clk = tm.substr(0, 5);
     var m  = parseInt(clk.match(/\d+$/)[0], 10);
     var h  = parseInt(clk.match(/^\d+/)[0], 10);
     h += (ampm.match(/pm/i))? 12: 0;
     return h*60+m;
    }  
    
});

//form submit enter key disabled
$("form").keypress(function(e){
    if(e.keyCode == 13) {
        e.preventDefault();
        return false;
    }
})
//clear button click
$('.buttons').on('click', '.btnClear', function(){
	$('.checkedElder1').empty();
    $('.checkedElder2').empty();
    $('.checkedElder3').empty();
    $('.checkedElder4').empty();
    $('.checkedElder5').empty();
});
//ajax function to get shift type from searched helper name
function fire_ajax_submit() {

    var search = {}
    search["helperName"] = $("#helperName").val();
    search["helperId"] = $("#helperId").val();


    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/getShiftType",
        data: JSON.stringify(search),
 
        cache: false,
        timeout: 600000,
        success: function (data) {
        	generateTab(data);

        },

    });
}
function ajax_date() {
	
	 var search = {}
	 search["helperName"] = $("#helperName").val();	
	 search["date"] = $("#date").val();
	 search["helperId"] = $("#helperId").val();
    //search["email"] = $("#email").val();


    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/getHelperDate",
        data: JSON.stringify(search),
 
        cache: false,
        timeout: 600000,
        success: function (data) {
        	dateValidation(data);

        },

    });
}
