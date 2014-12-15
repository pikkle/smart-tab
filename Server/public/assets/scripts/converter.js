/**
 * @author chrisgaubla
 */
var HOST = 'http://178.62.199.28/uploadTab';

/**
 * Adds the beginning of the JSON architecture to the result textarea
 */
function initialize() {
    var json = "{ \"name\": \"songName\", \"artist\": \"artistName\", \"tempo\": tempoValue, \"partition\":[ ";
    var div = document.getElementById('result');
    document.getElementById('add').disabled = false;
    document.getElementById('finish').disabled = false;
    document.getElementById('upload').disabled = true;

    displayError("");
    try {
        var name = document.getElementById('name').value;
        var tempo = document.getElementById('tempo').value;
        var artist = document.getElementById('artist').value;


        if (name =="" || tempo == ""){
            throw "Some fields are empty."
        }

        <!-- put the name and tempo in the string-->
        json = json.replace("songName", name);
        json = json.replace("artistName", artist);
        json = json.replace("tempoValue", tempo);
        json = json.replace("complexValue", false);

        div.innerText = json;
    }
    catch(err){
        displayError(err);
    }


}

/**
 * Finishes the JSON architecture in the result textarea
 */
function finish() {
    var div = document.getElementById('result');
    document.getElementById('add').disabled = true;
    document.getElementById('finish').disabled = true;
    displayError("");
    try {
        var tab = div.innerHTML;
        tab = tab.substr(0, tab.length-1);
        div.innerHTML = tab.concat("]}");
        document.getElementById('upload').disabled = false;
    }
    catch(err){
        displayError(err);

    }

}

/**
 * Parses the ASCII tab in the tab textarea into SmartTabs compatible JSON, and adds it to the result textarea
 */
function parser() {
    var timesArray = "";
    var div = document.getElementById('result');
    displayError("");
    try {
        var tab = document.getElementById('tab').value;

        var strings = tab.split("\n");

        if (strings.length != 6) {
            throw "Invalid number of strings."
        }
        var tuning = new Array();

        <!--Modify each strings to fit the format -->
        for (var i = 0; i < 6; i++) {
            tuning[i] = strings[i].charAt(0);
            strings[i] = strings[i].substr(1);
            strings[i] = strings[i].trim();
            strings[i] = strings[i].replace(/\|/g, "");
            strings[i] = strings[i].replace(/\//g, "-");
            strings[i] = strings[i].replace(/\D/g, "-");
        }

        <!-- Check if the length of each string match -->
        var length = strings[0].length
        for (var i = 0; i < 6; i++) {
            if (strings[i].length != length) {
                throw "The number of characters in two lines is different.";
            }
        }

        <!-- Create timesArray strings and add each times in json format-->


        var startString = "\"string_x\":\"y\"";
        var step = 0;
        for (var i = 0; i < length; i++) {

            var doubleNote = false;
            var emptyTime = true;
            if (i < length - 1) {
                for (var j = 0; j < 6; j++) {
                    if (strings[j].charAt(i) != "-"){
                        emptyTime = false;
                        if(strings[j].charAt(i + 1) != "-") {
                            doubleNote = true;
                        }
                    }
                }
            }
            if(!emptyTime){
                step++;
                timesArray = timesArray.concat("{");
                for (var j = 0; j < 6; j++) {
                    var stringBuilder = startString.replace("x", j + 1)
                    var note = "";
                    if (doubleNote) {
                        if (strings[j].charAt(i) != "-") {
                            note = note.concat(strings[j].charAt(i));
                            note = note.concat(strings[j].charAt(i + 1));
                            timesArray = timesArray.concat(stringBuilder.replace("y", note));
                        }
                        else if (strings[j].charAt(i) == "-" && strings[j].charAt(i + 1) != "-") {
                            timesArray = timesArray.concat(stringBuilder.replace("y", strings[j].charAt(i + 1)));
                        }
                        else {
                            timesArray = timesArray.concat(stringBuilder.replace("y", ""));
                        }
                    }
                    else if (!doubleNote) {
                        if (strings[j].charAt(i) != "-") {
                            timesArray = timesArray.concat(stringBuilder.replace("y", strings[j].charAt(i)));
                        }
                        else {
                            timesArray = timesArray.concat(stringBuilder.replace("y", ""));
                        }
                    }
                    if (j < (5)) {
                        timesArray = timesArray.concat(",");
                    }
                }

                timesArray = timesArray.concat(",");
                timesArray = timesArray.concat("\"length\" : \"Noire\" ");
                timesArray = timesArray.concat("}");
                if (doubleNote) {
                    doubleNote = false;
                    i++;
                }
                timesArray = timesArray.concat(",");
            }
        }
        div.innerHTML = div.innerHTML.concat(timesArray);
    }
    catch(err){
        displayError(err);

    }

}

/**
 * Sends with AJAX a POST request to upload the JSON as a SmartTabs tablature.
 */
jQuery(document).ready(function(){
    var result = $("#result");
    var name = $("#name");
    var artist = $("#artist");
    var error = $("#errorMessage");
    $("#upload").click(function(){
        var data = {
            name : name.val(),
            tab : result.val(),
            artist : artist.val()
        };
        $.ajax({
            url: HOST,
            type: 'POST',
            data : data,
            datatype : 'json',
            success : function(result, status){
                displayError("The tab was uploaded to " + result.uploadedUrl);
            },
            error : function(error) {
                displayError("The tab was not uploaded due to some error, check the console.");
                console.log(error);
            }
        })
    });

    result.bind('input propertychange', function() {
        result.innerHTML = JSON.stringify(result, null, 4);
    });
});

/**
 * Clear and adds the message to the error area
 * @param message
 */
function displayError(message) {
    document.getElementById('errorMessage').innerHTML = message;
}


document.getElementById('upload').disabled = true;
document.getElementById('add').disabled = true;
document.getElementById('finish').disabled = true;
