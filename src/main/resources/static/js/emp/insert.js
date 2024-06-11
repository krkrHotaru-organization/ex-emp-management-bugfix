"use strict";

$(function () {
  $("#get_address_button").on("click", function () {
    $.ajax({
      type: "GET",
      url: "https://zipcoda.net/api",
      data: {
        zipcode: $("#zipCode").val(),
      },
      dataType: "json",
    })
      .done(function (data) {
        $("#address").val(data.items[0].pref + data.items[0].address);
        console;
      })
      .fail(function (XMLRequest, textStatus, errorThrown) {
        alert("正しい結果を得られませんでした。");
        console.log(XMLHttpRequest.status);
        console.log(textStatus);
        console.log(errorThrown.message);
      });
  });
});
