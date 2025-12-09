
document.querySelector('.btn-entre').addEventListener('click', () => {
    alert('Você clicou em "ENTRE AQUI!"');
});

// Login
const wrapper = document.getElementById("loginWrapper");
const loginBox = document.getElementById("loginBox");
const registerBox = document.getElementById("registerBox");
const btnGoRegister = document.getElementById("btnGoRegister");
const btnGoLogin = document.getElementById("btnGoLogin");

btnGoRegister.addEventListener("click", () => {
    wrapper.classList.add("register-mode");
    loginBox.classList.remove("active");
    registerBox.classList.add("active");
});

btnGoLogin.addEventListener("click", () => {
    wrapper.classList.remove("register-mode");
    registerBox.classList.remove("active");
    loginBox.classList.add("active");
});

// Carrossel
const track = document.getElementById("carousel-track");
const items = Array.from(track.children);
const btnPrev = document.getElementById("btnPrev");
const btnNext = document.getElementById("btnNext");

const gap = 30;
const itemsVisible = 4;
const itemWidth = items[0].offsetWidth + gap;

for (let i = items.length - itemsVisible; i < items.length; i++) {
    const clone = items[i].cloneNode(true);
    clone.classList.add("clone");
    track.insertBefore(clone, track.firstChild);
}

for (let i = 0; i < itemsVisible; i++) {
    const clone = items[i].cloneNode(true);
    clone.classList.add("clone");
    track.appendChild(clone);
}

const allItems = Array.from(track.children);
let index = itemsVisible;  

track.style.transform = `translateX(${-itemWidth * index}px)`;

function moveCarousel() {
    track.style.transition = "transform 0.45s ease";
    track.style.transform = `translateX(${-itemWidth * index}px)`;
}

btnNext.addEventListener("click", () => {
    index++;
    moveCarousel();
});

btnPrev.addEventListener("click", () => {
    index--;
    moveCarousel();
});

track.addEventListener("transitionend", () => {
    if (allItems[index].classList.contains("clone")) {
        track.style.transition = "none";
        if (index >= allItems.length - itemsVisible) {
            index = itemsVisible;
        } else if (index < itemsVisible) {
            index = allItems.length - (itemsVisible * 2);
        }
        track.style.transform = `translateX(${-itemWidth * index}px)`;
    }
});
