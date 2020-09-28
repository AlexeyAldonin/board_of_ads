let buttonAdd = $('#searchCityDiv');

$("#region, #category-select-city").click(function() {
    $("#searchModel").modal("show");
});

$('select#cities').on('change', function() {
    $('input[name="cityInput"]').val(this.value);
});

function onOptionHover() {
    $(".opt").mouseover(
        function() {
            $(this).css('background', '#99ccff')
        });
    $(".opt").mouseleave(
        function() {
            $(this).css('background', '#fff')
        });
}

function onClickOpt(id) {
    $('#category-select-city').empty();
    $('#searchModal').modal('hide');
    let row = `<option>` + id + `</option>`;
    $('#category-select-city').append(row);
    document.getElementById('category-select-city').disabled = false;
}

$('[data-city]').on('click', function(event) {
    console.log(event)
    var animalId = event.target.id;
    console.log(animalId);
});

$(document).ready(function() {
    viewCities();
});

let cities;

async function viewCities() {
    $('#category-select-city').empty();
    const usersResponse = await userService.findAllCity();
    cities = usersResponse.json();
    let select=`<select id="citiesSelect" size="7"
                                class="form-control">
                        </select>`;
    $('.citiesOptions').append(select);
    let cityOptions = $('#citiesSelect');
    cities.then(users => {
        users.forEach(user => {
            let userRow = `<option onmouseover="onOptionHover()"
                                   onclick="onClickOpt(this.id)"
                                   id="${user.city}"
                                   class="opt"
                                   text="${user.city}">
                                       <div>${user.city}</div>
                                       <div>${', ' + user.region + ' ' + user.formSubject}</div>
                                </option>`;
            cityOptions.append(userRow);
        });
    });
    let button = `<div class="force-to-bottom" style="position: absolute; bottom: 15px; right: 10px">
                    <button 
                        type="button" 
                        class="btn btn-primary "                           
                        disabled>Показать 0 объявлений
                       </button>
                </div>`;
    buttonAdd.append(button);
}

$('.typeahead').on('change', function() {
    addOptions();
});

function addOptions() {
    $('#citiesSelect').empty();
    let addForm = $(".typeahead").val().toLowerCase();
    cities.then(users => {
        users.forEach(user => {
            if (user.city.toLowerCase().includes(addForm)) {
                let userRow = `<option onmouseover="onOptionHover()" 
                                       onclick="onClickOpt(this.id)"
                                       id="${user.city}"
                                       class="opt"                                
                                       text="${user.city}">
                                           <div>${user.city}</div>
                                           <div>${', ' + user.region + ' ' + user.formSubject}</div>
                                </option>`;
                $('#citiesSelect').append(userRow);
            }
        });
    });
}

const http = {
    fetch: async function(url, options = {}) {
        const response = await fetch(url, {
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            ...options,
        });
        return response;
    }
};

const userService = {
    findAllCity: async () => {
        return await http.fetch('/api/city');
    }
}

$.get("/user", function(data) {
    $("#user").html(data.userAuthentication.details.name);
    $(".unauthenticated").hide()
    $(".authenticated").show()
});