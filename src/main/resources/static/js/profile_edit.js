const inputs = document.getElementsByClassName('form-control');
const saveBtn = document.getElementById('save-btn');
let currents = [];
let isChanged = false;

function enableSaveButton() {
    const changed = changedFields();
    console.log();
    for(let i = 0; i < 3; i++)
        if(currents[i].toString().localeCompare(changed[i].toString())) {
            isChanged = true;
            saveBtn.innerHTML = `<button type="submit" class="btn btn-primary">Save</button>`
            return;
        }
    saveBtn.innerHTML = `<button type="button" class="btn btn-light">Save</button>`
    isChanged = false;
}

function changedFields() {
    const inputs = document.getElementsByClassName('form-control');
    let fields = [];
    for(let i = 0; i < 3; ++i)
        fields[i] = inputs[i].value;
    return fields;
}

window.onload = function() {
    for(let i = 0; i < 3; i++) {
        currents[i] = inputs[i].value;
        inputs[i].addEventListener('keyup', (e) => {
            enableSaveButton();
        });
    }
}
