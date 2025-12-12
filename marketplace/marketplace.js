const searchBar = document.getElementById('search-bar');
const filterSelect = document.getElementById('filter-select');
const sections = document.querySelectorAll('.marketplace-section');

function filterItems() {
  const searchValue = searchBar.value.toLowerCase();
  const filterValue = filterSelect.value;

  sections.forEach(section => {
    const category = section.dataset.category;
    let sectionVisible = false;

    section.querySelectorAll('.item-card').forEach(card => {
      const title = card.querySelector('h3').innerText.toLowerCase();
      const seller = card.querySelector('.seller span').innerText.toLowerCase();
      const matchesSearch = title.includes(searchValue) || seller.includes(searchValue);
      const matchesFilter = !filterValue || filterValue === category;

      if (matchesSearch && matchesFilter) {
        card.style.display = 'flex';
        sectionVisible = true;
      } else {
        card.style.display = 'none';
      }
    });

    section.style.display = sectionVisible ? 'block' : 'none';
  });
}

searchBar.addEventListener('input', filterItems);
filterSelect.addEventListener('change', filterItems);
