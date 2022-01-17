
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
    document.getElementById('getSomeday').value = resultDate;
    document.getElementById('frm').submit();
}
function returnMinusDate() {
    var dateString = initialDate();
    var tempDate = new Date(dateString);
    tempDate.setDate(tempDate.getDate() - 1);
    var resultDate = fromDatetoString(tempDate);
    document.getElementById('someday').value = resultDate;
    document.getElementById('getSomeday').value = resultDate;
    document.getElementById('frm').submit();
}
function returnTodayDate() {
    var tempDate = new Date();
    var resultDate = fromDatetoString(tempDate);
    document.getElementById('someday').value = resultDate;
    document.getElementById('getSomeday').value = resultDate;
    document.getElementById('frm').submit();
}

function initialDate() {
    

    if ( document.getElementById('someday') == null ) {
        var todayDte = new Date();
        var dateString = fromDatetoString(todayDte);
    } else {
        var modelDateString = document.getElementById('someday').value;
        var modelDate = new Date(modelDateString);
        var dateString = fromDatetoString(modelDate);
    }

    return dateString
}

/*
window.onload = function() {
    document.getElementById('minusDate').onclick = returnMinusDate(dateString);
    document.getElementById('plusDate').onclick = returnPlusDate(dateString);
    document.getElementById('todayDate').onclick = returnTodayDate();

    //document.getElementById('selectDate').value = dateString;

    document.getElementById('selectDateBt').onclick = function() {
        var dateString = document.getElementById('selectDate').value;
        document.getElementById('someday').value = dateString;
        document.getElementById('frm').action="recordsShow?p=0&m=0";
        document.getElementById('frm').submit();
    }

}*/