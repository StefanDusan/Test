function changeLogLevel(value) {

    var debugs = document.getElementsByName('debug');
    var traces = document.getElementsByName('trace');
    var i;

    if (value == 'main') {
        for (i = 0; i < traces.length; i++) {
            traces[i].style.display = 'NONE';
        }
        for (i = 0; i < debugs.length; i++) {
            debugs[i].style.display = 'NONE';
        }
    } else {
        if (value == 'debug') {
            for (i = 0; i < traces.length; i++) {
                traces[i].style.display = 'none';
            }
            for (i = 0; i < debugs.length; i++) {
                debugs[i].style.display = 'table-row';
            }
        } else {
            for (i = 0; i < traces.length; i++) {
                traces[i].style.display = 'table-row';
            }
            for (i = 0; i < debugs.length; i++) {
                debugs[i].style.display = 'table-row';
            }
        }
    }

}

$(window).load(function () {

        $("input[name=logLevel]").on("click", function (e) {
            changeLogLevel(this.value);
        });
    }
);