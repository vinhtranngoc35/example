const form = document.getElementById('form');
let productSelected = {};
const formBody = document.getElementById('formBody');
const tBody = document.getElementById('tBody');
const loadMore = document.getElementById('loadMore');
const loading = document.getElementById('loading');
const search = document.getElementById('search');
const formSearch = document.getElementById('formSearch');
const paging = document.getElementById('paging');
const LIMIT = 2;
let page = 0;
let categories;
let products = [];
let searchValue = '';
let totalPage = 0;

form.onsubmit = async (e) => {
    e.preventDefault();
    let data = getDataFromForm(form);
    data = {
        ...data,
        category: {
            id: data.category
        },
    }

    let result = false;
    if (productSelected.id) {
       result = await myFetch({data, url: '/api/products/' + productSelected.id, method: 'PUT', message: 'Edited'});

    } else {
        result = await myFetch({data, url: '/api/products', method: 'POST', message: 'Created'})
    }
    if(result){
        await renderTable();
        $('#staticBackdrop').modal('hide');
    }

}



async function getCategoriesSelectOption() {
    const res = await fetch('api/categories');
    return await res.json();
}



window.onload = async () => {
    categories = await getCategoriesSelectOption();
    await renderTable()
    renderForm(formBody, getDataInput());
}


function getDataInput() {
    return [
        {
            label: 'Name',
            name: 'name',
            value: productSelected.name,
            required: true,
            pattern: "^[A-Za-z 0-9]{6,20}",
            message: "Name must have minimum is 6 characters and maximum is 20 characters",
        },
        {
            label: 'Category',
            name: 'category',
            value: productSelected.categoryId,
            type: 'select',
            required: true,
            options: categories,
            message: 'Please choose Category'
        },
        {
            label: 'Price',
            name: 'price',
            value: productSelected.price,
            pattern: "^[1-9]\\d{3,}$",
            message: 'Price greater than 1000',
            required: true,
            type: 'text'
        },
        {
            label: 'Description',
            name: 'description',
            value: productSelected.description,
            pattern: "^[A-Za-z 0-9]{6,120}",
            message: "Description must have minimum is 6 characters and maximum is 20 characters",
            required: true
        },

    ];
}


async function findProductById(id) {
    const res = await fetch('/api/products/' + id);
    return await res.json();
}


async function showEdit(id) {
    $('#staticBackdropLabel').text('Edit Product');
    clearForm();
    productSelected = await findProductById(id);
    renderForm(formBody, getDataInput());
}


async function getProducts() {
    const res = await fetch(`/api/products?page=${page}&size=2&search=${searchValue}`);
    return await res.json();
}

function renderItemStr(item, index) {
    return      `<tr>
                    <td>
                        ${(page * LIMIT) + index + 1}
                    </td>
                    <td>
                        ${item.name}
                    </td>
                    <td>
                        ${item.description}
                    </td>
                    <td>
                        ${item.price}
                    </td>
                    <td>
                        ${item.categoryName || 'No Category'}
                    </td>
                     
                    <td>
                        <a class="btn btn-primary text-white  edit" data-id="${item.id}" data-bs-toggle="modal" data-bs-target="#staticBackdrop">Edit</a>
                        <a class="btn btn-warning text-white delete" data-id="${item.id}">Delete</a>
                    </td>
                </tr>`
}

function renderTBody(items) {
    let str = '';
    items.forEach((e, i) => {
        str += renderItemStr(e, i);
    })
    tBody.innerHTML = str;
}

async function renderTable() {
    loading.classList.remove('d-none');
    const pageable = await getProducts();
    products = pageable.content;
    totalPage = pageable.totalPages;
    renderTBody(products);
    await addEventEditAndDelete();
    renderPaging();
    loading.classList.add('d-none');
}

const addEventEditAndDelete = async () => {
    const eEdits = tBody.querySelectorAll('.edit');
    //const eDeletes = tBody.querySelectorAll('.delete');
    for (let i = 0; i < eEdits.length; i++) {
        eEdits[i].addEventListener('click', () => {
            showEdit(eEdits[i].dataset.id);
        })
    }
}

function clearForm() {
    form.reset();
    productSelected = {};
}

function showCreate() {
    $('#staticBackdropLabel').text('Create Product');
    clearForm();
    renderForm(formBody, getDataInput())
}

formSearch.addEventListener('submit', async (e) => {
    e.preventDefault();
    page = 0;
    count = 0;
    tBody.innerHTML = '';
    searchValue = search.value;
    await renderTable();
})

function renderPaging(){

    paging.innerHTML = '';
    let str = `<li class="page-item ${+page === 0 ? 'disabled' : ''}">
      <a class="page-link" tabindex="-1" aria-disabled="true" data-page="${page - 1}">Previous</a>
    </li>`;
    for(let i = 0; i < totalPage; i++){
        str += `<li class="page-item ${+page === i ? 'active' : ''}"><a class="page-link" data-page="${i}">${i + 1}</a></li>`
    }
    str += `<li class="page-item ${+page === totalPage - 1 ? 'disabled' : ''}">
      <a class="page-link"   data-page="${page + 1}">Next</a>
    </li>`;
    paging.innerHTML = str;

    const pages = document.querySelectorAll('.page-link');
    for (let i = 0; i < pages.length; i++) {
        pages[i].addEventListener('click', async () => {
            let newPage = +pages[i].dataset.page;
            if(newPage < 0 || newPage > totalPage - 1) return;
            page = newPage;
            page = pages[i].dataset.page;
            await renderTable();
        })
    }
}