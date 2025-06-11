document.addEventListener("DOMContentLoaded", function () {
  let currentSeason = "winter"; // Base

  const dropbtn = document.getElementById('dropdownMenuButton');
  const seasonButtons = document.querySelectorAll('.season-option');

  seasonButtons.forEach(button => {
    button.addEventListener('click', () => {
      const season = button.dataset.season;
      updateSeason(season);

      const dropdown = bootstrap.Dropdown.getInstance(dropbtn);
      if (dropdown) dropdown.hide();
    });
  });

  function updateSeason(season) {
    currentSeason = season;
    document.body.className = '';
    document.body.classList.add(season);

    // Dynamic basePath calculating (if not in index.html we need "../")
    const basePath = window.location.pathname.includes('/games/') || window.location.pathname.includes('/reviews/') || window.location.pathname.includes('/gallery') ? '../' : '';

    const seasonLogos = document.querySelectorAll('.season-logo');
    seasonLogos.forEach(logo => {
      const base = logo.dataset.base;
      logo.src = `images/logo/${base}-${season}.png`;
    });

    localStorage.setItem("selectedSeason", season);
  }

  const savedSeason = localStorage.getItem("selectedSeason");
  if (savedSeason) {
    updateSeason(savedSeason);
  } else {
    updateSeason(currentSeason);
  }
});
