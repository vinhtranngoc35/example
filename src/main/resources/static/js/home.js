const eSearch = document.getElementById('search');
const eLoadMore = document.getElementById('loadMore');
const eLoading = document.getElementById('loading');
const formSearch = document.getElementById('formSearch');
const eProducts = document.getElementById('products');
const eCategories = document.getElementById('category');
const eSortPrice = document.getElementById('sort-price');
let page = 0;
const fetchProduct = () => {
    eLoading.classList.remove('d-none');
    eLoadMore.classList.add('d-none');
    let url = `/api/products?search=${eSearch.value}&page=${page++}`;
    if(eSortPrice.value) {
        url += `&sort=price,${eSortPrice.value}`
    }
    if(eCategories.value) {
        url += `&categoryId=${eCategories.value}`
    }
    fetch(url)
        .then(response => response.json())
        .then(data => {
            if(!data.content.length) {
                toastr.info('No more products');
            }
            eProducts.innerHTML += data.content.map(product => {

                return `
                <div class="card col-5">
                    <div class="card-body">
                        <h5 class="card-title">${product.name}</h5>
                        <h6 class="card-subtitle mb-2 text-muted">${product.price}</h6>
                        <p class="card-text">${product.description}</p>
                        <p class="card-text">${product.categoryName}</p>
                        <a href="#" class="card-link">Card link</a>
                        <a href="#" class="card-link">Another link</a>
                    </div>
                </div>
               `
            }).join(" ")
            eLoading.classList.add('d-none');
            eLoadMore.classList.remove('d-none');
        })
}

eLoadMore.addEventListener('click', () => {
    fetchProduct();
})

function resetRenderProduct() {
    page = 0;
    eProducts.innerHTML = '';
    fetchProduct();
}

formSearch.addEventListener('submit', (e) => {
    e.preventDefault();
    resetRenderProduct();
})
eCategories.addEventListener('change', () => {
    resetRenderProduct();
})
eSortPrice.addEventListener('change', () => {
    resetRenderProduct();
})
fetchProduct();

function fetchCategories(){

    fetch(`/api/categories`)
        .then(response => response.json())
        .then(data => {
            data.forEach(category => {
                eCategories.innerHTML += `
                <option value="${category.id}">${category.name}</option>
                `
            })
        })
}
fetchCategories();