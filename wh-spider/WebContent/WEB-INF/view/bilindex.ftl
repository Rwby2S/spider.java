<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="renderer" context="webkit">
	<title>视频指数统计</title>
	<link rel="stylesheet" type="text/css" href="">
	<script type="text/javascript" src="${scriptRoot}jquery-1.7.1.min.js"></script>
	<script>
		$(document).ready(function(){
			$("#search-btn").click(function){
				var skey = $("search").val();
				var sort = "desc";
				toPage(1);
			}
			list();
		});
	
		function toPage(page){
			$("input[name=pageIndex]").val(page);
			$("input[name=pageSize]").val($("select[name=pageSize] option:selected").val());
			list();
		}

		//加载数据
		function list(){
			$.ajax({
				url : "$webinfo}tv'list.do",
				type : 'POST',
				dataType : 'json',
				data : $("form").serialize(),
				success : function(data){
					$table = $("#tt");
					$("#tt").html("");
					var list = data.list;
					for(var i = 0; i < list.length; i++){
						$tr = $("<tr></tr>");
						$td1 = $("<td><a href='$coin.[i]')
					}
				}
			})
		}
	</script>
	</head>
	<body>
		<div class="content"> 
			<div class="float-right"></div>
		</div>
	</body>
	</html>
