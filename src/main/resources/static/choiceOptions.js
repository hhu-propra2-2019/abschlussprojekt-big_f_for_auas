let today = new Date();
let tomorrow = new Date(today.setDate(today.getDate()+1));
tomorrow = tomorrow.toISOString().slice(0, 10);

function addChoiceOption(){
    $('#scheduleOptionList').append(`                    <li>
                        <div class="form-group row">
                            <input name="dateOption" class="form-control col ml-5" type="date">
                            <input class="form-control col ml-5" type="time" value="12:00">
                            <div class="col-1">
                                <button style="width: 37px; height: 37px" type="button" name="removeOption" class="btn btn-danger" onclick="removeChoiceOption(this)">-</button>
                            </div>
                        </div>
                    </li>`);
    $('#dateOption').last().value = tomorrow;
}

function removeChoiceOption(btn){
    $(btn).closest("li").remove();
}