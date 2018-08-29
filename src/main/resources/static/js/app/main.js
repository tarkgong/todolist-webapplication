var main = {
    init : function () {
        var _this = this;
        var selectedId;
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
                		console.log(element)
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
            }]
        });
        
        $('#todoTable tbody').on( 'click', 'tr', function () {
            if ( $(this).hasClass('selected') ) {
                $(this).removeClass('selected');
                selectedData = null;
            }
            else {
                table.$('tr.selected').removeClass('selected');
                $(this).addClass('selected');
                
                var data = table.row( this ).data();
                selectedData = data;
                
                $('#savaTodoModal').modal('show');
            }
        });
        
        $('#savaTodoModal').on('show.bs.modal', function(e) {        	
        	$('#todoReferId option').remove();

            $.ajax({
                type: 'GET',
                url: '/todos/select',
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
            alert('글이 등록되었습니다.');
            location.reload();
        }).fail(function (error) {
            alert(error);
        });
    }

};

main.init();