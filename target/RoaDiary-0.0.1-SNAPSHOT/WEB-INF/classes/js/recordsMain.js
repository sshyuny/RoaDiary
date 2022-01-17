
function fromDatetoString(yD, mD, dD) {
    var dateS;
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

    function plusDateUrl() {
    var url = new URL(location).searchParams;
    var pParam = Number(url.get("p"));
    var mParam = Number(url.get("m"));
    pParam += 1;
    newUrl = "recordsShow?p=" + pParam + "&m=" + mParam;
    return newUrl;
    }
    function minusDateUrl() {
    var url = new URL(location).searchParams;
    var pParam = Number(url.get("p"));
    var mParam = Number(url.get("m"));
    mParam += 1;
    newUrl = "recordsShow?p=" + pParam + "&m=" + mParam;
    return newUrl;
    }

    function setNewValue(newDate) {
    //
    var yearMinus = newDate.getFullYear();
    var monthMinus = newDate.getMonth()+1;
    var dayMinus = newDate.getDate();
    var dateString = fromDatetoString(yearMinus, monthMinus, dayMinus);
    document.getElementById('someday').value = dateString;
    }

    window.onload = function() {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth()+1;
        var day = date.getDate();
        document.getElementById('selectDate').value = fromDatetoString(year, month, day);
        
        document.getElementById('todayDate').onclick = function() {
            var dateString = fromDatetoString(year, month, day);
            document.getElementById('someday').value = dateString;
            document.getElementById('frm').action="recordsShow?p=0&m=0";
            document.getElementById('frm').submit();
            //location.href="./recordsShow?p=0m=0";
        }

        document.getElementById('minusDate').onclick = function() {
            var url = new URL(location).searchParams;
            var pmParam = Number(url.get("p")) - Number(url.get("m"));

            var newDate = new Date(year, month-1, day+pmParam-1);
            setNewValue(newDate);
            

            var newUrl = minusDateUrl();
            document.getElementById('frm').action=newUrl;
            document.getElementById('frm').submit();
        }

        document.getElementById('plusDate').onclick = function() {
            var url = new URL(location).searchParams;
            var pmParam = Number(url.get("p")) - Number(url.get("m"));

            var newDate = new Date(year, month-1, day+pmParam+1);
            setNewValue(newDate);
            

            var newUrl = plusDateUrl();
            document.getElementById('frm').action=newUrl;
            document.getElementById('frm').submit();
        }

        document.getElementById('selectDateBt').onclick = function() {
            var dateString = document.getElementById('selectDate').value;
            document.getElementById('someday').value = dateString;
            document.getElementById('frm').action="recordsShow?p=0&m=0";
            document.getElementById('frm').submit();
        }

    }