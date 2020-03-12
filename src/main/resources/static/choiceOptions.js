function addSchedulingChoiceOption(){
    $('#scheduleOptionList').append(`            <li>
                <div class="form-group">
                    <div class="input-group">
                        <input name="dateOption" class="form-control ml-4" type="date" required>
                        <input class="form-control optionTime" type="time" value="12:00" required>
                        <div class="input-group-append">
                            <button type="button" name="removeOption" class="btn btn-danger" onclick="removeChoiceOption(this)"><i class="fa fa-minus"></i></button>
                        </div>
                    </div>
                </div>
            </li>`);
}

function addPollChoiceOption(){
    $('#pollOptionList').append(`            <li>
                <div class="form-group">
                    <div class="input-group">
                        <input name="pollOption" class="form-control ml-4" type="text" placeholder="Möglichkeit eingeben" required>
                        <div class="input-group-append">
                            <button type="button" name="removeOption" class="btn btn-danger" onclick="removeChoiceOption(this)"><i class="fa fa-minus"></i></button>
                        </div>
                    </div>
                </div>
            </li>`);
}

function removeChoiceOption(btn){
    $(btn).closest("li").remove();
}