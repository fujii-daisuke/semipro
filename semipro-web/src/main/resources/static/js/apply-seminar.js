$('#apply-seminar').on('click', function () {
  const seminarId = $(this).data('seminarid');
  $.ajax({
    type: "GET",
    url: "/seminars/create/" + seminarId + "/apply",
    dataType: 'json'
  }).done(function (data, textStatus, jqXHR) {
    window.location.href="/seminars/create" + seminarId + "/preview";
  }).fail(function (jqXHR, textStatus, errorThrown) {
    alert(jqXHR.responseText);
  });
});