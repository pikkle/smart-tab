/**
 * Created by chris_000 on 09.11.2014.
 */
var json = "{ \"name\": \" songName\", \"complex\": complexValue, \"tempo\": tempoValue, \"partition\": timesArray"

function parser() {
    var name = document.getElementById('name').value;
    var tab  = document.getElementById('tab').value;
    var tempo = document.getElementById('tempo').value;
    var strings = tab.split("\n");
    var tuning = new Array();

    <!-- put the name and tempo in the string-->
    json=json.replace("songName", name);
    json=json.replace("tempoValue",tempo );
    json=json.replace("complexValue", false);




    <!--Modify each strings to fit the format -->
    for(var i = 0;i<6;i++){
        tuning[i] = strings[i].charAt(0);
        strings[i] = strings[i].substr(1);
        strings[i] = strings[i].trim();
        strings[i] = strings[i].replace(/\|/g, "");
        strings[i] = strings[i].replace(/\//g, "-");

        strings[i] = strings[i].replace(/[hps]/g,"-");
    }
    <!-- Check if the length of each string match -->
    var length = strings[0].length
    for (var i = 0;i<6;i++){
        if(strings[i].length != length){
            prompt("length doesn't match!");
        }
    }

    <!-- Create timesArray strings and add each times in json format-->

    var timesArray = "[";
    var startString = "\"string_x\":\"y\"";


    for(var i =0;i<length;i++){
        timesArray=timesArray.concat("{");
        for(var j =0; j<6;j++){
            var stringBuilder = startString.replace("x", j)
            if(strings[j].charAt(i)!="-"){
                timesArray=timesArray.concat(stringBuilder.replace("y", strings[j].charAt(i)));
            }
            else {
                timesArray=timesArray.concat(stringBuilder.replace("y", ""));
            }
            if(j<(5)){
                timesArray=timesArray.concat(",");
            }
        }
        timesArray=timesArray.concat("}");
        if(i<(length-1)){
            timesArray=timesArray.concat(",");
        }
    }
    timesArray=timesArray.concat("]");

    <!-- put timesArray in the json string-->
    json=json.replace("timesArray", timesArray);
    prompt(json);


};