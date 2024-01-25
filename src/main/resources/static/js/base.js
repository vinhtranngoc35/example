function createInput(props) {
    return `<div class="${props.classContainer || ''}">
                <label class="${props.classLabel || ''} form-label">${props.label}</label>
                <input class="input-custom form-control ${props.classInput || ''}" 
                type="${props.type || 'text'}" name="${props.name}"
                ${props.pattern ? `pattern="${props.pattern}"` : ""} 
                value="${props.value || ''}"
                ${props.required ? 'required' : ''} 
                />
                <span class="error form-text ${props.classError}">${props.message}</span>
            </div>`
}

function createSelect(props) {
    let optionsStr = "";
    props.options.forEach(e => {
        if (e.id == props.value) {
            optionsStr += `<option value="${e.id}" selected>${e.name}</option>`;
        } else {
            optionsStr += `<option value="${e.id}">${e.name}</option>`;
        }

    })

    return `<div "${props.classContainer || ''}">
                <label class="${props.classLabel || ''} form-label">${props.label}</label>
                <select class="input-custom form-control ${props.classSelect || ''}" 
                type="${props.type || 'text'}" name="${props.name}" 
                value="${props.value}"
                ${props.required ? 'required' : ''}>
                    <option value>---Choose---</option>
                    ${optionsStr}
                </select>
                <span class="error ${props.classError || ''} form-text">${props.message}</span>
            </div>`
}

function createFieldForm(props) {
    if (props.type === 'select') {
        return createSelect(props);
    }
    return createInput(props);
}

const onFocus = (formBody, index) => {
    const inputsForm = formBody.querySelectorAll('.input-custom')
    inputsForm[index].setAttribute("focused", "true");
}

function renderForm(formBody, inputs) {

    formBody.innerHTML = "";
    inputs.forEach((input) => {
        formBody.innerHTML += createFieldForm(input);
    })

    const inputElements = formBody.querySelectorAll('.input-custom');

    // add sự kiện onFocus
    for (let i = 0; i < inputElements.length; i++) {
        inputElements[i].onblur = function () {
            onFocus(formBody, i)
        }
    }

}

document.addEventListener('invalid', (function () {
    return function (e) {
        e.preventDefault(); // chặn popup của html5
        e.target.onblur(); // call onblur của tất cả các ô input
    };
})(), true);

function getDataFromForm(form) {
    // event.preventDefault()
    const data = new FormData(form);
    return Object.fromEntries(data.entries())
}

async function myFetch(props) {
    const {url, data, method, message} = props;
    try {
        const response =await fetch(url, {
            method,
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });
        if(!response.ok){
            const errors = await response.json();
            errors.forEach(error => {
                toastr.error(error);
            })

            return false;
        }
        toastr.success(message);
    }catch (e){
        console.error(e)
        return false;
    }
    return true;

}


function activeMenu(){
    const url = window.location.href;
    const menus = document.querySelectorAll('.nav-item a.nav-link');
    menus.forEach(menu => {

        if(menu.href === url) {
            menu.classList.add('active');
        }
    })
}
activeMenu();