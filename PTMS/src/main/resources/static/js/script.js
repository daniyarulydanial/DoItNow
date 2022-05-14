document.addEventListener('DOMContentLoaded', function() {

    var calendarEl = document.getElementById('calendar');

    let titles = document.getElementById('titles').innerText.replace("[",'').replace("]",'').split(", ");
    let starts = document.getElementById('starts').innerText.replace("[",'').replace("]",'').split(", ");
    let ends = document.getElementById('ends').innerText.replace("[",'').replace("]",'').split(", ");

    let events_json = [];

    for (let i = 0; i < titles.length; i++) {
        events_json [i] = {"title": titles[i], "start": starts[i], "end": ends[i]};
    }


    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        initialDate: '2022-04-07',
        headerToolbar: {
            left: 'prev,next today',
            center: 'title',
            right: 'dayGridMonth,timeGridWeek,timeGridDay'
        },
        events:
            events_json,
            // events_json[1],
            // events_json[2]

    });
    calendar.render();

});
