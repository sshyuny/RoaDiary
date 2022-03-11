function fromDatetoString(date) {
    var yD = date.getFullYear();
    var mD = date.getMonth()+1;
    var dD = date.getDate();
    yearString = yD.toString();
    if(mD <= 9) {
        monthString = "0" + mD.toString();
    } else {
        monthString = mD.toString();
    }
    if(dD <= 9) {
        dayString = "0" + dD.toString();
    } else {
        dayString = dD.toString();
    }
    var dateS = yearString + "-" + monthString + "-" + dayString;
    return dateS;
}

function returnPlusDate() {
    var dateString = initialDate();
    var tempDate = new Date(dateString);
    tempDate.setDate(tempDate.getDate() + 1);
    var resultDate = fromDatetoString(tempDate);

    document.getElementById('someday').value = resultDate;
    document.getElementById('frm').submit();
}
function returnMinusDate() {
    var dateString = initialDate();
    var tempDate = new Date(dateString);
    tempDate.setDate(tempDate.getDate() - 1);
    var resultDate = fromDatetoString(tempDate);
    document.getElementById('someday').value = resultDate;
    document.getElementById('frm').submit();
}
function returnTodayDate() {
    var tempDate = new Date();
    var resultDate = fromDatetoString(tempDate);
    document.getElementById('someday').value = resultDate;
    document.getElementById('frm').submit();
}

function initialDate() {

    var modelDateString = document.getElementById('someday').value;
    // 서버 RecordsController.recordsShowing에서 someday value를 "오늘", "어제" 등으로 수정했을 경우,
    // 아래와 같이 따로 날짜 수정 해주어야 예외 피할 수 있음
    if (modelDateString == "" || modelDateString == "오늘") {
        var todayDate = new Date();
        var dateString = fromDatetoString(todayDate);
    } else if (modelDateString == "어제") {
        var modelDate = new Date();
        modelDate.setDate(modelDate.getDate() - 1);
        var dateString = fromDatetoString(modelDate);
    } else if (modelDateString == "그제") {
        var modelDate = new Date();
        modelDate.setDate(modelDate.getDate() - 2);
        var dateString = fromDatetoString(modelDate);
    } else if (modelDateString == "내일") {
        var modelDate = new Date();
        modelDate.setDate(modelDate.getDate() + 1);
        var dateString = fromDatetoString(modelDate);
    } else if (modelDateString == "모레") {
        var modelDate = new Date();
        modelDate.setDate(modelDate.getDate() + 2);
        var dateString = fromDatetoString(modelDate);
    } else {  // 위에 해당되지 않는 경우, value 값 그대로 가져와서 사용함
        var modelDate = new Date(modelDateString);
        var dateString = fromDatetoString(modelDate);
    } 

    return dateString
}