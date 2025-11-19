/* ====== fram.js ====== */
const pageMap = {
    category: '/food/page/category.html',
    dish: '/food/page/dish.html',
    'dish-detail': '/food/page/dish-detail.html',
    cart: '/food/page/cart.html',
    order: '/food/page/order.html',
    'my-order': '/food/page/my-order.html',
    review: '/food/page/review.html'
};

const nav = document.querySelector('.side-nav');
const content = document.getElementById('content');

/* 1. 页面加载函数 */
function loadPage(page) {
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
            // 特殊处理购物车页面
            if (page === 'cart') {
                loadCartPage(html);
            } else {
                content.innerHTML = html;
                initializePage(page);
            }
        })
        .catch(err => content.innerHTML = `<p>加载失败：${err}</p>`);
}


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

/* 3. 分类页面初始化 */
function initCategoryPage() {
    console.log('=== 开始加载分类数据 ===');
    console.log('请求URL:', '/food/categories');

    fetch('/food/categories')
        .then(r => {
            console.log('响应状态:', r.status, r.statusText);
            if (!r.ok) throw new Error('分类API请求失败: ' + r.status);
            return r.json();
        })
        .then(arr => {
            console.log('接收到分类数据:', arr);
            console.log('数据长度:', arr.length);

            const box = document.getElementById('catGrid');
            console.log('catGrid元素:', box);

            if (box) {
                if (!arr || !arr.length) {
                    console.log('分类数据为空');
                    box.innerHTML = '<div class="text-muted">暂无分类</div>';
                    return;
                }

                console.log('开始渲染分类卡片');
                box.innerHTML = arr.map(c => `
                    <div class="cat-card" onclick="toDish(${c.id},'${c.name}')">
                        <img src="https://picsum.photos/300/200?random=${c.id}" alt="${c.name}">
                        <h3>${c.name}</h3>
                        <p>点击查看菜品</p>
                    </div>`).join('');
                console.log('分类卡片渲染完成');
            } else {
                console.error('catGrid元素未找到');
            }
        })
        .catch(err => {
            console.error('拉取分类失败:', err);
            const box = document.getElementById('catGrid');
            if (box) box.innerHTML = '<div class="error">加载分类失败: ' + err.message + '</div>';
        });
}

/* 4. 菜品页面初始化 */
function initDishPage() {
    console.log('=== 初始化菜品页面 ===');

    const catId = sessionStorage.getItem('catId');
    const catName = sessionStorage.getItem('catName');

    console.log('从sessionStorage获取:', {catId, catName});

    // 设置分类标题
    const titleEl = document.getElementById('categoryTitle');
    if (catName && titleEl) {
        titleEl.textContent = catName;
    }

    // 加载菜品数据
    if (catId) {
        console.log('开始加载分类ID为', catId, '的菜品');
        loadDishesByCategory(catId);
    } else {
        console.error('未找到分类ID');
        const dishList = document.getElementById('dishList');
        if (dishList) {
            dishList.innerHTML = '<div class="error">未选择分类</div>';
        }
    }
}

/* 5. 加载分类菜品函数 */
function loadDishesByCategory(categoryId) {
    console.log('🚀 loadDishesByCategory函数被调用，categoryId:', categoryId);

    fetch(`/food/dishes/categories/${categoryId}`)
        .then(r => {
            console.log('API响应状态:', r.status, r.statusText);
            if (!r.ok) throw new Error('API请求失败: ' + r.status);
            return r.json();
        })
        .then(dishes => {
            console.log('✅ 接收到菜品数据:', dishes);

            const dishList = document.getElementById('dishList');
            if (!dishList) {
                console.error('dishList元素未找到');
                return;
            }

            if (!dishes || !dishes.length) {
                console.log('该分类下无菜品');
                dishList.innerHTML = '<div class="no-dishes">该分类下暂无菜品</div>';
                return;
            }

            console.log('开始渲染', dishes.length, '个菜品');

            dishList.innerHTML = dishes.map(dish => `
                <div class="dish-card" onclick="showDishDetail(${dish.id})">
                    <img src="${dish.imageUrl || `https://picsum.photos/300/200?random=${dish.id}`}" 
                         alt="${dish.name}"
                         onerror="this.src='https://picsum.photos/300/200?random=${dish.id}'">
                    <div class="dish-info">
                        <h3>${dish.name}</h3>
                        <p class="price">¥${dish.price}</p>
                        <p class="stock">库存: ${dish.stock}</p>
                        <span class="status ${dish.status === 1 ? 'on-shelf' : 'off-shelf'}">
                            ${dish.status === 1 ? '上架' : '下架'}
                        </span>
                        <button class="add-to-cart-btn" onclick="event.stopPropagation(); addToCart(${dish.id})">
                            加入购物车
                        </button>
                    </div>
                </div>
            `).join('');

            console.log('✅ 菜品渲染完成');
        })
        .catch(err => {
            console.error('❌ 加载菜品失败:', err);
            const dishList = document.getElementById('dishList');
            if (dishList) {
                dishList.innerHTML = '<div class="error">加载菜品失败: ' + err.message + '</div>';
            }
        });
}

// 新增：购物车页面初始化函数
function initializeCartPage() {
    console.log('🛒 初始化购物车页面...');

    // 给一点延迟确保DOM完全加载
    setTimeout(() => {
        if (window.loadCart && typeof window.loadCart === 'function') {
            console.log('✅ 调用全局 loadCart 函数');
            window.loadCart();
        } else {
            console.log('❌ loadCart 未找到，使用备用方案');
            loadCartDirectly();
        }
    }, 100);
}


function loadCartDirectly() {
    console.log('🛒 直接加载购物车数据...');

    fetch('/food/cart/items')
        .then(r => {
            console.log('购物车响应状态:', r.status);
            if (!r.ok) throw new Error('加载失败: ' + r.status);
            return r.json();
        })
        .then(items => {
            console.log('✅ 接收到购物车数据:', items);
            renderCartItems(items);
        })
        .catch(err => {
            console.error('❌ 加载购物车失败:', err);
            renderCartError(err.message);
        });
}

function renderCartItems(items) {
    const cartItems = document.getElementById('cartItems');
    if (!cartItems) {
        console.error('cartItems 元素未找到');
        return;
    }

    if (!items || items.length === 0) {
        cartItems.innerHTML = `
            <div class="empty-cart">
                <div class="empty-icon">🛒</div>
                <p>购物车为空</p>
                <button class="browse-btn" onclick="loadPage('category')">去逛逛</button>
            </div>
        `;
        document.getElementById('totalAmount').textContent = '0.00';
        return;
    }

    let total = 0;
    cartItems.innerHTML = items.map(item => {
        const subtotal = item.subtotal || (item.price * item.quantity);
        total += parseFloat(subtotal);

        return `
            <div class="cart-item">
                <img src="https://picsum.photos/100/100?random=${item.dishId}" 
                     alt="${item.dishName}">
                <div class="item-info">
                    <h3 class="item-name">${item.dishName}</h3>
                    <p class="item-price">单价: ¥${item.price}</p>
                </div>
                <div class="quantity-control">
                    <button class="quantity-btn" onclick="updateQuantity(${item.dishId}, ${item.quantity - 1})">-</button>
                    <span class="quantity-display">${item.quantity}</span>
                    <button class="quantity-btn" onclick="updateQuantity(${item.dishId}, ${item.quantity + 1})">+</button>
                </div>
                <div class="item-subtotal">
                    <p>¥${subtotal.toFixed(2)}</p>
                </div>
                <button class="remove-btn" onclick="removeFromCart(${item.dishId})">删除</button>
            </div>
        `;
    }).join('');

    document.getElementById('totalAmount').textContent = total.toFixed(2);
    console.log('✅ 购物车渲染完成，总计:', total.toFixed(2));
}

function renderCartError(message) {
    const cartItems = document.getElementById('cartItems');
    if (cartItems) {
        cartItems.innerHTML = `
            <div class="error">
                <p>加载失败: ${message}</p>
                <button onclick="loadCartDirectly()" style="margin-top: 10px; padding: 8px 16px; background: #3498db; color: white; border: none; border-radius: 4px; cursor: pointer;">重试</button>
            </div>
        `;
    }
}


// 新增：专门处理购物车页面加载
function loadCartPage(html) {
    content.innerHTML = html;

    // 手动执行购物车页面的脚本
    const scripts = content.querySelectorAll('script');
    scripts.forEach(script => {
        const newScript = document.createElement('script');
        if (script.src) {
            newScript.src = script.src;
        } else {
            newScript.textContent = script.textContent;
        }
        document.body.appendChild(newScript);
    });

    console.log('🛒 购物车页面脚本已执行');
}

// 修改初始化函数
function initializePage(page) {
    if (page === 'category') {
        initCategoryPage();
    } else if (page === 'dish') {
        initDishPage();
    } else if (page === 'cart') {
        console.log('购物车页面初始化');
        // 购物车页面会自己初始化
    }
}



/*
6.
 */
// 跳转到菜品列表
window.toDish = function(catId, catName) {
    sessionStorage.setItem('catId', catId);
    sessionStorage.setItem('catName', catName);
    loadPage('dish');
};

// 返回分类
window.backToCategory = function() {
    console.log('返回分类页面');
    if (typeof loadPage === 'function') {
        loadPage('category');
    } else {
        window.history.back();
    }
};

// 返回菜品列表
window.backToDishList = function() {
    console.log('返回菜品列表');
    if (typeof loadPage === 'function') {
        loadPage('dish');
    } else {
        window.history.back();
    }
};

// 显示菜品详情
window.showDishDetail = function(dishId) {
    console.log('显示菜品详情:', dishId);
    sessionStorage.setItem('selectedDishId', dishId);
    loadPage('dish-detail');
};

// 添加到购物车
window.addToCart = function(dishId, quantity = 1) {
    console.log('添加到购物车:', dishId, quantity);

    fetch('/food/cart/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            dishId: dishId,
            quantity: quantity
        })
    })
        .then(r => {
            if (!r.ok) throw new Error('添加失败');
            return r.text();
        })
        .then(() => {
            alert('已添加到购物车！');
            updateCartCount();
        })
        .catch(err => {
            console.error('添加购物车失败:', err);
            alert('添加失败: ' + err.message);
        });
};

// 更新购物车数量显示
window.updateCartCount = function() {
    fetch('/food/cart/items')
        .then(r => {
            if (!r.ok) throw new Error('获取购物车失败');
            return r.json();
        })
        .then(items => {
            const totalCount = items ? items.reduce((sum, item) => sum + item.quantity, 0) : 0;
            const cartCountEl = document.getElementById('cartCount');
            if (cartCountEl) {
                cartCountEl.textContent = totalCount;
                cartCountEl.style.display = totalCount > 0 ? 'inline-block' : 'none';
            }
        })
        .catch(err => {
            console.error('获取购物车数量失败:', err);
            const cartCountEl = document.getElementById('cartCount');
            if (cartCountEl) {
                cartCountEl.style.display = 'none';
            }
        });
};

// 页面加载时默认显示分类页面并更新购物车数量
document.addEventListener('DOMContentLoaded', function() {
    console.log('=== 页面加载完成，初始化应用 ===');
    setActive('category');
    loadPage('category');
    updateCartCount();
});


// 全局暴露所有函数
window.loadPage = loadPage;
window.loadDishesByCategory = loadDishesByCategory;
window.showDishDetail = showDishDetail;
window.addToCart = addToCart;
window.backToCategory = backToCategory;
window.backToDishList = backToDishList;
window.toDish = toDish;
window.updateCartCount = updateCartCount;
// 在文件末尾的暴露函数部分，确保添加：
window.loadCartDirectly = loadCartDirectly;
