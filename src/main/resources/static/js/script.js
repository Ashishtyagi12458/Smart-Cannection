console.log("this is script file");

const toggleSidebar = () =>{
	if($(".sidebar").is(":visible")){
		$(".sidebar").hide();
		$(".content").css("margin-left","0%")
		
	}else{
		$(".sidebar").show()
				$(".content").css("margin-left","20%")
		
	}
	
};