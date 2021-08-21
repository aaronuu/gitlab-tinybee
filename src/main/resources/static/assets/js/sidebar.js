$(function () {
    var avatar = $("#sidebarImg");
    var imageUrl = baseUrl + '/sidebar/img';
    $.get(imageUrl, function (d) {
        if(d.data > 11) {
            avatar.remove();
        } else {
            avatar.attr('src', baseUrl + "/assets/img/sidebar/" + d.data + ".svg");
        }
    });
});
