var main = {
    init : function () {
        var _this = this;
        var selectedId;
        
        var table = $('#todoTable').DataTable( {
            'ajax': {
            	url: '/todo/',
            	dataSrc: ''
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
              } ]
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
                url: 'todo/',
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
        var url = '/todo/';

    	if(selectedData){
    		type = 'PUT';
    		url = '/todo/' + selectedData.id;
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