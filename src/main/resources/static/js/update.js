// (1) 회원정보 수정
function update(userId, event) {
    event.preventDefault(); // 폼태그 액션 막기
    let data = $("#profileUpdate").serialize();

    console.log(data);

    $.ajax({
        type: "put",
        url: `/api/user/${userId}`, // ''가 아니라 ``임
        data: data,
        contentType: "application/x-www-form-urlencoded; charset=utf-8",
        dataType: "json"
    }).done(res => {
        console.log("성공", res); // HttpStatus 상태코드 200번대
        location.href=`/user/${userId}`;
    }).fail(error => {  // HttpStatus 상태코드 200번대가 아닐 때
        if (error.data == null) {
            alert(error.responseJSON.message);  // (data : null, message : "찾을 수 없는 ID입니다.") 따라서 data.message (X), 그냥 message (O)
        } else {
            alert(JSON.stringify(error.responseJSON.data)); // JSON.stringify() : 자바스크립트 오브젝트를 JSON 문자열로 변경해줌
        }
    });
}