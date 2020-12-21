const formItems = document.getElementsByClassName('form-item');
const saveBtn = document.getElementById("save-btn");

window.onload = function() {
    for(let i = 0; i < formItems.length; ++i) {
        console.log(formItems[i]);
        formItems[i].addEventListener('input', activateButton);
    }
}

function activateButton() {
    saveBtn.innerHTML = `<button type="submit" class="btn btn-primary">Save</button>`;
}
