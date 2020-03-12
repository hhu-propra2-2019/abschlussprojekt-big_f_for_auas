function addChoiceOption(){
    $('#scheduleOptionList').append(`                    <li>
                        <div class="form-group row">
                            <div class="input-group">
                                <input name="dateOption" class="form-control col ml-5" type="date">
                                <input class="form-control col" type="time" value="12:00">
                                <div class="col-1 mr-3">
                                    <button style="width: 37px; height: 37px" type="button" name="removeOption" class="btn btn-danger" onclick="removeChoiceOption(this)">-</button>
                                </div>
                            </div>
                        </div>
                    </li>`);
}

function removeChoiceOption(btn){
    $(btn).closest("li").remove();
}