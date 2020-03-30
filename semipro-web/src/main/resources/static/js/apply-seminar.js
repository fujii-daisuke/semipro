$('#apply-seminar').on('click', function () {
  const seminarId = $(this).data('seminarid');
  $.ajax({
    type: "GET",
    url: "/seminars/" + seminarId + "/apply",
    dataType: 'json'
  }).done(function (data, textStatus, jqXHR) {
    window.location.href="/seminars/" + seminarId + "/preview";
  }).fail(function (jqXHR, textStatus, errorThrown) {
    alert(jqXHR.responseText);
  });
});