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


$(document).ready(function () {
	
    $("#search-form").submit(function (event) {
    	
        //stop submit the form, we will post it manually.
        event.preventDefault();

        fire_ajax_submit();

    });

});

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
			   new DailySchedule("13:30","15:00","おやつとお茶の準備・ラジオ体操・リハビリなど）"),
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

function generateTab(shift){
	var tabName = getTimes(shift);
	$('.nav-tabs').empty();
	$('.ui-page').empty();
	if(tabName == null){
		
	}else{
	
		for (i = 0; i < tabName.length; i++) {
			
		    $('.nav-tabs').append('<li class="nav-item"><a class="nav-link border" data-toggle="tab" href="#tab'+i+'">'+tabName[i].startTime+'-'+tabName[i].endTime+'</a></li>');
		    $('.ui-page').append(
		    	'<section id="tab'+i+'" class="tab-content hide border"> '+
		    	'<label>作業: '+tabName[i].activity+'</label>'+
		    	'<button type="submit" class="btnChoose" >高齢者を選択します</button><br>'+
	 		 	'<label>高齢者:</label><br> '+
	 		 	'<div class="form-group w-50">'+
	 		 		'<label>備考:</label>'+
	  			'<textarea class="form-control" aria-label="備考:" name="txtRemarks" rows="5"></textarea>'+  
	  		'</div>'+
	  		'</section>');
		}
		 $('.nav-tabs').append('<li class="nav-item"><a class="nav-link border" data-toggle="tab" href="#othertab">'+他の作業+'</a></li>');
		    $('.ui-page').append(
		    	'<section id="othertab" class="tab-content hide border"> '+
		    	'<label>作業:</label><input type="text" name="txtactivity" id="txtactivity"><br>'+
		    	'<label>開始時間:</label><input type="text" name="txtstar" id="txtstart"><br>'+
		    	'<label>終了時間:</label><input type="text" name="txtend" id="txtend"><br>'+
		    	'<button type="submit" class="btnChoose" >高齢者を選択します</button><br>'+
	 		 	'<label>高齢者:</label><br> '+
	 		 	'<div class="form-group w-50">'+
	 		 		'<label>備考:</label>'+
	  			'<textarea class="form-control" aria-label="備考:" name="txtRemarks" rows="5"></textarea>'+  
	  		'</div>'+
	  		'</section>');
	}
	
	
	  
	  $('.nav-tabs').on('click','li > a',function(event){
	    event.preventDefault();//stop browser to take action for clicked anchor

	    //get displaying tab content jQuery selector
	    var active_tab_selector = $('.nav-tabs > li.active > a').attr('href');					

	    //find actived navigation and remove 'active' css
	    var actived_nav = $('.nav-tabs > li.active');
	    actived_nav.removeClass('active');

	    //add 'active' css into clicked navigation
	    $(this).parents('li').addClass('active');

	    //hide displaying tab content
	    $(active_tab_selector).removeClass('active');
	    $(active_tab_selector).addClass('hide');

	    //show target tab content
	    var target_tab_selector = $(this).attr('href');
	    
	    $(target_tab_selector).removeClass('hide');
	    $(target_tab_selector).addClass('active');
	  });
}

function fire_ajax_submit() {

    var search = {}
    search["helperName"] = $("#helperName").val();
    //search["email"] = $("#email").val();


    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/getShiftType",
        data: JSON.stringify(search),
 
        cache: false,
        timeout: 600000,
        success: function (data) {
        	//alert(data);
        	generateTab(data);

        },
        error: function (e) {
        	//alert(e);
            var json = "<h4>Ajax Response</h4><pre>"
                + e.responseText + "</pre>";
            $('#feedback').html(json);

            console.log("ERROR : ", e);
            $("#btn-search").prop("disabled", false);

        }
    });
    
    
    
	
   
   
  
	

}