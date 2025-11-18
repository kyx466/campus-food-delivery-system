const BASE = 'http://localhost:8080/food';   // 8080 改成 application里的端口
async function loadCanteens() {
    const res = await fetch(`${BASE}/canteens`);
    const arr = await res.json();
    const sel = document.getElementById('canteenSelect');
    arr.forEach(c => {
        const opt = document.createElement('option');
        opt.value = c.id;
        opt.textContent = c.name;
        sel.appendChild(opt);
    });
}

async function loadDishes() {
    const canteenId = document.getElementById('canteenSelect').value;
    const name = document.getElementById('searchInput').value.trim();
    let url = `${BASE}/dishes`;
    if (canteenId) {
        url = `${BASE}/dishes/canteens/${canteenId}`;
    } else if (name) {
        url = `${BASE}/dishes/search?name=${encodeURIComponent(name)}`;
    }
    const res = await fetch(url);
    const arr = await res.json();
    const box = document.getElementById('dishList');
    box.innerHTML = arr.map(d => `
        <div class="dish-card">
            <img src="${d.imageUrl}" onerror="this.src='https://via.placeholder.com/200x140?text=🍲'">
            <h3>${d.name}</h3>
            <p>￥${d.price}</p>
            <button onclick="location.href='detail.html?id=${d.id}'">查看详情</button>
        </div>
    `).join('');
}

/* 绑定事件 */
document.getElementById('searchBtn').addEventListener('click', loadDishes);
document.getElementById('searchInput').addEventListener('keyup', e => {
    if (e.key === 'Enter') loadDishes();
});

/* 初始化 */
loadCanteens();
loadDishes();