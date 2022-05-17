// <input type="date" name="calanderDay" id="calanderDay" />에 아무 값도 들어오지 않을 경우, 서버에서 예외가 발생하여 오늘 값으로 응답합니다.
// 이를 알려주는 부분입니다.
function  checkCalanderDay() {

    var calanderDay = document.getElementById('calanderDay').value;

    // 길이가 4개 미만일 경우(date값이 선택되지 않은 채로, submit을 눌렀을 경우) 발동되는 if문입니다.
    if (calanderDay.length < 4) {
        alert("날짜를 선택해주세요.");
    }
    
    document.getElementById('calanderFrm').submit();
}

// 기록 수정 부분에서, 수정 부분에서 아무 글도 쓰지 않고, 버튼 눌렀을 경우
function clickSave() {
    if ((document.getElementById('timeChange').value == "") &&
            (document.getElementById('contentChange').value == "") &&
            (document.getElementById('tag1Change').value == "") &&
            (document.getElementById('tag2Change').value == "") &&
            (document.getElementById('tag3Change').value == "") &&
            (document.getElementById('tag4Change').value == "")) {
        alert("수정할 부분의 버튼을 누르시고 내용을 기입하신 뒤 눌러주세요.")
    }
}
// 기록 삭제 버튼 눌렀을 때
function clickDelete() {
    if (!confirm("해당 기록을 지우시겠습니까?")) {
        document.getElementById('changeFrm').action = "recordsShow";
    } else {
        document.getElementById('changeFrm').submit();
    }
}

// 빠른 내용 추가 버튼 눌렀을 때
function quickSave() {
    // 
    $.ajax({
        type : "POST",
        url : "",  //@@ Url연결 안되는 부분 수정하기
        error : function(error) {
            console.log("error");
            alert("error");
        },
        success : quickSaveFrm
    });

}

function quickSaveFrm() {
    document.getElementById('forOnlyDatefrm').submit();
}