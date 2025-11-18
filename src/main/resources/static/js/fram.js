
/* ====== fram.js ====== */
const pageMap = {
    category: '/food/page/category.html',
    dish:     '/food/page/dish.html',
    order:    '/food/page/order.html',
    'my-order':'/food/page/my-order.html',
    review:   '/food/page/review.html'
};

const nav = document.querySelector('.side-nav');
const content = document.getElementById('content');

/* 1. 实现并先挂全局 */
function loadPage(page){
    const url = pageMap[page];
    if (!url) {
        content.innerHTML = `<p>页面未配置：${page}</p>`;
        return;
    }
    fetch(url)
        .then(r => {
            if (!r.ok) throw new Error('404');
            return r.text();
        })
        .then(html => {
            content.innerHTML = html;
            // 加载页面后执行页面特定的初始化代码
            if (page === 'category') {
                initCategoryPage();
            }
        })
        .catch(err => content.innerHTML = `<p>加载失败：${err}</p>`);
}

window.loadPage = loadPage;   // 全局可用

/* 2. 事件委托 */
nav.addEventListener('click', e => {
    if (e.target.tagName === 'A') {
        e.preventDefault();
        const page = e.target.dataset.page;
        setActive(page);
        loadPage(page);
    }
});

function setActive(page) {
    nav.querySelectorAll('a').forEach(a => a.classList.remove('active'));
    const activeLink = nav.querySelector(`[data-page="${page}"]`);
    if (activeLink) {
        activeLink.classList.add('active');
    }
}

/* 分类页面初始化 */
function initCategoryPage() {
    fetch('/food/categories')
        .then(r => r.json())
        .then(arr => {
            const box = document.getElementById('catGrid');
            if (box) {
                box.innerHTML = arr.map(c => `
                    <div class="cat-card" onclick="toDish(${c.id},'${c.name}')">
                        <img src="https://picsum.photos/300/200?random=${c.id}" alt="${c.name}">
                        <h3>${c.name}</h3>
                        <p>点击查看菜品</p>
                    </div>`).join('');
            }
        })
        .catch(err => console.error('拉取分类失败', err));
}

/* 跳转菜品列表 */
window.toDish = function(catId, catName) {
    sessionStorage.setItem('catId', catId);
    sessionStorage.setItem('catName', catName);
    loadPage('dish');
};
