class ShiftTime {
	  constructor(startTime,endTime) {
	    this.startTime = startTime;
	    this.endTime = endTime;
	  }
	  getTime() {
		    return this.time;
		}
	}


$(document).ready(function () {
        fire_ajax_submit();
});

function getTimes(shift){
	if(shift=="morning"){
		return [
			   new ShiftTime("7:00","15:00")
			   ];
	}
	
	if(shift=="day"){
		return [
			   new ShiftTime("8:30","17:30")
			   ];
	}
	
	if(shift=="evening"){
		return [
			   new ShiftTime("11:30","20:00")
			   ];
	}
	
	if(shift=="night"){
		return  [
			   new ShiftTime("17:30","9:00")
			   ];
	}
	
	return null;
	 
}

function display(shift){
	var shiftTime= getTimes(shift);
	 $('.start-time').append('<label>開始時間 :'+shiftTime.startTime +'</label>');
	 $('.end-time').append('<label>開始時間 :'+shiftTime.endTime +'</label>');

	}
	
function fire_ajax_submit() {
	
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/getShiftTime",
        data: JSON.stringify(${shift}),
        
        cache: false,
        timeout: 600000,
        success: function (data) {
        	alert(data);
        	display(data);

        },
    });
    
    
    
	
   
   
  
	

}