/*
* var = main {} 형태로 작성
* index라는 변수의 속성으로 function을 추가한 이유??
* 해당 js 파일 뿐만이 아니라 다른 곳에서도 init, save  function을 작성해놨다면
* 브라우저의 스코프는 공용 공간으로 쓰이기 때문에, 나중에 로딩된 js의 init, save가
* 먼저 로딩된 js의 function을 덮어쓰기 때문
* 중복된 이름을 구별하기 위해서 작성함
* */
var main={
    init: function(){
        var _this = this;
        $('#btn-save').on('click', function (){
            _this.save();
        });
    },
    save : function() {
        var data ={
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val()
        };

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function(){
            alert('글이 등록되었습니다.');
            window.location.href='/';
        }).fail(function (error){
            alert(JSON.stringify(error));
        });
    }
};

main.init();

