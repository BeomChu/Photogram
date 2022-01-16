// (1) 회원정보 수정
function update(userId,event) {
    event.preventDefault(); //폼태그 액션 막기

    let data=$("#profileUpdate").serialize(); //key=value 혀태로 받아오기위함 serialize

    console.log(data);

    $.ajax({
        type:"put",
        url:`/api/user/${userId}`,
        data:data,
        contentType:"applecation/x-www-form-urlencoded; charset=utf-8",
        dataType:"json"
    }).done(res=>{ //Http상태코드 200번대이면 성공임 (done이 나옴)
        console.log("성공",res);
        location.href= `/user/${userId}`;
    }).fail(error=>{ //Http상태코드 200번대 아니면 실패임 (200번대가 안나옴)
        // console.log("실패",error.responseJSON.data);
        // alert(JSON.stringify(error.responseJSON.data));
        if(error.data==null){
            alert(error.responseJSON.message);
        }else{
            alert(JSON.stringify(error.responseJSON.data));
        }
    });
}