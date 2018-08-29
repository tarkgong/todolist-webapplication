var main = {
    init : function () {
        var _this = this;
        var columns = ['id','title','createdDate','modifiedDate','isfinish'];
        
        var table = $('#todoTable').DataTable( {
        	serverSide: true,
        	searching: false,
            'ajax': {
            	url: '/todos/',
                data: function ( params ) {
                	var requestParam = {};
                	var orders = [];
                	
                	requestParam.start = params.start;
                	requestParam.length = params.length;
                	                	
                	params.order.forEach(function(element) {
                		orders.push(element.column + "_" +element.dir)
                	});
                	
                	requestParam.columns = columns;
                	requestParam.orders = orders;

                	return requestParam;
                },
                dataFilter: function(data){
                    var json = jQuery.parseJSON( data );
                    json.recordsTotal = json.total;
                    json.recordsFiltered = json.total;
                    json.data = json.todos;
         
                    return JSON.stringify( json ); // return JSON string
                }
            },
            columns: [
                { data: 'id' },
                { data: 'title' },
                { data: 'createdDate' },
                { data: 'modifiedDate' },
                { data: 'isfinish' }
            ],
            'columnDefs': [ {
                'targets': 1,
                'render': function ( data, type, row, meta ) {
                	var referList = row.todoRefer;

                	referList.forEach(function(element) {
                		data += ' @' + element.todoReferId.referId;
                	});
        
                	return data;
                }
            },
            {
                'targets': 4,
                'render': function ( data, type, row, meta ) {
                	if(row.isfinish){
                		return "완료";
                	}else{
                		return '<button type="button" id="btn-finish" class="btn btn-secondary btn-sm"" data-dismiss="modal">완료 처리</button>'
                	}
                }
            }]
        });
        
        $('#todoTable tbody').on( 'click', 'td', function () {
            var clickRow = table.cell( this )[0];
        	var data = table.row(clickRow[0].row).data();
        	var trObject = $(this).parent();
        	
            if ( trObject.hasClass('selected') ) {
            	trObject.removeClass('selected');
                selectedData = null;
            }
            else {
                table.$('tr.selected').removeClass('selected');
                trObject.addClass('selected');
                
                selectedData = data;
                
            	if(clickRow[0].column !== 4){
            		$('#savaTodoModal').modal('show');
            	}
            }
        });
        
        $('#savaTodoModal').on('show.bs.modal', function(e) {        	
        	$('#todoReferId option').remove();
        	
        	var url = '/todos/select';

        	if(selectedData){
        		url = '/todos/' + selectedData.id + '/select';
        	}
        	
            $.ajax({
                type: 'GET',
                url: url,
            }).done(function(data) {
              	data.forEach(function(element) {

              		if(!selectedData || selectedData.id !== element.id){
	            		var option = $('<option value=' + element.id + '>' + element.id + '</option>');
	            		$('#todoReferId').append(option);
              		}
            	});

            	if(selectedData){
                	$('#title').val(selectedData.title);
                	var referIds = [];
                	
                	selectedData.todoRefer.forEach(function(element) {
                		referIds.push(element.todoReferId.referId);
                	});
                	
            		$('#todoReferId').val(referIds);
            	}
            	
            }).fail(function (error) {
                alert(error);
            });
            

        });

        $('#btnSaveModal').on('click', function () {
            selectedData = null;
        });
        
        $('#btn-save').on('click', function () {
            _this.save();
        });
        
        $('#todoTable tbody').on( 'click', 'button', function () {
            var data = table.row( $(this).parents('tr') ).data();
            _this.finish(data);
        });
    },
    save : function () {
        var type = 'POST';
        var url = '/todos/';

    	if(selectedData){
    		type = 'PUT';
    		url = '/todos/' + selectedData.id;
    	}
    	
        var data = {};
        
        if($('#title').val())		data.title =  $('#title').val();
        if($('#todoReferId').val())	data.referIds =  $('#todoReferId').val();

        $.ajax({
            type: type,
            url: url,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('할일이 등록되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(error);
        });
    },
    finish : function (data){
        $.ajax({
            type: 'PUT',
            url: '/todos/' + data.id + '/status',
            dataType: 'json',
            contentType:'application/json; charset=utf-8'
        }).done(function() {
            alert('완료처리 되었습니다.');
            location.reload();

        }).fail(function (error) {
        	if(error.responseJSON.code === "TO001")
        		alert("완료되지 않은 할일이 존재합니다.");
        });    	
    }

};

main.init();