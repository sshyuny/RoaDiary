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
    if (modelDateString == "") {
        var todayDate = new Date();
        var dateString = fromDatetoString(todayDate);
    } else {
        var modelDate = new Date(modelDateString);
        var dateString = fromDatetoString(modelDate);
    }

    return dateString
}

// 테이블 부분
function changeTime() {
    alert(this.id);
}