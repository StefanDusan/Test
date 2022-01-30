var statuses = new Array();
var treeData;
var groupedTreeData;
var showFailOnly = true;
var hideConfig = true;

function hideConfigView(state) {

    hideConfig = state;
    switchPassView(showFailOnly);
    var tree = $("#tree").jstree(true);
    var nodes = tree.settings.core.data;
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].a_attr != undefined && nodes[i].a_attr.config == 'true') {
            if (state && nodes[i].icon == 'jstree-ok') tree.hide_node(nodes[i]);
            else if (nodes[i].icon != 'jstree-ok' || !showFailOnly) tree.show_node(nodes[i]);
        }
    }
}

function switchPassView(state) {

    showFailOnly = state;
    var tree = $("#tree").jstree(true);
    var nodes = tree.settings.core.data;
    for (var i = 0; i < nodes.length; i++) {
        if (nodes[i].icon == 'jstree-ok') {
            if (state) tree.hide_node(nodes[i]);
            else tree.show_node(nodes[i]);
        }
    }

    updateViewInSummary();
}

function updateViewInSummary() {
    if (hideConfig) {
        $("tr.config").hide();
    } else {
        $("tr.config").show();
    }
}

function createStatTable() {
    var innerHtml = "<table style='border: none;padding-top:40px; padding-left:40px;'><tr colspan=4 align='center'><td style='padding-bottom: 20px;' >Summary</td></tr>";
    for (var i in statuses) {
        var node = statuses[i];
        innerHtml += "<tr class='" + (node.config ? "config " : "notconfig ") + (node.icon != 'jstree-ok' ? "notpass" : "pass") + "'>";
        innerHtml += "<td>" + node.name + " (" + node.group + ")</td>";
        innerHtml += "<td>" + node.tl + "</td>";
        innerHtml += "<td style='padding-left:10px'>" + (node.icon == 'jstree-ok' ? "<span class='pass'>Passed</span>" : (node.icon == 'jstree-er' ? "<span class='fail'>Failed</span>" : "Running...")) + "</td>";
        innerHtml += "<td style='padding-left:10px'>" + node.duration + " second(s)</td>";
        innerHtml += "</tr>";
    }
    innerHtml += "</table>";

    document.getElementById("stattable").innerHTML = innerHtml;
}

function updateStat() {
    var cntPass = 0;
    var cntFail = 0;
    var total = 0;
    var testsWithBugs = 0;

    for (var i in statuses) {
        var node = statuses[i];
        var nodeString = "{";
        if (node.icon == 'jstree-er') {
            cntFail++;
            total++;
        } else if (!node.config) {
            total++;
            if (node.icon == 'jstree-ok') cntPass++;
        }
    }

    document.getElementById('pass_stat').innerHTML = cntPass + '/' + total + " (" + (cntPass / total * 100).toFixed(2) + "%)";
    document.getElementById('fail_stat').innerHTML = cntFail + '/' + total + " (" + (cntFail / total * 100).toFixed(2) + "%)";
    createStatTable();
}

function createGroupedTreeData() {

    var groups = new Array();
    var treeDataObj = new Array();

    treeDataObj.push(new Object());
    treeDataObj[0].id = 'root';
    treeDataObj[0].parent = '#';
    treeDataObj[0].text = 'All';
    treeDataObj[0].state = new Object();
    treeDataObj[0].state.opened = true;

    for (var i = 0; i < treeData.length; i++) {
        var treeNode = treeData[i];
        if (treeNode.id == 'root') continue;
        var group = treeNode.a_attr.group;
        var names = group.split("->");

        handleGroup(names[names.length - 1], group, treeNode.icon == 'jstree-ok', groups);

        /*if (!(group in groups)){
         groups[group] = {name: group, total: 1, passed: 0};
         } else {
         groups[group].total++;
         }
         if (treeNode.icon=='jstree-ok') groups[group].passed++;*/

        treeDataObj.push(new Object());
        var idx = treeDataObj.length - 1;
        treeDataObj[idx].id = treeNode.id;
        treeDataObj[idx].parent = group;
        treeDataObj[idx].text = treeNode.text;
        treeDataObj[idx].icon = treeNode.icon;
        treeDataObj[idx].li_attr = new Object();
        treeDataObj[idx].li_attr.title = treeNode.li_attr.title;
        treeDataObj[idx].a_attr = treeNode.a_attr;

    }

    for (var groupIdx in groups) {

        var group = groups[groupIdx];
        treeDataObj.push(new Object());
        var idx = treeDataObj.length - 1;
        treeDataObj[idx].id = group.id;
        treeDataObj[idx].parent = group.parent;//'root';
        treeDataObj[idx].text = group.name + ' (' + group.passed + '/' + group.total + ')';
    }

    groupedTreeData = treeDataObj;

}

function joinArr(arr, sep, idx) {
    if (idx < 0) idx = arr.length;

    var res = "";
    for (var i = 0; i <= idx; i++) {
        res += arr[i];
        if (i < idx) res += sep;
    }

    return res;
}

function handleGroup(groupName, groupId, isPass, resArr) {

    if (!(groupId in resArr)) {
        resArr[groupId] = {id: groupId, name: groupName, total: 1, passed: isPass ? 1 : 0};
    } else {
        resArr[groupId].total++;
        if (isPass) resArr[groupId].passed++;
    }

    var subs = groupId.split("->");

    if (subs.length > 1) {
        resArr[groupId].parent = joinArr(subs, "->", subs.length - 2);
        handleGroup(subs[subs.length - 2], joinArr(subs, "->", subs.length - 2), isPass, resArr);
    } else {
        resArr[groupId].parent = 'root';
    }
}

function createTreeData() {

    var treeDataObj = new Array();

    treeDataObj.push(new Object());
    treeDataObj[0].id = 'root';
    treeDataObj[0].parent = '#';
    treeDataObj[0].text = 'All';
    treeDataObj[0].state = new Object();
    treeDataObj[0].state.opened = true;

    for (var i in statuses) {
        var node = statuses[i];

        treeDataObj.push(new Object());
        var idx = treeDataObj.length - 1;
        treeDataObj[idx].id = node.id;
        treeDataObj[idx].parent = 'root';
        treeDataObj[idx].text = node.name.replace("\"", "'").replace("'", "&apos;") + ' (' + node.tl + ')';
        treeDataObj[idx].icon = node.icon;
        treeDataObj[idx].li_attr = new Object();
        treeDataObj[idx].li_attr.title = node.name.replace("\"", "'").replace("'", "&apos;") + ' (' + node.tl + ', ' + node.duration + ' seconds)';
        treeDataObj[idx].a_attr = new Object();
        treeDataObj[idx].a_attr.duration = node.duration;
        treeDataObj[idx].a_attr.group = node.group;
        treeDataObj[idx].a_attr.config = node.config;

    }

    treeData = treeDataObj;
    createGroupedTreeData();
}

function switchGrouping(state) {

    if (state) {
        $('#tree').jstree(true).settings.core.data = groupedTreeData;
    } else {
        $('#tree').jstree(true).settings.core.data = treeData;
    }

    $('#tree').jstree("refresh");

    var interval_id = setInterval(function () {
        if (!$('#tree').jstree(true)._data.core.working) {
            clearInterval(interval_id)
            switchPassView(showFailOnly);
            hideConfigView(hideConfig);

            $("#tree").on("click", ".jstree-anchor[group]", function (e) {
                var id = $(this).attr("id").split("_")[0];
                document.getElementById("stattable").style.display = "none";
                document.getElementById("iframe").src = $("#fldr").attr("value") + id + ".html";
                document.getElementById("iframe").style.display = "inline-block";
            });
            $("#root > i")[0].remove();
            $("#tree").on("click", "#root_anchor", function (e) {
                document.getElementById("stattable").style.display = "";
                document.getElementById("iframe").style.display = "none";
            });
        }
    }, 15);

}

$(window).load(function () {
    if (!Object.keys) {
        Object.keys = function (obj) {
            var keys = [];

            for (var i in obj) {
                if (obj.hasOwnProperty(i)) {
                    keys.push(i);
                }
            }

            return keys;
        };
    }
    createTreeData();
    $("#tree").jstree();
    switchGrouping(true);
    updateStat();

    $("#treeGroup").on("click", function (e) {
        switchGrouping(this.checked);
    });

    $("#treeFailed").on("click", function (e) {
        switchPassView(this.checked);
    });

    $("#treeConfig").on("click", function (e) {
        hideConfigView(this.checked);
    });
});